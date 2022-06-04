package es.upm.pproject.sokoban.models.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.interfaces.Resetable;
import es.upm.pproject.sokoban.models.props.*;
import es.upm.pproject.sokoban.models.utils.Coordinates;


/**
* Class that represents a level.
* @author Alvaro Alonso Miguel
* @author Rafael Alonso Sirera
* @author Raul Casamayor Navas
* @version 1.7
* @since 04/06/2022
*/
@XmlRootElement(name="level")
@XmlType(propOrder = {"player","board","boxList","name","movements"})
public class Level implements Resetable{
    public static final String LEVEL_FILE_NAME_FORMAT = "src/resources/levels/level_%d.txt";
    
    private static final char UP = 'U';
    private static final char DOWN = 'D';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';
    
    private Player player;
    private Tile[][] board;
    private List<Box> boxList;
    private String name;
    private Deque<Character> movements;
    
    public Level(){}
    
    /**
    * Constructor of the class.
    * @param fileName Name of the file that contains the level.
    */
    public Level(String levelTxt) throws FileNotFoundException, WrongLevelFormatException {
        FileReader lvlFile = new FileReader(new File(levelTxt)); 
        BufferedReader br = new BufferedReader(lvlFile);
        int nGoals = 0;
        try{
            name = br.readLine();
            String[] numbersLine = br.readLine().split(" ");
            int rows = Integer.parseInt(numbersLine[0]);
            int cols = Integer.parseInt(numbersLine[1]);
            
            board = new Tile[rows][cols];
            boxList = new ArrayList<>();
            
            // Fills the board with the tiles read from the file char by char
            for(int row=0; row<rows; row++){
                String line = br.readLine();
                for(int column=0; column<cols; column++){
                    // Switch for different tiles + Wall, . empty square, * goal position, # Box, W player
                    switch(line.charAt(column)){
                        case 'W':
                        player = new Player(row, column);
                        board[row][column] = null;
                        break;
                        case '.':
                        board[row][column] = null;
                        break;
                        case '*':
                        board[row][column] = Tile.GOAL;
                        nGoals++;
                        break;
                        case '#':
                        board[row][column] = null;
                        boxList.add(new Box(row, column));
                        break;
                        case '+':
                        board[row][column] = Tile.WALL;
                        break;
                        default:
                        throw new IOException();
                    }
                }
            }
        }catch(IOException e){
            throw new WrongLevelFormatException("Error reading the file");
        }finally{
            try{
                br.close();
            }catch(IOException e){/*Close br in case its open */}
        }
        // Check if the level is correct
        if(player == null){
            throw new WrongLevelFormatException("The level must contain a player");
        }
        if(boxList.isEmpty()){
            throw new WrongLevelFormatException("The level must contain at least one box");
        }
        if(nGoals != boxList.size()){
            throw new WrongLevelFormatException("The number of goals must be equal to the number of boxes");
        }
        movements = new ArrayDeque<>();
    }
    
    /**
    * Returns the tile at the specified position.
    * @param row Row of the tile.
    * @param column Column of the tile.
    * @return The tile at the specified position.
    */
    public Tile getTile(int row, int column){
        return board[row][column];
    }
    
    /**
    * Method that returns the player coordinates.
    * @return Coordinates of the player position.
    */
    public Coordinates getPlayerCoords(){
        return player.currentPos();
    }
    
    /**
    * Method that returns all the boxes coordinates.
    * @return List of all the boxes coordinates.
    */
    public List<Coordinates> getBoxListCoordinates(){
        List<Coordinates> boxListCoordinates = new ArrayList<>();
        for(Box box : boxList){
            boxListCoordinates.add(box.currentPos());
        }
        return boxListCoordinates;
    }
    
    /**
    * Returns if a level is completed.
    * @return true if the level is completed, false otherwise.
    */
    public boolean checkStatus(){
        for(Box box : boxList){
            if(!box.isOnGoal()){
                return false;
            }
        }
        return true;
    }

    /**
     * Method to reset a level
     */
    @Override
    public void reset(){
        for (int i=0; i<boxList.size(); i++){
            boxList.get(i).reset();
        }
        player.reset();
        movements.clear();
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Integer[] playnbox = new Integer[board.length*board[0].length];
        for(int b=0; b<boxList.size();b++){
            Box box = boxList.get(b);
            playnbox[box.currentPos().getX()*board[0].length+box.currentPos().getY()] = 1;
        }
        playnbox[player.currentPos().getX()*board[0].length+player.currentPos().getY()] = 0;
        for(int i=0; i<playnbox.length; i++){
            if(i!=0 && (i%board[0].length)==0){
                res.append("\n");
            }
            if(playnbox[i]!=null){
                res.append(playnbox[i]==1 ? "#":"W");
                continue;
            }
            res.append(getStringRepresentation(board[i/board[0].length][i%board[0].length]));
        }
        return res.toString();
    }
    
    /**
    * This method moves the warehouse man one tile in the specified direction.
    * @param dir Movement direction
    * @return If the player did the movement
    */
    public boolean movePlayer(char dir){
        dir = Character.toUpperCase(dir);
        Coordinates newCoords = generateNewCoords(player.currentPos(), dir);
        if(newCoords == null) return false;
        MoveOrBox mob = canMoveElement(newCoords);
        if(mob.canMove == null){
            Coordinates newBoxCoords = generateNewCoords(mob.box.currentPos(), dir);
            if(newBoxCoords == null || !Boolean.TRUE.equals(canMoveElement(newBoxCoords).canMove) ) return false;
            mob.box.move(dir);
            mob.box.setOnGoal(board[newBoxCoords.getX()][newBoxCoords.getY()] == Tile.GOAL);
            dir = Character.toLowerCase(dir);
        }else if(Boolean.FALSE.equals(mob.canMove)) return false;
        player.move(dir);
        movements.push(dir);
        return true;
    }
    
    /**
    * Method that reverts the last movement of the warehouse man
    * @return If a movement could be undone
    */
    public boolean undoMove(){
        if(movements.isEmpty()) return false;
        char dir = movements.pop();
        char unDir = reverseDir(dir);
        if(Character.isLowerCase(dir)){
            Coordinates boxCoords = generateNewCoords(player.currentPos(), dir);
            Iterator<Box> it = boxList.iterator();
            boolean found = false;
            while(it.hasNext() && !found){
                Box b = it.next();
                if(b.currentPos().equals(boxCoords)){
                    b.move(unDir);
                    b.setOnGoal(board[b.currentPos().getX()][b.currentPos().getY()] == Tile.GOAL);
                    found = true;
                }
            }
        }
        player.move(unDir);
        return true;
    }
    
    /**
    * Private method use to determine if a coordinate is avaible as destination of a movement.
    * @param newCoords Destination to check
    * @return If the tile is avaible to move or the box that is placed on newCoords
    */
    private MoveOrBox canMoveElement(Coordinates newCoords){
        if(board[newCoords.getX()][newCoords.getY()] == Tile.WALL){
            return new MoveOrBox(false, null);
        }
        for (Box box : boxList) {
            if(box.currentPos().equals(newCoords)){
                return new MoveOrBox(null, box);
            }
        }
        return new MoveOrBox(true, null);
    }
    
    /**
    * Private method used to update a coordinate with a move.
    * @param oldCoords Current coordinates
    * @param dir Direction of the movement
    * @return Destination coordinates
    */
    private Coordinates generateNewCoords(Coordinates oldCoords, char dir){
        dir = Character.toUpperCase(dir);
        switch (dir) {
            case UP: return new Coordinates(oldCoords.getX() - 1, oldCoords.getY());
            case DOWN: return new Coordinates(oldCoords.getX() + 1, oldCoords.getY());
            case RIGHT: return new Coordinates(oldCoords.getX(), oldCoords.getY() + 1);
            case LEFT: return new Coordinates(oldCoords.getX(), oldCoords.getY() - 1);
            default: return null;
        }
    }
    
    /**
    * Private method used to retrive the reverse direction to the given one.
    * @param dir Direction to get the reverse
    * @return The reverse direction
    */
    private char reverseDir(char dir){
        dir = Character.toUpperCase(dir);
        switch (dir) {
            case UP: return DOWN;
            case DOWN: return UP;
            case RIGHT: return LEFT;
            default: return RIGHT;
        }
    }
    
    private String getStringRepresentation (Tile tile){
        if(tile==null){
            return " ";
        }
        if(tile==Tile.WALL){
            return "+";
        }
        return "*";
    }
    
    /**
    * Private class needed in canMoveElement method to return a Boolean and a Box object.
    */
    private class MoveOrBox {
        Boolean canMove;
        Box box;
        
        public MoveOrBox(Boolean canMove, Box box){
            this.canMove = canMove;
            this.box = box;
        }
    }
    
    /* Getters and setters needed for xml binding*/
    
    public Player getPlayer() {
        return player;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    @XmlElementWrapper(name="board")
    @XmlElement(name="tile")
    public Tile[][] getBoard() {
        return board;
    }
    
    public void setBoard(Tile[][] board) {
        this.board = board;
    }
    
    @XmlElementWrapper(name="boxList")
    @XmlElement(name="box")
    public List<Box> getBoxList() {
        return boxList;
    }
    
    public void setBoxList(List<Box> boxList) {
        this.boxList = boxList;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @XmlElementWrapper(name="movements")
    public Deque<Character> getMovements() {
        return movements;
    }
    
    public void setMovements(Deque<Character> movements) {
        this.movements = movements;
    }    
}
