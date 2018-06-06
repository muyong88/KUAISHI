package com.poac.quickview.controller;



import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.util.ArrayList;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.BaseNode;
import com.poac.quickview.model.Cabinet;
import com.poac.quickview.model.Capsule;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.Payload;;

public class MainFormController {
	
	@FXML
	private TreeView treeView_project;	
	@FXML
	private Accordion accordion_1;	
	@FXML
	private TitledPane titledPane;	
	@FXML
	private MainApp mainApp; 	
	@FXML
	private TabPane tabPanel;	
	private ArrayList<String> pageList = new ArrayList<>();	
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
		TreeItem<BaseNode> item = new TreeItem<>(new Capsule("天和"));
		treeView_project.setRoot(item);
		item.setExpanded(true);
		TreeItem<BaseNode> i1 = new TreeItem<>(new Page("数值"));
		TreeItem<BaseNode> i2 = new TreeItem<>(new Page("自定义页面"));
		TreeItem<BaseNode> i3 = new TreeItem<>(new Cabinet("流体柜"));
		item.getChildren().addAll(i1, i2, i3);
		TreeItem<BaseNode> i4 = new TreeItem<>(new Payload("空间三相多液滴迁移行为研究"));
		TreeItem<BaseNode> i5 = new TreeItem<>(new Payload("液桥"));
		i3.setExpanded(true);
		i3.getChildren().addAll(i4, i5);
		TreeItem<BaseNode> i6 = new TreeItem<>(new Page("自定义页面1"));
		TreeItem<BaseNode> i7 = new TreeItem<>(new Page("曲线"));
		i5.getChildren().addAll(i6, i7);
		i5.setExpanded(true);		
		accordion_1.setExpandedPane(titledPane);
		treeView_project.setCellFactory(new Callback<TreeView<BaseNode>, TreeCell<BaseNode>>() {
			@Override
			public TreeCell<BaseNode> call(TreeView<BaseNode> p) {
				TreeViewCellImpl tC = new TreeViewCellImpl(mainApp);
				tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if (event.getClickCount() == 2) {
							TreeCell c = (TreeCell) event.getSource();
							String tempS = c.getText();
							if (pageList.contains(tempS)) {
								boolean bb = false;
								for (Tab t : tabPanel.getTabs()) {
									if (t.getText().equals(tempS))
										bb = true;
								}
								if (bb == false) {
									Tab tab = new Tab();
									tab.setText(tempS);
									tabPanel.getTabs().add(tab);
									SingleSelectionModel<Tab> selectionModel = tabPanel.getSelectionModel();
									selectionModel.select(tab);
								}
							}
						}
					}
				});
				return tC;
			}
		});
//		 treeView_project.getSelectionModel().selectedItemProperty().addListener( new
//		 ChangeListener() {		
//		 @Override
//		 public void changed(ObservableValue observable, Object oldValue,
//		 Object newValue) {		
//		 TreeItem<BaseNode> selectedItem = (TreeItem<BaseNode>) newValue;
//		 System.out.println("Selected Text : " + selectedItem.getValue().getClass().getName());
//		 // do what ever you want
//		 }		
//		 });
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
final class TreeViewCellImpl extends TreeCell<BaseNode> {	 
    private ContextMenu addMenu1 = new ContextMenu();
    private ContextMenu addMenu2 = new ContextMenu();
    private MainApp mainApp; 
    public TreeViewCellImpl(MainApp mainApp) {
    	this.mainApp=mainApp;
        MenuItem addMenuItem1 = new MenuItem("增加页面");
        MenuItem addMenuItem2 = new MenuItem("增加容器");
        MenuItem addMenuItem3 = new MenuItem("增加参数");
        addMenu1.getItems().add(addMenuItem1);
        addMenu2.getItems().add(addMenuItem2);
        addMenu2.getItems().add(addMenuItem3);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Page page=new Page();
            	if(mainApp.showAddPage(page)) {
            		TreeItem newPage = new TreeItem<String>(page.getName());
                    getTreeItem().getChildren().add(newPage);
            	}
            	mainApp.getMainFormController().addPageName(page.getName());
            }
        }); 
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.showSubscribe();
            }
        });         
    }          
	@Override
	public void updateItem(BaseNode item, boolean empty) {
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

