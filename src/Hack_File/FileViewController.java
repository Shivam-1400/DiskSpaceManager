package Hack_File;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Utility.MakeAlert;
import Utility.Utility;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Drive;
import model.FileEnhanced;
import model.FileType;

public class FileViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView lnkImages;

    @FXML
    private ImageView lnkVideos;

    @FXML
    private ImageView lnkDocs;

    @FXML
    private ImageView lnkMusic;

    @FXML
    private ImageView lnkZip;

    @FXML
    private ImageView lnkApps;
    
    @FXML
    private ListView<FileEnhanced> listDataTableView;

    @FXML
    private Button btnDelete;

//    @FXML
//    private Button btnBrowse;

   
    
    @FXML
    private ComboBox<Drive> drpDrives;
    
    private final TableColumn<FileEnhanced, File> fileColumn = new TableColumn<FileEnhanced, File>("File");
    
	ObservableList<FileEnhanced> fileEnhanceds;
	private String fileType;
	private Utility utility;
	private ArrayList<Drive> drive;
	
	void helpDel(File fx) {
		boolean deleted = fx.delete();
		if (deleted) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
	}
	
//    @FXML
//    void doBrowse(ActionEvent event) {
//    	String path= MakeAlert.showInputAlertString("Enter path", "Enter path to browse");
//    	System.out.println(path);    	
//
//    } 
	
	@FXML
    void doDelete(ActionEvent event) {
		ObservableList<FileEnhanced> selFile= listDataTableView.getSelectionModel().getSelectedItems();    	
		if(selFile.size()!=0 ) {
    		boolean flg= MakeAlert.type2("Alert", "You are about to delete the selected files. Select Yes to delte");
			if(flg== false) {
				return;
			}
    		if(selFile.size()!=0) {
    			for(FileEnhanced fx: selFile) {
    				helpDel(fx.getFile());	
    				fileEnhanceds.remove(fx);
				 }
    		}
    		listDataTableView.setItems(fileEnhanceds);
    	}
    	else {
    		new MakeAlert("Alert", "Nothing is selected to delete. Select files to deleted.");
    	}
    }

    @FXML
    public void loadData(MouseEvent event) {
    	listDataTableView.getItems().clear();
    	fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));
    	System.out.println("HERE1");
		if(drpDrives.getValue()== null) {
			new MakeAlert("Alert", "First Select any drive. Then files will be loaded.");
			return;
		}
		
			ImageView imageView = (ImageView) event.getSource();
			if (imageView.equals(lnkDocs)) {
				fileType = FileType.Documents.name();
				utility.reset();
				listDataTableView.getItems().clear();

				Drive d= drpDrives.getValue();
				Thread thread = new Thread(() -> {
					fileEnhanceds = utility.recursiveGetAllDocumentFiles(
							d.getFile().listFiles(), 0);
					Platform.runLater(() -> {
						System.out.println("Total Documents :-" + fileEnhanceds.size());
						listDataTableView.setItems(fileEnhanceds);
					});
				});
				thread.start();

			} 
				else if (imageView.equals(lnkImages)) {
				fileType = FileType.Images.name();
				utility.reset();
				listDataTableView.getItems().clear();
				Drive d= drive.get(1);
				Thread thread = new Thread(() -> {
					fileEnhanceds = utility.recursiveGetAllImageFiles(
							d.getFile().listFiles(), 0);
					Platform.runLater(() -> {
						System.out.println("Total Images :-" + fileEnhanceds.size());
						listDataTableView.setItems(fileEnhanceds);
					});
				});
				thread.start();
			} 
			else if (imageView.equals(lnkVideos)) {
				fileType = FileType.Videos.name();
				utility.reset();
				listDataTableView.getItems().clear();
				
				Drive d= drpDrives.getValue();
				Thread thread = new Thread(() -> {
					fileEnhanceds = utility.recursiveGetAllVideoFiles(
							d.getFile().listFiles(), 0);
					Platform.runLater(() -> {
						System.out.println("Total Images :-" + fileEnhanceds.size());
						listDataTableView.setItems(fileEnhanceds);
					});
				});
				thread.start();
			} 
			else if (imageView.equals(lnkZip)) {
				fileType = FileType.Archives.name();
				utility.reset();
				listDataTableView.getItems().clear();

				Drive d= drpDrives.getValue();
				Thread thread = new Thread(() -> {
					fileEnhanceds = utility.recursiveGetAllArchFiles(
							d.getFile().listFiles(), 0);
					Platform.runLater(() -> {
						System.out.println("Total Images :-" + fileEnhanceds.size());
						listDataTableView.setItems(fileEnhanceds);
					});
				});
				thread.start();
			} 
			else if (imageView.equals(lnkMusic)) {
				fileType = FileType.Music.name();
				utility.reset();
				listDataTableView.getItems().clear();
				Drive d= drpDrives.getValue();
				Thread thread = new Thread(() -> {
					fileEnhanceds = utility.recursiveGetAllImageFiles(
							d.getFile().listFiles(), 0);
					Platform.runLater(() -> {
						System.out.println("Total Images :-" + fileEnhanceds.size());
						listDataTableView.setItems(fileEnhanceds);
					});
				});
				thread.start();
			} 
			else if (imageView.equals(lnkApps)) {
				fileType = FileType.Apps.name();
				utility.reset();
				listDataTableView.getItems().clear();
				Drive d= drpDrives.getValue();
				Thread thread = new Thread(() -> {
					fileEnhanceds = utility.recursiveGetAllAppFiles(
							d.getFile().listFiles(), 0);
					Platform.runLater(() -> {
						System.out.println("Total Images :-" + fileEnhanceds.size());
						listDataTableView.setItems(fileEnhanceds);
					});
				});
				thread.start();
			}
			
		
//		SortedList<FileEnhanced> sortedList = new SortedList<FileEnhanced>(filteredData);
//		sortedList.comparatorProperty().bind(listDataTableView.comparatorProperty());
//		listDataTableView.setItems(sortedList);
	}

    @FXML
    void initialize() {
    	utility = new Utility();
    	drive = utility.getAllDrives();
    	drpDrives.getItems().addAll(drive);
    	listDataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}
