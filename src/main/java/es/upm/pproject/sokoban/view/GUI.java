package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.frames.LoadFrame;
import es.upm.pproject.sokoban.view.frames.SaveFrame;
import es.upm.pproject.sokoban.view.panels.ImagePanel;

/**
* Class for the GUI of the application.
* @author Idir Carlos Aliane Crespo
* @author Rafael Alonso Sirera
* @version 2.1
* @since 11/06/2022
*/
public class GUI {
    
    public  static final int SPRITE_SIZE = 50;
    private static final int MAX_WIDTH = 900 + SPRITE_SIZE;
    private static final int MAX_HEIGHT = 900;
    private static final int INFO_PANEL_WIDTH = MAX_WIDTH;
    private static final int INFO_PANEL_HEIGHT = SPRITE_SIZE;
    
    private static final Font SCORE_FONT = new Font("Calibri", Font.BOLD, 30);

    private JFrame frame;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu helpMenu;

    private JLabel loadItem; 
    private JLabel saveItem;
    private JLabel undoLabel;
    private JLabel exitItem;
    private JLabel resetItem;
    private JLabel tutorialItem;
    private JLabel aboutItem;

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
        controller = control;
        frame = new JFrame("Sokoban");
        frame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);
        grid = new JPanel();
        sprites = new ImagePanel [MAX_HEIGHT/SPRITE_SIZE][MAX_WIDTH/SPRITE_SIZE];
        frame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        grid.setBounds(0, INFO_PANEL_HEIGHT, MAX_WIDTH, MAX_HEIGHT - INFO_PANEL_HEIGHT);
        info = new JPanel(new GridLayout(1,2));
        info.setBorder(new EmptyBorder(10, 20, 0, 20));
        grid.setLayout(null);
        info.setBackground(ConstantsGUI.INFO_PANEL_COLOR);
        info.setBounds(0,0,INFO_PANEL_WIDTH,INFO_PANEL_HEIGHT);
        score = new JLabel("Score: 0", SwingConstants.LEFT);
        levelName = new JLabel("Level: level_name", SwingConstants.RIGHT);
        score.setFont(SCORE_FONT);
        score.setBounds(0,25,0,0);
        levelName.setFont(SCORE_FONT);
        levelName.setBounds(0,25,0,0);
        info.add(score);
        info.add(levelName);
        frame.getContentPane().add(info);
        frame.getContentPane().add(grid);
        
        ImageIcon icon = new ImageIcon(ConstantsGUI.WAREHOUSEMAN_SPRITE);
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
        /*Here we should call the new method paintUpdatedCell() */
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
        grid.setBounds(INFO_PANEL_WIDTH, INFO_PANEL_HEIGHT, MAX_WIDTH - INFO_PANEL_WIDTH, 
                       MAX_HEIGHT - INFO_PANEL_HEIGHT);

        for (int x = 0; x < MAX_HEIGHT/SPRITE_SIZE; x++){
            for (int y = 0; y < MAX_WIDTH/SPRITE_SIZE; y++){
                if (boardLvl.length()-1 >= i){
                    switch(boardLvl.charAt(i)){
                        case '*':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.GOAL_SPRITE);
                            break;
                        case '+':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.ROCK_SPRITE);
                            break;
                        case 'W':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.WAREHOUSEMAN_SPRITE);
                            break;
                        case '#':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.BOX_SPRITE);
                            break;
                        case ' ':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.FLOOR_SPRITE);
                            break;
                        /**
                         * it is '\n' so we dont increse the counter 
                         * and wait until the entire row is painted with floor sprites
                         * */ 
                        default:  
                            sprites[x][y] = new ImagePanel(ConstantsGUI.FLOOR_SPRITE);
                            i--;
                    }
                    i++;
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+INFO_PANEL_HEIGHT/SPRITE_SIZE)*SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    grid.add(sprites[x][y]); 
                }
                // rest of the floor sprites
                else{   
                    sprites[x][y] = new ImagePanel(ConstantsGUI.FLOOR_SPRITE);
                    sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+INFO_PANEL_HEIGHT/SPRITE_SIZE)*SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    grid.add(sprites[x][y]); 
                }
            }
            i++;
        }
    }

    public void paintUpdatedCell(int x, int y, int sprite){
        grid.remove(sprites[x][y]);
        switch(sprite){
            case 0:     sprites[x][y] = new ImagePanel(ConstantsGUI.BOX_SPRITE); break;
            case 1:     sprites[x][y] = new ImagePanel(ConstantsGUI.WAREHOUSEMAN_SPRITE); break;
            default:     sprites[x][y] = new ImagePanel(ConstantsGUI.FLOOR_SPRITE); break;
            
        }
        sprites[x][y].setBounds((y)*SPRITE_SIZE,(x+INFO_PANEL_HEIGHT/SPRITE_SIZE)*SPRITE_SIZE,sprites[x][y].getWidth(),
        sprites[x][y].getHeight());
        grid.add(sprites[x][y]);
    }

    private void addListeners(){
        addFrameListeners();
        addSaveAndLoadListeners();
        addUndoAndResetListeners();
    }

    private void addFrameListeners(){
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isControlDown()){
                    switch(e.getKeyChar()){
                        // CTRL + Z -> Undo 
                        case 26: controller.keyTyped('U'); break;
                        // CTRL + S -> Save
                        case 19: launchSaveMenu(); break;
                        // CTRL + L -> Load
                        case 12: launchLoadMenu(); break;
                        default: controller.keyTyped(Character.toUpperCase(e.getKeyChar()));
                    }
                }
                else {
                    controller.keyTyped(Character.toUpperCase(e.getKeyChar()));
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                //This method is not required
            }
            @Override
            public void keyPressed(KeyEvent e) {
                //This method is not required
            }
        });
    }

    private void addSaveAndLoadListeners(){
        loadItem.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                launchLoadMenu();
                loadItem.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                loadItem.setOpaque(true);
                loadItem.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                loadItem.setOpaque(true);
                loadItem.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                loadItem.setOpaque(false);
                loadItem.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });

        saveItem.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){ 
                launchSaveMenu();
                saveItem.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                saveItem.setOpaque(true);
                saveItem.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                saveItem.setOpaque(true);
                saveItem.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                saveItem.setOpaque(false);
                saveItem.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
    }

    private void addUndoAndResetListeners(){
        undoLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){  
                controller.keyTyped('U');
                undoLabel.setBackground(ConstantsGUI.LABEL_COLOR);

            } 
            @Override
            public void mousePressed(MouseEvent e){
                undoLabel.setOpaque(true);
                undoLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                undoLabel.setOpaque(true);
                undoLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                undoLabel.setOpaque(false);
                undoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });

        resetItem.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){  
                resetItem.setBackground(ConstantsGUI.LABEL_COLOR);

            } 
            @Override
            public void mousePressed(MouseEvent e){
                resetItem.setOpaque(true);
                resetItem.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                resetItem.setOpaque(true);
                resetItem.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                resetItem.setOpaque(false);
                resetItem.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
    }

    private void giveStyleToComponents(){
        Font menuBarFont = new Font("Arial", Font.PLAIN, 12);
        
        gameMenu.setFont(menuBarFont);
        loadItem.setFont(menuBarFont);
        saveItem.setFont(menuBarFont);
        exitItem.setFont(menuBarFont);
        helpMenu.setFont(menuBarFont);
        tutorialItem.setFont(menuBarFont);
        aboutItem.setFont(menuBarFont);
        undoLabel.setFont(menuBarFont);
        resetItem.setFont(menuBarFont);

        menuBar.setBorder(ConstantsGUI.MENU_BAR_BORDER);
        loadItem.setBackground(ConstantsGUI.LABEL_COLOR);
        loadItem.setBorder(ConstantsGUI.EMPTY_BORDER);
        saveItem.setBackground(ConstantsGUI.LABEL_COLOR);
        saveItem.setBorder(ConstantsGUI.EMPTY_BORDER);
        undoLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        undoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        resetItem.setBackground(ConstantsGUI.LABEL_COLOR);
        resetItem.setBorder(ConstantsGUI.EMPTY_BORDER);
    }

    private void setupMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        gameMenu = new JMenu("Game");
        helpMenu =  new JMenu("  Help  ");
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
        new SaveFrame(frame, controller);
    }

    private void launchLoadMenu(){
        new LoadFrame(frame, controller);
    }
}
