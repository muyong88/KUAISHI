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
	private TreeView<IBaseNode> treeview_s;                                 //树状控件
	@FXML
	private CheckListView<IBaseNode>  checkListView_1;           //复选框列表
    private boolean okClicked = false;                           //标记确定按钮是否被单击
    private Stage dialogStage;                                   //本窗口
    private String subType;  									 //订阅类型
    private IController containerController=null;  				 //调用这个订阅的容器Controller
    //设置程序访问接口
    public void setMainApp(MainApp mainApp) {
    }
    //返回okClicked
    public boolean isOkClicked() {
        return okClicked;
    }
    //传递窗口
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    //设置调用这个订阅的容器控制器
    public void setContainerController(IController con) {
    	containerController=con;
    }
	/**
	 * 确定按钮事件
	 * 如果列表中某项被选择，容器中没有被订阅，则添加这个订阅
	 * 如果列表中某项没被选择，容器中已经被订阅，则取消这个订阅
	 */		
    @FXML
    private void handleOk() {             
    	for(IBaseNode node :checkListView_1.getItems()) {
			if (subType.equals(GlobalVariable.data)) {   //Table容器订阅
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
			}else if(subType.equals(GlobalVariable.curve)) {   //Curve容器订阅
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
			}else if(subType.equals(GlobalVariable.image)) {   //Curve容器订阅
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
    private void handleCancel() {      //取消按钮事件   
        dialogStage.close();
    }
	/**
	 * 1、初始化treeview_s数据
	 * 2、监听treeview_s，如果某Topic行被选择，则checkListView_1显示这个主题下的参数
	 * 3、客户化checklistview
	 * 4、工厂方法客户化treeview_s
	 */		
    public void initData(String type) {
    	//1、初始化treeview_s数据
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
    	//2、监听treeview_s，如果某Topic行被选择，则checkListView_1显示这个主题下的参数
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
			    		//其他类型以后实现
			    	}	
				}
				else {
					checkListView_1.getItems().clear();
				}
			}
		});
    	//3、客户化checklistview
    	checkListView_1.setCellFactory(lv -> new CheckBoxListCell<IBaseNode>(checkListView_1::getItemBooleanProperty) {
    	    @Override
    	    public void updateItem(IBaseNode node, boolean empty) {
    	        super.updateItem(node, empty);
    	        setText(node == null ? "" : node.getName());
    	    }
    	});	
    	//4、工厂方法客户化treeview_s
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

