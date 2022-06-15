package es.upm.pproject.sokoban.view.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import es.upm.pproject.sokoban.controller.Controller;
import es.upm.pproject.sokoban.view.panels.DynamicPanelList;
import es.upm.pproject.sokoban.view.utils.UtilsGUI;

/**
* Class that represents a frame with a dynamic panel inside of every saved game.
* @author Idir Carlos Aliane Crespo
* @version 1.4
* @since 16/06/2022
*/
public class LoadFrame {
    
    private static final int MAX_WIDTH = 350;
    private static final int MAX_HEIGHT = 500;

    private JFrame frame;
    private JFrame mainFrame;

    private JPanel games;

    /**
     * Constructor of the class.
     * @param mainFrame The main frame
     * @param controller The controller
     */
    public LoadFrame(JFrame mainFrame, Controller controller){
        this.mainFrame = mainFrame;
        frame = UtilsGUI.createAndSetupFrame("Load game", MAX_WIDTH, MAX_HEIGHT);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);
        games = new DynamicPanelList(controller, mainFrame, frame);
        frame.add(games);
        mainFrame.setEnabled(false);
        UtilsGUI.addIcon(frame);
        addListener();
    }

    /**
     * Adds the listener to the frame.
     */
    private void addListener(){
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                mainFrame.setEnabled(true);
                mainFrame.toFront();
                frame.setEnabled(false);
                frame.dispose();
            }
        });
    }    
}
