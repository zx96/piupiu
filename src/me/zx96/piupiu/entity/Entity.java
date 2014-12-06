/*
 * This class provides the base for most objects on the screen (Player, 
 * Projectiles, and Enemies).
 */
package me.zx96.piupiu.entity;

import me.zx96.piupiu.Resources;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public abstract class Entity extends ImageView {
    
    protected double width = 16;
    protected double height = 16;
    protected Image sprite = new Image(Resources.SPR_NULL, 
                                       width, height, false, false);
    
    /**
     * Constructs a new Entity with the null sprite and default size.
     * PreCondition: None.
     * PostCondition: A new Entity is created with a null sprite and default 
     * size.
     */
    public Entity() {
        super.setImage(sprite);
        setFitHeight(height);
        setFitWidth(width);
    }
    
    /**
     * Constructs a new Entity with the given sprite and default size.
     * PreCondition: None.
     * PostCondition: A new Entity is created with the given sprite and default 
     * size.
     * 
     * @param spriteName The name of the sprite to use.
     */
    public Entity(String spriteName) {
        sprite = new Image(spriteName, this.width, this.height, false, false);
        super.setImage(sprite);
        setFitHeight(height);
        setFitWidth(width);
    }
    
    
    /**
     * Constructs a new Entity with the given sprite and size.
     * PreCondition: None.
     * PostCondition: A new Entity is created with the given sprite and size.
     * 
     * @param spriteName The name of the sprite to use.
     * @param width The width of the Entity.
     * @param height The height of the Entity.
     */
    public Entity(String spriteName, double width, double height) {
        this.width = width;
        this.height = height;
        sprite = new Image(spriteName, width, height, false, false);
        super.setImage(sprite);
        setFitHeight(height);
        setFitWidth(width);
    }

    /**
     * Gets the width of the Entity.
     * PreCondition: None.
     * PostCondition: None.
     * @return The width of the Entity.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the Entity.
     * PreCondition: None.
     * PostCondition: None.
     * @return The height of the Entity.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the center x coordinate of the Entity.
     * PreCondition: None.
     * PostCondition: None.
     * @return The center x coordinate of the Entity.
     */
    public double getCenterX() {
        return getX() + (width / 2);
    }
    
    /**
     * Gets the center y coordinate of the Entity.
     * PreCondition: None.
     * PostCondition: None.
     * @return The center y coordinate of the Entity.
     */
    public double getCenterY() {
        return getY() + (height / 2);
    }
    
    /**
     * Processes events for this Entity.
     * PreCondition: None.
     * PostConditon: Varies.
     */
    public abstract void doTick();
}
