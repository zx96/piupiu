/*
 * This is a Rectangle with a size linked to a parent Mob's health.
 */
package me.zx96.piupiu.entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class HealthBar extends Rectangle {
    
    /**
     * Constructs a new HealthBar linked to the given Mob.
     * PreCondition: None.
     * PostCondition: A new HealthBar is created for the given Mob.
     * 
     * @param mob The mob to bind properties to.
     */
    public HealthBar(Mob mob) {
        super();
        setWidth(mob.width);
        setHeight(2);
        setFill(Color.WHITE);
        xProperty().bind(mob.xProperty());
        yProperty().bind(mob.yProperty().subtract(2));
        widthProperty().bind(mob.healthPropertyUnmodifiable().multiply(mob.width).divide(mob.getMaxHealth()));
    }
}
