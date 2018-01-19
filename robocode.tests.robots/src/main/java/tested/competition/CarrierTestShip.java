package tested.competition;

import robocode.CarrierShip;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class CarrierTestShip extends CarrierShip<CannonComponent, RadarComponent, MineComponent, CannonComponent> {

    public void run(){
        super.run();
        System.out.println(getX() + "-" + getY());

        if(getX() == 400){
            System.out.println("Carrier: Testing max velocity");
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
    public MineComponent setSlot3() {
        return new MineComponent();
    }

    @Override
    public CannonComponent setSlot4() {
        return new SingleBarrelCannon();
    }

}
