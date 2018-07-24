package com.poac.quickview.controller;


import com.poac.quickview.MainApp;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CirclePanelController implements IController  {
	@FXML
	private VBox vbox_circle1;
	@FXML 
	private VBox vbox_circle2;
	@FXML
	private VBox vbox_circle3;
	@FXML
	private VBox vbox_circle4;
	@FXML
	private VBox vbox_circle5;
	@FXML
	private Label label_circleNum1;
	@FXML
	private Label label_circleNum2;
	@FXML
	private Label label_circleNum3;
	@FXML
	private Label label_circleNum4;
	@FXML
	private Label label_circleNum5;
	@FXML
	private Label label_mode5;
	private MainApp mainApp; 	
	private int realtimeCircleNum=45;    //直播圈号
	private int disRightCircleNum=45;    //显示的五个中最右边的圈号，就是五个显示中最大的那个
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp; 
    } 
    public void setDisRightCircleNum(int num) {        //设置五个中最大圈数
    	label_circleNum1.setText(String.valueOf(num-4)+"圈" );
    	label_circleNum2.setText(String.valueOf(num-3) +"圈" );
    	label_circleNum3.setText(String.valueOf(num-2)+"圈"  );
    	label_circleNum4.setText(String.valueOf(num-1) +"圈" );
    	label_circleNum5.setText(String.valueOf(num) +"圈" );
    }
    @FXML
    public void onLeftArrowBtnClick() {
    	label_mode5.setText("回放");
    	label_mode5.getStyleClass().removeAll(label_mode5.getStyleClass());	
    	label_mode5.getStyleClass().add("replay_mode");
    	disRightCircleNum--;
    	setDisRightCircleNum(disRightCircleNum);
    }
    @FXML
    public void onRightArrowBtnClick() {
    	if(disRightCircleNum==realtimeCircleNum) {
    		return;
    	}
    	disRightCircleNum++;
    	if(disRightCircleNum==realtimeCircleNum) {
    		label_mode5.setText("直播");
        	label_mode5.getStyleClass().removeAll(label_mode5.getStyleClass());	
        	label_mode5.getStyleClass().add("realtime_mode");
    	}
    	setDisRightCircleNum(disRightCircleNum);
    }
    public void init() {
    	setDisRightCircleNum(disRightCircleNum);
    	vbox_circle1.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					vbox_circle1.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle1.getStyleClass().add("selected");
					vbox_circle2.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle2.getStyleClass().add("unselected");
					vbox_circle3.getStyleClass().removeAll(vbox_circle3.getStyleClass());
					vbox_circle3.getStyleClass().add("unselected");
					vbox_circle4.getStyleClass().removeAll(vbox_circle4.getStyleClass());
					vbox_circle4.getStyleClass().add("unselected");
					vbox_circle5.getStyleClass().removeAll(vbox_circle5.getStyleClass());	
					vbox_circle5.getStyleClass().add("unselected");	
				}
			}});
    	vbox_circle2.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					vbox_circle1.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle1.getStyleClass().add("unselected");
					vbox_circle2.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle2.getStyleClass().add("selected");
					vbox_circle3.getStyleClass().removeAll(vbox_circle3.getStyleClass());
					vbox_circle3.getStyleClass().add("unselected");
					vbox_circle4.getStyleClass().removeAll(vbox_circle4.getStyleClass());
					vbox_circle4.getStyleClass().add("unselected");
					vbox_circle5.getStyleClass().removeAll(vbox_circle5.getStyleClass());	
					vbox_circle5.getStyleClass().add("unselected");
				}
			}});
    	vbox_circle3.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					vbox_circle1.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle1.getStyleClass().add("unselected");
					vbox_circle2.getStyleClass().removeAll(vbox_circle2.getStyleClass());
					vbox_circle2.getStyleClass().add("unselected");
					vbox_circle3.getStyleClass().removeAll(vbox_circle3.getStyleClass());
					vbox_circle3.getStyleClass().add("selected");
					vbox_circle4.getStyleClass().removeAll(vbox_circle4.getStyleClass());
					vbox_circle4.getStyleClass().add("unselected");
					vbox_circle5.getStyleClass().removeAll(vbox_circle5.getStyleClass());	
					vbox_circle5.getStyleClass().add("unselected");
				}
			}});
    	vbox_circle4.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					vbox_circle1.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle1.getStyleClass().add("unselected");
					vbox_circle2.getStyleClass().removeAll(vbox_circle2.getStyleClass());
					vbox_circle2.getStyleClass().add("unselected");
					vbox_circle3.getStyleClass().removeAll(vbox_circle3.getStyleClass());
					vbox_circle3.getStyleClass().add("unselected");
					vbox_circle4.getStyleClass().removeAll(vbox_circle4.getStyleClass());
					vbox_circle4.getStyleClass().add("selected");
					vbox_circle5.getStyleClass().removeAll(vbox_circle5.getStyleClass());	
					vbox_circle5.getStyleClass().add("unselected");
				}
			}});
    	vbox_circle5.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					vbox_circle1.getStyleClass().removeAll(vbox_circle1.getStyleClass());
					vbox_circle1.getStyleClass().add("unselected");
					vbox_circle2.getStyleClass().removeAll(vbox_circle2.getStyleClass());
					vbox_circle2.getStyleClass().add("unselected");
					vbox_circle3.getStyleClass().removeAll(vbox_circle3.getStyleClass());
					vbox_circle3.getStyleClass().add("unselected");
					vbox_circle4.getStyleClass().removeAll(vbox_circle4.getStyleClass());
					vbox_circle4.getStyleClass().add("unselected");
					vbox_circle5.getStyleClass().removeAll(vbox_circle5.getStyleClass());	
					vbox_circle5.getStyleClass().add("selected");				
				}
			}});
    }
}
