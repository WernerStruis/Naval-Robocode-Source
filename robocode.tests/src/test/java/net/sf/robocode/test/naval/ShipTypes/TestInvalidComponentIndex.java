package net.sf.robocode.test.naval.ShipTypes;

import static org.junit.Assert.*;
import org.junit.Test;

import robocode.naval.*;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.RadarComponents.ShortRangeRadar;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class TestInvalidComponentIndex {

    ComponentManager manager;

    @Test
    public void testCruiser(){
        manager = new ComponentManager(ShipType.CRUISER);
        assertTrue("Component size is not right!", manager.getComponentsList().length == ShipType.CRUISER.getMaxSlots());

        //should be okay
        manager.setSlot(ComponentManager.SLOT1, new SingleBarrelCannon());

        //should be okay
        manager.setSlot(ComponentManager.SLOT2, new ShortRangeRadar());

        assertTrue("Component size is not right!", manager.getComponentsList().length == ShipType.CRUISER.getMaxSlots());

        //should do nothing
        manager.setSlot(ComponentManager.SLOT3, new ShortRangeRadar());

        //should do nothing
        manager.setSlot(ComponentManager.SLOT4, new ShortRangeRadar());
    }

    @Test
    public void testCorvette(){
        manager = new ComponentManager(ShipType.CORVETTE);
        assertTrue("Component size is not right!", manager.getComponentsList().length == ShipType.CORVETTE.getMaxSlots());

        //should be okay
        manager.setSlot(ComponentManager.SLOT1, new SingleBarrelCannon());

        //should be okay
        manager.setSlot(ComponentManager.SLOT2, new ShortRangeRadar());

        //should be okay
        manager.setSlot(ComponentManager.SLOT3, new ShortRangeRadar());

        assertTrue("Component size is not right!", manager.getComponentsList().length == ShipType.CORVETTE.getMaxSlots());

        //should do nothing
        manager.setSlot(ComponentManager.SLOT4, new ShortRangeRadar());


    }

    @Test
    public void testCarrier(){
        manager = new ComponentManager(ShipType.CARRIER);
        assertTrue("Component size is not right!", manager.getComponentsList().length == ShipType.CARRIER.getMaxSlots());

        //should be okay
        manager.setSlot(ComponentManager.SLOT1, new SingleBarrelCannon());

        //should be okay
        manager.setSlot(ComponentManager.SLOT2, new ShortRangeRadar());

        //should be okay
        manager.setSlot(ComponentManager.SLOT3, new ShortRangeRadar());

        //should be okay
        manager.setSlot(ComponentManager.SLOT4, new ShortRangeRadar());

        assertTrue("Component size is not right!", manager.getComponentsList().length == ShipType.CARRIER.getMaxSlots());

    }

}
