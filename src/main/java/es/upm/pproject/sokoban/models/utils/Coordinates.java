package es.upm.pproject.sokoban.models.utils;

/**
 * Class that represents a pair of coordinates x and y.
 * @author Alvaro Alonso
 * @author Idir Carlos Aliane Crespo
 * @author Raul Casamayor Navas
 * @version 2.1
 * @since 01/05/2022
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

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj.getClass() == Coordinates.class) &&
                this.x==((Coordinates)obj).x && this.y==((Coordinates)obj).y;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
