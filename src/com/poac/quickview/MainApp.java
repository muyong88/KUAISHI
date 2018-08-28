package com.poac.quickview;

import java.io.IOException;
import com.poac.quickview.controller.AddContainerController;
import com.poac.quickview.controller.AddPageController;
import com.poac.quickview.controller.ChangeHWSizeController;
import com.poac.quickview.controller.CirclePanelController;
import com.poac.quickview.controller.CurveContainerController;
import com.poac.quickview.controller.IController;
import com.poac.quickview.controller.ImageContainerController;
import com.poac.quickview.controller.LogonController;
import com.poac.quickview.controller.MainFormController;
import com.poac.quickview.controller.SearchListController;
import com.poac.quickview.controller.SubscribeController;
import com.poac.quickview.controller.TabPaneController;
import com.poac.quickview.controller.TableContainerController;
import com.poac.quickview.controller.VideoContainerController;
import com.poac.quickview.global.GlobalVariable;
import com.poac.quickview.model.Container;
import com.poac.quickview.model.Page;
import com.poac.quickview.test.DataParserThread;
import com.poac.quickview.test.ImageParserThread;
import com.poac.quickview.util.LogFactory;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle; 
//程序入口访问接口类
public class MainApp extends Application {	
	private Stage primaryStage;            
	private BorderPane rootLayout;      //MainForm
	private double xOffset = 0;        //鼠标按下时x坐标
	private double yOffset = 0;         //鼠标按下时y坐标
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
		DataParserThread dataParserThread=new DataParserThread();   //发送数据线程
		dataParserThread.start();
		ImageParserThread imageParserThread=new ImageParserThread(); //发送数据线程
		imageParserThread.start();
		LogFactory.getGlobalLog().info("System is Started!");
	}	
	public void initData() {
		tabPaneCon.initData();          //初始化TabPane数据
		tabPaneCon.setDefaultTab();   //设置TabPane默认TAB
	}
	/** 
	 * Initializes the root layout. 加载MainForm界面
	 */			
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/MainForm.fxml"));
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
	 * @param type  订阅容器类型
     * @param con   定期容器的Controller
	 */	
	public boolean showSubscribe(String type,IController con) {
		try {
			LogFactory.getGlobalLog().info("load Subscribe Form！");
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
			controller.setContainerController(con);
			controller.setDialogStage(dialogStage);
			controller.initData(type);
			dialogStage.showAndWait();			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	/**
	 * 显示增加页面窗口
	 * @param Page  Page模型，用于记录page名称
	 */	
	public boolean showAddPage(Page page) {
		try {
			LogFactory.getGlobalLog().info("load AddPage Form！");			
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
	 * @param pageName  传入page名称，判断page是否重复
	 */	
	public boolean showAddContainer(String pageName) {
		try {
			LogFactory.getGlobalLog().info("load AddContainer Form！");
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
			addContainerCon.initData();
			dialogStage.showAndWait();	
			return addContainerCon.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
	/**
	 * 显示登录框
	 */	
	public boolean showLogon() {
		try {
			LogFactory.getGlobalLog().info("load Logon Form！");
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
	 * @param container  传入container模型，记录容器属性（Width，Height）
	 */	
	public boolean showChangeHWSize(Container container) {
		try {
			LogFactory.getGlobalLog().info("load ChangeHWSize Form！");
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
	 * 显示搜索列表窗口
	 * @param paraCode  参数代号，支持模糊匹配
	 */	
	public boolean showSearchList(String paraCode) {
		try {
			LogFactory.getGlobalLog().info("load SearchList Form！");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("gui/SearchList.fxml"));
			AnchorPane page_AnchorPane = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("搜索结果");
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page_AnchorPane);
			dialogStage.setScene(scene);
			SearchListController  searchListControllerCon = loader.getController();
			searchListControllerCon.setDialogStage(dialogStage);
			searchListControllerCon.setMainApp(this);
			searchListControllerCon.initData(paraCode);
			searchListControllerCon.init();
			dialogStage.showAndWait();			
			return searchListControllerCon.isOkClicked();
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
		LogFactory.getGlobalLog().info("add TableContainer in page:"+pageName+"   ContainerName:"+addContainerCon.getConName());
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
	 * @param pageName
	 */	
	public void addCurveContainer(String pageName) {
		LogFactory.getGlobalLog().info("add CurveContainer in page:"+pageName+"  ContainerName:"+addContainerCon.getConName());
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
	 * @param pageName
	 */	
	public void addImageContainer(String pageName) { 
		LogFactory.getGlobalLog().info("add ImageContainer in page:"+pageName+"  ContainerName:"+addContainerCon.getConName());
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
		iCC.setHeadText(containerName);
		iCC.init();
		iCC.setContainerSize(width, height);
		tabPaneCon.addContainer(pageName, container_AnchorPane,iCC,containerName);
		tabPaneCon.refresh(pageName);
		} catch (IOException e) {
			e.printStackTrace();
		}	 
	}
	/**
	 * 在TabPane中视频容器
	 * @param pageName
	 */	
	public void addVideoContainer(String pageName) { 
		LogFactory.getGlobalLog().info("add VideoContainer in page:"+pageName+"  ContainerName:"+addContainerCon.getConName());
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
	/**
	 * 添加容器，根据controller类型，判断调用增加何种容器
	 * @param pageName
	 */	
	public void addContainer(String pageName) {
		if(addContainerCon.getType().equals(GlobalVariable.data)) {
			addTableContainer(pageName);
		}else if(addContainerCon.getType().equals(GlobalVariable.curve)){
			addCurveContainer(pageName);
		}else if(addContainerCon.getType().equals(GlobalVariable.image)){
			addImageContainer(pageName);
		}else if(addContainerCon.getType().equals(GlobalVariable.video)){
			addVideoContainer(pageName);
		}
	}
	/**
	 * 返回TabPaneController
	 */	
	public TabPaneController getTabPaneController() {
		return tabPaneCon;
	}
	/**
	 * 返回MainFormController
	 */	
	public MainFormController getMainFormController() {
		return mainCon;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
