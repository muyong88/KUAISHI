package com.poac.quickview.controller;

import java.util.ArrayList;
import java.util.Iterator;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.DragUtil;

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
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
public class TableContainerController implements IController {
	@FXML
	private AnchorPane anchor_table;
	@FXML
	private TableView<DataParameter> tableView;
	@FXML
	private Label label_head;
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
	private HBox hBox_Circles;
	public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList();
	private MainApp mainApp; 	
	private String pageName=null;
    private String containerName=null;
    private ContextMenu addMenu1 = new ContextMenu();
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
	public TableContainerController() {

	}
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void initData(TreeDataModel containerModel) {
    	for(IBaseNode para:containerModel.getChilds()) {
    		dataParameters.add((DataParameter)para);
    	}
    }
    private IController getThis() {
    	return this;
    }  
    public String getPageContainerName() {
    	return (pageName+":"+containerName);
    }
    public void selectTableViewRow(String paraCode) {
    	tableView.getSelectionModel().select(SubscribeParameters.getSubscribeParameters().subParameterMap.get(paraCode));
    }
    public void init() {
    	dataParameters.addListener(new ListChangeListener<DataParameter>() {
	        @Override
	        public void onChanged(ListChangeListener.Change<? extends DataParameter> change) {
	            while (change.next()) {
	            	if (change.wasAdded()) {
	                    for (DataParameter para : change.getAddedSubList()) {
	                    	if(!para.subscrbeContainer.contains(getThis())){
	                    		para.subscrbeContainer.add(getThis());
	                    	}
	                    }
	                }
	            }
	        }
	    });
        MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	if(mainApp.showSubscribe("data",getThis())) {

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
    	tc_paraName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	tc_paraCode.setCellValueFactory(cellData -> cellData.getValue().codeNameProperty());
    	tc_paraUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    	tc_paraRange.setCellValueFactory(cellData -> cellData.getValue().rangeProperty());
    	tc_paraResult.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    	tableView.setItems(dataParameters);  	
        //tableView.setContextMenu(addMenu1);
    	anchor_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
              if (MouseButton.SECONDARY.equals(event.getButton())) {
              	addMenu1.show(anchor_table, event.getScreenX(), event.getScreenY());
              }else {
            	  addMenu1.hide();
              }
            }         
          });	
    	tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
              if (MouseButton.SECONDARY.equals(event.getButton())) {
              	addMenu1.show(anchor_table, event.getScreenX(), event.getScreenY());
              }else {
            	  addMenu1.hide();
              }
            }         
          });
        tableView.setRowFactory(tv -> {        	 //拖拽表格行
            TableRow<DataParameter> row = new TableRow<>();
            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });
            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });
            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    DataParameter draggedParameter = tableView.getItems().remove(draggedIndex);

                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableView.getItems().add(dropIndex, draggedParameter);

                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });
            return row ;
        });
        DragUtil.addDragListener(anchor_table, mainApp, pageName);
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_table.setPrefSize(width, height);
    }
}
