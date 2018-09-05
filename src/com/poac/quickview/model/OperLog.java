package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OperLog implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public OperLog(String value) {
		name.set(value);
	} 
	public OperLog() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}

}
