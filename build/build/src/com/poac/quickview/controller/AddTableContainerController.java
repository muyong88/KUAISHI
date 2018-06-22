package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTableContainerController  implements IController {
	@FXML
	private TextField txtf_Name;
    private boolean okClicked = false;
    private Stage dialogStage;
    private MainApp mainApp;    
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
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
    public String getConName() {
    	return txtf_Name.getText();
    }
}
