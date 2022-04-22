package es.upm.pproject.sokoban.interfaces;

import es.upm.pproject.sokoban.models.Tile;
import es.upm.pproject.sokoban.models.props.Box;
import es.upm.pproject.sokoban.models.props.Player;

public interface Level {

    /**
     * Returns the tile at the specified position.
     * @param row Row of the tile.
     * @param column Column of the tile.
     * @return The tile at the specified position.
     */
    public Tile getTile(int row, int column);

    /**
     * Returns the player.
     * @return The player.
     */
    public Player getPlayer();

    /**
     * Returns the box at the specified position.
     * @param index Index of the box.
     * @return The box at the specified position.
     */
    public Box getBox(int index);

    /**
     * Returns if a level is completed.
     * @return true if the level is completed, false otherwise.
     */
    public boolean checkStatus();
}
