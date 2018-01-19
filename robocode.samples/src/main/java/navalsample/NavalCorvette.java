package navalsample;

import robocode.CorvetteShip;
import robocode.ScannedShipEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;
import robocode.util.Utils;

import java.awt.*;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class NavalCorvette extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent>{
    int directionFrontCannon = 1;
    int directionBackCannon = 1;
    double speed = 4;
    double radarHeading;

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

    public void run(){
        super.run();

        setBodyColor(Color.black);

        slot1().setAdjustComponentForShipTurn(true);
        slot2().setAdjustComponentForShipTurn(true);
        slot3().setAdjustComponentForShipTurn(true);

        slot1().setTurnRightDegrees(90);
        slot3().setTurnLeftDegrees(90);
        slot2().setTurnRightDegrees(90);
        while(slot3().getTurnRemainingRadians() != 0
                && slot2().getHeadingRadians() != 0
                && slot1().getTurnRemainingRadians() != 0){

            execute();
        }



        while(true){
            radarHeading = Utils.normalAbsoluteAngleDegrees(getBodyHeadingDegrees() - slot2().getHeadingDegrees());
            if(radarHeading > 90 && radarHeading < 270){
                slot1().setTurnLeftDegrees(speed);
                slot3().setTurnRightDegrees(speed);
            }
            else{
                slot3().setTurnLeftDegrees(speed);
                slot1().setTurnRightDegrees(speed);
            }
            slot2().setTurnRightDegrees(speed);
            execute();
        }

    }


    @Override
    public void onScannedShip(ScannedShipEvent e){
        System.out.printf("--SCANNED--\n" +
                "HEADING   : %f\n" +
                "RADAR     : %f\n" +
                "BEAR      : %f\n" +
                "RADARBEAR : %f", getBodyHeadingDegrees(), slot1().getHeadingDegrees(), e.getBearingDegrees(), slot1().getBearingDegrees(e));



        slot3().setTurnRightRadians(slot3().getBearingRadians(e));
        slot1().setTurnRightRadians(slot1().getBearingRadians(e));
        slot2().setTurnRightRadians(slot2().getBearingRadians(e));

        while(slot1().getTurnRemainingRadians() != 0 && slot2().getTurnRemainingRadians() !=0){
            execute();
        }
    }
}
