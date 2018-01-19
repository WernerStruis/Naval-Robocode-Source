package robocode.naval.interfaces.componentInterfaces;

import robocode.naval.Components.CannonComponents.BlindSpot;

import java.awt.*;

/**
 * This class contains all methods that are special to the cannon component
 * This is in addition to the base methods declared in IComponent
 */
public interface ICannonFunctions extends IMovementFunctions{

    /**
     * Returns a copy of the BlindSpot that the back cannon has.
     * The BlindSpot offers great utilities that will help you out working with a BlindSpot.
     * @return The BlindSpot of the back cannon.
     * @see BlindSpot#getFarLeft()		Furthest you can move to the left
     * @see BlindSpot#getFarRight()    	Furthest you can move to the right
     * @see BlindSpot#inBlindSpot(double) Returns whether the destination is within the BlindSpot
     */
    BlindSpot getCopyOfBlindSpot();

    /**
     * Returns true when the blindSpot has been reached for the backCannon
     * @return True when blindspot has been reached
     */
    boolean getAtBlindSpot();


    /**
     * Fires the a bullet/missile from the back cannon with the given power
     * @param power The power you want to shoot your bullet at. The value is a double between 0.1 and 3.0
     */
    void fire();

    /**
     * Sets the Bullet Color for the Back Cannon
     * @param color The Color you want the Bullets the Back Cannon shoots to be.
     */
    void setBulletColor(Color color);


}
