package es.upm.pproject.sokoban.controller;

import javax.xml.bind.JAXBException;

import java.awt.Dimension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.sokoban.models.sgfactory.SaveGameFactory;
import es.upm.pproject.sokoban.view.GUI;
import es.upm.pproject.sokoban.view.GameStatusGUI;


/**
* Class for the Controller of the application.
* @author Rafael Alonso Sirera
* @author Raul Casamayor Navas
* @version 1.5
* @since 15/06/2022
*/
public class Controller{
    
    private static final String SAVED_GAMES_FORMAT = "games/%s.xml";
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final Marker sgMarker = MarkerFactory.getMarker("SGFACTORY");
    
    private GUI gui;
    private GameStatusGUI game;
    private SaveGameFactory sgFactory;
    
    public Controller(){
        gui = new GUI(this);
        try {
            game = new GameStatusGUI();
            gui.init(game.getBoardToString());
            sgFactory = new SaveGameFactory();
        } catch(JAXBException e){
            logger.error(sgMarker, "An error ocurred while creating the SaveGameFactory", e);
        }
    }
    
    /**
    * Method used to interact with the model from the GUI
    * @param key Key pressed
    */
    public void keyTyped(char key){
        char dir;
        switch(key){
            case 'W': dir='U'; break;
            case 'A': dir='L'; break;
            case 'S': dir='D'; break;
            case 'D': dir='R'; break;
            case 'U': undo(); return;
            case 'R': redo(); return;
            default: dir='E';  break;
        }
        if(game.movePlayer(dir)){
            repaint();
        }
    }
    
    /**
    * Method used to save the current status of the game into a file.
    * @param name Name for the game
    * @return If the game could be saved
    */
    public boolean saveGame(String name){
        return sgFactory.saveGame(game, String.format(SAVED_GAMES_FORMAT, name));
    }
    
    /**
    * Method used to load a saved game.
    * @param name Name of the game to load
    * @return If the game could be loaded
    */
    public boolean loadGame(String name){
        GameStatusGUI loadedGame;
        if((loadedGame = sgFactory.loadGame(String.format(SAVED_GAMES_FORMAT, name))) == null) return false;
        game = loadedGame;
        repaint();
        return true;
    }
    
    public void createNewGame(){
        game.newGame();
        show();
    }
    
    /**
    * Method used to retrieve the score of the current game.
    * @return Game score
    */
    public int getGameScore(){
        return game.getTotalScore();
    }
    
    /**
    * Method used to retrieve the level score of the current game.
    * @return Level score
    */
    public int getLevelScore(){
        return game.getLevelScore();
    }
    
    /**
    * Method used to retrieve the level name of the current game.
    * @return Level name
    */
    public String getLevelName(){
        return game.getLevelName();
    }
    
    public Dimension getLevelDimension(){
        return game.getDimension();
    }
    
    public boolean hasBeenModified(){
        return game.hasBeenModified();
    }
    
    public boolean isFinished(){
        return game.isFinished();
    }
    
    /**
    * Method used to reset the current level of the game.
    */
    public void reset(){
        if(!game.isFinished() && game.getLevelScore() != 0){
            game.reset();
            repaint();
        }
    }
    
    private void undo(){
        if(game.undo()){
            repaint();
        }
    }
    
    private void redo(){
        if(game.redo()){
            repaint();
        }
    }
    
    private void repaint(){
        gui.repaint(game.getBoardToString());
        if(game.isFinished()){
            gui.win();
        }
    }
    
    private void show(){
        gui.show(game.getBoardToString());
    }
}
