package org.miniproject;

import java.io.IOException;
import java.util.ArrayList;

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
  
  private void pickUserCluster() {
    int a = GenUsers.numberOfUsers;
  }
  
  private void pickItemCluster() {
    
  }

  private void buildCandidateGeneration(int user) {
    //TODO use the twitter api among other stuff
  }
  public ArrayList<Integer> recommend(int user) {
    return null;
  }


}
