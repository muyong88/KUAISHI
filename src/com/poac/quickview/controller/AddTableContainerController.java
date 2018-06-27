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
	private TextField txt_Width;
	@FXML
	private TextField txt_Height;
	@FXML
	private Label label_error;
    private boolean okClicked = false;
    private Stage dialogStage;
    private MainApp mainApp;
    private String pageName;
    public boolean isOkClicked() {
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setPageName(String pageName) {
    	this.pageName=pageName;
    }
    @FXML
    private void handleOk() {
    	if(mainApp.getTabPaneController().isExsitContainerName(pageName, getConName())) {
    		label_error.setText("ÈÝÆ÷ÃûÒÑ´æÔÚ£¡");
    		return;
    	}
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
    public double getWidth() {
    	return Double.parseDouble(txt_Width.getText());
    }
    public double getHeight() {
    	return Double.parseDouble(txt_Height.getText());
    }
}


