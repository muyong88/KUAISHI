package com.poac.quickview.controller;



import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXMasonryPane;
import com.poac.quickview.MainApp;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.Cabinet;
import com.poac.quickview.model.Capsule;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.Payload;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.JsonParserCustomer;;

public class MainFormController  implements IController{		
	@FXML
	private TreeView treeView_project;	
	@FXML
	private ListView listView_Assist;
	@FXML
	private Accordion accordion_1;	
	@FXML
	private TitledPane titledPane;	
	private MainApp mainApp; 	
	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane split_RightAnchor;
	@FXML
	private TextField paraCodeTextField;
	private Stage dialogStage;
	private ArrayList<String> pageList = new ArrayList<>();	
	private String curNodeName=null;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }    
	@FXML
	private void onButtonClose(ActionEvent event){
		Platform.exit();
		System.exit(0);
	}	
	@FXML
	private void onSearch(ActionEvent event){    //搜索按钮响应事件
		mainApp.showSearchList(paraCodeTextField.getText());
	}
	@FXML
	private void onButtonMaxmize(ActionEvent event) {
		if(dialogStage.isMaximized()) {
			dialogStage.setMaximized(false);
		}else {
			dialogStage.setMaximized(true);
		}
	} 
	@FXML
	private void onKeyPressed(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) // 判断按下的键是否是回车键
		{
			mainApp.showSearchList(paraCodeTextField.getText());
		}
	}
	@FXML
	private void onButtonMinimize(ActionEvent event) {
		dialogStage.setIconified(true);
	}
	@FXML
	private void onLogonClick(ActionEvent event) {
		mainApp.showLogon(); 
	}
	@FXML
	private void initialize() {
		accordion_1.setExpandedPane(titledPane);
		treeView_project.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() {
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				TreeViewCellImpl tC = new TreeViewCellImpl(mainApp);
				tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) {
							TreeCell c = (TreeCell) event.getSource();
							mainApp.getTabPaneController().openTab(c.getText());
						}
					}
				});
				return tC;
			} 
		});			
		listView_Assist.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 ) {
    	        	mainApp.getTabPaneController().openTab(listView_Assist.getSelectionModel().getSelectedItem().toString().trim());
    	         }    
    	    }
    	});
	}	
	public void initData() {
		listView_Assist.getItems().add("  消息留言");
		listView_Assist.getItems().add("  操作日志");
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
	}
	public boolean isExsitPageName(String pageName) {
		if(pageList.contains(pageName))
			return true;
		return false;
	}	
	public void addPageName(String pageName) {
		pageList.add(pageName);
	}		
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
    private ContextMenu addMenu1 = new ContextMenu();
    private ContextMenu addMenu2 = new ContextMenu();
    private MainApp mainApp; 
    public TreeViewCellImpl(MainApp mainApp) {
    	this.mainApp=mainApp;
        MenuItem addMenuItem1 = new MenuItem("添加页面");
        MenuItem addMenuItem2 = new MenuItem("添加容器");   
        MenuItem addMenuItem3 = new MenuItem("删除页面");
        addMenu1.getItems().add(addMenuItem1);
        addMenu2.getItems().add(addMenuItem2);
        addMenu2.getItems().add(addMenuItem3);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Page page=new Page();
            	if(mainApp.showAddPage(page)) {
            		TreeItem<IBaseNode> newPage = new TreeItem<>(page);
                    getTreeItem().getChildren().add(0,newPage);
                    mainApp.getTabPaneController().createTab(page.getName());
            	}
            	mainApp.getMainFormController().addPageName(page.getName());
            }
        }); 
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	if(mainApp.showAddContainer(getString())) {
            		mainApp.addContainer(getString());            		
            	}
            }
        });   
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	getTreeItem().getParent().getChildren().remove(getTreeItem());
            	mainApp.getTabPaneController().removeTab(getString());
            	mainApp.getMainFormController().removePageName(getString());
            }
        });   
    }          
	@Override
	public void updateItem(IBaseNode item, boolean empty) {
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
				} else {
					setContextMenu(addMenu1);
				}
			}
		}
	}
	private String getString() {
		return getItem() == null ? "" : getItem().getName().toString();
	}
}


