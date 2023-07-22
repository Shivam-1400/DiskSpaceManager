//package Hack_Storage;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//import Utility.Utility;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.chart.PieChart;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import model.Drive;
//import java.text.DecimalFormat;
//
//public class StorageViewController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private ComboBox<String> drpDrives;
//
//    @FXML
//    private PieChart spaceChart;
//
//    @FXML
//    private HBox hboxStatus;
//
//    @FXML
//    private Label lblUsed;
//
//    @FXML
//    private Label lblFree;
//    
//    
//    Utility utility;
//    private ArrayList<Drive> drives;
//    private HashMap<String, Drive> driveNameOfDrives;
//    private ObservableList<PieChart.Data> pieChartData;
//	
//    
//    
////    void loadChartData(ActionEvent event) {
////    	pieChartData = FXCollections.observableArrayList();
////		Drive drive = drpDrives.getSelectionModel().getSelectedItem();
////		PieChart.Data usedData = new PieChart.Data("Used Space " + drive.getUsedPer() + " %", drive.getDblUsedSpace());
////		PieChart.Data remData = new PieChart.Data("Free Space " + drive.getRemPer() + " %", drive.getDblFreeSpace());
////		pieChartData.add(usedData);
////		pieChartData.add(remData);
////		spaceChart.setData(pieChartData);
////		hboxStatus.setVisible(true);
////		lblUsed.setText(drive.getUsedSpace());
////		lblFree.setText(drive.getFreeSpace());
////	}
//    
//    @FXML
//    void loadChartData(ActionEvent event) {
////    	pieChartData = FXCollections.observableArrayList();
//        String selected = drpDrives.getSelectionModel().getSelectedItem();
//        if(selected != "All Drives Combined"){
//        	pieChartData = FXCollections.observableArrayList();
//        	Drive drive= driveNameOfDrives.get(selected);
//        	PieChart.Data usedData = new PieChart.Data("Used Space " + drive.getUsedPer() + " %", drive.getDblUsedSpace());
//    		PieChart.Data remData = new PieChart.Data("Free Space " + drive.getRemPer() + " %", drive.getDblFreeSpace());
//    		pieChartData.add(usedData);
//    		pieChartData.add(remData);
//    		spaceChart.setData(pieChartData);
//    		hboxStatus.setVisible(true);
//    		lblUsed.setText(drive.getUsedSpace());
//    		lblFree.setText(drive.getFreeSpace());
//        	
//        }
//        else {
//        	pieChartData = FXCollections.observableArrayList();
//        	double usedSpace = 0, freeSpace = 0;
//        	for(Map.Entry<String, Drive> ex: driveNameOfDrives.entrySet()) {
//            	Drive drive = ex.getValue();
//            	usedSpace += drive.getDblUsedSpace();
//                freeSpace += drive.getDblFreeSpace();
//            	
//            }
//        	
//        	
//        	double total= usedSpace+ freeSpace;
//        	
//        	
//        	PieChart.Data usedData = new PieChart.Data("Used Space "+ (double)usedSpace*100/(total) + "0 %", usedSpace);
//		    PieChart.Data remData = new PieChart.Data("Free Space "+ (double)freeSpace*100/(total) + "0 %", freeSpace);
//		    pieChartData.add(usedData);
//		    pieChartData.add(remData);
//		    spaceChart.setData(pieChartData);
//		    hboxStatus.setVisible(true);
//		    lblUsed.setText(usedSpace+"");
//		    lblFree.setText(freeSpace+"");
//		  
//        	
//        }
//        
//	}
//
//
//    @FXML
//    void initialize() {
//    	driveNameOfDrives= new HashMap<String, Drive>();
//    	utility = new Utility();
//    	drives = utility.getAllDrives();
//    	for(Drive it: drives){
//            driveNameOfDrives.put(it.toString(), it);
////            drpDrives.Items.Add(it.getDriveName());
//            drpDrives.getItems().add(it.toString());
//        }
//        drpDrives.getItems().add("All Drives Combined");
//    	
//    	
//
//        assert drpDrives != null : "fx:id=\"drpDrives\" was not injected: check your FXML file 'FileView.fxml'.";
//        assert spaceChart != null : "fx:id=\"spaceChart\" was not injected: check your FXML file 'FileView.fxml'.";
//        assert hboxStatus != null : "fx:id=\"hboxStatus\" was not injected: check your FXML file 'FileView.fxml'.";
//        assert lblUsed != null : "fx:id=\"lblUsed\" was not injected: check your FXML file 'FileView.fxml'.";
//        assert lblFree != null : "fx:id=\"lblFree\" was not injected: check your FXML file 'FileView.fxml'.";
//
//    }
//}


package Hack_Storage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.Drive;
import java.text.DecimalFormat;

public class StorageViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> drpDrives;

    @FXML
    private PieChart spaceChart;

    @FXML
    private HBox hboxStatus;

    @FXML
    private Label lblUsed;

    @FXML
    private Label lblFree;
    
    
    Utility utility;
    private ArrayList<Drive> drives;
    private HashMap<String, Drive> driveNameOfDrives;
    private ObservableList<PieChart.Data> pieChartData;
	
    
    
//    void loadChartData(ActionEvent event) {
//    	pieChartData = FXCollections.observableArrayList();
//		Drive drive = drpDrives.getSelectionModel().getSelectedItem();
//		PieChart.Data usedData = new PieChart.Data("Used Space " + drive.getUsedPer() + " %", drive.getDblUsedSpace());
//		PieChart.Data remData = new PieChart.Data("Free Space " + drive.getRemPer() + " %", drive.getDblFreeSpace());
//		pieChartData.add(usedData);
//		pieChartData.add(remData);
//		spaceChart.setData(pieChartData);
//		hboxStatus.setVisible(true);
//		lblUsed.setText(drive.getUsedSpace());
//		lblFree.setText(drive.getFreeSpace());
//	}
    
    @FXML
    void loadChartData(ActionEvent event) {
//    	pieChartData = FXCollections.observableArrayList();
        String selected = drpDrives.getSelectionModel().getSelectedItem();
        if(selected != "All Drives Combined"){
        	pieChartData = FXCollections.observableArrayList();
        	Drive drive= driveNameOfDrives.get(selected);
        	PieChart.Data usedData = new PieChart.Data("Used Space " + drive.getUsedPer() + " %", drive.getDblUsedSpace());
    		PieChart.Data remData = new PieChart.Data("Free Space " + drive.getRemPer() + " %", drive.getDblFreeSpace());
    		pieChartData.add(usedData);
    		pieChartData.add(remData);
    		spaceChart.setData(pieChartData);
    		hboxStatus.setVisible(true);
    		lblUsed.setText(drive.getUsedSpace());
    		lblFree.setText(drive.getFreeSpace());
        	
        }
        else {
        	pieChartData = FXCollections.observableArrayList();
        	double usedSpace = 0, freeSpace = 0;
        	for(Map.Entry<String, Drive> ex: driveNameOfDrives.entrySet()) {
            	Drive drive = ex.getValue();
            	usedSpace += drive.getDblUsedSpace();
                freeSpace += drive.getDblFreeSpace();
            	
            }
        	
        	
        	usedSpace= Math.round(usedSpace * 100.0) / 100.0;
        	freeSpace= Math.round(freeSpace * 100.0) / 100.0;
        	double total= usedSpace+ freeSpace;
        	
        	
        	
        	PieChart.Data usedData = new PieChart.Data("Used Space "+ usedSpace*100/(total) + "%", usedSpace);
		    PieChart.Data remData = new PieChart.Data("Free Space "+ freeSpace*100/(total) + "%", freeSpace);
		    pieChartData.add(usedData);
		    pieChartData.add(remData);
		    spaceChart.setData(pieChartData);
		    hboxStatus.setVisible(true);
		    lblUsed.setText(usedSpace+"");
		    lblFree.setText(freeSpace+"");
		  
        	
        }
        
	}


    @FXML
    void initialize() {
    	driveNameOfDrives= new HashMap<String, Drive>();
    	utility = new Utility();
    	drives = utility.getAllDrives();
    	for(Drive it: drives){
            driveNameOfDrives.put(it.toString(), it);
//            drpDrives.Items.Add(it.getDriveName());
            drpDrives.getItems().add(it.toString());
        }
        drpDrives.getItems().add("All Drives Combined");
    	
    }
}
