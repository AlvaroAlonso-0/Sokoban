package es.upm.pproject.sokoban.interfaces;

/**
 * Interface that represents a prop entity.
 * @author Idir Carlos Aliane Crespo
 * @version 1.0
 * @since 22/04/2022
 */
public interface Prop extends Resetable, Movable {

    /**
     * returns the current coordinates of the prop
     * @return the coordinates of this prop
     */
    public Coordinates currentPos();
    
}
