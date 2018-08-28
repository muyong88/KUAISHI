package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Page;
import com.poac.quickview.util.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPageController implements IController {
	
	@FXML
	private TextField page_TextField;             //page名文本框
	@FXML
	private Label label_exist;                    //page是否存在label,已存在的时候显示
    private boolean okClicked = false;            //标记ok按钮是否被点击
    private Stage dialogStage;
    private Page page;                            //page模型
    private MainApp mainApp;                      //程序访问接口类
    public boolean isOkClicked() {                //返回ok按钮是否被点击
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setPage(Page page) {              //设置page模型
        this.page = page;
        }
	/**
	 * 1、判断page名是否存在
	 * 2、判断page文本框是否为空
	 * 3、重置okClicked为true
	 * 4、设置page模型
	 */	
    @FXML
    private void handleOk() {                      
    	if(mainApp.getMainFormController().isExsitPageName(page_TextField.getText())) {
    		label_exist.setText("页面已存在!");
    		return;
    	}
    	if(page_TextField.getText().equals("")) {
    		label_exist.setText("页面不可为空!");
    		return;
    	}
    	LogFactory.getGlobalLog().info("Add Page: "+page_TextField.getText());
    	okClicked = true;
    	page.setName(page_TextField.getText());
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {         //取消按钮事件
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
