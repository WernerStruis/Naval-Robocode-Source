package robocode.naval.interfaces.componentInterfaces;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface IMovementFunctions {
    
    /**
     * Turns the component towards the left by the amount given in degrees.
     * The cannon will be stopped when it reaches its blindspot. TurnRemaining won't go to 0 when this happens.
     * @param angle The angle in degrees you want to rotate your component to the left.
     */
    void setTurnLeftDegrees(double angle);

    /**
     * Turns the component towards the right by the amount given in degrees.
     * The cannon will be stopped when it reaches its blindspot. TurnRemaining won't go to 0 when this happens.
     * @param angle The angle in degrees you want to rotate your component to the right.
     */
    void setTurnRightDegrees(double angle);
    
    /**
     * Turns the component towards the left by the amount given in radians.
     * The cannon will be stopped when it reaches its blindspot. TurnRemaining won't go to 0 when this happens.
     * @param angle The angle in radians you want to rotate your component to the left.
     */
    void setTurnLeftRadians(double angle);

    /**
     * Turns the component owards the right by the amount given in radians.
     * The cannon will be stopped when it reaches its blindspot. TurnRemaining won't go to 0 when this happens.
     * @param angle The angle in radians you want to rotate your component to the right.
     */
    void setTurnRightRadians(double angle);

    /**
     * Retrieves the components turn rate in degrees
     * @return turn rate in degrees
     */
    double getTurnRateDegrees();

    /**
     * Retrieves the components turn rate in radians
     * @return turn rate in radians
     */
    double getTurnRateRadians();

    /**
     * Returns the components heading in degrees
     * @return component heading in degrees
     */
    double getHeadingDegrees();

    /**
     * Returns the components heading in radians
     * @return component heading in radians
     */
    double getHeadingRadians();

    /**
     * Sets whether the component moves dependently from the Ship or not. Can only be called if the turnRemaining on the component equals 0.
     * THOMA_NOTE: Might change this in the future to work even if the turn remaining isn't 0.
     * @param index The index of the component
     * @param independent True for independent movement. False of dependent movement.
     */
    void setAdjustComponentForShipTurn(boolean independent);

    /**
     * Gets the remaining turn angle in degrees
     * @return Remaining turn in degrees
     */
    double getTurnRemainingDegrees();

    /**
     * Gets the remaining turn angle in radians
     * @return Remaining turn in radians
     */
    double getTurnRemainingRadians();
    
}
