package com.poac.quickview.controller;

import java.io.PrintStream;

import com.poac.quickview.MainApp;
import com.poac.quickview.util.TextAreaConsole;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class TabTemplateLogController implements IController {
	@FXML
	private TextArea log_TextArea;
    private MainApp mainApp; 
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
    public void init() {
//    	TextAreaConsole console = new TextAreaConsole(log_TextArea);
//        PrintStream ps = new PrintStream(console, true);
//        System.setOut(ps);
//        System.setErr(ps);
    }    
}
