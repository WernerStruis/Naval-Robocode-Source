package net.sf.robocode.test.naval.ShipTypes;

import net.sf.robocode.battle.snapshot.RobotSnapshot;
import net.sf.robocode.battle.snapshot.ShipSnapshot;
import net.sf.robocode.io.Logger;
import net.sf.robocode.test.helpers.LargeRobocodeTestBed;
import org.junit.Test;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import robocode.naval.NavalRules;
import robocode.naval.ShipType;


import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class TestShipTypeAttributes extends LargeRobocodeTestBed {


    private static int x;
    private static int y;
    private static int heading;
    private static String pkg = "tested.ships.";
    private static String robotName;

    private static boolean isVelocityTest = false;
    private static boolean isTurnRateTest = false;
    private static boolean isEnergyTest = false;

    public TestShipTypeAttributes() {
        try {
            String currentDirAbsolutePath = new File("").getAbsolutePath();
            if (currentDirAbsolutePath.endsWith("robocode.tests")) {
                robotsPath = new File("../robocode.tests.robots").getCanonicalPath();
            } else if (currentDirAbsolutePath.endsWith("robocode.dotnet.tests")) {
                robotsPath = new File("../../../robocode.tests.robots").getCanonicalPath();
            } else if (new File("robocode.tests.robots").isDirectory()) {
                robotsPath = new File("robocode.tests.robots").getCanonicalPath();
            } else {
                throw new Error("Unknown directory: " + currentDirAbsolutePath);
            }
        } catch (IOException e) {
            e.printStackTrace(Logger.realErr);
        }
        System.setProperty("ROBOTPATH", robotsPath + "/target/classes");
    }

    @Override
    protected int getExpectedErrors() {
        return 1;
    }


    public String getRobotNames() {
        return pkg + robotName + ", tested.ships.SittingDuck";
    }

    @Override
    public String getInitialPositions() {
        return "(" + x + "," + y + "," + heading + "),(0,0,0)";
    }


    static double lastVelocity;
    static double lastBodyHeading = -1;


    @Override
    public void onTurnEnded(TurnEndedEvent event) {
        super.onTurnEnded(event);

        IRobotSnapshot ship = event.getTurnSnapshot().getRobots()[0];

        if (isTurnRateTest && isVelocityTest) {
            System.err.println("MESSED UP");
        }

        if (isVelocityTest) {
            if (lastVelocity == ship.getVelocity() && ship.getVelocity() != 0) {
                testVelocity(ship.getVelocity(), ship.getName());
            }
            lastVelocity = ship.getVelocity();
        } else if (isTurnRateTest) {

            if (lastBodyHeading != -1) {
                System.out.println(ship.getVelocity());
                testTurnRate(ship.getVelocity(), ship.getBodyHeading(), ship.getName());
            }
            lastBodyHeading = ship.getBodyHeading();

        } else if (isEnergyTest) {
            testEnergy(ship.getEnergy(), ship.getName());
        } else {
            this.runTeardown();
        }
    }

    public void testEnergy(double energy, String ship) {
        if (energy != 0.0) {
            if (ship.equals("tested.ships.CruiserTestShip")) {
                System.out.println("Testing for correct energy value");
                isEnergyTest = false;
                assertTrue("Energy does not match expected value", energy == 100);
            } else if (ship.equals("tested.ships.CorvetteTestShip")) {
                System.out.println("Testing for correct energy value");
                isEnergyTest = false;
                assertTrue("Energy does not match expected value", energy == 150);
            } else if (ship.equals("tested.ships.CarrierTestShip")) {
                System.out.println("Testing for correct energy value");
                isEnergyTest = false;
                assertTrue("Energy does not match expected value", energy == 200);
            } else {
                System.err.println("WHY DID I COME HERE?");
            }
        }
    }

    public void testTurnRate(double velocity, double heading, String ship) {

        if (ship.equals("tested.ships.CruiserTestShip")) {
            if (velocity == 20.4) {
                System.out.println("Testing for correct Turnrate now");
                isTurnRateTest = false;
                assertTrue("Turn rate is not as expected (EXPECTED:" + lastBodyHeading + Math.toRadians(2) + " - GOT:" + heading + ")", lastBodyHeading + Math.toRadians(2) == heading);
            }
        } else if (ship.equals("tested.ships.CorvetteTestShip")) {
            if (velocity == 15.4) {
                System.out.println("Testing for correct Turnrate now");
                isTurnRateTest = false;
                assertTrue("Turn rate is not as expected (EXPECTED:" + lastBodyHeading + Math.toRadians(1) + " - GOT:" + heading + ")", lastBodyHeading + Math.toRadians(1) == heading);
            }
        } else if (ship.equals("tested.ships.CarrierTestShip")) {
            if (velocity == 10.4) {
                System.out.println("Testing for correct Turnrate now");
                isTurnRateTest = false;
                assertTrue("Turn rate is not as expected (EXPECTED:" + lastBodyHeading + Math.toRadians(1) + " - GOT:" + heading + ")", lastBodyHeading + Math.toRadians(1) == heading);
            }
        } else {
            System.err.println("WHY DID I COME HERE?");
        }
    }

    public void testVelocity(double velocity, String ship) {
        System.out.println("Testing for correct velocity now");
        if (ship.equals("tested.ships.CruiserTestShip")) {
            isVelocityTest = false;
            assertTrue("Velocity is not as expected", velocity == 20.4);
        } else if (ship.equals("tested.ships.CorvetteTestShip")) {
            isVelocityTest = false;
            assertTrue("Velocity is not as expected", velocity == 15.4);
        } else if (ship.equals("tested.ships.CarrierTestShip")) {
            isVelocityTest = false;
            assertTrue("Velocity is not as expected", velocity == 10.4);
        } else {
            System.err.println("WHY DID I COME HERE?");
        }

    }

    @Test
    public void testCruiserEnergy() {
        lastVelocity = -1;
        isVelocityTest = false;
        isTurnRateTest = false;
        isEnergyTest = true;
        robotName = "CruiserTestShip";
        x = 500;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();

    }

    @Test
    public void testCorvetteEnergy() {
        lastVelocity = -1;
        isVelocityTest = false;
        isTurnRateTest = false;
        isEnergyTest = true;

        robotName = "CorvetteTestShip";
        x = 500;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();

    }

    @Test
    public void testCarrierEnergy() {
        lastVelocity = -1;
        isVelocityTest = false;
        isTurnRateTest = false;
        isEnergyTest = true;
        robotName = "CarrierTestShip";
        x = 500;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }

    @Test
    public void testCruiserMaxVelocity() {
        lastVelocity = -1;
        isVelocityTest = true;
        isTurnRateTest = false;
        isEnergyTest = false;
        robotName = "CruiserTestShip";
        x = 400;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }

    @Test
    public void testCorvetteMaxVelocity() {
        lastVelocity = -1;
        isVelocityTest = true;
        isTurnRateTest = false;
        isEnergyTest = false;
        robotName = "CorvetteTestShip";

        x = 400;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }

    @Test
    public void testCarrierMaxVelocity() {
        lastVelocity = -1;
        isVelocityTest = true;
        isTurnRateTest = false;
        isEnergyTest = false;
        robotName = "CarrierTestShip";

        x = 400;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }

    @Test
    public void testCruiserMaxTurnRate() {
        lastVelocity = -1;
        isVelocityTest = false;
        isTurnRateTest = true;
        isEnergyTest = false;
        robotName = "CruiserTestShip";

        x = 450;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }

    @Test
    public void testCorvetteMaxTurnRate() {
        lastVelocity = -1;
        isVelocityTest = false;
        isTurnRateTest = true;
        isEnergyTest = false;
        robotName = "CorvetteTestShip";

        x = 450;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }

    @Test
    public void testCarrierMaxTurnRate() {
        lastVelocity = -1;
        isVelocityTest = false;
        isTurnRateTest = true;
        isEnergyTest = false;
        robotName = "CarrierTestShip";

        x = 450;
        y = 400;
        heading = 0;

        TestShipTypeAttributes tsta = new TestShipTypeAttributes();
        tsta.run();
    }
}
