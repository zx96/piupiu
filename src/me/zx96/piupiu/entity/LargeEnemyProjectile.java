/*
 * This is the projectile fired by LargeEnemies
 */
package me.zx96.piupiu.entity;

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
public class LargeEnemyProjectile extends Projectile implements EnemyProjectile {
    
    /**
     * Constructs a new LargeEnemyProjectile.
     * PreCondition: None.
     * PostCondition: A new LargeEnemyProjectile is constructed.
     */
    public LargeEnemyProjectile() {
        super(Resources.SPR_PROJECTILE_ENEMY_LARGE, Dimensions.PROJECTILE_ENEMY_LARGE_WIDTH, 
              Dimensions.PROJECTILE_ENEMY_LARGE_HEIGHT, Health.PROJECTILE_ENEMY_LARGE_DMG);
    }
    
    /**
     * Constructs a new LargeEnemyProjectile and sets its location and 
     * direction.
     * PreCondition: None.
     * PostCondition: A new LargeEnemyProjectile is constructed at the given 
     * coordinates and fired downward.
     * 
     * @param originX The starting X coordinate
     * @param originY The starting Y coordinate
     */
    public LargeEnemyProjectile(double originX, double originY) {
        super(Resources.SPR_PROJECTILE_ENEMY_LARGE, Dimensions.PROJECTILE_ENEMY_LARGE_WIDTH, 
              Dimensions.PROJECTILE_ENEMY_LARGE_HEIGHT, Health.PROJECTILE_ENEMY_LARGE_DMG,
              originX, originY, Timing.PROJECTILE_ENEMY_LARGE_DELTA_X,
              Timing.PROJECTILE_ENEMY_LARGE_DELTA_Y);
    }
    
}
