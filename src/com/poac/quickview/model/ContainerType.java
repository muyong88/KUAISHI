package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContainerType implements BaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public ContainerType(String value) {
		name.set(value);
	} 
	public ContainerType() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
}
