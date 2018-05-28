package com.poac.quickview.controller;

import org.controlsfx.control.CheckTreeView;

import com.poac.quickview.gui.MainApp;

import javafx.fxml.FXML;
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
    	TreeItem<String> item = new TreeItem<>("ÌìºÍ");
    	checkTV_subscribe.setRoot(item);
    }

}
