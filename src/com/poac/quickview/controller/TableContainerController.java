package com.poac.quickview.controller;

import java.util.ArrayList;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Parameter;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
public class TableContainerController implements IController {
	@FXML
	private AnchorPane anchor_table;
	@FXML
	private TableView tableView;
	@FXML
	private Label label_head;
	private MainApp mainApp; 	
	private BorderPane rootLayout;
	private String pageName=null;
	private double xOffset = 0;
	private double yOffset = 0;
    private  int RESIZE_MARGIN = 5;
    private int dragging=0;     //0代表不拉 1代表横拉  2代表竖拉 3代表斜拉
    private double x;
    private double y;
    private ContextMenu addMenu1 = new ContextMenu();
	public TableContainerController() {
        MenuItem addMenuItem1 = new MenuItem("添加参数");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	ArrayList<Parameter> arParm=new ArrayList();
            	if(mainApp.showSubscribe(arParm)) {
            		
            	}
            }
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整位置");    //右击TableView显示调整位置菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangePosion(container))
            		anchor_table.relocate(container.getPositionX(), container.getPositionY());
            }
        }); 
        MenuItem addMenuItem3 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		setContainerSize(container.getWidth(), container.getHeight());
            	    mainApp.refresh(pageName);
            }
        }); 
	}
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        init();
    }
    public void init() {
        tableView.setContextMenu(addMenu1);
        anchor_table.setOnMousePressed(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_table.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_table.getWidth() - RESIZE_MARGIN))) {     //判断不改变大小范围
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
					 return;
				}
                if((event.getY() > (anchor_table.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_table.getWidth() - RESIZE_MARGIN))) {
                	dragging = 3;
                }else if(event.getX() > (anchor_table.getWidth() - RESIZE_MARGIN)) {
					dragging = 1;
				}else if(event.getY() > (anchor_table.getHeight() - RESIZE_MARGIN)) {
					dragging = 2;
				}
		        x = event.getX();
		        y = event.getY();
			}
		});
        anchor_table.setOnMouseDragged(new EventHandler<MouseEvent>() {       //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (dragging == 0) {
					anchor_table.setLayoutX(event.getScreenX() - xOffset);
					anchor_table.setLayoutY(event.getScreenY() - yOffset);
					return;
				} else if (dragging == 1) {
					double mousex = event.getX();
					double newWidth = anchor_table.getPrefWidth() + (mousex - x);
					anchor_table.setPrefWidth(newWidth);
					x = mousex;
				}else if (dragging == 2) {
					double mousey = event.getY();
					double newHeight = anchor_table.getPrefHeight() + (mousey - y);
					anchor_table.setPrefHeight(newHeight);
					y = mousey;
				} else if(dragging == 3) {
					double mousex = event.getX();
					double mousey = event.getY();
					double newWidth = anchor_table.getPrefWidth() + (mousex - x);
					double newHeight = anchor_table.getPrefHeight() + (mousey - y);
					anchor_table.setPrefWidth(newWidth);
					anchor_table.setPrefHeight(newHeight);
					x = mousex;
					y = mousey;
				}
			}});
        anchor_table.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if((event.getY() > (anchor_table.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_table.getWidth() - RESIZE_MARGIN))) {
                	anchor_table.setCursor(Cursor.NW_RESIZE);
                }else if((event.getY() > (anchor_table.getHeight() - RESIZE_MARGIN))) {
                	anchor_table.setCursor(Cursor.S_RESIZE);
                }else if((event.getX() > (anchor_table.getWidth() - RESIZE_MARGIN))) {
                	anchor_table.setCursor(Cursor.H_RESIZE);
                }
                else {
                	anchor_table.setCursor(Cursor.DEFAULT);
                }
            }});
        anchor_table.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragging = 0;
                anchor_table.setCursor(Cursor.DEFAULT);
                mainApp.refresh(pageName);
            }});
    }
    public void setHeadText(String txt) {
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {
    	anchor_table.setPrefSize(width, height);
    }
}
