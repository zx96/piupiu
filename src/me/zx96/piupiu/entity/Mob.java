/*
 * The Mob is an Entity with health.
 */
package me.zx96.piupiu.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public abstract class Mob extends Entity {
    
    private IntegerProperty healthProperty = new SimpleIntegerProperty(100);
    private int maxHealth;
    private HealthBar healthBar;
    
    /**
     * Constructs a bare Mob with default values.
     * PreCondition: None.
     * PostCondition: A new Mob is created.
     */
    public Mob() {}
    
    /**
     * Constructs a bare Mob with health set.  Health is set to the maximum.
     * PreCondition: None.
     * PostCondition: A new Mob is created with the given maximum health.
     * 
     * @param maxHealth The maximum health.
     */
    public Mob(int maxHealth) {
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
        healthBar = new HealthBar(this);
    }
    
    /**
     * A new Mob is created with the given sprite and maximum health.  Health 
     * is set to the provided maximum.
     * PreCondition: None.
     * PostCondition: A new Mob is created with the given sprite and maximum 
     * health.
     * 
     * @param spriteName The sprite to use.
     * @param maxHealth The maximum health.
     */
    public Mob(String spriteName, int maxHealth) {
        super(spriteName);
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
        healthBar = new HealthBar(this);
    }
    
    /**
     * A new Mob is created with the given sprite, size, and maximum health.  
     * Health is set to the provided maximum.
     * PreCondition: None.
     * PostCondition: A new Mob is created with the given sprite, size, and 
     * maximum health.
     * 
     * @param spriteName The sprite to use.
     * @param width The width of the Mob.
     * @param height The height of the Mob.
     * @param maxHealth The maximum health.
     */
    public Mob(String spriteName, double width, double height, int maxHealth) {
        super(spriteName, width, height);
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
        healthBar = new HealthBar(this);
    }

    /**
     * Gets the maximum health of the Mob.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return This Mob's maximum health.
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    
    /**
     * Gets the Mob's current health as an integer value.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The Mob's health.
     */
    public int getHealth() {
        return healthProperty.get();
    }

    /**
     * Sets the Mob's current health.
     * PreCondition: None.
     * PostCondition: The Mob's health has been set to the given value.
     * 
     * @param health The new health value.
     */
    public void setHealth(int health) {
        healthProperty.set(health);
    }
    
    /**
     * Subtracts from the Mob's current health.
     * PreCondition: None.
     * PostCondition: The Mob's health is reduced by the given value.
     * 
     * @param health The amount of health to subtract.
     */
    public void subtractHealth(int health) {
        healthProperty.set(healthProperty.get() - health);
    }
    
    /**
     * Adds from the Mob's current health.
     * PreCondition: None.
     * PostCondition: The Mob's health is increased by the given value.
     * 
     * @param health The amount of health to add.
     */
    public void addHealth(int health) {
        healthProperty.set(healthProperty.get() - health);
    }
    
    /**
     * Returns the current health as an IntegerProperty.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The health IntegerProperty.
     */
    public ReadOnlyIntegerProperty healthPropertyUnmodifiable() {
        return healthProperty;
    }

    /**
     * Gets the HealthBar associated with this Mob.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The HealthBar associated with this Mob.
     */
    public HealthBar getHealthBar() {
        return healthBar;
    }
}
