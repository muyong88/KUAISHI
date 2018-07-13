package com.poac.quickview.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageParameter implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();	
	private StringProperty  codeName=new SimpleStringProperty();
	private StringProperty  unit=new SimpleStringProperty();
	private StringProperty  range=new SimpleStringProperty();
	public ImageParameter(String value) {
		name.set(value);
	} 
	public ImageParameter() {}
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
	public void setUnit(String value) {
		unit.set(value);
	}
	public String getUnit() {
		return unit.get();
	}
	public void setRange(String value) {
		range.set(value);
	}
	public String getRange() {
		return range.get();
	}
}
