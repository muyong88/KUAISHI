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
	private AnchorPane anchor_image;                             //ͼ����� ������������������ؼ�
	@FXML
	private ImageView imageView;                                 //ͼ��ؼ�
	@FXML
	private TableView<DataParameter> tableView;                  //���ؼ�
	@FXML
	private TableColumn<DataParameter, String> tc_paraName;      //����������
	@FXML
	private TableColumn<DataParameter, String> tc_paraCode;      //����������
	@FXML
	private TableColumn<DataParameter, String> tc_paraUnit;      //������λ
	@FXML
	private TableColumn<DataParameter, String> tc_paraRange;     //������Χ
	@FXML
	private TableColumn<DataParameter, String> tc_paraResult;    //�������
	@FXML
	private Label label_head;                                    //��������
	@FXML
	private AnchorPane anchor_img;                               //���ڷ���imageView
	@FXML
	private HBox hBox_Circles;                                   //Ȧ��HBox
	public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList(); //���Ĳ����б�
	private MainApp mainApp; 	                                 //������ʽӿ�
	private String pageName=null;                                //page��
    private String containerName=null;                           //������
    private ContextMenu addMenu1 = new ContextMenu();            //�Ҽ��˵�
	public ImageContainerController() {
		
	}
	public void setPageName(String pageName) {                    //����page��
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {                     //���ó�����ʽӿ�
        this.mainApp = mainApp;
    }  
    private IController getThis() {                               //��ȡ��ʵ������
    	return this;
    }
	/**
	 * ��ʼ��������������
	 * @param containerModel  
	 */	
    public void initData(TreeDataModel containerModel) {          
    	for(IBaseNode para:containerModel.getChilds()) {
    		if(!((DataParameter)para).getCodeName().equals("Image"))
    			dataParameters.add((DataParameter)para);
    	}
    }
    public String getPageContainerName() {                         //��������Ψһ��־��
    	return (pageName+":"+containerName);              
    }
    public void selectTableViewRow(String paraCode) {               //ѡ��ĳ������
    	tableView.getSelectionModel().select(SubscribeParameters.getSubscribeParameters().subParameterMap.get(paraCode));
    }
	/**
	 * 1��ʵ�ֶ�dataParameters����
	 * 2����tableView���н������ݰ�
	 * 3��ʵ���Ҽ��˵������ݶ��ģ�������С��ɾ������
	 * 4������imageView���ԣ��������ţ���󻯣���ͼ������
	 * 5����ʼ��hBox_Circles����
	 * 6��anchor_imageʵ����ק
	 * 7������anchor_image��UserDataΪ������
	 */	
    public void init() {  
    	//1��ʵ�ֶ�dataParameters����
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
    	//2����tableView���н������ݰ�
    	tc_paraName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	tc_paraCode.setCellValueFactory(cellData -> cellData.getValue().codeNameProperty());
    	tc_paraUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    	tc_paraRange.setCellValueFactory(cellData -> cellData.getValue().rangeProperty());
    	tc_paraResult.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
    	tableView.setItems(dataParameters);  	
    	//3��ʵ���Ҽ��˵������ݶ��ģ�������С��ɾ������
    	MenuItem addMenuItem1 = new MenuItem("���ݶ���");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event) ->  {
            	if(mainApp.showSubscribe("image",getThis())) {
            		
            	} 
        }); 
        MenuItem addMenuItem2 = new MenuItem("������С");    //�һ�TableView��ʾ������С�˵�
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction((event) ->  {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		setContainerSize(container.getWidth(), container.getHeight());
        		LogFactory.getGlobalLog().info("Input Change Container Size! PageName:"+pageName+" ContainerName:"+
        				containerName+" New Width:"+container.getWidth()+" New Height:"+container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
        }); 
        MenuItem addMenuItem3 = new MenuItem("ɾ������");    //�һ�TableView��ʾ������С�˵�
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction((event) ->  {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	LogFactory.getGlobalLog().info("Delete Container! ContainerName:"+containerName);
            	mainApp.getTabPaneController().refresh(pageName);  
        }); 
        //4������imageView���ԣ��������ţ���󻯣���ͼ������
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
    	//5����ʼ��hBox_Circles����
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	imageView.setPreserveRatio(false);
    	imageView.fitWidthProperty().bind(anchor_img.widthProperty());
    	imageView.fitHeightProperty().bind(anchor_img.heightProperty());
    	SubscribeParameters.getSubscribeParameters().page_Container_ImageProperty.put(pageName+":"+containerName, new SimpleObjectProperty<>());
    	imageView.imageProperty().bind(SubscribeParameters.getSubscribeParameters()
    			.page_Container_ImageProperty.get(pageName+":"+containerName));
    	//6��anchor_imageʵ����ק
    	DragUtil.addDragListener(anchor_image, mainApp, pageName);
        //8������anchor_table��UserDataΪ������
    	anchor_image.setUserData(containerName);
    }
    public void setHeadText(String txt) {             //����������Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //��������Size
    	anchor_image.setPrefSize(width, height);
    }
}
