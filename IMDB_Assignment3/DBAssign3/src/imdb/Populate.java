package imdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Populate {
	public static Connection connect = null;
	public static Statement stat = null;
	public static String query = null;
	//public static final int batchSize = 20000;
    public static int count = 0;
    public static long startTime = System.currentTimeMillis();
	
	public static Connection jdbc_query()
    {
    	// Connect to Oracle 11g
        try{  
	        // Load the driver class
			if(count==0){
				
			    Class.forName("oracle.jdbc.driver.OracleDriver");  
	              
	            // Create connection object  
				connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","imdb","imdb");
				count++;
			    //st =con.createStatement();
			}

	    } catch(Exception e)
		{ 
			System.out.println(e);
			System.exit(0);
		} 
        return connect;
	}

    // Close connection
    public static void jdbc_close()
    {
    	try {
			connect.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public static void main(String args[]) throws Exception{
    	
    	//Connecting to the Database
    	connect=jdbc_query();
    	
    	//reading from Movies - start
    	String deleteQuery = "Delete from Movies";
    	query = "Insert into movies values(?,?,?,?,?,?,?,?,?)";
    	PreparedStatement deletestat = connect.prepareStatement(deleteQuery);
    	deletestat.executeUpdate();
        PreparedStatement insertstat = connect.prepareStatement(query);
        count = 0;
        startTime = System.currentTimeMillis();
        ArrayList<Movies> MoviesList = readMovies(args[0]);
        System.out.println("Time Taken for inserting into Movies " + (System.currentTimeMillis() - startTime));
        System.out.println("size" + MoviesList.size());

        for (int i = 0; i < MoviesList.size(); i++) {
        	insertstat.setInt(1, MoviesList.get(i).getMovieID());
        	insertstat.setString(2, MoviesList.get(i).getTitle());
        	insertstat.setInt(3, MoviesList.get(i).getMovieYear());
        	insertstat.setDouble(4, MoviesList.get(i).getRtAllCriticsRating());
        	insertstat.setInt(5, MoviesList.get(i).getRtAllCriticsNumReviews());
        	insertstat.setDouble(6, MoviesList.get(i).getRtTopCriticsRating());
        	insertstat.setInt(7, MoviesList.get(i).getRtTopCriticsNumReviews());
        	insertstat.setDouble(8, MoviesList.get(i).getRtAudienceRating());
        	insertstat.setInt(9, MoviesList.get(i).getRtAudienceNumRating());
        	     
        	insertstat.executeUpdate();
        }
        
        System.out.println("Movies" + insertstat);
        //reading from Movies - end
        
    	//reading from Tags - start
    	deleteQuery = "DELETE FROM TAGS";
    	query = " INSERT INTO TAGS VALUES (?,?)";
    	deletestat = connect.prepareStatement(deleteQuery);
    	deletestat.executeUpdate();
        insertstat = connect.prepareStatement(query);
        count = 0;
        startTime = System.currentTimeMillis();
        ArrayList<Tags> tagsList = readTags(args[1]);
        System.out.println("Time Taken for inserting into Tags " + (System.currentTimeMillis() - startTime));
        System.out.println("size" + tagsList.size());

        for (int i = 0; i < tagsList.size(); i++) {
        	insertstat.setInt(1, tagsList.get(i).getTagID());
        	insertstat.setString(2, tagsList.get(i).getTagValue());               
        	insertstat.executeUpdate();
        }
        
        System.out.println("Tags" + insertstat);
        //reading from tags - end
        
      //reading from Movie_tags - start
    	deleteQuery = "Delete from Movie_tags";
    	query = "Insert into movie_tags values(?,?,?)";
    	deletestat = connect.prepareStatement(deleteQuery);
    	deletestat.executeUpdate();
        insertstat = connect.prepareStatement(query);
        count = 0;
        startTime = System.currentTimeMillis();
        ArrayList<Movie_tags> MovieTagList = readMovieTag(args[2]);
        System.out.println("Time Taken for inserting into Movies_tags " + (System.currentTimeMillis() - startTime));
        System.out.println("size" + MovieTagList.size());

        for (int i = 0; i < MovieTagList.size(); i++) {
        	insertstat.setInt(1, MovieTagList.get(i).getMovieID());
        	insertstat.setInt(2, MovieTagList.get(i).getTagID());
        	insertstat.setInt(3, MovieTagList.get(i).getTagWeight());
        	insertstat.executeUpdate();
        }
        
        System.out.println("Movie_tags" + insertstat);
        //reading from Movie_tag- end

      //reading from Movie_countries- start
    	deleteQuery = "Delete from Movie_countries";
    	query = "Insert into movie_countries values(?,?)";
    	deletestat = connect.prepareStatement(deleteQuery);
    	deletestat.executeUpdate();
        insertstat = connect.prepareStatement(query);
        count = 0;
        startTime = System.currentTimeMillis();
        ArrayList<Movie_countries> MovieCntryList = readMovieCntry(args[3]);
        System.out.println("Time Taken for inserting into Movies_countries " + (System.currentTimeMillis() - startTime));
        System.out.println("size" + MovieCntryList.size());

        for (int i = 0; i < MovieCntryList.size(); i++) {
        	insertstat.setInt(1, MovieCntryList.get(i).getMovieID());
        	insertstat.setString(2, MovieCntryList.get(i).getCountry());
        	
        	insertstat.executeUpdate();
        }
        
        System.out.println("Movie_countries" + insertstat);
        //reading from Movie_countries- end
        
        //reading from Movie_genres- start
      	deleteQuery = "Delete from Movie_genres";
      	query = "Insert into movie_genres values(?,?)";
      	deletestat = connect.prepareStatement(deleteQuery);
      	deletestat.executeUpdate();
          insertstat = connect.prepareStatement(query);
          count = 0;
          startTime = System.currentTimeMillis();
          ArrayList<Movie_genres> MovieGenreList = readMovieGenre(args[4]);
          System.out.println("Time Taken for inserting into Movies_genres " + (System.currentTimeMillis() - startTime));
          System.out.println("size" + MovieGenreList.size());

          for (int i = 0; i < MovieGenreList.size(); i++) {
          	insertstat.setInt(1, MovieGenreList.get(i).getMovieID());
          	insertstat.setString(2, MovieGenreList.get(i).getGenre());
          	
          	insertstat.executeUpdate();
          }
          
          System.out.println("Movie_genres" + insertstat);
          //reading from Movie_genres end
          
          //reading from Movie_genres- start
        	deleteQuery = "Delete from Movie_locations";
        	query = "Insert into movie_locations values(?,?)";
        	deletestat = connect.prepareStatement(deleteQuery);
        	deletestat.executeUpdate();
            insertstat = connect.prepareStatement(query);
            count = 0;
            startTime = System.currentTimeMillis();
            ArrayList<Movie_locations> MovieLocList = readMovieLoc(args[5]);
            System.out.println("Time Taken for inserting into Movies_locations " + (System.currentTimeMillis() - startTime));
            System.out.println("size" + MovieLocList.size());

            for (int i = 0; i < MovieLocList.size(); i++) {
            	insertstat.setInt(1, MovieLocList.get(i).getMovieID());
            	insertstat.setString(2, MovieLocList.get(i).getLocation());
            	
            	insertstat.executeUpdate();
            }
            
            System.out.println("Movie_locations" + insertstat);
            //reading from Movie_genres end
        //close connection
        connect.close();

        System.exit(0);
    
    }
    
    public static ArrayList<Tags> readTags(String filepath) throws Exception{
		
		//File file = new File(filepath);
		ArrayList<Tags> listResult = new ArrayList<Tags>();

        try {

            Scanner sc = new Scanner((new FileInputStream(filepath)), "UTF-8");
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                //System.out.println("Line " + line);
                String[] column = line.split("\t");

                Tags tg = new Tags();
                tg.setTagID(Integer.parseInt(column[0]));
                tg.setTagValue(column[1]);
                listResult.add(tg);

            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return listResult;
        
	}
    public static ArrayList<Movies> readMovies(String filepath) throws Exception{
		
		//File file = new File(filepath);
		ArrayList<Movies> listResult = new ArrayList<Movies>();
	
	    try {
	
	    	Scanner sc = new Scanner((new FileInputStream(filepath)), "UTF-8");
	        sc.nextLine();
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            //System.out.println("Line " + line);
	            String[] column = line.split("\t");
	            Movies mov = new Movies();
	            mov.setMovieID(Integer.parseInt(column[0]));
	            mov.setTitle(column[1]);
	            mov.setMovieYear(Integer.parseInt(column[5]));
	            if (column[7] != null && !column[7].equals(" ") && !column[7].equals("\\N")) {
	              	mov.setRtAllCriticsRating(Double.parseDouble(column[7]));
	            }
	            if (column[8] != null && !column[8].equals(" ") && !column[8].equals("\\N")) {
	               	mov.setRtAllCriticsNumReviews(Integer.parseInt(column[8]));
	            }
	            if (column[12] != null && !column[12].equals(" ") && !column[12].equals("\\N")) {
	               	mov.setRtTopCriticsRating(Double.parseDouble(column[12]));
	            }
	            if (column[13] != null && !column[13].equals(" ") && !column[13].equals("\\N")) {
	               	mov.setRtTopCriticsNumReviews(Integer.parseInt(column[13]));
	            }
	            if (column[17] != null && !column[17].equals(" ") && !column[17].equals("\\N")) {
	              	mov.setRtAudienceRating(Double.parseDouble(column[17]));
	            }
	            if (column[18] != null && !column[18].equals(" ") && !column[18].equals("\\N")) {
	              	mov.setRtAudienceNumRating(Integer.parseInt(column[18]));
	            }
	            
	            listResult.add(mov);
	
	        }
	        sc.close();
	
	    } catch (FileNotFoundException e) {
	         e.printStackTrace();
	    }
	    return listResult;
	      
    }

	
	public static ArrayList<Movie_tags> readMovieTag(String filepath) throws Exception{
		
		//File file = new File(filepath);
		ArrayList<Movie_tags> listResult = new ArrayList<Movie_tags>();
	
	    try {
	
	        Scanner sc = new Scanner((new FileInputStream(filepath)), "UTF-8");
	        sc.nextLine();
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            //System.out.println("Line " + line);
	            String[] column = line.split("\t");
	
	            Movie_tags movtg = new Movie_tags();
	            movtg.setMovieID(Integer.parseInt(column[0]));
	            movtg.setTagID(Integer.parseInt(column[1]));
	            movtg.setTagWeight(Integer.parseInt(column[2]));
	            listResult.add(movtg);
	
	        }
	        sc.close();
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return listResult;
	    
	}
	public static ArrayList<Movie_countries> readMovieCntry(String filepath) throws Exception{
		
		//File file = new File(filepath);
		ArrayList<Movie_countries> listResult = new ArrayList<Movie_countries>();
	
	    try {
	
	        Scanner sc = new Scanner((new FileInputStream(filepath)), "UTF-8");
	        sc.nextLine();
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            //System.out.println("Line " + line);
	            String[] column = line.split("\t");
	            if (column.length < 2) {
                    continue;
                }
	            Movie_countries movcnt = new Movie_countries();
	            movcnt.setMovieID(Integer.parseInt(column[0]));
	            movcnt.setCountry(column[1]);
	            
	            listResult.add(movcnt);
	
	        }
	        sc.close();
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return listResult;
	    
	}
	public static ArrayList<Movie_genres> readMovieGenre(String filepath) throws Exception{
		
		//File file = new File(filepath);
		ArrayList<Movie_genres> listResult = new ArrayList<Movie_genres>();
	
	    try {
	
	        Scanner sc = new Scanner((new FileInputStream(filepath)), "UTF-8");
	        sc.nextLine();
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            //System.out.println("Line " + line);
	            String[] column = line.split("\t");
	
	            Movie_genres movgen = new Movie_genres();
	            movgen.setMovieID(Integer.parseInt(column[0]));
	            movgen.setGenre(column[1]);
	            listResult.add(movgen);
	
	        }
	        sc.close();
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return listResult;
	    
	}
	public static ArrayList<Movie_locations> readMovieLoc(String filepath) throws Exception{
		
		//File file = new File(filepath);
		ArrayList<Movie_locations> listResult = new ArrayList<Movie_locations>();
	
	    try {
	
	        Scanner sc = new Scanner((new FileInputStream(filepath)), "UTF-8");
	        sc.nextLine();
	        while (sc.hasNextLine()) {
	            String line = sc.nextLine();
	            //System.out.println("Line " + line);
	            String[] column = line.split("\t");
	            if (column.length < 2) {
                    continue;
                }
	            Movie_locations movloc = new Movie_locations();
	            movloc.setMovieID(Integer.parseInt(column[0]));
	            movloc.setLocation(column[1]);
	            listResult.add(movloc);
	
	        }
	        sc.close();
	
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    return listResult;
	    
	}
}