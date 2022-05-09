package es.upm.pproject.sokoban.game;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;

@DisplayName("Class to test the games objects")
class GamesTest {
    
    @Nested
    @DisplayName("Class to test the game object")
    class GameTest{
        private Game g;
        
        @BeforeEach
        void init(){
            assertDoesNotThrow(() -> {g = new Game();});
        }
        
        @Test
        @DisplayName("Test the game object playing and solving with just one game")
        void playGameTest() throws WrongLevelFormatException{  
            String levelBoardFormat = (new StringBuilder("Level 1\n"))
            .append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++%c*+%c +\n")
            .append("+  %c+  +\n")
            .append("+  %c++++\n")
            .append("+++++   ")
            .toString();
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());
            assertFalse(g.isFinished());
            assertFalse(g.movePlayer('o'));
            assertFalse(g.movePlayer('l'));
            assertTrue(g.movePlayer('U'));
            assertTrue(g.movePlayer('r'));
            assertTrue(g.movePlayer('R'));
            assertTrue(g.movePlayer('r'));
            assertTrue(g.movePlayer('r'));
            assertFalse(g.movePlayer('r'));
            assertFalse(g.movePlayer('R'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('D'));
            assertFalse(g.movePlayer('d'));
            assertTrue(g.movePlayer('l'));
            assertFalse(g.movePlayer('L'));
            assertTrue(g.movePlayer('u'));
            assertTrue(g.movePlayer('r'));
            assertFalse(g.isFinished());
            assertTrue(g.movePlayer('u'));
            assertTrue(g.movePlayer('l'));
            assertTrue(g.movePlayer('l'));
            assertTrue(g.movePlayer('l'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('l'));
            assertTrue(g.movePlayer('u'));
            assertTrue(g.movePlayer('l'));
            assertTrue(g.movePlayer('u'));
            assertTrue(g.movePlayer('U'));
            assertTrue(g.movePlayer('r'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('R'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('D'));
            assertTrue(g.movePlayer('l'));
            assertTrue(g.movePlayer('L'));
            assertTrue(g.movePlayer('U'));
            assertTrue(g.movePlayer('r'));
            assertTrue(g.movePlayer('d'));
            assertTrue(g.movePlayer('r'));
            assertEquals(String.format(levelBoardFormat, ' ',' ','#','W'), g.toString());
            assertTrue(g.movePlayer('u'));
            assertTrue(g.isFinished());
            assertFalse(g.movePlayer('d'));
        }
    }
    
    @Nested
    @DisplayName("Class to test the GameStatusGUI object")
    class GameStatusGUITest{
        private GameStatusGUI g;
        
        @BeforeEach
        void init(){
            assertDoesNotThrow(() -> {g = new GameStatusGUI();});
        }

        @Test
        @DisplayName("Testing string board getter")
        void getBoardToStringTest(){
            String levelBoard = (new StringBuilder("++++    \n"))
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++W*+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            assertEquals(levelBoard, g.getBoardToString());
        }
    }
}
