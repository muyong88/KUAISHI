package com.poac.quickview.controller;

import com.jfoenix.controls.JFXMasonryPane;
import com.poac.quickview.MainApp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class TabTemplateController implements IController {

	@FXML
	private JFXMasonryPane masonryPane;
	private MainApp mainApp; 
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void addContainer(Node no) {
    	masonryPane.getChildren().add(no);
    }
}

