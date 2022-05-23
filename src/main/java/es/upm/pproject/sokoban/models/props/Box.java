package es.upm.pproject.sokoban.models.props;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class that represents a box.
 * @author Alvaro Alonso Miguel
 * @author Raul Casamayor Navas
 * @version 1.3
 * @since 23/05/2022
 */
@XmlRootElement(name="box")
public class Box extends Prop{
    private boolean isOnGoal;

    public Box(){
        super();
        this.isOnGoal = false;
    }

    public Box(int initialStateX, int initialStateY) {
        super(initialStateX, initialStateY);
        this.isOnGoal = false;
    }

    @XmlElement(name="onGoal")
    public boolean isOnGoal() {
        return isOnGoal;
    }

    public void setOnGoal(boolean isOnGoal) {
        this.isOnGoal = isOnGoal;
    }
}
