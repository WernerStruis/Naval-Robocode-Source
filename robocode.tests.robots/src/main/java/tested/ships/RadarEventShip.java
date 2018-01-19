package tested.ships;

import robocode.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

/**
 * Used to test the Radar on a ship
 * Prints out the event values when it scans a ship
 * @author Thales B.V. / Thomas Hakkers
 *
 */
public class RadarEventShip extends CarrierShip<CannonComponent, RadarComponent, MineComponent, CannonComponent> {

	public void run(){
		super.run();
		slot2().setTurnRightDegrees(Double.POSITIVE_INFINITY);
		while(true){
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
	public MineComponent setSlot3() {
		return new MineComponent();
	}

	@Override
	public CannonComponent setSlot4() {
		return new DoubleBarrelCannon();
	}


	public void onScannedShip(ScannedShipEvent event){
		//Use a seperator that isn't used anywhere else
		System.out.print(slot4().getBearingDegrees(event) + "~" + slot1().getBearingDegrees(event) + "~" + Math.toDegrees(event.getBearingDegrees()));
	}

}
