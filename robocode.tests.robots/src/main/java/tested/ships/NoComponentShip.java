package tested.ships;

import robocode.CruiserShip;
import robocode.naval.Components.CannonComponents.CannonComponent;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class NoComponentShip extends CruiserShip {
    @Override
    public CannonComponent setSlot1() {
        return null;
    }

    @Override
    public CannonComponent setSlot2() {
        return null;
    }

    @Override
    public void run() {
        super.run();
    }
}
