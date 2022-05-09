package es.upm.pproject.sokoban.game;

import java.io.FileNotFoundException;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.models.Level;

/**
* Class that represents a Sokoban game
* @author Raul Casamayor Navas
* @version 1.0
* @since 09/05/2022
*/
public class Game {
    
    private Level lvl;
    private int levelNumber;
    private boolean gameFinished;

    public Game() throws WrongLevelFormatException{
        levelNumber = 1;
        gameFinished = false;
        levelLoad();
    }

    /**
     * Method used to move the warehouse man in each level of the game.
     * @param dir Direction in which the warehouse man has to move in the current level of the game
     * @return If the movement has been done
     * @throws WrongLevelFormatException When the level has been completed and the next one to load doesnt follow 
     * the format required
     */
    public boolean movePlayer(char dir) throws WrongLevelFormatException{
        if(gameFinished || !lvl.movePlayer(dir)) return false;
        if(lvl.checkStatus()){
            levelNumber++;
            levelLoad();
        }
        return true;
    }

    /**
     * Method used to check if the game is finished.
     * @return If game is finished
     */
    public boolean isFinished(){
        return gameFinished;
    }

    /**
     * Private method used to load the level of the game.
     * @throws WrongLevelFormatException When the level file doesnt follow the format required
     */
    private void levelLoad() throws WrongLevelFormatException{
        try{
            lvl = new Level(String.format(Level.LEVEL_FILE_NAME_FORMAT, levelNumber));
        }catch (FileNotFoundException e){
            gameFinished = true;
        }
    }

    /**
     * Protected method to give access to the level object.
     * @return The current level
     */
    protected Level getLevel(){
        return lvl;
    }

    @Override
    public String toString() {
        return String.format("Level %d%n%s", levelNumber, lvl);
    }
}
