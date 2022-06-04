package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.SpringLayout.Constraints;
import javax.swing.border.EmptyBorder;

import es.upm.pproject.sokoban.controller.Controller;

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
    private static final String FLOOR_SPRITE = "src/resources/sprites/default/floor.jpg";
    private static final Color INFO_PANEL_COLOR = new Color (187, 162, 232);

    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu gameMenu, helpMenu;
    private JLabel loadItem, saveItem, undoLabel, exitItem, resetItem, tutorialItem, aboutItem;
    private JPanel info;
    private JPanel grid;
    private JLabel score;
    private JLabel levelName;
    
    private Controller controller;
    private ImagePanel [][] sprites;  // Floor
    
    /**
    * Constructor of the class.
    */
    public GUI(Controller control){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controller = control;
        frame = new JFrame("Sokoban");
        frame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        frame.setResizable(false);
        grid = new JPanel();
        sprites = new ImagePanel [MAX_WIDTH/SPRITE_SIZE][MAX_HEIGHT/SPRITE_SIZE];
        frame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));

        grid.setBackground(Color.RED);
        info = new JPanel(new GridLayout(1,2));
        info.setSize(INFO_PANEL_WIDTH,INFO_PANEL_HEIGHT);
        info.setBorder(new EmptyBorder(10, 20, 0, 20));
        grid.setLayout(null);
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
        frame.getContentPane().add(grid);
        
        ImageIcon icon = new ImageIcon("src/resources/sprites/default/warehouseman.jpg");
        frame.setIconImage(icon.getImage());
        setupMenuBar();
        addListeners();
        
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
        grid.removeAll();

        info.removeAll();
        
        
        info.add(score);
        info.add(levelName);
        paint(boardLvl);
        grid.repaint();
        info.repaint();
        frame.pack();
    }
    
    /**
    * private method for painting the frame with the current state of the GUI
    * @param boardLvl the String level to be painted
    */
    private void paint(String boardLvl){
        int i = 0;
        grid.setBounds(INFO_PANEL_WIDTH, INFO_PANEL_HEIGHT, MAX_WIDTH - INFO_PANEL_WIDTH, MAX_HEIGHT - INFO_PANEL_HEIGHT);
        for (int x = 0; x < MAX_WIDTH/SPRITE_SIZE; x++){
            for (int y = 0; y < MAX_WIDTH/SPRITE_SIZE; y++){
                if (boardLvl.length()-1 >= i){
                    switch(boardLvl.charAt(i)){
                        case '*':
                            sprites[x][y] = new ImagePanel("src/resources/sprites/default/goal.jpg");
                            break;
                        case '+':
                            sprites[x][y] = new ImagePanel("src/resources/sprites/default/rock.jpg");
                            break;
                        case 'W':
                            sprites[x][y] = new ImagePanel("src/resources/sprites/default/warehouseman.jpg");
                            break;
                        case '#':
                            sprites[x][y] = new ImagePanel("src/resources/sprites/default/box.jpg");
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
                    grid.add(sprites[x][y]); 
                }
                // rest of the floor sprites
                else{   
                    sprites[x][y] = new ImagePanel(FLOOR_SPRITE);
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+2)*SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    grid.add(sprites[x][y]); 
                }
            }
            i++;
        }
    }

    private void addListeners(){
        loadItem.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){
                System.out.println(controller.loadGame("test"));
                loadItem.setOpaque(false);
                loadItem.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

            } 
            public void mousePressed(MouseEvent e){
                loadItem.setOpaque(true);
                loadItem.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(128, 126, 126)));

            }
        });

        saveItem.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){ 
                System.out.println(controller.saveGame("test"));
                launchSaveMenu();
                saveItem.setOpaque(false);
                saveItem.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

            } 
            public void mousePressed(MouseEvent e){
                saveItem.setOpaque(true);
                saveItem.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(128, 126, 126)));

            }
        });

        undoLabel.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){  
                controller.keyTyped('U');
                undoLabel.setOpaque(false);
                undoLabel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

            } 
            public void mousePressed(MouseEvent e){
                undoLabel.setOpaque(true);
                undoLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(128, 126, 126)));

            }
        });

        resetItem.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){  
                resetItem.setOpaque(false);
                resetItem.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

            } 
            public void mousePressed(MouseEvent e){
                resetItem.setOpaque(true);
                resetItem.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(128, 126, 126)));

            }
        });

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
    }

    private void giveStyleToComponents(){
        Font font = new Font("Arial", Font.PLAIN, 12);
        Color color = new Color(163, 184, 204);
        gameMenu.setFont(font);
        loadItem.setFont(font);
        saveItem.setFont(font);
        exitItem.setFont(font);
        helpMenu.setFont(font);
        tutorialItem.setFont(font);
        aboutItem.setFont(font);
        undoLabel.setFont(font);
        resetItem.setFont(font);

        menuBar.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(128, 126, 126)));
        loadItem.setBackground(color);
        loadItem.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        saveItem.setBackground(color);
        saveItem.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        undoLabel.setBackground(color);
        undoLabel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        resetItem.setBackground(color);
        resetItem.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
    }

    private void setupMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        gameMenu = new JMenu("Game");
        helpMenu =      new JMenu("  Help  ");
        loadItem =  new JLabel("  Load  ");
        saveItem =  new JLabel("  Save  ");
        exitItem =  new JLabel("  Exit  ");
        resetItem = new JLabel("  Reset  ");
        undoLabel = new JLabel("  Undo  ");
        tutorialItem = new JLabel("How to play Sokoban");
        aboutItem = new JLabel("About...");
        giveStyleToComponents();
        helpMenu.add(tutorialItem);
        helpMenu.add(aboutItem);
        menuBar.add(loadItem);
        menuBar.add(saveItem);
        menuBar.add(undoLabel);
        menuBar.add(resetItem);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
    }

    private void launchSaveMenu(){
        JFrame saveFrame = new JFrame("Save game");
        saveFrame.setSize(new Dimension(MAX_WIDTH/2, MAX_HEIGHT/2));
        saveFrame.setResizable(false);
        saveFrame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH/2, MAX_HEIGHT/2));
        saveFrame.pack();
        saveFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        saveFrame.setVisible(true);  
    }
}
