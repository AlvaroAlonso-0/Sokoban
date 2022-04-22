package es.upm.pproject.sokoban.interfaces;

import es.upm.pproject.sokoban.models.Tile;

public interface Level {

    /**
     * Returns the tile at the specified position.
     * @param row Row of the tile.
     * @param column Column of the tile.
     * @return The tile at the specified position.
     */
    public Tile getTile(int row, int column);

    /**
     * Returns if a level is completed.
     * @return true if the level is completed, false otherwise.
     */
    public boolean checkStatus();
}
