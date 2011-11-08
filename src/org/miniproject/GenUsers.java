package org.miniproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 *
 * @author Varun Thacker
 * This class is used to generate a random click history of a n number of users.
 * The class has code specific to the reuters data set.
 * TODO future- Check if making a random click history is useful for clustering
 * TODO future- A more efficient way to generate random click history 
 */



public class GenUsers {
 
  protected static int numberOfUsers = 10;
  protected static int numberOfStories = 100;
  
  public static void generate() throws IOException {
    // example input file
    // reut2-021.sgm-499.txt
    // 000-021 and 000 to 999
    ArrayList<Integer> list = new ArrayList<Integer>(numberOfUsers);
    for(int i = 0; i < 999; i++)
    {
      list.add(i);
    }
    FileWriter users = null;
    String link = null;
    int partOne, partTwo;

    for (int i = 0; i<numberOfUsers; i++) {    
      users = new FileWriter("/home/varun/mahout/genusers/"+Integer.toString(i));
      for (int j = 0; j<numberOfStories; j++) {
        Collections.shuffle(list);
        link = "reut2-0";
        partOne = list.get(i) % 22;
        partTwo = list.get(i);
        
        if (partOne < 10) 
          link +="0";
                
        link += partOne;  //adds 
        link +=".sgm-";    
        link += partTwo;  //adds from 0-999
        link +=".txt";  //adds .txt
        
        if (linkexists(link)) {
          users.write(link+"\n");
        }
      }
      users.close();
    }
  }
  
  public static void main(String args[]) throws IOException, InterruptedException {
    //generate();
    //BuildClusters bc = new BuildClusters();
    //bc.build();
    BuildRecommendation br = new BuildRecommendation();
    ArrayList<String> rec  = new ArrayList<String>(br.recommend(1));
    for (String s: rec) {
      System.out.println(s);
    }
  }

  
  //TODO improve this method to check if file exists
  public static boolean linkexists(String link) {
    try {
      FileReader f = new FileReader("/home/varun/mahout/reuters-out/"+link);
      f.close();
      return true;     
    }    
    
    catch (FileNotFoundException e) {
        return false;
    }    
    catch (IOException e) {
        return false;
    }
    
  }
}
