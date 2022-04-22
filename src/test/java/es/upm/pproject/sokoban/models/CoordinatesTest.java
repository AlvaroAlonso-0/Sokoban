package es.upm.pproject.sokoban.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.interfaces.Coordinates;

@DisplayName("Class to test the Coordinates")
public class CoordinatesTest {
    private Coordinates coords;
    
    @BeforeEach
    void init(){
        coords = new CoordinatesImp(5,9);
    }

    @Test
    @DisplayName("Tests the Coordinates constructor")
    void constTest(){
        assert coords.getX() == 5;
        assert coords.getY() == 9;
    }

}
