package es.upm.pproject.sokoban.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import es.upm.pproject.sokoban.interfaces.Level;
import es.upm.pproject.sokoban.models.props.*;


/**
 * Class that represents a level.
 * @author Alvaro Alonso
 * @version 1.0
 * @since 22/04/2022
 */
public class LevelImp implements Level{
    private Player player;
    private Tile[][] board;
    private List<Box> boxList;

    public LevelImp(String levelTxt){
        try(FileReader lvlFile = new FileReader(new File(levelTxt)); BufferedReader br = new BufferedReader(lvlFile)){

            // Read first line and obtain the number of rows and the number of columns
            String[] firstLine = br.readLine().split(" ");
            int rows = Integer.parseInt(firstLine[0]);
            int cols = Integer.parseInt(firstLine[1]);

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
                        default:
                            board[row][column] = Tile.WALL;
                            break;
                    }
                }
            }
        } catch (IOException e){
            System.err.printf("Error reading level file %s\n", levelTxt);
        }
    }

    public Tile getTile(int row, int column){
        return board[row][column];
    }

    public boolean checkStatus(){
        return true;
    }
}
