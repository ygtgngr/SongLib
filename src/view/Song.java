package view;

import java.util.Date;

public class Song
{
	String Title;
	String Artist;
	String Album;
	String Year;
/* public Song(String title, String artist){
	 Title = title;
	 Artist = artist;
 }	*/
 public Song(String title, String artist,String album , String year){
	 Title = title;
	 Artist = artist;
	 Album = album;
	 Year = year;
 }	
 
 @Override
 public String toString() {
     //return name + list; 
     return Title;
    		 //+ "\n" + "\t" + Artist;          
 }
 
 public String getTitle(){
	 return this.Title;
 }

 public void setTitle(String Title){
	 this.Title = Title;
 }

 public String getYear(){
	 return this.Year;
 }

 public void setYear(String Year){
	 this.Year = Year;
 }
 
 public String getArtist(){
	 return this.Artist;
 }

 public void setArtist(String Artist){
	 this.Artist = Artist;
 }
 
 public String getAlbum(){
	 return this.Album;
 }

 public void setAlbum(String Album){
	 this.Album = Album;}
 
 
 
}
