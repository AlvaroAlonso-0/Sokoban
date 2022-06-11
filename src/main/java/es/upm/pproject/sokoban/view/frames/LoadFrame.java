package es.upm.pproject.sokoban.view.frames;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.panels.DynamicPanelList;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents a frame with a dynamic panel inside of every saved game.
* @author Idir Carlos Aliane Crespo
* @version 1.1
* @since 11/06/2022
*/
public class LoadFrame {
    
    private static final int MAX_WIDTH = 350;
    private static final int MAX_HEIGHT = 500;

    private JFrame frame;

    private JPanel games;

    public LoadFrame(JFrame mainFrame, Controller controller){
        frame = UtilsGUI.createAndSetupFrame("Load game", MAX_WIDTH, MAX_HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);
        games = new DynamicPanelList(controller, mainFrame, frame);
        frame.add(games);
        mainFrame.setEnabled(false);
    }
}
