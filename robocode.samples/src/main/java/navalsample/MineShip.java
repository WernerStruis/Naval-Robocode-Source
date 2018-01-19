package navalsample;

import robocode.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.NavalRules;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;
import robocode.util.Utils;

import java.awt.*;


/**
 * @author Thales B.V. / Colin Heppener (Naval Robocode contributor)
 */


public class MineShip extends CarrierShip<CannonComponent, RadarComponent, MineComponent, CannonComponent> {
    private TargetShip targetShip = new TargetShip();

    private boolean tracking;

    private double direction = 1;

    private int rounds = 0;

    @Override
    public void run() {
        super.run();

        setBodyColor(Color.MAGENTA);
        slot3().setColor(Color.CYAN);
        slot1().setColor(Color.CYAN);
        slot2().setColor(Color.PINK);
        slot4().setBulletColor(Color.ORANGE);
        slot1().setBulletColor(Color.YELLOW);

        while(true) {



            if (!tracking && slot2().getTurnRemainingRadians() == 0.0) {
                slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);
            }
            if (getVelocity() == 0.0) {
//                setAhead(direction * 4000);
            }

            if (rounds > 10) {
                tracking = false;
                targetShip.setNone();
            }
            rounds++;

            execute();

        }
    }


    @Override
    public void onScannedShip(ScannedShipEvent event) {
        updatePotentialThreat(event);
        updateTrackStatus();

        if (tracking && targetShip.isSame(event.getName())) {
            rounds = 0;
            if (event.getDistance() > 1200) {
                targetShip.setNone();
                slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);
                tracking = false;
                return;
            }
            updateRadar(event);
            updateCannons(event);
            updateRobot(event);

            if (slot1().getTurnRemainingRadians() < 3) {
                double power = NavalRules.MIN_BULLET_POWER;
                if (event.getDistance() < 700 || getVelocity() == 0.0) {
                    System.out.println("Fire front");
                    power = slot1().getPower();
                }
                slot3().placeMine(NavalRules.MAX_MINE_POWER);
            }

            if (slot4().getTurnRemainingRadians() < 3) {
                double power = NavalRules.MIN_BULLET_POWER;
                if (event.getDistance() < 700 || getVelocity() == 0.0) {
                    System.out.println("Fire back");
                    power = slot4().getPower();
                }
                slot3().placeMine(NavalRules.MAX_MINE_POWER);

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
    public MineComponent setSlot3() {
        return new MineComponent();
    }

    @Override
    public CannonComponent setSlot4() {
        return new DoubleBarrelCannon();
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        if (direction < 0) {
            slot3().placeMine(NavalRules.MAX_MINE_POWER);
        }
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
        if (targetShip.isSame(event.getName())) {
            targetShip.setNone();
            tracking = false;
            slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);

        }

    }


    private void updateRobot(ScannedShipEvent event) {
        double adjusted = event.getBearingRadians() + (Math.PI/2) - (15 * direction);

        setTurnRightRadians(Utils.normalRelativeAngleDegrees(adjusted));
    }


    private void updateCannons(ScannedShipEvent event) {
        System.out.println("update Cannons");

        slot1().setTurnRightRadians(slot1().getBearingRadians(event));

        slot4().setTurnRightRadians(slot4().getBearingRadians(event));

    }


    private void updateRadar(ScannedShipEvent event) {
        slot2().setTurnRightRadians(slot2().getBearingRadians(event));
    }


    private void updateTrackStatus() {
        tracking = !targetShip.isNone();
    }


    private void updatePotentialThreat(ScannedShipEvent event) {
        if (isMostPromising(event)) {
            targetShip.update(event);
        }

    }


    private boolean isMostPromising(ScannedShipEvent event) {
        return targetShip.isNone() || targetShip.isSame(event.getName()) || event.getDistance() < targetShip.getDistance();
    }


    public class TargetShip {

        private double distance;
        private String name;
        private boolean none = true;

        public double getDistance() {
            return distance;
        }

        public void setNone() {
            this.none = true;
        }

        public void update(ScannedShipEvent event) {
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

