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
	private ComboBox<String> combox_type;       //容器类型下拉框
	@FXML
	private TextField txtf_Name;        //容器名称文本框
	@FXML
	private TextField txt_Width;        //容器宽度
	@FXML
	private TextField txt_Height;       //容器高度
	@FXML
	private Label label_error;          //重名或者为空显示错误
    private boolean okClicked = false;  //OK按钮是否被点击
    private Stage dialogStage;
    private MainApp mainApp;            //程序访问接口类
    private String pageName;            //容器所属Page名
    public AddContainerController() { 
    }
	/**
	 * 初始化下拉框数据，并默认选择data类型
	 */	
    public void initData() {
    	ObservableList<String> options =FXCollections.observableArrayList
    			(GlobalVariable.data,GlobalVariable.curve,GlobalVariable.image,GlobalVariable.video);
    	combox_type.getItems().addAll(options);
    	combox_type.setValue(GlobalVariable.data);
    }
    public boolean isOkClicked() {   //返回确定按钮是否被点击
        return okClicked;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setPageName(String pageName) {   //设置page名
    	this.pageName=pageName;
    }
    @FXML
    private void handleOk() {          //设置okClieck为true
    	if(mainApp.getTabPaneController().isExsitContainerName(pageName, getConName())) {
    		label_error.setText("容器名已存在！");
    		return;
    	}
    	okClicked = true;
    	dialogStage.close();        
    }
    @FXML
    private void handleCancel() {           //关闭窗口
        dialogStage.close();
    }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public String getType() {                 //获取容器类型
    	return combox_type.getValue().toString();
    }
    public String getConName() {              //获取容器名
    	return txtf_Name.getText();
    }
    public double getWidth() {                //获取容器宽度
    	return Double.parseDouble(txt_Width.getText());
    } 
    public double getHeight() {               //获取容器高度
    	return Double.parseDouble(txt_Height.getText());
    }
}

