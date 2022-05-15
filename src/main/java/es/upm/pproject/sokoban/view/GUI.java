package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import es.upm.pproject.sokoban.Controller;

/**
* Class for the GUI of the application.
* @author Idir Carlos Aliane Crespo
* @author Rafael Alonso Sirera
* @version 1.3
* @since 12/05/2022
*/
public class GUI {
    
    private static final int MAX_WIDTH = 900;
    private static final int MAX_HEIGHT = 900;
    protected static final int SPRITE_SIZE = 50;
    private static final int INFO_PANEL_WIDTH = MAX_WIDTH;
    private static final int INFO_PANEL_HEIGHT = SPRITE_SIZE*2;
    private JFrame frame;
    private JPanel info;
    private JLabel score;
    private JLabel levelName;
    private JPanel background;
    
    private Controller controller;
    private ImagePanel [][] sprites;  // Floor
    
    /**
    * Constructor of the class.
    */
    public GUI(Controller control){
        controller = control;
        frame = new JFrame("Sokoban");
        frame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        frame.setResizable(false);
        background = new JPanel();
        sprites = new ImagePanel [MAX_WIDTH/SPRITE_SIZE][MAX_HEIGHT/SPRITE_SIZE];
        frame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        background.setBounds(INFO_PANEL_WIDTH, INFO_PANEL_HEIGHT, MAX_WIDTH, MAX_HEIGHT);
        background.setBackground(Color.blue);
        info = new JPanel(new GridLayout(1,2));
        info.setSize(INFO_PANEL_WIDTH,INFO_PANEL_HEIGHT);
        info.setBorder(new EmptyBorder(10, 20, 0, 20));
        background.setLayout(null);
        info.setBackground(new Color(187, 162, 232));
        info.setBounds(0,0,INFO_PANEL_WIDTH,INFO_PANEL_HEIGHT);
        score = new JLabel("Score: 0", JLabel.LEFT);
        levelName = new JLabel("Level: level_name", JLabel.RIGHT);
        score.setFont(new Font("Calibri", Font.BOLD, 30));
        score.setBounds(0,25,0,0);
        levelName.setFont(new Font("Calibri", Font.BOLD, 30));
        levelName.setBounds(0,25,0,0);
        info.add(score);
        info.add(levelName);
        frame.getContentPane().add(info);
        frame.getContentPane().add(background);
        
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                //This method is not required
            }
            @Override
            public void keyTyped(KeyEvent e) {
                controller.keyTyped(Character.toUpperCase(e.getKeyChar()));
            }
            @Override
            public void keyReleased(KeyEvent e) {
                //This method is not required
            }
        });
        ImageIcon icon = new ImageIcon("resources/warehouseman.jpg");
        frame.setIconImage(icon.getImage());
    }
    
    /**
    * Displays a frame with the current state of the GUI
    */
    public void show(String boardLvl){
        paint(boardLvl);   
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);  
    }
    
    /**
    * Repaints the frame with the new state of the GUI
    */
    public void repaint(String boardLvl){
        background.removeAll();
        paint(boardLvl);
        info.repaint();
        background.repaint();
    }
    
    /**
    * private method for painting the frame with the current state of the GUI
    * @param boardLvl the String level to be painted
    */
    private void paint(String boardLvl){
        int i = 0;
        for (int x = 0; x < MAX_WIDTH/SPRITE_SIZE; x++){
            for (int y = 0; y < MAX_WIDTH/SPRITE_SIZE; y++){
                if (boardLvl.length()-1 >= i){
                    if (boardLvl.charAt(i) == '*'){
                        sprites[x][y] = new ImagePanel("resources/goal.jpg");
                        i++;
                    }
                    else if (boardLvl.charAt(i) == '+'){
                        sprites[x][y] = new ImagePanel("resources/rock.jpg");
                        i++;
                    }
                    else if(boardLvl.charAt(i) == 'W'){
                        sprites[x][y] = new ImagePanel("resources/warehouseman.jpg");
                        i++;
                    }
                    else if(boardLvl.charAt(i) == '#'){
                        sprites[x][y] = new ImagePanel("resources/box.jpg");
                        i++;
                    }
                    else if (boardLvl.charAt(i) == ' '){
                        sprites[x][y] = new ImagePanel("resources/floor.jpg");
                        i++;
                    }
                    else {  // it is '\n' so we dont increse the counter and wait until the entire row is painted with floor sprites
                        sprites[x][y] = new ImagePanel("resources/floor.jpg"); 
                    }
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+2)*SPRITE_SIZE,sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    background.add(sprites[x][y]); // Floor or Floor + Goal
                }
                else{   // rest of the floor sprites
                    sprites[x][y] = new ImagePanel("resources/floor.jpg");
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+2)*SPRITE_SIZE,sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    background.add(sprites[x][y]); // Floor or Floor + Goal
                }
            }
            i++;
        }
    }
}
