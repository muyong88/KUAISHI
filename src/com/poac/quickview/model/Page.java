package com.poac.quickview.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Page implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	public Page(String value) {
		name.set(value);
	} 
	public Page() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}

}
