package es.upm.pproject.sokoban.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.interfaces.Level;

@DisplayName("Class to test hte level")
class LevelTest {
    private Level level;
    
    @BeforeEach
    void init() throws IOException{
        level = new LevelImp("resources/level1.txt");
    }

    @Test
    @DisplayName("Test the level constructor")
    void constTest(){
        assertEquals(4, level.getPlayer().currentPos().getX());
        assertEquals(2, level.getPlayer().currentPos().getY());
        assertEquals(4, level.getBox(0).currentPos().getX());
        assertEquals(5, level.getBox(0).currentPos().getY());
        assertEquals(Tile.WALL, level.getTile(0,0));
        assertEquals(Tile.GOAL, level.getTile(4,3));
        assertEquals(null, level.getTile(1, 2));
    }

    @Test
    @DisplayName("Test the level constructor with a wrong file")
    void constTestWrongFile(){
        assertThrows(IOException.class, () -> {
            new LevelImp("resources/level0empty.txt");
        });
    }

    @Test
    @DisplayName("Test checkStatus")
    void checkStatusTest(){
        assertEquals(false, level.checkStatus());
        level.getBox(0).setOnGoal(true);
        assertEquals(true, level.checkStatus());
    }
}
