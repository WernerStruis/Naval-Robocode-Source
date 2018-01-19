package tested.ships;

import robocode.CarrierShip;
import robocode.HitByBulletEvent;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

public class SittingDuck extends CarrierShip<CannonComponent, RadarComponent, MineComponent, CannonComponent> {

    @Override
    public void run() {
        super.run();
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

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        super.onHitByBullet(event);
        System.out.println("OUCH");
    }
}
