package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.DataParameter;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchListController  implements IController{
    private boolean okClicked = false;              //标记确定按钮是否被单击    
	private MainApp mainApp;                        //程序访问接口
    private Stage dialogStage;                      //本窗口
    @FXML
    private ListView<String> searchListView;                //搜索列表
	@FXML
    private void handleOk() {                       //确定按钮事件
    	okClicked = true; 
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {                   //取消按钮事件
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {       //程序访问接口
        this.mainApp = mainApp;
    }
    public boolean isOkClicked() {                  //返回okClicked值
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) { //传递窗口
        this.dialogStage = dialogStage;
    }
	/**
	 *  searchListView单击事件
	 *  双击列表项时候打开响应页面Tab，并且选择该参数行，滚动条也跟着响应滚动这个参数行位置
	 */		    
    public void init() {
    	searchListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 ) {
    	        	String[] clickText=searchListView.getSelectionModel().getSelectedItem().toString().split(":");
    	        	mainApp.getTabPaneController().openTab(clickText[0]);
    	        	TabTemplateController ttC=mainApp.getTabPaneController().tabCMap.get(clickText[0]);
    	        	IController con=ttC.containerCon.get(clickText[1]);
    	        	if(con.getClass().getName().contains("TableContainerController")) {
    	        		((TableContainerController)con).selectTableViewRow(clickText[2]);
    	    		}else if(con.getClass().getName().contains("ImageContainerController")) {
    	    			((ImageContainerController)con).selectTableViewRow(clickText[2]);
    	    		}
    	        	ttC.selectScroll(clickText[1]);
    	         }    
    	    }
    	});
    }
	/**
	 *  初始化数据
	 *  模糊匹配参数代号，并将结果显示到searchListView
	 */	
    public void initData(String paraCode) {
    	for(String key:SubscribeParameters.getSubscribeParameters().subParameterMap.keySet()) {
    		if(key.toUpperCase().contains(paraCode.toUpperCase())) {
    			DataParameter dp=SubscribeParameters.getSubscribeParameters().subParameterMap.get(key);
    	    	for(IController con:dp.subscrbeContainer) {
    	    		if(con.getClass().getName().contains("TableContainerController")) {
    	    			searchListView.getItems().add(((TableContainerController)con).getPageContainerName()+":"+key);
    	    		}else if(con.getClass().getName().contains("ImageContainerController")) {
    	    			searchListView.getItems().add(((ImageContainerController)con).getPageContainerName()+":"+key);
    	    		}
    	    	}
    		}
    	}
    }
}
