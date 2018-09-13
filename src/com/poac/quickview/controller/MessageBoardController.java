package com.poac.quickview.controller;

import com.poac.quickview.MainApp;
import com.poac.quickview.util.LogFactory;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;


public class MessageBoardController implements IController{
	@FXML
	private AnchorPane messageBoardAnchorpane;
	private MainApp mainApp;                        //������ʽӿ�
	@Override
	public void setMainApp(MainApp mainApp) {
		// TODO Auto-generated method stub
		this.mainApp = mainApp;
	}
    @FXML
    private void handleClose() {                   //�رհ�ť�¼�
        mainApp.getTabPaneController().removeMessageBoard(messageBoardAnchorpane);
        LogFactory.getGlobalLog().info("�ر����԰�");
    }
    @FXML
    private void handleAdd() {                   //���Ӱ�ť�¼�
        mainApp.addMessageBoard();
        LogFactory.getGlobalLog().info("�������԰�");
    }
}
