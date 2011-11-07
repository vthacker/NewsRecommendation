package org.miniproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Varun Thacker
 * 
 * Basic aim of this class is to generate clusters using the MinHash algorithm.
 * We need to call the Mahout MinHash implementation from here.
 * CLustering will be done on both items and users.
 *
 */

public class BuildClusters {

  public static int clusterCount = 0; 
  
  /**
   * Compute Jaccard coefficient over two sets. (A intersect B) / (A union B)
   */
  private static double computeSimilarity(Iterable<String> listenerVector1, Iterable<String> listenerVector2) {
    Set<String> first = new HashSet<String>();
    for (String ele : listenerVector1) {
      first.add(ele);
    }
    Collection<String> second = new HashSet<String>();
    for (String ele : listenerVector2) {
      second.add(ele);
    }

    Collection<String> intersection = new HashSet<String>(first);
    intersection.retainAll(second);
    double intersectSize = intersection.size();

    first.addAll(second);
    double unionSize = first.size();
    return unionSize == 0 ? 0.0 : intersectSize / unionSize;
  }
  
  /** 
   * Returns the best matches for person from the prefs dictionary.
   * Number of results and similarity function are optional params.
   * @return 
   * @throws IOException 
   */
  
  public ArrayList<Integer> topMatches(int user) throws IOException {
    int count = 0;

    FileReader userRecords;
    userRecords = new FileReader("/home/varun/mahout/genusers/"+Integer.toString(user));
    BufferedReader br;
    br = new BufferedReader(userRecords);
    String s;
    ArrayList<String> listenVector1 = new ArrayList<String>();
    ArrayList<String> listenVector2 = new ArrayList<String>();
    ArrayList<Integer> cluster = new ArrayList<Integer>();
    cluster.add(user);
    while((s = br.readLine()) != null) {
      listenVector1.add(s);
    }
    
    br.close();
    userRecords.close();

    for (int i =0; i<GenUsers.numberOfUsers; i++) {
      if (i != user) {
        userRecords = new FileReader("/home/varun/mahout/genusers/"+Integer.toString(i));
        br = new BufferedReader(userRecords);
        while((s = br.readLine()) != null) {
          listenVector2.add(s);
        }
        double sim = computeSimilarity(listenVector1, listenVector2);        
        if (sim*100 > 90.0) {
          count ++;
          cluster.add(i);
          System.out.println(sim);
          System.out.println(count);
        }
        br.close();
        userRecords.close();
        listenVector2.clear();
       
      }      
      count = 0;
    }
    
    return cluster;
  }

  
  public void clusterItems() {
    //TODO call MinHash from here
    //currently using pre compute minhash on items located in file part-r-00000 
    
  }
  
  public void clusterUsers() throws IOException {
    ArrayList <Integer> userCluster = new ArrayList<Integer>();
    for (int i=0; i< GenUsers.numberOfUsers; i++) {
      userCluster = topMatches(i);
      
      if (userCluster.size() > 1 ) {
        FileWriter fr = new FileWriter("/home/varun/mahout/UserCluster/"+Integer.toString(clusterCount));
        for (int j =0; j<userCluster.size(); j++) {
          fr.write(userCluster.get(j)+"\n");
        }
        fr.close();
        clusterCount++;
      }      
    }
    //Update/Create new utility matrix

    
  }
  

  
  
  public void build() throws IOException {
    clusterItems();
    clusterUsers();
    //TODO remove dumplicates eg.. 1st cluster has 1&2 and 2nd cluster has 2&1 -- it is the same.
  }
  
}
