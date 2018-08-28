  package com.poac.quickview.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.DataParameter;
import com.poac.quickview.model.IBaseNode;
import com.poac.quickview.util.DragUtil;

import javafx.beans.property.SimpleObjectProperty;
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
	private MainApp mainApp; 	
	private String pageName=null;
    private String containerName=null;
    private int current_state=0; //0代表播放 1代表暂停
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
    public String getPageContainerName() {
    	return (pageName+":"+containerName);
    }
    public void init() {
    	SubscribeParameters.getSubscribeParameters().page_Container_MediaPlayerProperty
    	                     .put(getPageContainerName(), new SimpleObjectProperty<>());
    	SubscribeParameters.getSubscribeParameters().page_Container_MediaPlayerProperty
    	                     .get(getPageContainerName()).set(new MediaPlayer(new Media(getClass().getResource("/animal.mp4").toString())));
    	mediaView.mediaPlayerProperty().bind(SubscribeParameters.getSubscribeParameters()
    			             .page_Container_MediaPlayerProperty.get(getPageContainerName()));   
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
    		mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getTotalDuration().multiply(ProgressBar_videoProgress.progressProperty().get()));
		});
    	mediaView.getMediaPlayer().setAutoPlay(true); //设置自动播放    
    	mediaView.getMediaPlayer().setOnEndOfMedia(new Runnable() {            //设置video播放结束后事件内容
		    @Override
		    public void run() {
				current_state=1;
				Button_pause.getStyleClass().removeAll(Button_pause.getStyleClass());
				Button_pause.getStyleClass().add("buttonPlay");
				mediaView.getMediaPlayer().stop();
		    }
		});
		mediaView.getMediaPlayer().currentTimeProperty().addListener((o, ov, nv)->{
			double n=nv.toSeconds()/mediaView.getMediaPlayer().getTotalDuration().toSeconds()*100;
			Slider_videoProgress.setValue(n);
		});		  	
    	anchor_mediaview.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
            	addMenu1.show(anchor_mediaview, event.getScreenX(), event.getScreenY());
            }else {
          	  addMenu1.hide();
            }
          }         
        });
    	DragUtil.addDragListener(anchor_mediaview, mainApp, pageName);
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
			mediaView.getMediaPlayer().pause();
			current_state=1;
			Button_pause.getStyleClass().removeAll(Button_pause.getStyleClass());
			Button_pause.getStyleClass().add("buttonPlay");
		}else if (current_state == 1) {
			mediaView.getMediaPlayer().play();		
			current_state=0;
			Button_pause.getStyleClass().removeAll(Button_pause.getStyleClass());
			Button_pause.getStyleClass().add("buttonPause");
		}
    }
}
