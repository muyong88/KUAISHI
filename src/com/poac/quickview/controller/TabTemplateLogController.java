package com.poac.quickview.controller;


import java.io.PrintStream;

import com.poac.quickview.MainApp;
import com.poac.quickview.util.TextAreaConsole;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TabTemplateLogController implements IController {
	@FXML
	private TextArea log_TextArea;               //日志textarea
    public void setMainApp(MainApp mainApp) {   
    } 
    //把console输出设置为log_TextArea
    public void init() {
    	TextAreaConsole console = new TextAreaConsole(log_TextArea);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
    }    
}
