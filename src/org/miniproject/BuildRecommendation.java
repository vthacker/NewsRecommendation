package org.miniproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * 
 * @author Varun Thacker
 * 
 * Once we have clustered the users and/or items to the desired extent and computed the cluster-cluster
 * utility matrix, we can estimate entries in the original utility matrix as follows.
 * Suppose we want to predict the entry for user U and item I:
 *  
 * (a) Find the clusters to which U and I belong, say clusters C and D, respectively.
 *
 * (b) If the entry in the cluster-cluster utility matrix for C and D is something other than blank,
 * use this value as the estimated value for the U –I entry in the original utility matrix.
 *  
 * (c) If the entry for C–D is blank, then use the method outlined in Section 9.3.2 to estimate that entry
 * by considering clusters similar to C or D. Use the resulting estimate as the estimate for the U -I entry.
 */

public class BuildRecommendation {
  
  private static int numberOfCandidateStories = 40;
  
  private boolean worthRecommending(ArrayList<Integer> userCluster, ArrayList<String> itemCluster) {
    // TODO Auto-generated method stub
    return false;
  }
  
  public ArrayList<String> generateCanditate() throws IOException {
    //TODO this is the same code as the generateFunction in GenUsers without the file i/o. decouple it and use that.
    // example input file
    // reut2-021.sgm-499.txt
    // 000-021 and 000 to 999
    ArrayList<Integer> list = new ArrayList<Integer>(GenUsers.numberOfUsers);
    ArrayList<String> candidate = new ArrayList<String>();
    for(int i = 0; i < 999; i++)
    {
      list.add(i);
    }
    String link = null;
    int partOne, partTwo;

    for (int j = 0; j<numberOfCandidateStories; j++) {
        Collections.shuffle(list);
        link = "reut2-0";
        partOne = list.get(j) % 22;
        partTwo = list.get(j);
        
        if (partOne < 10) 
          link +="0";
                
        link += partOne;  //adds 
        link +=".sgm-";    
        link += partTwo;  //adds from 0-999
        link +=".txt";  //adds .txt
        
        if (GenUsers.linkexists(link)) {
          candidate.add(link);
        }
      }
    return candidate;
    }

  
  private ArrayList<Integer> findUserCluster(int user) throws NumberFormatException, IOException {
    FileReader userRecords;
    userRecords = new FileReader("/home/varun/mahout/UserCluster/"+Integer.toString(user));
    BufferedReader br;
    br = new BufferedReader(userRecords);
    ArrayList<Integer> userCluster = new ArrayList<Integer>();
    String s;
    while((s = br.readLine()) != null) {
      userCluster.add(Integer.parseInt(s));
    }
    return userCluster;
  }
  
  private ArrayList<String> findItemCluster(String story) throws IOException {
    
    //TODO improve efficiency of the method use to find all stories in cluster
    FileReader itemRecords;
    itemRecords = new FileReader("/home/varun/mahout/MinHash/part-r-00000");
    BufferedReader br;
    br = new BufferedReader(itemRecords);
    ArrayList<String> itemCluster = new ArrayList<String>();
    String s, cid[], clusterNumber = null;
    while((s = br.readLine()) != null) {
      cid = s.split(" ");
      if ( cid[1].equals(story))
        clusterNumber = cid[0];
    }
    br.close();
    itemRecords.close();
    
    itemRecords = new FileReader("/home/varun/mahout/MinHash/part-r-00000");
    br = new BufferedReader(itemRecords);
    
    if (clusterNumber == null) 
      return null;
    
    while((s = br.readLine()) != null) {
      cid = s.split(" ");
      if ( cid[0].equals(clusterNumber)) {
          itemCluster.add(cid[1]);
      }
        
    }
    return itemCluster;
  }
  

  private ArrayList<String> buildCandidateGeneration(int user) throws IOException {
    //TODO use the twitter trends API call among other stuff to build candidate generation
    //Currently I will assume that a randomly generated set of say 50 stories will serve as the Candidate Generator
    return (generateCanditate());
    
  }
  public ArrayList<String> recommend(int user) throws IOException {
    ArrayList<String> candidate = new ArrayList<String> ();
    ArrayList<String> itemCluster = new ArrayList<String> ();
    ArrayList<Integer> userCluster = new ArrayList<Integer> ();
    ArrayList<String> reco = new ArrayList<String>();
    
    candidate =  buildCandidateGeneration(user);
    userCluster =  findUserCluster(user);
    for (String story : candidate) {
      itemCluster = findItemCluster(story);
      if (worthRecommending(userCluster, itemCluster)) {
        reco.add(story);        
      }
    }
    return reco;
  }
  

}
