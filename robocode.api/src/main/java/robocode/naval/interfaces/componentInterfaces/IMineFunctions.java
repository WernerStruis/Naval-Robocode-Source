package robocode.naval.interfaces.componentInterfaces;

import robocode.Mine;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface IMineFunctions {

    /**
     * Places a mine with given firepower on the map
     * @param firePower desired firepower
     * @return Mineobject that is created with given firepower
     */
    Mine placeMine(double firePower);
}
