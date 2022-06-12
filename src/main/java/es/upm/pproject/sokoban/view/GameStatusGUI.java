package es.upm.pproject.sokoban.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.models.Game;
import es.upm.pproject.sokoban.models.level.Level;

/**
* Class that represents a Sokoban game used by the controller of the app.
* @author Raul Casamayor Navas
* @author Alvaro Alonso Miguel
* @version 1.4
* @since 12/06/2022
*/
@XmlRootElement(name="game")
public class GameStatusGUI extends Game{

    public GameStatusGUI() throws WrongLevelFormatException {
        super();
    }

    /**
     * Method needed by the gui to print the current status of the game.
     * @return The board as a String
     */
    public String getBoardToString(){
        return lvl.toString();
    }
    
    /**
     * Method needed by the gui to retrieve the total game score.
     * @return The game score
     */
    @Override
    public int getTotalScore(){
        return this.score + lvl.getScore();
    }

    /**
     * Method needed by the gui to retrieve the level score.
     * @return The level score
     */
    public int getLevelScore(){
        return lvl.getScore();
    }

    /* Getters and setters needed for xml binding*/
    
    @XmlElement(name="level")
    public Level getLevel(){
        return lvl;
    }

    public void setLevel(Level lvl){
        this.lvl = lvl;
    }

    @XmlElement(name="levelNumber")
    public int getLevelNumber(){
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber){
        this.levelNumber = levelNumber;
    }

    @XmlElement(name="gameFinished")
    public boolean getGameFinished(){
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished){
        this.gameFinished = gameFinished;
    }
    
    @XmlElement(name="score")
    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
