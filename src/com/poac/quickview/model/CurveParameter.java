package com.poac.quickview.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CurveParameter implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();	
	private StringProperty  codeName=new SimpleStringProperty();
	private StringProperty  dashstyle=new SimpleStringProperty();
	private StringProperty  linewidth=new SimpleStringProperty();
	private StringProperty  color=new SimpleStringProperty();
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
	public void setCodeName(String value) {
		codeName.set(value);
	}
	public String getCodeName() {
		return codeName.get();
	}
	public StringProperty codeNameProperty() {
		return codeName;
	}
	public void setDashstyle(String value) {
		dashstyle.set(value);
	}
	public String getDashstyle() {
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
