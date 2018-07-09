package com.poac.quickview;

import java.io.IOException;
import java.util.ArrayList;

import com.poac.quickview.controller.AddPageController;
import com.poac.quickview.controller.AddContainerController;
import com.poac.quickview.controller.ChangeHWSizeController;
import com.poac.quickview.controller.ChangePositionController;
import com.poac.quickview.controller.CurveContainerController;
import com.poac.quickview.controller.ImageContainerController;
import com.poac.quickview.controller.MainFormController;
import com.poac.quickview.controller.SubscribeController;
import com.poac.quickview.controller.TabPaneController;
import com.poac.quickview.controller.TableContainerController;
import com.poac.quickview.controller.VideoContainerController;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.Parameter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private double xOffset = 0;
	private double yOffset = 0;
	private MainFormController mainCon = null;
	private TabPaneController tabPaneCon = null;
	private AddContainerController  addContainerCon;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initRootLayout();
		rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {    //ʵ�ִ�����ƶ�
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {   //ʵ�ִ�����ƶ�
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
		//primaryStage.setMaximized(true); 
	}	
	/**
	 * Initializes the root layout.
	 */			
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/MainForm.fxml"));
			rootLayout = (BorderPane) loader.load();
			mainCon = loader.getController();
			mainCon.setMainApp(this);
			mainCon.LoadTabPanel(loadTabPane());
			Scene scene = new Scene(rootLayout);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Load tabPane.
	 */		
	public TabPane loadTabPane() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/TabPane.fxml"));
			TabPane tP=(TabPane)loader.load();
			tabPaneCon= loader.getController();
			tabPaneCon.setMainApp(this);
			return tP;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * ��ʾ�������ݴ���
	 */	
	public boolean showSubscribe(ArrayList<Parameter> parmList) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/Subscribe.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("���ݶ���");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			SubscribeController controller = loader.getController();
			controller.setMainApp(this);
			controller.setParmList(parmList);
			controller.setDialogStage(dialogStage);
			controller.initData();
			dialogStage.showAndWait();			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	/**
	 * ��ʾ����ҳ�洰��
	 */	
	public boolean showAddPage(Page page) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/AddPage.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("����ҳ��");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page_AnchorPane);
			dialogStage.setScene(scene);
			AddPageController  addPageCon = loader.getController();
			addPageCon.setDialogStage(dialogStage);
			addPageCon.setPage(page);
			addPageCon.setMainApp(this);
			dialogStage.showAndWait();			
			return addPageCon.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * ��ʾ���ӱ����������
	 */	
	public boolean showAddContainer(String pageName) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/AddContainer.fxml"));
			AnchorPane container_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("���ӱ������");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(container_AnchorPane);
			dialogStage.setScene(scene);
			addContainerCon = loader.getController();
			addContainerCon.setDialogStage(dialogStage);
			addContainerCon.setMainApp(this);
			addContainerCon.setPageName(pageName);
			addContainerCon.init();
			dialogStage.showAndWait();	
			return addContainerCon.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean showChangePosion(Container container) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/ChangePosition.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("����λ��");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page_AnchorPane);
			dialogStage.setScene(scene);
			ChangePositionController  changePositionCon = loader.getController();
			changePositionCon.setDialogStage(dialogStage);
			changePositionCon.setMainApp(this);
			changePositionCon.setContainer(container);
			dialogStage.showAndWait();			
			return changePositionCon.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	/**
	 * ��ʾ�޸�������С����
	 */	
	public boolean showChangeHWSize(Container container) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/ChangeHWSize.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("������С");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page_AnchorPane);
			dialogStage.setScene(scene);
			ChangeHWSizeController  changeHWSizeCon = loader.getController();
			changeHWSizeCon.setDialogStage(dialogStage);
			changeHWSizeCon.setMainApp(this);
			changeHWSizeCon.setContainer(container);
			dialogStage.showAndWait();			
			return changeHWSizeCon.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	/**
	 * ��TabPane�����ӱ������
	 *
	 */	
	public void addTableContainer(String pageName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/TableContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		TableContainerController tCC=loader.getController();
		tCC.setPageName(pageName);
		tCC.setMainApp(this);
		tCC.init();
		tCC.setHeadText(addContainerCon.getConName());
		tCC.setContainerSize(addContainerCon.getWidth(), addContainerCon.getHeight());
		tabPaneCon.addContainer(pageName, container_AnchorPane,tCC,addContainerCon.getConName());
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	/**
	 * ��TabPane��������������
	 *
	 */	
	public void addCurveContainer(String pageName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/CurveContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		CurveContainerController cCC=loader.getController();
		cCC.setPageName(pageName);
		cCC.setMainApp(this);
		cCC.init();
		cCC.setHeadText(addContainerCon.getConName());
		cCC.setContainerSize(addContainerCon.getWidth(), addContainerCon.getHeight());
		tabPaneCon.addContainer(pageName, container_AnchorPane,cCC,addContainerCon.getConName());
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	/**
	 * ��TabPane������ͼ������
	 *
	 */	
	public void addImageContainer(String pageName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/ImageContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		ImageContainerController iCC=loader.getController();
		iCC.setPageName(pageName);
		iCC.setMainApp(this);
		iCC.init();
		iCC.setHeadText(addContainerCon.getConName());
		iCC.setContainerSize(addContainerCon.getWidth(), addContainerCon.getHeight());
		tabPaneCon.addContainer(pageName, container_AnchorPane,iCC,addContainerCon.getConName());
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	 
	}	
	/**
	 * ��TabPane����Ƶ����
	 * 
	 */	
	public void addVideoContainer(String pageName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/VideoContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		VideoContainerController vCC=loader.getController();
		vCC.setPageName(pageName);
		vCC.setMainApp(this);
		vCC.init();
		vCC.setHeadText(addContainerCon.getConName());
		vCC.setContainerSize(addContainerCon.getWidth(), addContainerCon.getHeight());
		tabPaneCon.addContainer(pageName, container_AnchorPane,vCC,addContainerCon.getConName());
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	 
	}	
	public void addContainer(String pageName) {
		if(addContainerCon.getType().equals("Data")) {
			addTableContainer(pageName);
		}else if(addContainerCon.getType().equals("Curve")){
			addCurveContainer(pageName);
		}else if(addContainerCon.getType().equals("Image")){
			addImageContainer(pageName);
		}else if(addContainerCon.getType().equals("Video")){
			addVideoContainer(pageName);
		}
	}
	public TabPaneController getTabPaneController() {
		return tabPaneCon;
	}
	public MainFormController getMainFormController() {
		return mainCon;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
