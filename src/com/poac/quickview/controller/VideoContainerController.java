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
	private AnchorPane anchor_mediaview;                          //mediaview��壨�����пؼ���
	@FXML
	private MediaView  mediaView;                                 //mediaview�ؼ�
	@FXML
	private Label label_head;                                     //��������
	@FXML
	private AnchorPane anchor_video;                              //���ڷ���mediaView�ؼ�
	@FXML
	private Slider Slider_videoProgress;                          //����ؼ�
	@FXML
	private  ProgressBar ProgressBar_videoProgress;               //�������ؼ�
	@FXML
	private Button Button_pause;                                  //��ͣ��ť
	@FXML
	private HBox hBox_Circles;                                    //���ڷ���Ȧ�����ģ��
	private MainApp mainApp; 	                                  //Ӧ�ó���ӿ�
	private String pageName=null;                                 //page��
    private String containerName=null;                            //������
    private int current_state=0;                                  //0������ 1������ͣ                                                 
    private ContextMenu addMenu1 = new ContextMenu();             //�Ҽ��˵�
	public void setPageName(String pageName) {                    //����page��
		this.pageName=pageName;
	}
    public void setMainApp(MainApp mainApp) {                     //����Ӧ�ó���ӿ�
        this.mainApp = mainApp;
    } 
    private IController getThis() {                               //���ر����������ʵ��
    	return this;
    }
    public String getPageContainerName() {                         //��ȡ������ΨһID
    	return (pageName+":"+containerName);
    }
	/**
	 * 1��mediaView������
	 * 2��ʵ���Ҽ��˵������ݶ��ġ�������С��ɾ������
	 * 3��mediaView�������ű�������󻯣��Զ����ţ�����ʱ��Slider��ProgressBarֵ���Ÿı�
	 * 4��ʵ��Slider_videoProgress��ProgressBar_videoProgressͬ�����Լ�����Sliderʱ����Ƶ��֡
	 * 5������Ȧ��ģ��
	 * 6��ʵ����ק����
	 * 7������anchor_mediaview��UserDataΪ������
	 */	
    public void init() {
    	//1��mediaView������
    	SubscribeParameters.getSubscribeParameters().page_Container_MediaPlayerProperty
    	                     .put(getPageContainerName(), new SimpleObjectProperty<>());
    	SubscribeParameters.getSubscribeParameters().page_Container_MediaPlayerProperty
    	                     .get(getPageContainerName()).set(new MediaPlayer(new Media(getClass().getResource("/animal.mp4").toString())));
    	mediaView.mediaPlayerProperty().bind(SubscribeParameters.getSubscribeParameters()
    			             .page_Container_MediaPlayerProperty.get(getPageContainerName()));   
    	//2��ʵ���Ҽ��˵������ݶ��ġ�������С��ɾ������
    	MenuItem addMenuItem1 = new MenuItem("���ݶ���");    //�һ�TableView��ʾ��Ӳ����˵�
        addMenu1.getItems().add(addMenuItem1);
        addMenuItem1.setOnAction((event)-> {
            	if(mainApp.showSubscribe("video",getThis())) {            		
            	}
        }); 
        MenuItem addMenuItem2 = new MenuItem("������С");    //�һ�TableView��ʾ������С�˵�
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
        MenuItem addMenuItem3 = new MenuItem("ɾ������");    //�һ�TableView��ʾ������С�˵�
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
    	//3��mediaView�������ű�������󻯣��Զ����ţ�����ʱ��Slider��ProgressBarֵ���Ÿı�
    	mediaView.setPreserveRatio(false);               //����Զ��仯
    	mediaView.fitWidthProperty().bind(anchor_video.widthProperty());    //�󶨿��游�ڵ�仯
    	mediaView.fitHeightProperty().bind(anchor_video.heightProperty());  //�󶨸��游�ڵ�仯
    	mediaView.getMediaPlayer().setAutoPlay(true); //�����Զ�����    
    	mediaView.getMediaPlayer().setOnEndOfMedia(new Runnable() {            //����video���Ž������¼�����
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
		//4��ʵ��Slider_videoProgress��ProgressBar_videoProgressͬ�����Լ�����Sliderʱ����Ƶ��֡
    	Slider_videoProgress.setOnMouseDragExited(e ->{
			double d = Slider_videoProgress.getValue();
			ProgressBar_videoProgress.setProgress(d/100);
		});
    	Slider_videoProgress.valueProperty().addListener((o, ov, nv)->{   //������������Ƶͬ��
    		ProgressBar_videoProgress.setProgress(Slider_videoProgress.getValue()/100);
		});    	
    	ProgressBar_videoProgress.progressProperty().addListener((o, ov, nv)->{  
    		mediaView.getMediaPlayer().seek(mediaView.getMediaPlayer().getTotalDuration().multiply(ProgressBar_videoProgress.progressProperty().get()));
		});
	  	//5������Ȧ��ģ��
    	hBox_Circles.getChildren().add(mainApp.loadCirclePanel());
    	//6��ʵ����ק����
    	DragUtil.addDragListener(anchor_mediaview, mainApp, pageName);
        //7������anchor_mediaview��UserDataΪ������
    	anchor_mediaview.setUserData(containerName);
    }
    public void setHeadText(String txt) {             //����������Label
    	containerName=txt;
    	label_head.setText(txt);    	
    }
    public void setContainerSize(double width,double height) {   //��������Size
    	anchor_mediaview.setPrefSize(width, height);
    }
    @FXML
    private void onButtonPause() {                       //��ͣ��ť�¼�
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
