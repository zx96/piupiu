/*
 * This is the large enemy (adult alien).
 */
package me.zx96.piupiu.entity;

import me.zx96.piupiu.GamePane;
import me.zx96.piupiu.Dimensions;
import me.zx96.piupiu.Health;
import me.zx96.piupiu.ScoreValues;
import me.zx96.piupiu.Resources;
import me.zx96.piupiu.Timing;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class LargeEnemy extends Enemy {
    
    /**
     * Constructs a new LargeEnemy.
     * PreCondition: None.
     * PostConditon: A new LargeEnemy is constructed.
     */
    public LargeEnemy() {
        super(Resources.SPR_ENEMY_LARGE, Dimensions.ENEMY_LARGE_WIDTH, Dimensions.ENEMY_LARGE_HEIGHT, 
                Health.ENEMY_LARGE, ScoreValues.ENEMY_LARGE, Timing.ENEMY_LARGE_DELTA_X, 
                (Math.random() < 0.5 ? Direction.LEFT : Direction.RIGHT));
    }
    
    /**
     * Reverses the direction of this LargeEnemy's movement.
     * PreCondition: None.
     * PostCondition: This LargeEnemy will now be moving in the opposite 
     * direction.
     */
    public void reverseDirection() {
        switch (dir) {
            case LEFT:
                dir = Direction.RIGHT; break;
            case RIGHT:
                dir = Direction.LEFT; break;
        }
    }
    
    /**
     * Fires a large enemy projectile downward from the current position.
     * PreCondition: None.
     * PostCondition: A projectile is created and queued for addition to the game.
     */
    public void fireProjectile() {
        ((GamePane)getParent()).getEngine().queueAddition(
            new LargeEnemyProjectile(getX() + 5, getY() + height));
        ((GamePane)getParent()).getEngine().queueAddition(
            new LargeEnemyProjectile(getX() + 54, getY() + height));
    }
    
    /**
     * Processes events for this Entity.
     * PreCondition: None.
     * PostConditon: This LargeEnemy has moved.
     */
    @Override
    public void doTick() {
        doMovement();
    }
}
