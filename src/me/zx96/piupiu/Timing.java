/*
 * This file contains constants related to timing and speeds.
 */
package me.zx96.piupiu;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class Timing {
    //The time taken for one "tick" of the game engine (in ms)
    public static final double TICK_LENGTH = 10;
    
    //Entity speeds (in px/tick)
    public static final double PLAYER_DELTA_X = 2.0;
    public static final double PLAYER_DELTA_Y = 2.0;
    public static final double ENEMY_SMALL_DELTA_X = 3.0;
    public static final double ENEMY_LARGE_DELTA_X = 1.2;
    public static final double PROJECTILE_PLAYER_DELTA_X = 0.0;
    public static final double PROJECTILE_PLAYER_DELTA_Y = 3.0;
    public static final double PROJECTILE_ENEMY_LARGE_DELTA_X = 0.0;
    public static final double PROJECTILE_ENEMY_LARGE_DELTA_Y = -3.0;
    public static final double PROJECTILE_ENEMY_SMALL_DELTA = 1.0;
    
    //Adjust frequency of enemy actions
    public static final double ENEMY_LARGE_REVERSE_CHANCE = 0.01;
    public static final double ENEMY_SPAWN_CHANCE = 0.005;
    //Relative percentages of each enemy (0-1)
    public static final double SMALL_ENEMY_PROPORTION = 0.7;
    public static final double LARGE_ENEMY_PROPORTION = 0.7;
    
    public static final int ENEMY_SMALL_FIRE_RATE = 400;
    public static final int ENEMY_LARGE_FIRE_RATE = 600;
}
