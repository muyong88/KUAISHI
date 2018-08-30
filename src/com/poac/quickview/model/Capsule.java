package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Capsule implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public Capsule(String value) {
		name.set(value);
	} 
	public Capsule() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}


}
