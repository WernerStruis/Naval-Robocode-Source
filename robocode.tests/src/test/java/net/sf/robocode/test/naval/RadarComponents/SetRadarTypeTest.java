package net.sf.robocode.test.naval.RadarComponents;

import static org.junit.Assert.*;
import org.junit.Test;
import robocode.naval.*;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.ShortRangeRadar;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class SetRadarTypeTest {
    ComponentManager manager;

    public SetRadarTypeTest(){
        manager = new ComponentManager(ShipType.CRUISER);
        manager.setSlot(ComponentManager.SLOT1, new LongRangeRadar());
        manager.setSlot(ComponentManager.SLOT2, new ShortRangeRadar());

    }

    @Test
    public void setRadarTypesAndValues(){
        assertTrue("RadarType not configured correct", (manager.getComponent(LongRangeRadar.class)  != null));
        assertTrue("RadarType not configured correct", (manager.getComponent(ShortRangeRadar.class) != null));

        assertTrue("RadarType not configured correct", (manager.getComponent(LongRangeRadar.class)) == manager.getSlot(ComponentManager.SLOT1));
        assertTrue("RadarType not configured correct", (manager.getComponent(ShortRangeRadar.class)) == manager.getSlot(ComponentManager.SLOT2));

        assertTrue("Radar range was not updated", manager.getComponent(LongRangeRadar.class).getScanRadius() == RadarType.LONG_RANGE.getScanRadius());
        assertTrue("Radar speed was not updated", manager.getComponent(LongRangeRadar.class).getTurnRateDegrees() == RadarType.LONG_RANGE.getTurnRate());

        assertTrue("Radar range was not updated", manager.getComponent(ShortRangeRadar.class).getScanRadius() == RadarType.SHORT_RANGE.getScanRadius());
        assertTrue("Radar speed was not updated", manager.getComponent(ShortRangeRadar.class).getTurnRateDegrees() == RadarType.SHORT_RANGE.getTurnRate());
    }





}
