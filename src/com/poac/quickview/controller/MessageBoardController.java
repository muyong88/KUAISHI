package com.poac.quickview.controller;

import com.poac.quickview.MainApp;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class MessageBoardController implements IController{
	@FXML
	private AnchorPane messageBoardAnchorpane;
	private MainApp mainApp;                        //程序访问接口
	@Override
	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
		this.mainApp = mainApp;
	}
    @FXML
    private void handleClose() {                   //关闭按钮事件
        mainApp.getTabPaneController().removeMessageBoard(messageBoardAnchorpane);
    }
    @FXML
    private void handleAdd() {                   //增加按钮事件
        mainApp.addMessageBoard();
    }
}
