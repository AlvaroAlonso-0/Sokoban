package es.upm.pproject.sokoban.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.interfaces.Level;

@DisplayName("Class to test hte level")
public class LevelTest {
    private Level level;
    
    @BeforeEach
    void init(){
        level = new LevelImp("resources/level1.txt");
    }

    @Test
    @DisplayName("Test the level constructor")
    void constTest(){
        System.out.println("Algo");
        assert level.getTile(0,0).equals(Tile.WALL);
        assert level.getTile(4,3).equals(Tile.GOAL);
        assert level.getTile(1,2) == null;
    }

}
