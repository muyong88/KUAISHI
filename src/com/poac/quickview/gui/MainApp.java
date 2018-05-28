package com.poac.quickview.gui;

import java.io.IOException;

import com.poac.quickview.controller.MainFormController;
import com.poac.quickview.controller.SubscribeController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
            mainCon.setMainApp(this);
            // Show the scene containing the root layout.
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
		// Load the fxml file and create a new stage for the popup dialog.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("Subscribe.fxml"));
		AnchorPane page = (AnchorPane) loader.load();		
		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Êý¾Ý¶©ÔÄ");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);

		// Set the person into the controller.
		SubscribeController controller = loader.getController();
		controller.initData();
		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();

    	return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }

	public static void main(String[] args) {
		launch(args);
	}
}
