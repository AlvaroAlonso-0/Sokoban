# **SOKOBAN** #

This repository conatains a Java project to play the game **sokoban**. Sokoban is a puzzle game where the player has to move boxes to the goal. The game is played on a grid of squares. The player can move the boxes by pushing them on to another square. The player can only move the boxes in one direction at a time. Play the game and see if you can solve it to win the game and unlock the secret.

## Table of Contents ##

* [Introduction](#introduction)
  * [Download the source code](#download-the-source-code)
  * [Run the tests](#run-the-tests)
  * [Directory Layout](#directory-layout)
  * [Implementation](#implementation)
* [Run the game](#run-the-game)
  * [How to play](#how-to-play)
  * [Load and save](#load-and-save)
* [Authors](#authors)

## Introduction ##

### Download the source code ###

1. [Install Java](<http://www.oracle.com/java/technologies/downloads/>)
2. [Install Maven](<https://maven.apache.org/download.cgi>)
3. Clone the git repository `git clone https://github.com/AlvaroAlonso-0/Sokoban.git`

### Run the tests ###

1. Change to the root directory of the repository
2. Create the project build `mvn clean install`
3. Run the tests with `mvn test`

### Directory Layout ###

``` terminal

    sokoban
    ├── README.md
    ├── pom.xml
    ├── deliverables
    │   ├── backlog1.csv
    │   ├── backlog2.csv
    │   ├── backlog3.csv
    │   ├── sprint1.csv
    │   └── sprint2.csv
    ├── log
    │   └── output.log
    ├── games
    │   └── default_game.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── es
        │   │       └── upm
        │   │           └── pproject
        │   │               └── sokoban
        │   │                   ├── App.java
        │   │                   ├── controller
        │   │                   │   └── Controller.java
        │   │                   ├── exceptions
        │   │                   │   └── WrongLevelFormatException.java
        │   │                   ├── interfaces
        │   │                   │   ├── Movable.java
        │   │                   │   └── Resetable.java
        │   │                   ├── models
        │   │                   │   ├── Game.java
        │   │                   │   ├── level
        │   │                   │   │   ├── Level.java
        │   │                   │   │   └── Tile.java
        │   │                   │   ├── props
        │   │                   │   │   ├── Box.java
        │   │                   │   │   ├── Player.java
        │   │                   │   │   └── Prop.java
        │   │                   │   ├── sgfactory
        │   │                   │   │   └── SaveGameFactory.java
        │   │                   │   └── utils
        │   │                   │       └── Coordinates.java
        │   │                   └── view
        │   │                       ├── GUI.java
        │   │                       ├── GameStatusGUI.java
        │   │                       ├── frames
        │   │                       │   ├── AcceptFrame.java
        │   │                       │   ├── AlertFrame.java
        │   │                       │   ├── HelpFrame.java
        │   │                       │   ├── LoadFrame.java
        │   │                       │   └── SaveFrame.java
        │   │                       ├── panels
        │   │                       │   ├── DynamicPanelList.java
        │   │                       │   ├── ImagePanel.java
        │   │                       │   └── Rectangle.java
        │   │                       └── utils
        │   │                           ├── ConstantsGUI.java
        │   │                           └── UtilsGUI.java
        │   └── resources
        │       ├── backgrounds
        │       │   └── main_background.png
        │       ├── icons
        │       │   └── main_icon.png
        │       ├── levels
        │       │   ├── level_1.txt
        │       │   ├── level_2.txt
        │       │   └── level_3.txt
        │       ├── levels4Testing
        │       │   ├── level0empty.txt
        │       │   ├── level0wrongBox.txt
        │       │   ├── level0wrongGoal.txt
        │       │   ├── level0wrongPlayer.txt
        │       │   ├── levelTwoBoxes.txt
        │       │   ├── levelTwoBoxesMove.txt
        │       │   ├── level_2.txt
        │       │   └── level_3.txt
        │       ├── log4j.properties
        │       ├── sprites
        │       │   └── default
        │       │       ├── amogusongoal.png
        │       │       ├── amongus.png
        │       │       ├── box.png
        │       │       ├── d_chestplate.png
        │       │       ├── e_chestplate.png
        │       │       ├── enchanting.png
        │       │       ├── floor.png
        │       │       ├── goal.png
        │       │       ├── goldenapple.png
        │       │       ├── magma.png
        │       │       ├── nautilus.png
        │       │       ├── pickaxe.png
        │       │       ├── wall.png
        │       │       ├── warehouse.png
        │       │       └── warehouseongoal.png
        │       └── xml4testing
        │           ├── Test 1.xml
        │           ├── Test 2.xml
        │           └── wrongGame
        └── test
            └── java
                └── es
                    └── upm
                        └── pproject
                            └── sokoban
                                └── models
                                    ├── GamesTest.java
                                    ├── level
                                    │   └── LevelTest.java
                                    ├── props
                                    │   ├── BoxTest.java
                                    │   └── PlayerTest.java
                                    ├── sgfactory
                                    │   └── SaveGameFactoryTest.java
                                    └── utils
                                        └── CoordinatesTest.java
        
```

### Implementation ###

This project is oriented to emulate the game **sokoban** using the **model-view-controller** arquitecture pattern.

The **controller** is the class that handles the communication between the model and the view. Is in charge of starting the game, handling the user input and updating the model.

The **model** is formed by *Game, Level, Tiles and Props* classes which implement interfaces for a better code reliability and maintainability, also are available personalized exceptions for the Level. The *Game* is compossed of multiple levels, each one with a different layout. One *Level* is repesented by an array of Tiles, where each *Tile* (Wall, Floor, Goal) is a square on the grid. The player is a *Prop* and is represented by a character on the grid and located by specific coordinates, represented by a pair of integers. The boxes are *Props* and are contained in a list of boxes on the level. The player can move the boxes by pushing them on to another square, the player can only move the boxes in one direction at a time. The direction of the movement is represented by a character (*u, d, l, r*). The score is represented by an integer and is incremented when the player makes a valid move, the final score is the sum of the scores of all the levels. A logger is used to log the game events such as the player movement, the changes in the level and the status of the game.

The *Level* is represented by a string of characters, where each character represents a square on the grid and is finished when all the boxes are on the goals, if another level is available, the game continues with the next level, if not, the game is over and the final score is displayed.

The **view** is the graphical interface of the game. It is composed by a *JFrame* and a *JPanel* with a *GridLayout*. The *JPanel* contains a *JLabel* with the score and a *JLabel* with the level. The *JPanel* with the level is updated when the player makes a valid move. The *JLabel* with the score is updated when the player makes a valid move. On the top of the *JFrame* is a *JMenuBar* with all the options of the game. This options are: *Load*, *Save*, *Undo*, *Redo*, *Reset*, *Help*.

*Load* loads a level from a file, *Save* saves the current level to a file, *Undo* undoes the last move, *Redo* redoes the last move, *Reset* resets the game, *Help* shows the help of the game. All the options are available with shortcuts.

All the logic of the model is tested with the *JUnit* framework and try to cover all the possible scenarios.

Inside the resources folder there are the files used to run the game, the files used to test the game and the sprites used in the game.

The way we worked on the project was divided in four parts:

* Figured out the architecture of the game and the objects that compose it.
* Created the interfaces and the classes that represent the game objects.
* Implemented the game logic leaning on the test results.
* Connected the game logic with the view by the controller.

## Run the game ##

1. Change to the root directory of the repository
2. Compile the project with: `mvn clean compile`
3. Run the game with: `mvn exec:java`

This will be displayed on a new window.

<div style="text-align: center;">

  ![Sokoban](https://i.imgur.com/BVmTZDs.png)

</div>

###  How to play ###

The objective of the game is to move the boxes to the goals. The player can move the boxes by pushing them on to another square. The player can only move the boxes in one direction at a time. The available keys are: *w, a, s, d* for the player which represent *up, left, down, right* respectively. The game is played by pressing the keys on the keyboard. The score is incremented when the player makes a valid move, the final score is the sum of the scores of all the levels.

There are some shortcuts for the game:

* *Ctrl+Z* to undo the last move.
* *Ctrl+Shift+Z* to redo the last move.
* *Ctrl+S* to save the current level.
* *Ctrl+L* to load a level.
* *Ctrl+N* to start a new game.

### Load and save ###

The game can be saved and loaded from a file, available at the *games* folder. The file is a xml file that contains the game state with all its variables as attributes like the score, the level name, the grid, the player position, the boxes positions, the stack of moves and the stack of undo moves.

## Authors ##

190300 - Álvaro Alonso Miguel

190243 - Raúl Casamayor Navas

190182 - Rafael Alonso Sirera

190384 - Idir Carlos Aliane Crespo
