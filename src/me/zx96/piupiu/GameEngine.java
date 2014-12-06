/*
 * This class represents the engine which drives every event in the game.
 */
package me.zx96.piupiu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import me.zx96.piupiu.effects.ParticleExplosion;
import me.zx96.piupiu.effects.VariableColor;
import me.zx96.piupiu.entity.Direction;
import me.zx96.piupiu.entity.Enemy;
import me.zx96.piupiu.entity.EnemyProjectile;
import me.zx96.piupiu.entity.Entity;
import me.zx96.piupiu.entity.LargeEnemy;
import me.zx96.piupiu.entity.Mob;
import me.zx96.piupiu.entity.Player;
import me.zx96.piupiu.entity.PlayerProjectile;
import me.zx96.piupiu.entity.Projectile;
import me.zx96.piupiu.entity.SmallEnemy;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class GameEngine {
    
    private GamePane pane;
    private Scene scene;
    
    //Timelines to fire events at regular intervals
    private Timeline gameLoop;
    private Timeline smallEnemyFireLoop;
    private Timeline largeEnemyFireLoop;
    
    //IntegerProperties (to permit binding) for scores
    private IntegerProperty score = new SimpleIntegerProperty(0);
    private IntegerProperty highScore = new SimpleIntegerProperty(0);
    
    //Sound
    private MediaPlayer bgm = new MediaPlayer(
            new Media(ClassLoader.getSystemResource(Resources.AUD_BGM).toExternalForm()));
    private MediaPlayer sfxExplode = new MediaPlayer(
            new Media(ClassLoader.getSystemResource(Resources.AUD_SFX_EXPLOSION).toExternalForm()));
    
    //Entities
    private Player player = new Player();
    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    
    //We can't add or remove objects while iterating, so make queues
    ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    ArrayList<Entity> entitiesToRemove = new ArrayList<>();
    ArrayList<ParticleExplosion> explosionsToAdd = new ArrayList<>();
    ArrayList<ParticleExplosion> explosionsToRemove = new ArrayList<>();
    
    public GameEngine() {
        //Set up the Pane and Scene
        pane = new GamePane();
        scene = new Scene(pane, Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        setupScene(pane);
        //Key bindings
        setupKeybinds();
        //Load the high score from file if applicable
        highScore.set(loadHighScore());
        
        //Add the Player
        add(player);
        
        //Set the explosion sound effect to reset after playing
        sfxExplode.setOnEndOfMedia(() -> sfxExplode.stop());
        //And keep it from being so freaking loud
        sfxExplode.setVolume(0.3);
        
        //Play the BGM and start up the Timelines
        bgm.setCycleCount(MediaPlayer.INDEFINITE);
        bgm.play();
        setupTimelines();
        
        //Begin the game by showing the help
        displayHelp();
    }
    
    /**
     * Configures the GamePane for use and sets the Scene to display it.
     * PreCondition: None.
     * PostCondition: The Scene is displaying a configured GamePane.
     * 
     * @param pane An empty GamePane to be configured for use by the GameEngine.
     */
    private void setupScene(GamePane pane) {
        this.pane = pane;
        pane.bindScore(score);
        pane.bindHighScore(highScore);
        pane.setEngine(this);
        
        scene.setRoot(pane);
        
        /* Uncomment me to get a button
        javafx.scene.control.Button button = new javafx.scene.control.Button("Fire!");
        button.setOnAction(e -> player.fireProjectile());
        button.setLayoutX(Dimensions.SCREEN_WIDTH - 40);
        button.setLayoutY(Dimensions.SCREEN_HEIGHT - 28);
        button.setFocusTraversable(false);
        pane.getChildren().add(button);
        */
    }
    
    /**
     * Sets up key bindings for the game.
     * PreCondition: A Scene has been created.
     * PostCondition: The Scene has configured key bindings.
     */
    private void setupKeybinds() {
        scene.setOnKeyPressed(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        player.startMovement(Direction.LEFT); break;
                    case RIGHT:
                    case D:
                        player.startMovement(Direction.RIGHT); break;
                    case UP:
                    case W:
                        player.startMovement(Direction.UP); break;
                    case DOWN:
                    case S:
                        player.startMovement(Direction.DOWN); break;
                    case SPACE:
                        player.fireProjectile(); break;
                    case ESCAPE:
                        displayPaused(); break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Tried to move player, but player does not exist.");
            }
        });
        scene.setOnKeyReleased(e -> {
            try {
                switch (e.getCode()) {
                    case LEFT:
                    case A:
                        player.stopMovement(Direction.LEFT); break;
                    case RIGHT:
                    case D:
                        player.stopMovement(Direction.RIGHT); break;
                    case UP:
                    case W:
                        player.stopMovement(Direction.UP); break;
                    case DOWN:
                    case S:
                        player.stopMovement(Direction.DOWN); break;
                }
            } catch (NullPointerException ex) {
                System.err.println("Tried to stop player, but player does not exist.");
            }
        });
    }
    
    /**
     * Adds an Entity to the GamePane and to GameEngine's state variables.
     * PreCondition: Nothing is currently modifying or iterating over the 
     * GamePane's children or the GameEngine's ArrayLists.
     * PostCondition: A new Entity is added to the GamePane and GameEngine.
     * 
     * @param entity The entity to add.
     */
    private void add(Entity entity) {
        entities.add(entity);
        if (entity instanceof Player) {
            player = (Player)entity;
            pane.bindHealth(player.healthPropertyUnmodifiable());
        }
        if (entity instanceof Enemy) {
            enemies.add((Enemy)entity);
            pane.getChildren().add(((Enemy)entity).getHealthBar());
        }
        if (entity instanceof Projectile) projectiles.add((Projectile)entity);
        pane.getChildren().add(entity);
    }
    
    /**
     * Adds a ParticleExplosion to the GamePane.
     * PreCondition: Nothing is currently modifying or iterating over the 
     * GamePane's children.
     * PostCondition: A new ParticleExplosion is added to the GamePane.
     * 
     * @param explosion The ParticleExplosion to add.
     */
    private void add(ParticleExplosion explosion) {
        pane.getChildren().add(explosion);
    }
    
    /**
     * Removes an Entity from the GamePane and from GameEngine's state variables.
     * PreCondition: Nothing is currently modifying or iterating over the 
     * GamePane's children or the GameEngine's ArrayLists.
     * PostCondition: The entity is removed from the GamePane and GameEngine.
     * 
     * @param entity The entity to remove.
     */
    private void remove(Entity entity) {
        //Enemies also have associated HealthBars to be added
        if (entity instanceof Enemy)
            pane.getChildren().remove(((Enemy)entity).getHealthBar());
        entities.remove(entity);
        //Remove from the other ArraryLists as well
        //No harm done if it isn't in one of these
        enemies.remove(entity);
        projectiles.remove(entity);
        pane.getChildren().remove(entity);
    }
    
    /**
     * Removes a ParticleExplosion from the GamePane.
     * PreCondition: Nothing is currently modifying or iterating over the 
     * GamePane's children.
     * PostCondition: The ParticleExplosion is removed from the GamePane.
     * 
     * @param explosion The ParticleExplosion to remove.
     */
    private void remove(ParticleExplosion explosion) {
        pane.getChildren().remove(explosion);
    }
    
    /**
     * Queues the addition of a new Entity to the game.
     * PreCondition: None.
     * PostCondition: A new Entity is queued for addition.
     * 
     * @param entity The Entity to add to the game.
     */
    public void queueAddition(Entity entity) {
        entitiesToAdd.add(entity);
    }
    
    /**
     * Queues the addition of a new ParticleExplosion to the game.
     * PreCondition: None.
     * PostCondition: A new ParticleExplosion is queued for addition.
     * 
     * @param explosion The ParticleExplosion to add to the game.
     */
    public void queueAddition(ParticleExplosion explosion) {
        explosionsToAdd.add(explosion);
    }
    
    /**
     * Queues the removal of an existing Entity from the game.
     * PreCondition: None.
     * PostCondition: A new Entity is queued for removal.
     * 
     * @param entity The Entity to remove from the game.
     */
    public void queueRemoval(Entity entity) {
        entitiesToRemove.add(entity);
    }
    
    /**
     * Queues the removal of an existing ParticleExplosion from the game.
     * PreCondition: None.
     * PostCondition: A new ParticleExplosion is queued for removal.
     * 
     * @param explosion The ParticleExplosion remove from to the game.
     */
    public void queueRemoval(ParticleExplosion explosion) {
        explosionsToRemove.add(explosion);
    }
    
    /**
     * Queues a ParticleExplosion for addition and later removal and starts its 
     * animation.
     * PreCondition: None.
     * PostCondition: A ParticleExplosion has been played back.
     * 
     * @param explosion 
     */
    public void playExplosion(ParticleExplosion explosion) {
        queueAddition(explosion);
        //Set a timer to remove the explosion after playing
        Timeline removalDelay = new Timeline(new KeyFrame(
                Duration.millis(explosion.getDuration()), e -> {
                    queueRemoval(explosion);
                }
        ));
        removalDelay.play();
        //Play the explosion
        explosion.explode();
    }
    
    //We need to expose the Player's coordinates for SmallEnemy's use
    
    /**
     * Returns the X coordinate of the Player.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The Player's X coordinate.
     */
    public double getPlayerX() {
        return player.getX();
    }
    
    /**
     * Returns the Y coordinate of the Player.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The Player's Y coordinate.
     */
    public double getPlayerY() {
        return player.getY();
    }
    
    /**
     * Returns the GamePane associated with this GameEngine.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The GamePane.
     */
    public GamePane getPane() {
        return pane;
    }
    
    /**
     * Returns the Scene associated with this GameEngine's GamePane.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The Scene.
     */
    public Scene getScene() {
        return scene;
    }
    
    /**
     * Writes the high score out to the high score file.
     * PreCondition: None.
     * PostCondition: The high score is written to the high score file.
     */
    private void saveHighScore() {
        File file = new File(Resources.HIGH_SCORE_FILE);
        try {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(highScore.getValue());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't write high score file.");
        }
    }
    
    /**
     * Loads the high score from the high score file, if it exists.
     * PreCondition: None.
     * PostCondition: The high score is loaded from the high score file.
     * 
     * @return 0 if the file is invalid or nonexistent, the high score otherwise.
     */
    private int loadHighScore() {
        File file = new File(Resources.HIGH_SCORE_FILE);
        try {
            try (Scanner scanner = new Scanner(file)) {
                return scanner.nextInt();
            }
        } catch (InputMismatchException ex) {
            System.err.println("High score file invalid.");
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't open high score file.");
        }
        return 0;
    }
    
    /**
     * Pauses all game events.
     * PreCondition: The game is running.
     * PostCondition: The game is paused.
     */
    private void pause() {
        gameLoop.pause();
        smallEnemyFireLoop.pause();
        largeEnemyFireLoop.pause();
        bgm.pause();
    }
    
    /**
     * Resumes the game from a paused or stopped state.
     * PreCondition: The game is paused or stopped.
     * PostCondition: The game is resumed.
     */
    private void play() {
        gameLoop.play();
        smallEnemyFireLoop.play();
        largeEnemyFireLoop.play();
        bgm.play();
    }
    
    /**
     * Stops all game events.
     * PreCondition: The game is running or paused.
     * PostCondition: The game is stopped.
     */
    private void stop() {
        gameLoop.stop();
        smallEnemyFireLoop.stop();
        largeEnemyFireLoop.stop();
        bgm.stop();
    }
    
    /**
     * Resets the game to a clean state from a stopped state and starts it.
     * PreCondition: The game is stopped.
     * PostCondition: The game has been restarted in a clean state.
     */
    private void restart() {
        //Clear out the ArrayLists
        entities.clear();
        entitiesToAdd.clear();
        entitiesToRemove.clear();
        enemies.clear();
        projectiles.clear();
        
        //Add a new Player
        queueAddition(new Player());
        
        setupScene(new GamePane());
        
        //Reset the score
        score.set(0);
        
        //Restart the timelines and BGM
        play();
    }
    
    /**
     * Pauses the game and displays information about the game in the 
     * GamePane.
     * PreCondition: None.
     * PostCondition: Help is displayed and the game will play on any key.
     */
    private void displayHelp() {
        pause();
        
        Label helpText = new Label(
            "Welcome to Piu Piu!\n\n"
            
          + "In this game, you are the sole surviving member of the latest battle in this long war.\n"
          + "You are swarmed by your alien foes, but you cannot give up hope - you must defeat as many as possible. "
          + "You must weaken them so that humanity will have a chance at emerging victorious.\n\n"

          + "Each larva you kill is worth 100 points, and each adult, 500. "
          + "Your ship's health is shown as a bar on the top, and the aliens' healths are shown above their bodies.\n"
          + "Hold them off, and NEVER STOP SHOOTING.\n\n"
                    
          + "We can win.\n\n"
                    
          + "...Some day...\n\n"
          
          + "Use the arrow keys or WASD to move.\n"
          + "Press Space to fire a plasma ball.\n"
          + "Press Esc to pause or end the game.\n\n"
            
          + "Press any key to continue."
        );
        
        //Display the text
        helpText.setFont(Font.loadFont(ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_NORMAL));
        helpText.setMinSize(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        helpText.setMaxSize(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        helpText.setAlignment(Pos.CENTER);
        helpText.setTextAlignment(TextAlignment.CENTER);
        helpText.setWrapText(true);
        helpText.setTextFill(Color.WHITE);
        
        //On a shaded background
        Rectangle background = new Rectangle(Dimensions.SCREEN_WIDTH, 384, 
                new Color(0, 0, 0, 0.6));
        background.setX(0);
        background.setY((Dimensions.SCREEN_HEIGHT / 2) - 192);
        
        pane.getChildren().addAll(background, helpText);
        
        //Bind every key to play play
        background.setOnKeyPressed(e -> {
            pane.getChildren().removeAll(background, helpText);
            play();
        });
        background.requestFocus();
    }
    
    /**
     * Stops the game and displays a game over banner.  Sets a key binding for 
     * R to restart the game.
     * PreCondition: The game is running.
     * PostCondition: The game is stopped and a banner is displayed.
     */
    private void gameOver() {
        //Stop all looping events
        stop();
        
        //Write out the high score
        saveHighScore();
        
        //Display "GAME OVER" in the center over a translucent bar
        Label goLabel = new Label("GAME OVER");
        goLabel.setFont(Font.loadFont(ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_NORMAL));
        goLabel.setMinSize(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        goLabel.setAlignment(Pos.CENTER);
        goLabel.setTranslateY(-2);
        goLabel.setTextAlignment(TextAlignment.CENTER);
        goLabel.setTextFill(Color.WHITE);
        
        //Add "Press R to restart"
        Label restartLabel = new Label("Press R to restart");
        restartLabel.setFont(Font.loadFont(
                ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_SMALL));
        restartLabel.setMinSize(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        restartLabel.setAlignment(Pos.CENTER);
        restartLabel.setTranslateY(10);
        restartLabel.setTextAlignment(TextAlignment.CENTER);
        restartLabel.setTextFill(Color.WHITE);
        
        //And a translucent background for the text
        Rectangle background = new Rectangle(Dimensions.SCREEN_WIDTH, 30, 
                new Color(0, 0, 0, 0.6));
        background.setX(0);
        background.setY((Dimensions.SCREEN_HEIGHT / 2) - 12);
        
        //Display the background and labels
        pane.getChildren().addAll(background, goLabel, restartLabel);
        //Bind R to restart
        background.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case R:
                    restart(); break;
                case ESCAPE:
                    Platform.exit(); break;
            }
        });
        background.requestFocus();
    }
    
    /**
     * Stops the game and displays a game over banner.  Sets a key binding for 
     * R to restart the game.
     * PreCondition: The game is running.
     * PostCondition: The game is stopped and a banner is displayed.
     */
    private void displayPaused() {
        //Pauses all looping events
        pause();
        
        //Display "PAUSED" in the center over a translucent bar
        Label goLabel = new Label("PAUSED");
        goLabel.setFont(Font.loadFont(ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_NORMAL));
        goLabel.setMinSize(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        goLabel.setAlignment(Pos.CENTER);
        goLabel.setTranslateY(-2);
        goLabel.setTextAlignment(TextAlignment.CENTER);
        goLabel.setTextFill(Color.WHITE);
        
        //Add "Press Space to resume"
        Label resumeLabel = new Label("Press Space to resume");
        resumeLabel.setFont(Font.loadFont(
                ClassLoader.getSystemResource(Resources.FONT).toExternalForm(), 
                Dimensions.FONT_SIZE_SMALL));
        resumeLabel.setMinSize(Dimensions.SCREEN_WIDTH, Dimensions.SCREEN_HEIGHT);
        resumeLabel.setAlignment(Pos.CENTER);
        resumeLabel.setTranslateY(10);
        resumeLabel.setTextAlignment(TextAlignment.CENTER);
        resumeLabel.setTextFill(Color.WHITE);
        
        //And a translucent background for the text
        Rectangle background = new Rectangle(Dimensions.SCREEN_WIDTH, 30, 
                new Color(0, 0, 0, 0.6));
        background.setX(0); 
        background.setY((Dimensions.SCREEN_HEIGHT / 2) - 12);
        
        //Display the background and labels
        pane.getChildren().addAll(background, goLabel, resumeLabel);
        //Bind Space to resume
        background.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE:
                    pane.getChildren().removeAll(background, goLabel, resumeLabel);
                    play(); break;
                case ESCAPE:
                    Platform.exit(); break;
            }
        });
        background.requestFocus();
    }
    
    /**
     * Sets up the game logic within Timelines and plays them.
     * PreCondition: Player is not null.
     * PostCondition: The game logic has been set up and the loops have been 
     * started.
     */
    private void setupTimelines() {
        
        gameLoop = new Timeline(new KeyFrame(Duration.millis(Timing.TICK_LENGTH), e -> {
            //Tick for every entity
            for (Entity entity : entities) {
                entity.doTick();
                
                //Check if the entity's health is zero
                if (entity instanceof Enemy) {
                    if (((Enemy)entity).getHealth() <= 0) {
                        //Update the score
                        score.set(score.get() + ((Enemy)entity).getScoreValue());
                        if (score.get() > highScore.get()) highScore.set(score.get());
                        
                        //Make a new ParticleExplosion at the Enemy's position
                        ParticleExplosion explosion = new ParticleExplosion();
                        explosion.generateParticles(entity.getCenterX(), 
                            entity.getCenterY(), 120, 600, 200, 2, VariableColor.GREEN);
                        explosion.generateParticles(entity.getCenterX(), 
                            entity.getCenterY(), 120, 500, 120, 2, VariableColor.RED);
                        sfxExplode.play();

                        //Queue the Entity for removal and play the ParticleExplosion
                        playExplosion(explosion);
                        queueRemoval(entity);
                    }
                    
                    //Randomly reverse large enemies
                    if (entity instanceof LargeEnemy) {
                        if (Math.random() < Timing.ENEMY_LARGE_REVERSE_CHANCE)
                            ((LargeEnemy)entity).reverseDirection();
                    }
                }
                if (entity instanceof Player) {
                    //Display "GAME OVER" if player died
                    if (((Mob)entity).getHealth() <= 0) {
                        gameOver();
                        
                        //Make a new ParticleExplosion at the Enemy's position
                        ParticleExplosion explosion = new ParticleExplosion();
                        explosion.generateParticles(entity.getCenterX(), 
                            entity.getCenterY(), 120, 600, 200, 2, VariableColor.PINK);
                        explosion.generateParticles(entity.getCenterX(), 
                            entity.getCenterY(), 120, 500, 450, 2, VariableColor.RED);
                        sfxExplode.play();

                        //Play the ParticleExplosion
                        playExplosion(explosion);
                        queueRemoval(entity);
                    }
                }
            }
            
            //Randomly spawn a new enemy
            if (Math.random() < Timing.ENEMY_SPAWN_CHANCE) {
                if (Math.random() < Timing.SMALL_ENEMY_PROPORTION) queueAddition(new SmallEnemy());
                else queueAddition(new LargeEnemy());
            }
            
            //Detect collisions and remove any Projectiles that went outside the GamePane
            for (Projectile projectile : projectiles) {
                //PlayerProjectiles colliding with Enemies
                if (projectile instanceof PlayerProjectile) {
                    for (Enemy enemy : enemies) {
                        if (projectile.intersects(enemy.getX(), enemy.getY(),
                                enemy.getWidth(), enemy.getHeight())) {
                            enemy.subtractHealth(projectile.getDamage());
                            queueRemoval(projectile);
                        }
                    }
                }
                //EnemyProjectiles colliding with the Player
                if (projectile instanceof EnemyProjectile
                        && projectile.intersects(player.getX(), player.getY(),
                        player.getWidth(), player.getHeight())) {
                    player.subtractHealth(projectile.getDamage());
                    queueRemoval(projectile);
                }
                //Remove any that are outside the play area
                if (projectile.getY() < -projectile.getHeight() 
                        || projectile.getY() > Dimensions.SCREEN_HEIGHT)
                    queueRemoval(projectile);
            }
            
            //Remove Entities queued for removal
            for (Entity entity : entitiesToRemove) {
                remove(entity);
            }
            //And clear the list to start fresh next cycle
            entitiesToRemove.clear();
            //Same thing for those queued for addition
            for (Entity entity : entitiesToAdd) {
                add(entity);
            }
            entitiesToAdd.clear();
            
            //And explosions as well
            for (ParticleExplosion explosion : explosionsToRemove) {
                remove(explosion);
            }
            explosionsToRemove.clear();
            for (ParticleExplosion explosion : explosionsToAdd) {
                add(explosion);
            }
            explosionsToAdd.clear();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
        
        smallEnemyFireLoop = new Timeline(new KeyFrame(Duration.millis(Timing.ENEMY_SMALL_FIRE_RATE), e -> {
            for (Entity entity : entities) {
                if (entity instanceof SmallEnemy) ((SmallEnemy)entity).fireProjectile();
            }
        }));
        smallEnemyFireLoop.setCycleCount(Timeline.INDEFINITE);
        smallEnemyFireLoop.play();
        
        largeEnemyFireLoop = new Timeline(new KeyFrame(Duration.millis(Timing.ENEMY_LARGE_FIRE_RATE), e -> {
            for (Entity entity : entities) {
                if (entity instanceof LargeEnemy) ((LargeEnemy)entity).fireProjectile();
            }
        }));
        largeEnemyFireLoop.setCycleCount(Timeline.INDEFINITE);
        largeEnemyFireLoop.play();
    }
    
}
