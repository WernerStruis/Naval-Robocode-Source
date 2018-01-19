package robocode.naval.interfaces.componentInterfaces;

import java.awt.*;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface IRadarFunctions extends IMovementFunctions {

    /**
     * Sets the scanArc color to the given color
     * @param color desired scanArc color
     */
    void setScanColor(Color color);

    /**
     * Returns the scan radius of the radar
     */
    int getScanRadius();
}
