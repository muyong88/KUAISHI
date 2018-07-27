package com.poac.quickview.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CurveParameter implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();	
	private StringProperty  dashstyle=new SimpleStringProperty();
	private StringProperty  linewidth=new SimpleStringProperty();
	private StringProperty  color=new SimpleStringProperty();
	private BooleanProperty isSubscribe=new SimpleBooleanProperty();
	public CurveParameter(String value) {
		name.set(value);
	} 
	public CurveParameter() {}
	public void setName(String value) {
		name.set(value);
	}
	public String getName() {
		return name.get();
	}
	public StringProperty nameProperty() {
		return name;
	}
	public void setDashstyle(String value) {
		dashstyle.set(value);
	}
	public String getCodeName() {
		return dashstyle.get();
	}
	public void setLinewidth(String value) {
		linewidth.set(value);
	}
	public String getLinewidth() {
		return linewidth.get();
	}
	public void setColor(String value) {
		color.set(value);
	}
	public String getColor() {
		return color.get();
	}

}
