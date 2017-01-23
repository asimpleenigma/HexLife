/*
 */
package hexlife;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author Lloyd Cloer
 */
public class Rules {
    public HashMap<String, RuleSet> rule_sets;
    
    public Rules(){
        initRules();
        rule_sets = RuleSet.rule_sets;
    }
    
    private Point[] translate(Point[] pattern, int x, int y){
        Point [] result = new Point[pattern.length];
        Point p;
        for (int i = 0; i < pattern.length; i++){
            p = (Point) pattern[i].getLocation();
            p.translate(x, y);
            result[i] = p;
        }
        return result;
    }
    //private Point[] rotate(Point[] pattern){}
    private Point[] concatenate(Point[] b, Point[] a){
        int a_len = a.length;
        int b_len = b.length;
        Point[] c = new Point[a_len+b_len];
        System.arraycopy(a, 0, c, 0, a_len);
        System.arraycopy(b, 0, c, a_len, b_len);
        return c;
    }
    private void initRules(){ //upon construction rulesets are added to static RuleSet hashmap
        //rule_sets = new HashMap<String, RuleSet>();
        //RuleSet rules = new RuleSet("Conway's Life", new int[]{3}, new int[]{2,3}); 
            //rules.addPattern("Toad", "t", new Point[]{new Point(0,0), new Point(1,0))  
            
        //rules = new RuleSet("Makenzey's Life", new int[]{2}, new int[]{4});
    }
    public Point[] BoxN(int n){
        Point[] p = new Point[n*n];
        for (int i = 0 ; i < n; i++){
            for (int j = 0 ; j < n; j++){
                p[i*n+j] = new Point(i, j);
            }
        }
        return p;
    }
    public Point[] DiagonalN(int n){
        Point[] p = new Point[n*(n+1)/2];
        int k = 0;
        for (int i = 0 ; i < n; i++){
            for (int j = 0 ; j < n; j++){
                if (i<=j){
                    p[k] = new Point(i, j);
                    k++;
                }    
            }
        }
        return p;
    }
    public Point[] TriangleN(int n){
        int m = 0;
        for (int i = 0 ; i < n; i++){
            for (int j = i ; j < n-i; j++){
                m++;
            }
        }
        Point[] p = new Point[m];
        int k = 0;
        for (int i = 0 ; i < n; i++){
            for (int j = i ; j < n-i; j++){
                p[k] = new Point(i, j);
                k++;
            }
        }
        return p;
    }
}
