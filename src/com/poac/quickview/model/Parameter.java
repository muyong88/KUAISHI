package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Parameter implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();	
	public Parameter(String value) {
		name.set(value);
	} 
	public Parameter() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
	public StringProperty nameProperty() {
		return name;
	}
}
