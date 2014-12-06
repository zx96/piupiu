/*
 * This is a shooter game called Piu Piu in which enemies randomly spawn and 
 * the player tries to last as long as possible.
 */
package me.zx96.piupiu;

import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class Main extends Application {
    
    /**
     * Starts the JavaFX application.  Creates a new GameEngine and loops.
     * PreCondition: None.
     * PostCondition: The game is started.
     * 
     * @param primaryStage The game window.
     */
    @Override
    public void start(Stage primaryStage) {
        
        //Set up the game window
        GameEngine engine = new GameEngine();
        
        primaryStage.setTitle("Piu Piu");
        primaryStage.setScene(engine.getScene());
        primaryStage.show();
    }
    
}
