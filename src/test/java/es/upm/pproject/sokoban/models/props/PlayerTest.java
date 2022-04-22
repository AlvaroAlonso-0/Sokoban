package es.upm.pproject.sokoban.models.props;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Class to test the Player")
public class PlayerTest {
    private Player player;
    
    @BeforeEach
    void init(){
        player = new Player(3,2);
    }

    @Test
    @DisplayName("Tests the Player")
    void constTest(){
        assert player.currentPos().getX() == 3;
        assert player.currentPos().getY() == 2;
    }

}
