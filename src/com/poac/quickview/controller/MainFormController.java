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
import com.poac.quickview.model.Page;;


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
        TreeItem<String> item = new TreeItem<>("天和");
         treeView_project.setRoot(item);
         item.setExpanded(true);    
         TreeItem<String> i1 = new TreeItem<>("数值");
         TreeItem<String> i2 = new TreeItem<>("自定义页面");
         TreeItem<String> i3 = new TreeItem<>("流体柜");
         item.getChildren().addAll(i1,i2,i3);
         TreeItem<String> i4 = new TreeItem<>("空间三相多液滴迁移行为研究");
         TreeItem<String> i5 = new TreeItem<>("液桥");
         i3.setExpanded(true);
         i3.getChildren().addAll(i4,i5);
         TreeItem<String> i6 = new TreeItem<>("自定义页面1");
         TreeItem<String> i7 = new TreeItem<>("曲线");
         i5.getChildren().addAll(i6,i7);         
         i5.setExpanded(true);
         accordion_1.setExpandedPane(titledPane);
         treeView_project.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
             @Override
             public TreeCell<String> call(TreeView<String> p) {
            	 TreeViewCellImpl tC=new TreeViewCellImpl(mainApp);
            	 tC.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         if (event.getClickCount() == 2) {
                             TreeCell c = (TreeCell) event.getSource();
                             String tempS=c.getText();
                             if(pageList.contains(tempS)) {                            	 
                                	 boolean bb=false;
                                	 for(Tab t:tabPanel.getTabs()){
                                		 if(t.getText().equals(tempS))
                                			 bb=true;
                                	 }
                                	 if(bb==false){
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
//         treeView_project.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {
//
//             @Override
//             public void changed(ObservableValue observable, Object oldValue,
//                     Object newValue) {
//
//                 TreeItem<String> selectedItem = (TreeItem<String>) newValue;
//                 System.out.println("Selected Text : " + selectedItem.getValue());
//                 // do what ever you want 
//             }
//
//           });
	}
	public boolean isExsitPageName(String pageName) {
		if(pageList.contains(pageName))
			return true;
		return false;
	}	
}
final class TreeViewCellImpl extends TreeCell<String> {	 
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
            		TreeItem newPage = new TreeItem<String>(page.getPageName());
                    getTreeItem().getChildren().add(newPage);
            	}
            }
        }); 
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.showSubscribe();
            }
        });         
    }          
	@Override
	public void updateItem(String item, boolean empty) {
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
				if (mainApp.getMainFormController().isExsitPageName(getString())) {
					setContextMenu(addMenu2);
				} else {
					setContextMenu(addMenu1);
				}

			}
		}
	}
	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}
