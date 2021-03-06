package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.time.LocalTime;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.frames.AlertFrame;
import es.upm.pproject.sokoban.view.frames.HelpFrame;
import es.upm.pproject.sokoban.view.frames.AcceptFrame;
import es.upm.pproject.sokoban.view.frames.LoadFrame;
import es.upm.pproject.sokoban.view.frames.SaveFrame;
import es.upm.pproject.sokoban.view.panels.ImagePanel;
import es.upm.pproject.sokoban.view.panels.Rectangle;
import es.upm.pproject.sokoban.view.utils.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class for the GUI of the application.
* @author Idir Carlos Aliane Crespo
* @author Rafael Alonso Sirera
* @version 2.4
* @since 15/06/2022
*/
public class GUI {

    private static final ImagePanel WAREHOUSEMAN_SPRITE = new ImagePanel(ConstantsGUI.WAREHOUSEMAN_SPRITE);
    private static final ImagePanel AMONGUS_SPRITE = new ImagePanel(ConstantsGUI.AMONGUS_SPRITE);
    private static final ImagePanel WAREHOUSEMAN_GOAL_SPRITE = new ImagePanel(ConstantsGUI.WAREHOUSEMAN_GOAL_SPRITE);
    private static final ImagePanel AMONGUS_GOAL_SPRITE = new ImagePanel(ConstantsGUI.AMOGUS_GOAL_SPRITE);
    private static final ImagePanel BACKGROUND_SPRITE = new ImagePanel(
        ConstantsGUI.BACKGROUND, ConstantsGUI.MAIN_FRAME_MAX_WIDTH,ConstantsGUI.MAIN_FRAME_MAX_HEIGHT);
    

    private JFrame frame;

    private JMenuBar menuBar;

    private JLabel newGameLabel;
    private JLabel loadLabel; 
    private JLabel saveLabel;
    private JLabel undoLabel;
    private JLabel redoLabel;
    private JLabel exitLabel;
    private JLabel resetLabel;
    private JLabel helpLabel;

    private JPanel info;
    private JPanel grid;

    private JLabel score;
    private JLabel levelScore;
    private JLabel levelName;
    
    private Controller controller;
    private ImagePanel [][] sprites;

    private Dimension levelDimension;

    private boolean isWarehouseManSprite;

    private double offset;
    
    /**
    * Constructor of the class.
    */
    public GUI(Controller control){
        offset = LocalTime.now().getSecond() / 60.0;
        controller = control;
        frame = new JFrame(ConstantsGUI.DEFAULT_FRAME_TITLE);
        frame.setSize(new Dimension(ConstantsGUI.MAIN_FRAME_MAX_WIDTH, ConstantsGUI.MAIN_FRAME_MAX_HEIGHT));
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);
        grid = new JPanel();
        
        frame.getContentPane().setPreferredSize(
                new Dimension(ConstantsGUI.MAIN_FRAME_MAX_WIDTH, ConstantsGUI.MAIN_FRAME_MAX_HEIGHT));
        grid.setBounds(0, ConstantsGUI.INFO_PANEL_HEIGHT, ConstantsGUI.MAIN_FRAME_MAX_WIDTH, 
                    ConstantsGUI.MAIN_FRAME_MAX_HEIGHT - ConstantsGUI.INFO_PANEL_HEIGHT);
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
        
        levelDimension = new Dimension();

        UtilsGUI.addIcon(frame);

        isWarehouseManSprite = true;

        setupMenuBar();
        addListeners();
        
    }
    
    /**
    * Displays a frame with the current state of the GUI
    */
    public void init(String boardLvl){
        show(boardLvl);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);  
    }

    /**
    * Repaints the frame with the new level.
    */
    public void show(String boardLvl){
        levelDimension = controller.getLevelDimension();
        sprites = new ImagePanel [(int)levelDimension.getHeight()]
                                [(int)levelDimension.getWidth()];
        frame.setTitle(frame.getTitle().substring(0, frame.getTitle().length()));
        repaintScreen(boardLvl);
        
    }
    
    /**
    * Repaints the frame with the new movement.
    */
    public void repaint(String boardLvl){
        String title = frame.getTitle();
        if (!levelDimension.equals(controller.getLevelDimension())){
            levelDimension = controller.getLevelDimension();
            sprites = new ImagePanel [(int)levelDimension.getHeight()]
                                [(int)levelDimension.getWidth()];
        }
        if (title.charAt(title.length()-1) != '*'){
            frame.setTitle(frame.getTitle()+"*");
        }
        repaintScreen(boardLvl);
    }

    /**
     * Repaints every component.
     * @param boardLvl The board String.
     */
    private void repaintScreen(String boardLvl){
        levelDimension = controller.getLevelDimension();
        grid.removeAll();
        repaintInfo();
        paint(boardLvl);
        repaintBorder();
        repaintBackground();
        grid.repaint();
        frame.pack();   
    }

    /*
     * Method for repainting the info panel.
     */
    private void repaintInfo(){
        info.removeAll();
        score.setText("Score: " + controller.getGameScore());
        levelScore.setText("Level score: " + controller.getLevelScore());
        levelName.setText(controller.getLevelName());
        info.add(score);
        info.add(levelName);
        info.add(levelScore);
        info.repaint();
    }


    /**
     * Method for repainting the background.
     */
    private void repaintBackground(){
        BACKGROUND_SPRITE.setBounds(0, 0,
             BACKGROUND_SPRITE.getWidth(), BACKGROUND_SPRITE.getHeight());
        grid.add(BACKGROUND_SPRITE);
    }

    /**
     * Method for repainting the border of the board.
     */
    private void repaintBorder(){
        int width = (int)levelDimension.getWidth()*ConstantsGUI.SPRITE_SIZE;
        int heigth = (int)levelDimension.getHeight()*ConstantsGUI.SPRITE_SIZE;
        int initialX = (ConstantsGUI.MAIN_FRAME_MAX_WIDTH/2 - width/2);
        int initialY = (ConstantsGUI.MAIN_FRAME_MAX_HEIGHT/2 - heigth/2);
        Rectangle rect = new Rectangle(width+10,heigth+10);
        rect.setBorder(BorderFactory.createMatteBorder(10, 10,10, 10, Color.WHITE));
        rect.setBounds(initialX-5, initialY-4, rect.getWidth(), rect.getHeight());
        grid.add(rect);
    }
    
    /**
    * private method for painting the frame with the current state of the GUI
    * @param boardLvl the String level to be painted
    */
    private void paint(String boardLvl){
        int i = 0;
        int width = (int)levelDimension.getWidth()*ConstantsGUI.SPRITE_SIZE;
        int heigth = (int)levelDimension.getHeight()*ConstantsGUI.SPRITE_SIZE;
        int initialX = (ConstantsGUI.MAIN_FRAME_MAX_WIDTH - width)/2;
        int initialY = (ConstantsGUI.MAIN_FRAME_MAX_HEIGHT - heigth)/2;

        ImagePanel currentPlayer = WAREHOUSEMAN_SPRITE;
        ImagePanel currentPlayerOnGoal = WAREHOUSEMAN_GOAL_SPRITE;
        if (!isWarehouseManSprite){
            currentPlayer = AMONGUS_SPRITE;
            currentPlayerOnGoal = AMONGUS_GOAL_SPRITE;
        }
        
        boolean isNewLine = false;
        grid.setBounds(ConstantsGUI.INFO_PANEL_WIDTH, ConstantsGUI.INFO_PANEL_HEIGHT, 
                        ConstantsGUI.MAIN_FRAME_MAX_WIDTH - ConstantsGUI.INFO_PANEL_WIDTH, 
                       ConstantsGUI.MAIN_FRAME_MAX_WIDTH - ConstantsGUI.INFO_PANEL_HEIGHT);

        for (int x = 0; x < sprites.length; x++){
            for (int y = 0; y < sprites[0].length; y++){
                if (boardLvl.length()-1 >= i){
                    switch(boardLvl.charAt(i)){
                        case '*':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.GOAL_SPRITE);
                            break;
                        case '+':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.WALL_SPRITE);
                            break;
                        case 'W':
                            sprites[x][y] = currentPlayer;
                            break;
                        case '#':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.BOX_SPRITE);
                            break;
                        case ' ':
                            sprites[x][y] = new ImagePanel(ConstantsGUI.FLOOR_SPRITE);
                            break;
                        case 'O':
                            sprites[x][y] = new ImagePanel(UtilsGUI.getRandomSprite(x, y, offset));
                            break;
                        case 'X':
                            sprites[x][y] = currentPlayerOnGoal;
                            break;
                        /**
                         * it is '\n' so we dont increse the counter 
                         * and wait until the entire row is painted with floor sprites
                         * */ 
                        default:  
                            isNewLine = true;
                            i--;
                    }
                    i++;
                    if(isNewLine){
                        isNewLine = false;
                        break;
                    }
                    sprites[x][y].setBounds(initialX,
                                (initialY+ConstantsGUI.INFO_PANEL_HEIGHT/ConstantsGUI.SPRITE_SIZE),
                                            sprites[x][y].getWidth(),sprites[x][y].getHeight());
                    grid.add(sprites[x][y]);
                }
                initialX = initialX + ConstantsGUI.SPRITE_SIZE;
            }
            i++;
            initialX = (ConstantsGUI.MAIN_FRAME_MAX_WIDTH/2 - width/2);
            initialY = initialY + ConstantsGUI.SPRITE_SIZE;
        }
    }

    /**
     * Method for adding every listener of the frame
     */
    private void addListeners(){
        addFrameListeners();
        addLoadAndSaveListeners();
        addUndoAndRedoListeners();
        addResetAndExitListeners();
        addNewGameAndHelpListener();
    }

    /**
     * Frame listeners
     */
    private void addFrameListeners(){
        frame.addKeyListener(new KeyAdapter(){ 
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.isControlDown()){
                    switch(e.getKeyChar()){
                        // CTRL + Z -> Undo 
                        case 26: controller.keyTyped(e.isShiftDown() ? 'R' : 'U'); break;
                        // CTRL + S -> Save
                        case 19: launchSaveMenu(0); break;
                        // CTRL + L -> Load
                        case 12: launchLoadMenu(); break;
                        // CTRL + N -> New game
                        case 14: launchNewGame(); break;
                        default: controller.keyTyped(Character.toUpperCase(e.getKeyChar()));
                    }
                }
                else {
                    controller.keyTyped(Character.toUpperCase(e.getKeyChar()));
                }
            }
        });
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                if (controller.hasBeenModified() && !controller.isFinished()){
                    new AlertFrame(controller, frame, null, null, true, 0);
                }
                else{
                    frame.setEnabled(false);
                    frame.dispose();
                }
            }
        });
    }

    /**
     * New game listeners
     */
    private void addNewGameAndHelpListener(){
        newGameLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                launchNewGame();
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
        helpLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                if (helpLabel.isEnabled()){
                    helpLabel.setEnabled(false);
                    new HelpFrame(helpLabel);
                    helpLabel.setBackground(ConstantsGUI.LABEL_COLOR);
                }
            } 
            @Override
            public void mousePressed(MouseEvent e){
                if (helpLabel.isEnabled()){
                    helpLabel.setOpaque(true);
                    helpLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e){
                if (helpLabel.isEnabled()){
                    helpLabel.setOpaque(true);
                    helpLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                helpLabel.setOpaque(false);
                helpLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
    }

    /**
     * Save and load listeners
     */
    private void addLoadAndSaveListeners(){
        loadLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                if(controller.hasBeenModified() && !controller.isFinished()){
                    launchSaveMenu(1);
                }
                else{
                    launchLoadMenu();
                }
                loadLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                loadLabel.setOpaque(true);
                loadLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                loadLabel.setOpaque(true);
                loadLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                loadLabel.setOpaque(false);
                loadLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });

        saveLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){ 
                launchSaveMenu(0);
                saveLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                saveLabel.setOpaque(true);
                saveLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                saveLabel.setOpaque(true);
                saveLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                saveLabel.setOpaque(false);
                saveLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
    }

    /**
     * Undo and reset listeners
     */
    private void addUndoAndRedoListeners(){
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
    }

    /**
     * Redo listeners
     */
    private void addResetAndExitListeners(){
        resetLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){ 
                controller.reset();
                resetLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                resetLabel.setOpaque(true);
                resetLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                resetLabel.setOpaque(true);
                resetLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                resetLabel.setOpaque(false);
                resetLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
        exitLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                if (controller.hasBeenModified() && !controller.isFinished()){
                    new AlertFrame(controller, frame, null, null, true,0);
                }
                else{
                    frame.setEnabled(false);
                    frame.dispose();
                }
                exitLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                exitLabel.setOpaque(true);
                exitLabel.setBackground(ConstantsGUI.PRESSED_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                exitLabel.setOpaque(true);
                exitLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
            }
            @Override
            public void mouseExited(MouseEvent e){
                exitLabel.setOpaque(false);
                exitLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
            }
        });
    }

    /**
     * Give style to every component of the bar menu
     */
    private void giveStyleToComponents(){
        Font menuBarFont = new Font("Arial", Font.PLAIN, 12);
        
        newGameLabel.setFont(menuBarFont);
        loadLabel.setFont(menuBarFont);
        saveLabel.setFont(menuBarFont);
        exitLabel.setFont(menuBarFont);
        helpLabel.setFont(menuBarFont);
        undoLabel.setFont(menuBarFont);
        redoLabel.setFont(menuBarFont);
        resetLabel.setFont(menuBarFont);
        helpLabel.setFont(menuBarFont);

        menuBar.setBorder(ConstantsGUI.MENU_BAR_BORDER);
        newGameLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        newGameLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        loadLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        loadLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        saveLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        saveLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        undoLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        undoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        redoLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        redoLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        resetLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        resetLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        exitLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        exitLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
        helpLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        helpLabel.setBorder(ConstantsGUI.EMPTY_BORDER);
    }

    /*
     * Add components to the menu bar
     */
    private void setupMenuBar(){
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        newGameLabel = new JLabel("  New game  ");
        loadLabel =  new JLabel("  Load  ");
        saveLabel =  new JLabel("  Save  ");
        exitLabel =  new JLabel("  Exit  ");
        resetLabel = new JLabel("  Reset  ");
        undoLabel = new JLabel("  Undo  ");
        redoLabel = new JLabel("  Redo  ");
        helpLabel = new JLabel("  Help  ");
        giveStyleToComponents();
        menuBar.add(newGameLabel);
        menuBar.add(loadLabel);
        menuBar.add(saveLabel);
        menuBar.add(undoLabel);
        menuBar.add(redoLabel);
        menuBar.add(resetLabel);
        menuBar.add(exitLabel);
        menuBar.add(helpLabel);
        frame.setJMenuBar(menuBar);
    }

    /*
     * Method that displays a win message with the total score
     * and a small easteregg...
     */
    public void win(){
        isWarehouseManSprite = false;
        new AcceptFrame(frame,"Congratulations!",
        String.format("<html><div align=\"center\">You win!<br>Your score: %d</div></html>",
             controller.getGameScore()));
    }

    /**
     * Method that displays the launch menu
     * @param mode The mode of the saving
     */
    private void launchSaveMenu(int mode){
        new SaveFrame(frame, controller, false, mode);
    }

    /**
     * Method that displays the load menu
     */
    private void launchLoadMenu(){
        new LoadFrame(frame, controller);
    }

    /**
     * Method that creates a new game
     */
    private void launchNewGame(){
        if (controller.hasBeenModified() && !controller.isFinished()){
            launchSaveMenu(2);
        }
        else{
            controller.createNewGame();
            frame.setTitle(ConstantsGUI.DEFAULT_FRAME_TITLE);
        }
    }
}
