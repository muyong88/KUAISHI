package com.poac.quickview.controller;

import org.controlsfx.control.CheckTreeView;

import com.poac.quickview.gui.MainApp;
import com.poac.quickview.model.Page;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class SubscribeController {
	
	@FXML
	private MainApp mainApp; 
	@FXML
	private CheckTreeView checkTV_subscribe;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void initData() {

    	CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>("Data");    	
    	checkTV_subscribe.setRoot(item);
    	item.setExpanded(true);
    	
    }

}
