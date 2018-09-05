package com.poac.quickview.controller;



import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ArrayList;
import com.poac.quickview.MainApp;
import com.poac.quickview.model.AssistRoot;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.MessageBorad;
import com.poac.quickview.model.OperLog;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.JsonParserCustomer;
import com.poac.quickview.util.LogFactory;;

public class MainFormController  implements IController{		
	@FXML
	private TreeView<IBaseNode> treeView_project;	       //����TreeView
	@FXML
	private TreeView<IBaseNode> treeView_Assist;              //��������TreeView_Assist
	@FXML
	private Accordion accordion_1;	                       //Accordion�ؼ���TitledPane���з��飬Accordion�ؼ��������㴴�������岢��ÿ����ʾ����һ��      
	@FXML
	private TitledPane titledPane;	                       //��������Pane
	private MainApp mainApp; 	                           //���ڷ�������Controller
	@FXML
	private AnchorPane split_RightAnchor;                  //���ڷ�TabPane
	@FXML
	private TextField paraCodeTextField;                   //�������������ı���
	private Stage dialogStage;
	private ArrayList<String> pageList = new ArrayList<>();	//ҳ���б��������ҳ������
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }    
	@FXML
	private void onButtonClose(ActionEvent event){           //�رմ���
		Platform.exit();
		System.exit(0);
	}	
	@FXML
	private void onSearch(ActionEvent event){                 //������ť��Ӧ�¼�
		if(paraCodeTextField.getText().isEmpty())
			return;
		LogFactory.getGlobalLog().info("Search Field:"+paraCodeTextField.getText());
		mainApp.showSearchList(paraCodeTextField.getText());  //��ʾ�����б���
	}
	@FXML
	private void onButtonMaxmize(ActionEvent event) {         //��󻯴���
		if(dialogStage.isMaximized()) {
			dialogStage.setMaximized(false);
		}else {
			dialogStage.setMaximized(true);
		}
	} 
	@FXML
	private void onKeyPressed(KeyEvent e) {                     //�����¼� 
		if (e.getCode() == KeyCode.ENTER&&!paraCodeTextField.getText().isEmpty()) // �жϰ��µļ��Ƿ��ǻس���
		{
			LogFactory.getGlobalLog().info("Search Field:"+paraCodeTextField.getText());
			mainApp.showSearchList(paraCodeTextField.getText());  //��ʾ�����б���
		}
	}
	@FXML
	private void onButtonMinimize(ActionEvent event) {  //��С������
		dialogStage.setIconified(true);
	}
	@FXML
	private void onLogonClick(ActionEvent event) {       //��¼����
		mainApp.showLogon(); 
	}
	@FXML
	private void initialize() {
		accordion_1.setExpandedPane(titledPane); //�򿪹�������Pane
		//���������ͻ���treeView_project 
		treeView_project.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() { 
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				TreeViewCellImpl tC = new TreeViewCellImpl(mainApp);
				tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) { // ˫����Tab
							@SuppressWarnings("unchecked")
							TreeCell<IBaseNode> c = (TreeCell<IBaseNode>) event.getSource();
							if (!c.isEmpty()) {
								mainApp.getTabPaneController().openTab(c.getText());
								LogFactory.getGlobalLog().info("Open  Tab:" + c.getText());
							}
						}
					}
				});
				return tC;
			} 
		});			
		//���������ͻ���treeView_Assist 
		treeView_Assist.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() { 
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				TreeViewCellImpl tC = new TreeViewCellImpl(mainApp);
				tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) { // ˫����Tab
							@SuppressWarnings("unchecked")
							TreeCell<IBaseNode> c = (TreeCell<IBaseNode>) event.getSource();
							if (!c.isEmpty()) {
								mainApp.getTabPaneController().openTab(c.getText());
								LogFactory.getGlobalLog().info("Open  Tab:" + c.getText());
							}
						}
					}
				});
				return tC;
			} 
		});	
	}	
	//��ʼ��MainForm����:�������ƣ��������ܶ�Ӧ��������
	public void initData() {
		TreeItem<IBaseNode> itemRootAssist = new TreeItem<>(new AssistRoot("")); 
		TreeItem<IBaseNode> itemLog = new TreeItem<>(new OperLog("������־")); 
		TreeItem<IBaseNode> itemMes = new TreeItem<>(new MessageBorad("��Ϣ����")); 
		itemRootAssist.getChildren().add(itemMes);
		itemRootAssist.getChildren().add(itemLog);
		treeView_Assist.setRoot(itemRootAssist);
		treeView_Assist.setShowRoot(false);
		TreeDataModel rootM=(new JsonParserCustomer()).getNavationData(pageList);
		TreeItem<IBaseNode> itemRoot = new TreeItem<>(rootM); 
		itemRoot.setExpanded(true);
		treeView_project.setRoot(itemRoot);
		for(IBaseNode i :rootM.getChilds()) {
	   		TreeItem<IBaseNode> childNode=new TreeItem<>(i);
    		childNode.setExpanded(true);
    		itemRoot.getChildren().add(childNode);
			if (i.getClass().getName().contains("TreeDataModel")) {
				for (IBaseNode j : ((TreeDataModel) i).getChilds()) {
					TreeItem<IBaseNode> cchildNode = new TreeItem<>(j);
					cchildNode.setExpanded(true);
					childNode.getChildren().add(cchildNode);
					if (j.getClass().getName().contains("TreeDataModel")) {
						for (IBaseNode k : ((TreeDataModel) j).getChilds()) {
							TreeItem<IBaseNode> ccchildNode = new TreeItem<>(k);
							ccchildNode.setExpanded(true);
							cchildNode.getChildren().add(ccchildNode);
						}
					}
				}
			}
		}
	}	
	/**
	 * ����TabPane,����ANCHOR�����򿪸������ܶ�ӦTab
	 * @param tabPanel  
	 */	
	@SuppressWarnings("static-access")	
	public void LoadTabPanel(TabPane tabPanel) {	
		split_RightAnchor.getChildren().add(tabPanel);
		split_RightAnchor.setTopAnchor(tabPanel,0.0);		
		split_RightAnchor.setRightAnchor(tabPanel,0.0);
		split_RightAnchor.setBottomAnchor(tabPanel,0.0);
		split_RightAnchor.setLeftAnchor(tabPanel,0.0);
		for(String pageName:pageList) {
			mainApp.getTabPaneController().createTab(pageName);
		}
		mainApp.getTabPaneController().createTab("��Ϣ����");
		mainApp.getTabPaneController().createTabLog("������־");
		mainApp.addMessageBoard();
	}
	/**
	 * �ж�Page���Ƿ����
	 * @param pageName  
	 */	
	public boolean isExsitPageName(String pageName) {
		if(pageList.contains(pageName))
			return true;
		return false;
	}	
	/**
	 * ����Page��
	 * @param pageName  
	 */	
	public void addPageName(String pageName) {
		pageList.add(pageName);
	}	
	/**
	 * ɾ��Page��
	 * @param pageName  
	 */	
	public void removePageName(String pageName) {
		pageList.remove(pageName);
	}
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
/**
 * define cell factory to customize tableview.
 */	
final class TreeViewCellImpl extends TreeCell<IBaseNode> {	 
    private ContextMenu addMenu1 = new ContextMenu();  //�һ��˵�
    private ContextMenu addMenu2 = new ContextMenu();  //�һ��˵�
	private ContextMenu addMenu3 = new ContextMenu();            //�Ҽ��˵�
    public TreeViewCellImpl(MainApp mainApp) {
    	MenuItem addMenuItem1 = new MenuItem("���ҳ��");
        MenuItem addMenuItem2 = new MenuItem("�������");   
        MenuItem addMenuItem3 = new MenuItem("ɾ��ҳ��");
        MenuItem addMenuItem4 = new MenuItem("������԰�");
        addMenu1.getItems().add(addMenuItem1);
        addMenu2.getItems().add(addMenuItem2);
        addMenu2.getItems().add(addMenuItem3); 
        addMenu3.getItems().add(addMenuItem4);
		addMenuItem1.setOnAction((event) -> {
			Page page = new Page();
			if (mainApp.showAddPage(page)) {
				TreeItem<IBaseNode> newPage = new TreeItem<>(page);
				getTreeItem().getChildren().add(0, newPage);
				mainApp.getTabPaneController().createTab(page.getName());
			}
			mainApp.getMainFormController().addPageName(page.getName());
		});
		addMenuItem2.setOnAction((event) -> {
			if (mainApp.showAddContainer(getString())) {
				mainApp.addContainer(getString());
			}
		});
		addMenuItem3.setOnAction((event) -> {
			getTreeItem().getParent().getChildren().remove(getTreeItem());
			mainApp.getTabPaneController().removeTab(getString());
			mainApp.getMainFormController().removePageName(getString());
			LogFactory.getGlobalLog().info("Delete Page:" + getString());
		});
		addMenuItem4.setOnAction((event) -> {
			mainApp.addMessageBoard();
		});
    }          
	@Override
	public void updateItem(IBaseNode item, boolean empty) {   //����treeitem
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				setText(null);
				setGraphic(null);
			} else {
				setText(getString());
				setGraphic(getTreeItem().getGraphic());
				if (item.getClass().getName().contains("Page")) {
					setContextMenu(addMenu2);
				}else if(item.getClass().getName().contains("MessageBorad")) {
					setContextMenu(addMenu3);
				}else if(item.getClass().getName().contains("OperLog")) {
					 
				}else {
					setContextMenu(addMenu1);
				}
			}
		}
	}
	private String getString() {
		return getItem() == null ? "" : getItem().getName().toString();
	}
}


