package es.upm.pproject.sokoban.models.props;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class to test the Player")
public class BoxTest {
    private Box box;
    
    @BeforeEach
    void init(){
        box = new Box(7,1);
    }

    @Test
    @DisplayName("Tests the Box")
    void constTest(){
        assert box.currentPos().getX() == 7;
        assert box.currentPos().getY() == 1;
    }

}
