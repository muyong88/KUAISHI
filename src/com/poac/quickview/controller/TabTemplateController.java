package com.poac.quickview.controller;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.poac.quickview.MainApp;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class TabTemplateController implements IController {

	@FXML
	private JFXMasonryPane masonryPane;
	@FXML
	private ScrollPane scrollpane;
	private MainApp mainApp; 
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void addContainer(Node no) {
    	masonryPane.getChildren().add(no);  
    }
}

