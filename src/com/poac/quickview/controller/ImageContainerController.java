package com.poac.quickview.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.TreeDataModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
public class ImageContainerController implements IController {
	@FXML
	private AnchorPane anchor_image;
	@FXML
	private ImageView imageView; 
	@FXML
	private TableView<DataParameter> tableView;
	@FXML
	private TableColumn<DataParameter, String> tc_paraName;
	@FXML
	private TableColumn<DataParameter, String> tc_paraCode;
	@FXML
	private TableColumn<DataParameter, String> tc_paraUnit;
	@FXML
	private TableColumn<DataParameter, String> tc_paraRange;
	@FXML
	private TableColumn<DataParameter, String> tc_paraResult;
	@FXML
	private Label label_head;
	@FXML
	private AnchorPane anchor_img;
	@FXML
	private HBox hBox_Circles;
	public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList();
	private MainApp mainApp; 	
	private String pageName=null;
    private String containerName=null;
	private double xOffset = 0;
	private double yOffset = 0;
    private  int RESIZE_MARGIN = 5;
    private int dragging=0;     //0代表不拉 1代表横拉  2代表竖拉 3代表斜拉
    private double x;
    private double y;
    private ContextMenu addMenu1 = new ContextMenu();
	public ImageContainerController() {
		
	}
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }  
    private IController getThis() {
    	return this;
    }
    public void initData(TreeDataModel containerModel) {
    	for(IBaseNode para:containerModel.getChilds()) {
    		if(!((DataParameter)para).getCodeName().equals("Image"))
    			dataParameters.add((DataParameter)para);
    	}
    }
    public void init() {
    	tc_paraName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	tc_paraCode.setCellValueFactory(cellData -> cellData.getValue().codeNameProperty());
    	tc_paraUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    	tc_paraRange.setCellValueFactory(cellData -> cellData.getValue().rangeProperty());
    	tc_paraResult.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    	tableView.setItems(dataParameters);  	
    	MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	if(mainApp.showSubscribe("image",getThis())) {
            		
            	} 
            }
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		setContainerSize(container.getWidth(), container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
            } 
        }); 
        MenuItem addMenuItem3 = new MenuItem("删除容器");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	mainApp.getTabPaneController().refresh(pageName);
            }  
        }); 
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	imageView.setPreserveRatio(false);
    	imageView.fitWidthProperty().bind(anchor_img.widthProperty());
    	imageView.fitHeightProperty().bind(anchor_img.heightProperty());
    	SubscribeParameters.getSubscribeParameters().page_Container_ImageProperty.put(pageName+containerName, new SimpleObjectProperty<>());
    	imageView.imageProperty().bind(SubscribeParameters.getSubscribeParameters()
    			.page_Container_ImageProperty.get(pageName+containerName));
    	anchor_image.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_image, event.getScreenX(), event.getScreenY());
            }else {
            	addMenu1.hide(); 
            }  
          }         
        });
        anchor_image.setOnMousePressed(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_image.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_image.getWidth() - RESIZE_MARGIN))) {     //判断不改变大小范围
					xOffset = event.getX();
					yOffset = event.getY();						
					 return;
				}
                if((event.getY() > (anchor_image.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_image.getWidth() - RESIZE_MARGIN))) {
                	dragging = 3;
                }else if(event.getX() > (anchor_image.getWidth() - RESIZE_MARGIN)) {
					dragging = 1;
				}else if(event.getY() > (anchor_image.getHeight() - RESIZE_MARGIN)) {
					dragging = 2;
				}
		        x = event.getX();
		        y = event.getY();
			}
		});
        anchor_image.setOnMouseDragged(new EventHandler<MouseEvent>() {       //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (dragging == 0) {
					x=anchor_image.getLayoutX()+event.getX() - xOffset;
					y=anchor_image.getLayoutY()+event.getY() - yOffset;
					anchor_image.setLayoutX(x);
					anchor_image.setLayoutY(y);
					mainApp.getTabPaneController().getTabTemplateController(pageName).setScrollVaule(y,y+anchor_image.getHeight());					
					return;
				} else if (dragging == 1) {
					double mousex = event.getX();
					double newWidth = anchor_image.getPrefWidth() + (mousex - x);
					anchor_image.setPrefWidth(newWidth);
					x = mousex;
				}else if (dragging == 2) {
					double mousey = event.getY();
					double newHeight = anchor_image.getPrefHeight() + (mousey - y);
					anchor_image.setPrefHeight(newHeight);
					y = mousey;
				} else if(dragging == 3) {
					double mousex = event.getX();
					double mousey = event.getY();
					double newWidth = anchor_image.getPrefWidth() + (mousex - x);
					double newHeight = anchor_image.getPrefHeight() + (mousey - y);
					anchor_image.setPrefWidth(newWidth);
					anchor_image.setPrefHeight(newHeight);
					x = mousex;
					y = mousey;
				}
			}});
        anchor_image.setOnMouseMoved(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
            @Override
            public void handle(MouseEvent event) {
                if((event.getY() > (anchor_image.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_image.getWidth() - RESIZE_MARGIN))) {
                	anchor_image.setCursor(Cursor.NW_RESIZE);
                }else if((event.getY() > (anchor_image.getHeight() - RESIZE_MARGIN))) {
                	anchor_image.setCursor(Cursor.S_RESIZE);
                }else if((event.getX() > (anchor_image.getWidth() - RESIZE_MARGIN))) {
                	anchor_image.setCursor(Cursor.H_RESIZE);
                }
                else {
                	anchor_image.setCursor(Cursor.DEFAULT);
                }
            }});
        anchor_image.setOnMouseReleased(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
            @Override
            public void handle(MouseEvent event) {
                dragging = 0;
                anchor_image.setCursor(Cursor.DEFAULT);
                mainApp.getTabPaneController().refresh(pageName);
            }});
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_image.setPrefSize(width, height);
    }
}
