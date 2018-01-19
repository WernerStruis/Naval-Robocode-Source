package net.sf.robocode.test.naval;

import org.junit.Ignore;
import org.junit.Test;
import robocode.naval.CannonType;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MissileComponent;
import robocode.util.Utils;

import static org.junit.Assert.assertEquals;

@Ignore
public class MissileComponentTest {
    private MissileComponent missileComponent;


    public MissileComponentTest() {
        missileComponent = new MissileComponent();
        missileComponent.setAttributeValues(CannonType.DOUBLE_BARREL.getTurnRate(), CannonType.DOUBLE_BARREL.getDamageMultiplier(), CannonType.DOUBLE_BARREL.getBonusMultiplier(), CannonType.DOUBLE_BARREL.getBulletSpeedMultiplier(), CannonType.DOUBLE_BARREL.getMaxBulletPower());
    }

    public double testTurnRadians(double turnRemaining) {
        double output = missileComponent.turnRadians(turnRemaining);
        System.out.println("Turn Radians Test - TurnRemaining before: " + Math.toDegrees(turnRemaining) + " turnRemaining after: " + Math.toDegrees(output));
        return output;
    }

    @Test
    public void testTurning() {

        double degreesToTurn = 360;
        double result;

        //Turn until the blindspot or the destination is reached
        while (!Utils.isNear(degreesToTurn, 0.0)) {

            //Turn as much as you can
            result = testTurnRadians(Math.toRadians(degreesToTurn));

            //Make sure the testing value isn't 0 and that it won't go over the blindSpot
            degreesToTurn -= CannonType.DOUBLE_BARREL.getTurnRate();

            if (degreesToTurn < 0) {
                degreesToTurn = 0;
            }


            assertEquals("result NOT near: " + Math.toDegrees(result) + " - " + degreesToTurn, Utils.isNear(result, Math.toRadians(degreesToTurn)), true);
        }
        System.out.println("*** End of " + "TurnSpeedTest" + " ***");
    }


}
