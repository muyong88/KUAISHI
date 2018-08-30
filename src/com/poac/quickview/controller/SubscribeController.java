package com.poac.quickview.controller;

import org.controlsfx.control.CheckListView;
import com.poac.quickview.MainApp;
import com.poac.quickview.global.GlobalVariable;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.TreeDataModel;
import com.poac.quickview.util.JsonParserCustomer;
import com.poac.quickview.util.LogFactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SubscribeController implements IController {
	
	@FXML
	private TreeView<IBaseNode> treeview_s;                                 //��״�ؼ�
	@FXML
	private CheckListView<IBaseNode>  checkListView_1;           //��ѡ���б�
    private boolean okClicked = false;                           //���ȷ����ť�Ƿ񱻵���
    private Stage dialogStage;                                   //������
    private String subType;  									 //��������
    private IController containerController=null;  				 //����������ĵ�����Controller
    //���ó�����ʽӿ�
    public void setMainApp(MainApp mainApp) {
    }
    //����okClicked
    public boolean isOkClicked() {
        return okClicked;
    }
    //���ݴ���
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    //���õ���������ĵ�����������
    public void setContainerController(IController con) {
    	containerController=con;
    }
	/**
	 * ȷ����ť�¼�
	 * ����б���ĳ�ѡ��������û�б����ģ�������������
	 * ����б���ĳ��û��ѡ���������Ѿ������ģ���ȡ���������
	 */		
    @FXML
    private void handleOk() {             
    	for(IBaseNode node :checkListView_1.getItems()) {
			if (subType.equals(GlobalVariable.data)) {   //Table��������
				TableContainerController tCC=(TableContainerController)containerController;
				if(checkListView_1.getCheckModel().isChecked(node)) {
					if(!tCC.dataParameters.contains((DataParameter)node)) {
						tCC.dataParameters.add((DataParameter)node);
						LogFactory.getGlobalLog().info("Add Subscribe Table Parameter! Parameter Code:"+((DataParameter)node).getCodeName());
					}
				}else {
					if(tCC.dataParameters.contains((DataParameter)node)) {
						tCC.dataParameters.remove((DataParameter)node);
						LogFactory.getGlobalLog().info("Cancel Subscribe Table Parameter! Parameter Code:"+((DataParameter)node).getCodeName());
					}
				}
			}else if(subType.equals(GlobalVariable.curve)) {   //Curve��������
				CurveContainerController cCC=(CurveContainerController)containerController;
				if(checkListView_1.getCheckModel().isChecked(node)) {
					if(!cCC.dataParameters.contains((DataParameter)node)) {
						cCC.dataParameters.add((DataParameter)node);
						LogFactory.getGlobalLog().info("Add Subscribe Curve Parameter! Parameter Code:"+((DataParameter)node).getCodeName());
					}
				}else {
					if(cCC.dataParameters.contains((DataParameter)node)) {
						cCC.dataParameters.remove((DataParameter)node);
						LogFactory.getGlobalLog().info("Cancel Subscribe Curve Parameter! Parameter Code:"+((DataParameter)node).getCodeName());
					}
				}
			}else if(subType.equals(GlobalVariable.image)) {   //Curve��������
				ImageContainerController iCC=(ImageContainerController)containerController;
				if(checkListView_1.getCheckModel().isChecked(node)) {
					if(!iCC.dataParameters.contains((DataParameter)node)) {
						iCC.dataParameters.add((DataParameter)node);
						LogFactory.getGlobalLog().info("Add Subscribe Image Parameter! Parameter Code:"+((DataParameter)node).getCodeName());
					}
				}else {
					if(iCC.dataParameters.contains((DataParameter)node)) {
						iCC.dataParameters.remove((DataParameter)node);
						LogFactory.getGlobalLog().info("Cancel Subscribe Image Parameter! Parameter Code:"+((DataParameter)node).getCodeName());
					}
				}
			}
    	}
    	okClicked = true; 
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {      //ȡ����ť�¼�   
        dialogStage.close();
    }
	/**
	 * 1����ʼ��treeview_s����
	 * 2������treeview_s�����ĳTopic�б�ѡ����checkListView_1��ʾ��������µĲ���
	 * 3���ͻ���checklistview
	 * 4�����������ͻ���treeview_s
	 */		
    public void initData(String type) {
    	//1����ʼ��treeview_s����
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
    	//2������treeview_s�����ĳTopic�б�ѡ����checkListView_1��ʾ��������µĲ���
    	treeview_s.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				@SuppressWarnings("unchecked")
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
			    		//���������Ժ�ʵ��
			    	}	
				}
				else {
					checkListView_1.getItems().clear();
				}
			}
		});
    	//3���ͻ���checklistview
    	checkListView_1.setCellFactory(lv -> new CheckBoxListCell<IBaseNode>(checkListView_1::getItemBooleanProperty) {
    	    @Override
    	    public void updateItem(IBaseNode node, boolean empty) {
    	        super.updateItem(node, empty);
    	        setText(node == null ? "" : node.getName());
    	    }
    	});	
    	//4�����������ͻ���treeview_s
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

