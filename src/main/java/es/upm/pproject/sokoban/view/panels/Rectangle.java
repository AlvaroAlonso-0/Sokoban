package es.upm.pproject.sokoban.view.panels;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Rectangle extends JPanel{

    public Rectangle(int width, int height) {
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }
}
