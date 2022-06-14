package es.upm.pproject.sokoban.models.sgfactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.view.GameStatusGUI;

@DisplayName("Class to test the SaveGameFactory")
class SaveGameFactoryTest {
    private static SaveGameFactory sgFactory;
    @BeforeAll
    static void init(){
        assertDoesNotThrow(() -> {sgFactory = new SaveGameFactory();});
    }

    @Test
    @DisplayName("Test loading a level with a name that missmatch any of the existent saved games")
    void saveWrongNameTest() throws WrongLevelFormatException{
        assertFalse(sgFactory.saveGame(new GameStatusGUI(),""));
    }

    @Test
    @DisplayName("Save a game test")
    void saveGameTest() throws WrongLevelFormatException{
        GameStatusGUI game = new GameStatusGUI();
        game.movePlayer('u');
        game.movePlayer('r');
        assertTrue(sgFactory.saveGame(game, "src/main/resources/xml4testing/Test 1.xml"));
    }
    
    @Test
    @DisplayName("Test loading a level with a name that missmatch any of the existent saved games")
    void loadNoLevelTest() throws WrongLevelFormatException{
        assertEquals(null, sgFactory.loadGame("src/main/resources/xml4testing/wrongGame"));
    }

    @Test
    @DisplayName("Test if the game loaded is the same as its current version but arent the same object")
    void loadGameTest() throws WrongLevelFormatException{
        String name = "src/main/resources/xml4testing/Test 2.xml";
        GameStatusGUI game = new GameStatusGUI();
        game.movePlayer('u');
        game.movePlayer('r');
        sgFactory.saveGame(game, name);
        GameStatusGUI gameLoaded = sgFactory.loadGame(name);
        assertNotEquals(null, gameLoaded);
        assertEquals(game.toString(), gameLoaded.toString());
        game.undo();
        gameLoaded.undo();
        assertEquals(game.toString(), gameLoaded.toString());
        game.redo();
        gameLoaded.redo();
        assertEquals(game.toString(), gameLoaded.toString());
        game.movePlayer('r');
        assertNotEquals(game.toString(), gameLoaded.toString());
    }
}
