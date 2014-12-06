/*
 * This class provides a way of generating random colors within a range.
 */
package me.zx96.piupiu.effects;

import java.util.Random;
import javafx.scene.paint.Color;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class VariableColor {
    
    public static final VariableColor RED = new VariableColor(0, 0, 0.6, 1.0, 0.3, 0.9);
    public static final VariableColor GREEN = new VariableColor(130, 130, 0.6, 1.0, 0.3, 1.0);
    public static final VariableColor PINK = new VariableColor(5, 5, 0.6, 0.7, 0.3, 1.0);
    
    private static Random rand = new Random();
    
    private double minHue = 0, maxHue = 0;
    private double minSat = 0, maxSat = 0;
    private double minLum = 0, maxLum = 0;

    public VariableColor() {}

    public VariableColor(double minHue, double maxHue, 
            double minSat, double maxSat, double minLum, double maxLum) {
        this.minHue = minHue;
        this.maxHue = maxHue;
        this.minSat = minSat;
        this.maxSat = maxSat;
        this.minLum = minLum;
        this.maxLum = maxLum;
    }

    public double getMinHue() {
        return minHue;
    }

    public void setMinHue(double minHue) {
        this.minHue = minHue;
    }

    public double getMaxHue() {
        return maxHue;
    }

    public void setMaxHue(double maxHue) {
        this.maxHue = maxHue;
    }

    public double getMinSat() {
        return minSat;
    }

    public void setMinSat(double minSat) {
        this.minSat = minSat;
    }

    public double getMaxSat() {
        return maxSat;
    }

    public void setMaxSat(double maxSat) {
        this.maxSat = maxSat;
    }

    public double getMinLum() {
        return minLum;
    }

    public void setMinLum(double minLum) {
        this.minLum = minLum;
    }

    public double getMaxLum() {
        return maxLum;
    }

    public void setMaxLum(double maxLum) {
        this.maxLum = maxLum;
    }
    
    public void setHue(double hue) {
        this.maxHue = hue;
        this.minHue = hue;
    }
    
    public void setSat(double sat) {
        this.maxSat = sat;
        this.minSat = sat;
    }
    
    public void setLum(double lum) {
        this.maxLum = lum;
        this.minLum = lum;
    }
    
    public Color toColor() {
        double hue = ((rand.nextDouble() * (maxHue - minHue)) + minHue);
        double sat = ((rand.nextDouble() * (maxSat - minSat)) + minSat);
        double lum = ((rand.nextDouble() * (maxLum - minLum)) + minLum);
        return Color.hsb(hue, sat, lum);
    }
}
