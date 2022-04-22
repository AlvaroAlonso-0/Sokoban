package es.upm.pproject.sokoban.models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.interfaces.Coordinates;

@DisplayName("Class to test the Coordinates")
class CoordinatesTest {
    private Coordinates coords;
    
    @BeforeEach
    void init(){
        coords = new CoordinatesImp(5,9);
    }

    @Test
    @DisplayName("Tests the Coordinates constructor")
    void constTest(){
        assertEquals(5, coords.getX());
        assertEquals(9, coords.getY());
    }

}
