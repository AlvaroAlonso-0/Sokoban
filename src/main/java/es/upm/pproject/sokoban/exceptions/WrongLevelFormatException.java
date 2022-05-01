package es.upm.pproject.sokoban.exceptions;

/**
 * Exception that is thrown when the level format is wrong.
 * @author Alvaro Alonso Miguel
 * @since 01/05/2022
 * @version 1.0
 */
public class WrongLevelFormatException extends Exception {
    public WrongLevelFormatException(String message) {
        super(message);
    }
}
