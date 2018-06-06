package com.poac.quickview;

import java.io.IOException;
import java.util.ArrayList;

import com.poac.quickview.controller.AddPageController;
import com.poac.quickview.controller.MainFormController;
import com.poac.quickview.controller.SubscribeController;
import com.poac.quickview.model.Page;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private double xOffset = 0;
	private double yOffset = 0;
	private MainFormController mainCon = null;
	private AddPageController  addPageCon = null;
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		initRootLayout();
		rootLayout.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		rootLayout.setOnMouseDragged(new EventHandler<MouseEvent>() {
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
			Scene scene = new Scene(rootLayout);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean showSubscribe() {
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
			controller.initData();
			dialogStage.showAndWait();			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}	
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
			addPageCon = loader.getController();
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
	public AddPageController getAddPageController() {
		return addPageCon;
	}
	public MainFormController getMainFormController() {
		return mainCon;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
