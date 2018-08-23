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
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class TabTemplateController implements IController {

	@FXML
	private JFXMasonryPane masonryPane;
	@FXML 
	private ScrollPane scrollpane;
	@FXML 
	private  Tab tabTemplate;
	public HashMap<String,IController> containerCon=new HashMap<>(); //����������Ӧ��Controller
	private HashMap<String,AnchorPane> containerAll=new HashMap<>(); //����������Ӧ������
	private MainApp mainApp; 
	private ObservableList<AnchorPane> anchorCollection = FXCollections.observableArrayList(); //��tab����������
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
    	//��Ƶ��ʼ�����������Ժ����
    }
	@FXML  
	private void onTabSelectChanged() {
//		System.out.println("refresh from onTabSelectChanged,Tab Name is "+tabTemplate.getText());
		refresh();		
	}
    public void addContainer(AnchorPane no,IController tCC,String conName) {   //(��������������������������)
    	containerCon.put(conName, tCC);
    	containerAll.put(conName, no);
    	anchorCollection.add(no);
    	masonryPane.getChildren().setAll(anchorCollection); 
    }    
	/**
	 * 1�����ڸ���anchorCollection����ˢ��Tab
	 * 2������layoutX,layoutY��������
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
    public void setScrollVaule(double dragTopValue) {
    	double newValue=0;
    	Bounds visibleBounds = scrollpane.getViewportBounds();
    	double totalHeight = scrollpane.getContent().getBoundsInLocal().getHeight();//�ܸ߶�
    	newValue=dragTopValue/(totalHeight-visibleBounds.getHeight());
    	scrollpane.setVvalue(newValue);
    }
    public void selectScroll(String containerName) { //������������λ��������
    	setScrollVaule(containerAll.get(containerName).getLayoutY());
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
