package es.upm.pproject.sokoban.view.frames;

import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.view.utils.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents an alert frame when the user attempts to save a level with an empty name.
* @author Idir Carlos Aliane Crespo
* @version 1.1
* @since 11/06/2022
*/
public class AcceptFrame {

    private static final int MAX_WIDTH = 350;
    private static final int MAX_HEIGHT = 100;

    private JFrame backFrame;

    private JFrame informationFrame;
    private JPanel background;
    private JLabel informationLabel;
    private JLabel acceptLabel;

    public AcceptFrame(JFrame backFrame, String frameTitle, String information) {
        this.backFrame = backFrame;
        backFrame.setEnabled(false);
        
        informationFrame = UtilsGUI.createAndSetupFrame(frameTitle, MAX_WIDTH, MAX_HEIGHT);
        informationFrame.toFront();
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        informationLabel = new JLabel(information);
        acceptLabel = new JLabel("Accept");
        informationLabel.setFont(ConstantsGUI.DEAULT_FONT);
        acceptLabel.setFont(ConstantsGUI.DEAULT_FONT);

        informationLabel.setBounds(0, 10, MAX_WIDTH, 30);
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
        UtilsGUI.addIcon(informationFrame);
        setupListeners();
    }

    public void setupListeners(){
        informationFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                backFrame.setEnabled(true);
                backFrame.toFront();
            }
        });
        acceptLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
                backFrame.setEnabled(true);
                backFrame.toFront();
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
