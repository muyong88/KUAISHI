package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.global.GlobalVariable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContainerController  implements IController {
	@FXML
	private ComboBox<String> combox_type;       //��������������
	@FXML
	private TextField txtf_Name;        //���������ı���
	@FXML
	private TextField txt_Width;        //�������
	@FXML
	private TextField txt_Height;       //�����߶�
	@FXML
	private Label label_error;          //��������Ϊ����ʾ����
    private boolean okClicked = false;  //OK��ť�Ƿ񱻵��
    private Stage dialogStage;
    private MainApp mainApp;            //������ʽӿ���
    private String pageName;            //��������Page��
    public AddContainerController() { 
    }
	/**
	 * ��ʼ�����������ݣ���Ĭ��ѡ��data����
	 */	
    public void initData() {
    	ObservableList<String> options =FXCollections.observableArrayList
    			(GlobalVariable.data,GlobalVariable.curve,GlobalVariable.image,GlobalVariable.video);
    	combox_type.getItems().addAll(options);
    	combox_type.setValue(GlobalVariable.data);
    }
    public boolean isOkClicked() {   //����ȷ����ť�Ƿ񱻵��
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setPageName(String pageName) {   //����page��
    	this.pageName=pageName;
    }
    @FXML
    private void handleOk() {          //����okClieckΪtrue
    	if(mainApp.getTabPaneController().isExsitContainerName(pageName, getConName())) {
    		label_error.setText("�������Ѵ��ڣ�");
    		return;
    	}
    	okClicked = true;
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {           //�رմ���
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public String getType() {                 //��ȡ��������
    	return combox_type.getValue().toString();
    }
    public String getConName() {              //��ȡ������
    	return txtf_Name.getText();
    }
    public double getWidth() {                //��ȡ�������
    	return Double.parseDouble(txt_Width.getText());
    } 
    public double getHeight() {               //��ȡ�����߶�
    	return Double.parseDouble(txt_Height.getText());
    }
}

