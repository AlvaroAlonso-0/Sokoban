package es.upm.pproject.sokoban.models;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.view.GameStatusGUI;

@DisplayName("Class to test the games objects")
class GamesTest {
    
    @Nested
    @DisplayName("Class to test the game object")
    class GameTest{
        private Game g;
        private String levelBoardFormat;
        private String levelTwo;
        
        @BeforeEach
        void init(){
            assertDoesNotThrow(() -> {g = new Game();});
            levelBoardFormat = (new StringBuilder("Level 1%n"))
            .append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++%c*+%c +\n")
            .append("+  %c+  +\n")
            .append("+  %c++++\n")
            .append("+++++   ")
            .toString();

            levelTwo = (new StringBuilder("Level 2%n"))
            .append(" ++++++\n")
            .append(" +    +\n")
            .append("++# * +\n")
            .append("+  W  +\n")
            .append("+ #+* +\n")
            .append("+  ++++\n")
            .append("++++   ")
            .toString();
        }
        
        @Test
        @DisplayName("Test the game object playing and solving with just one game")
        void playGameTest() throws WrongLevelFormatException{  
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());
            assertFalse(g.isFinished());
            assertFalse(g.movePlayer('o') || g.movePlayer('l'));
            assertTrue(g.movePlayer('U') && g.movePlayer('r') && g.movePlayer('R') && g.movePlayer('r') && g.movePlayer('r'));
            assertFalse(g.movePlayer('r') || g.movePlayer('R'));
            assertTrue(g.movePlayer('d') && g.movePlayer('D'));
            assertFalse(g.movePlayer('d'));
            assertTrue(g.movePlayer('l'));
            assertFalse(g.movePlayer('L'));
            assertTrue(g.movePlayer('u') && g.movePlayer('r'));
            assertFalse(g.isFinished());
            assertTrue(g.movePlayer('u'));
            assertTrue(g.movePlayer('l') && g.movePlayer('l') && g.movePlayer('l'));
            assertTrue(g.movePlayer('d') && g.movePlayer('l'));
            assertTrue(g.movePlayer('u') && g.movePlayer('l'));
            assertTrue(g.movePlayer('u') && g.movePlayer('U'));
            assertTrue(g.movePlayer('r'));
            assertTrue(g.movePlayer('d') && g.movePlayer('d') && g.movePlayer('d'));
            assertTrue(g.movePlayer('R'));
            assertTrue(g.movePlayer('d') && g.movePlayer('D'));
            assertTrue(g.movePlayer('l') && g.movePlayer('L'));
            assertTrue(g.movePlayer('U') && g.movePlayer('r') && g.movePlayer('d') && g.movePlayer('r'));
            assertEquals(String.format(levelBoardFormat, ' ',' ','#','W'), g.toString());
            assertTrue(g.movePlayer('u'));
            assertEquals(String.format(levelTwo), g.toString());
        }

        @Test
        @DisplayName("Testing the score with multiple levels solved")
        void ScoreTwoLevels() throws WrongLevelFormatException{
            char[] moves = {'u', 'r', 'r', 'r', 'r', 'd', 'd', 'l', 'u', 'r',
            'u', 'l', 'l', 'l', 'd', 'l', 'u', 'l', 'u', 'u',
            'r', 'd', 'd', 'd', 'r', 'd', 'd', 'l', 'l', 'u',
            'r', 'd', 'r', 'u', 'l', 'l', 'l'};
            for(char c : moves){
                g.movePlayer(c);
            }
            assertEquals(36, g.getScore());
        } 
        
        @Test
        @DisplayName("Undo method test")
        void undoTest() throws WrongLevelFormatException{
            char[] moves = {'u', 'r', 'r', 'r', 'r', 'r', 'd', 'd',
            'l', 'u', 'r', 'u', 'l', 'l', 'l', 'd',
            'l', 'u', 'l', 'u', 'u', 'r', 'd', 'd',
            'd', 'r', 'd', 'd', 'l', 'l', 'u', 'r',
            'd', 'r'};
            for (char dir : moves) {
                g.movePlayer(dir);
            }
            assertEquals(33, g.getScore());
            assertTrue(g.undo());
            assertEquals(32, g.getScore());
            assertTrue(g.movePlayer('r'));
            assertEquals(String.format(levelBoardFormat, ' ', ' ', '#', 'W'), g.toString());
            g.movePlayer('u');
            assertFalse(g.undo());
        }
        
        @Test
        @DisplayName("Test reset")
        void testReset() throws WrongLevelFormatException{
            StringBuilder sb = new StringBuilder();
            sb.append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++W*+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ");
            g.movePlayer('u');
            g.movePlayer('r');
            g.movePlayer('r');
            g.movePlayer('u');
            g.reset();
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());
            assertEquals(0, g.getScore());
        }

        @Test
        @DisplayName("New game method test")
        void newGameTest() throws WrongLevelFormatException{
            char[] moves = {'u', 'r', 'r', 'r', 'r', 'r', 'd', 'd',
            'l', 'u', 'r', 'u', 'l', 'l', 'l', 'd',
            'l', 'u', 'l', 'u', 'u', 'r', 'd', 'd',
            'd', 'r', 'd', 'd', 'l', 'l', 'u', 'r',
            'd', 'r', 'u'};
            for (char dir : moves) {
                g.movePlayer(dir);
            }
            assertEquals(String.format(levelTwo), g.toString());
            g.newGame();
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());

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
