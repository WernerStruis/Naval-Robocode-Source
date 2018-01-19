package navalsample;

import robocode.CorvetteShip;
import robocode.HitRobotEvent;
import robocode.ScannedShipEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

import java.awt.*;
import java.awt.geom.Point2D;

import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Goes to a wall at a 45 + n*90 degree angle, and
 * scans the area for ships, which it will then 
 * proceed to shoot.
 * @author Thales B.V. / Thomas Hakkers
 *
 */
public class NavalCorners extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {
	boolean backwards;

	Point2D.Double[] corners;

	/**
	 * run:  Corners' main run function.
	 */
	public void run() {
		super.run();

		// Set colors
		setBodyColor(Color.red);
		slot1().setColor(Color.black);
		slot3().setColor(Color.black);
		slot2().setColor(Color.yellow);
		slot1().setBulletColor(Color.green);
		slot3().setBulletColor(Color.green);
		slot2().setScanColor(Color.green);
		
		corners = new Point2D.Double[4];
		
		corners[0] = new Point2D.Double(getBattleFieldWidth(), getBattleFieldHeight());
		corners[1] = new Point2D.Double(getBattleFieldWidth(), 0);
		corners[2] = new Point2D.Double(0,0);
		corners[3] = new Point2D.Double(0, getBattleFieldHeight());

		// Move to a corner
		goCorner();

//		// Initialize gun turn speed to 4
		int gunIncrement = 4;
		if(backwards){
			slot2().setTurnRightDegrees(getBodyHeadingDegrees() - slot1().getHeadingDegrees());
		}
		else{
			slot2().setTurnRightDegrees(getBodyHeadingDegrees() - slot1().getHeadingDegrees() + 180);
		}
		while(slot1().getTurnRemainingDegrees() != 0){
			execute();
		}
		if(backwards){
			for (int i = 0; i < 25; i++) {
				slot1().setTurnRightDegrees(gunIncrement);
				slot2().setTurnRightDegrees(gunIncrement * 0.8);
				execute();
			}
		}
		else{
			for (int i = 0; i < 25; i++) {
				slot3().setTurnRightDegrees(gunIncrement);
				slot2().setTurnRightDegrees(gunIncrement * 0.8);
				execute();
			}
		}
		gunIncrement *= -1;
		
		// Spin gun back and forth
		while (true) {
			if(backwards){
				for (int i = 0; i < 50; i++) {
					slot1().setTurnRightDegrees(gunIncrement);
					slot2().setTurnRightDegrees(gunIncrement * 0.8);
					execute();
				}
			}
			else{
				for (int i = 0; i < 50; i++) {
					slot3().setTurnRightDegrees(gunIncrement);
					slot2().setTurnRightDegrees(gunIncrement * 0.8);
					execute();
				}
			}
			gunIncrement *= -1;
		}
	}

	/**
	 * goCorner:  A very inefficient way to get to a corner.  Can you do better?
	 */
	public void goCorner() {
		//Get the easiest corner to go to
		int corner = (int)(getBodyHeadingRadians() / (Math.PI/2));
		
		
		setTurnRightDegrees(normalRelativeAngleDegrees((corner*90 + 45)- getBodyHeadingDegrees()));
		
		backwards = getBackwards(corner);
		if(backwards){
			setBack(5000);
		}
		else{
			setAhead(5000);
		}	
	}

	//true if backwards; false if not
	public boolean getBackwards(int corner){
		int cornerBackward = (corner + 2)%4;
		
		return getDistance(corner) > getDistance(cornerBackward);
	}
	
	public double getDistance(int corner){
		double a = getX() - corners[corner].getX();
		double b = getY() - corners[corner].getY();
		return Math.sqrt(a*a + b*b);
	}
	
	/**
	 * onScannedShip:  Stop and fire!
	 */
	public void onScannedShip(ScannedShipEvent e) {
		if(backwards){
			slot1().setTurnRightDegrees(slot1().getBearingDegrees(e));
		}
		else{
			slot3().setTurnRightDegrees(slot1().getBearingDegrees(e));
		}
		smartFire(e.getDistance());
		
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

	public void onHitRobot(HitRobotEvent e){
		if(backwards)
			slot3().fire();
		else
			slot1().fire();
	}

	/**
	 * smartFire:  Custom fire method that determines firepower based on distance.
	 *
	 * @param robotDistance the distance to the robot to fire at
	 */
	public void smartFire(double robotDistance) {
		if (robotDistance > 600 || getEnergy() < 15) {
			if(!backwards)
				slot3().fire();
			else
				slot1().fire();
		} else if (robotDistance > 300) {
			if(!backwards)
				slot3().fire();
			else
				slot1().fire();
		} else {
			if(!backwards)
				slot3().fire();
			else
				slot1().fire();
		}
	}
}
