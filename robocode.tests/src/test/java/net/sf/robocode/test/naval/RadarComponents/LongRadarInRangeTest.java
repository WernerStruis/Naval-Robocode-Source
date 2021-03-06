package net.sf.robocode.test.naval.RadarComponents;

import net.sf.robocode.test.helpers.LargeRobocodeTestBed;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.naval.NavalRules;
import robocode.naval.RadarType;
import robocode.naval.ShipType;

import static org.junit.Assert.assertTrue;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class LongRadarInRangeTest extends LargeRobocodeTestBed{

    int sittingDuckX = 300;
    int sittingDuckY = 300;
    int sittingDuckHeading = 90;

    int longRangeShipX = sittingDuckX + 1197;
    int longRangeShipY = 300;
    int longRangeShipHeading = 270;

    int radarRangeTreshold = ShipType.CORVETTE.getHeight() / 2;

    public String getRobotNames() {
        return "tested.ships.SittingDuck,tested.ships.LongRangeRadarShip";
    }

    @Override
    public String getInitialPositions() {
        return "("+sittingDuckX+","+sittingDuckY+","+sittingDuckHeading+"), ("+ longRangeShipX+","+longRangeShipY+","+longRangeShipHeading+")";
    }

    @Override
    protected int getExpectedErrors() {
        return 1;
    }

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        IRobotSnapshot shortRangeRobotSnapshot = event.getTurnSnapshot().getRobots()[1];
        assertTrue("LongRangeRobot is not the actual one", shortRangeRobotSnapshot.getName().equals("tested.ships.LongRangeRadarShip"));

        String longRangeOut = shortRangeRobotSnapshot.getOutputStreamSnapshot();
        double longRangeDistance = getDistance(longRangeOut);

        //getDistance returns 0.0 if no scannedevent, so skip the assert
        if(longRangeDistance != 0.0) {
            assertTrue("Ship is out of range, but still registered??", longRangeDistance <= RadarType.LONG_RANGE.getScanRadius() + radarRangeTreshold);
        }


    }

    private double getDistance(String outputString){
        try{
            return Double.parseDouble(outputString.substring(outputString.indexOf('>') + 1, outputString.indexOf('<')));
        }catch (StringIndexOutOfBoundsException e){
            return 0;
        }
    }

}
