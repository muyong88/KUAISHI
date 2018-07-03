package com.poac.quickview.controller;

import java.util.ArrayList;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Parameter;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
public class CurveContainerController implements IController {
	@FXML
	private AnchorPane anchor_curve;
	@FXML
	private LineChart lineChart;
	@FXML
	private Label label_head;
	private MainApp mainApp; 	
	private String pageName=null;
    private String containerName=null;
	private double xOffset = 0;
	private double yOffset = 0;
    private  int RESIZE_MARGIN = 5;
    private int dragging=0;     //0������ 1�������  2�������� 3����б��
    private double x;
    private double y;
    private ContextMenu addMenu1 = new ContextMenu();
	public CurveContainerController() {
        MenuItem addMenuItem1 = new MenuItem("��Ӳ���");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	ArrayList<Parameter> arParm=new ArrayList();
            	if(mainApp.showSubscribe(arParm)) {
            		
            	}
            }
        }); 
        MenuItem addMenuItem2 = new MenuItem("������С");    //�һ�TableView��ʾ������С�˵�
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		setContainerSize(container.getWidth(), container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
            }
        });
        MenuItem addMenuItem3 = new MenuItem("ɾ������");    //�һ�TableView��ʾ������С�˵�
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	mainApp.getTabPaneController().refresh(pageName);
            }
        }); 
	}
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
    public void init() {
    	lineChart.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_curve, event.getScreenX(), event.getScreenY());
            }  
          }
        });
    	anchor_curve.setOnMousePressed(new EventHandler<MouseEvent>() {      //��������anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_curve.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_curve.getWidth() - RESIZE_MARGIN))) {     //�жϲ��ı��С��Χ
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
					 return;
				}
                if((event.getY() > (anchor_curve.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_curve.getWidth() - RESIZE_MARGIN))) {
                	dragging = 3;
                }else if(event.getX() > (anchor_curve.getWidth() - RESIZE_MARGIN)) {
					dragging = 1;
				}else if(event.getY() > (anchor_curve.getHeight() - RESIZE_MARGIN)) {
					dragging = 2;
				}
		        x = event.getX();
		        y = event.getY();
			}
		});
    	anchor_curve.setOnMouseDragged(new EventHandler<MouseEvent>() {       //��������anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (dragging == 0) {
					anchor_curve.setLayoutX(event.getScreenX() - xOffset);
					anchor_curve.setLayoutY(event.getScreenY() - yOffset);
					return;
				} else if (dragging == 1) {
					double mousex = event.getX();
					double newWidth = anchor_curve.getPrefWidth() + (mousex - x);
					anchor_curve.setPrefWidth(newWidth);
					x = mousex;
				}else if (dragging == 2) {
					double mousey = event.getY();
					double newHeight = anchor_curve.getPrefHeight() + (mousey - y);
					anchor_curve.setPrefHeight(newHeight);
					y = mousey;
				} else if(dragging == 3) {
					double mousex = event.getX();
					double mousey = event.getY();
					double newWidth = anchor_curve.getPrefWidth() + (mousex - x);
					double newHeight = anchor_curve.getPrefHeight() + (mousey - y);
					anchor_curve.setPrefWidth(newWidth);
					anchor_curve.setPrefHeight(newHeight);
					x = mousex;
					y = mousey;
				}
			}});
        anchor_curve.setOnMouseMoved(new EventHandler<MouseEvent>() {      //��������anchorpane
            @Override
            public void handle(MouseEvent event) {
                if((event.getY() > (anchor_curve.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_curve.getWidth() - RESIZE_MARGIN))) {
                	anchor_curve.setCursor(Cursor.NW_RESIZE);
                }else if((event.getY() > (anchor_curve.getHeight() - RESIZE_MARGIN))) {
                	anchor_curve.setCursor(Cursor.S_RESIZE);
                }else if((event.getX() > (anchor_curve.getWidth() - RESIZE_MARGIN))) {
                	anchor_curve.setCursor(Cursor.H_RESIZE);
                }
                else {
                	anchor_curve.setCursor(Cursor.DEFAULT);
                }
            }});
        anchor_curve.setOnMouseReleased(new EventHandler<MouseEvent>() {      //��������anchorpane
            @Override
            public void handle(MouseEvent event) {
                dragging = 0;
                anchor_curve.setCursor(Cursor.DEFAULT);
                mainApp.getTabPaneController().refresh(pageName);
            }});
    }
    public void setHeadText(String txt) {             //����������Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //��������Size
    	anchor_curve.setPrefSize(width, height);
    }
}
