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
import es.upm.pproject.sokoban.view.frames.AlertFrame;
import es.upm.pproject.sokoban.view.frames.LoadFrame;
import es.upm.pproject.sokoban.view.frames.SaveFrame;
import es.upm.pproject.sokoban.view.panels.ImagePanel;
import es.upm.pproject.sokoban.view.utils.ConstantsGUI;

/**
* Class for the GUI of the application.
* @author Idir Carlos Aliane Crespo
* @author Rafael Alonso Sirera
* @version 2.2
* @since 14/06/2022
*/
public class GUI {
    
    private JFrame frame;

    private JMenuBar menuBar;
    private JMenu helpMenu;

    private JLabel newGameLabel;
    private JLabel loadItem; 
    private JLabel saveItem;
    private JLabel undoLabel;
    private JLabel redoLabel;
    private JLabel exitItem;
    private JLabel resetItem;
    private JLabel tutorialItem;
    private JLabel aboutItem;

    private JPanel info;
    private JPanel grid;

    private JLabel score;
    private JLabel levelScore;
    private JLabel levelName;
    
    private Controller controller;
    private ImagePanel [][] sprites;  // Floor

    private boolean askSaveGame;
    
    /**
    * Constructor of the class.
    */
    public GUI(Controller control){
        controller = control;
        frame = new JFrame(ConstantsGUI.DEFAULT_FRAME_TITLE);
        frame.setSize(new Dimension(ConstantsGUI.MAIN_FRAME_MAX_WIDTH, ConstantsGUI.MAIN_FRAME_MAX_WIDTH));
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);
        grid = new JPanel();
        sprites = new ImagePanel [ConstantsGUI.MAIN_FRAME_MAX_WIDTH/ConstantsGUI.SPRITE_SIZE]
                                [ConstantsGUI.MAIN_FRAME_MAX_WIDTH/ConstantsGUI.SPRITE_SIZE];
        frame.getContentPane().setPreferredSize(
                new Dimension(ConstantsGUI.MAIN_FRAME_MAX_WIDTH, ConstantsGUI.MAIN_FRAME_MAX_WIDTH));
        grid.setBounds(0, ConstantsGUI.INFO_PANEL_HEIGHT, ConstantsGUI.MAIN_FRAME_MAX_WIDTH, 
                    ConstantsGUI.MAIN_FRAME_MAX_WIDTH - ConstantsGUI.INFO_PANEL_HEIGHT);
        info = new JPanel(new GridLayout(1,3));
        info.setBorder(new EmptyBorder(10, 20, 0, 20));
        grid.setLayout(null);
        info.setBackground(ConstantsGUI.INFO_PANEL_COLOR);
        info.setBounds(0,0,ConstantsGUI.INFO_PANEL_WIDTH,ConstantsGUI.INFO_PANEL_HEIGHT);
        levelScore = new JLabel("", SwingConstants.RIGHT);
        score = new JLabel("", SwingConstants.LEFT);
        levelName = new JLabel("", SwingConstants.CENTER);
        levelScore.setFont(ConstantsGUI.SCORE_FONT);
        levelScore.setBounds(0,25,0,0);
        score.setFont(ConstantsGUI.SCORE_FONT);
        score.setBounds(0,25,0,0);
        levelName.setFont(ConstantsGUI.SCORE_FONT);
        levelName.setBounds(0,25,0,0);
        
        frame.getContentPane().add(info);
        frame.getContentPane().add(grid);
        
        ImageIcon icon = new ImageIcon(ConstantsGUI.WAREHOUSEMAN_SPRITE);
        frame.setIconImage(icon.getImage());

        askSaveGame = false;
        setupMenuBar();
        addListeners();
        
    }
    
    /**
    * Displays a frame with the current state of the GUI
    */
    public void init(String boardLvl){
        paint(boardLvl);
        repaintInfo();
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);  
    }

        /**
    * Displays a frame with the current state of the GUI
    */
    public void show(String boardLvl){
        repaintScreen(boardLvl);
        askSaveGame = false;
    }
    
    /**
    * Repaints the frame with the new state of the GUI
    */
    public void repaint(String boardLvl){
        repaintScreen(boardLvl);
        askSaveGame = true;
    }

    private void repaintScreen(String boardLvl){
        grid.removeAll();
        repaintInfo();
        paint(boardLvl);
        grid.repaint();
        frame.pack();   
    }

    private void repaintInfo(){
        info.removeAll();
        score.setText("Score: " + controller.getGameScore());
        levelScore.setText("Level score: " + controller.getLevelScore());
        levelName.setText("Level: " + controller.getLevelName());
        info.add(score);
        info.add(levelName);
        info.add(levelScore);
        info.repaint();
    }
    
    /**
    * private method for painting the frame with the current state of the GUI
    * @param boardLvl the String level to be painted
    */
    private void paint(String boardLvl){
        int i = 0;
        grid.setBounds(ConstantsGUI.INFO_PANEL_WIDTH, ConstantsGUI.INFO_PANEL_HEIGHT, 
                        ConstantsGUI.MAIN_FRAME_MAX_WIDTH - ConstantsGUI.INFO_PANEL_WIDTH, 
                       ConstantsGUI.MAIN_FRAME_MAX_WIDTH - ConstantsGUI.INFO_PANEL_HEIGHT);

        for (int x = 0; x < ConstantsGUI.MAIN_FRAME_MAX_WIDTH/ConstantsGUI.SPRITE_SIZE; x++){
            for (int y = 0; y < ConstantsGUI.MAIN_FRAME_MAX_WIDTH/ConstantsGUI.SPRITE_SIZE; y++){
                if (boardLvl.length()-1 >= i){
                    switch(boardLvl.charAt(i)){
                        case '*':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.GOAL_SPRITE);
                            break;
                        case '+':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.WALL_SPRITE);
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
                        case 'O':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.BOX_GOAL_SPRITE); 
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
                    sprites[x][y].setBounds((y)*ConstantsGUI.SPRITE_SIZE,
                                (x+ConstantsGUI.INFO_PANEL_HEIGHT/ConstantsGUI.SPRITE_SIZE)*ConstantsGUI.SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    grid.add(sprites[x][y]); 
                }
                // rest of the floor sprites
                else{   
                    sprites[x][y] = new ImagePanel(ConstantsGUI.FLOOR_SPRITE);
                    sprites[x][y].setBounds((y)*ConstantsGUI.SPRITE_SIZE,
                                (x+ConstantsGUI.INFO_PANEL_HEIGHT/ConstantsGUI.SPRITE_SIZE)*ConstantsGUI.SPRITE_SIZE,
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    // Floor or Floor + Goal
                    grid.add(sprites[x][y]); 
                }
            }
            i++;
        }
    }

    private void addListeners(){
        addNewGameListener();
        addFrameListeners();
        addSaveAndLoadListeners();
        addUndoAndResetListeners();
        addRedoAndWindowListeners();
        addExitAndHelpListeners();
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
                        case 19: launchSaveMenu(0); break;
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

    private void addNewGameListener(){
        newGameLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                if (askSaveGame){
                    launchSaveMenu(2);
                }
                else{
                    controller.createNewGame();
                    frame.setTitle(ConstantsGUI.DEFAULT_FRAME_TITLE);
                }
                newGameLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                newGameLabel.setOpaque(true);
                newGameLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                newGameLabel.setOpaque(true);
                newGameLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                newGameLabel.setOpaque(false);
                newGameLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
    }

    private void addSaveAndLoadListeners(){
        loadItem.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                if(askSaveGame){
                    launchSaveMenu(1);
                }
                else{
                    launchLoadMenu();
                }
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
                launchSaveMenu(0);
                saveItem.setBackground(ConstantsGUI.LABEL_COLOR);
                askSaveGame = false;
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
                controller.reset();
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

    private void addRedoAndWindowListeners(){
        redoLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                controller.keyTyped('R');
                redoLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                redoLabel.setOpaque(true);
                redoLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                redoLabel.setOpaque(true);
                redoLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                redoLabel.setOpaque(false);
                redoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                if (askSaveGame){
                    new AlertFrame(controller, frame, null, null, true, 0);
                }
                else{
                    frame.setEnabled(false);
                    frame.dispose();
                }
            }
        });
    }

    private void addExitAndHelpListeners(){
        exitItem.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                if (askSaveGame){
                    new AlertFrame(controller, frame, null, null, true,0);
                }
                else{
                    frame.setEnabled(false);
                    frame.dispose();
                }
                exitItem.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                exitItem.setOpaque(true);
                exitItem.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                exitItem.setOpaque(true);
                exitItem.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                exitItem.setOpaque(false);
                exitItem.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
        //TODO: Add help listener
    }

    private void giveStyleToComponents(){
        Font menuBarFont = new Font("Arial", Font.PLAIN, 12);
        
        newGameLabel.setFont(menuBarFont);
        loadItem.setFont(menuBarFont);
        saveItem.setFont(menuBarFont);
        exitItem.setFont(menuBarFont);
        helpMenu.setFont(menuBarFont);
        tutorialItem.setFont(menuBarFont);
        aboutItem.setFont(menuBarFont);
        undoLabel.setFont(menuBarFont);
        redoLabel.setFont(menuBarFont);
        resetItem.setFont(menuBarFont);

        
        menuBar.setBorder(ConstantsGUI.MENU_BAR_BORDER);
        newGameLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        newGameLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        loadItem.setBackground(ConstantsGUI.LABEL_COLOR);
        loadItem.setBorder(ConstantsGUI.EMPTY_BORDER);
        saveItem.setBackground(ConstantsGUI.LABEL_COLOR);
        saveItem.setBorder(ConstantsGUI.EMPTY_BORDER);
        undoLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        undoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        redoLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        redoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        resetItem.setBackground(ConstantsGUI.LABEL_COLOR);
        resetItem.setBorder(ConstantsGUI.EMPTY_BORDER);
        exitItem.setBackground(ConstantsGUI.LABEL_COLOR);
        exitItem.setBorder(ConstantsGUI.EMPTY_BORDER);
    }

    private void setupMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        helpMenu =  new JMenu("  Help  ");
        newGameLabel = new JLabel("  New game  ");
        loadItem =  new JLabel("  Load  ");
        saveItem =  new JLabel("  Save  ");
        exitItem =  new JLabel("  Exit  ");
        resetItem = new JLabel("  Reset  ");
        undoLabel = new JLabel("  Undo  ");
        redoLabel = new JLabel("  Redo  ");
        tutorialItem = new JLabel("How to play Sokoban");
        aboutItem = new JLabel("About...");
        giveStyleToComponents();
        helpMenu.add(tutorialItem);
        helpMenu.add(aboutItem);
        menuBar.add(newGameLabel);
        menuBar.add(loadItem);
        menuBar.add(saveItem);
        menuBar.add(undoLabel);
        menuBar.add(redoLabel);
        menuBar.add(resetItem);
        menuBar.add(exitItem);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
    }

    public void dispose(){
        frame.setEnabled(false);
        frame.dispose();
    }

    private void launchSaveMenu(int mode){
        new SaveFrame(frame, controller, false, mode);
    }

    private void launchLoadMenu(){
        new LoadFrame(frame, controller);
    }
}
