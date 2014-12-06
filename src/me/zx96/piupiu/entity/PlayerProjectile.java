/*
 * This is the projectile fired by the Player.
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
public class PlayerProjectile extends Projectile {
    
    /**
     * Constructs a new PlayerProjectile.
     * PreCondition: None.
     * PostCondition: A new PlayerProjectile is constructed.
     */
    public PlayerProjectile() {
        super(Resources.SPR_PROJECTILE_PLAYER, Dimensions.PROJECTILE_PLAYER_WIDTH, 
              Dimensions.PROJECTILE_PLAYER_HEIGHT, Health.PROJECTILE_PLAYER_DMG);
    }
    
    /**
     * Constructs a new PlayerProjectile and sets its location and 
     * direction.
     * PreCondition: None.
     * PostCondition: A new PlayerProjectile is constructed at the given 
     * coordinates and fired downward.
     * 
     * @param originX The starting X coordinate
     * @param originY The starting Y coordinate
     */
    public PlayerProjectile(double originX, double originY) {
        super(Resources.SPR_PROJECTILE_PLAYER, Dimensions.PROJECTILE_PLAYER_WIDTH, 
              Dimensions.PROJECTILE_PLAYER_HEIGHT, Health.PROJECTILE_PLAYER_DMG,
              originX, originY, Timing.PROJECTILE_PLAYER_DELTA_X,
              Timing.PROJECTILE_PLAYER_DELTA_Y);
    }
}
