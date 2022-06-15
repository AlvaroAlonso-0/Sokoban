package es.upm.pproject.sokoban.models;

import java.awt.Dimension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.view.GameStatusGUI;

@DisplayName("Class to test the games objects")
class GamesTest {
    private char[] movesLevelOne = {'u', 'r', 'r', 'r', 'r', 'd', 'd', 'l', 'u', 'r', 'u', 
    'l', 'l', 'l', 'd','l', 'u', 'l', 'u', 'u', 'r', 'd', 'd', 'd', 'r', 'd', 'd', 'l', 
    'l', 'u', 'r', 'd', 'r'}; //last move of level is 'u'

    private char[] movesLevelTwo = {'l', 'l', 'd', 'd', 'r', 'u', 'l', 'u', 'r', 'r', 
            'u', 'u', 'l', 'd', 'd', 'u', 'r', 'r', 'r', 'd', 'd', 'l', 'u', 'r', 'u', 
            'l', 'd', 'l', 'l', 'l', 'd', 'd', 'r', 'u', 'l', 'u', 'r', 'r', 'l', 'u', 
            'u', 'r', 'r', 'd', 'd', 'l', 'l', 'u', 'r'};

    private boolean movePlayer(Game game, char[] moves) throws WrongLevelFormatException{
        boolean hasMoved = true;
        for(char c : moves){
            hasMoved = hasMoved && game.movePlayer(c);
        }
        return hasMoved;
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Class to test the game object")
    class GameTest{
        private Game g;
        private String levelBoardFormat;
        private String levelTwo;
        private char[] movesLevelThree;

        @BeforeAll
        void init(){
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
            
            
            movesLevelThree = new char[]{'d', 'r', 'u', 'r', 'r', 'd', 'l', 'l', 'u', 'l', 
            'u', 'u', 'r', 'd', 'l', 'd', 'r', 'd', 'd', 'l', 'u', 'u', 'u', 'd', 'd', 'r', 'r', 
            'r', 'u', 'l', 'l', 'd', 'l', 'u'};
        }
        
        @BeforeEach
        void restart(){
            assertDoesNotThrow(() -> {g = new Game();});
        }

        @Test
        @DisplayName("Test loading first level")
        void load(){
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());
        }

        @Test
        @DisplayName("Move player test")
        void possibleMoves() throws WrongLevelFormatException{
            assertTrue(g.movePlayer('U'));
            assertTrue(g.movePlayer('r'));
            assertTrue(g.movePlayer('R'));
            assertTrue(g.movePlayer('r'));

        }

        @Test
        @DisplayName("Check that the game cant do impossible moves")
        void impossibleMoves() throws WrongLevelFormatException{
            assertFalse(g.movePlayer('o'));
            assertFalse(g.movePlayer('l'));
            movePlayer(g, new char[]{'U', 'r', 'R', 'r', 'r'});
            assertFalse(g.movePlayer('r'));
            assertFalse(g.movePlayer('R'));

        }

        @Test
        @DisplayName("Test the game object playing and solving the first level")
        void changeLevel() throws WrongLevelFormatException{
            assertTrue(movePlayer(g, new char[]{'u', 'r', 'R', 'r', 'r', 'd', 'D', 'l', 'U', 'r'}));
            assertTrue(movePlayer(g, new char[]{'u', 'l', 'l', 'l', 'd', 'l', 'u', 'l', 'u', 'u',
            'r', 'd', 'd', 'd', 'r', 'd', 'd', 'l', 'l', 'u','r', 'd', 'r'}));
            assertEquals(String.format(levelBoardFormat, ' ',' ','#','W'), g.toString());
            assertTrue(g.movePlayer('u'));
            assertEquals(String.format(levelTwo), g.toString());
            assertEquals(34, g.score);
        }
        
        @Test
        @DisplayName("Undo method test")
        void undoTest() throws WrongLevelFormatException{
            assertFalse(g.undo());
            movePlayer(g, movesLevelOne);
            assertTrue(g.undo());
            g.movePlayer('r');
            assertEquals(String.format(levelBoardFormat, ' ', ' ', '#', 'W'), g.toString());
            g.movePlayer('u');
            assertFalse(g.undo());
        }

        @Test
        @DisplayName("Redo method test")
        void redoTest() throws WrongLevelFormatException{
            assertFalse(g.redo());
            movePlayer(g, movesLevelOne);
            assertFalse(g.redo());
            g.undo();
            assertTrue(g.redo());
            assertEquals(String.format(levelBoardFormat, ' ', ' ', '#', 'W'), g.toString());
            g.movePlayer('u');
            assertFalse(g.redo());
        }
        
        @Test
        @DisplayName("Test reset")
        void testReset() throws WrongLevelFormatException{
            g.movePlayer('u');
            g.movePlayer('r');
            g.movePlayer('r');
            g.movePlayer('u');
            g.reset();
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());
            assertEquals(0, g.score);
        }

        @Test
        @DisplayName("New game method test")
        void newGameTest() throws WrongLevelFormatException{
            movePlayer(g, movesLevelOne);
            g.movePlayer('u');
            assertEquals(String.format(levelTwo), g.toString());
            g.newGame();
            assertEquals(String.format(levelBoardFormat, 'W','#',' ',' '), g.toString());
            assertEquals(0, g.score);

        }

        @Test
        @DisplayName("Game finished and its blocking capabilities")
        void finished() throws WrongLevelFormatException{
            assertTrue(movePlayer(g, movesLevelOne) && g.movePlayer('u') &&
             movePlayer(g, movesLevelTwo) && movePlayer(g, movesLevelThree));
            assertTrue(g.isFinished());
            assertFalse(g.movePlayer('u'));
            assertFalse(g.undo());
            assertFalse(g.redo());
        }

        @Test
        @DisplayName("Test has been modified")
        void hasBeenModified() throws WrongLevelFormatException{
            assertFalse(g.hasBeenModified);
            assertFalse(!g.movePlayer('l') && g.hasBeenModified);
            assertTrue(g.movePlayer('u') && g.hasBeenModified);
            g.reset();
            assertFalse(g.hasBeenModified);
            movePlayer(g, movesLevelOne);
            g.movePlayer('u');
            g.movePlayer('l');
            g.reset();
            assertTrue(g.hasBeenModified);
        }

        @Test
        @DisplayName("Test has been modified for undo and redo")
        void hasBeenModifiedForced() throws WrongLevelFormatException{
            g.movePlayer('u');
            assertTrue(g.undo() && g.hasBeenModified);
            assertTrue(g.redo() && g.hasBeenModified);
            g.movePlayer('r');
            g.undo();
            g.gameSaved();
            assertTrue(g.redo() && g.hasBeenModified);
            g.gameSaved();
            assertTrue(g.undo() && g.hasBeenModified);
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

        @Test
        @DisplayName("Testing game score getter")
        void gameScoreTest() throws WrongLevelFormatException{
            g.movePlayer('d');
            g.movePlayer('d');
            assertEquals(2, g.getTotalScore());
            g.undo();
            assertEquals(1, g.getTotalScore());
            g.redo();
            assertEquals(2, g.getTotalScore());
            g.reset();
            assertEquals(0, g.getTotalScore());
        }

        @Test
        @DisplayName("Testing level name getter")
        void levelScoreTest() throws WrongLevelFormatException{
            assertEquals("Level One", g.getLevelName());
            movePlayer(g, movesLevelOne);
            g.movePlayer('u');
            assertEquals("Level Two", g.getLevelName());
        }

        @Test
        @DisplayName("Testing level name getter")
        void hasbeenModifiedTest() {
            assertFalse(g.hasBeenModified());
            g.hasBeenModified = true;
            assertTrue(g.hasBeenModified());
        }

        @Test
        @DisplayName("Testing the dimension of the level")
        void getDimension() throws WrongLevelFormatException {
            assertEquals(new Dimension(8, 8), g.getDimension());
            movePlayer(g, movesLevelOne);
            g.movePlayer('U');
            movePlayer(g, movesLevelTwo);
            assertEquals(new Dimension(6, 7), g.getDimension());
        }
    }
}
