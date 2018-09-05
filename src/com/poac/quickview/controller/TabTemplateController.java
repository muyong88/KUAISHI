package com.poac.quickview.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.poac.quickview.MainApp;
import com.poac.quickview.model.TreeDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

public class TabTemplateController implements IController {

	@FXML
	private JFXMasonryPane masonryPane;                 //flowPane
	@FXML 
	private ScrollPane scrollpane;                      //滚动条pane
	@FXML 
	private  Tab tabTemplate;                           //模板tab
	public HashMap<String,IController> containerCon=new HashMap<>(); //存容器名对应的Controller
	private HashMap<String,AnchorPane> containerAll=new HashMap<>(); //存容器名对应的容器AnchorPane
	private ObservableList<AnchorPane> anchorCollection = FXCollections.observableArrayList(); //存tab里所有容器
    public void setMainApp(MainApp mainApp) {
    } 
    //初始化数据，调用容器控制器初始化数据
    public void initData(TreeDataModel containerModel) {
    	String containerName=containerModel.getName();
    	if(containerCon.get(containerName).getClass().getName().contains("TableContainerController")){
    		((TableContainerController)containerCon.get(containerName)).initData(containerModel);
    	}else if(containerCon.get(containerName).getClass().getName().contains("CurveContainerController")){
    		((CurveContainerController)containerCon.get(containerName)).initData(containerModel);
    	}else if(containerCon.get(containerName).getClass().getName().contains("ImageContainerController")){
    		((ImageContainerController)containerCon.get(containerName)).initData(containerModel);
    	}    	
    	//视频初始化数据留待以后完成
    }
	@FXML  
	private void onTabSelectChanged() {             //Tab选择变化时响应事件
//		System.out.println("refresh from onTabSelectChanged,Tab Name is "+tabTemplate.getText());
		refresh();		
	}
	//增加容器，参数(容器，容器控制器，容器名称)
    public void addContainer(AnchorPane no,IController tCC,String conName) { 
    	containerCon.put(conName, tCC);
    	containerAll.put(conName, no);
    	anchorCollection.add(no);
    	masonryPane.getChildren().setAll(anchorCollection); 
    }    
    //添加消息留言板
    public void addMessageBoard(AnchorPane  no) {
    	anchorCollection.add(no);
    }
    //删除消息留言板
    public void removeMessageBoard(AnchorPane  no) {
    	anchorCollection.remove(no);
    	masonryPane.getChildren().setAll(anchorCollection); 
    }
	/**
	 * 1、用于根据anchorCollection内容刷新Tab
	 * 2、根据layoutX,layoutY排序容器
	 */		
    public void refresh() {   
    	sortContainer();
    	masonryPane.requestLayout();        	
    	scrollpane.requestLayout();
    	JFXScrollPane.smoothScrolling(scrollpane);
    }
    //判断是否存在容器名
    public boolean isExsitContainerName(String name) {
    	if(containerCon.containsKey(name))
    		return true;
    	return false;
    }
    //删除容器
    public void removeContainer(String name) {    	
    	anchorCollection.remove(containerAll.get(name));
    	containerCon.remove(name);
    	containerAll.remove(name);
    }
    //设置滚动条位置
    public void setScrollVaule(double dragTopValue) {
    	double newValue=0;
    	Bounds visibleBounds = scrollpane.getViewportBounds();
    	double totalHeight = scrollpane.getContent().getBoundsInLocal().getHeight();//总高度
    	newValue=dragTopValue/(totalHeight-visibleBounds.getHeight());
    	scrollpane.setVvalue(newValue);
    }
    //用于参数搜索功能模块定位参数
    public void selectScroll(String containerName) { 
    	setScrollVaule(containerAll.get(containerName).getLayoutY());
    }
    //排序容器
    private void sortContainer() {
    	Collections.sort(anchorCollection, new NodeComparator());
    	masonryPane.getChildren().setAll(anchorCollection); 
    }
    private static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            double o1X = o1.getLayoutX();
            double o1Y = o1.getLayoutY();
            double o2X = o2.getLayoutX();
            double o2Y = o2.getLayoutY();
            if(o1Y<o2Y) {
            	return -1;
            	}else if(o1Y>o2Y) {
            		return 1;
            	}else{
            		if(o1X<o2X) {
            			return  -1;
            		}else if(o1X>o2X){
            			return 1;
            		}else {
            			return 0;
            		}
            	}        	
        }
    }
}
