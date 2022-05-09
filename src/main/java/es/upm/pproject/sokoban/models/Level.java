package es.upm.pproject.sokoban.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.models.props.*;
import es.upm.pproject.sokoban.models.utils.Coordinates;


/**
* Class that represents a level.
* @author Alvaro Alonso Miguel
* @author Rafael Alonso Sirera
* @author Raul Casamayor Navas
* @version 1.4
* @since 01/05/2022
*/
public class Level{
    public static final String LEVEL_FILE_NAME_FORMAT = "resources/level_%d.txt";

    private static final char UP = 'U';
    private static final char DOWN = 'D';
    private static final char LEFT = 'L';
    private static final char RIGHT = 'R';
    
    private Player player;
    private Tile[][] board;
    private List<Box> boxList;
    private String name;
    
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
    }
    
    /**
    * Method that returns the name of the level.
    * @return Name of the level.
    */
    public String getName(){
        return name;
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
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Integer[][] playnbox = new Integer[board.length][board.length];
        for(int b=0; b<boxList.size();b++){
            Box box = boxList.get(b);
            playnbox[box.currentPos().getX()][box.currentPos().getY()] = 1;
        }
        playnbox[player.currentPos().getX()][player.currentPos().getY()] = 0;
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(playnbox[i][j]!=null){
                    res.append(playnbox[i][j]==1 ? "#":"W");
                }
                else{
                    if(board[i][j]==null){
                        res.append(" ");
                    }
                    else{
                        res.append(board[i][j]==Tile.WALL ? "+":"*");
                    }
                    
                }
            }
            res.append("\n");
        }
        res.setLength(res.length()-1);
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
        }else if(Boolean.FALSE.equals(mob.canMove)) return false;
        player.move(dir);
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
        switch (dir) {
            case UP: return new Coordinates(oldCoords.getX() - 1, oldCoords.getY());
            case DOWN: return new Coordinates(oldCoords.getX() + 1, oldCoords.getY());
            case RIGHT: return new Coordinates(oldCoords.getX(), oldCoords.getY() + 1);
            case LEFT: return new Coordinates(oldCoords.getX(), oldCoords.getY() - 1);
            default: return null;
        }
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
}
