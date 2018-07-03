package com.poac.quickview.controller;

import java.io.IOException;
import java.util.HashMap;

import com.poac.quickview.MainApp;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class TabPaneController implements IController {
	@FXML
	private TabPane tabPane;
	private MainApp mainApp; 	
	private HashMap<String,Tab> tabMap=new HashMap<>();  //存tab名（page名）对应Tab
	private HashMap<String,TabTemplateController> tabCMap=new HashMap<>(); //存tab名（page名）对应Controller
	public void openTab(String tabName) {           //打开TAb
		if(!tabMap.containsKey(tabName))
			return;
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		tabPane.getTabs().add(tabMap.get(tabName));
		selectionModel.select(tabMap.get(tabName));
	}
	public void closeTab(String tabName) {            //关闭TAB
		if(!tabMap.containsKey(tabName))
			return;
		tabPane.getTabs().remove(tabMap.get(tabName));
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	/**
	 * 创建TAB
	 */		
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
	//删除page时候调用
	public void removeTab(String tabName) {
		closeTab(tabName);
		tabMap.remove(tabName);
		tabCMap.remove(tabName);
	}
	//增加容器
	public void addContainer(String pageName,AnchorPane  container,IController tcc,String conName) {		
		tabCMap.get(pageName).addContainer(container,tcc,conName);
	} 
	public void refresh(String pageName) {		
		Platform.runLater(() -> tabCMap.get(pageName).refresh());
	} 
	//判断tab里是否存在容器名
	public boolean isExsitContainerName(String tabName,String containName) {
		return tabCMap.get(tabName).isExsitContainerName(containName);
	}
	//删除容器
	public void removeConatiner(String tabName,String containName) {
		tabCMap.get(tabName).removeContainer(containName);
	}
}
