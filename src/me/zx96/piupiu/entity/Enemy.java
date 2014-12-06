/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.zx96.piupiu.entity;

import me.zx96.piupiu.Dimensions;
import me.zx96.piupiu.Resources;

/**
 *
 * @author Jonathan
 */
public abstract class Enemy extends Mob {
    
    //The amount to move each tick
    protected double deltaX = 0.0;
    protected int scoreValue = 0;
    protected Direction dir = Direction.LEFT;
    
    public Enemy() {
        super(Resources.SPR_NULL, 32, 32, 1);
        setRandomInitialPosition();
    }
    
    public Enemy(String spriteName, int health, int scoreValue) {
        super(spriteName, 32, 32, health);
        setRandomInitialPosition();
        this.scoreValue = scoreValue;
    }
    
    public Enemy(String spriteName, double width, double height, int health, int scoreValue) {
        super(spriteName, width, height, health);
        setRandomInitialPosition();
        this.scoreValue = scoreValue;
    }
    
    public Enemy(String spriteName, double width, double height, int health, 
            int scoreValue, double deltaX, Direction dir) {
        super(spriteName, width, height, health);
        setRandomInitialPosition();
        this.scoreValue = scoreValue;
        this.deltaX = deltaX;
        this.dir = dir;
    }
    
    public void setRandomInitialPosition() {
        //Spawn at a random x coordinate within the GamePane
        setX(Math.random() * (Dimensions.SCREEN_WIDTH - width));
        //Spawn in rows on the top third of the screen, shifted down by one row
        setY(height * (int)(1 + Math.random() * Dimensions.SCREEN_HEIGHT / 3 / height));
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
    
    public void doMovement() {
        switch (dir) {
            case LEFT:
                if (getX() >= deltaX) setX(getX() - deltaX);
                else dir = Direction.RIGHT;
                break;
            case RIGHT:
                if (getX() < (Dimensions.SCREEN_WIDTH - width - deltaX))
                    setX(getX() + deltaX);
                else dir = Direction.LEFT;
        }
    }
    
    @Override
    public abstract void doTick();
}
