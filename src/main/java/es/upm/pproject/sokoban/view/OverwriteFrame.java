package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.text.LabelView;

import es.upm.pproject.sokoban.controller.Controller;

public class OverwriteFrame {
    
    private static final int MAX_WIDTH = 350;
    private static final int MAX_HEIGHT = 100;
    private static final Color colorLabel = new Color(229, 243, 255);

    private Controller controller;
    private JFrame mainFrame;
    private JFrame saveFrame;
    private String levelName;

    private JFrame overwriteFrame;
    private JPanel background;
    private JLabel infoLabel;
    private JLabel cancel;
    private JLabel accept;

    public OverwriteFrame(Controller controller, JFrame mainFrame, JFrame saveFrame, String levelName){
        this.controller = controller;
        this.mainFrame = mainFrame;
        this.saveFrame = saveFrame;
        this.levelName = levelName;
        setupFrame();
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        infoLabel = new JLabel("Name already exists. Do you want to overwrite it?");
        cancel = new JLabel("No");
        accept = new JLabel("Yes");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        accept.setFont(new Font("Arial", Font.PLAIN, 14));
        cancel.setFont(new Font("Arial", Font.PLAIN, 14));

        infoLabel.setBounds(0, 12, MAX_WIDTH, 25);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cancel.setBounds(MAX_WIDTH/8 + MAX_WIDTH/15, MAX_HEIGHT/2, MAX_WIDTH/4, 30);
        cancel.setBackground(new Color(229, 243, 255));
        cancel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        cancel.setOpaque(true);
        cancel.setBackground(colorLabel);
        cancel.setHorizontalAlignment(SwingConstants.CENTER);

        accept.setBounds(MAX_WIDTH/2 + MAX_WIDTH/16, MAX_HEIGHT/2, MAX_WIDTH/4, 30);
        accept.setBackground(new Color(229, 243, 255));
        accept.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        accept.setOpaque(true);
        accept.setBackground(colorLabel);
        accept.setHorizontalAlignment(SwingConstants.CENTER);

        background.add(infoLabel);
        background.add(cancel);
        background.add(accept);
        
        overwriteFrame.getContentPane().add(background);
        setupListeners();

    }
    
    private void setupFrame(){
        overwriteFrame = new JFrame("Invalid name");
        overwriteFrame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        overwriteFrame.setResizable(false);
        overwriteFrame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        overwriteFrame.pack();
        overwriteFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        overwriteFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        overwriteFrame.setLocation(dim.width/2-overwriteFrame.getSize().width/2, dim.height/2-overwriteFrame.getSize().height/2 - 35);
    }

    public void setupListeners(){
        Color pressedColor = new Color(204, 232, 255);
        accept.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){
                accept.setBackground(colorLabel);
                if(controller.saveGame(levelName)){
                    mainFrame.setEnabled(true);
                    mainFrame.toFront();
                    saveFrame.setVisible(false);
                    saveFrame.dispose();
                    overwriteFrame.setVisible(false);
                    overwriteFrame.dispose();
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
                saveFrame.setEnabled(true);
                saveFrame.toFront();
                overwriteFrame.setVisible(false);
                overwriteFrame.dispose();
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
    }
}
