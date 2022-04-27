package es.upm.pproject.sokoban.interfaces;

import es.upm.pproject.sokoban.models.Tile;

public interface Level {

    
    public Tile getTile(int row, int column);
    
    public boolean checkStatus();
}
