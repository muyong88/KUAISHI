package com.poac.quickview.controller;

import java.io.IOException;
import java.util.HashMap;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.JsonParserCustomer;

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
	private HashMap<String,Tab> tabMap=new HashMap<>();  //��tab����page������ӦTab
	private HashMap<String,TabTemplateController> tabCMap=new HashMap<>(); //��tab����page������ӦController
	public void openTab(String tabName) {           //��TAb
		if(!tabMap.containsKey(tabName))
			return;
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		tabPane.getTabs().add(tabMap.get(tabName));
		selectionModel.select(tabMap.get(tabName));
		refresh(tabName);
	}
	public void closeTab(String tabName) {            //�ر�TAB
		if(!tabMap.containsKey(tabName))
			return;
		tabPane.getTabs().remove(tabMap.get(tabName));
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    //���ݷ���PYTHON����ʼ�������Ͷ�Ӧ����
    public void initData() {
    	TreeDataModel rootM=(new JsonParserCustomer()).getPageData();
    	String pageName = rootM.getName();
    	TreeDataModel containerModel=(TreeDataModel)rootM.getChild(0);
    	Container container=(Container)containerModel.getCurNode();
    	mainApp.addTableContainer(pageName, container.getWidth(), container.getHeight(),container.getName());
    	tabCMap.get(pageName).initData(containerModel);
    }
	/**
	 * ����TAB
	 */		
	public  void createTab(String tabName) {               
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/TabTemplate.fxml"));
			Tab tab= loader.load();
			tab.setText(tabName);
			tabMap.put(tabName, tab);
			TabTemplateController  tT=loader.getController();
			tabCMap.put(tabName, tT);
			tT.setMainApp(mainApp);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//ɾ��pageʱ����� 
	public void removeTab(String tabName) {
		closeTab(tabName);
		tabMap.remove(tabName);
		tabCMap.remove(tabName);
	}
	//��������,(ҳ�����ƣ�����������Controller,��������)
	public void addContainer(String pageName,AnchorPane  container,IController tcc,String conName) {		
		tabCMap.get(pageName).addContainer(container,tcc,conName);
	} 
	public void refresh(String pageName) {	
    	tabMap.get(pageName).getContent().requestFocus();            //ˢ��Tab����
		Platform.runLater(() -> tabCMap.get(pageName).refresh());
	} 
	//�ж�tab���Ƿ����������
	public boolean isExsitContainerName(String tabName,String containName) {
		return tabCMap.get(tabName).isExsitContainerName(containName);
	}
	//ɾ������
	public void removeConatiner(String tabName,String containName) {
		tabCMap.get(tabName).removeContainer(containName);
	}
}
