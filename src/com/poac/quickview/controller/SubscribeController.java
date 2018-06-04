package com.poac.quickview.controller;

import org.controlsfx.control.CheckTreeView;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Page;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

public class SubscribeController {
	
	@FXML
	private MainApp mainApp; 
	@FXML
	private CheckTreeView checkTV_subscribe;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    
    public void initData() {

    	CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>("Group：力学所");    	
    	checkTV_subscribe.setRoot(item);
    	item.setExpanded(true);
    	CheckBoxTreeItem<String> item1 = new CheckBoxTreeItem<>("Type：data");  
    	CheckBoxTreeItem<String> item2 = new CheckBoxTreeItem<>("Type：image");  
    	CheckBoxTreeItem<String> item3 = new CheckBoxTreeItem<>("Type：video");  
    	CheckBoxTreeItem<String> item4 = new CheckBoxTreeItem<>("Type：curve");  
    	item.getChildren().add(item1);
    	item.getChildren().add(item2);
    	item.getChildren().add(item3);
    	item.getChildren().add(item4);
    	CheckBoxTreeItem<String> item5 = new CheckBoxTreeItem<>("Topic:流体实验柜-遥测数据-空间三相多液滴迁移"); 
    	CheckBoxTreeItem<String> item6 = new CheckBoxTreeItem<>("Topic:流体实验柜-工程数据-实验柜控制器A1"); 
    	CheckBoxTreeItem<String> item7 = new CheckBoxTreeItem<>("Topic:天和-工程数据"); 
    	item1.getChildren().add(item5);
    	item1.getChildren().add(item6);
    	item1.getChildren().add(item7);
    	item1.setExpanded(true);
    	CheckBoxTreeItem<String> item8 = new CheckBoxTreeItem<>("Topic:高等植物-应用数据JPEG"); 
    	item2.getChildren().add(item8);
    	item2.setExpanded(true);
    	CheckBoxTreeItem<String> item9 = new CheckBoxTreeItem<>("Topic:流体实验柜-工程数据-实验柜控制器A1"); 
    	item4.getChildren().add(item9);
    	item4.setExpanded(true);
    	CheckBoxTreeItem<String> item10 = new CheckBoxTreeItem<>("Data_Name：俯仰姿态角度估值");  
    	CheckBoxTreeItem<String> item11 = new CheckBoxTreeItem<>("Data_Name：偏航姿态角度估值");  
    	CheckBoxTreeItem<String> item12 = new CheckBoxTreeItem<>("Data_Name：滚动姿态角度估值");  
    	CheckBoxTreeItem<String> item13 = new CheckBoxTreeItem<>("Data_Name：俯仰角速度预估");  
    	item7.getChildren().add(item10);
    	item7.getChildren().add(item11);
    	item7.getChildren().add(item12);
    	item7.getChildren().add(item13);
    	item7.setExpanded(true);
    	CheckBoxTreeItem<String> item14 = new CheckBoxTreeItem<>("Data_Name：A1温度1");  
    	CheckBoxTreeItem<String> item15 = new CheckBoxTreeItem<>("Data_Name：A1温度2"); 
    	item6.getChildren().add(item14);
    	item6.getChildren().add(item15);
    	item6.setExpanded(true);
    	CheckBoxTreeItem<String> item16 = new CheckBoxTreeItem<>("Data_Name：液滴温度1");  
    	CheckBoxTreeItem<String> item17 = new CheckBoxTreeItem<>("Data_Name：液滴温度2"); 
    	item5.getChildren().add(item16);
    	item5.getChildren().add(item17);
    	item5.setExpanded(true);
    	CheckBoxTreeItem<String> item18 = new CheckBoxTreeItem<>("Data_Name：温度1"); 
    	CheckBoxTreeItem<String> item19 = new CheckBoxTreeItem<>("Data_Name：温度2"); 
    	CheckBoxTreeItem<String> item20 = new CheckBoxTreeItem<>("Data_Name：高等植物图像"); 
    	item8.getChildren().add(item18);
    	item8.getChildren().add(item19);
    	item8.getChildren().add(item20);
    	item8.setExpanded(true);
    	CheckBoxTreeItem<String> item21 = new CheckBoxTreeItem<>("Data_Name：#0000ff");  
    	CheckBoxTreeItem<String> item22 = new CheckBoxTreeItem<>("Data_Name：#ff8040"); 
    	item9.getChildren().add(item21);
    	item9.getChildren().add(item22);
    	item9.setExpanded(true);
    	
    } 

}
