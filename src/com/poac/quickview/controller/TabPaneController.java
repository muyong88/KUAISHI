package com.poac.quickview.controller;

import java.io.IOException;
import java.util.HashMap;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.JsonParserCustomer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class TabPaneController implements IController {
	@FXML
	private TabPane tabPane;     //ѡ���ؼ�               
	@FXML
	private Tab defaultTab;      //Ĭ��Tab����ʹ��˵��Tab
	private MainApp mainApp; 	 //Ӧ�ó���ӿ�
	private HashMap<String,Tab> tabMap=new HashMap<>();  //��tab����page������ӦTab
	public HashMap<String,TabTemplateController> tabCMap=new HashMap<>(); //��tab����page������ӦController
    /**
     * ��Tab,���tabMap����tabName��ӦTab��򿪣���ѡ�����Tab������ֱ��return
     * @param tabName
     */
	public void openTab(String tabName) {            
		if(!tabMap.containsKey(tabName))
			return;
		if(!tabPane.getTabs().contains(tabMap.get(tabName))) {   //tabPane�м���Tab
			tabPane.getTabs().add(tabMap.get(tabName));
		}
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();	
		selectionModel.select(tabMap.get(tabName));    //ѡ�����Tab
	}
    /**
     * �ر�TAB
     * @param tabName
     */
	public void closeTab(String tabName) {            
		if(!tabMap.containsKey(tabName))
			return;
		tabPane.getTabs().remove(tabMap.get(tabName));
	}
	//����Ӧ�ó�����ʽӿ�
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * ���ݷ���PYTHON����ʼ�������Ͷ�Ӧ����
     */
    public void initData() {
    	TreeDataModel rootM=(new JsonParserCustomer()).getPageData();
    	String pageName = rootM.getName();
    	openTab(pageName);      //����򿪣���Ȼ��ʾ�ӳ�
		for (int i = 0; i < rootM.getChilds().size(); i++) {
			TreeDataModel containerModel = (TreeDataModel) rootM.getChild(i);
			Container container = (Container) containerModel.getCurNode();
			if (container.getType().equals("data")) {
				mainApp.addTableContainer(pageName, container.getWidth(), container.getHeight(), container.getName());
			} else if (container.getType().equals("image")) {
				mainApp.addImageContainer(pageName, container.getWidth(), container.getHeight(), container.getName());
			} else if (container.getType().equals("curve")) {
				mainApp.addCurveContainer(pageName, container.getWidth(), container.getHeight(), container.getName());
			} else if (container.getType().equals("video")) {
				mainApp.addVideoContainer(pageName, container.getWidth(), container.getHeight(), container.getName());
			}
			tabCMap.get(pageName).initData(containerModel);
		}
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
	/**
	 * ������־TAB
	 */		
	public  void createTabLog(String tabName) {               
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/TabTemplateLog.fxml"));
			Tab tab= loader.load();
			tab.setText(tabName);
			tabMap.put(tabName, tab);
			TabTemplateLogController  tTL=loader.getController();
			tTL.setMainApp(mainApp);	
			tTL.init();
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
	//��������,����(ҳ�����ƣ�����������Controller,��������)
	public void addContainer(String pageName,AnchorPane  container,IController tcc,String conName) {		
		tabCMap.get(pageName).addContainer(container,tcc,conName);
	} 
	//�������԰�,����(ҳ�����ƣ�����)
	public void addMessageBoard(String pageName,AnchorPane  container) {		
		tabCMap.get(pageName).addMessageBoard(container);
	} 
	//ɾ�����԰�,����(ҳ�����ƣ�����)
	public void removeMessageBoard(AnchorPane  container) {
		tabCMap.get("��Ϣ����").removeMessageBoard(container);
	}
	//ˢ������
	public void refresh(String pageName) {	
//		System.out.println("refresh from "+pageName+" refresh");
		Platform.runLater(() ->tabCMap.get(pageName).refresh()); 
	} 
	//�ж�tab���Ƿ����������
	public boolean isExsitContainerName(String tabName,String containName) {
		return tabCMap.get(tabName).isExsitContainerName(containName);
	}
	//ɾ������
	public void removeConatiner(String tabName,String containName) {
		tabCMap.get(tabName).removeContainer(containName);
	}
	//����Ĭ�ϴ�Tab
	public void setDefaultTab() {
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();	
		selectionModel.select(defaultTab);
	}
	//����TabTemplate������
	public TabTemplateController getTabTemplateController(String tabName) {
		return tabCMap.get(tabName);
	}
}
