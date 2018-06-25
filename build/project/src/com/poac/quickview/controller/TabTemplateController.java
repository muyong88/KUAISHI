package com.poac.quickview.controller;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.poac.quickview.MainApp;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public void addContainer(AnchorPane no) {
    	masonryPane.getChildren().add(no); 
    }
    public void refresh() {    	
    	masonryPane.requestLayout();
    	scrollpane.requestLayout();
    	JFXScrollPane.smoothScrolling(scrollpane);
    }
}
