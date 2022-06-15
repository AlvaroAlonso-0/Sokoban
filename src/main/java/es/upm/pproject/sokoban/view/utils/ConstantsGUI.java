package es.upm.pproject.sokoban.view.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class ConstantsGUI {

    private ConstantsGUI(){ }

    public static final String SPRITE_FOLDER_PATH = "src/main/resources/sprites";
    public static final String DEFAULT_FRAME_TITLE = "Sokoban - untitled";
    public static final String SOKOBAN_TITLE = "Sokoban";

    public static final String WAREHOUSEMAN_NO_BACK_SPRITE = SPRITE_FOLDER_PATH + "/default/ware.png";

    public static final String AMONGUS_SPRITE = SPRITE_FOLDER_PATH + "/default/amongus.png";
    public static final String WAREHOUSEMAN_SPRITE = SPRITE_FOLDER_PATH + "/default/warehouse.png";
    public static final String WAREHOUSEMAN_GOAL_SPRITE = SPRITE_FOLDER_PATH + "/default/warehouseongoal.png";
    public static final String FLOOR_SPRITE = SPRITE_FOLDER_PATH + "/default/nuevosuelo.png";
    public static final String GOAL_SPRITE = SPRITE_FOLDER_PATH + "/default/meta.png";
    public static final String BOX_SPRITE = SPRITE_FOLDER_PATH + "/default/crafting.png";
    public static final String WALL_SPRITE = SPRITE_FOLDER_PATH + "/default/walltest.png";
    public static final String BOX_GOAL_PICKAXE_SPRITE = SPRITE_FOLDER_PATH + "/default/craftingpicaxe.png";
    public static final String BOX_GOAL_CHESTPLATE_SPRITE = SPRITE_FOLDER_PATH + "/default/craftingpechera.png";
    public static final String BACKGROUND_SPRITE = SPRITE_FOLDER_PATH + "/default/fondo.png";

    protected static final String [] BOX_GOALS = {BOX_GOAL_PICKAXE_SPRITE, BOX_GOAL_CHESTPLATE_SPRITE};

    public static final Color INFO_PANEL_COLOR = new Color (187, 162, 232);
    public static final Color LABEL_COLOR = new Color(229, 243, 255);
    public static final Color BORDER_COLOR  = new Color(153, 209, 255);
    public static final Color PRESSED_COLOR = new Color(204, 232, 255);
    public static final Color HOLDED_COLOR = new Color(218, 234, 247);

    public static final Border DEFAULT_BORDER = BorderFactory.createMatteBorder(1,1,1,1,BORDER_COLOR);
    public static final Border EMPTY_BORDER = BorderFactory.createEmptyBorder(1,1,1,1);
    public static final Border MENU_BAR_BORDER = BorderFactory.createMatteBorder(0,0,1,0,new Color(128, 126, 126));

    public static final int SPRITE_SIZE = 50;
    public static final int MAIN_FRAME_MAX_WIDTH = 900;
    public static final int MAIN_FRAME_MAX_HEIGHT = 700 + SPRITE_SIZE;
    public static final int INFO_PANEL_WIDTH = MAIN_FRAME_MAX_WIDTH;
    public static final int INFO_PANEL_HEIGHT = SPRITE_SIZE;
    
    public static final Font SCORE_FONT = new Font("Calibri", Font.BOLD, 30);
    public static final Font DEAULT_FONT = new Font("Arial", Font.PLAIN, 14);
}
