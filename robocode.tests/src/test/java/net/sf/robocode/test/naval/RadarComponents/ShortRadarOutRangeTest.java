package net.sf.robocode.test.naval.RadarComponents;

import static org.junit.Assert.*;

import net.sf.robocode.battle.snapshot.ShipSnapshot;
import net.sf.robocode.test.helpers.LargeRobocodeTestBed;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.naval.NavalRules;
import robocode.naval.RadarType;
import robocode.naval.ShipType;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class ShortRadarOutRangeTest extends LargeRobocodeTestBed {

    int sittingDuckX = 300;
    int sittingDuckY = 300;
    int sittingDuckHeading = 90;

    int shortRangeShipX = sittingDuckX + 598;
    int shortRangeShipY = 300;
    int shortRangeShipHeading = 270;

    int radarRangeTreshold = ShipType.CORVETTE.getHeight() / 2;

    public String getRobotNames() {
        return "tested.ships.SittingDuck,tested.ships.ShortRangeRadarShip";
    }

    @Override
    public String getInitialPositions() {
        return "("+sittingDuckX+","+sittingDuckY+","+sittingDuckHeading+"), ("+ shortRangeShipX+","+shortRangeShipY+","+shortRangeShipHeading+")";
    }

    @Override
    protected int getExpectedErrors() {
        return 1;
    }




    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        IRobotSnapshot shortRangeRobotSnapshot = event.getTurnSnapshot().getRobots()[1];
        assertTrue("ShortRangeRobot is not the actual one", shortRangeRobotSnapshot.getName().equals("tested.ships.ShortRangeRadarShip"));

        String shortRangeOut = shortRangeRobotSnapshot.getOutputStreamSnapshot();
        double shortRangeDistance = getDistance(shortRangeOut);

        //getDistance returns 0.0 if no scannedevent, so skip the assert
        if(shortRangeDistance != 0.0) {
            throw new AssertionError("There should be nothing registered?");
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
