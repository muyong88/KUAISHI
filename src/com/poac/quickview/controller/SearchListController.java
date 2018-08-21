package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.GlobalVariable;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchListController  implements IController{
    private boolean okClicked = false;
	private MainApp mainApp; 
    private Stage dialogStage;
    @FXML
    private ListView searchListView;
	@FXML
    private void handleOk() {
    	okClicked = true; 
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
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
