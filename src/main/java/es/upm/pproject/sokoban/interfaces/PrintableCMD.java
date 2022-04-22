package es.upm.pproject.sokoban.interfaces;

/**
 * Interface that is used for allowing objects to be printed in the command line
 * @author Idir Carlos Aliane Crespo
 * @version 1.0
 * @since 22/04/2022
 */
public interface PrintableCMD {
    
    /**
     * Methd that returs the String represenation of this tile
     * @return a String of the representation of the tile
     */
    public String printTileCMD();
}
