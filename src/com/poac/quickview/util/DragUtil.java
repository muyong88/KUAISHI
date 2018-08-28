package com.poac.quickview.util;

import com.poac.quickview.MainApp;

import javafx.scene.layout.AnchorPane;

public class DragUtil {
	public static void addDragListener(AnchorPane anchorP,MainApp mainApp,String pageName) {
		new DragListener(anchorP,mainApp,pageName);
	}
}
