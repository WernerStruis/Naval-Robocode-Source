package navalsample;

import robocode.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

import java.awt.*;

/**
 * 					Naval Sample
 * A crazy ship. Does a fairly random movement pattern 
 * and turns it's guns randomly. Even the bullet power 
 * and Component colors are random.
 * @author Thales B.V. / Thomas Hakkers
 *
 */


public class NavalCrazy extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {
	boolean movingForward;

	/**
	 * run: Crazy's main run function
	 */
	public void run() {
		super.run();

		// Set colors
		setBodyColor(Color.cyan);
		slot1().setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
		slot3().setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
		slot2().setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
		slot1().setBulletColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
		slot3().setBulletColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
		slot2().setScanColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));

		slot2().setTurnRightDegrees(Double.POSITIVE_INFINITY);

		// Loop forever
		while (true) {
			// Tell the game we will want to move ahead 40000 -- some large number
			setAhead(40000);
			movingForward = true;
			setTurnRightDegrees(45 + 45 * Math.random());
			waitFor(new NavalTurnCompleteCondition(this));
			setTurnLeftDegrees(90 + 90 * Math.random());
			// ... and wait for the turn to finish ...
			waitFor(new NavalTurnCompleteCondition(this));
			// ... then the other way ...
			setTurnRightDegrees(90 + 90 * Math.random());
			// .. and wait for that turn to finish.
			waitFor(new NavalTurnCompleteCondition(this));
			// then back to the top to do it all again
			if(getEnergy() > 50)
//				placeMine(5);
			execute();
		}
	}

	/**
	 * onHitWall:  Handle collision with wall.
	 */
	public void onHitWall(HitWallEvent e) {
		
		// Bounce off!
		reverseDirection();
	}

	/**
	 * reverseDirection:  Switch from ahead to back & vice versa
	 */
	public void reverseDirection() {
		if (movingForward) {
			setBack(40000);
			movingForward = false;
		} else {
			setAhead(40000);
			movingForward = true;
		}
	}

	/**
	 * onScannedRobot:  Fire!
	 */
	public void onScannedShip(ScannedShipEvent e) {
		slot1().setTurnRightDegrees(Math.random() * 100 - 50);
		slot1().fire();
		slot3().setTurnRightDegrees(Math.random() * 100 - 50);
		slot3().fire();
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

	/**
	 * onHitRobot:  Back up!
	 */
	public void onHitRobot(HitRobotEvent e) {
		// If we hit another Ship, reverse direction 50% of the time
		double random = Math.random();
		if(random > 0.5)
			reverseDirection();
	}
}
