package es.upm.pproject.sokoban.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.view.GUI;
import es.upm.pproject.sokoban.view.GameStatusGUI;

/**
* Class for the Controller of the application.
* @author Rafael Alonso Sirera
* @author Raul Casamayor Navas
* @version 1.3
* @since 12/06/2022
*/
public class Controller{

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final Marker loadMarker = MarkerFactory.getMarker("FATAL");
    private static final Marker loadGameMarker = MarkerFactory.getMarker("LOAD-GAME");
    private static final Marker saveGameMarker = MarkerFactory.getMarker("SAVE-GAME");

    private static final String SAVED_GAMES_FORMAT = "games/%s.xml";

    private GUI gui;
    private GameStatusGUI game;
    public Controller(){
        gui = new GUI(this);
        try {
            game = new GameStatusGUI();
            gui.show(game.getBoardToString());
        } catch (WrongLevelFormatException e) {
            logger.error(loadMarker, "First level couldnt be loaded", e);
            //TODO implement GUI error message method
        }
    }

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
        try{
            if(game.movePlayer(dir)){
                repaint();
            }
        }
        catch(WrongLevelFormatException e){
            logger.error(loadMarker, "Next level couldnt be loaded", e);
            //TODO implement GUI error message method
        }
    }

    /**
     * Method used to save the current status of the game into a file.
     * @param name Name for the game
     * @return If the game could be saved
     */
    public boolean saveGame(String name){
        try{
            JAXBContext context = JAXBContext.newInstance(GameStatusGUI.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(game, new FileWriter(String.format(SAVED_GAMES_FORMAT, name)));
        }catch(JAXBException | IOException e){
            logger.warn(saveGameMarker, "Current game couldnt be saved", e);
            return false;
        }
        String logMsg = String.format( "Current game has been saved as %s.xml", name);
        logger.info(saveGameMarker, logMsg);
        return true;
    }

    /**
     * Method used to load a saved game.
     * @param name Name of the game to load
     * @return If the game could be loaded
     */
    public boolean loadGame(String name){
        String logMsg;
        try{
            JAXBContext context = JAXBContext.newInstance(GameStatusGUI.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            game = (GameStatusGUI) unmarshaller.unmarshal(new File(String.format(SAVED_GAMES_FORMAT, name)));
        }catch(JAXBException e){
            logMsg = String.format( "Was imposible to load %s game ", name);
            logger.warn(loadGameMarker, logMsg, e);
            return false;
        }
        logMsg = String.format( "%s game has been loaded", name);
        logger.info(loadGameMarker, logMsg);
        repaint();
        return true;
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

    public void reset(){
        game.reset();
        gui.repaint(game.getBoardToString());
    }

    private void repaint(){
        gui.repaint(game.getBoardToString());
    }
}
