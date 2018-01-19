package navalsample;

import robocode.CarrierShip;
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
public class NavalCarrier extends CarrierShip<CannonComponent, CannonComponent, CannonComponent, RadarComponent> {
    int directionFrontCannon = 1;
    int directionBackCannon = 1;
    double speed = 4;
    double radarHeading;

    @Override
    public CannonComponent setSlot1() {
        return new SingleBarrelCannon();
    }

    @Override
    public CannonComponent setSlot2() {
        return new SingleBarrelCannon();
    }

    @Override
    public CannonComponent setSlot3() {
        return new DoubleBarrelCannon();
    }

    @Override
    public RadarComponent setSlot4() {
        return new LongRangeRadar();
    }

    public void run() {
        super.run();

        setBodyColor(Color.black);

        slot1().setAdjustComponentForShipTurn(true);
        slot2().setAdjustComponentForShipTurn(true);
        slot3().setAdjustComponentForShipTurn(true);
        slot4().setAdjustComponentForShipTurn(true);

        slot1().setTurnRightDegrees(90);
        slot3().setTurnLeftDegrees(90);
        slot4().setTurnLeftDegrees(90);
        slot2().setTurnRightDegrees(90);

        while ( slot4().getTurnRemainingRadians() != 0 &&
                slot3().getTurnRemainingRadians() != 0
                && slot2().getHeadingRadians() != 0
                && slot1().getTurnRemainingRadians() != 0) {

            execute();
        }


        while (true) {
            radarHeading = Utils.normalAbsoluteAngleDegrees(getBodyHeadingDegrees() - slot2().getHeadingDegrees());
            if (radarHeading > 90 && radarHeading < 270) {
                slot1().setTurnLeftDegrees(speed);
                slot2().setTurnLeftDegrees(speed);
                slot3().setTurnRightDegrees(speed);
            } else {
                slot3().setTurnLeftDegrees(speed);
                slot2().setTurnLeftDegrees(speed);
                slot1().setTurnRightDegrees(speed);
            }
            slot4().setTurnRightDegrees(speed);
            execute();
        }

    }


    @Override
    public void onScannedShip(ScannedShipEvent e) {
        System.out.printf("--SCANNED--\n" +
                "HEADING   : %f\n" +
                "RADAR     : %f\n" +
                "BEAR      : %f\n" +
                "RADARBEAR : %f", getBodyHeadingDegrees(), slot4().getHeadingDegrees(), e.getBearingDegrees(), slot4().getBearingDegrees(e));


        slot3().setTurnRightRadians(slot3().getBearingRadians(e));
        slot1().setTurnRightRadians(slot1().getBearingRadians(e));
        slot2().setTurnRightRadians(slot2().getBearingRadians(e));
        slot4().setTurnRightRadians(slot4().getBearingRadians(e));

        while (slot1().getTurnRemainingRadians() != 0 && slot2().getTurnRemainingRadians() != 0 && slot4().getTurnRemainingRadians() != 0) {
            execute();
        }
    }
}
