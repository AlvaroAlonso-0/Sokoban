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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import es.upm.pproject.sokoban.Controller;

/**
* Class for the GUI of the application.
* @author Idir Carlos Aliane Crespo
* @author Rafael Alonso Sirera
* @version 1.4
* @since 16/05/2022
*/
public class GUI {
    
    private static final int MAX_WIDTH = 900;
    private static final int MAX_HEIGHT = 900;
    protected static final int SPRITE_SIZE = 50;
    private static final int INFO_PANEL_WIDTH = MAX_WIDTH;
    private static final int INFO_PANEL_HEIGHT = SPRITE_SIZE*2;
    private static final String FLOOR_SPRITE = "resources/sprites/default/floor.jpg";
    private static final Color INFO_PANEL_COLOR = new Color (187, 162, 232);

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
        background.setBackground(INFO_PANEL_COLOR);
        info = new JPanel(new GridLayout(1,2));
        info.setSize(INFO_PANEL_WIDTH,INFO_PANEL_HEIGHT);
        info.setBorder(new EmptyBorder(10, 20, 0, 20));
        background.setLayout(null);
        info.setBackground(INFO_PANEL_COLOR);
        info.setBounds(0,0,INFO_PANEL_WIDTH,INFO_PANEL_HEIGHT);
        score = new JLabel("Score: 0", SwingConstants.LEFT);
        levelName = new JLabel("Level: level_name", SwingConstants.RIGHT);
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
        ImageIcon icon = new ImageIcon("resources/sprites/default/warehouseman.jpg");
        frame.setIconImage(icon.getImage());
    }
    
    /**
    * Displays a frame with the current state of the GUI
    */
    public void show(String boardLvl){
        paint(boardLvl);   
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
                    switch(boardLvl.charAt(i)){
                        case '*':
                            sprites[x][y] = new ImagePanel("resources/sprites/default/goal.jpg");
                            break;
                        case '+':
                            sprites[x][y] = new ImagePanel("resources/sprites/default/rock.jpg");
                            break;
                        case 'W':
                            sprites[x][y] = new ImagePanel("resources/sprites/default/warehouseman.jpg");
                            break;
                        case '#':
                            sprites[x][y] = new ImagePanel("resources/sprites/default/box.jpg");
                            break;
                        case ' ':
                            sprites[x][y] = new ImagePanel(FLOOR_SPRITE);
                            break;
                        /**
                         * it is '\n' so we dont increse the counter 
                         * and wait until the entire row is painted with floor sprites
                         * */ 
                        default:  
                            sprites[x][y] = new ImagePanel(FLOOR_SPRITE);
                            i--;
                    }
                    i++;
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+2)*SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    background.add(sprites[x][y]); 
                }
                // rest of the floor sprites
                else{   
                    sprites[x][y] = new ImagePanel(FLOOR_SPRITE);
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+2)*SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    background.add(sprites[x][y]); 
                }
            }
            i++;
        }
    }
}
