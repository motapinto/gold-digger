# Description
In this exciting and very interactive 2D single-player adventure game you have to explore the worlds we created to progress through the levels. You are a Yukon Miner, and you have a debt with a Mexican drug cartel. Using your skill you have to collect as much gold as you can, without getting killed. Also, the time is ticking... 

# Implementation

![UML Architecture](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/UML/base_structure.png)

## Features
We have two types of features
* - [ ] :arrow_right: planned feature
* - [x] :arrow_right: implemented feature

### Menus
* **Main Menu:**
     - [x] Choose a certain achieved level
     - [x] Start from the first level, reseting the achieved levels 
     - [x] Continue from the first not achieved level.
     - [ ] View highscores (top scores for each level)
* **Playing:**
    - [x] Pause: Stops timer and level
    - [x] Undo: Undo last play
* **Pause:**
    - [x] Continue game: Continues timer and level
    - [x] Restart: Start level again
    - [x] Exit: Go to main menu
* **Game Over:**
    - [x] Undo: undo last valid move
    - [x] Restart: Start level again
    - [x] Main Menu: goes to Main Menu
* **Game Won:**
    - [x] Undo: undo last valid move
    - [x] Restart: Start level again
    - [x] Main Menu: goes to Main Menu
    - [x] Nex Level: procedes with Yukon journey
* **Selector:**
    - [x] Choose level: select a level that the user has achieved before
    - [x] Main Menu: goes to Main Menu
* **All Completed:**
    - [x] Replay: replay the last level
    - [x] Main Menu: goes to Main Menu

### The game
#### Elements
* **Digger:** 
    - [x] The player can move the digger;
    - [x] The digger can jump one tile;
    - [x] When a boulder falls on top of the digger he will loose a life;
* **Boulder:** 
    - [x] A boulder may fall down if no dirt is bellow it;
    - [x] A boulder that collides with another boulder may move to either left or right;
    - [x] A boulder may be moved by the digger either to the left or right.
* **Dirt:** 
    - [x] When the digger hits a dirt block, the dirt looses HP 
    - [x] When the dirt block's HP reach 0 it is destroyed
* **Door:** 
    - [x] When all keys are collected the door will open
    - [x] When the digger is in the door tile and this is open the digger completes the level
* **Key:** 
    - [x] When the digger moves to a key tile he  picks it up
* **Gold:** 
    - [x] When the digger moves to a gold tile he picks it up
* **Scaffold:** 
    - [x] When the digger destroys a dirt block on top or bellow him, a scaffold is placed
* **Delimiter:**
    - [x] When the player tries to move to a delimiter tile he will be stopped

#### **Score**
- [x] The score that will be based on the cumulative time that the player needs to complete a level and the gold it collects on its way. The objective is to minimize the time spent on a level that will collect as much gold as possible.

#### Highscores
- [ ] Still to develop, but we intend to have a high score history that will save the top score per level. 


#### Themes 
- [ ] We intent on having different themes for each level. We probably will develop only two themes, retro and modern themes.


#### InfoBar
- [x] Infobar Display Information: 
    - [x] Level Name
    - [x] Keys: Collected keys / Total keys on the level
    - [x] Timer: Counts the time it takes to complete a level
    - [x] Gold : Total amount of gold collected from the start of the level

#### Flood Fill
 - [x] We wanted to make sure the player would feel involved and with a sense of constant danger of the hazardous environment. For that, we developed a flood fill algorithm in which we draw only the elements the player could see, ie, by analyzing each of the boundaries surrounding the player we can determine what is visible and what is not. This makes the task of completing a level much more difficult, but also, much more challenging and fun to surpass it.

### LevelReader
 - [x] We have the LevelReader class that decodes .lvl files that are placed under resources/levels. Each level resource contains ASCII symbols that represent each element on a level.

### Saving history
 - [x] We wanted to make sure a player, when exists the game can then continue to play and follow Yukon journey. The achieved level is saved in a properties file, that consistes in a series os pair of key and values. This done vai the [IOManager class]()
 

## Design Patterns

### Using different GUI's
> #### Refactor Guru:
> :bulb: Use the Abstract Factory when your code needs to work with various families of related products, but you don’t want it to depend on the concrete classes of those products—they might be unknown beforehand or you simply want to allow for future extensibility."  

#### Problem in context
> We wanted to make sure that our game would work with any GUI. By implementing this pattern, we would not need to modify code in more than one place by adding a GUI. We would only need to create specific views for a new GUI.
#### The Pattern
> The pattern choosed was the [**Abstract Factory pattern**](https://refactoring.guru/design-patterns/abstract-factory)  which, depending on the target platform, creates Views specific for it.
#### Implementation
![View ](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/UML/view.png)
> Classes:
> * [GUIFactory](../src/main/java/com/g12/golddigger/view/GUIFactory.java)
>   * [LanternaFactory](../src/main/java/com/g12/golddigger/view/LanternaFactory.java)
> * [GUI](../src/main/java/com/g12/golddigger/view/GUI.java)
>   * [LanternaGUI](../src/main/java/com/g12/golddigger/view/LanternaGUI.java)
>     * [LanternaMenu](../src/main/java/com/g12/golddigger/view/views/LanternaMenu.java)
>     * [LanternaGame](../src/main/java/com/g12/golddigger/view/views/LanternaGame.java)
>     * [LanternaGameWon](../src/main/java/com/g12/golddigger/view/views/LanternaGameWon.java)
>     * [LanternaGameOver](../src/main/java/com/g12/golddigger/view/views/LanternaGameOver.java)
>     * [LanternaPaused](../src/main/java/com/g12/golddigger/view/views/LanternaPaused.java)
>     * [LanternaSelector](../src/main/java/com/g12/golddigger/view/views/LanternaSelector.java)
>     * [LanternaAllCompleted](../src/main/java/com/g12/golddigger/view/views/LanternaAllCompleted.java)
>     
> We decided on using an abstract factory that creates views for all menu states that are overridden on the concrete factories. These concrete factories are specific for each GUI.
> If possible, the user will have the possibility to play using different GUI's, therefore a concrete factory will be created based on his choice.
#### Consequences
* Compliance with Open/Closed Principle and Single Responsibility Principle
* Ensures compatibility and consistency with the "products" from the same factory
* Even though the code may become more complicated than it should be, by adding a lot of "unidirectionality" the advantages far outweigh the cons.

---

### Different actions in different stages of the game
> #### Refactor Guru:
> :bulb: Use the State pattern when you have an object that behaves differently depending on its current state, the number of states is enormous, and the state-specific code changes frequently.  
> :bulb: Use the pattern when you have a class polluted with massive conditionals that alter how the class behaves according to the current values of the class’s fields.  
> :bulb: Use State when you have a lot of duplicate code across similar states and transitions of a condition-based state machine.

#### Problem in context
> The problem we faced was that the game has to behave differently based on the current game state. So, in order to remove an unnecessarily big switch statement, we went on to implement this pattern.
#### The Pattern
> The pattern choosed was the [**State pattern**](https://refactoring.guru/design-patterns/state).
> which allows you represent different states with different subclasses. We can switch to a different state of the application by switching to another implementation.
#### Implementation

![Controller](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/UML/controller.png)
> Classes:
> * [Controller](../src/main/java/com/g12/golddigger/controller/Controller.java)
> * [State](../src/main/java/com/g12/golddigger/controller/state/State.java)
>   * [AllCompleted](../src/main/java/com/g12/golddigger/controller/state/AllCompleted.java)
>   * [LevelChanger](../src/main/java/com/g12/golddigger/controller/state/levelchanger/LevelChanger.java)
>     * [MainMenu](../src/main/java/com/g12/golddigger/controller/state/levelchanger/MainMenu.java)
>     * [Paused](../src/main/java/com/g12/golddigger/controller/state/levelchanger/Paused.java)
>     * [Selector](../src/main/java/com/g12/golddigger/controller/state/levelchanger/Selector.java)
>     * [Undoable](../src/main/java/com/g12/golddigger/controller/state/levelchanger/undoable/Undoable.java)
>       * [GameOver](../src/main/java/com/g12/golddigger/controller/state/levelchanger/undoable/GameOver.java)
>       * [GameWon](../src/main/java/com/g12/golddigger/controller/state/levelchanger/undoable/GameWon.java)
>       * [PlayingState](../src/main/java/com/g12/golddigger/controller/state/levelchanger/undoable/playing/PlayingState.java)
>         * [Playing](../src/main/java/com/g12/golddigger/controller/state/levelchanger/undoable/playing/Playing.java)
>         * [InAir](../src/main/java/com/g12/golddigger/controller/state/levelchanger/undoable/playing/InAir.java)
>         
> The pattern was implemented using an abstract class State which has a single public function (handle) that receives an InputKey that comes from the GUI. Each state knows how to process and change the model according to the input received as well as call the controller to change the current state.




#### Consequences
* Compliance with Open/Closed Principle and Single Responsibility Principle
* Simplify the code of the context by eliminating bulky state machine conditionals.
* The controller needs to have public methods that allow the state to call for a change of state.


## Architectural Patterns

We are implementing an MVC pattern. To do this we have divided the architecture into 3 separate packages:
* Model: this package <ins>is not aware of any other package</ins> and all public functions are relatively simple. It functions as a simple database. We separated this package into 3 main classes: 
    * Level: This class holds all information regarding the current level (digger, boulders, limiters, keys, gold pieces, door, and dirt blocks).
    * Menu: A menu consists in a list of options of generic type and is used all acroos the game (main menu, selector menu, paused menu, gamewon menu, gameover menu)
    * Model: The class that represents the model. It contains the level, all the menus, a list of states (for the undo operation) and the last achieved level.
![UML Model Architecture](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/UML/model.png)

* View: this package <ins>is only aware of the Model</ins> package, it uses the model's data to draw the current state of that information. This package is currently implemented using the Abstract Factory pattern, this factory returns a GUI implementing class. The factory currently has a function for each state of the game (main menu, playing, game won, game over, selector, menu and selector), each of these functions returns a different GUI class.
![UML View Architecture](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/UML/view.png)
    
* Controller: this package <ins>is aware of both Model and View</ins> packages and is responsible for deciding when to call the view to draw the model. This package makes use of the State pattern to handle the input in different stages of the game (Menu, Selector, Paused, Playing, and GameOver). 
![UML Controller Architecture](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/UML/controller.png)


## Code Smells and Refactoring
### Duplicated code
While creating the State pattern we found that some operations, despite being from different states, follow the same procedure that might have only variations in the beginning or end. This caused some portions of code to be duplicated from across the multiple states.
The solution to this problem is to implement the Command pattern in such a way that the actions performed by the states are executed by commands, effectively eliminating the duplicated code. This pattern would also help to standardize the undo operation.  
This solution is viable but it would take a long time to implement and also make the code more complicated since the commands would be subclasses of an abstract Command, much like the State pattern we have implemented.

### Large Class
We have a class named Level that has too many fields and because of that has to many getters. It has all the objects in a level, which are: list of boulders, list of gold pieces, list of keys, list of dirt blocks, list of delimiters, list of scaffolds, digger, and door.

To fix this we may try to divide the class into smaller classes with less responsibility. Having said this, there does not seem to be a good way to divide the class since all the classes inside Level are related to a map/level. 

A possible solution is to use the <ins>Extract class </ins> technique of refactoring, but the Level class makes more sense if it is done like this and also dividing into subclasses would make for larger function chains.

This problem also occurs in the PlayingState class. This class defines all the methods that are in a way useful for subclasses. One way to remove this code smell would be to add more classes that would be responsible for some of the functions implemented. 

### Switch  & if's statements
The current implementation of the State pattern has a switch in each handle function, this is a code smell but there is no way to avoid it, since we need to process each input differently.   
Another smell that is currently in the code is the use of if statements in the act function of the Playing state, this is because the conditions are chained in the sense that an operation only accours if the other fails/succeeds, this can be avoided by using a Command Pattern where we can chain these commands, effectivly reducing the chain of if statements.

### NULL returning function
In the PlayingState class there are functions for determining with which class the Digger collided, this is done by the use of null returning functions. To avoid this, we can introduce the Null Object or instead of returning that, we can throw a custom Exception. This way, we avoid always checking if the return is null.


## Unit Tests
### Classes
We made a great effort to tried to maximize the coverage of our tests, but without having the fear to have some lines of codes without coverage. This is because there are a lot of getters and setters with no need to add tests, or some other tests that would basically the copy of the original code.

We also have private and protected method that are not possible to test, and would be just stupid to disregard OOP concepts and start making every function public so that we could test them.
* - [ ] :arrow_right: tests not done or not functional
* - [x] :arrow_right: tests fully done and functional

* **Controller:** 
   - [x] [ControllerTest](../src/test/java/com/g12/golddigger/controller/ControllerTest.java)
   - [x] [MainMenuTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/MainMenuTest.java)
    - [x] [SelectorTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/SelectorTest.java)
    - [x] [PlayingTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/undoable/playing/PlayingTest.java)
    - [x] [PlayingStateTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/undoable/playing/PlayingStateTest.java)
    - [x] [PausedTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/PausedTest.java)
    - [x] [GameOverTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/undoable/GameOverTest.java)
    - [x] [GameWonTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/undoable/GameWonTest.java)
     - [x] [InAirTest](../src/test/java/com/g12/golddigger/controller/state/levelchanger/undoable/playing/InAirTest.java)
     - [x] [AllCompletedTest](../src/test/java/com/g12/golddigger/controller/state/AllCompletedTest.java)

* **Utils:** 
   - [x] [IOManagerTest](../src/test/java/com/g12/golddigger/utils/IOManagerTest.java)
   - [x] [LevelReaderTest](../src/test/java/com/g12/golddigger/utils/LevelReaderTest.java)
   - [x] [ModelBuilderTest](../src/test/java/com/g12/golddigger/utils/ModelBuilderTest.java)
  
* **Model:** 
   - [x] [ModelTest](../src/test/java/com/g12/golddigger/model/ModelTest.java)
   - [x] [MenuTest](../src/test/java/com/g12/golddigger/model/MenuTest.java)

* **View:** 
     - [x] [LanternaGUITest](../src/test/java/com/g12/golddigger/view/lanterna/LanternaGUITest.java)
   - [x] [LanternaMainMenu](../src/test/java/com/g12/golddigger/view/views/LanternaMenuTest.java)
   - [x] [LanternaSelector](../src/test/java/com/g12/golddigger/view/views/LanternaSelector.java)
   - [x] [LanternaGame](../src/test/java/com/g12/golddigger/view/views/LanternaGame.java)
   - [x] [LanternaPaused](../src/test/java/com/g12/golddigger/view/views/LanternaPaused.java)
   - [x] [LanternaGameOver](../src/test/java/com/g12/golddigger/view/views/LanternaGameOver.java)
   - [x] [LanternaGameWon](../src/test/java/com/g12/golddigger/view/views/LanternaGameWon.java)
   - [x] [LanternaAllCompleted](../src/test/java/com/g12/golddigger/view/views/LanternaAllCompleted.java)
   - [x] [LanternaFactoryTest](../src/test/java/com/g12/golddigger/view/LanternaFactoryTest.java)


### Property Based Tests (PBT)
- [x] We explored this new technology and executed 3 PPT tests in [MenuTest](/src/test/java/com/g12/golddigger/model/MenuTest.java) class and [ModelTest](/src/test/java/com/g12/golddigger/model/ModelTest.java) class.

### Results
#### Coverage
![Coverage](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/tests/unitTests.jpg)

#### PI Test
The Pit Test Coverage Report can be seen below:
![Pitest report](https://github.com/FEUP-LPOO/lpoo-2020-g12/blob/master/docs/tests/pitest.png)
Note: in order for the pitest to run properly most calls to Mockito.verify method had to be removed in the com.g12.golddigger.controller.state package since this caused an unexpected exception.
As such the report might not match the actual tests done, however we expect the difference to not be noticeable.
The actual tests that were used to build this report can be seen in the pitest branch of the project.
