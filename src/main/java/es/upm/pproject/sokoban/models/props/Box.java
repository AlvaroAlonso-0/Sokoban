package es.upm.pproject.sokoban.models.props;

/**
 * Class that represents a box.
 * @author Alvaro Alonso
 * @version 1.1
 * @since 22/04/2022
 */

public class Box extends Prop{
    boolean isOnGoal;
    public Box(int initialStateX, int initialStateY) {
        super(initialStateX, initialStateY);
        this.isOnGoal = false;
    }

    public void setOnGoal(boolean isOnGoal) {
        this.isOnGoal = isOnGoal;
    }

    public boolean isOnGoal() {
        return isOnGoal;
    }
}
