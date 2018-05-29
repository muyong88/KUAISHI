package com.poac.quickview.controller;



import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

import com.poac.quickview.gui.MainApp;;


public class MainFormController {
	
	@FXML
	private TreeView treeView_project;
	
	@FXML
	private Accordion accordion_1;
	
	@FXML
	private TitledPane titledPane;
	
	@FXML
	private MainApp mainApp; 
	
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
	@FXML
	private void onButtonClose(ActionEvent event){//��ť���رճ���id
		Platform.exit();
		System.exit(0);
	}
	
	@FXML
	private void onButtonSubscribe() {
		mainApp.showSubscribe();
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
         treeView_project.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
             @Override
             public TreeCell<String> call(TreeView<String> p) {
                 return new TreeViewCellImpl();
             }
         });

	}


}

final class TreeViewCellImpl extends TreeCell<String> {
	 
    private ContextMenu addMenu1 = new ContextMenu();
    private ContextMenu addMenu2 = new ContextMenu();

    public TreeViewCellImpl() {
        MenuItem addMenuItem1 = new MenuItem("����ҳ��");
        MenuItem addMenuItem2 = new MenuItem("��������");
        MenuItem addMenuItem3 = new MenuItem("���Ӳ���");
        addMenu1.getItems().add(addMenuItem1);
        addMenu2.getItems().add(addMenuItem2);
        addMenu2.getItems().add(addMenuItem3);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
                TreeItem newEmployee = 
                    new TreeItem<String>("��ҳ��");
                        getTreeItem().getChildren().add(newEmployee);
            }
        });   
    }
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if(getString().equals("���")| getString().equals("�����")| getString().equals("Һ��")| getString().equals("�ռ������Һ��Ǩ����Ϊ�о�")) {
                    setContextMenu(addMenu1);
                    }else {
                    	setContextMenu(addMenu2);
                    }
                   
                }
            }
        }
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
 
    
}
