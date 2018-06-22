package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeHWSizeController implements IController {
	
	@FXML
	private TextField txt_Width;
	@FXML
	private TextField txt_Height;
    private boolean okClicked = false;
    private Stage dialogStage;
    private MainApp mainApp;
    private Container container;
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleOk() {
    	okClicked = true;
    	container.setWidth(txt_Width.getText());;
    	container.setHeight(txt_Height.getText());
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setContainer(Container container) {
    	this.container=container;
    }
}
