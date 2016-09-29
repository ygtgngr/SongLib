package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ListController {
	
	Stage mainStage;
	
	//TESTING
	//THIS IS A TEST
	
	//FXML REFERENCES
	   @FXML     
	   ListView<Song> listView;
	   @FXML
	   Button addButton;
	   @FXML
	   Label detailsText;
	   @FXML
	   GridPane EditGrid;
	   @FXML
	   GridPane AddGrid;
	   @FXML
	   TextField AddTitleTF;
	   @FXML
	   TextField AddArtistTF;
	   @FXML
	   TextField AddAlbumTF;
	   @FXML
	   TextField AddYearTF;
	   @FXML
	   TextField EditTitleTF;
	   @FXML
	   TextField EditArtistTF;
	   @FXML
	   TextField EditAlbumTF;
	   @FXML
	   TextField EditYearTF;
	   @FXML
	   Button SortLabel;
	   @FXML
	   Button SaveButton;
	//END FXML REFERENCES
	   private ObservableList<Song> obsList;              
	  
	   public void start(Stage mainStage) throws Exception {
		   this.mainStage = mainStage;
	      // create an ObservableList 
	      // from an ArrayList              
	      obsList = FXCollections.observableList(readDataFrom(System.getProperty("user.dir") + "/data/songdata.csv"));
	    		  /*.observableArrayList(
	    		  new Song("Hello","Adele"),
	    		  new Song("Let it be","Beatles")
	    		  */
	      
	      
	      Collections.sort(obsList, comparatorSong_byTitle);
	      
	      listView.setItems(obsList);     
	      	      
		    
			      listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Song>() {
			    	 	@Override
						public void changed(ObservableValue<? extends Song> observable, Song oldValue, Song newValue) {
							if(listView.getSelectionModel().getSelectedItem() != null){
		    					
		    					detailsText.setText("Title: " + listView.getSelectionModel().getSelectedItem().Title
		    									+"\nArtist: "+ listView.getSelectionModel().getSelectedItem().Artist);
		    					
		    					if(listView.getSelectionModel().getSelectedItem().Album != null && !listView.getSelectionModel().getSelectedItem().Album.isEmpty()){
		    						
		    						detailsText.setText(detailsText.getText()+"\nAlbum: "+ listView.getSelectionModel().getSelectedItem().Album);
						    						
		    					}
		    					if(listView.getSelectionModel().getSelectedItem().Year != null && !listView.getSelectionModel().getSelectedItem().Year.isEmpty()){
		    						
		    						detailsText.setText(detailsText.getText()+"\nYear: "+ listView.getSelectionModel().getSelectedItem().Year);
						    						
		    					}
		    				   }
		    			   else{
		    				   detailsText.setText("");
		    			   }
							

							   AddArtistTF.setText("");
							   AddTitleTF.setText("");
							   AddAlbumTF.setText("");
							   AddYearTF.setText("");

							   EditArtistTF.setText("");
							   EditTitleTF.setText("");
							   EditAlbumTF.setText("");
							   EditYearTF.setText("");
							   AllGridsHidden();
						}
			    });
	      
	      

			      // select the first item
			      listView.getSelectionModel().select(0);
	      
	   }
	   
	   
	   //COMPARING TITLES OF THE SONGS TO SORT
	   Comparator<? super Song> comparatorSong_byTitle = new Comparator<Song>() {
			@Override
			public int compare(Song o1, Song o2) {
				// TODO Auto-generated method stub
				return o1.getTitle().compareToIgnoreCase(o2.getTitle());
			}
   	  };
	   
   	  //UI STUFF
	   private void AddGridVisible(){
		   EditGrid.setVisible(false);
		   AddGrid.setVisible(true);
	   }
	   private void EditGridVisible(){
		   EditGrid.setVisible(true);
		   AddGrid.setVisible(false);
	   }
	   private void AllGridsHidden(){
		   EditGrid.setVisible(false);
		   AddGrid.setVisible(false);
	   }
	
	   //EVENT HANDLERS
	   @FXML
	   private void handleButtonAction(ActionEvent event) {	
		   AddArtistTF.setText("");
		   AddTitleTF.setText("");
		   AddAlbumTF.setText("");
		   AddYearTF.setText("");
		   AddGridVisible();
	   }
	   @FXML
	   private void SaveAddButtonAction(ActionEvent event) {
		   addItem();
	   }
	  
	   @FXML
	   private void hideAllGridsAction(ActionEvent event) {
		   AllGridsHidden();
	   }
	   
	   
	   @FXML
	   private void SaveEditButtonAction(ActionEvent event) {
		   editItem();
	   }
	   @FXML
	   private void editButtonAction(ActionEvent event) {
		   EditGridVisible();
		   if(listView.getSelectionModel().getSelectedItem() == null){
			   senderror();
			   }else{  
				   Song item = listView.getSelectionModel().getSelectedItem();
				   EditArtistTF.setText(item.Artist);
				   EditTitleTF.setText(item.Title);
				   EditAlbumTF.setText(item.Album);
				   EditYearTF.setText(item.Year);
			   }
	   }   
	   

	   @FXML
	   private void saveButtonAction(ActionEvent event) {   

	    	this.writeDataToManual();
	   }
	   

	   @FXML
	   private void sortLabelAction(ActionEvent event) {
		   	sortList();
	   }
	   
	   public void sortList(){
		      Collections.sort(obsList, comparatorSong_byTitle);

		      listView.setItems(null);
		      listView.setItems(obsList);
	   }
	   
	   @FXML
	   private void removeButtonAction(ActionEvent event) {
		   AllGridsHidden();
		   if(listView.getSelectionModel().getSelectedItem() != null){
		   obsList.remove(listView.getSelectionModel().getSelectedIndex());
		   listView.getSelectionModel().select(0);}
	   }
	   
	   //END EVENT HANDLERS 
	   
	   
	   //ADD NEW ITEM
	   private void addItem(){
		   
		   String Title = AddTitleTF.getText();
		   String Artist = AddArtistTF.getText();
		   String Album = AddAlbumTF.getText();
		   String Year = AddYearTF.getText();
		   Song song = null;		   
		   if((Title != null && !Title.trim().isEmpty()) && (Artist != null && !Artist.trim().isEmpty()) && (Album != null) && (Year != null)){		   
		   song = new Song(Title,Artist,Album,Year);
		   }
		   
		   for(Song i : obsList){
			   if(i.Title.equalsIgnoreCase(Title) && i.Artist.equalsIgnoreCase(Artist)){
				   senderror();
				   return;
			   }
		   }
		   
		   if (Title != null && !Title.isEmpty() && Artist != null && !Artist.isEmpty() && song != null && !obsList.contains(song)) { obsList.add(song); }
		   else { senderror();}

		   
		   AddArtistTF.setText("");
		   AddTitleTF.setText("");
		   AddAlbumTF.setText("");
		   AddYearTF.setText("");
		   AllGridsHidden();
		   
		   sortList();
		   
		   listView.getSelectionModel().select(song);

	}
	   //END ADD NEW ITEM
	 
	   /*
	@SuppressWarnings("unused")
	private void showItem(Stage mainStage) {                
		   Alert alert = 
				   new Alert(AlertType.INFORMATION);
		   alert.initOwner(mainStage);
		   alert.setTitle(
				   listView.getSelectionModel()
				   .getSelectedItem().Title + " Details");
		   alert.setHeaderText(
				   listView.getSelectionModel()
				   .getSelectedItem().Title);

		   String content = "Index: " + 
				   listView.getSelectionModel()
		   .getSelectedIndex() + 
		   "\nTitle: " + 
		   listView.getSelectionModel()
		   .getSelectedItem().Title +
		   "\nArtist: " + 
		   listView.getSelectionModel()
		   .getSelectedItem().Artist;

		   alert.setContentText(content);
		   alert.showAndWait();
	   }*/
	   
	   //EDIT ITEM

	   private void editItem() {                
		   Song item = listView.getSelectionModel().getSelectedItem();

		   
		   String Title = EditTitleTF.getText();
		   String Artist = EditArtistTF.getText();
		   String Album = EditAlbumTF.getText();
		   String Year = EditYearTF.getText();
		   Song song = null;		   
		   if((Title != null) && (Artist != null) && (Album != null) && (Year != null)){
		   
		   song = new Song(Title,Artist,Album,Year);
		   }
		   
		   
		   for(Song i : obsList){
			   if(i == listView.getSelectionModel().getSelectedItem()){
			   if(i.Title.equals(Title) && i.Artist.equals(Artist) && i.Album.equals(Album) && i.Year.equals(Year)){
				   senderror();
				   return;
			   }}
		   }
		   
		   
		   if(song != null && !obsList.contains(song)){
		   if (Title != null && !Title.trim().isEmpty()){item.setTitle(song.Title); }
		   if (Artist != null && !Artist.trim().isEmpty()){item.setArtist(song.Artist);}
		   if (Album != null){item.setArtist(song.Album);}
		   if (Year != null){item.setYear(song.Year);}
		   }
		   else{
			   senderror();
			   return;
		   }
		   
		   
		   listView.setItems(null);
		   listView.setItems(obsList);   
		   
		   EditArtistTF.setText("");
		   EditTitleTF.setText("");
		   EditAlbumTF.setText("");
		   EditYearTF.setText("");
		   AllGridsHidden();


		   sortList();

		   listView.getSelectionModel().select(song);
	   }
	   //END EDIT ITEM
	   
	   
	   //GENERIC ERROR
	   public void senderror(){
		   Alert alert = 
				   new Alert(AlertType.ERROR);
		   alert.initOwner(mainStage);
		   alert.setTitle("Oops");
		   String content = "Something's wrong...";
		   alert.setContentText(content);
		   alert.showAndWait();
		   
	   }
	   
	   //READ DATA
		public List<Song> readDataFrom(String uri) throws Exception{
			
			BufferedReader br;
			try{
			br = new BufferedReader(new FileReader(uri));
			}
			catch(FileNotFoundException err){
				/*PrintWriter writer = new PrintWriter(uri, "UTF-8");
				writer.print("");
				writer.close();		*/		
				File file = new File(uri);
				  if(!file.exists()){
				     file.getParentFile().mkdirs();
				     file.createNewFile();
				  }
				
				br = new BufferedReader(new FileReader(uri));		
			}
		    String line = null;
		    
		    List<Song> songList = new ArrayList<Song>();
		    while ((line = br.readLine()) != null) {
		    	if(line != ""){
		      String[] values = line.split(",");
		      if(values.length == 2){
			      songList.add(new Song(values[0],values[1],"",""));}
		      if(values.length == 3){
			      songList.add(new Song(values[0],values[1],values[2],""));
		      }
		      if(values.length == 4){
			      songList.add(new Song(values[0],values[1],values[2],values[3]));}
		      }else{
		    	  senderror();
		    	  return null;
		      }
		    }
		    br.close();

		    
		    return songList;
			
		}
	   //END READ DATA
		
	   //WRITE DATA WITHOUT NOTIFICATION
		public void writeDataTo(){
			File fnew = new File(System.getProperty("user.dir") + "/data/songdata.csv");
			String source = "";
			
			boolean first = true;
			for (Song song : obsList) {
				if(first){
	        	source = song.getTitle() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getYear();	 
	        	}else{
	        		source = source + "\n" + song.getTitle() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getYear();	 
	        	}
				first = false;
	        }
			
			try {
			    FileWriter filewriter = new FileWriter(fnew, false);
			    filewriter.write(source);
			    filewriter.close();
				}
				catch (IOException e) {
				    e.printStackTrace();
				} 
			
			}
		//END WRITE DATA WITHOUT NOTIFICATION
		
		//WRITE DATA WITH NOTIFICATION
		public void writeDataToManual(){
			File fnew = new File(System.getProperty("user.dir") + "/data/songdata.csv");
			String source = "";
			
			boolean first = true;
			for (Song song : obsList) {
				if(first){
	        	source = song.getTitle() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getYear();	 
	        	}else{
	        		source = source + "\n" + song.getTitle() + "," + song.getArtist() + "," + song.getAlbum() + "," + song.getYear();	
	        	}
				first = false;
	        }
			
			try {
			    FileWriter filewriter = new FileWriter(fnew, false);
			    filewriter.write(source);
			    filewriter.close();
				}
				catch (IOException e) {
				    e.printStackTrace();
				} 

			   Alert alert = 
					   new Alert(AlertType.INFORMATION);
			   alert.initOwner(mainStage);
			   alert.setTitle("Saved");
			   String content = "Your data is saved...";
			   alert.setContentText(content);
			   alert.showAndWait();
			
			}
		//END WRITE DATA WITH NOTIFICATION
		
	   

}
