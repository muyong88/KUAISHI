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
	private VBox vbox_circle1;              //��࿪ʼ��һ��vbox
	@FXML 
	private VBox vbox_circle2;				//��࿪ʼ�ڶ���vbox
	@FXML
	private VBox vbox_circle3;				//��࿪ʼ������vbox
	@FXML
	private VBox vbox_circle4;				//��࿪ʼ���ĸ�vbox
	@FXML
	private VBox vbox_circle5;				//��࿪ʼ�����vbox
	@FXML
	private Label label_circleNum1;         //��࿪ʼ��һ��Ȧ��label
	@FXML
	private Label label_circleNum2;			//��࿪ʼ�ڶ���Ȧ��label
	@FXML
	private Label label_circleNum3;         //��࿪ʼ������Ȧ��label
	@FXML
	private Label label_circleNum4;			//��࿪ʼ���ĸ�Ȧ��label
	@FXML
	private Label label_circleNum5;			//��࿪ʼ�����Ȧ��label
	@FXML
	private Label label_mode5;				//��࿪ʼ�����ֱ��/�ط�label
	private int realtimeCircleNum=45;    //ֱ��Ȧ��
	private IntegerProperty disRightCircleNum = new SimpleIntegerProperty();    //��ʾ����������ұߵ�Ȧ�ţ����������ʾ�������Ǹ�
	private IntegerProperty selectedCircleNum= new SimpleIntegerProperty();     //ѡ�е�Ȧ��
    public void setMainApp(MainApp mainApp) { 
    } 
    public void setDisRightCircleNum() {        //�������Ȧ��labelֵ
    	label_circleNum1.setText(String.valueOf(disRightCircleNum.get()-4)+"Ȧ" );
    	label_circleNum2.setText(String.valueOf(disRightCircleNum.get()-3) +"Ȧ" );
    	label_circleNum3.setText(String.valueOf(disRightCircleNum.get()-2)+"Ȧ"  );
    	label_circleNum4.setText(String.valueOf(disRightCircleNum.get()-1) +"Ȧ" );
    	label_circleNum5.setText(String.valueOf(disRightCircleNum.get()) +"Ȧ" );
    }
	/**
	 * ��߼�ͷ��ť�����¼�
	 * 1������label_mode5Ϊ�ؿ������Ҹı�CSS��ʽ��
	 * 2��disRightCircleNum����-1
	 * 3���������Ȧ��labelֵ
	 */	
    @FXML
    public void onLeftArrowBtnClick() {       
    	label_mode5.setText("�ؿ�");
    	label_mode5.getStyleClass().removeAll(label_mode5.getStyleClass());	
    	label_mode5.getStyleClass().add("replay_mode");
    	disRightCircleNum.set(disRightCircleNum.get()-1);;
    	setDisRightCircleNum();
    }
	/**
	 * �ұ߼�ͷ��ť�����¼�
	 * 1��������ұ�Ȧ������ʵʱȦ���������Ѿ�������������Ȧ����ֱ�ӷ��أ��������
	 * 2��disRightCircleNum����+1
	 * 3��������������ұ�Ȧ������ʵʱȦ��������label_mode5Ϊֱ�������ı�label_mode5��ʽ��
	 * 4���������Ȧ��labelֵ
	 */	
    @FXML
    public void onRightArrowBtnClick() {
    	if(disRightCircleNum.get()==realtimeCircleNum) {           //��ʾ��������ൽʵʱȦ��
    		return;
    	}
    	disRightCircleNum.set(disRightCircleNum.get()+1);;
    	if(disRightCircleNum.get()==realtimeCircleNum) {            
    		label_mode5.setText("ֱ��");
        	label_mode5.getStyleClass().removeAll(label_mode5.getStyleClass());	
        	label_mode5.getStyleClass().add("realtime_mode");
    	}
    	setDisRightCircleNum();
    }
	/**
	 * ��ʼ��
	 * 1��disRightCircleNum����45�����ҳ�ʼ�����Ȧ��labelֵ
	 * 2�� ����selectedCircleNum��disRightCircleNum��ֵ�仯ʱ�����selectVboxCircle����
	 * 3�� vbox_circle1-5��굥���¼�
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
    	vbox_circle1.setOnMouseClicked(new EventHandler<MouseEvent>() {           //���ʱ��ѡ������ؼ�
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-4);
				}
			}});
    	vbox_circle2.setOnMouseClicked(new EventHandler<MouseEvent>() {           //���ʱ��ѡ������ؼ�
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-3);
				}
			}});
    	vbox_circle3.setOnMouseClicked(new EventHandler<MouseEvent>() {           //���ʱ��ѡ������ؼ�
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-2);
				}
			}});
    	vbox_circle4.setOnMouseClicked(new EventHandler<MouseEvent>() {           //���ʱ��ѡ������ؼ�
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get()-1);
				}
			}});
    	vbox_circle5.setOnMouseClicked(new EventHandler<MouseEvent>() {           //���ʱ��ѡ������ؼ�
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (MouseButton.PRIMARY.equals(event.getButton())) {
					selectedCircleNum.set(disRightCircleNum.get());			
				}
			}});
    }
	/**
	 * ����selectedCircleNum��disRightCircleNumֵ���ֹ�ϵ���ò�ͬ��ʽ��
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
