package ananas.lib.blueprint.loader.reflect.dom;

import java.io.PrintStream;

import ananas.lib.blueprint.core.lang.BPEnvironment;
import ananas.lib.blueprint.core.lang.BPNamespace;

public class RefElement_element extends RefElement {

	private String mLocalName;
	private String mCtrlClass;
	private String mTargetClass;

	public RefElement_element(RefDocument ownerDoc) {
		super(ownerDoc);
	}

	@Override
	public boolean setAttribute(String attrURI, String attrLName,
			String attrValue) {

		if (attrLName == null) {
			return false;

		} else if (attrLName.equals("controllerClass")) {
			this.mCtrlClass = attrValue;

		} else if (attrLName.equals("targetClass")) {
			this.mTargetClass = attrValue;

		} else if (attrLName.equals("name")) {
			this.mLocalName = attrValue;

		} else {
			return false;
		}
		return true;
	}

	@Override
	public boolean appendChild(RefNode child) {
		return false;
	}

	@Override
	public void printSelf(PrintStream out) {
		out.println("<element name='" + this.mLocalName + "' />");
	}

	public void regElement(IRefProperties properties, BPEnvironment envi,
			BPNamespace ns) {

		properties.put(IRefProperties.ns_localName, this.mLocalName);

		String targetClassName = this.caleClassName(properties,
				this.mTargetClass, IRefProperties.ns_defaultTargetClass);
		String ctrlClassName = this.caleClassName(properties, this.mCtrlClass,
				IRefProperties.ns_defaultControllerClass);

		MyElementType.Config conf = new MyElementType.Config();
		conf.targetClass = this.classByName(targetClassName);
		conf.ctrlClass = this.classByName(ctrlClassName);
		conf.ownerNS = ns;
		conf.localName = this.mLocalName;
		MyElementType elementType = new MyElementType(conf);

		String prefixAttr = properties.get(
				IRefProperties.ns_set_attr_method_prefix, true,
				"set_attribute_", true);
		String prefixChild = properties.get(
				IRefProperties.ns_append_child_method_prefix, true,
				"append_child_", true);
		elementType.reflectMethods(prefixAttr, prefixChild);
		ns.registerType(elementType);

	}

	private String caleClassName(IRefProperties properties, String className,
			String defaultClassNameKey) {
		if (className == null) {
			className = properties.get(defaultClassNameKey, false, null);
		}
		className = properties.processMacro(className);
		return className;
	}

	private Class<?> classByName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
