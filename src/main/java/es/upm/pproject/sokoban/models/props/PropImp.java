package es.upm.pproject.sokoban.models.props;

import es.upm.pproject.sokoban.interfaces.Coordinates;
import es.upm.pproject.sokoban.interfaces.Prop;
import es.upm.pproject.sokoban.models.CoordinatesImp;


/**
 * Class that implements a prop entity.
 * @author Alvaro Alonso
 * @author Idir Carlos Aliane Crespo
 * @version 1.1
 * @since 22/04/2022
 */
public class PropImp implements Prop {
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

    @Override
    public Coordinates currentPos() {
        return new CoordinatesImp(this.currentPositionX,this.currentPositionY);
    }
}