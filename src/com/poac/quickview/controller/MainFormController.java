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
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXMasonryPane;
import com.poac.quickview.MainApp;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.Cabinet;
import com.poac.quickview.model.Capsule;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.Payload;;

public class MainFormController  implements IController{		
	@FXML
	private TreeView treeView_project;	
	@FXML
	private Accordion accordion_1;	
	@FXML
	private TitledPane titledPane;	
	private MainApp mainApp; 	
	@FXML
	private BorderPane borderPane;
	@FXML
	private AnchorPane split_RightAnchor;
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
	private void initialize() {
		pageList.add("数值");
		pageList.add("自定义页面");
		pageList.add("自定义页面1");
		pageList.add("曲线");
		TreeItem<IBaseNode> item = new TreeItem<>(new Capsule("天和"));
		treeView_project.setRoot(item);
		item.setExpanded(true);
		TreeItem<IBaseNode> i1 = new TreeItem<>(new Page("数值"));
		TreeItem<IBaseNode> i2 = new TreeItem<>(new Page("自定义页面"));
		TreeItem<IBaseNode> i3 = new TreeItem<>(new Cabinet("流体柜"));
		item.getChildren().addAll(i1, i2, i3);
		TreeItem<IBaseNode> i4 = new TreeItem<>(new Payload("空间三相多液滴迁移行为研究"));
		TreeItem<IBaseNode> i5 = new TreeItem<>(new Payload("液桥"));
		i3.setExpanded(true);
		i3.getChildren().addAll(i4, i5);
		TreeItem<IBaseNode> i6 = new TreeItem<>(new Page("自定义页面1"));
		TreeItem<IBaseNode> i7 = new TreeItem<>(new Page("曲线"));
		i5.getChildren().addAll(i6, i7);
		i5.setExpanded(true);		
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
							mainApp.openTab(c.getText());
						}
					}
				});
				return tC;
			}
		});				
	}	
	public void LoadTabPanel(TabPane tabPanel) {	
		split_RightAnchor.getChildren().add(tabPanel);
		split_RightAnchor.setTopAnchor(tabPanel,0.0);		
		split_RightAnchor.setRightAnchor(tabPanel,0.0);
		split_RightAnchor.setBottomAnchor(tabPanel,0.0);
		split_RightAnchor.setLeftAnchor(tabPanel,0.0);
		mainApp.createTab("数值");
		mainApp.createTab("自定义页面");
		mainApp.createTab("自定义页面1");
		mainApp.createTab("曲线");
	}
	public boolean isExsitPageName(String pageName) {
		if(pageList.contains(pageName))
			return true;
		return false;
	}	
	public void addPageName(String pageName) {
		pageList.add(pageName);
	}		
}
final class TreeViewCellImpl extends TreeCell<IBaseNode> {	 
    private ContextMenu addMenu1 = new ContextMenu();
    private ContextMenu addMenu2 = new ContextMenu();
    private MainApp mainApp; 
    public TreeViewCellImpl(MainApp mainApp) {
    	this.mainApp=mainApp;
        MenuItem addMenuItem1 = new MenuItem("添加页面");
        MenuItem addMenuItem2 = new MenuItem("添加表格容器");        
        addMenu1.getItems().add(addMenuItem1);
        addMenu2.getItems().add(addMenuItem2);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Page page=new Page();
            	if(mainApp.showAddPage(page)) {
            		TreeItem<IBaseNode> newPage = new TreeItem<>(page);
                    getTreeItem().getChildren().add(newPage);
                    mainApp.createTab(page.getName());
            	}
            	mainApp.getMainFormController().addPageName(page.getName());
            }
        }); 
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	if(mainApp.showAddTableContainer()) {
            		mainApp.addContainer(getString());            		
            	}
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


