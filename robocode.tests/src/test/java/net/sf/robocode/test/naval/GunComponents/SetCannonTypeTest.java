package net.sf.robocode.test.naval.GunComponents;

import static org.junit.Assert.*;
import org.junit.Test;
import robocode.naval.*;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.ShortRangeRadar;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class SetCannonTypeTest {
    ComponentManager manager;

    public SetCannonTypeTest(){
        manager = new ComponentManager(ShipType.CARRIER);
        manager.setSlot(ComponentManager.SLOT2, new LongRangeRadar());
        manager.setSlot(ComponentManager.SLOT1, new SingleBarrelCannon());
        manager.setSlot(ComponentManager.SLOT4, new SingleBarrelCannon());
        manager.setSlot(ComponentManager.SLOT3, new MineComponent());
    }

    @Test
    public void isTypeCorrect(){
        assertTrue("Type not correct", manager.getSlot(ComponentManager.SLOT1) instanceof SingleBarrelCannon);
        assertTrue("Type not correct", manager.getSlot(ComponentManager.SLOT2) instanceof LongRangeRadar);
        assertTrue("Type not correct", manager.getSlot(ComponentManager.SLOT3) instanceof MineComponent);
        assertTrue("Type not correct", manager.getSlot(ComponentManager.SLOT4) instanceof SingleBarrelCannon);
    }

    @Test
    public void isTypeFalse(){
        assertFalse("Type not correct", manager.getSlot(ComponentManager.SLOT1) instanceof DoubleBarrelCannon);
        assertFalse("Type not correct", manager.getSlot(ComponentManager.SLOT2) instanceof ShortRangeRadar);
        assertFalse("Type not correct", manager.getSlot(ComponentManager.SLOT4) instanceof DoubleBarrelCannon);

    }
}
