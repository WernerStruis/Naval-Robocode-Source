package tested.ships;


import robocode.CorvetteShip;
import robocode.HitWallEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

public class IndependentComponentShip extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {
	int direction = 1;
	int i = 0;
	@Override
	public void run() {
		super.run();
		//Make sure every component moves independently
		slot1().setAdjustComponentForShipTurn(true);
		slot2().setAdjustComponentForShipTurn(true);
		slot3().setAdjustComponentForShipTurn(true);
		setTurnRightRadians(Double.POSITIVE_INFINITY);
				
		while (true) {
			System.out.print(i + ": " + slot2().getHeadingDegrees());
			setTurnRightDegrees(90);
			setAhead(50*direction);
			execute();
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

	@Override
	public void onHitWall(HitWallEvent e){
		direction *= -1;
	}
}
