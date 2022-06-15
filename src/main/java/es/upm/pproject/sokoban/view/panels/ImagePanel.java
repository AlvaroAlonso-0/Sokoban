package es.upm.pproject.sokoban.view.panels;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import es.upm.pproject.sokoban.view.utils.ConstantsGUI;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

/**
* Class that extends a JPanel to display a sprite on it.
* @author Idir Carlos Aliane Crespo
* @version 1.4
* @since 15/06/2022
*/
public class ImagePanel extends JPanel{
    private transient Image img;

    /**
     * The class constructor
     * @param spritePath The path of the sprite to display
     */
    public ImagePanel(String spritePath) {
        img = new ImageIcon(spritePath).getImage();
        Dimension size = new Dimension(ConstantsGUI.SPRITE_SIZE,ConstantsGUI.SPRITE_SIZE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);    
    }

    /**
     * The class constructor
     * @param spritePath The sprite to display
     * @param width Sprite dimension (width)
     * @param height Sprite dimension (height)
     */
    public ImagePanel(String spritePath, int width, int height) {
        img = new ImageIcon(spritePath).getImage();
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);  
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
