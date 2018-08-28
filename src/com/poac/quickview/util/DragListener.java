package com.poac.quickview.util;

import com.poac.quickview.MainApp;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class DragListener  implements EventHandler<MouseEvent> {

	private double xOffset = 0;                           //鼠标按下时x坐标 (移动)
	private double yOffset = 0;							  //鼠标按下时y坐标（移动）
    private AnchorPane anchorP;
    private String pageName;
    private MainApp mainApp;
    private  int RESIZE_MARGIN = 5;                       //拖拽边框MARGIN范围
    private double x;                                     //鼠标按下或者移动x坐标
    private double y;                                     //鼠标按下或者移动x坐标
    private int dragging=0;                               //0代表不拉 1代表横拉  2代表竖拉 3代表斜拉
    private boolean needRefresh=false;
    
    public DragListener(AnchorPane anchorP,MainApp mainApp,String pageName) {
    	this.anchorP = anchorP;
    	this.mainApp=mainApp;
    	this.pageName=pageName;
    	anchorP.setOnMousePressed(this);
    	anchorP.setOnMouseDragged(this);
    	anchorP.setOnMouseReleased(this);
    	anchorP.setOnMouseMoved(this);
    }


    @Override
    public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			if (!(event.getY() > (anchorP.getHeight() - RESIZE_MARGIN))
					&& !(event.getX() > (anchorP.getWidth() - RESIZE_MARGIN))) {
				xOffset = event.getX();
				yOffset = event.getY();
				return;
			}
	        x = event.getX();
	        y = event.getY();
            if((y > (anchorP.getHeight() - RESIZE_MARGIN))&&
            		(x > (anchorP.getWidth() - RESIZE_MARGIN))) {
            	dragging = 3;
            }else if(x > (anchorP.getWidth() - RESIZE_MARGIN)) {
				dragging = 1;
			}else if(event.getY() > (anchorP.getHeight() - RESIZE_MARGIN)) {
				dragging = 2;
			}
		} else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			needRefresh=true;
			if (dragging == 0) {
				double xTemp=anchorP.getLayoutX()+event.getX() - xOffset;
				double yTemp=anchorP.getLayoutY()+event.getY() - yOffset;
				anchorP.setLayoutX(xTemp);
				anchorP.setLayoutY(yTemp);
				mainApp.getTabPaneController().getTabTemplateController(pageName).setScrollVaule(yTemp);
				return;
			} else if (dragging == 1) {
				double mousex = event.getX();
				double newWidth = anchorP.getPrefWidth() + (mousex - x);
				anchorP.setPrefWidth(newWidth);
				x = mousex;
			}else if (dragging == 2) {
				double mousey = event.getY();
				double newHeight = anchorP.getPrefHeight() + (mousey - y);
				anchorP.setPrefHeight(newHeight);
				y = mousey;
			} else if(dragging == 3) {
				double mousex = event.getX();
				double mousey = event.getY();
				double newWidth = anchorP.getPrefWidth() + (mousex - x);
				double newHeight = anchorP.getPrefHeight() + (mousey - y);
				anchorP.setPrefWidth(newWidth);
				anchorP.setPrefHeight(newHeight);
				x = mousex;
				y = mousey;
			}
        }else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            if((event.getY() > (anchorP.getHeight() - RESIZE_MARGIN))&&
            		(event.getX() > (anchorP.getWidth() - RESIZE_MARGIN))) {
            	anchorP.setCursor(Cursor.NW_RESIZE);
            }else if((event.getY() > (anchorP.getHeight() - RESIZE_MARGIN))) {
            	anchorP.setCursor(Cursor.S_RESIZE);
            }else if((event.getX() > (anchorP.getWidth() - RESIZE_MARGIN))) {
            	anchorP.setCursor(Cursor.H_RESIZE);
            }
            else {
            	anchorP.setCursor(Cursor.DEFAULT);
            }
        }else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            dragging = 0;
            anchorP.setCursor(Cursor.DEFAULT);   
			if (needRefresh) {
				mainApp.getTabPaneController().refresh(pageName);
			}
			needRefresh=false;
        }
		
    }

}
