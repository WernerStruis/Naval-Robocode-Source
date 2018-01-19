package net.sf.robocode.test.naval;

import static org.junit.Assert.*;

import org.junit.Test;

import robocode.naval.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.CannonComponents.DoubleBarrelCannon;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.MissileComponent;
import robocode.naval.Components.RadarComponents.LongRangeRadar;
import robocode.naval.Components.RadarComponents.RadarComponent;

public class ComponentManagerTest {
	ComponentManager manager;
	
	public ComponentManagerTest(){
		manager = new ComponentManager(ShipType.CARRIER);
		//1 Weapon, 1 Radars, 2 Mines
		manager.setSlot(ComponentManager.SLOT1, new SingleBarrelCannon());
		manager.setSlot(ComponentManager.SLOT2, new LongRangeRadar());
		manager.setSlot(ComponentManager.SLOT3, new MineComponent());
		manager.setSlot(ComponentManager.SLOT4, new DoubleBarrelCannon());
		manager.setSlot(4, new MissileComponent());
	}
	
	@Test public void test1a(){assertTrue(manager.getComponents(RadarComponent.class).length == 1);}	
	@Test public void test1b(){assertTrue(manager.getComponents(CannonComponent.class).length == 2);}
	@Test public void test1c(){assertTrue(manager.getComponents(MineComponent.class).length == 1);}
	@Test public void test1d(){assertTrue(manager.getComponents(MissileComponent.class).length == 0);}



	@Test public void test3a(){assertTrue(manager.getComponent(SingleBarrelCannon.class) != null);}
	@Test public void test3b(){assertTrue(manager.getComponent(LongRangeRadar.class) != null);}
	@Test public void test3c(){assertTrue(manager.getComponent(DoubleBarrelCannon.class) != null);}
	@Test public void test3d(){assertTrue(manager.getComponent(MissileComponent.class) == null);}

	@Test public void testMissileComponent(){
		manager = new ComponentManager(ShipType.CARRIER);
		//1 Weapon, 1 Radars, 2 Mines
		manager.setSlot(ComponentManager.SLOT1, new SingleBarrelCannon());
		manager.setSlot(ComponentManager.SLOT2, new LongRangeRadar());
		manager.setSlot(ComponentManager.SLOT3, new MineComponent());
		manager.setSlot(ComponentManager.SLOT4, new MissileComponent());
		assertTrue(manager.getComponents(MissileComponent.class).length == 1);
		assertTrue(manager.getComponents(RadarComponent.class).length == 1);
		assertTrue(manager.getComponents(CannonComponent.class).length == 1);
		assertTrue(manager.getComponents(MineComponent.class).length == 1);
	}

}
