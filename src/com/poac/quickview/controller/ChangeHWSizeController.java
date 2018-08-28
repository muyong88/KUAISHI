package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeHWSizeController implements IController {
	
	@FXML
	private TextField txt_Width;   			//容器宽度文本框
	@FXML									
	private TextField txt_Height;			//容器高度文本框
    private boolean okClicked = false;      //ok按钮点击状态，默认false
    private Stage dialogStage;     
    private Container container;            //容器模型
    public boolean isOkClicked() {          //返回ok按钮点击状态
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	/**
	 * 1、重置okClicked为true
	 * 2、设置container模型
	 */	
    @FXML
    private void handleOk() {
    	okClicked = true;
    	container.setWidth(txt_Width.getText());;
    	container.setHeight(txt_Height.getText());
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {        //取消按钮事件，关闭对话框
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
    }
    public void setContainer(Container container) {       //传递容器模型
    	this.container=container;
    }
}
