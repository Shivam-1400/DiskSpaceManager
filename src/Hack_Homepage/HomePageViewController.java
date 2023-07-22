package Hack_Homepage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void btnFile(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Hack_File/FileView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnOperation(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Hack_FileOpr/FileOprView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnStorage(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Hack_Storage/StorageView.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {

    }
}
