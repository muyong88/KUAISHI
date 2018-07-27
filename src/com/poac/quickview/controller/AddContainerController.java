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
	private ComboBox combox_type;
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
    public AddContainerController() { 
    }
    public void init() {
    	ObservableList<String> options =FXCollections.observableArrayList
    			(GlobalVariable.data,GlobalVariable.curve,GlobalVariable.image,GlobalVariable.video);
    	combox_type.getItems().addAll(options);
    	combox_type.setValue(GlobalVariable.data);
    }
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
    public String getType() {
    	return combox_type.getValue().toString();
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

