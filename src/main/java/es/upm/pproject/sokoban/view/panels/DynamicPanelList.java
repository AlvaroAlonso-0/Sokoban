package es.upm.pproject.sokoban.view.panels;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.utils.ConstantsGUI;

/**
* Class that represents a dynamic panel of every saved game for loading.
* @author Idir Carlos Aliane Crespo
* @version 1.1
* @since 14/06/2022
*/
public class DynamicPanelList extends JPanel{

    private static final Border listBorder = new MatteBorder(0, 0, 1, 0, new Color(153, 209, 255));
    private transient Controller controller;
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
        for (int i = 0; i < listOfFiles.length; i++) {
            JPanel panel = new JPanel();
            JLabel game = new JLabel(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length()-4));
            game.setFont(font);
            game.setBackground(new Color(229, 243, 255));
            panel.setOpaque(true);
            panel.setBackground(ConstantsGUI.LABEL_COLOR);
            game.setHorizontalAlignment(SwingConstants.CENTER);
            panel.addMouseListener(new MouseAdapter(){  
                @Override
                public void mouseReleased(MouseEvent e){
                    panel.setBackground(ConstantsGUI.LABEL_COLOR);
                    mainFrame.setEnabled(true);
                    mainFrame.toFront();
                    loadFrame.setVisible(false);
                    loadFrame.dispose();
                    controller.loadGame(game.getText());
                    mainFrame.setTitle("Sokoban - " + game.getText());
                } 
                @Override
                public void mousePressed(MouseEvent e){
                    panel.setBackground(ConstantsGUI.PRESSED_COLOR);                
                }
                
                @Override
                public void mouseExited(MouseEvent e){
                    panel.setBackground(ConstantsGUI.LABEL_COLOR);
                }
            });
            panel.add(game);
            panel.setBorder(listBorder);
            mainList.add(panel, gbc, 0);
            validate();
            repaint();
        }
    }
}
