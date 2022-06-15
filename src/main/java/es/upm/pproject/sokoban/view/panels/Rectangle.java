package es.upm.pproject.sokoban.view.panels;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
* Class that represents a Rectangle it will be used to draw borders surrounding the game board
* @author Idir Carlos Aliane Crespo
* @version 1.1
* @since 15/06/2022
*/
public class Rectangle extends JPanel{

    /**
     * Rectangle constructor
     * @param width Rectangle width 
     * @param height Rectangle height
     */
    public Rectangle(int width, int height) {
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }
}
