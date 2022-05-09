package es.upm.pproject.sokoban.view;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Graphics;

/**
* Class that extends a JPanel for displaying a sprite in it.
* @author Idir Carlos Aliane Crespo
* @version 1.1
* @since 08/05/2022
*/
public class ImagePanel extends JPanel{
    private Image img;

    public ImagePanel(String spritePath) {
      img = new ImageIcon(spritePath).getImage();
      Dimension size = new Dimension(GUI.SPRITE_SIZE,GUI.SPRITE_SIZE);
      setPreferredSize(size);
      setMinimumSize(size);
      setMaximumSize(size);
      setSize(size);
      setLayout(null);
    }
    
    public void paintComponent(Graphics g) {
      g.drawImage(img, 0, 0, null);
    }

    public String toString(){
        return "(" + getX() + "," + getY() + ")";
    }
}
