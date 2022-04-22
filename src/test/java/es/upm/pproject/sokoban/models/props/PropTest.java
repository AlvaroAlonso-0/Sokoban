package es.upm.pproject.sokoban.models.props;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.upm.pproject.sokoban.interfaces.Prop;

@DisplayName("Class to test the Props")
class PropTest {
    private Prop prop;
    
    @DisplayName("Tests the Prop")
    @BeforeEach
    void init(){
        prop = new PropImp(3,2);
    }
    
    @DisplayName("Tests the Prop")
    @Test
    void constTest(){
        assertEquals(3,prop.currentPos().getX());
        assertEquals(2,prop.currentPos().getY());
    }

    @DisplayName("Tests the move method")
    @Test
    void moveTest(){
        prop.move('W');
        assertEquals(3,prop.currentPos().getY());
        prop.move('A');
        assertEquals(2,prop.currentPos().getX());
        prop.move('S');
        assertEquals(2,prop.currentPos().getY());
        prop.move('D');
        assertEquals(3,prop.currentPos().getX());
        prop.move('R');
        assertEquals(3,prop.currentPos().getX());
        assertEquals(2,prop.currentPos().getY());
    }

    @DisplayName("Test the reset method")
    @Test
    void resetTest() {
        prop.move('W');
        prop.move('D');
        prop.reset();
        assertEquals(3, prop.currentPos().getX());
        assertEquals(2, prop.currentPos().getY());
    }
}
