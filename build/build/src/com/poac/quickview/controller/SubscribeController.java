package com.poac.quickview.controller;

import org.controlsfx.control.CheckListView;
import com.poac.quickview.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SubscribeController implements IController {
	
	@FXML
	private MainApp mainApp; 
	@FXML
	private TreeView treeview_s;
	@FXML
	private CheckListView checkListView_1;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }    
    
    public void initData() {
    	TreeItem<String> item = new TreeItem<>("Group：力学所");    	
    	treeview_s.setRoot(item);
    	item.setExpanded(true);
    	TreeItem<String> item1 = new TreeItem<>("Type：data");      	
    	TreeItem<String> item2 = new TreeItem<>("Type：image");  
    	TreeItem<String> item3 = new TreeItem<>("Type：video");  
    	TreeItem<String> item4 = new TreeItem<>("Type：curve");  
    	item.getChildren().add(item1);
    	item.getChildren().add(item2);
    	item.getChildren().add(item3);
    	item.getChildren().add(item4);
    	TreeItem<String> item5 = new TreeItem<>("Topic:流体实验柜-遥测数据-空间三相多液滴迁移"); 
    	TreeItem<String> item6 = new TreeItem<>("Topic:流体实验柜-工程数据-实验柜控制器A1"); 
    	TreeItem<String> item7 = new TreeItem<>("Topic:天和-工程数据"); 
    	item1.getChildren().add(item5);
    	item1.getChildren().add(item6);
    	item1.getChildren().add(item7);
    	item1.setExpanded(true);
    	TreeItem<String> item8 = new TreeItem<>("Topic:高等植物-应用数据JPEG"); 
    	item2.getChildren().add(item8);
    	item2.setExpanded(true);
    	TreeItem<String> item9 = new TreeItem<>("Topic:流体实验柜-工程数据-实验柜控制器A1"); 
    	item4.getChildren().add(item9);
    	item4.setExpanded(true);
    	treeview_s.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem<String> selectedItem = (TreeItem<String>) newValue;				
				if(selectedItem.getValue().contains("天和-工程数据")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("俯仰姿态角度估值","偏航姿态角度估值","滚动姿态角度估值","俯仰角速度预估");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("流体实验柜-工程数据-实验柜控制器A1")&&selectedItem.getParent().getValue().contains("流体实验柜-工程数据-实验柜控制器A1")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("A1温度1","A1温度2");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("流体实验柜-遥测数据-空间三相多液滴迁移")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("液滴温度1","液滴温度2");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("高等植物-应用数据JPEG")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("温度1","温度2","高等植物图像");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("高等植物-应用数据JPEG")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("温度1","温度2","高等植物图像");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("流体实验柜-工程数据-实验柜控制器A1")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("#0000ff","#ff8040");  
					checkListView_1.setItems(strList);
				}
				else {
					checkListView_1.getItems().clear();
				}
			}
		});
    } 

}
