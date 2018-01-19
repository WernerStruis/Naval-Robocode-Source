package robocode.naval.interfaces.componentInterfaces;

import robocode.ScannedMissileEvent;
import robocode.ScannedShipEvent;
import robocode.robotinterfaces.ITransformable;
import robocode.robotinterfaces.peer.IBasicShipPeer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Interface that defines methods required by components.
 * @author Thales B.V. / Jiri Waning
 * @since 1.8.3.0 Alpha 1
 * @version 0.1
 */
public interface IComponent {

	/**
	 * Get the color of the component.
	 * @return The color of the component.
	 */
	Color getColor();
	
	/**
	 * Set the color of the component.
	 * @param color The color of the component.
	 */
	void setColor(Color color);

	/**
	 * get the serialize type (used for serializing)
	 * @return byte containing serialize type
	 */
	byte getSerializeType();

	/**
	 * Set the angle of the component; in radians.
	 * @param angle The angle to which to set the component.
	 */
	void setAngle(double angle);

	/**
	 * Get the angle of the component; in radians.
	 * @return The angle of the component; in radians.
	 */
	double getAngle();

	/**
	 * Get the pivot point of the component as a {@link java.awt.geom.Point2D Point2D} object.
	 * <p/>
	 * <i>The point is relative to that of the ship.</i>
	 * @return The pivot point of the component.
	 */
	Point2D getPivot();

	/**
	 * Get the exact location of the component. (On battlefield coordinates!)
	 * @param peer The robot the component belongs to.
	 * @return The exact location of the component.
	 */
	Point2D getOrigin(ITransformable peer);

	/**
	 * get the index of this component on the ship
	 */
	int getComponentIndex();

	void setUpComponent(int slotIndex, IBasicShipPeer owner);


	/**
	 * Attempts to turn the component.
	 * @param turnRemaining The angle in RADIANS the component still has to turn.
	 * @return The turnRemaining after the turning has been done.
	 */
	double turnRadians(double turnRemaining);

	/**
	 * Sets the lastHeading of the component in Radians.
	 * This function is for example used to determine how large the Arc of the Radar has to be.
	 * @param lastHeading The lastHeading in Radians.
	 */
	public void setLastAngle(double lastHeading);

	public double getLastAngle();

	/**
	 * Returns the bearing of this component to the scanned ship.
	 * If this component has no bearing ready, -1 is returned
	 * @param event the scannedship
	 * @return bearing of this component to the ship
	 */
	double getBearingRadians(ScannedShipEvent event);

	double getBearingDegrees(ScannedShipEvent event);


	/**
	 * Returns the bearing of this component to the scanned missile.
	 * If this component has no bearing ready, -1 is returned
	 * @param event the scanned missile
	 * @return bearing of this component to the ship
	 */
	double getBearingRadians(ScannedMissileEvent event);

	double getBearingDegrees(ScannedMissileEvent event);


}
