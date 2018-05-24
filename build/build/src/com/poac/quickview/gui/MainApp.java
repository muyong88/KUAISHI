package com.poac.quickview.gui;

import java.io.IOException;

import com.poac.quickview.controller.MainFormController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
	private double xOffset = 0;  
    private double yOffset = 0;  
    private MainFormController mainCon = null;
    
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
        primaryStage.setMaximized(true);
	}
	
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("MainForm.fxml"));
            rootLayout = (BorderPane) loader.load();
            mainCon=loader.getController();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.initStyle(StageStyle.UNDECORATED);  
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

	public static void main(String[] args) {
		launch(args);
	}
}
