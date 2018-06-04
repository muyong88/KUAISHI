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

    	CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>("Group����ѧ��");    	
    	checkTV_subscribe.setRoot(item);
    	item.setExpanded(true);
    	CheckBoxTreeItem<String> item1 = new CheckBoxTreeItem<>("Type��data");  
    	CheckBoxTreeItem<String> item2 = new CheckBoxTreeItem<>("Type��image");  
    	CheckBoxTreeItem<String> item3 = new CheckBoxTreeItem<>("Type��video");  
    	CheckBoxTreeItem<String> item4 = new CheckBoxTreeItem<>("Type��curve");  
    	item.getChildren().add(item1);
    	item.getChildren().add(item2);
    	item.getChildren().add(item3);
    	item.getChildren().add(item4);
    	CheckBoxTreeItem<String> item5 = new CheckBoxTreeItem<>("Topic:����ʵ���-ң������-�ռ������Һ��Ǩ��"); 
    	CheckBoxTreeItem<String> item6 = new CheckBoxTreeItem<>("Topic:����ʵ���-��������-ʵ��������A1"); 
    	CheckBoxTreeItem<String> item7 = new CheckBoxTreeItem<>("Topic:���-��������"); 
    	item1.getChildren().add(item5);
    	item1.getChildren().add(item6);
    	item1.getChildren().add(item7);
    	item1.setExpanded(true);
    	CheckBoxTreeItem<String> item8 = new CheckBoxTreeItem<>("Topic:�ߵ�ֲ��-Ӧ������JPEG"); 
    	item2.getChildren().add(item8);
    	item2.setExpanded(true);
    	CheckBoxTreeItem<String> item9 = new CheckBoxTreeItem<>("Topic:����ʵ���-��������-ʵ��������A1"); 
    	item4.getChildren().add(item9);
    	item4.setExpanded(true);
    	CheckBoxTreeItem<String> item10 = new CheckBoxTreeItem<>("Data_Name��������̬�Ƕȹ�ֵ");  
    	CheckBoxTreeItem<String> item11 = new CheckBoxTreeItem<>("Data_Name��ƫ����̬�Ƕȹ�ֵ");  
    	CheckBoxTreeItem<String> item12 = new CheckBoxTreeItem<>("Data_Name��������̬�Ƕȹ�ֵ");  
    	CheckBoxTreeItem<String> item13 = new CheckBoxTreeItem<>("Data_Name���������ٶ�Ԥ��");  
    	item7.getChildren().add(item10);
    	item7.getChildren().add(item11);
    	item7.getChildren().add(item12);
    	item7.getChildren().add(item13);
    	item7.setExpanded(true);
    	CheckBoxTreeItem<String> item14 = new CheckBoxTreeItem<>("Data_Name��A1�¶�1");  
    	CheckBoxTreeItem<String> item15 = new CheckBoxTreeItem<>("Data_Name��A1�¶�2"); 
    	item6.getChildren().add(item14);
    	item6.getChildren().add(item15);
    	item6.setExpanded(true);
    	CheckBoxTreeItem<String> item16 = new CheckBoxTreeItem<>("Data_Name��Һ���¶�1");  
    	CheckBoxTreeItem<String> item17 = new CheckBoxTreeItem<>("Data_Name��Һ���¶�2"); 
    	item5.getChildren().add(item16);
    	item5.getChildren().add(item17);
    	item5.setExpanded(true);
    	CheckBoxTreeItem<String> item18 = new CheckBoxTreeItem<>("Data_Name���¶�1"); 
    	CheckBoxTreeItem<String> item19 = new CheckBoxTreeItem<>("Data_Name���¶�2"); 
    	CheckBoxTreeItem<String> item20 = new CheckBoxTreeItem<>("Data_Name���ߵ�ֲ��ͼ��"); 
    	item8.getChildren().add(item18);
    	item8.getChildren().add(item19);
    	item8.getChildren().add(item20);
    	item8.setExpanded(true);
    	CheckBoxTreeItem<String> item21 = new CheckBoxTreeItem<>("Data_Name��#0000ff");  
    	CheckBoxTreeItem<String> item22 = new CheckBoxTreeItem<>("Data_Name��#ff8040"); 
    	item9.getChildren().add(item21);
    	item9.getChildren().add(item22);
    	item9.setExpanded(true);
    	
    } 

}
