package es.upm.pproject.sokoban.models.utils;

import es.upm.pproject.sokoban.interfaces.Coordinates;

public class CoordinatesImp implements Coordinates{
    private int x;
    private int y;
    public CoordinatesImp(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
}
