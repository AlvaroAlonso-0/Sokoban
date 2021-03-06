package es.upm.pproject.sokoban.view.frames;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.utils.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents an alert frame when the user attempts to save a level with an existing name
* or exiting the application without saving the game.
* @author Idir Carlos Aliane Crespo
* @version 1.2
* @since 15/06/2022
*/
public class AlertFrame {

    private static final int MAX_WIDTH = 450;
    private static final int MAX_HEIGHT = 100;

    private Controller controller;
    private JFrame mainFrame;
    private JFrame saveFrame;
    private String levelName;

    private JFrame frame;
    private JPanel background;
    private JLabel infoLabel;
    private JLabel cancel;
    private JLabel accept;

    private boolean wantExit;
    private int mode;
    /**
     * The class constructor
     * @param controller The controller
     * @param mainFrame The main frame of the application
     * @param saveFrame The save frame (can be null)
     * @param levelName The level name
     * @param wantExit If the user wants to exit the level
     * @param mode Calling mode to build the AlertFrame (mode = 2) NewGame calling
     */
    public AlertFrame(Controller controller, JFrame mainFrame, JFrame saveFrame, 
                    String levelName, boolean wantExit, int mode){
        this.controller = controller;
        this.mainFrame = mainFrame;
        this.saveFrame = saveFrame;
        this.levelName = levelName;
        this.wantExit = wantExit;
        this.mode = mode;
        frame = UtilsGUI.createAndSetupFrame(saveFrame != null ? "Name in use" :
                                                                 "Are you sure?", MAX_WIDTH, MAX_HEIGHT);
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        infoLabel = new JLabel(saveFrame != null ? "Name already exists. Do you want to overwrite it?" : 
                                                   "You made movements. Do you want to save the game?");
        cancel = new JLabel("No");
        accept = new JLabel("Yes");
        infoLabel.setFont(ConstantsGUI.DEAULT_FONT);
        accept.setFont(ConstantsGUI.DEAULT_FONT);
        cancel.setFont(ConstantsGUI.DEAULT_FONT);

        infoLabel.setBounds(0, 12, MAX_WIDTH, 25);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cancel.setBounds(MAX_WIDTH/8 + MAX_WIDTH/15, MAX_HEIGHT/2, MAX_WIDTH/4, 30);
        cancel.setBackground(ConstantsGUI.LABEL_COLOR);
        cancel.setBorder(ConstantsGUI.DEFAULT_BORDER);
        cancel.setOpaque(true);
        cancel.setHorizontalAlignment(SwingConstants.CENTER);

        accept.setBounds(MAX_WIDTH/2 + MAX_WIDTH/16, MAX_HEIGHT/2, MAX_WIDTH/4, 30);
        accept.setBackground(ConstantsGUI.LABEL_COLOR);
        accept.setBorder(ConstantsGUI.DEFAULT_BORDER);
        accept.setOpaque(true);
        accept.setHorizontalAlignment(SwingConstants.CENTER);

        background.add(infoLabel);
        background.add(cancel);
        background.add(accept);

        UtilsGUI.addIcon(frame);
        
        frame.getContentPane().add(background);
        setupListeners();
        blockBackFrame();

    }

    /**
     * Method that prepares the listeners to react to different interactions at GUI
     */
    public void setupListeners(){
        if (saveFrame != null){
            setupAcceptOverwriteListener();
        }
        else{
            setupAcceptExitListener();
        }
        cancel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseEntered(MouseEvent e){
                cancel.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                cancel.setBackground(ConstantsGUI.LABEL_COLOR);
                if(saveFrame != null){
                    saveFrame.setVisible(true);
                    saveFrame.setEnabled(true);
                    saveFrame.toFront();
                }
                else{
                    mainFrame.setVisible(false);
                    mainFrame.setEnabled(true);
                    mainFrame.dispose();
                }
                frame.setVisible(false);
                frame.dispose();
            } 
            @Override
            public void mousePressed(MouseEvent e){
                cancel.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
            
            @Override
            public void mouseExited(MouseEvent e){
                cancel.setBackground(ConstantsGUI.LABEL_COLOR);
            }
        });
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                if (saveFrame != null){
                    saveFrame.setEnabled(true);
                    saveFrame.toFront();
                }
                else{
                    mainFrame.setEnabled(true);
                    mainFrame.toFront();
                }
            }
        });
    }

    /**
     * The listeners in case the user wants to save a game
     */
    private void setupAcceptOverwriteListener(){
        accept.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseEntered(MouseEvent e){
                accept.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
                if(controller.saveGame(levelName)){
                    if(wantExit){
                        frame.setVisible(false);
                        frame.dispose();
                        saveFrame.setVisible(false);
                        saveFrame.dispose();
                        mainFrame.setVisible(false);
                        mainFrame.dispose();
                    }
                    else{
                        frame.setVisible(false);
                        frame.dispose();
                        saveFrame.setVisible(false);
                        saveFrame.dispose();
                        mainFrame.setEnabled(true);
                        mainFrame.toFront();
                        mainFrame.setTitle(mode == 2 ? ConstantsGUI.DEFAULT_FRAME_TITLE : 
                                        String.format("%s - %s",ConstantsGUI.SOKOBAN_TITLE, levelName));
                        if (mode == 2){
                            controller.createNewGame();
                        }
                    }
                }
            } 
            @Override
            public void mousePressed(MouseEvent e){
                accept.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
           
            @Override
            public void mouseExited(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
            }
        });
    }

    /**
     * The listeners in case the user wants to exit a game
     */
    private void setupAcceptExitListener(){
        accept.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseEntered(MouseEvent e){
                accept.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
                SaveFrame sf = new SaveFrame(mainFrame, controller, wantExit, 0);
                frame.setVisible(false);
                frame.dispose();
                sf.setMainFrame(mainFrame);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                accept.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
           
            @Override
            public void mouseExited(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
            }
        });
    }

    /**
     * Method that blocks the back frame (so it doesn't dissapear)
     */
    private void blockBackFrame(){
        if (saveFrame != null){
            saveFrame.setEnabled(false);
        }
        else{
            mainFrame.setEnabled(false);
        }
    }
}
