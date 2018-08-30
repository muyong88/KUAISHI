package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogonController implements IController {
	
	@FXML
	private TextField txt_UserName;                         //�û����ı���
	@FXML
	private TextField txt_Password;                         //�����ı���
    private boolean okClicked = false;                      //���ڱ��ȷ����ť�Ƿ񱻵���
    private Stage dialogStage;                              //�����ڣ���̨��
    public boolean isOkClicked() {                          //����okClickedֵ
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {          //�����������
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleOk() {                                //ȷ����ť�¼�
    	okClicked = true;
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {                             //ȡ����ť�¼�
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {                 //���ó�����ʽӿ�
        
    }
}
