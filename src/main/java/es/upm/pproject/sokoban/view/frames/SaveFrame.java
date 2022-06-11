package es.upm.pproject.sokoban.view.frames;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.ConstantsGUI;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents a frame for saving a game.
* @author Idir Carlos Aliane Crespo
* @version 1.0
* @since 6/06/2022
*/
public class SaveFrame {

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 150;

    private Controller controller;
    private JFrame mainFrame;

    private JFrame frame;
    private JPanel background;
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel cancel;
    private JLabel accept;

    public SaveFrame(JFrame mainFrame, Controller controller){
        frame = UtilsGUI.createAndSetupFrame("Save game", MAX_WIDTH, MAX_HEIGHT);
        this.controller = controller;
        this.mainFrame  = mainFrame;
        mainFrame.setEnabled(false);
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        nameLabel = new JLabel("Level name");
        nameText = new JTextField();
        accept = new JLabel("Save");
        cancel = new JLabel("Cancel");
        nameLabel.setFont(ConstantsGUI.DEAULT_FONT);
        nameText.setFont(ConstantsGUI.DEAULT_FONT);
        accept.setFont(ConstantsGUI.DEAULT_FONT);
        cancel.setFont(ConstantsGUI.DEAULT_FONT);
        nameLabel.setBounds(MAX_WIDTH/8 + MAX_WIDTH/15, 27, 80, 25);
        nameText.setBounds(185, 27, 175, 25);
        

        cancel.setBounds(MAX_WIDTH/8 + MAX_WIDTH/15, MAX_HEIGHT/2 + 10, MAX_WIDTH/4, 30);
        cancel.setBackground(new Color(229, 243, 255));
        cancel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        cancel.setOpaque(true);
        cancel.setBackground(ConstantsGUI.LABEL_COLOR);
        cancel.setHorizontalAlignment(SwingConstants.CENTER);

        accept.setBounds(MAX_WIDTH/2 + MAX_WIDTH/16, MAX_HEIGHT/2 + 10, MAX_WIDTH/4, 30);
        accept.setBackground(new Color(229, 243, 255));
        accept.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        accept.setOpaque(true);
        accept.setBackground(ConstantsGUI.LABEL_COLOR);
        accept.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(nameLabel);
        background.add(nameText);
        background.add(cancel);
        background.add(accept);
        frame.getContentPane().add(background);
        setupListeners();
    }

    public void setupListeners(){
        accept.addMouseListener(new MouseAdapter(){  
            @Override
            public void mouseReleased(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
                if (!nameText.getText().isBlank()){
                    if (nameAlreadyExists(nameText.getText()+".xml")){
                        new OverwriteFrame(controller, mainFrame, frame, nameText.getText());
                    }
                    else {
                        if(controller.saveGame(nameText.getText())){
                            mainFrame.setEnabled(true);
                            mainFrame.toFront();
                            frame.setVisible(false);
                            frame.dispose();
                        }
                    }
                }
                else {
                    new BlankFrame(frame);
                }
            } 
            @Override
            public void mousePressed(MouseEvent e){
                accept.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
            @Override
            public void mouseEntered(MouseEvent e){
                accept.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e){
                accept.setBackground(ConstantsGUI.LABEL_COLOR);
            }
        });

        cancel.addMouseListener(new MouseAdapter(){ 
            @Override 
            public void mouseReleased(MouseEvent e){
                cancel.setBackground(ConstantsGUI.LABEL_COLOR);
                mainFrame.setEnabled(true);
                mainFrame.toFront();
                frame.setVisible(false);
                frame.dispose();
            } 
            @Override
            public void mousePressed(MouseEvent e){
                cancel.setBackground(ConstantsGUI.PRESSED_COLOR);                
            }
            @Override
            public void mouseEntered(MouseEvent e){
                cancel.setBackground(ConstantsGUI.HOLDED_COLOR);
            }
            @Override
            public void mouseExited(MouseEvent e){
                cancel.setBackground(ConstantsGUI.LABEL_COLOR);
            }
        });

        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                mainFrame.setEnabled(true);
                mainFrame.toFront();
            }
        });
    }

    private boolean nameAlreadyExists(String name){
        File folder = new File("games");
        File[] listOfFiles = folder.listFiles();
        boolean exists = false;
        for (int i = 0; i < listOfFiles.length && !exists; i++) {
            if (listOfFiles[i].getName().equals(name)){
                exists = true;
            }
        }
        return exists;
    }
}


