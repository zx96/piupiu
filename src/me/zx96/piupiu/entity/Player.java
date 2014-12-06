/*
 * This is the Player that the user controls.
 */
package me.zx96.piupiu.entity;

import javafx.scene.image.Image;
import me.zx96.piupiu.GamePane;
import me.zx96.piupiu.Dimensions;
import me.zx96.piupiu.Health;
import me.zx96.piupiu.Resources;
import me.zx96.piupiu.Timing;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class Player extends Mob {
    
    private static Image leftSprite = new Image(Resources.SPR_PLAYER_LEFT, 
            Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT, false, false);
    private static Image rightSprite = new Image(Resources.SPR_PLAYER_RIGHT, 
            Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT, false, false);
    
    //The amount to move each tick
    protected double deltaX = Timing.PLAYER_DELTA_X;
    protected double deltaY = Timing.PLAYER_DELTA_Y;
    
    protected boolean movingLeft = false;
    protected boolean movingRight = false;
    protected boolean movingUp = false;
    protected boolean movingDown = false;
    
    /**
     * Constructs a new Player.
     * PreCondition: None.
     * PostCondition: A new Player is constructed.
     */
    public Player() {
        super(Resources.SPR_PLAYER, Dimensions.PLAYER_WIDTH, 
                Dimensions.PLAYER_HEIGHT, Health.PLAYER);
        //Place the player in the middle of the bottom row
        setInitialPosition();
    }
    
    /**
     * Places the player at the bottom-center of the screen.
     * PreCondition: None.
     * PostCondition: The Player is placed in the bottom-center of the screen.
     */
    private void setInitialPosition() {
        setX((Dimensions.SCREEN_WIDTH - Dimensions.PLAYER_WIDTH) / 2);
        setY(Dimensions.SCREEN_HEIGHT - Dimensions.PLAYER_HEIGHT);
    }
    
    /**
     * Sets the boolean value for movement in a given direction.
     * PreCondition: None.
     * PostCondition: A boolean value for movement is set.
     * 
     * @param dir The direction in which to start movement.
     */
    public void startMovement(Direction dir) {
        switch (dir) {
            case UP:
                movingUp = true; break;
            case DOWN:
                movingDown = true; break;
            case LEFT:
                movingLeft = true; break;
            case RIGHT:
                movingRight = true; break;
        }
    }
    
    /**
     * Clears the boolean value for movement in a given direction.
     * PreCondition: None.
     * PostCondition: A boolean value for movement is cleared.
     * 
     * @param dir The direction to stop moving in.
     */
    public void stopMovement(Direction dir) {
        switch (dir) {
            case UP:
                movingUp = false; break;
            case DOWN:
                movingDown = false; break;
            case LEFT:
                movingLeft = false; break;
            case RIGHT:
                movingRight = false; break;
        }
    }
    
    /**
     * Fires a player projectile upward from the current position.
     * PreCondition: None.
     * PostCondition: A projectile is created and queued for addition to the game.
     */
    public void fireProjectile() {
        ((GamePane)getParent()).getEngine().queueAddition(
            new PlayerProjectile(
                getCenterX() - (Dimensions.PROJECTILE_PLAYER_WIDTH / 2),
                getY() - Dimensions.PROJECTILE_PLAYER_HEIGHT
            )
        );
    }
    
    /**
     * Moves the Player according to the current moving boolean values.
     * PreCondition: The booleans are appropriately set.
     * PostCondition: The Player has shifted position.
     */
    protected void doMovement() {
        if (movingLeft ^ movingRight) { //Prevents a potential jiggle effect
            if (movingLeft) {
                //Prevent player from moving past left edge of game area
                if (getX() >= deltaX) setX(getX() - deltaX);
                else setX(0);
            }
            if (movingRight) {
                //Prevent player from moving past right edge of game area
                if (getX() < (Dimensions.SCREEN_WIDTH - width - deltaX))
                    setX(getX() + deltaX);
                else setX(Dimensions.SCREEN_WIDTH - width - deltaX);
            }
        }
        if (movingUp ^ movingDown) { //Prevents a potential jiggle effect
            if (movingUp) {
                //Prevent player from passing into the HUD area or above
                if (getY() >= (deltaY + height)) setY(getY() - deltaY);
                else setY(height);
            }
            if (movingDown) {
                //Prevent player from leaving the game area at the bottom
                if (getY() < (Dimensions.SCREEN_HEIGHT - height - deltaY))
                    setY(getY() + deltaY);
                else setY(Dimensions.SCREEN_HEIGHT - height - deltaY);
            }
        }
    }
    
    /**
     * Processes events for this Entity.
     * PreCondition: None.
     * PostConditon: This Player has moved and had a sprite set according to 
     * the direction in which it moved.
     */
    @Override
    public void doTick() {
        doMovement();
        //Set the sprite according to how we're moving
        if (movingLeft ^ movingRight) {
            if (movingLeft) setImage(leftSprite);
            if (movingRight) setImage(rightSprite);
        } else setImage(sprite);
    }
}
