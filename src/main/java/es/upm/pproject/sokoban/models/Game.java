package es.upm.pproject.sokoban.models;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.interfaces.Resetable;
import es.upm.pproject.sokoban.models.level.Level;

/**
* Class that represents a Sokoban game
* @author Raul Casamayor Navas
* @author Rafael Alonso Sirera
* @author Alvaro Alonso Miguel
* @version 1.8
* @since 15/06/2022
*/
public class Game implements Resetable{

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final Marker gameMarker = MarkerFactory.getMarker("GAME");
    private static final Marker loadMarker = MarkerFactory.getMarker("FATAL");
    
    protected String levelPathFormat;
   
    protected Level lvl;
    protected int levelNumber;
    protected boolean gameFinished;
    protected int score;
    protected boolean hasBeenModified; 

    public Game(){
        levelPathFormat = Level.LEVEL_FILE_NAME_FORMAT;
        newGame();
    }

    /**
     * Method used to move the warehouse man in each level of the game.
     * @param dir Direction in which the warehouse man has to move in the current level of the game
     * @return If the movement has been done 
     */
    public boolean movePlayer(char dir){
        if(gameFinished || !lvl.movePlayer(dir)) return false;
        if(lvl.checkStatus()){
            levelNumber++;
            score += lvl.getScore();
            levelLoad();
        }
        hasBeenModified = true;
        return true;
    }

    /**
     * Method used to revert the last movement of the warehouse man in the current level.
     * @return If a move has been undone
     */
    public boolean undo(){
        if(gameFinished || !lvl.undoMove()) return false;
        hasBeenModified = true;
        return true;
    }

    /**
     * Method used to revert the last undone movement of the warehouse man in the current level.
     * @return If a move has been redone
     */
    public boolean redo(){
        if(gameFinished || !lvl.redoMove()) return false;
        hasBeenModified = true;
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
     * Method used to update the status after saved
     */
    public void gameSaved(){
        hasBeenModified = false;
    }

    @Override
    public String toString() {
        return String.format("Level %d%n%s", levelNumber, lvl);
    }

    @Override
    public void reset() {
        lvl.reset();
        if(levelNumber == 1){
            hasBeenModified = false;
        }
    }

    /**
     * Starts a new game from the beginning level.
     */
    public void newGame(){
        levelNumber = 1;
        gameFinished = false;
        score = 0;
        hasBeenModified = false;
        logger.info(gameMarker, "New game started");
        levelLoad();
    }

    /**
     * Private method used to load the level of the game.
     */
    private void levelLoad(){
        try{
            lvl = new Level(String.format(levelPathFormat, levelNumber));
        }catch (FileNotFoundException e){
            logger.info(gameMarker,"Game completed");
            gameFinished = true;   
        }catch(WrongLevelFormatException e){
            logger.error(loadMarker, String.format("Level %d couldnt be loaded", levelNumber), e);
            levelNumber++;
            levelLoad();
        }
    }
}
