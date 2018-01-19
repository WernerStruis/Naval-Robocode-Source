package tested.competition;

import robocode.CorvetteShip;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class CorvetteTestShip extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {

    public void run(){
        super.run();
        System.out.println(getX() + "-" + getY());

        if(getX() == 400){
            System.out.println("Corvette: Testing max velocity");
            setAhead(3000);
            execute();
        }else if(getX() == 450){
            System.out.println("Cruiser: Testing max turn rate");
            setAhead(3000);
            setTurnRightDegrees(360);
            execute();
        }else if(getX() == 500){
            System.out.println("Cruiser: Testing energy");
        }
    }


    @Override
    public CannonComponent setSlot1() {
        return new DoubleBarrelCannon();
    }

    @Override
    public RadarComponent setSlot2() {
        return new LongRangeRadar();
    }

    @Override
    public CannonComponent setSlot3() {
        return new SingleBarrelCannon();
    }
}
