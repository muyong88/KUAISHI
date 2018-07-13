package com.poac.quickview.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Container implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	private StringProperty  type=new SimpleStringProperty();
	private DoubleProperty  width=new SimpleDoubleProperty();
	private DoubleProperty height=new SimpleDoubleProperty();
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
		height.set(Double.parseDouble(value));
	}
	public double getHeight() {
		return height.get();
	}
	public void setWidth(String value) {
		width.set(Double.parseDouble(value));
	}
	public double getWidth() {
		return width.get();
	}
	public void setType(String value) {
		type.set(value);
	}
	public String getType() {
		return type.get();
	}
}

