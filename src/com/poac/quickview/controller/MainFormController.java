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
	private TreeView<IBaseNode> treeView_project;	       //导航TreeView
	@FXML
	private TreeView<IBaseNode> treeView_Assist;              //辅助功能TreeView_Assist
	@FXML
	private Accordion accordion_1;	                       //Accordion控件对TitledPane进行分组，Accordion控件可以让你创建多个面板并且每次显示其中一个      
	@FXML
	private TitledPane titledPane;	                       //工程名称Pane
	private MainApp mainApp; 	                           //用于访问其他Controller
	@FXML
	private AnchorPane split_RightAnchor;                  //用于放TabPane
	@FXML
	private TextField paraCodeTextField;                   //搜索参数代码文本框
	private Stage dialogStage;
	private ArrayList<String> pageList = new ArrayList<>();	//页面列表，存错所有页面名称
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }    
	@FXML
	private void onButtonClose(ActionEvent event){           //关闭窗口
		Platform.exit();
		System.exit(0);
	}	
	@FXML
	private void onSearch(ActionEvent event){                 //搜索按钮响应事件
		if(paraCodeTextField.getText().isEmpty())
			return;
		LogFactory.getGlobalLog().info("Search Field:"+paraCodeTextField.getText());
		mainApp.showSearchList(paraCodeTextField.getText());  //显示搜索列表窗口
	}
	@FXML
	private void onButtonMaxmize(ActionEvent event) {         //最大化窗口
		if(dialogStage.isMaximized()) {
			dialogStage.setMaximized(false);
		}else {
			dialogStage.setMaximized(true);
		}
	} 
	@FXML
	private void onKeyPressed(KeyEvent e) {                     //按键事件 
		if (e.getCode() == KeyCode.ENTER&&!paraCodeTextField.getText().isEmpty()) // 判断按下的键是否是回车键
		{
			LogFactory.getGlobalLog().info("Search Field:"+paraCodeTextField.getText());
			mainApp.showSearchList(paraCodeTextField.getText());  //显示搜索列表窗口
		}
	}
	@FXML
	private void onButtonMinimize(ActionEvent event) {  //最小化窗口
		dialogStage.setIconified(true);
	}
	@FXML
	private void onLogonClick(ActionEvent event) {       //登录窗口
		mainApp.showLogon(); 
	}
	@FXML
	private void initialize() {
		accordion_1.setExpandedPane(titledPane); //打开工程名称Pane
		//工厂方法客户化treeView_project 
		treeView_project.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() { 
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				TreeViewCellImpl tC = new TreeViewCellImpl(mainApp);
				tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) { // 双击打开Tab
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
		//工厂方法客户化treeView_Assist 
		treeView_Assist.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() { 
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				TreeViewCellImpl tC = new TreeViewCellImpl(mainApp);
				tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) { // 双击打开Tab
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
	//初始化MainForm数据:工程名称，辅助功能对应导航数据
	public void initData() {
		TreeItem<IBaseNode> itemRootAssist = new TreeItem<>(new AssistRoot("")); 
		TreeItem<IBaseNode> itemLog = new TreeItem<>(new OperLog("操作日志")); 
		TreeItem<IBaseNode> itemMes = new TreeItem<>(new MessageBorad("消息留言")); 
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
	 * 加载TabPane,设置ANCHOR，并打开辅助功能对应Tab
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
		mainApp.getTabPaneController().createTab("消息留言");
		mainApp.getTabPaneController().createTabLog("操作日志");
		mainApp.addMessageBoard();
	}
	/**
	 * 判断Page名是否存在
	 * @param pageName  
	 */	
	public boolean isExsitPageName(String pageName) {
		if(pageList.contains(pageName))
			return true;
		return false;
	}	
	/**
	 * 增加Page名
	 * @param pageName  
	 */	
	public void addPageName(String pageName) {
		pageList.add(pageName);
	}	
	/**
	 * 删除Page名
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
    private ContextMenu addMenu1 = new ContextMenu();  //右击菜单
    private ContextMenu addMenu2 = new ContextMenu();  //右击菜单
	private ContextMenu addMenu3 = new ContextMenu();            //右键菜单
    public TreeViewCellImpl(MainApp mainApp) {
    	MenuItem addMenuItem1 = new MenuItem("添加页面");
        MenuItem addMenuItem2 = new MenuItem("添加容器");   
        MenuItem addMenuItem3 = new MenuItem("删除页面");
        MenuItem addMenuItem4 = new MenuItem("添加留言板");
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
	public void updateItem(IBaseNode item, boolean empty) {   //重载treeitem
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


