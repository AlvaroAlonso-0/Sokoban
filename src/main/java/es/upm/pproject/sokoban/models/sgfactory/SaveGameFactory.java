package es.upm.pproject.sokoban.models.sgfactory;

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

import es.upm.pproject.sokoban.view.GameStatusGUI;

public class SaveGameFactory {
    private static final Logger logger = LoggerFactory.getLogger(SaveGameFactory.class);
    private static final Marker loadGameMarker = MarkerFactory.getMarker("LOAD-GAME");
    private static final Marker saveGameMarker = MarkerFactory.getMarker("SAVE-GAME");

    private JAXBContext context;


    public SaveGameFactory() throws JAXBException{
        context = JAXBContext.newInstance(GameStatusGUI.class);
    }

    /**
     * Method used to save the current status of the game into a file.
     * @param name Name for the game
     * @return If the game could be saved
     */
    public boolean saveGame(GameStatusGUI game, String name){
        try{
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(game, new FileWriter(name));
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
    public GameStatusGUI loadGame(String name){
        String logMsg;
        GameStatusGUI game;
        try{
            Unmarshaller unmarshaller = context.createUnmarshaller();
            game = (GameStatusGUI) unmarshaller.unmarshal(new File(name));
        }catch(JAXBException e){
            logMsg = String.format( "Was imposible to load %s game ", name);
            logger.warn(loadGameMarker, logMsg, e);
            return null;
        }
        logMsg = String.format( "%s game has been loaded", name);
        logger.info(loadGameMarker, logMsg);
        return game;
    }
}
