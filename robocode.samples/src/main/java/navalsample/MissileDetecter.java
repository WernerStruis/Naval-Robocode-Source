package navalsample;

import robocode.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.NavalRules;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;
import robocode.util.Utils;

import java.awt.*;

public class MissileDetecter extends CorvetteShip <CannonComponent, RadarComponent, CannonComponent> {

    private Target target = new Target();

    private boolean tracking;

    private double direction = 1;

    private int rounds = 0;

    @Override
    public void run() {
        super.run();


        setBodyColor(Color.BLACK);
        slot1().setColor(Color.CYAN);
        slot3().setColor(Color.CYAN);
        slot2().setColor(Color.PINK);
        slot1().setBulletColor(Color.ORANGE);
        slot3().setBulletColor(Color.YELLOW);

        while(true) {



            if (!tracking && slot2().getTurnRemainingRadians() == 0.0) {
                slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);
            }
            if (getVelocity() == 0.0) {
                setAhead(direction * 4000);
            }

            if (rounds > 10) {
                tracking = false;
                target.setNone();
            }
            rounds++;

            execute();

        }
    }


    @Override
    public void onScannedMissile(ScannedMissileEvent event) {
        updatePotentialThreat(event);
        updateTrackStatus();

        if (tracking && target.isSame(event.getName())) {
            rounds = 0;
            if (event.getDistance() > 1200) {
                target.setNone();
                slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);
                tracking = false;
                return;
            }
            updateRadar(event.getBearingRadians());
            updateCannons(event.getBearingRadians());
            updateRobot(event.getBearingRadians());
            if (slot1().getTurnRemainingDegrees() < 3) {
                slot1().fire();
            }

            if (slot3().getTurnRemainingDegrees() < 3) {
                slot3().fire();
            }


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

    @Override
    public void onHitRobot(HitRobotEvent event) {
        direction *= -1;
        setAhead(direction  * 4000);

    }

    @Override
    public void onHitWall(HitWallEvent event) {
        direction *= -1;
        setAhead(direction * 4000);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        if (target.isSame(event.getName())) {
            target.setNone();
            tracking = false;
            slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);

        }

    }


    private void updateRobot(double bearingRadians) {
        double adjusted = bearingRadians + (Math.PI/2) - (15 * direction);

        setTurnRightRadians(Utils.normalRelativeAngleDegrees(adjusted));
    }


    private void updateCannons(double bearingFrontRadians) {

        double front = getBodyHeadingRadians() - slot1().getHeadingRadians() + bearingFrontRadians;
        slot1().setTurnRightRadians(Utils.normalRelativeAngle(front));

        double back = getBodyHeadingRadians() - slot3().getHeadingRadians() + bearingFrontRadians;
        slot3().setTurnRightRadians(Utils.normalRelativeAngle(back));

    }


    private void updateRadar(double bearingRadians) {
        double adjusted = getBodyHeadingRadians() - slot2().getHeadingRadians() + bearingRadians;
        slot2().setTurnRightRadians(Utils.normalRelativeAngle(adjusted));
    }


    private void updateTrackStatus() {
        tracking = !target.isNone();
    }


    private void updatePotentialThreat(ScannedMissileEvent event) {
        if (isMostPromising(event)) {
            target.update(event);
        }
    }


    private boolean isMostPromising(ScannedMissileEvent event) {
        return target.isNone() || target.isSame(event.getName()) || event.getDistance() < target.getDistance();
    }


    private class Target {

        private double distance;
        private String name;
        private boolean none = true;

        public double getDistance() {
            return distance;
        }

        public void setNone() {
            this.none = true;
        }

        public void update(ScannedMissileEvent event) {
            this.name = event.getName();
            this.distance = event.getDistance();
            this.none = false;
        }

        public boolean isNone() {
            return none ;
        }

        public boolean isSame(String name) {
            return name.equals(this.name);
        }

    }

}
