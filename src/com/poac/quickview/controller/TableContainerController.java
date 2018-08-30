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
	private AnchorPane anchor_table;                                //����������
	@FXML
	private TableView<DataParameter> tableView;                     //���ؼ�
	@FXML
	private Label label_head;                                       //��������
	@FXML
	private TableColumn<DataParameter, String> tc_paraName;         //��������
	@FXML
	private TableColumn<DataParameter, String> tc_paraCode;         //����������
	@FXML
	private TableColumn<DataParameter, String> tc_paraUnit;         //����������
	@FXML
	private TableColumn<DataParameter, String> tc_paraRange;        //������Χ��
	@FXML
	private TableColumn<DataParameter, String> tc_paraResult;       //���������
	@FXML
	private HBox hBox_Circles;                                      //Ȧ��HBOX
	public ObservableList<DataParameter> dataParameters = FXCollections.observableArrayList(); //�����б�
	private MainApp mainApp; 	                                    //������ʽӿ�
	private String pageName=null;                                   //page��
    private String containerName=null;                              //������
    private ContextMenu addMenu1 = new ContextMenu();               //�Ҽ��˵�
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");

	public void setPageName(String pageName) {                       //����page��
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {                        //����Ӧ�ó���ӿ�
        this.mainApp = mainApp;
    }
    public void initData(TreeDataModel containerModel) {             //��ʼ������
    	for(IBaseNode para:containerModel.getChilds()) {
    		dataParameters.add((DataParameter)para);
    	}
    }
    private IController getThis() {                                   //��ȡ��ʵ������
    	return this;
    }  
    public String getPageContainerName() {                            //��ȡ������ΨһID
    	return (pageName+":"+containerName);
    }
    public void selectTableViewRow(String paraCode) {                 //ѡ���������������
    	tableView.getSelectionModel().select(SubscribeParameters.getSubscribeParameters().subParameterMap.get(paraCode));
    }
	/**
	 * 1������dataParameters��������������ʱ���ڲ�����subscrbeContainer�����������������������󣨼���ʾ����������������������
	 * 2��tableView������dataParameters
	 * 3��ʵ���Ҽ��˵������ݶ��ġ�������С��ɾ������
	 * 4������а���������
	 * 5�����������ͻ���tableView��ʵ�������ı���˳��
	 * 6������Ȧ��ģ��
	 * 7��ʵ����ק����
	 * 8������anchor_table��UserDataΪ������
	 */	
    public void init() {
    	//1������dataParameters��������������ʱ���ڲ�����subscrbeContainer�����������������������󣨼���ʾ����������������������
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
    	//2��tableView������dataParameters
    	tableView.setItems(dataParameters);  
    	//3��ʵ���Ҽ��˵������ݶ��ġ�������С��ɾ������
        MenuItem addMenuItem1 = new MenuItem("���ݶ���");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event) -> {
            	if(mainApp.showSubscribe("data",getThis())) {

            	} 
        }); 
        MenuItem addMenuItem2 = new MenuItem("������С");    //�һ�TableView��ʾ������С�˵�
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
        MenuItem addMenuItem3 = new MenuItem("ɾ������");    //�һ�TableView��ʾ������С�˵�
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
        //4������а���������
    	tc_paraName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    	tc_paraCode.setCellValueFactory(cellData -> cellData.getValue().codeNameProperty());
    	tc_paraUnit.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    	tc_paraRange.setCellValueFactory(cellData -> cellData.getValue().rangeProperty());
    	tc_paraResult.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
        //5�����������ͻ���tableView��ʵ�������ı���˳��
    	tableView.setRowFactory(tv -> {        	 //��ק�����
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
    	//6������Ȧ��ģ��
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	//7��ʵ����ק����
        DragUtil.addDragListener(anchor_table, mainApp, pageName);
        //8������anchor_table��UserDataΪ������
        anchor_table.setUserData(containerName);
    }
    public void setHeadText(String txt) {             //����������Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //��������Size
    	anchor_table.setPrefSize(width, height);
    }
}
