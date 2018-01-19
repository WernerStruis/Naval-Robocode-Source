package robocode;


import robocode.naval.*;
import robocode.naval.Components.ComponentBase;
import robocode.robotinterfaces.peer.IBasicShipPeer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 * @version 0.3
 * @since 1.8.3.0 Alpha 1
 */
public abstract class CruiserShip<SLOT1 extends ComponentBase, SLOT2 extends ComponentBase> extends Ship  {

	//method to get user defined component types for all slots
	private void initSlots(){
		setSlot(ComponentManager.SLOT1, setSlot1());
		setSlot(ComponentManager.SLOT2, setSlot2());
	}

	//method to set all user defined component types
	private void setSlot(int slotIndex, ComponentBase component){
		if (peer != null) {
			((IBasicShipPeer) peer).setSlot(slotIndex, component);
		} else {
			uninitializedException();
		}
	}

	//method to get all user defined component types
	private ComponentBase getSlot(int slotIndex){
		if (peer != null) {
			return ((IBasicShipPeer) peer).getSlot(slotIndex);
		} else {
			uninitializedException();
		}
		return null;
	}

	//set the different component types for every slot
	public abstract SLOT1 setSlot1();
	public abstract SLOT2 setSlot2();

	//get the diffrent component at slots;
	public final SLOT1 slot1(){
		return (SLOT1) getSlot(ComponentManager.SLOT1) ;
	}

	//get the diffrent component at slots;
	public final SLOT2 slot2(){
		return (SLOT2) getSlot(ComponentManager.SLOT2) ;
	}


	/**
	 * Ship methods
	 */

	@Override
	public final double getXMiddle() {
		return getX() + (ShipType.CRUISER.getProwOffset() * Math.cos(getBodyHeadingRadians() + Math.PI/2));
	}

	@Override
	public final double getYMiddle() {
		return getY() - (ShipType.CRUISER.getProwOffset() * Math.sin(getBodyHeadingRadians() + Math.PI/2));
	}

	/**
	 * This is the method you have to override to create your own ship.
	 * super.run();
	 */
	public void run() {
		super.run();
		initSlots();
	}
}
