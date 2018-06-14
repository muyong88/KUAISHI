package com.poac.quickview.controller;

import java.io.IOException;
import java.util.HashMap;

import com.poac.quickview.MainApp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class TabPaneController implements IController {
	@FXML
	private TabPane tabPane;
	private MainApp mainApp; 	
	private HashMap<String,Tab> tabMap=new HashMap<>();
	private HashMap<String,TabTemplateController> tabCMap=new HashMap<>();
	public void openTab(String tabName) {
		if(!tabMap.containsKey(tabName))
			return;
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		tabPane.getTabs().add(tabMap.get(tabName));
		selectionModel.select(tabMap.get(tabName));
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	public  void createTab(String tabName) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/TabTemplate.fxml"));
			Tab tab= loader.load();
			tab.setText(tabName);
			tabMap.put(tabName, tab);
			tabCMap.put(tabName, loader.getController());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void AddContainer(String pageName,Node container) {
		tabCMap.get(pageName).addContainer(container);
	} 
}
