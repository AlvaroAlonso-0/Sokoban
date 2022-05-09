package es.upm.pproject.sokoban.game;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;

/**
* Class that represents a Sokoban game used by the controller of the app.
* @author Raul Casamayor Navas
* @version 1.0
* @since 09/05/2022
*/
public class GameStatusGUI extends Game{

    public GameStatusGUI() throws WrongLevelFormatException {
        super();
    }

    /**
     * Method needed by the gui to print the current status of the game.
     * @return The board as a String
     */
    public String getBoardToString(){
        return getLevel().toString();
    }
    
}
