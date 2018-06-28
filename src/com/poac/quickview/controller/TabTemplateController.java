package com.poac.quickview.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

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
import javafx.scene.layout.FlowPane;

public class TabTemplateController implements IController {

	@FXML
	private JFXMasonryPane masonryPane;
	@FXML
	private ScrollPane scrollpane;
	private HashMap<String,TableContainerController> containerCon=new HashMap<>(); //存容器名对应的Controller
	private HashMap<String,Node> containerAll=new HashMap<>(); //存容器名对应的容器
	private MainApp mainApp; 
	private ObservableList<Node> anchorCollection = FXCollections.observableArrayList(); //存tab里所有容器
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void addContainer(AnchorPane no,TableContainerController tCC,String conName) {
    	containerCon.put(conName, tCC);
    	containerAll.put(conName, no);
    	anchorCollection.add(no);
    	refresh(); 
    }    
	/**
	 * 1、用于根据anchorCollection内容刷新Tab
	 * 2、根据layoutX,layoutY排序容器
	 * 3、刷新scrollpane，解决延迟问题
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
    	containerAll.remove(name);
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
