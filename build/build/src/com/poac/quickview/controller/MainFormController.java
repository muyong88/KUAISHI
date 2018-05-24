package com.poac.quickview.controller;



import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;

import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class MainFormController {
	
	@FXML
	private TreeView treeView_project;
	
	@FXML
	private Accordion accordion_1;
	
	@FXML
	private TitledPane titledPane;
	
	@FXML
	private void onButtonClose(ActionEvent event){//��ť���رճ���id
		Platform.exit();
		System.exit(0);
	}

	@FXML
    private void initialize() {
        TreeItem<String> item = new TreeItem<>("���");
         treeView_project.setRoot(item);
         item.setExpanded(true);
         TreeItem<String> i1 = new TreeItem<>("��ֵ");
         TreeItem<String> i2 = new TreeItem<>("�Զ���ҳ��");
         TreeItem<String> i3 = new TreeItem<>("�����");
         item.getChildren().addAll(i1,i2,i3);
         TreeItem<String> i4 = new TreeItem<>("�ռ������Һ��Ǩ����Ϊ�о�");
         TreeItem<String> i5 = new TreeItem<>("Һ��");
         i3.setExpanded(true);
         i3.getChildren().addAll(i4,i5);
         TreeItem<String> i6 = new TreeItem<>("�Զ���ҳ��1");
         TreeItem<String> i7 = new TreeItem<>("����");
         i5.getChildren().addAll(i6,i7);
         i5.setExpanded(true);
         accordion_1.setExpandedPane(titledPane);
	}


}
