package es.upm.pproject.sokoban.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.upm.pproject.sokoban.models.Level;

/**
* Class for the GUI of the applicaton.
* @author Idir Carlos Aliane Crespo
* @version 1.1
* @since 02/05/2022
*/
public class GUI {

    private static final int MAX_WIDTH = 700;
    private static final int MAX_HEIGHT = 700;
    private static final int SPRITE_SIZE = 50;
    private JFrame frame;
    private JPanel background;
    private ImagePanel [][] sprites;  // Floor
    private Level level;
    
    /**
    * Constructor of the class.
    */
    public GUI(){
        frame = new JFrame("Sokoban");
        frame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        frame.setResizable(false);
        background = new JPanel(new GridLayout(MAX_WIDTH/SPRITE_SIZE, MAX_HEIGHT/SPRITE_SIZE,0,0));
        sprites = new ImagePanel [MAX_WIDTH/SPRITE_SIZE][MAX_HEIGHT/SPRITE_SIZE];
        background.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        frame.getContentPane().add(background);
        ImageIcon icon = new ImageIcon("resources/warehouseman.jpg");
        frame.setIconImage(icon.getImage());
    }

    /**
     * initialize the GUI with the level indicated
     * @param lvl the level to be initialized
     */
    public void init(Level lvl){
        this.level = lvl;
    }

     /**
     * Displays a frame with the current state of the GUI
     */
    public void show(){
        paint(level.toString());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Repaints the frame with the new state of the GUI
     */
    public void repaint(){
        String boardLvl = level.toString();
        background.removeAll();
        paint(boardLvl);
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
                    sprites[x][y].setBounds(y*SPRITE_SIZE,x*SPRITE_SIZE,sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    background.add(sprites[x][y]); // Floor or Floor + Goal
                }
                else{   // rest of the floor sprites
                    sprites[x][y] = new ImagePanel("resources/floor.jpg");
                    sprites[x][y].setBounds(y*SPRITE_SIZE,x*SPRITE_SIZE,sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    background.add(sprites[x][y]); // Floor or Floor + Goal
                }
            }
            i++;
        }
    }
}
