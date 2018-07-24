package com.poac.quickview;

import java.io.IOException;
import java.util.ArrayList;

import com.poac.quickview.controller.AddContainerController;
import com.poac.quickview.controller.AddPageController;
import com.poac.quickview.controller.ChangeHWSizeController;
import com.poac.quickview.controller.CirclePanelController;
import com.poac.quickview.controller.CurveContainerController;
import com.poac.quickview.controller.ImageContainerController;
import com.poac.quickview.controller.LogonController;
import com.poac.quickview.controller.MainFormController;
import com.poac.quickview.controller.SubscribeController;
import com.poac.quickview.controller.TabPaneController;
import com.poac.quickview.controller.TableContainerController;
import com.poac.quickview.controller.VideoContainerController;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Page;
import com.poac.quickview.model.DataParameter;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {    //实现窗体可移动
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {   //实现窗体可移动
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
		initData();
		//primaryStage.setMaximized(true); 
	}	
	public void initData() {
		tabPaneCon.initData();
		tabPaneCon.setDefaultTab();
	}
	/**
	 * Initializes the root layout.
	 */			
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/MainForm.fxml"));
//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Information Dialog");
//			alert.setHeaderText("Look, an Information Dialog");
//			alert.setContentText(MainApp.class.getResource("gui/MainForm.fxml").getFile());
//			alert.showAndWait();
			rootLayout = (BorderPane) loader.load();
			mainCon = loader.getController();
			mainCon.initData();
			mainCon.setMainApp(this);
			mainCon.LoadTabPanel(loadTabPane());
			mainCon.setDialogStage(primaryStage);
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
	 * 加载圈数PANE
	 */	
	public HBox loadCirclePanel() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/CirclePane.fxml"));
			HBox vB=(HBox)loader.load();
			CirclePanelController cPC= loader.getController();
			cPC.setMainApp(this);	
			cPC.init();
			return vB;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 显示订阅数据窗口
	 */	
	public boolean showSubscribe(ArrayList<DataParameter> parmList) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/Subscribe.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("数据订阅");
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
	 * 显示增加页面窗口
	 */	
	public boolean showAddPage(Page page) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/AddPage.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("增加页面");
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
	 * 显示增加表格容器窗口
	 */	
	public boolean showAddContainer(String pageName) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/AddContainer.fxml"));
			AnchorPane container_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("增加表格容器");
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
	public boolean showLogon() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/Logon.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("登录");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page_AnchorPane);
			dialogStage.setScene(scene);
			LogonController  logonCon = loader.getController();
			logonCon.setDialogStage(dialogStage);
			logonCon.setMainApp(this);
			dialogStage.showAndWait();			
			return logonCon.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	/**
	 * 显示修改容器大小窗口
	 */	
	public boolean showChangeHWSize(Container container) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/ChangeHWSize.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("调整大小");
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
	 * 在TabPane中增加表格容器
	 *
	 */	
	public void addTableContainer(String pageName) {
		addTableContainer(pageName,addContainerCon.getWidth(),addContainerCon.getHeight(),addContainerCon.getConName());
	}	
	public void addTableContainer(String pageName,double width,double height,String containerName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/TableContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		TableContainerController tCC=loader.getController();
		tCC.setPageName(pageName);
		tCC.setMainApp(this);
		tCC.init();
		tCC.setHeadText(containerName);
		tCC.setContainerSize(width, height);
		tabPaneCon.addContainer(pageName, container_AnchorPane,tCC,containerName);
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * 在TabPane中增加曲线容器
	 *
	 */	
	public void addCurveContainer(String pageName) {
		addCurveContainer(pageName,addContainerCon.getWidth(),addContainerCon.getHeight(),addContainerCon.getConName());
	}	
	public void addCurveContainer(String pageName,double width,double height,String containerName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/CurveContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		CurveContainerController cCC=loader.getController();
		cCC.setPageName(pageName);
		cCC.setMainApp(this);
		cCC.init();
		cCC.setHeadText(containerName);
		cCC.setContainerSize(width, height);
		tabPaneCon.addContainer(pageName, container_AnchorPane,cCC,containerName);
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	/**
	 * 在TabPane中增加图像容器
	 *
	 */	
	public void addImageContainer(String pageName) { 
		addImageContainer(pageName,addContainerCon.getWidth(),addContainerCon.getHeight(),addContainerCon.getConName());
	}
	public void addImageContainer(String pageName,double width,double height,String containerName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/ImageContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		ImageContainerController iCC=loader.getController();
		iCC.setPageName(pageName);
		iCC.setMainApp(this);
		iCC.init();
		iCC.setHeadText(containerName);
		iCC.setContainerSize(width, height);
		tabPaneCon.addContainer(pageName, container_AnchorPane,iCC,containerName);
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	 
	}
	/**
	 * 在TabPane中视频容器
	 * 
	 */	
	public void addVideoContainer(String pageName) { 
		addVideoContainer(pageName,addContainerCon.getWidth(),addContainerCon.getHeight(),addContainerCon.getConName());
	}	
	public void addVideoContainer(String pageName,double width,double height,String containerName) {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("gui/VideoContainer.fxml"));
		AnchorPane container_AnchorPane = (AnchorPane) loader.load();
		VideoContainerController vCC=loader.getController();
		vCC.setPageName(pageName);
		vCC.setMainApp(this);
		vCC.init();
		vCC.setHeadText(containerName);
		vCC.setContainerSize(width, height);
		tabPaneCon.addContainer(pageName, container_AnchorPane,vCC,containerName);
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
