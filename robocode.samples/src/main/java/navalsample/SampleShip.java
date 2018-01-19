package navalsample;

import robocode.CorvetteShip;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedShipEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

import java.awt.*;

public class SampleShip extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {
	int direction = 1;
	// This is the code your Ship will run
	public void run(){
		super.run();

		// The following components can all be colored seperately
		// Setup
		setBodyColor(Color.red);
		slot1().setColor(Color.orange);
		slot3().setColor(Color.yellow);
		slot2().setColor(Color.green);
		slot1().setBulletColor(Color.blue);
		slot3().setBulletColor(Color.blue);

		slot2().setScanColor(Color.magenta);
		
		//Easy way to make your radar go in circles forever
		slot2().setTurnRightRadians(Double.POSITIVE_INFINITY);
		
		// Loop
		while(true){
			//Makes sure we go 4000 units toward a certain direction
			setAhead(4000 * direction);
			//Execute makes sure your commands actually get executed. You could see it
			//as if you're ending your turn.
			//Try removing the execute command. You'll see that that your Ship
			//won't even move! This is because you'll end up saying "Go ahead!" an infinite 
			//amount of times, without ever ending your turn.
			execute();
		}
	}
	// When a wall is hit, reverse direction and make a slight turn.
	public void onHitWall(HitWallEvent event){
		direction *= -1;
		setTurnRightDegrees(45);
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

	public void onHitRobot(HitRobotEvent event){
		//Fill in something you'd like your Ship to do when it hits another Ship
	}
	
	public void onScannedShip(ScannedShipEvent event){
		// The easiest way to target the scanned ship is to use these functions
		// Turn your front cannon towards the ship
		slot1().setTurnRightRadians(slot1().getBearingRadians(event));
		//At this point you've already told your ship to move 4000 ahead,
		//AND you've told it to turn its front cannon
		//All you gotta do now is wait for your cannon to reach its destination
		while(slot1().getTurnRemainingRadians() != 0){
			execute();
			if(slot1().getAtBlindSpot())
				break;
		}
		//If your front cannon is not at its blindspot
		if(!slot1().getAtBlindSpot()){
			//Then shoot!
			slot1().fire();
		}
	}
}
