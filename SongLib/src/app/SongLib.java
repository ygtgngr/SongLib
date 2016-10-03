package app;
/*
 * Yigit Gungor
 * matthew kalitasr 
 * csc 213
 *  Assignment 1
 * 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import view.ListController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SongLib extends Application {

	static ListController listController;
	public void start(Stage primaryStage) 
			throws Exception {                
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/List.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		listController = 
				loader.getController();
		listController.start(primaryStage);

		Scene scene = new Scene(root, 540, 540);
	    primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	listController.writeDataTo();
		    }
		});
		
	}
	
	

	

}
