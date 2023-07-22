package Hack_FileOpr;

import java.io.File;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utility.MakeAlert;
import Utility.Utility;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import model.Drive;
import model.FileEnhanced;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.nio.file.attribute.BasicFileAttributes;

public class FileOprViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLargeFile;

    @FXML
    private Button btnFindDup;

    @FXML
    private Button btnRare;

    @FXML
    private TextField txtFile;

    @FXML
    private ListView<FileEnhanced> listFile;
    
    @FXML
    private ListView<String> listSize;

    @FXML
    private Button btnDelete;
    
    @FXML
    private ComboBox<Drive> drpDrives;
    
    private Utility utility;
    private ArrayList<Drive> drive;
    
    private ObservableList<FileEnhanced> largeFile;
    private ObservableList<String> largeSize;
    private ObservableList<FileEnhanced> dupFile;
    private ObservableList<String> dupSize;
    private ObservableList<FileEnhanced> rareFile;
    private ObservableList<String> rareDate;
	
	
	int flag;

    @FXML
    void doFindDup(ActionEvent event) {
    	if(drpDrives.getValue()== null) {
//			System.out.println("ALERT select drive");
    		new MakeAlert("Alert", "First Select any drive. Then files will be loaded.");
			return;
		}
    	flag= 2;
    	System.out.print("DUP CLICKED");
//    	Drive d= drpDrives.getValue();
    	Drive d= drpDrives.getSelectionModel().getSelectedItem();
    	System.out.println(d.getDriveName()+"#######");
    	try {
    		dupFile= FXCollections.observableArrayList();
			dupSize= FXCollections.observableArrayList();
			
			Map<String, List<String>> duplicates= find_Duplicates(d.getFile().listFiles());
			System.out.println("Duplicates fpounf");
			for(Map.Entry<String, List<String>> mpx: duplicates.entrySet()) {
				String key = mpx.getKey();
			    List<String> value = mpx.getValue();
			    if(value.size() !=1) {
			    	System.out.println("Key: " + key);
				    System.out.println("Values: " + value);
	
				    for (String element : value) {
				        System.out.println("List Element: " + element);
				        
				        File duu= new File(element);
				        FileEnhanced enhanced = new FileEnhanced();
						enhanced.setFile(duu);
				        
				        dupFile.add(enhanced);
				        dupSize.add(enhanced.getFile().length()+"");
				    }
				    dupFile.add(null);
			        dupSize.add("*****");
			    }
			}
			listFile.setItems(dupFile);
			listSize.setItems(dupSize);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }

    @FXML
    void doLargeFile(ActionEvent event) {
    	if(drpDrives.getValue()== null) {
			new MakeAlert("Alert", "First Select any drive. Then files will be loaded.");
			return;
		}
    	int sz= MakeAlert.showInputAlert("Input", "Enter the threshold size for large files in mb:");
    	
    	flag=1;
    	utility.reset();
    	listFile.getItems().clear();
    	listSize.getItems().clear();
    	
    	Drive d= drpDrives.getSelectionModel().getSelectedItem();
    	
    	Thread thread = new Thread(() -> {
			ObservableList<FileEnhanced> fileEnhanceds = utility.recursiveGetAllTypeFiles(
					d.getFile().listFiles(), 0);
			Platform.runLater(() -> {
				largeFile= FXCollections.observableArrayList();
				largeSize= FXCollections.observableArrayList();
				for(FileEnhanced ax: fileEnhanceds) {
		    		if(ax.getFile().length() > sz * 1024 * 1024) {
		    			largeFile.add(ax);
		    			largeSize.add(ax.getFile().length()/(1024* 1024)+"");
		    		}
		    	}
				System.out.println("Total Files :-" + fileEnhanceds.size());
				listFile.setItems(largeFile);
				listSize.setItems(largeSize);
			});
		});
		thread.start();
    }

    @FXML
    void doRare(ActionEvent event) {
    	if(drpDrives.getValue()== null) {
//			System.out.println("ALERT select drive");
			new MakeAlert("Alert", "First Select any drive. Then files will be loaded.");
			return;
		}
    	int days= MakeAlert.showInputAlert("Input", "Enter the threshold number of days for rarely used files:");
    	flag=3;
    	utility.reset();
    	listFile.getItems().clear();
    	listSize.getItems().clear();
    	
    	Drive d= drpDrives.getSelectionModel().getSelectedItem();
    	Thread thread = new Thread(() -> {
			ObservableList<FileEnhanced> fileEnhanceds = utility.recursiveGetAllTypeFiles(
					d.getFile().listFiles(), 0);
			Platform.runLater(() -> {
				rareFile= FXCollections.observableArrayList();
				rareDate= FXCollections.observableArrayList();
				for(FileEnhanced ax: fileEnhanceds) {
					long diff;
					try {
						diff = getDaysSinceLastAccess(ax.getFile()+"");
						if(diff > days) {
							rareFile.add(ax);
							rareDate.add(diff+"");
							System.out.println(ax+ " "+ diff);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
				listFile.setItems(rareFile);
				listSize.setItems(rareDate);
			});
		});
		thread.start();
    }
    
    public static long getDaysSinceLastAccess(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        long lastAccessTimeInMillis = attrs.lastAccessTime().to(TimeUnit.MILLISECONDS);
        
        long currentTimeInMillis = System.currentTimeMillis();

        return ChronoUnit.DAYS.between(
                Instant.ofEpochMilli(lastAccessTimeInMillis),
                Instant.ofEpochMilli(currentTimeInMillis)
        );
    }
    
    @FXML
    void doDelete(ActionEvent event) {
    	ObservableList<FileEnhanced> selFile= listFile.getSelectionModel().getSelectedItems();    	
    	ObservableList<Integer> selSizeIndex= listSize.getSelectionModel().getSelectedIndices();
    	ObservableList<String> selSize= listSize.getSelectionModel().getSelectedItems();
    	
    	if(selFile.contains(null)) {
    		new MakeAlert("Alert", "null Wrong item selected. Select correct files to delete");
    		return;
    	}
    	if(selSize.contains("*****")) {
    		new MakeAlert("Alert", "*****  Wrong item selected. Select correct files to delete");
    		return;
    	}
    	
    	
    	if(selFile.size()!=0 || selSizeIndex.size() !=0) {
    		boolean flg= MakeAlert.type2("Alert", "You are about to delete the selected files. Select Yes to delte");
			if(flg== false) {
				return;
			}
    		if(selFile.size()!=0) {
    			for(FileEnhanced fx: selFile) {
    				helpDel(fx.getFile());	
				 }
    		}
    		else if(selSizeIndex.size() !=0) {
    			for(int ind: selSizeIndex) {
    				helpDel(largeFile.get(ind).getFile());
    			}    			
    		}
			if(flag==1) {
				doLargeFile(null);
			}
			else if(flag==2) {
				doFindDup(null);
			}
			else if(flag==3) {
				doRare(null);
			}
    	}
    	else {
    		new MakeAlert("Alert", "Nothing is selected to delete. Select files to deleted.");
    	}
    }

    @FXML
    void initialize() {
    	utility = new Utility();
    	drive = utility.getAllDrives();
    	drpDrives.getItems().addAll(drive);
    	listFile.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	listSize.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
    }
    
    




//-----------------helper classes
    void helpDel(File fx) {
		boolean deleted = fx.delete();
		if (deleted) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
	}
// DuplicateFileDetector {

    public static String calculateChecksum(String filePath) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
        }

        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }
    
    public static Map<String, List<String>> find_Duplicates(File[] files2) throws NoSuchAlgorithmException, IOException {
        if (files2== null) {
            throw new IllegalArgumentException("Drive or directory does not exist.");
        }

        Map<String, List<String>> duplicates = new HashMap<>();
        Map<String, List<String>> filesBySizeAndExtension = group_FilesBySizeAndExtension(files2);

        for (List<String> files : filesBySizeAndExtension.values()) {
            if (files.size() > 1) {
                for (String file : files) {
                    String checksum = calculateChecksum(file);
                    duplicates.putIfAbsent(checksum, new ArrayList<>());
                    duplicates.get(checksum).add(file);
                }
            }
        }

        return duplicates;
    }
    public static Map<String, List<String>> group_FilesBySizeAndExtension(File[] arr){
    	Map<String, List<String>> filesBySizeAndExtension = new HashMap<>();
    	
    	if (arr != null) {
			for (File f : arr) {
				if (f.isFile()) {
                    long fileSize = f.length();
                    String extension = getFileExtension(f.getName());

                    String key = fileSize + "-" + extension;
                    filesBySizeAndExtension.putIfAbsent(key, new ArrayList<>());
                    filesBySizeAndExtension.get(key).add(f.getAbsolutePath());
                } else if (f.isDirectory()) {
                    filesBySizeAndExtension.putAll(group_FilesBySizeAndExtension(f.listFiles()));
                }

			}
		}
    	return filesBySizeAndExtension;
    }
}

