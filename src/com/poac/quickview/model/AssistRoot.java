package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AssistRoot implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public AssistRoot(String value) {
		name.set(value);
	} 
	public AssistRoot() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}

}
