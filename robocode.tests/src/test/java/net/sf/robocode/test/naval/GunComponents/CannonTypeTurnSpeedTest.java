package net.sf.robocode.test.naval.GunComponents;

import net.sf.robocode.test.helpers.LargeRobocodeTestBed;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.naval.CannonType;

import static org.junit.Assert.assertTrue;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class CannonTypeTurnSpeedTest extends LargeRobocodeTestBed{

    int x = 300;
    int y = 300;
    int heading = 0;



    public String getRobotNames() {
        return "tested.ships.TurnSpeedTestShip";
    }

    @Override
    public String getInitialPositions() {
        return "("+x+","+y+","+heading+")";
    }



    double oldFrontGunHeading = -1;
    double oldBackGunHeading = -1;
    boolean frontCheck = false;
    boolean backCheck = false;

    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        if(oldFrontGunHeading != 100 && oldBackGunHeading != 80) {

            IRobotSnapshot shortRangeRobotSnapshot = event.getTurnSnapshot().getRobots()[0];
            assertTrue("TurnSpeedTestShip is not the actual one", shortRangeRobotSnapshot.getName().equals("tested.ships.TurnSpeedTestShip"));

            String out = shortRangeRobotSnapshot.getOutputStreamSnapshot();
            double frontGunHeading = getGunHeadings(out, 1);
            double backGunHeading = getGunHeadings(out, 0);

            System.out.printf("F: %f - R: %f\n", frontGunHeading, backGunHeading);

            if (oldFrontGunHeading != -1 && frontGunHeading <= 100 - CannonType.SINGLE_BARREL.getTurnRate() && !frontCheck) {
                frontCheck = true;
                System.out.printf("FRONT: old: %f - new: %f \n", oldFrontGunHeading, frontGunHeading);
                assertTrue("Front gun: Turn speed not correct " + frontGunHeading + " - " + oldFrontGunHeading, frontGunHeading == (oldFrontGunHeading + CannonType.SINGLE_BARREL.getTurnRate()));
            }
            if (oldBackGunHeading != -1 && backGunHeading >= 80 + CannonType.DOUBLE_BARREL.getTurnRate() && !backCheck) {
                backCheck = true;
                System.out.printf("BACK: old: %f - new: %f \n", oldBackGunHeading, backGunHeading);
                assertTrue("Rear gun: Turn speed not correct " + backGunHeading + " - " + oldBackGunHeading, backGunHeading == (oldBackGunHeading + CannonType.DOUBLE_BARREL.getTurnRate()));
            }

            oldFrontGunHeading = frontGunHeading;
            oldBackGunHeading = backGunHeading;
            System.out.println("--");
        }
    }
    @Override
    protected int getExpectedErrors() {
        return 1;
    }


    private double getGunHeadings(String outputString, int type){
        int startIndex = (type == 1 ? outputString.indexOf('>')  : outputString.lastIndexOf('>')) + 1;
        int endIndex = (type == 1 ? outputString.indexOf('<') : outputString.lastIndexOf('<'));
        try{
            return Double.parseDouble(outputString.substring(startIndex, endIndex));
        }catch (StringIndexOutOfBoundsException e){
            return 0;
        }
    }

}
