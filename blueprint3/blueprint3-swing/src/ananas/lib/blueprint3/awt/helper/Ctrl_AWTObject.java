package ananas.lib.blueprint3.awt.helper;

import ananas.lib.blueprint3.core.dom.BPAttribute;
import ananas.lib.blueprint3.core.lang.CObject;

public class Ctrl_AWTObject extends CObject {

	private String mId;

	public boolean set_attribute_id(BPAttribute id) {
		this.mId = id.getValue();
		return true;
	}

	public String getId() {
		return this.mId;
	}
}
