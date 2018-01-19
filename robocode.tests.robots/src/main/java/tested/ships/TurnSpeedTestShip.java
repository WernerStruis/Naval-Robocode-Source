package tested.ships;

import robocode.CorvetteShip;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class TurnSpeedTestShip extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {

    public void run(){
        super.run();
        slot1().setTurnRightDegrees(360);
        slot3().setTurnRightDegrees(360);
        execute();
        while (true) {

            System.out.printf(">%f<*>%f<\n", slot1().getHeadingDegrees(), slot3().getHeadingDegrees());
            execute();
        }
    }

    @Override
    public CannonComponent setSlot1() {
        return new SingleBarrelCannon();
    }

    @Override
    public RadarComponent setSlot2() {
        return new LongRangeRadar();
    }

    @Override
    public CannonComponent setSlot3() {
        return new DoubleBarrelCannon();
    }


}
