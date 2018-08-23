package com.poac.quickview.util;

import java.io.IOException;
import java.io.OutputStream;

import com.poac.quickview.MainApp;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class TextAreaConsole extends OutputStream{
    private TextArea    output;
    public TextAreaConsole(TextArea ta)
    {
        this.output = ta;
    }

    @Override
    public void write(int i) throws IOException
    {
        Platform.runLater(new Runnable() {
            public void run() {
                output.appendText(String.valueOf((char) i));
            }
        });
    }
}
