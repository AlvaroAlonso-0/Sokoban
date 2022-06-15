package es.upm.pproject.sokoban.view.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
* Auxiliar class for util functions for the GUI.
* @author Idir Carlos Aliane Crespo
* @version 1.2
* @since 15/06/2022 
 */
public class UtilsGUI {

    private UtilsGUI(){ }
    
    /**
     * Creates and sets up a frame.
     * @param title The title of the frame
     * @param width The width of the frame
     * @param height The height of the frame
     * @return The frame
     */
    public static JFrame createAndSetupFrame(String title, int width, int height) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.getContentPane().setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2 - 35);
        return frame;
    }

    /**
     * Gets a random Box on Goal sprite.
     * @param x The x coordinate of the sprite
     * @param y The y coordinate of the sprite
     * @param offset a random offset
     * @return The sprite path
     */
    public static String getRandomSprite(int x, int y, double offset){
        int length = ConstantsGUI.BOX_GOALS.length;
        return ConstantsGUI.BOX_GOALS[(int)((x*y + offset*length)%length)];
    }
    
    /**
     * Adds an icon to a frame.
     * @param frame The frame
     */
    public static void addIcon(JFrame frame){
        ImageIcon icon = new ImageIcon(ConstantsGUI.MAIN_ICON);
        frame.setIconImage(icon.getImage());
    }
}
