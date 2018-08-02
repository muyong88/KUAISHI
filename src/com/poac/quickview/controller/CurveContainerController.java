package com.poac.quickview.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.CurveParameter;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.TreeDataModel;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
public class CurveContainerController implements IController {
	@FXML
	private AnchorPane anchor_curve;
	@FXML
	private LineChart<String,Double>   lineChart;
	@FXML
	private Label label_head;
	@FXML
	private HBox hBox_Circles;
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
    private ObservableList<Series<String,Double>> seriesOblst=FXCollections.observableArrayList();    
    public ObservableList<CurveParameter> curveParameters = FXCollections.observableArrayList();  //订阅Curve参数列表
    public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList();  //订阅Curve参数列表
    private HashMap<String,ChangeListener> listenerMap=new HashMap<String,ChangeListener>(); //记录codename对应listener
    
	public CurveContainerController() {
		//监听curve参数订阅列表
		dataParameters.addListener(new ListChangeListener<DataParameter>() {
	        @Override
	        public void onChanged(ListChangeListener.Change<? extends DataParameter> change) {
	            while (change.next()) {
	            	if (change.wasAdded()) {
	                    for (DataParameter para : change.getAddedSubList()) {
	                		Series<String, Double> series = new Series<>();
	                		series.setName(para.getCodeName());
	                		seriesOblst.add(series);
	                		DataParameter dp=SubscribeParameters.getSubscribeParameters().subParameterMap.get(para.getCodeName());
	                		ChangeListener listener=new ChangeListener<String>() {
	                			 @Override
	                			    public void changed(ObservableValue<? extends String> o,
	                			    		String ov, String v) {
	                				 Platform.runLater(() ->series.getData().add(new XYChart.Data(dp.getTime(), Double.parseDouble(dp.getValue()))));  
	                			 }
	                		};
	                		dp.timeProperty().addListener(listener);
	                		listenerMap.put(para.getCodeName(), listener);
	                    }
	                } else if (change.wasRemoved()) {
	                    for (DataParameter para : change.getRemoved()) {
	                    	Iterator<Series<String, Double>> it=seriesOblst.iterator();
	                    	while(it.hasNext()) {
	                    		Series<String,Double> series=it.next();
	                    		if(series.getName().equals(para.getCodeName())) {
	                    			Platform.runLater(() ->seriesOblst.remove(series));
	                    			DataParameter dp=SubscribeParameters.getSubscribeParameters().subParameterMap.get(para.getCodeName());
	                    			dp.timeProperty().removeListener(listenerMap.get(para.getCodeName()));
	                    			listenerMap.remove(para.getCodeName());
	                    		}
	                    	}
	                    }

	                } 
	            }
	        }

	    });
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
    		curveParameters.add((CurveParameter)para);
    		dataParameters.add(SubscribeParameters.getSubscribeParameters().subParameterMap.get(((CurveParameter)para).getCodeName()));
    	}
    }
    public void init() {
        MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	if(mainApp.showSubscribe("curve",getThis())) {
            		
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
        lineChart.setData(seriesOblst);
        anchor_curve.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_curve, event.getScreenX(), event.getScreenY());
            }else {
            	addMenu1.hide();
            }  
          } 
        });
    	anchor_curve.setOnMousePressed(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_curve.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_curve.getWidth() - RESIZE_MARGIN))) {     //判断不改变大小范围
					xOffset = event.getX();
					yOffset = event.getY();	
					//System.out.println(anchor_curve.getLayoutX()+" setOnMousePressed "+anchor_curve.getLayoutY());
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
    	anchor_curve.setOnMouseDragged(new EventHandler<MouseEvent>() {       //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (dragging == 0) {
					x=anchor_curve.getLayoutX()+event.getX() - xOffset;
					y=anchor_curve.getLayoutY()+event.getY() - yOffset;
					anchor_curve.setLayoutX(x);
					anchor_curve.setLayoutY(y);
					mainApp.getTabPaneController().getTabTemplateController(pageName).setScrollVaule(y,anchor_curve.getHeight());
					
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
        anchor_curve.setOnMouseMoved(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
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
        anchor_curve.setOnMouseReleased(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
            @Override
            public void handle(MouseEvent event) {
                dragging = 0;
                anchor_curve.setCursor(Cursor.DEFAULT);
                mainApp.getTabPaneController().refresh(pageName);
            }});
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_curve.setPrefSize(width, height);
    }
}
