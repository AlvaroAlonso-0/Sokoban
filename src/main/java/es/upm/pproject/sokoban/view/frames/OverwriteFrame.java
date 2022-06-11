package es.upm.pproject.sokoban.view.frames;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents an alert frame when the user attempts to save a level with an existing name.
* @author Idir Carlos Aliane Crespo
* @version 1.0
* @since 6/06/2022
*/
public class OverwriteFrame {
    
    private static final int MAX_WIDTH = 350;
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

    public OverwriteFrame(Controller controller, JFrame mainFrame, JFrame saveFrame, String levelName){
        this.controller = controller;
        this.mainFrame = mainFrame;
        this.saveFrame = saveFrame;
        this.levelName = levelName;
        frame = UtilsGUI.createAndSetupFrame("Name in use", MAX_WIDTH, MAX_HEIGHT);
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        infoLabel = new JLabel("Name already exists. Do you want to overwrite it?");
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
        
        frame.getContentPane().add(background);
        setupListeners();

    }

    public void setupListeners(){
        accept.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseEntered(MouseEvent e){
                accept.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
                if(controller.saveGame(levelName)){
                    mainFrame.setEnabled(true);
                    mainFrame.toFront();
                    saveFrame.setVisible(false);
                    saveFrame.dispose();
                    frame.setVisible(false);
                    frame.dispose();
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

        cancel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseEntered(MouseEvent e){
                cancel.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseReleased(MouseEvent e){
                cancel.setBackground(ConstantsGUI.LABEL_COLOR);
                saveFrame.setEnabled(true);
                saveFrame.toFront();
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
    }
}
