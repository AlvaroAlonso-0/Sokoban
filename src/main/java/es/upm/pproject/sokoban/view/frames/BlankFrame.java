package es.upm.pproject.sokoban.view.frames;

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.view.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents an alert frame when the user attempts to save a level with an empty name.
* @author Idir Carlos Aliane Crespo
* @version 1.0
* @since 6/06/2022
*/
public class BlankFrame {

    private static final int MAX_WIDTH = 350;
    private static final int MAX_HEIGHT = 100;

    private JFrame saveFrame;

    private JFrame informationFrame;
    private JPanel background;
    private JLabel informationLabel;
    private JLabel acceptLabel;

    public BlankFrame(JFrame saveFrame) {
        this.saveFrame = saveFrame;
        saveFrame.setEnabled(false);
        
        informationFrame = UtilsGUI.createAndSetupFrame("Invalid name", MAX_WIDTH, MAX_HEIGHT);
        informationFrame.toFront();
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        informationLabel = new JLabel("Name cannot be blank!");
        acceptLabel = new JLabel("Accept");
        informationLabel.setFont(ConstantsGUI.DEAULT_FONT);
        acceptLabel.setFont(ConstantsGUI.DEAULT_FONT);

        informationLabel.setBounds(0, 12, MAX_WIDTH, 25);
        informationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        acceptLabel.setBounds(MAX_WIDTH/4, MAX_HEIGHT/2, MAX_WIDTH/2, 30);
        acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        acceptLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
        acceptLabel.setOpaque(true);
        acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        acceptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        background.add(informationLabel);
        background.add(acceptLabel);
        informationFrame.getContentPane().add(background);
        setupListeners();
    }

    public void setupListeners(){
        acceptLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
                saveFrame.setEnabled(true);
                saveFrame.toFront();
                informationFrame.setVisible(false);
                informationFrame.dispose();
            } 
            @Override
            public void mousePressed(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
            @Override
            public void mouseEntered(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            }
        });
    }
    
}