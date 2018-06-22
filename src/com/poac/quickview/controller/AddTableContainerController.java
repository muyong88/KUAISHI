package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTableContainerController  implements IController {
	@FXML
	private TextField txtf_Name;
	@FXML
	private TextField txt_PositionX;
	@FXML
	private TextField txt_PositionY;
	@FXML
	private TextField txt_Width;
	@FXML
	private TextField txt_Height;
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
    public double getPositionX() {
    	return Double.parseDouble(txt_PositionX.getText());
    }
    public double getPositionY() {
    	return Double.parseDouble(txt_PositionY.getText());
    }
    public double getWidth() {
    	return Double.parseDouble(txt_Width.getText());
    }
    public double getHeight() {
    	return Double.parseDouble(txt_Height.getText());
    }
}



