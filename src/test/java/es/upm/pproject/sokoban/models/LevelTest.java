package es.upm.pproject.sokoban.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.models.utils.Coordinates;

@DisplayName("Class to test hte level")
class LevelTest {
    private Level level;
    
    @BeforeEach
    void init() throws IOException{
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
    @DisplayName("Test checkStatus")
    void checkStatusTest(){
        assertEquals(false, level.checkStatus());
    }
}
