package com.poac.quickview.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXScrollPane;
import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.TreeDataModel;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class TabTemplateController implements IController {

	@FXML
	private JFXMasonryPane masonryPane;
	@FXML 
	private ScrollPane scrollpane;
	private HashMap<String,IController> containerCon=new HashMap<>(); //存容器名对应的Controller
	private HashMap<String,Node> containerAll=new HashMap<>(); //存容器名对应的容器
	private MainApp mainApp; 
	private ObservableList<Node> anchorCollection = FXCollections.observableArrayList(); //存tab里所有容器
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
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
	private void onTabSelectChanged() {
		refresh();
	}
    public void addContainer(AnchorPane no,IController tCC,String conName) {   //(容器，容器控制器，容器名称)
    	containerCon.put(conName, tCC);
    	containerAll.put(conName, no);
    	anchorCollection.add(no);
    	masonryPane.getChildren().setAll(anchorCollection); 
    }    
	/**
	 * 1、用于根据anchorCollection内容刷新Tab
	 * 2、根据layoutX,layoutY排序容器
	 * 
	 */		
    public void refresh() {   
    	sortContainer();
    	masonryPane.requestLayout();        	
    	scrollpane.requestLayout();
    	JFXScrollPane.smoothScrolling(scrollpane);
    }
    public boolean isExsitContainerName(String name) {
    	if(containerCon.containsKey(name))
    		return true;
    	return false;
    }
    public void removeContainer(String name) {    	
    	anchorCollection.remove(containerAll.get(name));
    	containerCon.remove(name);
    	containerAll.remove(name);
    }
    public void setScrollVaule(double dragTopValue,double dragBottomValue) {
    	double newValue=0;
    	Bounds visibleBounds = scrollpane.getViewportBounds();
    	double totalHeight = scrollpane.getContent().getBoundsInLocal().getHeight();//总高度
    	double vValue = scrollpane.getVvalue();
    	double vMinHeight=vValue*totalHeight;
    	double vMaxHeight=vValue*totalHeight+visibleBounds.getHeight();
    	double scrollWidth = totalHeight - visibleBounds.getHeight();//待滚动宽度
    	//System.out.println(vMinHeight+" setScrollVaule  "+vMaxHeight);
    	if(dragBottomValue<=vMaxHeight&&dragTopValue>=vMinHeight)
    		return;
    	if(dragTopValue<vMinHeight) {
    		newValue=vValue-(vMinHeight-dragTopValue)/totalHeight;
    	}else if(dragBottomValue>vMaxHeight) {
    		newValue=vValue+(dragBottomValue-vMaxHeight)/totalHeight;
    	}
    	scrollpane.setVvalue(newValue);
    }
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
