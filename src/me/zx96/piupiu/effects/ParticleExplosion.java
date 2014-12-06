/*
 * This is a Group that stores Rectangles and generates transitions to animate 
 * those rectangles in a way resembling an explosion.
 */
package me.zx96.piupiu.effects;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/*
 * Jonathan Zentgraf
 * Michael Ondrasek
 * CS-1180-01 Lab 10
 * Matthew Flaute
 */
public class ParticleExplosion extends Group {
    
    private ArrayList<Transition> transitions = new ArrayList<>();
    private double duration = 0;
    
    /**
     * The default constructor.  You must use generateParticles to add particles to the new ParticleExplosion.
     * PreCondition: None.
     * PostCondition: A ParticleExplosion with no particles has been generated.
     */
    public ParticleExplosion() {}
    
    /**
     * Creates a new ParticleExplosion class with pre-generated particles.
     * PreCondition: None.
     * PostCondition: A new ParticleExplosion has been instantiated with pre-generated particles.
     * 
     * @param originX The starting X coordinate of the particles.
     * @param originY The starting Y coordinate of the particles.
     * @param radius The radius within which the particles can land.
     * @param duration The length (in milliseconds) of the animation.
     * @param numParticles The number of particles to generate.
     * @param particleSize The radius of the particles
     * @param color The color of the particles.
     */
    public ParticleExplosion(double originX, double originY, double radius, 
            double duration, int numParticles, double particleSize, VariableColor color) {
        generateParticles(originX, originY, radius, duration, numParticles, particleSize, color);
        this.duration = duration;
    }
    
    /**
     * Generates particles of the given VariableColor and creates animations for them.
     * PreCondition: None.
     * PostCondition: New particles have been generated and added to this ParticleExplosion.
     * 
     * @param originX The starting X coordinate of the particles.
     * @param originY The starting Y coordinate of the particles.
     * @param radius The radius within which the particles can land.
     * @param duration The length (in milliseconds) of the animation.
     * @param numParticles The number of particles to generate.
     * @param particleSize The radius of the particles
     * @param color The color of the particles.
     */
    public final void generateParticles(double originX, double originY, double radius, 
            double duration, int numParticles, double particleSize, VariableColor color) {
        for (int i = 0; i < numParticles; i++) {
            Rectangle particle = new Rectangle(originX, originY, particleSize, particleSize);
            particle.setFill(color.toColor());
            getChildren().add(particle);
            
            //Generate a Transition to animate it
            TranslateTransition translate = new TranslateTransition(Duration.millis(duration), particle);
            //Move to a random point in the circle defined by center (originX, originY) and radius radius
            double degree = Math.random() * 360; //Get an angle to move in
            translate.setToX(Math.random() * radius * Math.cos(degree)); //Move in that direction
            translate.setToY(Math.random() * radius * Math.sin(degree));
            
            //Generate a FadeTransition after the first tenth of the movement
            FadeTransition fade = new FadeTransition(Duration.millis(duration - (duration / 10)), particle);
            fade.setDelay(Duration.millis(duration / 10));
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            
            //Store the translate to play back
            transitions.add(translate);
            transitions.add(fade);
            
            //If the duration of these particles is higher than that of any 
            //other particles, reset the ParticleExplosion's duration
            if (duration > this.duration) this.duration = duration;
        }
    }

    /**
     * Gets the duration of the animation.
     * PreCondition: None.
     * PostCondition: None.
     * 
     * @return The length (in milliseconds) of the animation.
     */
    public double getDuration() {
        return duration;
    }
    
    /**
     * Plays back all of the transitions for the individual particles and 
     * removes this instance of ParticleExplosion.
     * PreCondition: We have set up our ParticleExplosion with generated particles and this resides within a Pane.
     * PostCondition: The particles have been animated and this instance has been removed from the parent Pane.
     */
    public void explode() {
        //Play the animations and destroy this instance of ParticleExplosion
        for (Transition transition : transitions) {
            transition.play();
        }
    }
}
