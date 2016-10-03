package view;
/*
 * Yigit Gungor
 * matthew kalitasr 
 * csc 213
 *  Assignment 1
 * 
 */

//import java.util.Date; not used 

public class Song
{
	String Title;
	String Artist;
	String Album;
	String Year;
	
	
	//default constructor not used in current program
 public Song(){
	 
 }	
 
 // overloaded constructor takes 4 string arguments and instantiates objects string variables 
 public Song(String title, String artist,String album , String year){
	 this.Title = title;
	 this.Artist = artist;
	 this.Album = album;
	 this.Year = year;
 }	
 
 @Override
 // Override of objects toString method when called returns string Title 
 public String toString() {
     //return name + list; 
     return Title;
    		 //+ "\n" + "\t" + Artist;          
 }
 
 // Assessor method returns title String  variable 
 public String getTitle(){
	 return this.Title;
 }

 
 // mutator method allows setting of string variable title in object 
 public void setTitle(String Title){
	 this.Title = Title;
 }

 
 
//Assessor method returns year String variable 
 public String getYear(){
	 return this.Year;
 }

 
//mutator method allows setting of string variable Year in object 
 public void setYear(String Year){
	 this.Year = Year;
 }
 
 
//Assessor method returns year Artist variable  
 public String getArtist(){
	 return this.Artist;
 }

 
//mutator method allows setting of string variable artist  in object 
 public void setArtist(String Artist){
	 this.Artist = Artist;
 }
 
 
//Assessor method returns Album String variable 
 public String getAlbum(){
	 return this.Album;
 }

 
//mutator method allows setting of string variable albulm in object 
 public void setAlbum(String Album){
	 this.Album = Album;}
 
 
 
}
