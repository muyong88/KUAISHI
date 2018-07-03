package com.poac.quickview.controller;

import java.util.ArrayList;

import com.poac.quickview.MainApp;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Parameter;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
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
	private MainApp mainApp; 	
	private String pageName=null;
    private String containerName=null;
	private double xOffset = 0;
	private double yOffset = 0;
    private  int RESIZE_MARGIN = 5;
    private int dragging=0;     //0代表不拉 1代表横拉  2代表竖拉 3代表斜拉
    private double x;
    private double y;
    private ContextMenu addMenu1 = new ContextMenu();
	public VideoContainerController() {
        MenuItem addMenuItem1 = new MenuItem("添加参数");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction(new EventHandler() {
            public void handle(Event t) {
            	ArrayList<Parameter> arParm=new ArrayList();
            	if(mainApp.showSubscribe(arParm)) {            		
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
	}   
	public void setPageName(String pageName) {
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    } 
    public void init() {
    	mediaView.setPreserveRatio(false);               //宽高自动变化
    	mediaView.fitWidthProperty().bind(anchor_video.widthProperty());    //绑定宽随父节点变化
    	mediaView.fitHeightProperty().bind(anchor_video.heightProperty());  //绑定高随父节点变化
    	
    	Slider_videoProgress.setOnMouseClicked(e ->{
			double d = Slider_videoProgress.getValue();
			ProgressBar_videoProgress.setProgress(d/100);
		});
    	Slider_videoProgress.setOnMouseDragged(e ->{
			double d = Slider_videoProgress.getValue();
			ProgressBar_videoProgress.setProgress(d/100);
		});
    	Slider_videoProgress.setOnMouseDragEntered(e ->{
			double d = Slider_videoProgress.getValue();
			ProgressBar_videoProgress.setProgress(d/100);
		});
    	Slider_videoProgress.setOnMouseDragExited(e ->{
			double d = Slider_videoProgress.getValue();
			ProgressBar_videoProgress.setProgress(d/100);
		});
    	
    	String media_URL = getClass().getResource("../css/containerCss/animal.mp4").toString();
    	Media media = new Media(media_URL);
    	MediaPlayer mediaPlayer = new MediaPlayer(media);
    	mediaPlayer.setAutoPlay(true); //设置自动播放
    	mediaView.setMediaPlayer(mediaPlayer);
    	mediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_mediaview, event.getScreenX(), event.getScreenY());
            }  
          }         
        });
        anchor_mediaview.setOnMousePressed(new EventHandler<MouseEvent>() {      //用于拖拉anchorpane
			@Override
			public void handle(MouseEvent event) {
				if (!(event.getY() > (anchor_mediaview.getHeight() - RESIZE_MARGIN))&&
						!(event.getX() > (anchor_mediaview.getWidth() - RESIZE_MARGIN))) {     //判断不改变大小范围
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
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
					anchor_mediaview.setLayoutX(event.getScreenX() - xOffset);
					anchor_mediaview.setLayoutY(event.getScreenY() - yOffset);
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
}
