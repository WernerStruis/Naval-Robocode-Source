package tested.ships;

import robocode.CarrierShip;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

public class MineShip extends CarrierShip<CannonComponent, RadarComponent, MineComponent, CannonComponent> {

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

	public void run(){
		super.run();

		System.out.println("Mine placed: " + slot3().placeMine(15));
	}
}
