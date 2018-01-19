package robocode.naval.interfaces.componentInterfaces;

import robocode.Mine;
import robocode.Missile;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface IMissileFunctions extends IMovementFunctions{

    /**
     * Places a mine with given firepower on the map
     * @param firePower desired firepower
     * @return Mineobject that is created with given firepower
     */
    Missile launch(double firePower);

    void setMissilePower(double energy, double power);
}
