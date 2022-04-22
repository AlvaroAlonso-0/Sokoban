package es.upm.pproject.sokoban.models.props;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class to test the Player")
class PlayerTest {
    private Player player;
    
    @BeforeEach
    void init(){
        player = new Player(3,2);
    }

    @Test
    @DisplayName("Tests the Player")
    void constTest(){
        assertEquals(3,player.currentPos().getX());
        assertEquals(2,player.currentPos().getY());
    }

}
