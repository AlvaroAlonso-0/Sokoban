package es.upm.pproject.sokoban.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.sokoban.models.props.*;
import es.upm.pproject.sokoban.models.utils.Coordinates;


/**
* Class that represents a level.
* @author Alvaro Alonso
* @author Rafael Alonso Sirera
* @author Raul Casamayor Navas
* @version 1.3
* @since 01/05/2022
*/
public class Level{
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
    public Level(String levelTxt) throws IOException {
        FileReader lvlFile = new FileReader(new File(levelTxt)); 
        BufferedReader br = new BufferedReader(lvlFile);
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
        }finally{
            br.close();
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
    
    private Coordinates generateNewCoords(Coordinates oldCoords, char dir){
        switch (dir) {
            case UP: return new Coordinates(oldCoords.getX() - 1, oldCoords.getY());
            case DOWN: return new Coordinates(oldCoords.getX() + 1, oldCoords.getY());
            case RIGHT: return new Coordinates(oldCoords.getX(), oldCoords.getY() + 1);
            case LEFT: return new Coordinates(oldCoords.getX(), oldCoords.getY() - 1);
            default: return null;
        }
    }
    
    private class MoveOrBox {
        Boolean canMove;
        Box box;
        
        public MoveOrBox(Boolean canMove, Box box){
            this.canMove = canMove;
            this.box = box;
        }
    }    
}
