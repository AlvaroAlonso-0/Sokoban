package es.upm.pproject.sokoban.models.props;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class to test the Box")
class BoxTest {
    private Box box;
    
    @BeforeEach
    void init(){
        box = new Box(7,1);
    }

    @Test
    @DisplayName("Tests the Box")
    void constTest(){
        assertEquals(7,box.currentPos().getX());
        assertEquals(1,box.currentPos().getY());
        assertEquals(false, box.isOnGoal());
    }

    @Test
    @DisplayName("Tests the setOnGoal method")
    void setOnGoalTest(){
        box.setOnGoal(true);
        assertEquals(true, box.isOnGoal());
    }

    @Test
    @DisplayName("Tests the isOnGoal method")
    void isOnGoalTest(){
        assertEquals(false, box.isOnGoal());
    }

    @DisplayName("Tests the move method")
    @Test
    void moveTest(){
        box.move('L');
        assertEquals(0,box.currentPos().getY());
        box.move('R');
        assertEquals(1,box.currentPos().getY());
        box.move('U');
        assertEquals(6,box.currentPos().getX());
        box.move('D');
        assertEquals(7,box.currentPos().getX());
        box.move('P');
        assertEquals(7,box.currentPos().getX());
        assertEquals(1,box.currentPos().getY());
    }

    @DisplayName("Test the reset method")
    @Test
    void resetTest() {
        box.move('L');
        box.move('D');
        box.reset();
        assertEquals(7, box.currentPos().getX());
        assertEquals(1, box.currentPos().getY());
    }
}
