package tested.ships;

import robocode.CorvetteShip;
import robocode.ScannedShipEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.RadarComponent;
import robocode.naval.Components.RadarComponents.ShortRangeRadar;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class ShortRangeRadarShip extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {

    public void run(){
        super.run();

        while(true){
            slot2().setTurnRightDegrees(360);
            System.out.println("{"+ slot2().getHeadingDegrees()+"}");
            execute();
        }
    }


    @Override
    public CannonComponent setSlot1() {
        return new SingleBarrelCannon();
    }

    @Override
    public RadarComponent setSlot2() {
        return new ShortRangeRadar();
    }

    @Override
    public CannonComponent setSlot3() {
        return new DoubleBarrelCannon();
    }

    @Override
    public void onScannedShip(ScannedShipEvent e) {
        System.out.println("SRRS>" + e.getDistance() + "<");
    }

}
