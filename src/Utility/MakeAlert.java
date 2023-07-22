package Utility;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class MakeAlert {
	public MakeAlert() {}
	public MakeAlert(String header, String msg) {
		 Alert alert= new Alert(AlertType.INFORMATION);
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
	 }
	public static boolean type2(String header, String msg) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait();

        if (alert.getResult() == yesButton) {
            System.out.println("User clicked 'Yes'. Proceed with the operation.");
            return true; 
        } else {
            System.out.println("User clicked 'No'. Cancel the operation.");
            return false; 
        }
    }
	public static int showInputAlert(String header, String msg) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText(header);
        inputDialog.setContentText(msg);

       Optional<String> result = inputDialog.showAndWait();

       if (result.isPresent()) {
            try {
                int userInput = Integer.parseInt(result.get());
                System.out.println("User input: " + userInput);
                return userInput; 
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                new MakeAlert("Invalid input", "Invalid input. Please enter a valid integer.");
                return -1; 
            }
        } else {
            System.out.println("User canceled the input dialog.");
            return -1; 
        }
    }
	public static String showInputAlertString(String header, String msg) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText(header);
        inputDialog.setContentText(msg);

       Optional<String> result = inputDialog.showAndWait();

       if (result.isPresent()) {
            try {
                String userInput = result.get();
                System.out.println("User input: " + userInput);
                return userInput;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                new MakeAlert("Invalid input", "Invalid input. Please enter a valid String.");
                return null; 
            }
        } else {
            System.out.println("User canceled the input dialog.");
            return null; 
        }
    }
}
