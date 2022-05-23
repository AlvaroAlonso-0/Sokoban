package es.upm.pproject.sokoban.models.props;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that implements a prop entity.
 * @author Alvaro Alonso Miguel
 * @author Raul Casamayor Navas
 * @version 1.1
 * @since 23/05/2022
 */
@XmlRootElement(name="player")
public class Player extends Prop{

    public Player(){
        super();
    }

    public Player(int initialStateX, int initialStateY) {
        super(initialStateX, initialStateY);
    }
}
