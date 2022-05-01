package es.upm.pproject.sokoban.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.models.utils.Coordinates;

@DisplayName("Class to test hte level")
class LevelTest {
    private Level level;
    
    @BeforeEach
    void init() throws IOException, WrongLevelFormatException {
        level = new Level("resources/level_1.txt");
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
    }
    
    @Test
    @DisplayName("Test the level constructor with a wrong file")
    void constTestWrongFile(){
        assertThrows(IOException.class, () -> {
            new Level("resources/level0empty.txt");
        });
    }

    @Test
    @DisplayName("Test the level constructor without any player")
    void constTestWrongFileFormatPlayer(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("resources/level0wrongPlayer.txt");
        });

        assertEquals("The level must contain a player", e.getMessage());
    }

    @Test
    @DisplayName("Test the level constructor without any box")
    void constTestWrongFileFormatBox(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("resources/level0wrongBox.txt");
        });

        assertEquals("The level must contain at least one box", e.getMessage());
    }

    @Test
    @DisplayName("Test the level constructor with different number of goals and boxes")
    void constTestWrongFileFormatGoalsBoxes(){
        Exception e = assertThrows(WrongLevelFormatException.class, () -> {
            new Level("resources/level0wrongGoal.txt");
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
            .append("++ W+# +\n")
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
            lvl = new Level("resources/level_1.txt");
        }
        
        @Test
        @DisplayName("Testing unhandled directions")
        void someUnhandledDirections(){
            assertFalse(lvl.movePlayer('o'));
            assertEquals(defaultLevel, lvl.toString());
            assertFalse(lvl.movePlayer('q'));
            assertEquals(defaultLevel, lvl.toString());
            assertFalse(lvl.movePlayer('O'));
            assertEquals(defaultLevel, lvl.toString());
        }

        @Test
        @DisplayName("Testing simple moves without touching boxes")
        void movesWithoutBoxes(){
            assertFalse(lvl.movePlayer('l'));
            assertEquals(defaultLevel, lvl.toString());
            assertTrue(lvl.movePlayer('D'));
            assertEquals(oneStep, lvl.toString());
            assertTrue(lvl.movePlayer('R'));
            assertEquals(twoSteps, lvl.toString());
            assertFalse(lvl.movePlayer('r'));
            assertEquals(twoSteps, lvl.toString());
            assertTrue(lvl.movePlayer('u'));
            assertEquals(threeSteps, lvl.toString());
        }

        @Test
        @DisplayName("Testing moves with boxes")
        void movingBoxes(){
            assertTrue(lvl.movePlayer('u'));
            assertTrue(lvl.movePlayer('r'));
            assertTrue(lvl.movePlayer('r'));
            assertTrue(lvl.movePlayer('r'));
            assertEquals(prevBoxMove, lvl.toString());
            assertTrue(lvl.movePlayer('d'),lvl.toString());
            assertEquals(boxMove, lvl.toString());
            assertFalse(lvl.movePlayer('D'));
            assertEquals(boxMove, lvl.toString());
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
}
