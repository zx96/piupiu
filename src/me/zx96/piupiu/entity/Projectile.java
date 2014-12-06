/*
 * This is the base class for all Projectiles.
 */
package me.zx96.piupiu.entity;

import me.zx96.piupiu.Resources;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class Projectile extends Entity {

    //The amount to move each tick
    protected double deltaX = 0.0;
    protected double deltaY = 0.0;
    protected int damage = 9999;
    
    /**
     * Constructs a new Projectile using default values.
     * PreCondition: None.
     * PostCondition: A new Projectile is created.
     */
    public Projectile() {
        super(Resources.SPR_NULL, 8, 8);
    }
    
    /**
     * Constructs a new Projectile using the provided sprite and damage value.
     * PreCondition: None.
     * PostCondition: A new Projectile is created using the provided data.
     * 
     * @param spriteName The name of the sprite to use.
     * @param damage The amount of damage the Projectile will cause.
     */
    public Projectile(String spriteName, int damage) {
        super(spriteName, 8, 8);
        this.damage = damage;
    }
    
    /**
     * Constructs a new Projectile using the provided sprite, size, and damage 
     * value.
     * PreCondition: None.
     * PostCondition: A new Projectile is created using the provided data.
     * 
     * @param spriteName The name of the sprite to use.
     * @param width The width of the Projectile
     * @param height The height of the Projectile
     * @param damage The amount of damage the Projectile will cause.
     */
    public Projectile(String spriteName, double width, double height, int damage) {
        super(spriteName, width, height);
        this.damage = damage;
    }
    
    /**
     * Constructs a new Projectile using the provided sprite, size, and damage 
     * value.
     * PreCondition: None.
     * PostCondition: A new Projectile is created using the provided data.
     * 
     * @param spriteName The name of the sprite to use.
     * @param width The width of the Projectile
     * @param height The height of the Projectile
     * @param damage The amount of damage the Projectile will cause.
     * @param originX The starting X coordinate of the Projectile.
     * @param originY The starting Y coordinate of the Projectile.
     * @param deltaX The distance by which to move the projectile horizontally 
     * on every tick.
     * @param deltaY The distance by which to move the projectile vertically 
     * on every tick.
     */
    public Projectile(String spriteName, double width, double height, int damage,
            double originX, double originY, double deltaX, double deltaY) {
        super(spriteName, width, height);
        this.damage = damage;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        setX(originX);
        setY(originY);
    }

    /**
     * Gets the delta of X for each tick.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The delta of X for each tick.
     */
    public double getDeltaX() {
        return deltaX;
    }

    /**
     * Sets the delta of X for each tick.
     * PreCondition: None.
     * PostCondition: The delta of X is set to the provided value.
     * 
     * @param deltaX The desired delta of X for each tick.
     */
    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    /**
     * Gets the delta of Y for each tick.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The delta of Y for each tick.
     */
    public double getDeltaY() {
        return deltaY;
    }

    /**
     * Sets the delta of Y for each tick.
     * PreCondition: None.
     * PostCondition: The delta of Y is set to the provided value.
     * 
     * @param deltaY The desired delta of Y for each tick.
     */
    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    /**
     * Gets the damage the Projectile will deal.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The damage value of this Projectile.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the damage the Projectile will deal.
     * PreCondition: None.
     * PostCondition: The Projectile has a new damage value.
     * 
     * @param damage The new damage value of this Projectile.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    /**
     * Moves the Projectile by its deltas.
     * PreCondition: None.
     * PostCondition: The Projectile has shifted position.
     */
    protected void doMovement() {
        setX(getX() - deltaX);
        setY(getY() - deltaY);
    }
    
    /**
     * Processes events for this Entity.
     * PreCondition: None.
     * PostConditon: This Projectile has moved.
     */
    @Override
    public void doTick() {
        doMovement();
    }
    
}
