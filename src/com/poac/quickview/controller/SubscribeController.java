package com.poac.quickview.controller;

import java.util.ArrayList;

import org.controlsfx.control.CheckListView;
import com.poac.quickview.MainApp;
import com.poac.quickview.global.GlobalVariable;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Group;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.CurveParameter;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.Topic;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.model.Type;
import com.poac.quickview.util.JsonParserCustomer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SubscribeController implements IController {
	
	private MainApp mainApp; 
	@FXML
	private TreeView treeview_s;
	@FXML
	private CheckListView<IBaseNode>  checkListView_1;
    private boolean okClicked = false;
    private Stage dialogStage;
    private String subType;  //订阅类型
    private IController containerController=null;   //调用这个订阅的容器Controller
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setContainerController(IController con) {
    	containerController=con;
    }
    @FXML
    private void handleOk() {
    	for(IBaseNode node :checkListView_1.getItems()) {
			if (subType.equals(GlobalVariable.data)) {   //Table容器订阅
				TableContainerController tCC=(TableContainerController)containerController;
				if(checkListView_1.getCheckModel().isChecked(node)) {
					if(!tCC.dataParameters.contains((DataParameter)node)) {
						tCC.dataParameters.add((DataParameter)node);
					}
				}else {
					if(tCC.dataParameters.contains((DataParameter)node)) {
						tCC.dataParameters.remove((DataParameter)node);
					}
				}
			}else if(subType.equals(GlobalVariable.curve)) {   //Curve容器订阅
				CurveContainerController cCC=(CurveContainerController)containerController;
				if(checkListView_1.getCheckModel().isChecked(node)) {
					if(!cCC.dataParameters.contains((DataParameter)node)) {
						cCC.dataParameters.add((DataParameter)node);
					}
				}else {
					if(cCC.dataParameters.contains((DataParameter)node)) {
						cCC.dataParameters.remove((DataParameter)node);
					}
				}
			}else if(subType.equals(GlobalVariable.image)) {   //Curve容器订阅
				ImageContainerController iCC=(ImageContainerController)containerController;
				if(checkListView_1.getCheckModel().isChecked(node)) {
					if(!iCC.dataParameters.contains((DataParameter)node)) {
						iCC.dataParameters.add((DataParameter)node);
					}
				}else {
					if(iCC.dataParameters.contains((DataParameter)node)) {
						iCC.dataParameters.remove((DataParameter)node);
					}
				}
			}
    	}
    	okClicked = true; 
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void initData(String type) {
    	subType=type;
    	TreeDataModel rootNode=(new JsonParserCustomer()).getSubscribeData(type);
    	TreeItem<IBaseNode> item = new TreeItem<>(rootNode);    	
    	treeview_s.setRoot(item);
    	item.setExpanded(true);   
    	for(IBaseNode i :rootNode.getChilds()) {
    		TreeItem<IBaseNode> childNode=new TreeItem<>(i);
    		childNode.setExpanded(true);
    		item.getChildren().add(childNode);		
    	}
    	
    	treeview_s.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				TreeItem<IBaseNode> selectedItem = (TreeItem<IBaseNode>) newValue;	
				TreeDataModel treeModel=(TreeDataModel)selectedItem.getValue();
				if(treeModel.getCurNode().getClass().getName().contains("Topic")) {
			    	ObservableList<IBaseNode> obsList = FXCollections.observableArrayList();  
			    	for(IBaseNode node : treeModel.getChilds()) {
			    		obsList.add(node);
			    	}			    	
					checkListView_1.setItems(obsList);	
			    	for(IBaseNode node : treeModel.getChilds()) {
			    		if(subType.equals(GlobalVariable.data)) {
			    			if(((TableContainerController)containerController).dataParameters.contains(node)) {
			    				checkListView_1.getCheckModel().check(node);
			    			}
			    		}else if(subType.equals(GlobalVariable.curve)) {
			    			CurveContainerController cc=((CurveContainerController)containerController);
			    			if(cc.dataParameters.contains((DataParameter)node)) {
			    				checkListView_1.getCheckModel().check(node);
			    			}
		    			}else if(subType.equals(GlobalVariable.image)) {
		    				ImageContainerController cc=((ImageContainerController)containerController);
			    			if(cc.dataParameters.contains((DataParameter)node)) {
			    				checkListView_1.getCheckModel().check(node);
			    			}
		    			}
			    		//其他类型以后实现
			    	}	
				}
				else {
					checkListView_1.getItems().clear();
				}
			}
		});
    	//客户化checklistview
    	checkListView_1.setCellFactory(lv -> new CheckBoxListCell<IBaseNode>(checkListView_1::getItemBooleanProperty) {
    	    @Override
    	    public void updateItem(IBaseNode node, boolean empty) {
    	        super.updateItem(node, empty);
    	        setText(node == null ? "" : node.getName());
    	    }
    	});	
    	treeview_s.setCellFactory(new Callback<TreeView<IBaseNode>, TreeCell<IBaseNode>>() {
			@Override
			public TreeCell<IBaseNode> call(TreeView<IBaseNode> p) {
				return new TreeViewCellImpl_Subscribe();
			}
		});				
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

