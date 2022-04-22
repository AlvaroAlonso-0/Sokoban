package es.upm.pproject.sokoban.models.props;

import es.upm.pproject.sokoban.interfaces.Coordinates;
import es.upm.pproject.sokoban.interfaces.Prop;
import es.upm.pproject.sokoban.models.utils.CoordinatesImp;


/**
 * Class that implements a prop entity.
 * @author Alvaro Alonso
 * @author Idir Carlos Aliane Crespo
 * @version 1.2
 * @since 22/04/2022
 */
public class PropImp implements Prop{
    private int initialStateX;
    private int initialStateY;
    private int currentPositionX;
    private int currentPositionY;

    public PropImp(int initialStateX, int initialStateY) {
        this.initialStateX = initialStateX;
        this.initialStateY = initialStateY;
        this.currentPositionX = initialStateX;
        this.currentPositionY = initialStateY;
    }

    public Coordinates currentPos() {
        return new CoordinatesImp(this.currentPositionX,this.currentPositionY);
    }

    public void reset() {
        this.currentPositionX = this.initialStateX;
        this.currentPositionY = this.initialStateY;
    }

    @Override
    public void move(char dir) {
        switch (dir) {
            case 'S':
                this.currentPositionY--;
                break;
            case 'W':
                this.currentPositionY++;
                break;
            case 'A':
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