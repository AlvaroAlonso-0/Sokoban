package es.upm.pproject.sokoban.models.props;

import es.upm.pproject.sokoban.interfaces.Coordinates;
import es.upm.pproject.sokoban.interfaces.Movable;
import es.upm.pproject.sokoban.interfaces.Resetable;
import es.upm.pproject.sokoban.models.utils.CoordinatesImp;


/**
 * Class that implements a prop entity.
 * @author Alvaro Alonso
 * @author Idir Carlos Aliane Crespo
 * @version 1.3
 * @since 27/04/2022
 */
public abstract class Prop implements Movable, Resetable{
    private int initialStateX;
    private int initialStateY;
    private int currentPositionX;
    private int currentPositionY;

    /**
     * Constructor of the class.
     * @param initialStateX Initial position of the prop in the x axis.
     * @param initialStateY Initial position of the prop in the y axis.
     */
    protected Prop(int initialStateX, int initialStateY) {
        this.initialStateX = initialStateX;
        this.initialStateY = initialStateY;
        this.currentPositionX = initialStateX;
        this.currentPositionY = initialStateY;
    }

    /**
     * Returns the prop's current coordinates.
     * @return The prop's current coordinates.
     */
    public Coordinates currentPos() {
        return new CoordinatesImp(this.currentPositionX,this.currentPositionY);
    }

    public void reset() {
        this.currentPositionX = this.initialStateX;
        this.currentPositionY = this.initialStateY;
    }

    public void move(char dir) {
        // U = Up, D = Down, L = Left, R = Right
        switch (dir) {
            case 'L':
                this.currentPositionY--;
                break;
            case 'R':
                this.currentPositionY++;
                break;
            case 'U':
                this.currentPositionX--;
                break;
            case 'D':
                this.currentPositionX++;
                break;
            default:
                break;
        }
    }
}