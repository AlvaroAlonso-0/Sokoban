package es.upm.pproject.sokoban.models.props;

import javax.xml.bind.annotation.XmlElement;

import es.upm.pproject.sokoban.interfaces.Movable;
import es.upm.pproject.sokoban.interfaces.Resetable;
import es.upm.pproject.sokoban.models.utils.Coordinates;


/**
 * Class that implements a prop entity.
 * @author Alvaro Alonso Miguel
 * @author Idir Carlos Aliane Crespo
 * @version 1.5
 * @since 23/05/2022
 */
public abstract class Prop implements Movable, Resetable{
    protected int initialStateX;
    protected int initialStateY;
    protected int currentPositionX;
    protected int currentPositionY;

    protected Prop(){}

    /**
     * Constructor of the class.
     * @param initialStateX Initial position of the prop in the x axis.
     * @param initialStateY Initial position of the prop in the y axis.
     */
    protected Prop(int initialStateX, int initialStateY) {
        this.initialStateX = initialStateX;
        this.initialStateY = initialStateY;
        this.currentPositionX = initialStateX;
        this.currentPositionY = initialStateY;
    }

    /**
     * Returns the prop's current coordinates.
     * @return The prop's current coordinates.
     */
    public Coordinates currentPos() {
        return new Coordinates(this.currentPositionX,this.currentPositionY);
    }

    public void reset() {
        this.currentPositionX = this.initialStateX;
        this.currentPositionY = this.initialStateY;
    }

    public void move(char dir) {
        dir = Character.toUpperCase(dir);
        // U = Up, D = Down, L = Left, R = Right
        switch (dir) {
            case 'L':
                this.currentPositionY--;
                break;
            case 'R':
                this.currentPositionY++;
                break;
            case 'U':
                this.currentPositionX--;
                break;
            case 'D':
                this.currentPositionX++;
                break;
            default:
                break;
        }
    }

        /* Getters and setters needed for xml binding*/

        @XmlElement(name="initialStateX")
        public int getInitialStateX() {
            return initialStateX;
        }
    
        public void setInitialStateX(int initialStateX) {
            this.initialStateX = initialStateX;
        }
    
        @XmlElement(name="initialStateY")
        public int getInitialStateY() {
            return initialStateY;
        }
    
        public void setInitialStateY(int initialStateY) {
            this.initialStateY = initialStateY;
        }
    
        @XmlElement(name="currentPositionX")
        public int getCurrentPositionX() {
            return currentPositionX;
        }
    
        public void setCurrentPositionX(int currentPositionX) {
            this.currentPositionX = currentPositionX;
        }
    
        @XmlElement(name="currentPositionY")
        public int getCurrentPositionY() {
            return currentPositionY;
        }
    
        public void setCurrentPositionY(int currentPositionY) {
            this.currentPositionY = currentPositionY;
        }
}