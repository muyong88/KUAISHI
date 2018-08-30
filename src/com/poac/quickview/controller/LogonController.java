package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogonController implements IController {
	
	@FXML
	private TextField txt_UserName;                         //用户名文本框
	@FXML
	private TextField txt_Password;                         //密码文本框
    private boolean okClicked = false;                      //用于标记确定按钮是否被单击
    private Stage dialogStage;                              //本窗口（舞台）
    public boolean isOkClicked() {                          //返回okClicked值
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {          //传递这个窗口
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleOk() {                                //确定按钮事件
    	okClicked = true;
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {                             //取消按钮事件
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {                 //设置程序访问接口
        
    }
}
