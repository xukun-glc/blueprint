package ananas.lib.blueprint.awt;

import ananas.lib.blueprint.core.dom.BPAttribute;
import ananas.lib.blueprint.core.lang.CObject;

public class Ctrl_pos extends CObject implements ICtrl_pos {

	private String mValue;

	@Override
	public boolean set_attribute_value(BPAttribute attr) {
		this.mValue = attr.getValue();
		return true;
	}

	@Override
	public String getValue() {
		return this.mValue;
	}

}
