package robocode;


import robocode.naval.*;
import robocode.naval.Components.ComponentBase;
import robocode.naval.interfaces.componentInterfaces.IComponent;
import robocode.robotinterfaces.peer.IBasicShipPeer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 * @version 0.3
 * @since 1.8.3.0 Alpha 1
 */
public abstract class CarrierShip<SLOT1 extends IComponent, SLOT2 extends IComponent, SLOT3 extends IComponent, SLOT4 extends IComponent> extends Ship {

    //method to get user defined component types for all slots
    private void initSlots() {
        setSlot(ComponentManager.SLOT1, setSlot1());
        setSlot(ComponentManager.SLOT2, setSlot2());
        setSlot(ComponentManager.SLOT3, setSlot3());
        setSlot(ComponentManager.SLOT4, setSlot4());
    }

    //method to set all user defined component types
    private void setSlot(int slotIndex, IComponent component) {
        if (peer != null) {
            ((IBasicShipPeer) peer).setSlot(slotIndex, (ComponentBase) component);
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
    public abstract SLOT3 setSlot3();
    public abstract SLOT4 setSlot4();

    //get the diffrent component at slots;
    public final SLOT1 slot1(){
        return (SLOT1) getSlot(ComponentManager.SLOT1) ;
    }

    //get the diffrent component at slots;
    public final SLOT2 slot2(){
        return (SLOT2) getSlot(ComponentManager.SLOT2) ;
    }

    //get the diffrent component at slots;
    public final SLOT3 slot3(){
        return (SLOT3) getSlot(ComponentManager.SLOT3) ;
    }

    //get the diffrent component at slots;
    public final SLOT4 slot4(){
        return (SLOT4) getSlot(ComponentManager.SLOT4) ;
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