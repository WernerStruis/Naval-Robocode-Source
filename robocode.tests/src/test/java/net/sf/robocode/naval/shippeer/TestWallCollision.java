package net.sf.robocode.naval.shippeer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import robocode.control.snapshot.RobotState;
import robocode.naval.NavalRules;
import robocode.naval.ShipType;


public class TestWallCollision {
	private JMockShipPeer peer;
	private double distanceFrontToPivot = ShipType.CARRIER.getHeight() /2 - ShipType.CARRIER.getProwOffset();
	private double distanceBackToPivot = ShipType.CARRIER.getHeight() - ShipType.CARRIER.getProwOffset();
	
	public boolean testCollision(double x, double y, double heading, String testName, boolean log){
		boolean inCollision = false;
		peer = new JMockShipPeer(x,y,heading);
		
		peer.checkWallCollision();
		inCollision = peer.getState() == RobotState.HIT_WALL;
		if(log)
			System.out.println(testName+": ["+x+", "+y+"] heading: " + heading + (inCollision ? " ends up bringing the Ship out of bounds!" : " is a safe spot for the Ship!"));
		return inCollision;
	}
	
	/**
	 * Tests run without a change in heading
	 */
	@Test public void test1a(){assertTrue(testCollision(0,0,0,"test1a",true));}	
	@Test public void test1b(){assertTrue(!testCollision(300, 300, 0,"test1b", true));}
	@Test public void test1c(){assertTrue(testCollision(ShipType.CARRIER.getWidth()/2, ShipType.CARRIER.getHeight()/2 - ShipType.CARRIER.getProwOffset(), 0,"test1c", true));}
	@Test public void test1d(){assertTrue(!testCollision(ShipType.CARRIER.getWidth()/2 + 1, distanceFrontToPivot + 1, 0,"test1d", true));}
	@Test public void test1e(){assertTrue(!testCollision(800 - (ShipType.CARRIER.getWidth()/2 + 1), 600 - (distanceBackToPivot + 1), 0,"test1e", true));}
	@Test public void test1f(){assertTrue(testCollision(-100,-100,0,"test1f",true));}	
	@Test public void test1g(){assertTrue(testCollision(3489,2459,0,"test1g",true));}	

	
	
	@Test public void test2a(){assertTrue(!testCollision(100,100,100,"test2a",true));}	
	@Test public void test2b(){double angle = Math.toRadians(315); assertTrue(!testCollision(distanceFrontToPivot * Math.sin(angle+Math.PI/2),distanceFrontToPivot * Math.cos(angle+Math.PI/2),angle,"test2b",true));}	
	@Test public void test2c(){double angle = Math.toRadians(135); assertTrue(!testCollision(Math.abs(distanceBackToPivot * Math.sin(angle+Math.PI/2)),Math.abs(distanceBackToPivot * Math.cos(angle+Math.PI/2)),angle,"test2c",true));}	


	
}
