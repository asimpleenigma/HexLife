/*
 */
package hexlife;


import java.io.IOException;
import java.io.*; //FileInputStream;
import java.util.*;
/**
 *
 * @author Lloyd Cloer
 */
public class SearchWorlds implements Serializable{
    
    public static RuleList search(){
        RuleList rl = new RuleList(); // rule list populated with all possible rule sets
        
        int n_rows = 64;
        int n_cols = n_rows*2;
        HexModel model = new HexModel(n_rows, n_cols);
        for (RuleSet rs : rl.RL){
            model.rule_set = rs;
            model.randomizeWorld();
            for (int t=0; t<137; t++){
                model.evolveWorld();
            }
            boolean[][] w0 = model.world;
            model.evolveWorld();
            boolean[][] w1 = model.world;
            model.evolveWorld();
            boolean[][] w2 = model.world;
            float[][] d1 = subtractArrays(w1, w0); // first derivative
            float[][] d2 = subtractArrays(w2, w1); // first derivative
            float[][] d3 = subtractArrays(w2, w0); // 2-cycles
            float[][] dd = subtractArrays(d2, d1); // second derivative
            rs.first_deriv = avgArray(d1);
            rs.second_deriv = avgArray(dd);
            rs.cycle_2 = avgArray(d3);
        }
        
        return rl;
    }
    public static float[][] subtractArrays(boolean[][] a, boolean[][] b){
        float[][] c = new float[a.length][a[0].length];
        for (int i = 0; i<a.length; i++){
            for (int j = 0; j<a[0].length; j++){
                if (a[i][j] != b[i][j]){
                    c[i][j] = 1;
                } else{
                    c[i][j] = 0;
                }
            }
        }
        return c;
    }
    public static float[][] subtractArrays(float[][] a, float[][] b){
        float[][] c = new float[a.length][a[0].length];
        for (int i = 0; i<a.length; i++){
            for (int j = 0; j<a[0].length; j++){
                c[i][j] = (a[i][j] - b[i][j])%2;
            }
        }
        return c;
    }
    public static float avgArray(float[][] a){
        float sum = 0;
        for (int i = 0; i<a.length; i++){
            for (int j = 0; j<a[0].length; j++){
                sum += a[i][j];
            }
        }
        return sum/(a.length * a[0].length);
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
        
    public static void writeFile() {
        try {
            RuleList mb = search();

            // write object to file
            FileOutputStream fos = new FileOutputStream("rule_sets.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mb);
            oos.close();


        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
       // } catch (ClassNotFoundException e) {
         //       e.printStackTrace();
        }
    }public static RuleList readFile() {
        RuleList result;
        try {

            // read object from file
            FileInputStream fis = new FileInputStream("rule_sets.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (RuleList) ois.readObject();
            ois.close();
            
            return result;
            

        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        }
        return new RuleList(); // dummy for compiler.
    }
    /*
    public static void serializeDataOut(Object obj) throws IOException{
        String fileName= "Test.txt";
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
    }

    public static Object serializeDataIn()throws IOException{
       String fileName= "Test.txt";
       FileInputStream fin = new FileInputStream(fileName);
       ObjectInputStream ois = new ObjectInputStream(fin);
       Object iHandler = new Object();
       try{
          iHandler = (Object) ois.readObject();
        }catch(ClassNotFoundException e){
          e.printStackTrace();
        }
       
       ois.close();
       return iHandler;
       
    }*/
}
