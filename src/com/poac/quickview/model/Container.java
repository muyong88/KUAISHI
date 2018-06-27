package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Container implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	private StringProperty  height=new SimpleStringProperty();
	private StringProperty  width=new SimpleStringProperty();
	public Container(String value) {
		name.set(value);
	} 
	public Container() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
	public void setHeight(String value) {
		height.set(value);
	}
	public double getHeight() {
		return Double.parseDouble(height.get());
	}
	public void setWidth(String value) {
		width.set(value);
	}
	public double getWidth() {
		return Double.parseDouble(width.get());
	}
}

