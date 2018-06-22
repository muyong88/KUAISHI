package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TableContainerController implements IController {
	@FXML
	private AnchorPane anchor_table;
	@FXML
	private TableView tableView;
	@FXML
	private Label label_head;
	private MainApp mainApp; 	
    private ContextMenu addMenu1 = new ContextMenu();
	public TableContainerController() {
        MenuItem addMenuItem1 = new MenuItem("��Ӳ���");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.showSubscribe();
            }
        }); 
        MenuItem addMenuItem2 = new MenuItem("����λ��");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangePosion(container))
            		anchor_table.relocate(container.getPositionX(), container.getPositionY());
            }
        }); 
        MenuItem addMenuItem3 = new MenuItem("�ı��С");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		anchor_table.resize(container.getWidth(), container.getHeight());
            }
        }); 
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tableView.setContextMenu(addMenu1);
    }
    public void setHeadText(String txt) {
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {
    	anchor_table.setPrefSize(width, height);
    }
    public void relocate(double x,double y) {
    	anchor_table.relocate(x, y);
    }
    public void resize(double width,double height) {
    	anchor_table.resize(width, height);
    }
    public void resizeRelocate(double x,double y,double width,double height) {
    	anchor_table.resizeRelocate(x, y, width, height);
    }
}