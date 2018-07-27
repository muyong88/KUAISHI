package com.poac.quickview.controller;

import java.util.ArrayList;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.TreeDataModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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
    public void init() {
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
        anchor_table.setOnMousePressed(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_table.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_table.getWidth() - RESIZE_MARGIN))) {     //判断不改变大小范围
					xOffset = event.getX();
					yOffset = event.getY();		
					dragging=0;
					//System.out.println(anchor_table.getHeight()+" setOnMousePressed "+anchor_table.getLayoutY());
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
					x=anchor_table.getLayoutX()+event.getX() - xOffset;
					y=anchor_table.getLayoutY()+event.getY() - yOffset;
					anchor_table.setLayoutX(x);
					anchor_table.setLayoutY(y);
					mainApp.getTabPaneController().getTabTemplateController(pageName).setScrollVaule(y,y+anchor_table.getHeight());
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
        anchor_table.setOnMouseMoved(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
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
        anchor_table.setOnMouseReleased(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
            @Override
            public void handle(MouseEvent event) {
                dragging = 0;
                anchor_table.setCursor(Cursor.DEFAULT);
                mainApp.getTabPaneController().refresh(pageName);
            }});
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_table.setPrefSize(width, height);
    }
}
