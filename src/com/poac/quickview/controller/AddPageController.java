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
	private TextField page_TextField;             //page���ı���
	@FXML
	private Label label_exist;                    //page�Ƿ����label,�Ѵ��ڵ�ʱ����ʾ
    private boolean okClicked = false;            //���ok��ť�Ƿ񱻵��
    private Stage dialogStage;
    private Page page;                            //pageģ��
    private MainApp mainApp;                      //������ʽӿ���
    public boolean isOkClicked() {                //����ok��ť�Ƿ񱻵��
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setPage(Page page) {              //����pageģ��
        this.page = page;
        }
	/**
	 * 1���ж�page���Ƿ����
	 * 2���ж�page�ı����Ƿ�Ϊ��
	 * 3������okClickedΪtrue
	 * 4������pageģ��
	 */	
    @FXML
    private void handleOk() {                      
    	if(mainApp.getMainFormController().isExsitPageName(page_TextField.getText())) {
    		label_exist.setText("ҳ���Ѵ���!");
    		return;
    	}
    	if(page_TextField.getText().equals("")) {
    		label_exist.setText("ҳ�治��Ϊ��!");
    		return;
    	}
    	LogFactory.getGlobalLog().info("Add Page: "+page_TextField.getText());
    	okClicked = true;
    	page.setName(page_TextField.getText());
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {         //ȡ����ť�¼�
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
