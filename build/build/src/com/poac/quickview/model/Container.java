package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Container implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();
	private StringProperty  positionX=new SimpleStringProperty();
	private StringProperty  positionY=new SimpleStringProperty();
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
	public void setPositionX(String value) {
		positionX.set(value);
	}
	public double getPositionX() {
		return Double.parseDouble(positionX.get());
	}
	public void setPositionY(String value) {
		positionY.set(value);
	}
	public double getPositionY() {
		return Double.parseDouble(positionY.get());
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

