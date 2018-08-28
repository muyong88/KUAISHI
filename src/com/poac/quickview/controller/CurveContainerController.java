package com.poac.quickview.controller;

import java.util.HashMap;
import java.util.Iterator;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.CurveParameter;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.DragUtil;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
public class CurveContainerController implements IController {
	@FXML
	private AnchorPane anchor_curve;                      //曲线容器
	@FXML
	private LineChart<String,Double>   lineChart;         //曲线图
	@FXML
	private Label label_head;                             //容器标题
	@FXML
	private HBox hBox_Circles;                            //用于加载圈数PANE
	private MainApp mainApp; 	                          //程序访问接口类
	private String pageName=null;                         //页名
    private String containerName=null;                    //容器名
    private ContextMenu addMenu1 = new ContextMenu();     //右击菜单
    private ObservableList<Series<String,Double>> seriesOblst=FXCollections.observableArrayList();           //曲线列表，每个Series对应一条曲线
    public ObservableList<CurveParameter> curveParameters = FXCollections.observableArrayList();             //订阅参数列表，与dataParameters保持一致，只是反应数据的维度不一样
    public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList();               //订阅参数列表，与curveParameters保持一致，只是反应数据的维度不一样
    private HashMap<String,ChangeListener<String>> listenerMap=new HashMap<String,ChangeListener<String>>(); //记录codename对应listener
	public CurveContainerController() {
		//监听dataParameters列表数据变化
		dataParameters.addListener(new ListChangeListener<DataParameter>() {
			/**
			 * 1、当dataParameters增加数据时，初始化Series，增加到seriesOblst列表，并对参数的time属性监听
			 * 2、当dataParameters删除数据时，seriesOblst中删除对应Series，并且删除对time属性的监听
			 */	
			@Override
			public void onChanged(ListChangeListener.Change<? extends DataParameter> change) {
				while (change.next()) {
					if (change.wasAdded()) {
						for (DataParameter para : change.getAddedSubList()) {
							Series<String, Double> series = new Series<>();
							series.setName(para.getCodeName());
							seriesOblst.add(series);
							DataParameter dp = SubscribeParameters.getSubscribeParameters().subParameterMap
									.get(para.getCodeName());
							ChangeListener<String> listener = new ChangeListener<String>() {
								@Override
								public void changed(ObservableValue<? extends String> o, String ov, String v) {
									Platform.runLater(() -> series.getData()
											.add(new XYChart.Data<String,Double>(dp.getTime(), Double.parseDouble(dp.getValue()))));
								}
							};
							dp.timeProperty().addListener(listener);
							listenerMap.put(para.getCodeName(), listener);
						}
					} else if (change.wasRemoved()) {
						for (DataParameter para : change.getRemoved()) {
			   				Iterator<Series<String, Double>> it = seriesOblst.iterator();
							while (it.hasNext()) {
								Series<String, Double> series = it.next();
								if (series.getName().equals(para.getCodeName())) {
									Platform.runLater(() -> seriesOblst.remove(series));
									DataParameter dp = SubscribeParameters.getSubscribeParameters().subParameterMap
											.get(para.getCodeName());
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

	//设置Page名
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
	//设置程序访问接口类
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp; 
    } 
    //返回本类对象
    private IController getThis() {
    	return this;
    }
    //初始化curveParameters和dataParameters数据
    public void initData(TreeDataModel containerModel) {
    	for(IBaseNode para:containerModel.getChilds()) {
    		curveParameters.add((CurveParameter)para);
    		dataParameters.add(SubscribeParameters.getSubscribeParameters().subParameterMap.get(((CurveParameter)para).getCodeName()));
    	}  
    //	lineChart.lookup(".default-color0.chart-series-line").setStyle(("-fx-stroke-width: 2; -fx-stroke: #00FF00; -fx-stroke-dash-array: 8 8;"));
    
    }
	/**
	 * 1、右击TableView显示添加参数，调整大小，删除容器菜单，并定义相关单击事件
	 * 2、初始化hBox_Circles内容
	 * 3、设置lineChart数据
	 * 4、初始化anchor_curve右击菜单
	 * 5、实现anchor_curve拖拽实现移动和改变大小
	 */	
    public void init() {
        MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event) ->  {
            	if(mainApp.showSubscribe("curve",getThis())) {     //显示订阅窗口
            		
            	}
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction((event) ->  {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))   //显示改变窗体大小窗口
            		setContainerSize(container.getWidth(), container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);            
        });
        MenuItem addMenuItem3 = new MenuItem("删除容器");    //右击TableView显示删除容器菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction((event) ->  {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	mainApp.getTabPaneController().refresh(pageName);
        }); 
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());    //初始化hBox_Circles内容
        lineChart.setData(seriesOblst);                               //设置lineChart数据
        anchor_curve.setOnMouseClicked(new EventHandler<MouseEvent>() { //初始化anchor_curve右击菜单
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_curve, event.getScreenX(), event.getScreenY());
            }else {
            	addMenu1.hide();
            }  
          } 
        });
        DragUtil.addDragListener(anchor_curve, mainApp, pageName);   //实现anchor_curve拖拽实现移动和改变大小
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_curve.setPrefSize(width, height);
    }
}
