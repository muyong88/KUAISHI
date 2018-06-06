package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Page;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPageController {
	
	@FXML
	private TextField page_TextField;
	@FXML
	private Label label_exist;
    private boolean okClicked = false;
    private Stage dialogStage;
    private Page page;
    private MainApp mainApp;
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setPage(Page page) {
        this.page = page;
        }
    @FXML
    private void handleOk() {
    	if(mainApp.getMainFormController().isExsitPageName(page_TextField.getText())) {
    		label_exist.setText("ÒÑ´æÔÚ!");
    		return;
    	}
    	okClicked = true;
    	page.setName(page_TextField.getText());
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
