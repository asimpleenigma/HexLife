/*
 */
package hexlife;

import java.io.*;
import java.util.*;
/**
 */
public class RuleList implements Serializable{
    ArrayList<RuleSet> RL;
    
    public RuleList(){
        RL = new ArrayList<RuleSet>();
        ArrayList<ArrayList<Integer>> power_set = powerSet(7);
        for (ArrayList<Integer> B: power_set){ // populate the Rule List with the square of the power set.
            for (ArrayList<Integer> S : power_set){
                RuleSet rs = new RuleSet("", B, S);
                RL.add(rs);
            }
        }
    }
    public static ArrayList<ArrayList<Integer>> powerSet(int n){
        ArrayList<ArrayList<Integer>> power_set = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        int k = 0;
        for (int r = 0; r < Math.pow(2, n); r++){ // for each rule set
            a = new ArrayList<Integer>(); // clear rules
            int j = k;
            for (int b = 0; b < n; b++){ // for each possible number of neighbors
                if (j % 2 == 1){ // if last digit is 1;
                    a.add(b); // add number
                }
                j /= 2; // divide j by 2
            }
            k++;
            power_set.add(a);
        }
        return power_set;
    }
    /*
        //RuleSet rs;
        //BL = new ArrayList<int[]>();
        //SL = new ArrayList<int[]>();
        //rs = new RuleSet("gibber", new int[]{1,3,4}, new int[]{4,6,7});
        //RuleSet rt = new RuleSet("gibber", new int[]{1,2,4,5}, new int[]{4,9,7});
        //RL = new ArrayList<RuleSet>();
        //RL.add(rs);
        //RL.add(rt);
        /*
        BL = new ArrayList<int[]>();
        BL.add(new int[]{1,3,5});
        BL.add(new int[]{2,2,6,8});
        
        
        SL = new ArrayList<int[]>();
        SL.add(new int[]{1,3,7});
        SL.add(new int[]{4,8});
        //a = new RuleSet("", new int[]{1,3,4},new int[]{6,7});
        
    }
    */
}
