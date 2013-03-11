package ananas.lib.impl.blueprint3.core;

import ananas.lib.blueprint3.core.dom.BPAttribute;
import ananas.lib.blueprint3.core.dom.BPDocument;
import ananas.lib.blueprint3.core.dom.BPElement;
import ananas.lib.blueprint3.core.dom.BPElementMap;
import ananas.lib.blueprint3.core.dom.BPNode;
import ananas.lib.blueprint3.core.dom.BPText;
import ananas.lib.blueprint3.core.lang.BPEnvironment;
import ananas.lib.blueprint3.core.lang.BPNamespace;
import ananas.lib.blueprint3.core.lang.BPType;

public class BpDocumentImpl implements BPDocument {

	private final String mDocURI;
	private final BPEnvironment mEnvironment;
	private BPElement mRoot;
	private BPElementMap mElementReg;

	public BpDocumentImpl(BPEnvironment envi, String uri) {
		this.mEnvironment = envi;
		this.mDocURI = uri;
	}

	@Override
	public boolean appendChild(BPNode newChild) {
		if (newChild instanceof BPElement) {
			this.mRoot = (BPElement) newChild;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeChild(BPNode theChild) {
		if (theChild != null) {
			if (theChild.equals(this.mRoot)) {
				this.mRoot = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public void setParent(BPNode newParent) {
	}

	@Override
	public BPNode getParent() {
		return null;
	}

	@Override
	public BPDocument getOwnerDocument() {
		return null;
	}

	@Override
	public boolean bindOwnerDocument(BPDocument ownerDoc) {
		return false;
	}

	@Override
	public BPElement getRootElement() {
		return this.mRoot;
	}

	@Override
	public String getDocumentURI() {
		return this.mDocURI;
	}

	@Override
	public BPEnvironment getEnvironment() {
		return this.mEnvironment;
	}

	@Override
	public BPText createText(String data) {
		return new BpTextImpl(data);
	}

	private BPElement _createElement(String uri, String localName) {

		BPNamespace pkg = this.mEnvironment.getNamespaceRegistrar()
				.getNamespace(uri);

		if (pkg == null) {
			System.err.println("No namespace!");
			System.err.println("    namespaceURI:" + uri);
			System.err.println("       loaclName:" + localName);
			return null;
		}
		BPType bpcls = pkg.getType(localName);
		if (bpcls == null) {
			System.err.println("No class!");
			System.err.println("    namespaceURI:" + uri);
			System.err.println("       loaclName:" + localName);
			return null;
		}
		Class<?> cls = bpcls.getControllerClass();
		try {
			BPElement node = (BPElement) cls.newInstance();
			node.bindType(bpcls);
			node.bindOwnerDocument(this);
			return node;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BPElement createElement(String uri, String localName) {
		return this._createElement(uri, localName);
	}

	@Override
	public BPElement findElementById(String id) {
		BPElementMap ereg = this.mElementReg;
		if (ereg == null) {
			return null;
		} else {
			return ereg.get(id);
		}
	}

	@Override
	public BPElement findElementByURI(String uri) {
		BPElementMap ereg = this.mElementReg;
		if (ereg == null) {
			return null;
		} else {
			String id = uri;
			return ereg.get(id);
		}
	}

	@Override
	public Object findTargetById(String id) {
		BPElement ele = this.findElementById(id);
		if (ele == null) {
			return null;
		} else {
			return ele.getTarget(true);
		}
	}

	@Override
	public Object findTargetByURI(String uri) {
		BPElement ele = this.findElementByURI(uri);
		if (ele == null) {
			return null;
		} else {
			return ele.getTarget(true);
		}
	}

	@Override
	public BPElementMap getElementRegistrar() {
		BPElementMap reg = this.mElementReg;
		if (reg == null) {
			this.mElementReg = reg = new BPElementMapImpl();
		}
		return reg;
	}

	@Override
	public void setElementRegistrar(BPElementMap reg) {
		if (reg != null) {
			this.mElementReg = reg;
		}
	}

	@Override
	public BPAttribute createAttribute(String uri, String localName,
			String value) {

		return new BpAttrImpl(uri, localName, value);
	}

	@Override
	public String getLocalName() {
		return null;
	}

	@Override
	public String getNamespaceURI() {
		return null;
	}

}