package com.poac.quickview.controller;

import com.poac.quickview.MainApp;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class TableContainerController implements IController {
	@FXML
	private TableView tableView;
	@FXML
	private Label label_head;
	private MainApp mainApp; 	
    private ContextMenu addMenu = new ContextMenu();
	public TableContainerController() {
        MenuItem addMenuItem = new MenuItem("Ìí¼Ó²ÎÊý");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.showSubscribe();
            }
        }); 
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        tableView.setContextMenu(addMenu);
    }
    public void setHeadText(String txt) {
    	label_head.setText(txt);
    	
    }
}
