/*
 * This file contains constants for the names of resources.
 */
package me.zx96.piupiu;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class Resources {
    public static final String BASE = "me/zx96/piupiu/res/";
    public static final String SPR_BASE = BASE + "img/";
    public static final String AUD_BASE = BASE + "audio/";
    
    //Sprites
    public static final String SPR_NULL = SPR_BASE + "null_sprite.png";
    
    public static final String SPR_PLAYER = SPR_BASE + "player.png";
    public static final String SPR_PLAYER_LEFT = SPR_BASE + "player_l.png";
    public static final String SPR_PLAYER_RIGHT = SPR_BASE + "player_r.png";
    
    public static final String SPR_ENEMY_SMALL = SPR_BASE + "enemy_sm.png";
    public static final String SPR_ENEMY_LARGE = SPR_BASE + "enemy_lg.png";
    
    public static final String SPR_PROJECTILE_PLAYER = SPR_BASE + "projectile_player.png";
    public static final String SPR_PROJECTILE_ENEMY_SMALL = SPR_BASE + "projectile_enemy_sm.png";
    public static final String SPR_PROJECTILE_ENEMY_LARGE = SPR_BASE + "projectile_enemy_lg.png";
    
    //Audio
    public static final String AUD_BGM = AUD_BASE + "bgm.mp3";
    public static final String AUD_SFX_EXPLOSION = AUD_BASE + "explode.mp3";
    
    //Font
    public static final String FONT = BASE + "PressStart2P.ttf";
    
    //Not actually in the res package, but this is a good file to put this in
    public static final String HIGH_SCORE_FILE = "highscore.txt";
}
