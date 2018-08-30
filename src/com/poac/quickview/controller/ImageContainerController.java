package com.poac.quickview.controller;



import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.DragUtil;
import com.poac.quickview.util.LogFactory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
public class ImageContainerController implements IController {
	@FXML
	private AnchorPane anchor_image;                             //图像面板 ，放置所有这个容器控件
	@FXML
	private ImageView imageView;                                 //图像控件
	@FXML
	private TableView<DataParameter> tableView;                  //表格控件
	@FXML
	private TableColumn<DataParameter, String> tc_paraName;      //参数名称列
	@FXML
	private TableColumn<DataParameter, String> tc_paraCode;      //参数代码列
	@FXML
	private TableColumn<DataParameter, String> tc_paraUnit;      //参数单位
	@FXML
	private TableColumn<DataParameter, String> tc_paraRange;     //参数范围
	@FXML
	private TableColumn<DataParameter, String> tc_paraResult;    //参数结果
	@FXML
	private Label label_head;                                    //容器标题
	@FXML
	private AnchorPane anchor_img;                               //用于放置imageView
	@FXML
	private HBox hBox_Circles;                                   //圈数HBox
	public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList(); //订阅参数列表
	private MainApp mainApp; 	                                 //程序访问接口
	private String pageName=null;                                //page名
    private String containerName=null;                           //容器名
    private ContextMenu addMenu1 = new ContextMenu();            //右键菜单
	public ImageContainerController() {
		
	}
	public void setPageName(String pageName) {                    //设置page名
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {                     //设置程序访问接口
        this.mainApp = mainApp;
    }  
    private IController getThis() {                               //获取本实例对象
    	return this;
    }
	/**
	 * 初始化订阅数据数据
	 * @param containerModel  
	 */	
    public void initData(TreeDataModel containerModel) {          
    	for(IBaseNode para:containerModel.getChilds()) {
    		if(!((DataParameter)para).getCodeName().equals("Image"))
    			dataParameters.add((DataParameter)para);
    	}
    }
    public String getPageContainerName() {                         //返回容器唯一标志符
    	return (pageName+":"+containerName);              
    }
    public void selectTableViewRow(String paraCode) {               //选择某参数行
    	tableView.getSelectionModel().select(SubscribeParameters.getSubscribeParameters().subParameterMap.get(paraCode));
    }
	/**
	 * 1、实现对dataParameters监听
	 * 2、对tableView和列进行数据绑定
	 * 3、实现右键菜单：数据订阅，调整大小，删除容器
	 * 4、设置imageView属性：保存缩放，最大化，绑定图像数据
	 * 5、初始化hBox_Circles内容
	 * 6、anchor_image实现拖拽
	 * 7、设置anchor_image的UserData为容器名
	 */	
    public void init() {  
    	//1、实现对dataParameters监听
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
    	//2、对tableView和列进行数据绑定
    	tc_paraName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	tc_paraCode.setCellValueFactory(cellData -> cellData.getValue().codeNameProperty());
    	tc_paraUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    	tc_paraRange.setCellValueFactory(cellData -> cellData.getValue().rangeProperty());
    	tc_paraResult.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    	tableView.setItems(dataParameters);  	
    	//3、实现右键菜单：数据订阅，调整大小，删除容器
    	MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event) ->  {
            	if(mainApp.showSubscribe("image",getThis())) {
            		
            	} 
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction((event) ->  {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		setContainerSize(container.getWidth(), container.getHeight());
        		LogFactory.getGlobalLog().info("Input Change Container Size! PageName:"+pageName+" ContainerName:"+
        				containerName+" New Width:"+container.getWidth()+" New Height:"+container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
        }); 
        MenuItem addMenuItem3 = new MenuItem("删除容器");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction((event) ->  {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	LogFactory.getGlobalLog().info("Delete Container! ContainerName:"+containerName);
            	mainApp.getTabPaneController().refresh(pageName);  
        }); 
        //4、设置imageView属性：保存缩放，最大化，绑定图像数据
    	imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if (MouseButton.SECONDARY.equals(event.getButton())) {
                	addMenu1.show(anchor_image, event.getScreenX(), event.getScreenY());
                }else {
                	addMenu1.hide(); 
                }  
              }         
            });
    	tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if (MouseButton.SECONDARY.equals(event.getButton())) {
                	addMenu1.show(anchor_image, event.getScreenX(), event.getScreenY());
                }else {
                	addMenu1.hide(); 
                }  
              }         
            });
    	anchor_image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
              if (MouseButton.SECONDARY.equals(event.getButton())) {
              	addMenu1.show(anchor_image, event.getScreenX(), event.getScreenY());
              }else {
              	addMenu1.hide(); 
              }  
            }         
          });
    	//5、初始化hBox_Circles内容
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	imageView.setPreserveRatio(false);
    	imageView.fitWidthProperty().bind(anchor_img.widthProperty());
    	imageView.fitHeightProperty().bind(anchor_img.heightProperty());
    	SubscribeParameters.getSubscribeParameters().page_Container_ImageProperty.put(pageName+":"+containerName, new SimpleObjectProperty<>());
    	imageView.imageProperty().bind(SubscribeParameters.getSubscribeParameters()
    			.page_Container_ImageProperty.get(pageName+":"+containerName));
    	//6、anchor_image实现拖拽
    	DragUtil.addDragListener(anchor_image, mainApp, pageName);
        //8、设置anchor_table的UserData为容器名
    	anchor_image.setUserData(containerName);
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_image.setPrefSize(width, height);
    }
}
