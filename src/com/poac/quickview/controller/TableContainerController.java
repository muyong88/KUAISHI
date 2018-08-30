package com.poac.quickview.controller;


import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.DragUtil;
import com.poac.quickview.util.LogFactory;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.layout.HBox;
public class TableContainerController implements IController {
	@FXML
	private AnchorPane anchor_table;                                //表格容器面板
	@FXML
	private TableView<DataParameter> tableView;                     //表格控件
	@FXML
	private Label label_head;                                       //容器标题
	@FXML
	private TableColumn<DataParameter, String> tc_paraName;         //参数名列
	@FXML
	private TableColumn<DataParameter, String> tc_paraCode;         //参数代码列
	@FXML
	private TableColumn<DataParameter, String> tc_paraUnit;         //参数名称列
	@FXML
	private TableColumn<DataParameter, String> tc_paraRange;        //参数范围列
	@FXML
	private TableColumn<DataParameter, String> tc_paraResult;       //参数结果列
	@FXML
	private HBox hBox_Circles;                                      //圈数HBOX
	public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList(); //参数列表
	private MainApp mainApp; 	                                    //程序访问接口
	private String pageName=null;                                   //page名
    private String containerName=null;                              //容器名
    private ContextMenu addMenu1 = new ContextMenu();               //右键菜单
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	public void setPageName(String pageName) {                       //设置page名
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {                        //设置应用程序接口
        this.mainApp = mainApp;
    }
    public void initData(TreeDataModel containerModel) {             //初始化数据
    	for(IBaseNode para:containerModel.getChilds()) {
    		dataParameters.add((DataParameter)para);
    	}
    }
    private IController getThis() {                                   //获取本实例对象
    	return this;
    }  
    public String getPageContainerName() {                            //获取该容器唯一ID
    	return (pageName+":"+containerName);
    }
    public void selectTableViewRow(String paraCode) {                 //选择参数代码所在行
    	tableView.getSelectionModel().select(SubscribeParameters.getSubscribeParameters().subParameterMap.get(paraCode));
    }
	/**
	 * 1、监听dataParameters，发现增加数据时，在参数的subscrbeContainer属性中增加这个表格容器对象（即表示这个容器订阅了这个参数）
	 * 2、tableView绑定数据dataParameters
	 * 3、实现右键菜单：数据订阅、调整大小，删除容器
	 * 4、表格列绑定数据属性
	 * 5、工厂方法客户化tableView，实现拖拉改变行顺序
	 * 6、加载圈数模块
	 * 7、实现拖拽功能
	 * 8、设置anchor_table的UserData为容器名
	 */	
    public void init() {
    	//1、监听dataParameters，发现增加数据时，在参数的subscrbeContainer属性中增加这个表格容器对象（即表示这个容器订阅了这个参数）
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
    	//2、tableView绑定数据dataParameters
    	tableView.setItems(dataParameters);  
    	//3、实现右键菜单：数据订阅、调整大小，删除容器
        MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event) -> {
            	if(mainApp.showSubscribe("data",getThis())) {

            	} 
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction((event) -> {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container)) {
            		setContainerSize(container.getWidth(), container.getHeight());
            		LogFactory.getGlobalLog().info("Input Change Container Size! PageName:"+pageName+" ContainerName:"+
            				containerName+" New Width:"+container.getWidth()+" New Height:"+container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
            	}
        });
        MenuItem addMenuItem3 = new MenuItem("删除容器");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction((event) -> {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	LogFactory.getGlobalLog().info("Delete Container! ContainerName:"+containerName);
            	mainApp.getTabPaneController().refresh(pageName);
        }); 
    	anchor_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
        //4、表格列绑定数据属性
    	tc_paraName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	tc_paraCode.setCellValueFactory(cellData -> cellData.getValue().codeNameProperty());
    	tc_paraUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    	tc_paraRange.setCellValueFactory(cellData -> cellData.getValue().rangeProperty());
    	tc_paraResult.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        //5、工厂方法客户化tableView，实现拖拉改变行顺序
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
    	//6、加载圈数模块
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	//7、实现拖拽功能
        DragUtil.addDragListener(anchor_table, mainApp, pageName);
        //8、设置anchor_table的UserData为容器名
        anchor_table.setUserData(containerName);
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_table.setPrefSize(width, height);
    }
}
