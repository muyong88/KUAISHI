package com.poac.quickview.model;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TreeDataModel implements IBaseNode {
	private IBaseNode  curNode;
	private ArrayList<IBaseNode> childsData=new ArrayList<>();
	public TreeDataModel(IBaseNode curNode) {
		this.curNode=curNode;
	}
	public void add(IBaseNode childNode) {
		childsData.add(childNode);
	}
	public void remove(IBaseNode childNode) {
		childsData.remove(childNode);
	}
	public IBaseNode getChild(int i) {
		return childsData.get(i);
	}
	public IBaseNode getCurNode() {
		return curNode;
	}
	public ArrayList<IBaseNode> getChilds(){
		return childsData;
	}
	@Override
	public void setName(String value) {
		curNode.setName(value);		
	}
	@Override
	public String getName() {
		return curNode.getName();
	}

}

