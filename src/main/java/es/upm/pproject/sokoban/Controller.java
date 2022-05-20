package es.upm.pproject.sokoban;

import es.upm.pproject.sokoban.exceptions.WrongLevelFormatException;
import es.upm.pproject.sokoban.view.GUI;
import es.upm.pproject.sokoban.game.GameStatusGUI;

/**
* Class for the Controller of the application.
* @author Rafael Alonso Sirera
* @version 1.0
* @since 12/05/2022
*/
public class Controller{
    private GUI gui;
    private GameStatusGUI game;
    public Controller(){
        gui = new GUI(this);
        try {
            game = new GameStatusGUI();
            gui.show(game.getBoardToString());
        } catch (WrongLevelFormatException e) {
            //TODO implement GUI error message method
        }
    }

    public void keyTyped(char key){
        char dir;
        switch(key){
            case 'W': dir='U'; break;
            case 'A': dir='L'; break;
            case 'S': dir='D'; break;
            case 'D': dir='R'; break;
            case 'U': undo(); return;
            default: dir='E';  break;
        }
        try{
            if(game.movePlayer(dir)){
                repaint();
            }
        }
        catch(WrongLevelFormatException e){
            //TODO implement GUI error message method
        }
    }

    private void undo(){
        if(game.undo()){
            repaint();
        }
    }

    private void repaint(){
        gui.repaint(game.getBoardToString());
    }
}
