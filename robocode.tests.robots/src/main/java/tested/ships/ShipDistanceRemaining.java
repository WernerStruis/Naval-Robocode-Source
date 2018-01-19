package tested.ships;

import robocode.CorvetteShip;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

public class ShipDistanceRemaining extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {

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

	public void run(){
		super.run();

		setAhead(150);
		while(getDistanceRemaining() != 0)
			execute();
		
		setBack(200);
	}
}
