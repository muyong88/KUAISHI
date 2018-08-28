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
	private AnchorPane anchor_curve;                      //��������
	@FXML
	private LineChart<String,Double>   lineChart;         //����ͼ
	@FXML
	private Label label_head;                             //��������
	@FXML
	private HBox hBox_Circles;                            //���ڼ���Ȧ��PANE
	private MainApp mainApp; 	                          //������ʽӿ���
	private String pageName=null;                         //ҳ��
    private String containerName=null;                    //������
    private ContextMenu addMenu1 = new ContextMenu();     //�һ��˵�
    private ObservableList<Series<String,Double>> seriesOblst=FXCollections.observableArrayList();           //�����б�ÿ��Series��Ӧһ������
    public ObservableList<CurveParameter> curveParameters = FXCollections.observableArrayList();             //���Ĳ����б���dataParameters����һ�£�ֻ�Ƿ�Ӧ���ݵ�ά�Ȳ�һ��
    public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList();               //���Ĳ����б���curveParameters����һ�£�ֻ�Ƿ�Ӧ���ݵ�ά�Ȳ�һ��
    private HashMap<String,ChangeListener<String>> listenerMap=new HashMap<String,ChangeListener<String>>(); //��¼codename��Ӧlistener
	public CurveContainerController() {
		//����dataParameters�б����ݱ仯
		dataParameters.addListener(new ListChangeListener<DataParameter>() {
			/**
			 * 1����dataParameters��������ʱ����ʼ��Series�����ӵ�seriesOblst�б����Բ�����time���Լ���
			 * 2����dataParametersɾ������ʱ��seriesOblst��ɾ����ӦSeries������ɾ����time���Եļ���
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

	//����Page��
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
	//���ó�����ʽӿ���
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp; 
    } 
    //���ر������
    private IController getThis() {
    	return this;
    }
    //��ʼ��curveParameters��dataParameters����
    public void initData(TreeDataModel containerModel) {
    	for(IBaseNode para:containerModel.getChilds()) {
    		curveParameters.add((CurveParameter)para);
    		dataParameters.add(SubscribeParameters.getSubscribeParameters().subParameterMap.get(((CurveParameter)para).getCodeName()));
    	}  
    //	lineChart.lookup(".default-color0.chart-series-line").setStyle(("-fx-stroke-width: 2; -fx-stroke: #00FF00; -fx-stroke-dash-array: 8 8;"));
    
    }
	/**
	 * 1���һ�TableView��ʾ��Ӳ�����������С��ɾ�������˵�����������ص����¼�
	 * 2����ʼ��hBox_Circles����
	 * 3������lineChart����
	 * 4����ʼ��anchor_curve�һ��˵�
	 * 5��ʵ��anchor_curve��קʵ���ƶ��͸ı��С
	 */	
    public void init() {
        MenuItem addMenuItem1 = new MenuItem("���ݶ���");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event) ->  {
            	if(mainApp.showSubscribe("curve",getThis())) {     //��ʾ���Ĵ���
            		
            	}
        }); 
        MenuItem addMenuItem2 = new MenuItem("������С");    //�һ�TableView��ʾ������С�˵�
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction((event) ->  {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))   //��ʾ�ı䴰���С����
            		setContainerSize(container.getWidth(), container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);            
        });
        MenuItem addMenuItem3 = new MenuItem("ɾ������");    //�һ�TableView��ʾɾ�������˵�
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction((event) ->  {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	mainApp.getTabPaneController().refresh(pageName);
        }); 
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());    //��ʼ��hBox_Circles����
        lineChart.setData(seriesOblst);                               //����lineChart����
        anchor_curve.setOnMouseClicked(new EventHandler<MouseEvent>() { //��ʼ��anchor_curve�һ��˵�
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_curve, event.getScreenX(), event.getScreenY());
            }else {
            	addMenu1.hide();
            }  
          } 
        });
        DragUtil.addDragListener(anchor_curve, mainApp, pageName);   //ʵ��anchor_curve��קʵ���ƶ��͸ı��С
    }
    public void setHeadText(String txt) {             //����������Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //��������Size
    	anchor_curve.setPrefSize(width, height);
    }
}
