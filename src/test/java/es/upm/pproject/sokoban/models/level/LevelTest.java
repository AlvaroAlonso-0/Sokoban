package es.upm.pproject.sokoban.models.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.models.props.Box;
import es.upm.pproject.sokoban.models.props.Player;
import es.upm.pproject.sokoban.models.utils.Coordinates;

@DisplayName("Class to test the level")
class LevelTest {
    private Level level;
    private final String levelTwoBoxes = "src/main/resources/levels4Testing/levelTwoBoxes.txt";
    private final String levelTwoBoxesMove = "src/main/resources/levels4Testing/levelTwoBoxesMove.txt";
    
    @Nested
    @DisplayName("Empty constructor test")
    class emptyConstructor{
        private Level level;
        
        @BeforeEach
        void init(){
            level = new Level();
        }
        
        @Test
        @DisplayName("Player getter and setter")
        void playerGS(){
            Player pl = new Player(1, 2);
            level.setPlayer(pl);
            assertEquals(pl, level.getPlayer());
        }
        
        @Test
        @DisplayName("Tiles getter and setter")
        void tilesGS(){
            Tile[][] board = new Tile[2][2];
            board[0][0] = Tile.WALL;
            board[0][1] = Tile.GOAL;
            board[1][0] = null;
            board[1][1] = Tile.WALL;
            level.setBoard(board);
            assertEquals(board, level.getBoard());
        }
        
        @Test
        @DisplayName("Boxes getter and setter")
        void boxesGS(){
            List<Box> boxes = new ArrayList<>();
            boxes.add(new Box());
            level.setBoxList(boxes);
            assertEquals(boxes, level.getBoxList());
        }
        
        @Test
        @DisplayName("Level name getter and setter")
        void levelNameGS(){
            level.setName("Test");
            assertEquals("Test", level.getName());
        }
        
        @Test
        @DisplayName("Moves getter and setter")
        void movesGS(){
            Deque<Character> moves = new ArrayDeque<>();
            moves.add('C');
            level.setMovements(moves);
            assertEquals(moves, level.getMovements());
        }

        @Test
        @DisplayName("Undone moves getter and setter")
        void undoneMovesGS(){
            Deque<Character> undoneMovements = new ArrayDeque<>();
            undoneMovements.add('C');
            level.setUndoneMovements(undoneMovements);
            assertEquals(undoneMovements, level.getUndoneMovements());
        }

        @Test
        @DisplayName("Score getter and setter")
        void scoreGS(){
            int score = 5;
            level.setScore(score);
            assertEquals(score, level.getScore());
        }
        
        
    }
    
    @BeforeEach
    void init() throws IOException, WrongLevelFormatException {
        level = new Level("src/main/resources/levels/level_1.txt");
    }
    
    @Test
    @DisplayName("Test the level constructor")
    void constTest(){
        assertEquals("Level One", level.getName());
        assertEquals(4, level.getPlayerCoords().getX());
        assertEquals(2, level.getPlayerCoords().getY());
        List<Coordinates> boxCoords = level.getBoxListCoordinates();
        assertEquals(4, boxCoords.get(0).getX());
        assertEquals(5, boxCoords.get(0).getY());
        assertEquals(Tile.WALL, level.getTile(0,0));
        assertEquals(Tile.GOAL, level.getTile(4,3));
        assertEquals(null, level.getTile(1, 2));
        assertEquals(0,level.getScore());
    }
    
    @Test
    @DisplayName("Test the level constructor with non existent file")
    void constTest2() {
        assertThrows(FileNotFoundException.class, () -> {
            new Level("src/main/resources/levels4Testing/level_not_existent.txt");
        });
    }
    
    @Test
    @DisplayName("Test the level constructor with a wrong file")
    void constTestWrongFile(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("src/main/resources/levels4Testing/level0empty.txt");
        });
        
        assertEquals("Error reading the file", e.getMessage());
    }
    
    @Test
    @DisplayName("Test the level constructor without any player")
    void constTestWrongFileFormatPlayer(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("src/main/resources/levels4Testing/level0wrongPlayer.txt");
        });
        
        assertEquals("The level must contain a player", e.getMessage());
    }
    
    @Test
    @DisplayName("Test the level constructor without any box")
    void constTestWrongFileFormatBox(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("src/main/resources/levels4Testing/level0wrongBox.txt");
        });
        
        assertEquals("The level must contain at least one box", e.getMessage());
    }
    
    @Test
    @DisplayName("Test the level constructor with different number of goals and boxes")
    void constTestWrongFileFormatGoalsBoxes(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("src/main/resources/levels4Testing/level0wrongGoal.txt");
        });
        
        assertEquals("The number of goals must be equal to the number of boxes", e.getMessage());
    }
    
    
    @Test
    @DisplayName("Test toString")
    void toStringTest(){
        StringBuilder sb = new StringBuilder();
        sb.append("++++    \n")
        .append("+  +    \n")
        .append("+  +++++\n")
        .append("+      +\n")
        .append("++W*+# +\n")
        .append("+   +  +\n")
        .append("+   ++++\n")
        .append("+++++   ");
        assertEquals(sb.toString(), level.toString());
    }

    @Test
    @DisplayName("Test toString with waregouse on goal")
    void toStringTest2(){
        StringBuilder sb = new StringBuilder();
        sb.append("++++    \n")
        .append("+  +    \n")
        .append("+  +++++\n")
        .append("+      +\n")
        .append("++ X+# +\n")
        .append("+   +  +\n")
        .append("+   ++++\n")
        .append("+++++   ");
        level.getPlayer().setCurrentPositionX(4);
        level.getPlayer().setCurrentPositionY(3);
        assertEquals(sb.toString(), level.toString());
    }
    
    @Test
    @DisplayName("Test reset")
    void testReset(){
        StringBuilder sb = new StringBuilder();
        sb.append("++++    \n")
        .append("+  +    \n")
        .append("+  +++++\n")
        .append("+      +\n")
        .append("++W*+# +\n")
        .append("+   +  +\n")
        .append("+   ++++\n")
        .append("+++++   ");
        level.getPlayer().setCurrentPositionX(3);
        level.getPlayer().setCurrentPositionX(5);
        level.getBoxList().get(0).setCurrentPositionX(1);
        level.getBoxList().get(0).setCurrentPositionY(1);
        level.reset();
        assertEquals(sb.toString(), level.toString());
        assertEquals(0, level.getScore());
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Move player test")
    class MovePlayerTest{
        Level lvl;
        String defaultLevel, oneStep, twoSteps, threeSteps, prevBoxMove,
        boxMove;
        
        @BeforeAll
        void init(){
            defaultLevel = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++W*+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            oneStep = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+# +\n")
            .append("+ W +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            twoSteps = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+# +\n")
            .append("+  W+  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            threeSteps = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ X+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            prevBoxMove = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+    W +\n")
            .append("++ *+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            boxMove = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+W +\n")
            .append("+   +# +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
        }
        
        @BeforeEach
        void restart() throws IOException, WrongLevelFormatException{
            lvl = new Level("src/main/resources/levels/level_1.txt");
        }
        
        @Test
        @DisplayName("Testing unhandled directions")
        void someUnhandledDirections(){
            assertFalse(lvl.movePlayer('l'));
            assertFalse(lvl.movePlayer('q'));
            assertFalse(lvl.movePlayer('O'));
            assertEquals(defaultLevel, lvl.toString());
        }
        
        @Test
        @DisplayName("Testing simple moves without touching boxes")
        void movesWithoutBoxes(){
            boolean hasMoved = lvl.movePlayer('D');
            assertEquals(oneStep, lvl.toString());
            hasMoved = hasMoved && lvl.movePlayer('R');
            assertEquals(twoSteps, lvl.toString());
            hasMoved = hasMoved && lvl.movePlayer('u');
            assertEquals(threeSteps, lvl.toString());
            assertTrue(hasMoved && lvl.movePlayer('l'));
            assertEquals(defaultLevel, lvl.toString());
        }
        
        @Test
        @DisplayName("Testing simple moving against wall")
        void movesAgainstWall(){
            assertFalse(lvl.movePlayer('l'));
            lvl.movePlayer('D');
            lvl.movePlayer('R');
            assertFalse(lvl.movePlayer('r'));
            lvl.movePlayer('u');
            lvl.movePlayer('l');
            assertEquals(defaultLevel, lvl.toString());
            
        }
        
        @Test
        @DisplayName("Testing moves with boxes")
        void movingBoxes(){
            lvl.movePlayer('u');
            lvl.movePlayer('r');
            lvl.movePlayer('r');
            lvl.movePlayer('r');
            assertEquals(prevBoxMove, lvl.toString());
            assertTrue(lvl.movePlayer('d'),lvl.toString());
            assertEquals(boxMove, lvl.toString());
            assertFalse(lvl.movePlayer('D'));
            assertEquals(boxMove, lvl.toString());
        }
        
        @Test
        @DisplayName("Testing moving with two boxes")
        void movingTwoBoxes() throws FileNotFoundException, WrongLevelFormatException{
            lvl = new Level(levelTwoBoxesMove);
            assertTrue(lvl.movePlayer('r'));
            assertFalse(lvl.movePlayer('r'));
        }
    }
    
    @Test
    @DisplayName("Test checkStatus")
    void checkStatusTest(){
        assertEquals(false, level.checkStatus());
        
        level.movePlayer('u');
        level.movePlayer('r');
        level.movePlayer('r');
        level.movePlayer('r');
        level.movePlayer('r');
        level.movePlayer('d');
        level.movePlayer('d');
        level.movePlayer('l');
        level.movePlayer('u');
        level.movePlayer('r');
        level.movePlayer('u');
        level.movePlayer('l');
        level.movePlayer('l');
        level.movePlayer('l');
        level.movePlayer('d');
        level.movePlayer('l');
        level.movePlayer('u');
        level.movePlayer('l');
        level.movePlayer('u');
        level.movePlayer('u');
        level.movePlayer('r');
        level.movePlayer('d');
        level.movePlayer('d');
        level.movePlayer('d');
        level.movePlayer('r');
        level.movePlayer('d');
        level.movePlayer('d');
        level.movePlayer('l');
        level.movePlayer('l');
        level.movePlayer('u');
        level.movePlayer('r');
        level.movePlayer('d');
        level.movePlayer('r');
        level.movePlayer('u');
        
        assertEquals(true, level.checkStatus());
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Undo method test")
    class UndoTest{
        Level lvl;
        String defaultLevel, oneStep, twoSteps, threeSteps, prevBoxMove,
        boxMove;
        
        @BeforeAll
        void init(){
            defaultLevel = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++W*+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            oneStep = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+# +\n")
            .append("+ W +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            twoSteps = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+# +\n")
            .append("+  W+  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            threeSteps = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ X+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            prevBoxMove = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+    W +\n")
            .append("++ *+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            boxMove = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+W +\n")
            .append("+   +# +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
        }
        
        @BeforeEach
        void restart() throws IOException, WrongLevelFormatException{
            lvl = new Level("src/main/resources/levels/level_1.txt");
        }
        
        @Test
        @DisplayName("Undo with one step")
        void firstMove(){
            assertFalse(lvl.undoMove());
            assertTrue(lvl.movePlayer('d'));
            assertEquals(oneStep, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(defaultLevel, lvl.toString());
            assertFalse(lvl.undoMove());
        }
        
        @Test
        @DisplayName("Undo with multiple steps")
        void multipleMoves(){
            lvl.movePlayer('d');
            lvl.movePlayer('r');
            lvl.movePlayer('u');
            lvl.movePlayer('l');
            assertEquals(defaultLevel, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(threeSteps, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(twoSteps, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(oneStep, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(defaultLevel, lvl.toString());
            assertFalse(lvl.undoMove());
        }
        
        @Test
        @DisplayName("Undo with box moving")
        void boxMoving(){
            char[] moves = {'u', 'r', 'r', 'r'};
            for (char c : moves) {
                lvl.movePlayer(c);
            }
            assertEquals(prevBoxMove, lvl.toString());
            assertTrue(lvl.movePlayer('d'));
            assertEquals(boxMove, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(prevBoxMove, lvl.toString());
            for(int i = 0; i < 4; i++){
                assertTrue(lvl.undoMove());
            }
            assertFalse(lvl.undoMove());
            
        }
        
        @Test
        @DisplayName("Undo move to put box on goal")
        void boxToGoalUndo() throws IOException, WrongLevelFormatException{
            lvl = new Level(levelTwoBoxes);
            assertTrue(lvl.movePlayer('r'));
            assertTrue(lvl.movePlayer('d'));
            assertTrue(lvl.movePlayer('r'));
            assertTrue(lvl.movePlayer('u'));
            assertTrue(lvl.undoMove());
        }
        
        @Test
        @DisplayName("Undo with multiple boxes moved level")
        void multipleBoxesMoved() throws IOException, WrongLevelFormatException{
            lvl = new Level(levelTwoBoxes);
            String original = lvl.toString();
            lvl.movePlayer('r');
            lvl.movePlayer('l');
            lvl.movePlayer('l');
            lvl.movePlayer('l');
            for(int i = 0; i < 4; i++){
                assertTrue(lvl.undoMove());
            }
            assertFalse(lvl.undoMove());
            assertEquals(original, lvl.toString());
        }
    }
    
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @DisplayName("Redo method test")
    class RedoTest{
        Level lvl;
        String defaultLevel, oneStep, twoSteps, threeSteps, prevBoxMove,
        boxMove;
        
        @BeforeAll
        void init(){
            defaultLevel = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++W*+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            oneStep = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+# +\n")
            .append("+ W +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            twoSteps = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+# +\n")
            .append("+  W+  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            threeSteps = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ X+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            prevBoxMove = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+    W +\n")
            .append("++ *+# +\n")
            .append("+   +  +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
            boxMove = (new StringBuilder()).append("++++    \n")
            .append("+  +    \n")
            .append("+  +++++\n")
            .append("+      +\n")
            .append("++ *+W +\n")
            .append("+   +# +\n")
            .append("+   ++++\n")
            .append("+++++   ")
            .toString();
        }
        
        @BeforeEach
        void restart() throws IOException, WrongLevelFormatException{
            lvl = new Level("src/main/resources/levels/level_1.txt");
        }
        
        @Test
        @DisplayName("Redo with one step")
        void firstMove(){
            assertFalse(lvl.redoMove());
            lvl.movePlayer('d');
            assertTrue(lvl.undoMove() && lvl.redoMove());
            assertEquals(oneStep, lvl.toString());
        }
        
        @Test
        @DisplayName("Redo with multiple steps")
        void multipleMoves(){
            for (char dir : new char[]{'d','r','u','l'}) {
                lvl.movePlayer(dir);
            }
            assertEquals(defaultLevel, lvl.toString());
            for(int i = 0; i < 4;i++){
                lvl.undoMove();
            }
            assertEquals(defaultLevel, lvl.toString());
            assertTrue(lvl.redoMove());
            assertEquals(oneStep, lvl.toString());
            assertTrue(lvl.redoMove() && lvl.redoMove() && lvl.redoMove());
            assertEquals(defaultLevel, lvl.toString());
            assertFalse(lvl.redoMove());
        }
        
        @Test
        @DisplayName("Redo with box moving")
        void boxMoving(){
            char[] moves = {'u', 'r', 'r', 'r','d'};
            for (char c : moves) {
                lvl.movePlayer(c);
            }
            assertEquals(boxMove, lvl.toString());
            assertTrue(lvl.undoMove());
            assertEquals(prevBoxMove, lvl.toString());
            assertTrue(lvl.redoMove());
            assertEquals(boxMove, lvl.toString());
        }
        
        @Test
        @DisplayName("Redo with multiple boxes level")
        void multipleBoxes() throws IOException, WrongLevelFormatException{
            lvl = new Level(levelTwoBoxes);
            lvl.movePlayer('r');
            lvl.movePlayer('l');
            lvl.movePlayer('l');
            lvl.movePlayer('l');
            String moved = lvl.toString();
            for(int i = 0; i < 4;i++){
                lvl.undoMove();
            }
            for(int i = 0; i < 4;i++){
                lvl.redoMove();
            }
            assertEquals(moved, lvl.toString());
        }
    }
    
    @Nested
    @DisplayName("Score test")
    class ScoreTest{
        @Test
        @DisplayName("Good move score test")
        void upScoreTest(){
            level.movePlayer('u');
            level.movePlayer('r');
            level.movePlayer('r');
            assertEquals(3, level.getScore());
        }
        
        @Test
        @DisplayName("Bad move score test")
        void noScoreTest(){
            level.movePlayer('u');
            assertEquals(1, level.getScore());
            level.movePlayer('w');
            assertEquals(1, level.getScore());
        }
        
        @Test
        @DisplayName("Test undo and score")
        void undoScore(){
            level.movePlayer('u');
            level.movePlayer('r');
            level.movePlayer('r');
            level.undoMove();
            assertEquals(2, level.getScore());
        }
        
        @Test
        @DisplayName("Test score with all moves undone plus one")
        void allMovesUndo(){
            level.movePlayer('u');
            level.undoMove();
            level.undoMove();
            assertEquals(0, level.getScore());
        }
        
        
    }
}
