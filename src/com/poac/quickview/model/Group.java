package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Group implements BaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public Group(String value) {
		name.set(value);
	} 
	public Group() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
}
