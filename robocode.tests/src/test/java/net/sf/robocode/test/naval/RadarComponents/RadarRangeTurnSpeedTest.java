package net.sf.robocode.test.naval.RadarComponents;

import net.sf.robocode.test.helpers.LargeRobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.naval.RadarType;

import static org.junit.Assert.assertTrue;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class RadarRangeTurnSpeedTest extends LargeRobocodeTestBed{

    int shortRangeShipX = 300;
    int shortRangeShipY = 300;
    int shortRangeShipHeading = 0;

    int longRangeShipX = shortRangeShipX + 400;
    int longRangeShipY = 300;
    int longRangeShipHeading = 0;

    private double radarSpeedThreshold = 0.5;

    public String getRobotNames() {
        return "tested.ships.ShortRangeRadarShip,tested.ships.LongRangeRadarShip";
    }

    @Override
    public String getInitialPositions() {
        return "("+shortRangeShipX+","+shortRangeShipY+","+shortRangeShipHeading+"), ("+ longRangeShipX+","+longRangeShipY+","+longRangeShipHeading+")";
    }



    double oldLongRadarHeading = 0;
    double oldShortRadarHeading = 0;

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        IRobotSnapshot shortRangeRobotSnapshot = event.getTurnSnapshot().getRobots()[0];
        assertTrue("LongRangeRobot is not the actual one", shortRangeRobotSnapshot.getName().equals("tested.ships.ShortRangeRadarShip"));

        IRobotSnapshot longRangeRobotSnapshot = event.getTurnSnapshot().getRobots()[1];
        assertTrue("LongRangeRobot is not the actual one", longRangeRobotSnapshot.getName().equals("tested.ships.LongRangeRadarShip"));

        String longRangeOut = longRangeRobotSnapshot.getOutputStreamSnapshot();
        double newLongRangeRadarHeading = getRadarHeading(longRangeOut);

        String shortRangeOut = shortRangeRobotSnapshot.getOutputStreamSnapshot();
        double newShortRangeRadarHeading = getRadarHeading(shortRangeOut);

        if(oldLongRadarHeading != 0 && newLongRangeRadarHeading < 349 && event.getTurnSnapshot().getTurn() > 10){
            double temp = oldLongRadarHeading;
            oldLongRadarHeading = newLongRangeRadarHeading;
            assertTrue("LongRangeRadar: Diffrence between old and new radarheading was less than expected " + newLongRangeRadarHeading + " - " + temp, newLongRangeRadarHeading <= (temp + RadarType.LONG_RANGE.getTurnRate() + radarSpeedThreshold));
        }else{
            oldLongRadarHeading = newLongRangeRadarHeading;
        }

        if(oldShortRadarHeading != 0 && newShortRangeRadarHeading < 349 && event.getTurnSnapshot().getTurn() > 10){
            double temp = oldShortRadarHeading;
            oldShortRadarHeading = newShortRangeRadarHeading;
            assertTrue("ShortRangeRadar: Diffrence between old and new radarheading was less than expected " + newShortRangeRadarHeading + " - " + temp, newShortRangeRadarHeading <= (temp + RadarType.SHORT_RANGE.getTurnRate() + radarSpeedThreshold));
        }else{
            oldShortRadarHeading = newShortRangeRadarHeading;
        }




    }

    @Override
    protected int getExpectedErrors() {
        return 1;
    }

    private double getRadarHeading(String outputString){
        try{
            return Double.parseDouble(outputString.substring(outputString.indexOf('{') + 1, outputString.indexOf('}')));
        }catch (StringIndexOutOfBoundsException e){
            return 0;
        }
    }

}
