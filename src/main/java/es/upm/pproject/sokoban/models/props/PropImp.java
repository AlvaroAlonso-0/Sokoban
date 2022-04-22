package es.upm.pproject.sokoban.models.props;

import es.upm.pproject.sokoban.interfaces.Prop;

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
}