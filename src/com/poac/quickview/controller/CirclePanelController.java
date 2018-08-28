package com.poac.quickview.controller;


import com.poac.quickview.MainApp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CirclePanelController implements IController  {
	@FXML
	private VBox vbox_circle1;              //左侧开始第一个vbox
	@FXML 
	private VBox vbox_circle2;				//左侧开始第二个vbox
	@FXML
	private VBox vbox_circle3;				//左侧开始第三个vbox
	@FXML
	private VBox vbox_circle4;				//左侧开始第四个vbox
	@FXML
	private VBox vbox_circle5;				//左侧开始第五个vbox
	@FXML
	private Label label_circleNum1;         //左侧开始第一个圈数label
	@FXML
	private Label label_circleNum2;			//左侧开始第二个圈数label
	@FXML
	private Label label_circleNum3;         //左侧开始第三个圈数label
	@FXML
	private Label label_circleNum4;			//左侧开始第四个圈数label
	@FXML
	private Label label_circleNum5;			//左侧开始第五个圈数label
	@FXML
	private Label label_mode5;				//左侧开始第五个直播/回放label
	private int realtimeCircleNum=45;    //直播圈号
	private IntegerProperty disRightCircleNum = new SimpleIntegerProperty();    //显示的五个中最右边的圈号，就是五个显示中最大的那个
	private IntegerProperty selectedCircleNum= new SimpleIntegerProperty();     //选中的圈号
    public void setMainApp(MainApp mainApp) { 
    } 
    public void setDisRightCircleNum() {        //设置五个圈数label值
    	label_circleNum1.setText(String.valueOf(disRightCircleNum.get()-4)+"圈" );
    	label_circleNum2.setText(String.valueOf(disRightCircleNum.get()-3) +"圈" );
    	label_circleNum3.setText(String.valueOf(disRightCircleNum.get()-2)+"圈"  );
    	label_circleNum4.setText(String.valueOf(disRightCircleNum.get()-1) +"圈" );
    	label_circleNum5.setText(String.valueOf(disRightCircleNum.get()) +"圈" );
    }
	/**
	 * 左边箭头按钮单击事件
	 * 1、设置label_mode5为回看，并且改变CSS样式表
	 * 2、disRightCircleNum设置-1
	 * 3、重置五个圈数label值
	 */	
    @FXML
    public void onLeftArrowBtnClick() {       
    	label_mode5.setText("回看");
    	label_mode5.getStyleClass().removeAll(label_mode5.getStyleClass());	
    	label_mode5.getStyleClass().add("replay_mode");
    	disRightCircleNum.set(disRightCircleNum.get()-1);;
    	setDisRightCircleNum();
    }
	/**
	 * 右边箭头按钮单击事件
	 * 1、如果最右边圈数等于实时圈数，就是已经滚动到了最新圈数，直接返回，否则继续
	 * 2、disRightCircleNum设置+1
	 * 3、滚动后如果最右边圈数等于实时圈数，设置label_mode5为直播，并改变label_mode5样式表
	 * 4、重置五个圈数label值
	 */	
    @FXML
    public void onRightArrowBtnClick() {
    	if(disRightCircleNum.get()==realtimeCircleNum) {           //显示最右面最多到实时圈数
    		return;
    	}
    	disRightCircleNum.set(disRightCircleNum.get()+1);;
    	if(disRightCircleNum.get()==realtimeCircleNum) {            
    		label_mode5.setText("直播");
        	label_mode5.getStyleClass().removeAll(label_mode5.getStyleClass());	
        	label_mode5.getStyleClass().add("realtime_mode");
    	}
    	setDisRightCircleNum();
    }
	/**
	 * 初始化
	 * 1、disRightCircleNum设置45，并且初始化五个圈数label值
	 * 2、 监听selectedCircleNum，disRightCircleNum，值变化时候调用selectVboxCircle（）
	 * 3、 vbox_circle1-5鼠标单击事件
	 */	
    public void init() {
    	disRightCircleNum.set(45);
    	setDisRightCircleNum();
    	selectedCircleNum.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				selectVboxCircle();
			}});
    	disRightCircleNum.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				selectVboxCircle();
			}});
    	vbox_circle1.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-4);
				}
			}});
    	vbox_circle2.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-3);
				}
			}});
    	vbox_circle3.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-2);
				}
			}});
    	vbox_circle4.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-1);
				}
			}});
    	vbox_circle5.setOnMouseClicked(new EventHandler<MouseEvent>() {           //点击时候选择这个控件
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get());			
				}
			}});
    }
	/**
	 * 根据selectedCircleNum和disRightCircleNum值各种关系设置不同样式表
	 */	
    private void selectVboxCircle() {
    	if(selectedCircleNum.get()==disRightCircleNum.get()) {
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
    	}else if(selectedCircleNum.get()==(disRightCircleNum.get()-1)) {
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
    	}else if(selectedCircleNum.get()==(disRightCircleNum.get()-2)) {
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
    	}else if(selectedCircleNum.get()==(disRightCircleNum.get()-3)) {
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
    	}else if(selectedCircleNum.get()==(disRightCircleNum.get()-4)) {
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
    	}else {
			vbox_circle1.getStyleClass().removeAll(vbox_circle1.getStyleClass());
			vbox_circle1.getStyleClass().add("unselected");
			vbox_circle2.getStyleClass().removeAll(vbox_circle1.getStyleClass());
			vbox_circle2.getStyleClass().add("unselected");
			vbox_circle3.getStyleClass().removeAll(vbox_circle3.getStyleClass());
			vbox_circle3.getStyleClass().add("unselected");
			vbox_circle4.getStyleClass().removeAll(vbox_circle4.getStyleClass());
			vbox_circle4.getStyleClass().add("unselected");
			vbox_circle5.getStyleClass().removeAll(vbox_circle5.getStyleClass());	
			vbox_circle5.getStyleClass().add("unselected");	
    	}
    }
}
