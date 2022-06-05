package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import es.upm.pproject.sokoban.controller.Controller;

public class SaveFrame {

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 150;
    private static final Color colorLabel = new Color(229, 243, 255);

    private Controller controller;
    private JFrame mainFrame;

    private JFrame saveFrame;
    private JPanel background;
    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel cancel;
    private JLabel accept;

    public SaveFrame(JFrame mainFrame, Controller controller){
        setupFrame();
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
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameText.setFont(new Font("Arial", Font.PLAIN, 14));
        accept.setFont(new Font("Arial", Font.PLAIN, 14));
        cancel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setBounds(MAX_WIDTH/8 + MAX_WIDTH/15, 27, 80, 25);
        nameText.setBounds(185, 27, 175, 25);
        

        cancel.setBounds(MAX_WIDTH/8 + MAX_WIDTH/15, MAX_HEIGHT/2 + 10, MAX_WIDTH/4, 30);
        cancel.setBackground(new Color(229, 243, 255));
        cancel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        cancel.setOpaque(true);
        cancel.setBackground(colorLabel);
        cancel.setHorizontalAlignment(SwingConstants.CENTER);

        accept.setBounds(MAX_WIDTH/2 + MAX_WIDTH/16, MAX_HEIGHT/2 + 10, MAX_WIDTH/4, 30);
        accept.setBackground(new Color(229, 243, 255));
        accept.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        accept.setOpaque(true);
        accept.setBackground(colorLabel);
        accept.setHorizontalAlignment(SwingConstants.CENTER);
        background.add(nameLabel);
        background.add(nameText);
        background.add(cancel);
        background.add(accept);
        saveFrame.getContentPane().add(background);
        setupListeners();
    }

    private void setupFrame(){
        saveFrame = new JFrame("Save game");
        saveFrame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        saveFrame.setResizable(false);
        saveFrame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        saveFrame.pack();
        saveFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        saveFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        saveFrame.setLocation(dim.width/2-saveFrame.getSize().width/2, dim.height/2-saveFrame.getSize().height/2 - 35);
    }

    public void setupListeners(){
        Color pressedColor = new Color(204, 232, 255);
        accept.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){
                accept.setBackground(colorLabel);
                if (!nameText.getText().isBlank()){
                    if (nameAlreadyExists(nameText.getText()+".xml")){
                        new OverwriteFrame(controller, mainFrame, saveFrame, nameText.getText());
                    }
                    else {
                        if(controller.saveGame(nameText.getText())){
                            mainFrame.setEnabled(true);
                            mainFrame.toFront();
                            saveFrame.setVisible(false);
                            saveFrame.dispose();
                        }
                    }
                }
                else {
                    new BlankFrame(saveFrame);
                }
            } 
            public void mousePressed(MouseEvent e){
                accept.setBackground(pressedColor);                
            }
            public void mouseEntered(MouseEvent e){
                accept.setBackground(new Color(218, 234, 247));
            }
            public void mouseExited(MouseEvent e){
                accept.setBackground(colorLabel);
            }
        });

        cancel.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){
                cancel.setBackground(colorLabel);
                mainFrame.setEnabled(true);
                mainFrame.toFront();
                saveFrame.setVisible(false);
                saveFrame.dispose();
            } 
            public void mousePressed(MouseEvent e){
                cancel.setBackground(pressedColor);                
            }
            public void mouseEntered(MouseEvent e){
                cancel.setBackground(new Color(218, 234, 247));
            }
            public void mouseExited(MouseEvent e){
                cancel.setBackground(colorLabel);
            }
        });

        saveFrame.addWindowListener(new WindowAdapter(){
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
