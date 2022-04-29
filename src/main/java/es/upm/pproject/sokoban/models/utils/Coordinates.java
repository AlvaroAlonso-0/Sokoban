package es.upm.pproject.sokoban.models.utils;

/**
 * Class that represents a pair of coordinates x and y.
 * @author Alvaro Alonso
 * @author Idir Carlos Aliane Crespo
 * @version 2.0
 * @since 29/04/2022
 */
public class Coordinates{
    private int x;
    private int y;
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * returns the x coordinate
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * returns the y coordinate
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
    
}
