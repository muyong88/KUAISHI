package com.poac.quickview.controller;

import java.util.ArrayList;

import org.controlsfx.control.CheckListView;
import com.poac.quickview.MainApp;
import com.poac.quickview.model.Group;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.Parameter;
import com.poac.quickview.model.Topic;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.model.Type;

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
	private ArrayList<Parameter> parmList;
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
    	okClicked = true;

    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void initData() {
    	TreeDataModel rootNode=new TreeDataModel(new Group("力学所"));
    	TreeDataModel dataType=new TreeDataModel(new Type("data"));
    	rootNode.add(dataType);
    	TreeDataModel imageType=new TreeDataModel(new Type("image"));
    	rootNode.add(imageType);
    	TreeDataModel videoType=new TreeDataModel(new Type("video"));
    	rootNode.add(videoType);
    	TreeDataModel curveType=new TreeDataModel(new Type("curve"));
    	rootNode.add(curveType);
    	TreeDataModel topic_data_1=new TreeDataModel(new Topic("流体实验柜-遥测数据-空间三相多液滴迁移"));
    	TreeDataModel topic_data_2=new TreeDataModel(new Topic("流体实验柜-工程数据-实验柜控制器A1"));
    	TreeDataModel topic_data_3=new TreeDataModel(new Topic("天和-工程数据"));
    	dataType.add(topic_data_1);
    	dataType.add(topic_data_2);
    	dataType.add(topic_data_3);
    	TreeDataModel topic_image=new TreeDataModel(new Topic("高等植物-应用数据JPEG"));
    	imageType.add(topic_image);
    	TreeDataModel topic_curve=new TreeDataModel(new Topic("流体实验柜-工程数据-实验柜控制器A1"));
    	curveType.add(topic_curve);
    	topic_data_1.add(new Parameter("液滴温度1"));
    	topic_data_1.add(new Parameter("液滴温度2"));
    	topic_data_2.add(new Parameter("A1温度1"));
    	topic_data_2.add(new Parameter("A1温度2"));
    	topic_data_3.add(new Parameter("俯仰姿态角度估值"));
    	topic_data_3.add(new Parameter("偏航姿态角度估值"));
    	topic_data_3.add(new Parameter("滚动姿态角度估值"));
    	topic_data_3.add(new Parameter("俯仰角速度预估"));
    	topic_image.add(new Parameter("温度1"));
    	topic_image.add(new Parameter("温度2"));
    	topic_image.add(new Parameter("高等植物图像"));
    	topic_curve.add(new Parameter("#0000ff"));
    	topic_curve.add(new Parameter("#ff8040"));
    	TreeItem<IBaseNode> item = new TreeItem<>(rootNode);    	
    	treeview_s.setRoot(item);
    	item.setExpanded(true);    	
    	TreeItem<IBaseNode> item1 = new TreeItem<>(dataType);      	
    	TreeItem<IBaseNode> item2 = new TreeItem<>(imageType);  
    	TreeItem<IBaseNode> item3 = new TreeItem<>(videoType);  
    	TreeItem<IBaseNode> item4 = new TreeItem<>(curveType);  
    	item.getChildren().add(item1);
    	item.getChildren().add(item2);
    	item.getChildren().add(item3);
    	item.getChildren().add(item4);
    	TreeItem<IBaseNode> item5 = new TreeItem<>(topic_data_1); 
    	TreeItem<IBaseNode> item6 = new TreeItem<>(topic_data_2); 
    	TreeItem<IBaseNode> item7 = new TreeItem<>(topic_data_3); 
    	item1.getChildren().add(item5);
    	item1.getChildren().add(item6);
    	item1.getChildren().add(item7);
    	item1.setExpanded(true);    	
    	TreeItem<IBaseNode> item8 = new TreeItem<>(topic_image); 
    	item2.getChildren().add(item8);
    	item2.setExpanded(true);
    	TreeItem<IBaseNode> item9 = new TreeItem<>(topic_curve); 
    	item4.getChildren().add(item9);
    	item4.setExpanded(true);
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
    public void setParmList( ArrayList<Parameter> parmList) {
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

