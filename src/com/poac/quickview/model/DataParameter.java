package com.poac.quickview.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataParameter implements IBaseNode {
	private StringProperty  name=new SimpleStringProperty();	
	private StringProperty  codeName=new SimpleStringProperty();
	private StringProperty  unit=new SimpleStringProperty();
	private StringProperty  range=new SimpleStringProperty();
	private StringProperty value=new SimpleStringProperty();
	private StringProperty  time=new SimpleStringProperty();
	public DataParameter(String value) {
		name.set(value);
	} 
	public DataParameter() {}
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
	public void setUnit(String value) {
		unit.set(value);
	}
	public String getUnit() {
		return unit.get();
	}
	public StringProperty unitProperty() {
		return unit;
	}
	public void setRange(String value) {
		range.set(value);
	}
	public String getRange() {
		return range.get();
	}
	public StringProperty rangeProperty() {
		return range;
	}
	public void setValue(String v) {
		value.set(v);
	}
	public String getValue() {
		return value.get();
	}
	public StringProperty valueProperty() {
		return value;
	}
	public void setTime(String value) {
		time.set(value);
	}
	public String getTime() {
		return time.get();
	}
	public StringProperty timeProperty() {
		return time;
	}
	}

