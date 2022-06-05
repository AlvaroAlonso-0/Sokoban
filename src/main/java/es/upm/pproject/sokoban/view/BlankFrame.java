package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class BlankFrame {

    private final static int MAX_WIDTH = 350;
    private final static int MAX_HEIGHT = 100;
    private static final Color colorLabel = new Color(229, 243, 255);

    private JFrame saveFrame;

    private JFrame informationFrame;
    private JPanel background;
    private JLabel informationLabel;
    private JLabel acceptLabel;

    public BlankFrame(JFrame saveFrame) {
        this.saveFrame = saveFrame;
        saveFrame.setEnabled(false);
        
        setupFrame();
        informationFrame.toFront();
        background = new JPanel();
        background.setBounds(0,0, MAX_WIDTH, MAX_HEIGHT);
        background.setLayout(null);
        informationLabel = new JLabel("Name cannot be blank!");
        acceptLabel = new JLabel("Accept");
        informationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        acceptLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        informationLabel.setBounds(0, 12, MAX_WIDTH, 25);
        informationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        acceptLabel.setBounds(MAX_WIDTH/4, MAX_HEIGHT/2, MAX_WIDTH/2, 30);
        acceptLabel.setBackground(new Color(229, 243, 255));
        acceptLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,new Color(153, 209, 255)));
        acceptLabel.setOpaque(true);
        acceptLabel.setBackground(colorLabel);
        acceptLabel.setHorizontalAlignment(SwingConstants.CENTER);

        background.add(informationLabel);
        background.add(acceptLabel);
        informationFrame.getContentPane().add(background);
        setupListeners();
    }

    private void setupFrame(){
        informationFrame = new JFrame("Invalid name");
        informationFrame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        informationFrame.setResizable(false);
        informationFrame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        informationFrame.pack();
        informationFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        informationFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        informationFrame.setLocation(dim.width/2-informationFrame.getSize().width/2, dim.height/2-informationFrame.getSize().height/2 - 35);
    }

    public void setupListeners(){
        Color pressedColor = new Color(204, 232, 255);
        acceptLabel.addMouseListener(new MouseAdapter(){  
            public void mouseReleased(MouseEvent e){
                acceptLabel.setBackground(colorLabel);
                saveFrame.setEnabled(true);
                saveFrame.toFront();
                informationFrame.setVisible(false);
                informationFrame.dispose();
            } 
            public void mousePressed(MouseEvent e){
                acceptLabel.setBackground(pressedColor);                
            }
            public void mouseEntered(MouseEvent e){
                acceptLabel.setBackground(new Color(218, 234, 247));
            }
            public void mouseExited(MouseEvent e){
                acceptLabel.setBackground(colorLabel);
            }
        });
    }
    
}
