package es.upm.pproject.sokoban.interfaces;

/**
 * Interface that makes movable props
 * @author Idir Carlos Aliane Crespo
 * @version 1.0
 * @since 22/04/2022
 */
public interface Movable {
    
    /**
     * moves a prop to the direction indicated in the param
     * @param dir the direction of the move
     */
    public void move(final char dir);
}
