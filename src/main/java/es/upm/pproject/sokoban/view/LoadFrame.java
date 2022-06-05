package es.upm.pproject.sokoban.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import es.upm.pproject.sokoban.controller.Controller;

public class LoadFrame {
    
    private static final int MAX_WIDTH = 350;
    private static final int MAX_HEIGHT = 500;

    private Controller controller;
    private JFrame mainFrame;
    private JPanel games;

    private JFrame loadFrame;

    public LoadFrame(JFrame mainFrame, Controller controller){
        this.mainFrame = mainFrame;
        this.controller = controller;
        setupFrame();
        games = new DynamicPanelList(controller, mainFrame, loadFrame);
        games.setBackground(Color.RED);
        loadFrame.add(games);
    }
    

    private void setupFrame(){
        loadFrame = new JFrame("Load game");
        loadFrame.setSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        loadFrame.setResizable(false);
        loadFrame.getContentPane().setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        loadFrame.pack();
        loadFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        loadFrame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        loadFrame.setLocation(dim.width/2-loadFrame.getSize().width/2, dim.height/2-loadFrame.getSize().height/2 - 35);
    }
}
