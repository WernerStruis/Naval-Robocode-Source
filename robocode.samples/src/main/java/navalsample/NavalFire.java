package navalsample;

import robocode.CorvetteShip;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.ScannedShipEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Sample Ship
 * this version has some kind of laziness implemented.
 * Which means that if the target is too far a way
 * for the weapon, then the weapon won't turn.
 * @author Thales B.V. / Thomas Hakkers / Werner Struis
 */
public class NavalFire extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {
	int dist = 200; // distance to move when we're hit
	int imprecission = 10;	//How imprecise the guns are. 
	int laziness = 80;	//Distance it wants to move its guns. The Ship wants to shoot on every target out there. If we don't do this, the guns will just go back and forth trying to reach the enemy of the previous event.

	/**
	 * run:  NavalFire's main run function
	 */
	public void run() {
		super.run();

		// Set colors
		setBodyColor(Color.orange);
		slot1().setColor(Color.orange);
		slot3().setColor(Color.orange);
		slot2().setColor(Color.red);

		slot2().setScanColor(Color.red);
		slot1().setBulletColor(Color.red);
		slot3().setBulletColor(Color.red);

		// Spin the radar around ... forever
		slot2().setTurnRightDegrees(Double.POSITIVE_INFINITY);
		execute();
		
	}

	/**
	 * onScannedShip: Turn weapons towards it and fire!
	 */
	public void onScannedShip(ScannedShipEvent e) {
		if(Math.abs(slot1().getBearingDegrees(e)) < laziness){
			slot1().setTurnRightDegrees(slot1().getBearingDegrees(e));
			//Test whether the enemy Ship is in gun range.
			//FRONT
			if (Math.abs(slot1().getBearingDegrees(e)) < imprecission) {
				if(e.getDistance() < 200 && getEnergy() > 50)
					slot1().fire();
				else
					slot1().fire();
			} 
		}
		else if(Math.abs(slot3().getBearingDegrees(e)) < laziness){
			slot3().setTurnRightDegrees(slot3().getBearingDegrees(e));
			//BACK
			if (Math.abs(slot3().getBearingDegrees(e)) < imprecission) {
				if(e.getDistance() < 200 && getEnergy() > 50)
					slot3().fire();
				else
					slot3().fire();
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
	public CannonComponent setSlot3() {
		return new DoubleBarrelCannon();
	}

	/**
	 * onHitByBullet:  Move almost a full length of a Ship ahead or backward
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRightDegrees(normalRelativeAngleDegrees(90 - (getBodyHeadingDegrees() - e.getHeading())));

		setAhead(dist);
		dist *= -1;
	}

	/**
	 * onHitRobot:  Aim at it.  Fire Hard!
	 */
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmtFront = normalRelativeAngleDegrees(e.getBearing() + getBodyHeadingDegrees() - slot1().getHeadingDegrees());
		slot1().setTurnRightDegrees(turnGunAmtFront);
		//fire immediately
		slot1().fire();
		
		double turnGunAmtBack = normalRelativeAngleDegrees(e.getBearing() + getBodyHeadingDegrees() - slot3().getHeadingDegrees());
		slot3().setTurnRightDegrees(turnGunAmtBack);
		//fire immediately
		slot3().fire();
	}
	
}
