/*
 * This is the small enemy (alien larva).
 */
package me.zx96.piupiu.entity;

import me.zx96.piupiu.GameEngine;
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
public class SmallEnemy extends Enemy {
    
    /**
     * Constructs a SmallEnemy.
     * PreCondition: None.
     * PostCondition: A new SmallEnemy is constructed.
     */
    public SmallEnemy() {
        super(Resources.SPR_ENEMY_SMALL, Dimensions.ENEMY_SMALL_WIDTH, Dimensions.ENEMY_SMALL_HEIGHT,
                Health.ENEMY_SMALL, ScoreValues.ENEMY_SMALL, Timing.ENEMY_SMALL_DELTA_X, 
                (Math.random() < 0.5 ? Direction.LEFT : Direction.RIGHT));
    }
    
    /**
     * Fires a small enemy projectile toward the player's current position.
     * PreCondition: None.
     * PostCondition: A projectile is created and queued for addition to the game.
     */
    public void fireProjectile() {
        GameEngine engine = ((GamePane)getParent()).getEngine();
        SmallEnemyProjectile projectile = new SmallEnemyProjectile(
            (getX() + (width / 2)), (getY() + height));
        
        double dx = projectile.getX() - engine.getPlayerX();
        double dy = projectile.getY() - engine.getPlayerY();
        double angle = Math.atan(dx/dy);
        
        projectile.setDeltaX(-Timing.PROJECTILE_ENEMY_SMALL_DELTA * Math.sin(angle));
        projectile.setDeltaY(-Timing.PROJECTILE_ENEMY_SMALL_DELTA * Math.cos(angle));
        engine.queueAddition(projectile);
    }
    
    /**
     * Processes events for this Entity.
     * PreCondition: None.
     * PostConditon: This SmallEnemy has moved.
     */
    @Override
    public void doTick() {
        doMovement();
    }
}
