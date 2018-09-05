package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MessageBorad implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public MessageBorad(String value) {
		name.set(value);
	} 
	public MessageBorad() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}

}
