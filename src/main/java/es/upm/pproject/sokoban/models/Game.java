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
* @version 1.5.2
* @since 11/06/2022
*/
public class Game implements Resetable{

    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final Marker gameMarker = MarkerFactory.getMarker("GAME");
   
    protected Level lvl;
    protected int levelNumber;
    protected boolean gameFinished;
    protected int score;

    public Game() throws WrongLevelFormatException{
        newGame();
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
            score += lvl.getScore();
            levelLoad();
        }
        return true;
    }
    /**
     * Method used to revert the las movement of the warehouse man in the current level.
     * @return If a move has been undone
     */
    public boolean undo(){
        if(gameFinished) return false;
        return lvl.undoMove();
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
            logger.info(gameMarker,"Game completed");
            gameFinished = true;   
        }
    }

    @Override
    public String toString() {
        return String.format("Level %d%n%s", levelNumber, lvl);
    }

    @Override
    public void reset() {
        lvl.reset();
    }

    public void newGame() throws WrongLevelFormatException{
        levelNumber = 1;
        gameFinished = false;
        logger.info(gameMarker, "New game started");
        levelLoad();
    }

    public int getTotalScore(){
        return this.score + lvl.getScore();
    }
}
