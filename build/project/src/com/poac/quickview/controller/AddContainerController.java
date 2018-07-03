package com.poac.quickview.controller;

import com.poac.quickview.MainApp;

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
    	ObservableList<String> options =FXCollections.observableArrayList("Data","Curve","Image","Video");
    	combox_type.getItems().addAll(options);
    	combox_type.setValue("Data");
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
    		label_error.setText("容器名已存在！");
    		return;
    	}
    	if(!combox_type.getValue().equals("Data")) {
    		label_error.setText("请选择Data类型！");
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

