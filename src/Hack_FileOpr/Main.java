
package Hack_FileOpr;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application 
{
	
 public void start(@SuppressWarnings("exports") Stage primaryStage) 
   {
		try {
				Parent root=(Parent) FXMLLoader.load(getClass().getResource("FileOprView.fxml"));
				Scene scene = new Scene(root,800,700);
//				scene.getStylesheets().add("style1.css");
				primaryStage.setScene(scene);
				primaryStage.show();
		    } 
		catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}




