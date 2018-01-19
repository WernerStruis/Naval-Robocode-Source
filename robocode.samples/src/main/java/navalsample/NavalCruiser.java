package navalsample;

import robocode.CruiserShip;
import robocode.ScannedShipEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;
import robocode.util.Utils;

import java.awt.*;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class NavalCruiser extends CruiserShip<CannonComponent, RadarComponent> {
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



    public void run(){
        super.run();

        setBodyColor(Color.black);

        slot1().setAdjustComponentForShipTurn(true);
        slot2().setAdjustComponentForShipTurn(true);

        slot1().setTurnRightDegrees(90);
        slot2().setTurnRightDegrees(90);
        while(slot2().getHeadingRadians() != 0
                && slot1().getTurnRemainingRadians() != 0){

            execute();
        }



        while(true){
            radarHeading = Utils.normalAbsoluteAngleDegrees(getBodyHeadingDegrees() - slot2().getHeadingDegrees());
            if(radarHeading > 90 && radarHeading < 270){
                slot1().setTurnLeftDegrees(speed);
            }
            else{
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
                "RADARBEAR : %f", getBodyHeadingDegrees(), slot1().getHeadingDegrees(), e.getBearingDegrees(), slot2().getBearingDegrees(e));



        slot1().setTurnRightRadians(slot1().getBearingRadians(e));
        slot2().setTurnRightRadians(slot2().getBearingRadians(e));

        while(slot1().getTurnRemainingRadians() != 0 && slot2().getTurnRemainingRadians() !=0){
            execute();
        }
    }
}
