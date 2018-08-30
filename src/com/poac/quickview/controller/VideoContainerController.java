  package com.poac.quickview.controller;


import com.poac.quickview.MainApp;
import com.poac.quickview.global.SubscribeParameters;
import com.poac.quickview.model.Container;
import com.poac.quickview.util.DragUtil;
import com.poac.quickview.util.LogFactory;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
public class VideoContainerController implements IController {
	@FXML
	private AnchorPane anchor_mediaview;                          //mediaview面板（放所有控件）
	@FXML
	private MediaView  mediaView;                                 //mediaview控件
	@FXML
	private Label label_head;                                     //容器标题
	@FXML
	private AnchorPane anchor_video;                              //用于放置mediaView控件
	@FXML
	private Slider Slider_videoProgress;                          //滑块控件
	@FXML
	private  ProgressBar ProgressBar_videoProgress;               //进度条控件
	@FXML
	private Button Button_pause;                                  //暂停按钮
	@FXML
	private HBox hBox_Circles;                                    //用于放置圈数组件模块
	private MainApp mainApp; 	                                  //应用程序接口
	private String pageName=null;                                 //page名
    private String containerName=null;                            //容器名
    private int current_state=0;                                  //0代表播放 1代表暂停                                                 
    private ContextMenu addMenu1 = new ContextMenu();             //右键菜单
	public void setPageName(String pageName) {                    //设置page名
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {                     //设置应用程序接口
        this.mainApp = mainApp;
    } 
    private IController getThis() {                               //返回本控制类对象实例
    	return this;
    }
    public String getPageContainerName() {                         //获取本容器唯一ID
    	return (pageName+":"+containerName);
    }
	/**
	 * 1、mediaView绑定数据
	 * 2、实现右键菜单：数据订阅、调整大小、删除容器
	 * 3、mediaView设置缩放比例，最大化，自动播放，播放时候Slider和ProgressBar值随着改变
	 * 4、实现Slider_videoProgress和ProgressBar_videoProgress同步，以及拖拉Slider时候视频跳帧
	 * 5、加载圈数模块
	 * 6、实现拖拽功能
	 * 7、设置anchor_mediaview的UserData为容器名
	 */	
    public void init() {
    	//1、mediaView绑定数据
    	SubscribeParameters.getSubscribeParameters().page_Container_MediaPlayerProperty
    	                     .put(getPageContainerName(), new SimpleObjectProperty<>());
    	SubscribeParameters.getSubscribeParameters().page_Container_MediaPlayerProperty
    	                     .get(getPageContainerName()).set(new MediaPlayer(new Media(getClass().getResource("/animal.mp4").toString())));
    	mediaView.mediaPlayerProperty().bind(SubscribeParameters.getSubscribeParameters()
    			             .page_Container_MediaPlayerProperty.get(getPageContainerName()));   
    	//2、实现右键菜单：数据订阅、调整大小、删除容器
    	MenuItem addMenuItem1 = new MenuItem("数据订阅");    //右击TableView显示添加参数菜单
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event)-> {
            	if(mainApp.showSubscribe("video",getThis())) {            		
            	}
        }); 
        MenuItem addMenuItem2 = new MenuItem("调整大小");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem2);
        addMenuItem2.setOnAction((event)-> {
            	Container container=new Container();
            	if(mainApp.showChangeHWSize(container)) {
            		setContainerSize(container.getWidth(), container.getHeight());
            		LogFactory.getGlobalLog().info("Input Change Container Size! PageName:"+pageName+" ContainerName:"+
            				containerName+" New Width:"+container.getWidth()+" New Height:"+container.getHeight());
            	    mainApp.getTabPaneController().refresh(pageName);
            	}
        });
        MenuItem addMenuItem3 = new MenuItem("删除容器");    //右击TableView显示调整大小菜单
        addMenu1.getItems().add(addMenuItem3);
        addMenuItem3.setOnAction((event)->{
            	mainApp.getTabPaneController().removeConatiner(pageName, containerName);
            	LogFactory.getGlobalLog().info("Delete Container! ContainerName:"+containerName);
            	mainApp.getTabPaneController().refresh(pageName);
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
    	//3、mediaView设置缩放比例，最大化，自动播放，播放时候Slider和ProgressBar值随着改变
    	mediaView.setPreserveRatio(false);               //宽高自动变化
    	mediaView.fitWidthProperty().bind(anchor_video.widthProperty());    //绑定宽随父节点变化
    	mediaView.fitHeightProperty().bind(anchor_video.heightProperty());  //绑定高随父节点变化
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
		//4、实现Slider_videoProgress和ProgressBar_videoProgress同步，以及拖拉Slider时候视频跳帧
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
	  	//5、加载圈数模块
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	//6、实现拖拽功能
    	DragUtil.addDragListener(anchor_mediaview, mainApp, pageName);
        //7、设置anchor_mediaview的UserData为容器名
    	anchor_mediaview.setUserData(containerName);
    }
    public void setHeadText(String txt) {             //设置容器名Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //设置容器Size
    	anchor_mediaview.setPrefSize(width, height);
    }
    @FXML
    private void onButtonPause() {                       //暂停按钮事件
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
