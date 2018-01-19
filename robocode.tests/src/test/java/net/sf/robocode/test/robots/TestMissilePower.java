package net.sf.robocode.test.robots;

import net.sf.robocode.test.helpers.Assert;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Ignore;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.control.snapshot.IShipSnapshot;

/**
 * @author Thales B.V. / Colin Heppener (Naval Robocode contributor)
 */
@Ignore("test still being written")
public class TestMissilePower extends RobocodeTestBed {
    @Override
    public String getRobotNames() {
        return "tested.ships.SittingDuck, tested.ships.MissileShip";
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);
        IShipSnapshot bp = event.getTurnSnapshot().getShips()[1];

        final int time = event.getTurnSnapshot().getTurn();

        switch (time) {
            case 30:
            case 46:
            case 62:
            case 78:
            case 94:
            case 110:
                test(bp, "Missile power: 30.0");
                break;

            default:
                if (time > 1 && time < 115) {
                    test(bp, "No missile");
                }
                break;
        }
    }

    private void test(IShipSnapshot gh, String s) {
        Assert.assertTrue(gh.getOutputStreamSnapshot() + " expected " + s, gh.getOutputStreamSnapshot().contains(s));
    }
}
