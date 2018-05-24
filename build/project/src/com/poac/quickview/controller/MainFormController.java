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
	private void onButtonClose(ActionEvent event){//按钮“关闭程序”id
		Platform.exit();
		System.exit(0);
	}

	@FXML
    private void initialize() {
        TreeItem<String> item = new TreeItem<>("天和");
         treeView_project.setRoot(item);
         item.setExpanded(true);
         TreeItem<String> i1 = new TreeItem<>("数值");
         TreeItem<String> i2 = new TreeItem<>("自定义页面");
         TreeItem<String> i3 = new TreeItem<>("流体柜");
         item.getChildren().addAll(i1,i2,i3);
         TreeItem<String> i4 = new TreeItem<>("空间三相多液滴迁移行为研究");
         TreeItem<String> i5 = new TreeItem<>("液桥");
         i3.setExpanded(true);
         i3.getChildren().addAll(i4,i5);
         TreeItem<String> i6 = new TreeItem<>("自定义页面1");
         TreeItem<String> i7 = new TreeItem<>("曲线");
         i5.getChildren().addAll(i6,i7);
         i5.setExpanded(true);
         accordion_1.setExpandedPane(titledPane);
	}


}
