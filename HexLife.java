/*
 */
package hexlife;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
/**
 *
 * @author Lloyd Cloer
 */
public class HexLife extends JFrame implements ActionListener{
    
    
    int n_rows = 32;
    int n_cols = n_rows*2;
    private int delay;
    private Timer timer;
    private RuleSelector rule_selector;
    private JLabel hotkey_legend;
    private String primed_key;
    public int k = 0;
    public HexModel model;
    //public JPanel world_panel;

    public static void main(String[] args) {
        HexLife app = new HexLife();
    }
    public HexLife() {
        //countNeighbors(2,2);
        //world[1][0] = true;
        model = new HexModel(n_rows, n_cols);
        
        setTitle("Hex Life");
        setSize(1200, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //System.out.println(rule_selector);

        // Master Panel
        JPanel master_panel = (JPanel) this.getContentPane();
        master_panel.setLayout(new BorderLayout());
        
        
        // Button Panel
        JPanel control_panel = new JPanel();
        control_panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
     //   button_panel.setLayout(new GridLayout(8, 1, 5, 5));
        control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.Y_AXIS));
      //  button_panel.setPreferredSize(new Dimension(200,100));
        master_panel.add(control_panel, BorderLayout.WEST);
        
        
        
        
        // Button Panel
        JPanel button_panel = new JPanel();
        button_panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button_panel.setLayout(new GridLayout(5, 1, 5, 5));
        button_panel.setMaximumSize(new Dimension(200, 200));
        
        //System.out.println(model.rule_sets);

        rule_selector = new RuleSelector();
        button_panel.add(rule_selector);
        // Pause Button
        JButton pause_button = new JButton("Play");
        button_panel.add(pause_button);
        pause_button.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e){
                if (timer.isRunning()){timer.stop(); pause_button.setText("Play");}
                else{timer.start(); pause_button.setText("Pause");}
            }
        });
        // Step Button
        JButton step_button = new JButton("Step");
        step_button.addActionListener(this);
        button_panel.add(step_button);
        
        // Randomize Button
        JButton randomize_button = new JButton("Randomize");
        button_panel.add(randomize_button);
        randomize_button.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {model.randomizeWorld(); repaint();}
        });
        
        // Clear Button
        JButton clear_button = new JButton("Clear");
        button_panel.add(clear_button);
        clear_button.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {model.clearWorld(); repaint();}
        });
        control_panel.add(button_panel);
        
        
        // Init Rule Sets
        //initRules();
        /*
        Rules rules = new Rules();
        rule_sets = rules.rule_sets;
        for (String name : rule_sets.keySet()){ 
            System.out.println(name);
            System.out.println(rule_selector);

            rule_selector.addItem(name);
        }
        // Hotkey Legend
        */
        hotkey_legend = new JLabel();
        control_panel.add(hotkey_legend);
        
        // Speed Slider
        control_panel.add(new JLabel("Speed"));
        JSlider speed_slider = new JSlider(JSlider.HORIZONTAL, 1, 30, 10); // timesteps per second
        speed_slider.setMaximumSize(new Dimension(220,40));
        control_panel.add(speed_slider);
        speed_slider.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()){
                    delay = 1000 / source.getValue();
                    timer.setDelay(delay);
                }
            }
        });
        // World Panel
        
        JPanel world_panel = new HexPanel();
        world_panel.setBorder(BorderFactory.createLineBorder(Color.green));
      //  button_panel.setPreferredSize(new Dimension(200,100));
        master_panel.add(world_panel, BorderLayout.CENTER);
        //JPanel world_panel = new JPanel();
        world_panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        world_panel.setLayout(new GridLayout(n_rows, n_cols, 0, 0));
        master_panel.add(world_panel, BorderLayout.CENTER);
        
        setVisible(true);
        
        repaint();
        
        // Timer
        delay = 100;
        timer = new Timer(delay, this);
        timer.setRepeats(true);
        
        
        
        
        setVisible(true);
        
    }
    @Override // Main class's action for timer
    public void actionPerformed(ActionEvent e){
        model.evolveWorld();
        repaint();
      //world[k/n_cols][k%n_cols] =true;
        //k++;
        //repaint();
        

    }
    
    public class HexPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Polygon poly;
            
            double sx = this.getWidth()/(n_rows*Math.sqrt(3)*2); // side length
            double sy = this.getHeight()/(n_cols*3/4);
            double theta = Math.PI/6.;
            double y = -sy/2.;
            
            
            for (int row=0; row<=n_rows; row++){ // for each row
                
                double x = sx*Math.sqrt(3)/2 - (sx*Math.sqrt(3)/2)*(row%2); // set initial x position, offset every other row
                for (int col=0; col<=n_cols; col++){ // for each hex col
                    
                    poly = new Polygon();

                    for (int i=0; i<6; i++){ // for each vertex
                        poly.addPoint((int)x, (int)y);
                        x += sx*Math.cos(theta);
                        y += sy*Math.sin(theta);
                        theta += Math.PI/3.0;
                    }
                    g.setColor(new Color(155,0,255));
                    g.drawPolygon(poly); 
                    g.setColor(Color.cyan);
                    if (model.world[row%n_rows][col%n_cols])
                        g.fillPolygon(poly);
                    x += sx*Math.sqrt(3);// *2/2   Move to next hex
                    //x %= this.getWidth(); 
                }
                y += sy *(3./2.); // move to next row
            }
        }
    } 
    private class RuleSelector extends JComboBox{
        public RuleSelector(){
            super();
            for (String name : model.rule_sets.keySet()){ 
                addItem(name);
            }
            this.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    JComboBox cb = (JComboBox) e.getSource();
                    String s_rule = (String) cb.getSelectedItem();
                    model.rule_set = (RuleSet) model.rule_sets.get(s_rule);
                    String str = "<html>"+model.rule_set.legend+"<br><br><html>";
                    hotkey_legend.setText(str);
                }
            });
            //this.setSelectedItem("Conway's Life (B3/S23)");
        }
    }
    
}
