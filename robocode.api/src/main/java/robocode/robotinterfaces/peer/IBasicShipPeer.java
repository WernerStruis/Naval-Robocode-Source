package robocode.robotinterfaces.peer;

import robocode.Bullet;
import robocode.Mine;
import robocode.Missile;
import robocode.naval.*;
import robocode.naval.Components.ComponentBase;
import robocode.naval.interfaces.componentInterfaces.IComponent;

/**
 * An interface that describes all the functions in ShipPeer 
 * that are available the call from Ship.
 * @author Thales B.V. / Thomas Hakkers
 *
 */
public interface IBasicShipPeer extends IBasicRobotPeer {

	/**
	 * Returns the chosen shipType
	 * @return chosen shipType
	 */
	ShipType getShipType();

	/**
	 * Returns the offset based on the slotIndex
	 * @return slot offset
	 */
	double getSlotOffset(int slotIndex);


	/**
	 * Set the component for slot index = slotIndex
	 * @param slotIndex index in manager
	 * @param component specified component
	 */
	void setSlot(int slotIndex, IComponent component);

	/**
	 * get the component for slot index = slotIndex
	 * @param slotIndex index in manager
	 */
	ComponentBase getSlot(int slotIndex);


	/**
	 * Rotates the component by the specified angle in degrees.
	 * @param index The index of the component that has to rotate.
	 * @param angle The angle to turn the component in degrees.
	 */
	void rotate(int index, double angle);

	/**
	 * Returns the angleRemaining of the given index in radians
	 * @param index The index of the component you want to know the remaining angle of
	 * @return The angleRemaining of the given componentindex in radians
	 */
	double getAngleRemainingComponent(int index);

	double getBodyHeadingDegrees();
	double getBodyHeadingRadians();


	/**
	 * Returns the turnRemaining for the component at the given index in degrees.
	 *
	 * @param index The index of the component you want to know the turnRemaining of
	 * @return The amount the given component still needs to turn in degrees.
	 */
	double getComponentTurnRemainingDegrees(int index);
	double getComponentTurnRemainingRadians(int index);

	/**
	 * Returns the absolute heading of the component in degrees.
	 * Return value is between 0 and 360, where 90 degrees would be equivalent to east.
	 * The value is NOT relative to the ship; it's relative to the battlefield.
	 * @param index The index of the component you want to know the heading of.
	 * @return The heading of the component in degrees.
	 */
	double getComponentHeadingDegrees(int index);

	/**
	 * Returns the absolute heading of the component in radians.
	 * @param index The index of the component you want to know the heading of.
	 * @return The heading of the component in radians.
	 */
	double getComponentHeadingRadians(int index);

	/**
	 * Sets whether the component is independent or not
	 * @param index The index of the component you want to change the dependency of
	 * @param independent true for independent movement, false if you want the component to be stuck to the ship.
	 */
	void setAdjustComponentForShipTurn(int index, boolean independent);

	int getMaxComponents();

	/**
	 * {@inheritDoc}
	 */
	Bullet fireComponent(int index);

	Missile fireComponentMissile(int index);


	/**
	 * {@inheritDoc}
	 */
	Mine mine(double power, int index);


}
