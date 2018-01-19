package tested.ships;

import robocode.Ship;
import robocode.naval.CannonType;
import robocode.naval.RadarType;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class InvalidShip extends Ship {

    public void run(){
        super.run();
    }


    @Override
    public double getXMiddle() {
        return 0;
    }



    @Override
    public double getYMiddle() {
        return 0;
    }
}
