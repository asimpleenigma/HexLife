/*
 */
package hexlife;

import java.awt.Point;
import java.util.*;
import java.io.*;


/**
 *
 * @author Lloyd 
 */
public class RuleSet implements Serializable{
    static HashMap rule_sets = new HashMap<String, RuleSet>();
    String name;//, notation;
    ArrayList<Integer> B; ArrayList<Integer> S; // number of neighbors for birth and survival
    HashMap patterns; // pattern name to point array
    String legend; // instructions to use hotkeys
    float first_deriv; // avg first derivative
    float second_deriv; // avg first derivative
    float cycle_2;

    RuleSet(String name, ArrayList<Integer> B, ArrayList<Integer> S){
        this.B = B; this.S = S; this.legend = "Hotkeys: <br>";
        name += " (B";
        for (int b : B) name += b;
        name += "/S";
        for (int s : S) name += s;
        name += ")";
        this.name = name;
        rule_sets.put(name, this);
        patterns = new HashMap<String, Point[]>();
    }
    public void addPattern(String pattern_name, String hotkey, Point[] pattern){
        legend = legend + hotkey.toUpperCase() + " : " + pattern_name + "<br>";
        patterns.put(hotkey, pattern);
    }
    public boolean nextState(boolean currentState, int n_neighbors){
        if (currentState){
            for (int s : S)
                if (n_neighbors == s) return true;
        } else
            for (int b : B)
                if (n_neighbors == b) return true;
        return false;
    }
    public String toString(){
        String s = name;
        s += "\n  1st Deriv: " + first_deriv;
        s += "\n  2nd Deriv: " + second_deriv;
        s += "\n  2-cycke:   " + cycle_2 + '\n';
        return s;
    }
}
