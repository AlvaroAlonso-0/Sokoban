package es.upm.pproject.sokoban.view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import es.upm.pproject.sokoban.controller.Controller;

public class DynamicPanelList extends JPanel{

    private static final Color colorLabel = new Color(229, 243, 255);
    private Controller controller;
    private JFrame mainFrame;
    private JFrame loadFrame;

    private JPanel mainList;
    
    public DynamicPanelList(Controller controller, JFrame mainFrame, JFrame loadFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        this.loadFrame = loadFrame;
        setLayout(new BorderLayout());
        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(),gbc);
        add(new JScrollPane(mainList));
        setupSavedGames();
    }

    private void setupSavedGames(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        File folder = new File("games");
        File[] listOfFiles = folder.listFiles();
        Font font = new Font("Arial", Font.PLAIN, 14);
        Color pressedColor = new Color(204, 232, 255);
        for (int i = 0; i < listOfFiles.length; i++) {
            JPanel panel = new JPanel();
            JLabel game = new JLabel(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length()-4));
            game.setFont(font);
            game.setBackground(new Color(229, 243, 255));
            panel.setOpaque(true);
            panel.setBackground(colorLabel);
            game.setHorizontalAlignment(SwingConstants.CENTER);
            panel.addMouseListener(new MouseAdapter(){  
                public void mouseReleased(MouseEvent e){
                    panel.setBackground(colorLabel);
                    mainFrame.setEnabled(true);
                    mainFrame.toFront();
                    loadFrame.setVisible(false);
                    loadFrame.dispose();
                    controller.loadGame(game.getText());
                } 
                public void mousePressed(MouseEvent e){
                    panel.setBackground(pressedColor);                
                }
                public void mouseEntered(MouseEvent e){
                    panel.setBackground(new Color(218, 234, 247));
                }
                public void mouseExited(MouseEvent e){
                    panel.setBackground(colorLabel);
                }
            });
            panel.add(game);
            panel.setBorder(new MatteBorder(0, 0, 1, 0, new Color(153, 209, 255)));
            mainList.add(panel, gbc, 0);
            validate();
            repaint();
        }
    }

    public static void main(String [] args){

    }
}
