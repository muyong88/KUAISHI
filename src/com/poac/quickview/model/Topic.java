package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Topic implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public Topic(String value) {
		name.set(value);
	} 
	public Topic() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}

}
