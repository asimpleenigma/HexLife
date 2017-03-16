/*
 */
package hexlife;

import java.util.*;
/**
 * @author Lloyd Cloer
 */
public class HexModel {
    int n_rows;
    int n_cols;
    boolean[][] world;
    private boolean[][] next_world;
    public RuleSet rule_set;
    public HashMap<String, RuleSet> rule_sets;
    

    
    public HexModel(int n_rows, int n_cols){
        this.n_rows = n_rows;
        this.n_cols = n_cols;
        this.world = new boolean[n_rows][n_cols];
        this.world[n_rows/2][n_cols/2] = true;
        this.next_world = new boolean[n_rows][n_cols];
        
        //countNeighbors(3,2,true);
        //System.out.println("hello");
        rule_sets = new HashMap<String, RuleSet>();
        
        RuleList rl = SearchWorlds.readFile();
        
        float min = (float) .17;
        float max = (float) .18;
        float min2 = (float) .00;
        int k = 0;
        for (RuleSet rs : rl.RL){
            if ((rs.first_deriv < max)&&(rs.first_deriv >min)&&(rs.cycle_2 > 0.05)&&(rs.second_deriv > min2)){
                rule_sets.put(rs.name, rs);
                k++;
            }
        }
        System.out.println(k);
//35/02356        
        
        //RuleSet day_night = new RuleSet("Day And Nigth", new int[]{3,5,6}, new int[]{2,4,5,6}); // B / S
        
        //rule_set = day_night;
        
        //RuleSet night_lattice = new RuleSet("Conway's Life", new int[]{3}, new int[]{3,4,5}); 
        //rule_set = night_lattice;
        ArrayList<Integer> b = new ArrayList();
        //b.add(1);
        int[] ba = new int[]{3};
        int[] sa = new int[]{3,4,5};
        for (int bb : ba){
            b.add(bb);
        }
        
        ArrayList<Integer> s = new ArrayList();
        for (int ss : sa){
            s.add(ss);
        }
        //s.add(0);
        //s.add(3);
        rule_set = new RuleSet("Night Lattice", b, s); 
        
        rule_sets.put(rule_set.name, rule_set);
        
        b = new ArrayList();
        //b.add(1);
        ba = new int[]{4};
        sa = new int[]{3,4,5};
        for (int bb : ba){
            b.add(bb);
        }
        
        s = new ArrayList();
        for (int ss : sa){
            s.add(ss);
        }
        rule_set = new RuleSet("Lattice", b, s); 
        rule_sets.put(rule_set.name, rule_set);
        
        
    }
    
    public void evolveWorld(){
        for(int row = 0; row < n_rows; row++) //for each cell
            for (int col = 0; col < n_cols; col++)
                next_world[row][col] = evolveCell(row, col);
        world = next_world;
        next_world = new boolean[n_rows][n_cols];
        //System.out.println(world[1][1]);
        //System.out.println(countNeighbors(1,1, true));
        //System.out.println(" ");
    }
    
    private boolean evolveCell(int row, int col){
        int n_neighbors = countNeighbors(row, col, false);
        return rule_set.nextState(world[row][col], n_neighbors);
    }
    
    public void randomizeWorld(){
        for(int row = 0; row < n_rows; row++)
            for (int col = 0; col < n_cols; col++)
                world[row][col] = Math.random() > .5; // random initialization
        //repaint();
    }
    public void clearWorld(){
        world = new boolean[n_rows][n_cols];
        //repaint();
    }    
    private int countNeighbors(int row, int col, boolean debug){
        //boolean debug = true;
        int n_neighbors = 0;
        for (int r = -1; r <= 1; r++){
            for (int c = 0; c <= 1; c++){ // neighbors 
                int cx = c;
                if ((row%2 == 1)&&(r!=0)) cx--; // if odd row, and we are looking row above or below, reduce column
                if (r==0 && c==0){ cx = -1;} // if r = 0, c = 0 --> c = -1 
                if (world[(row+r+n_rows)%n_rows][(col+cx+n_cols)%n_cols]){
                    n_neighbors++;
                    if (debug && row==1 && col==1){
                        //System.out.println("("+(row+r)+", "+(col+cx)+")");
                    }
                }
                //world[(row+r+n_rows)%n_rows][(col+cx+n_cols)%n_cols] = true;
                //System.out.println(r +" " + cx);
                //world[(row+r+n_rows)%n_rows][(col+cx+n_cols)%n_cols] = true;
            }
        }
        if (debug && row==1 && col==1){
            System.out.println(n_neighbors);
        }
        //repaint();
        return n_neighbors;
    }
}
