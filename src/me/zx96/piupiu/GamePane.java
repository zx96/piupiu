/*
 * This is the Pane that displays all of the game's visual elements.
 */
package me.zx96.piupiu;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class GamePane extends Pane {
    
    private GameEngine engine;
    
    private Label scoreLabel;
    private Label highScoreLabel;
    private Rectangle healthBar;
    
    /**
     * Constructs a new GamePane with a dark background, a player health bar, 
     * and labels for the current and high scores.
     * PreCondition: None.
     * PostCondition: None.
     */
    public GamePane() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(8, 8, 8), null, null)));
        
        scoreLabel = new Label();
        scoreLabel.setMinWidth(Dimensions.SCREEN_WIDTH);
        scoreLabel.setFont(Font.loadFont(ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_NORMAL));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setAlignment(Pos.TOP_RIGHT);
        scoreLabel.setTextAlignment(TextAlignment.RIGHT);
        scoreLabel.setTranslateY(Dimensions.HEALTH_BAR_SIZE + 2);
        
        highScoreLabel = new Label();
        highScoreLabel.setFont(Font.loadFont(ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_NORMAL));
        highScoreLabel.setTextFill(Color.WHITE);
        highScoreLabel.setTranslateY(Dimensions.HEALTH_BAR_SIZE + 2);
        
        healthBar = new Rectangle(Dimensions.SCREEN_WIDTH, Dimensions.HEALTH_BAR_SIZE, Color.WHITE);
        healthBar.setX(0);
        healthBar.setY(0);
        
        this.getChildren().addAll(highScoreLabel, scoreLabel, healthBar);
    }

    /**
     * Binds the score label to an IntegerProperty.
     * PreCondition: None.
     * PostCondition: The label's text is bound to the provided 
     * IntegerProperty's value.
     * 
     * @param score The IntegerProperty storing a value for the score.
     */
    public void bindScore(ReadOnlyIntegerProperty score) {
        scoreLabel.textProperty().bind(score.asString());
    }

    /**
     * Binds the high score label to an IntegerProperty.
     * PreCondition: None.
     * PostCondition: The label's text is bound to the provided 
     * IntegerProperty's value.
     * 
     * @param highScore The IntegerProperty storing a value for the high score.
     */
    public void bindHighScore(ReadOnlyIntegerProperty highScore) {
        highScoreLabel.textProperty().bind(new SimpleStringProperty("HIGH ").concat(highScore.asString()));
    }
    
    /**
     * Binds the health bar's size to an IntegerProperty.
     * PreCondition: None.
     * PostCondition: The label's text is bound to the provided 
     * IntegerProperty's value.
     * 
     * @param health The IntegerProperty storing a value for the player's health.
     */
    public void bindHealth(ReadOnlyIntegerProperty health) {
        healthBar.widthProperty().bind(health.multiply(Dimensions.SCREEN_WIDTH).divide(Health.PLAYER));
    }
    
    /**
     * Gets the GameEngine associated with this GamePane.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The GameEngine associated with this GamePane.
     */
    public GameEngine getEngine() {
        return engine;
    }
    
    /**
     * Associates a GameEngine with this GamePane.
     * PreCondition: None.
     * PostCondition: This GamePane has an associated GameEngine.
     * 
     * @param engine The engine to associate this GamePane with.
     */
    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }
}
