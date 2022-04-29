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
 * @version 1.1
 * @since 22/04/2022
 */
public class Level{
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
}
