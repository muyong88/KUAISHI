package com.poac.quickview.controller;

import java.util.ArrayList;

import org.controlsfx.control.CheckListView;
import com.poac.quickview.MainApp;
import com.poac.quickview.model.Group;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.Topic;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.model.Type;
import com.poac.quickview.util.JsonParserCustomer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SubscribeController implements IController {
	
	@FXML
	private MainApp mainApp; 
	@FXML
	private TreeView treeview_s;
	@FXML
	private CheckListView checkListView_1;
	private ArrayList<DataParameter> parmList;
    private boolean okClicked = false;
    private Stage dialogStage;
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleOk() {
    	for(Object n :checkListView_1.getCheckModel().getCheckedItems()) {
    		parmList.add(new DataParameter(n.toString()));
    	}
    	okClicked = true; 
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void initData() {
    	TreeDataModel rootNode=(new JsonParserCustomer()).getSubscribeData();
    	TreeItem<IBaseNode> item = new TreeItem<>(rootNode);    	
    	treeview_s.setRoot(item);
    	item.setExpanded(true);   
    	for(IBaseNode i :rootNode.getChilds()) {
    		TreeItem<IBaseNode> childNode=new TreeItem<>(i);
    		childNode.setExpanded(true);
    		item.getChildren().add(childNode);
    		for(IBaseNode j:((TreeDataModel)i).getChilds()){
    			TreeItem<IBaseNode> cchildNode=new TreeItem<>(j);
    			cchildNode.setExpanded(true);
    			childNode.getChildren().add(cchildNode);
    		}    		
    	}
    	treeview_s.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem<IBaseNode> selectedItem = (TreeItem<IBaseNode>) newValue;	
				TreeDataModel treeModel=(TreeDataModel)selectedItem.getValue();
				if(treeModel.getCurNode().getClass().getName().contains("Topic")) {
			    	ObservableList<String> strList = FXCollections.observableArrayList();  
			    	for(IBaseNode node : treeModel.getChilds()) {
			    		strList.add(node.getName());
			    	}
					checkListView_1.setItems(strList);					
				}
				else {
					checkListView_1.getItems().clear();
				}
			}
		});
    	treeview_s.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() {
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				return new TreeViewCellImpl_Subscribe();
			}
		});				
    } 
    public void setParmList( ArrayList<DataParameter> parmList) {
    	this.parmList=parmList;
    }
}

/**
 * define cell factory to customize tableview.
 */	
final class TreeViewCellImpl_Subscribe extends TreeCell<IBaseNode> {	 
    public TreeViewCellImpl_Subscribe() {      
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
			}
		}
	}
	private String getString() {
		return getItem() == null ? "" : getItem().getName().toString();
	}
}

