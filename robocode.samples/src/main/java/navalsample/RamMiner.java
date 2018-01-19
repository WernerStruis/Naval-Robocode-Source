package navalsample;

import robocode.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;
import robocode.util.Utils;

import java.awt.*;

/**
 * Spin around in backward circles until a target is
 * found, after which it attempt to turn towards it,
 * ram it and place mines on the other ship.
 * @author Thales B.V. / Thomas Hakkers
 *
 */
public class RamMiner extends CarrierShip <CannonComponent, RadarComponent, MineComponent, CannonComponent> {
	boolean hitWall= false;

	/**
	 * run: Spin around looking for a target
	 */
	public void run() {
		super.run();

		// Set colors
		setBodyColor(Color.lightGray);
		slot1().setColor(Color.gray);
		slot4().setColor(Color.gray);
		slot2().setColor(Color.darkGray);
//		setMineComponentColor(Color.yellow);

		slot2().setTurnRightDegrees(getBodyHeadingDegrees() - slot2().getHeadingDegrees() + 180);
		
		while (true) {
			setTurnRightDegrees(10);
			setBack(50);
			execute();
		}
	}

	/**
	 * onScannedShip:  We have a target.  Go get it.
	 */
	public void onScannedShip(ScannedShipEvent e) {
		setTurnRightRadians(Utils.normalRelativeAngle(e.getBearingRadians() - Math.PI));
		System.out.println("Test! " + Math.toDegrees(e.getBearingRadians()));
		setBack(e.getDistance() + 5);
		scan(); // Might want to move ahead again!
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
	public MineComponent setSlot3() {
		return new MineComponent();
	}

	@Override
	public CannonComponent setSlot4() {
		return new DoubleBarrelCannon();
	}

	/**
	 * onHitRobot:  Turn to face ship, fire hard, and ram him again!
	 */
	public void onHitRobot(HitRobotEvent e) {
		if(hitWall){
			if (e.getEnergy() > 16) {
				slot1().fire();
			} else if (e.getEnergy() > 10) {
				slot1().fire();
			} else if (e.getEnergy() > 4) {
				slot1().fire();
			} else if (e.getEnergy() > 2) {
				slot1().fire();
			} else if (e.getEnergy() > .4) {
				slot1().fire();
			}
		}
		else{
			setTurnRightDegrees(e.getBearing());
	
			// Determine a shot that won't kill the robot...
			// We want to ram him instead for bonus points
			if (e.getEnergy() > 70) {
				slot3().placeMine(15);
			} else if (e.getEnergy() > 50) {
				slot3().placeMine(12);
			} else if (e.getEnergy() > 35) {
				slot3().placeMine(10);
			} else if (e.getEnergy() > 20) {
				slot4().fire();
			} else if (e.getEnergy() > 10) {
				slot4().fire();
			}else{
				slot4().fire();
			}
			setBack(40); // Ram him again!
		}
	}
	
	public void onHitWall(HitWallEvent e){
		if(hitWall){
			hitWall = true;
			setAhead(-50);
			while(getDistanceRemaining() != 0){
				execute();
			}
			hitWall = false;
		}
		else{
			hitWall = true;
			setAhead(100);
			while(getDistanceRemaining() != 0){
				execute();
			}
			hitWall = false;
		}
		
	}
}
