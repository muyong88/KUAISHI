  package com.poac.quickview.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
public class VideoContainerController implements IController {
	@FXML
	private AnchorPane anchor_mediaview;
	@FXML
	private MediaView  mediaView;
	@FXML
	private Label label_head;
	@FXML
	private AnchorPane anchor_video;
	@FXML
	private Slider Slider_videoProgress; 
	@FXML
	private  ProgressBar ProgressBar_videoProgress; 
	@FXML
	private Button Button_pause;
	@FXML
	private HBox hBox_Circles;
	private MediaPlayer mediaPlayer;
	private MainApp mainApp; 	
	private String pageName=null;
    private String containerName=null;
	private double xOffset = 0;
	private double yOffset = 0;
    private  int RESIZE_MARGIN = 5;
    private int dragging=0;     //0代表不拉 1代表横拉  2代表竖拉 3代表斜拉
    private int current_state=0; //0代表播放 1代表暂停
    private double x;
    private double y;
    private ContextMenu addMenu1 = new ContextMenu();    
	public VideoContainerController() {
	}   
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
    private IController getThis() {
    	return this;
    }
    public void init() {
        MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	if(mainApp.showSubscribe("video",getThis())) {            		
            	}
            }
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container))
            		setContainerSize(container.getWidth(), container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
            }
        });
        MenuItem addMenuItem3 = new MenuItem("删除容器");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	mainApp.getTabPaneController().refresh(pageName);
            }
        });  
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	mediaView.setPreserveRatio(false);               //宽高自动变化
    	mediaView.fitWidthProperty().bind(anchor_video.widthProperty());    //绑定宽随父节点变化
    	mediaView.fitHeightProperty().bind(anchor_video.heightProperty());  //绑定高随父节点变化
    	Slider_videoProgress.setOnMouseDragExited(e ->{
			double d = Slider_videoProgress.getValue();
			ProgressBar_videoProgress.setProgress(d/100);
		});
    	Slider_videoProgress.valueProperty().addListener((o, ov, nv)->{   //拖拉进步条视频同步
    		ProgressBar_videoProgress.setProgress(Slider_videoProgress.getValue()/100);
		});    	
    	ProgressBar_videoProgress.progressProperty().addListener((o, ov, nv)->{  
    		mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(ProgressBar_videoProgress.progressProperty().get()));
		});
    	String media_URL = getClass().getResource("/animal.mp4").toString();
    	Media media = new Media(media_URL);
    	mediaPlayer = new MediaPlayer(media);
    	mediaPlayer.setAutoPlay(true); //设置自动播放    
		mediaPlayer.setOnEndOfMedia(new Runnable() {            //设置video播放结束后事件内容
		    @Override
		    public void run() {
				current_state=1;
				Button_pause.getStyleClass().removeAll(Button_pause.getStyleClass());
				Button_pause.getStyleClass().add("buttonPlay");
				mediaPlayer.stop();
		    }
		});
		mediaPlayer.currentTimeProperty().addListener((o, ov, nv)->{
			double n=nv.toSeconds()/mediaPlayer.getTotalDuration().toSeconds()*100;
			Slider_videoProgress.setValue(n);
		});		
    	mediaView.setMediaPlayer(mediaPlayer);   
    	
    	
    	anchor_mediaview.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_mediaview, event.getScreenX(), event.getScreenY());
            }else {
          	  addMenu1.hide();
            }
          }         
        });
        anchor_mediaview.setOnMousePressed(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_mediaview.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_mediaview.getWidth() - RESIZE_MARGIN))) {     //判断不改变大小范围
					xOffset = event.getX();
					yOffset = event.getY();	
					 return;
				}
                if((event.getY() > (anchor_mediaview.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_mediaview.getWidth() - RESIZE_MARGIN))) {
                	dragging = 3;
                }else if(event.getX() > (anchor_mediaview.getWidth() - RESIZE_MARGIN)) {
					dragging = 1;
				}else if(event.getY() > (anchor_mediaview.getHeight() - RESIZE_MARGIN)) {
					dragging = 2;
				}
		        x = event.getX();
		        y = event.getY();
			}
		});
        anchor_mediaview.setOnMouseDragged(new EventHandler<MouseEvent>() {       //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (dragging == 0) {
					x=anchor_mediaview.getLayoutX()+event.getX() - xOffset;
					y=anchor_mediaview.getLayoutY()+event.getY() - yOffset;
					anchor_mediaview.setLayoutX(x);
					anchor_mediaview.setLayoutY(y);
					mainApp.getTabPaneController().getTabTemplateController(pageName).setScrollVaule(y,y+anchor_mediaview.getHeight());
					return;
				} else if (dragging == 1) {
					double mousex = event.getX();
					double newWidth = anchor_mediaview.getPrefWidth() + (mousex - x);
					anchor_mediaview.setPrefWidth(newWidth);
					x = mousex;
				}else if (dragging == 2) {
					double mousey = event.getY();
					double newHeight = anchor_mediaview.getPrefHeight() + (mousey - y);
					anchor_mediaview.setPrefHeight(newHeight);
					y = mousey;
				} else if(dragging == 3) {
					double mousex = event.getX();
					double mousey = event.getY();
					double newWidth = anchor_mediaview.getPrefWidth() + (mousex - x);
					double newHeight = anchor_mediaview.getPrefHeight() + (mousey - y);
					anchor_mediaview.setPrefWidth(newWidth);
					anchor_mediaview.setPrefHeight(newHeight);
					x = mousex;
					y = mousey;
				}
			}});
        anchor_mediaview.setOnMouseMoved(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
            @Override
            public void handle(MouseEvent event) {
                if((event.getY() > (anchor_mediaview.getHeight() - RESIZE_MARGIN))&&
                		(event.getX() > (anchor_mediaview.getWidth() - RESIZE_MARGIN))) {
                	anchor_mediaview.setCursor(Cursor.NW_RESIZE);
                }else if((event.getY() > (anchor_mediaview.getHeight() - RESIZE_MARGIN))) {
                	anchor_mediaview.setCursor(Cursor.S_RESIZE);
                }else if((event.getX() > (anchor_mediaview.getWidth() - RESIZE_MARGIN))) {
                	anchor_mediaview.setCursor(Cursor.H_RESIZE);
                }
                else {
                	anchor_mediaview.setCursor(Cursor.DEFAULT);
                }
            }});
        anchor_mediaview.setOnMouseReleased(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
            @Override
            public void handle(MouseEvent event) {
                dragging = 0;
                anchor_mediaview.setCursor(Cursor.DEFAULT);
                mainApp.getTabPaneController().refresh(pageName);
            }});
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_mediaview.setPrefSize(width, height);
    }
    @FXML
    private void onButtonPause() {
		if (current_state == 0) {
			mediaPlayer.pause();
			current_state=1;
			Button_pause.getStyleClass().removeAll(Button_pause.getStyleClass());
			Button_pause.getStyleClass().add("buttonPlay");
		}else if (current_state == 1) {
			mediaPlayer.play();		
			current_state=0;
			Button_pause.getStyleClass().removeAll(Button_pause.getStyleClass());
			Button_pause.getStyleClass().add("buttonPause");
		}
    }
}
