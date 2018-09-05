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
	private TabPane tabPane;     //选项板控件               
	@FXML
	private Tab defaultTab;      //默认Tab，即使用说明Tab
	private MainApp mainApp; 	 //应用程序接口
	private HashMap<String,Tab> tabMap=new HashMap<>();  //存tab名（page名）对应Tab
	public HashMap<String,TabTemplateController> tabCMap=new HashMap<>(); //存tab名（page名）对应Controller
    /**
     * 打开Tab,如果tabMap包含tabName对应Tab则打开，并选择这个Tab，否则直接return
     * @param tabName
     */
	public void openTab(String tabName) {            
		if(!tabMap.containsKey(tabName))
			return;
		if(!tabPane.getTabs().contains(tabMap.get(tabName))) {   //tabPane中加入Tab
			tabPane.getTabs().add(tabMap.get(tabName));
		}
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();	
		selectionModel.select(tabMap.get(tabName));    //选择这个Tab
	}
    /**
     * 关闭TAB
     * @param tabName
     */
	public void closeTab(String tabName) {            
		if(!tabMap.containsKey(tabName))
			return;
		tabPane.getTabs().remove(tabMap.get(tabName));
	}
	//设置应用程序访问接口
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    /**
     * 根据返回PYTHON，初始化容器和对应数据
     */
    public void initData() {
    	TreeDataModel rootM=(new JsonParserCustomer()).getPageData();
    	String pageName = rootM.getName();
    	openTab(pageName);      //必须打开，不然显示延迟
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
	 * 创建TAB 
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
	 * 创建日志TAB
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
	//删除page时候调用 
	public void removeTab(String tabName) {
		closeTab(tabName);
		tabMap.remove(tabName);
		tabCMap.remove(tabName);
	}
	//增加容器,参数(页面名称，容器，容器Controller,容器名称)
	public void addContainer(String pageName,AnchorPane  container,IController tcc,String conName) {		
		tabCMap.get(pageName).addContainer(container,tcc,conName);
	} 
	//增加留言板,参数(页面名称，容器)
	public void addMessageBoard(String pageName,AnchorPane  container) {		
		tabCMap.get(pageName).addMessageBoard(container);
	} 
	//删除留言板,参数(页面名称，容器)
	public void removeMessageBoard(AnchorPane  container) {
		tabCMap.get("消息留言").removeMessageBoard(container);
	}
	//刷新容器
	public void refresh(String pageName) {	
//		System.out.println("refresh from "+pageName+" refresh");
		Platform.runLater(() ->tabCMap.get(pageName).refresh()); 
	} 
	//判断tab里是否存在容器名
	public boolean isExsitContainerName(String tabName,String containName) {
		return tabCMap.get(tabName).isExsitContainerName(containName);
	}
	//删除容器
	public void removeConatiner(String tabName,String containName) {
		tabCMap.get(tabName).removeContainer(containName);
	}
	//设置默认打开Tab
	public void setDefaultTab() {
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();	
		selectionModel.select(defaultTab);
	}
	//返回TabTemplate控制器
	public TabTemplateController getTabTemplateController(String tabName) {
		return tabCMap.get(tabName);
	}
}
