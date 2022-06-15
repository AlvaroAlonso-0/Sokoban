package es.upm.pproject.sokoban.view.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.view.utils.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;



public class HelpFrame {

    private static final int MAX_WIDTH = 450;
    private static final int MAX_HEIGHT = 250;

    private JFrame frame;
    private JPanel background;

    private JLabel helpLabel;

    private JLabel information;
    private JLabel acceptLabel;
    
    public HelpFrame(JLabel helpLabel){
        this.helpLabel = helpLabel;
        acceptLabel = new JLabel("Understood!");
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        information = new JLabel(createHelpText());
        information.setFont(ConstantsGUI.DEAULT_FONT);
        acceptLabel.setFont(ConstantsGUI.DEAULT_FONT);
        information.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        information.setBounds(0,0,MAX_WIDTH,MAX_HEIGHT);
        information.setHorizontalAlignment(SwingConstants.CENTER);
        information.setVerticalAlignment(SwingConstants.TOP);
        frame = UtilsGUI.createAndSetupFrame("Help", MAX_WIDTH, MAX_HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);

        acceptLabel.setBounds(MAX_WIDTH/4, MAX_HEIGHT - MAX_HEIGHT/4, MAX_WIDTH/2, 30);
        acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        acceptLabel.setBorder(ConstantsGUI.DEFAULT_BORDER);
        acceptLabel.setOpaque(true);
        acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
        acceptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        background.add(information);
        background.add(acceptLabel);

        frame.getContentPane().add(background);

        UtilsGUI.addIcon(frame);

        addListeners();

    }

    public void addListeners(){
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                frame.setEnabled(false);
                frame.dispose();
                helpLabel.setEnabled(true);
            }
        });
        acceptLabel.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                frame.setEnabled(false);
                frame.dispose();
                helpLabel.setEnabled(true);
                acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            } 
            @Override
            public void mousePressed(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
            @Override
            public void mouseExited(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.LABEL_COLOR);
            }
            @Override
            public void mouseEntered(MouseEvent e){
                acceptLabel.setBackground(ConstantsGUI.HOLDED_COLOR);
            }

        });
    }

    public String createHelpText(){
        StringBuilder builder = new StringBuilder();
        builder.append("<html>• The objective of the game is to move the boxes to the goals.<br>")
        .append("• The player can move the boxes by pushing them on to another square.<br>")
        .append("• The player can only move the boxes in one direction at a time.<br>")
        .append("• The available keys are: W - A - S - D for the player which represent up, ")
        .append("left, down, right respectively.<br>")
        .append("• The game is played by pressing the keys on the keyboard.<br>")
        .append("• The score is incremented when the player makes a valid move.<br>")
        .append("• The final score is the sum of the scores of all the levels.<br></html>");
        return builder.toString();
    }
}
