package es.upm.pproject.sokoban.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.view.GUI;
import es.upm.pproject.sokoban.view.GameStatusGUI;

/**
* Class for the Controller of the application.
* @author Rafael Alonso Sirera
* @author Raul Casamayor Navas
* @version 1.1
* @since 23/05/2022
*/
public class Controller{
    private static final String SAVED_GAMES_FORMAT = "games/%s.xml";

    private GUI gui;
    private GameStatusGUI game;
    public Controller(){
        gui = new GUI(this);
        try {
            game = new GameStatusGUI();
            gui.show(game.getBoardToString());
        } catch (WrongLevelFormatException e) {
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
            default: dir='E';  break;
        }
        try{
            if(game.movePlayer(dir)){
                repaint();
            }
        }
        catch(WrongLevelFormatException e){
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
            return false;
        }
        return true;
    }

    /**
     * Method used to load a saved game.
     * @param name Name of the game to load
     * @return If the game could be loaded
     */
    public boolean loadGame(String name){
        try{
            JAXBContext context = JAXBContext.newInstance(GameStatusGUI.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            game = (GameStatusGUI) unmarshaller.unmarshal(new File(String.format(SAVED_GAMES_FORMAT, name)));
        }catch(JAXBException e){
            return false;
        }
        repaint();
        return true;
    }

    private void undo(){
        if(game.undo()){
            repaint();
        }
    }

    private void repaint(){
        gui.repaint(game.getBoardToString());
    }
}
