package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Payload implements BaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public Payload(String value) {
		name.set(value);
	} 
	public Payload() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
}
