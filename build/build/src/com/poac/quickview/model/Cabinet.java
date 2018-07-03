package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cabinet implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public Cabinet(String value) {
		name.set(value);
	} 
	public Cabinet() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
}
