package es.upm.pproject.sokoban.view.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class ConstantsGUI {

    private ConstantsGUI(){ }

    public static final String DEFAULT_SPRITE_FOLDER_PATH = "src/main/resources/sprites/default/";
    public static final String ICON_FOLDER_PATH = "src/main/resources/icons/";
    public static final String BACKGROUND_FOLDER_PATH = "src/main/resources/backgrounds/";
    public static final String DEFAULT_FRAME_TITLE = "Sokoban - untitled";
    public static final String SOKOBAN_TITLE = "Sokoban";

    public static final String MAIN_ICON = ICON_FOLDER_PATH + "main_icon.png";

    public static final String AMONGUS_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "amongus.png";
    public static final String AMOGUS_GOAL_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "amogusongoal.png";
    public static final String WAREHOUSEMAN_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "warehouse.png";
    public static final String WAREHOUSEMAN_GOAL_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "warehouseongoal.png";
    public static final String FLOOR_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "floor.png";
    public static final String GOAL_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "goal.png";
    public static final String BOX_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "box.png";
    public static final String WALL_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "wall.png";
    public static final String BACKGROUND = BACKGROUND_FOLDER_PATH + "main_background.png";

    public static final String BOX_GOAL_PICKAXE_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "pickaxe.png";
    public static final String BOX_GOAL_CHESTPLATE_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "d_chestplate.png";
    public static final String BOX_GOAL_MAGMA_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "magma.png";
    public static final String BOX_GOAL_NAUTILUS_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "nautilus.png";
    public static final String BOX_GOAL_GOLDEN_APPLE_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "goldenapple.png";
    public static final String BOX_GOAL_EMERALD_SPRITE = DEFAULT_SPRITE_FOLDER_PATH + "e_chestplate.png";

    protected static final String [] BOX_GOALS = {BOX_GOAL_PICKAXE_SPRITE, BOX_GOAL_CHESTPLATE_SPRITE,
                                                    BOX_GOAL_MAGMA_SPRITE, BOX_GOAL_NAUTILUS_SPRITE, 
                                                    BOX_GOAL_GOLDEN_APPLE_SPRITE, BOX_GOAL_EMERALD_SPRITE};

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
