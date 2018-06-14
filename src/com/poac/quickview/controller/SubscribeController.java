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
    	TreeItem<String> item = new TreeItem<>("Group����ѧ��");    	
    	treeview_s.setRoot(item);
    	item.setExpanded(true);
    	TreeItem<String> item1 = new TreeItem<>("Type��data");      	
    	TreeItem<String> item2 = new TreeItem<>("Type��image");  
    	TreeItem<String> item3 = new TreeItem<>("Type��video");  
    	TreeItem<String> item4 = new TreeItem<>("Type��curve");  
    	item.getChildren().add(item1);
    	item.getChildren().add(item2);
    	item.getChildren().add(item3);
    	item.getChildren().add(item4);
    	TreeItem<String> item5 = new TreeItem<>("Topic:����ʵ���-ң������-�ռ������Һ��Ǩ��"); 
    	TreeItem<String> item6 = new TreeItem<>("Topic:����ʵ���-��������-ʵ��������A1"); 
    	TreeItem<String> item7 = new TreeItem<>("Topic:���-��������"); 
    	item1.getChildren().add(item5);
    	item1.getChildren().add(item6);
    	item1.getChildren().add(item7);
    	item1.setExpanded(true);
    	TreeItem<String> item8 = new TreeItem<>("Topic:�ߵ�ֲ��-Ӧ������JPEG"); 
    	item2.getChildren().add(item8);
    	item2.setExpanded(true);
    	TreeItem<String> item9 = new TreeItem<>("Topic:����ʵ���-��������-ʵ��������A1"); 
    	item4.getChildren().add(item9);
    	item4.setExpanded(true);
    	treeview_s.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem<String> selectedItem = (TreeItem<String>) newValue;				
				if(selectedItem.getValue().contains("���-��������")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("������̬�Ƕȹ�ֵ","ƫ����̬�Ƕȹ�ֵ","������̬�Ƕȹ�ֵ","�������ٶ�Ԥ��");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("����ʵ���-��������-ʵ��������A1")&&selectedItem.getParent().getValue().contains("����ʵ���-��������-ʵ��������A1")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("A1�¶�1","A1�¶�2");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("����ʵ���-ң������-�ռ������Һ��Ǩ��")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("Һ���¶�1","Һ���¶�2");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("�ߵ�ֲ��-Ӧ������JPEG")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("�¶�1","�¶�2","�ߵ�ֲ��ͼ��");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("�ߵ�ֲ��-Ӧ������JPEG")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList("�¶�1","�¶�2","�ߵ�ֲ��ͼ��");  
					checkListView_1.setItems(strList);
				}
				else if(selectedItem.getValue().contains("����ʵ���-��������-ʵ��������A1")) {
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
