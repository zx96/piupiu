/*
 * This is the projectile fired by small enemies.
 */
package me.zx96.piupiu.entity;

import me.zx96.piupiu.Dimensions;
import me.zx96.piupiu.Health;
import me.zx96.piupiu.Resources;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class SmallEnemyProjectile extends Projectile implements EnemyProjectile {
    
    /**
     * Constructs a new SmallEnemyProjectile.
     * PreCondition: None.
     * PostCondition: A new SmallEnemyProjectile is constructed.
     */
    public SmallEnemyProjectile() {
        super(Resources.SPR_PROJECTILE_ENEMY_SMALL, Dimensions.PROJECTILE_ENEMY_SMALL_WIDTH, 
              Dimensions.PROJECTILE_ENEMY_SMALL_HEIGHT, Health.PROJECTILE_ENEMY_SMALL_DMG);
    }
    
    /**
     * Constructs a new SmallEnemyProjectile and sets its location and 
     * direction.
     * PreCondition: None.
     * PostCondition: A new SmallEnemyProjectile is constructed at the given 
     * coordinates and fired downward.
     * 
     * @param originX The starting X coordinate
     * @param originY The starting Y coordinate
     */
    public SmallEnemyProjectile(double originX, double originY) {
        super(Resources.SPR_PROJECTILE_ENEMY_SMALL, Dimensions.PROJECTILE_ENEMY_SMALL_WIDTH, 
              Dimensions.PROJECTILE_ENEMY_SMALL_HEIGHT, Health.PROJECTILE_ENEMY_SMALL_DMG,
              originX, originY, 0, 0);
    }
    
}
