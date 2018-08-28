package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeHWSizeController implements IController {
	
	@FXML
	private TextField txt_Width;   			//��������ı���
	@FXML									
	private TextField txt_Height;			//�����߶��ı���
    private boolean okClicked = false;      //ok��ť���״̬��Ĭ��false
    private Stage dialogStage;     
    private Container container;            //����ģ��
    public boolean isOkClicked() {          //����ok��ť���״̬
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	/**
	 * 1������okClickedΪtrue
	 * 2������containerģ��
	 */	
    @FXML
    private void handleOk() {
    	okClicked = true;
    	container.setWidth(txt_Width.getText());;
    	container.setHeight(txt_Height.getText());
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {        //ȡ����ť�¼����رնԻ���
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
    }
    public void setContainer(Container container) {       //��������ģ��
    	this.container=container;
    }
}
