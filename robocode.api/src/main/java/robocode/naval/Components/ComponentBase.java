package robocode.naval.Components;

import net.sf.robocode.security.IHiddenComponentHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.ScannedMissileEvent;
import robocode.ScannedShipEvent;
import robocode.naval.interfaces.componentInterfaces.IComponent;
import robocode.robotinterfaces.ITransformable;
import robocode.robotinterfaces.peer.IBasicShipPeer;
import robocode.util.Coordinates;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.nio.ByteBuffer;

import static java.lang.Math.PI;
import static java.lang.Math.negateExact;

/**
 * The abstract class for the basic ship components. 
 * @author Thales B.V. / Jiri Waning
 * @since 1.9.2.2
 */
public abstract class ComponentBase implements IComponent, Serializable {
	private static final long serialVersionUID = 1L;
	private Color color = Color.WHITE;

	protected IBasicShipPeer owner;
	protected int slotIndex;

	/** The relative point, based on the ship's origin, that the component is attached to. **/
	private Point2D pivot = new Point2D.Double(0.0d, 0.0d);

	/** The angle, in radians, of the component. **/
	protected double angle = 0.0d;

	/** Keeping track of the heading before the current heading. Useful for deciding the scan width **/
	protected double lastHeading = 0;


	public ComponentBase() {
		this.slotIndex = -1;
		this.owner = null;
	}

	public int getSlotIndex() {
		return slotIndex;
	}

	public void setSlotIndex(int slotIndex) {
		this.slotIndex = slotIndex;
	}

	public void setPivot(Point2D pivot){
		this.pivot = pivot;
	}

	public ComponentBase(Point2D pivot, double angle) {
		this.pivot = pivot;
		this.angle = angle;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	// ======================================================================
	//  Basic serialize for components.
	// ======================================================================
	public byte getSerializeType(){
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAngle(double angle) {
		if(angle > 2*PI){
			angle %= 2*PI;
		}
		this.angle = angle;
	}

	@Override
	public double getAngle() {
		return angle;
	}

	public void setLastAngle(double lastHeading){
		this.lastHeading = lastHeading;
	}

	public double getLastAngle(){
		return lastHeading;
	}

	/**
	 * These methods are used by ShipPeer, but preferably they need to be moved to the IMovementFunctions
	 */
	public abstract double turnRadians(double turnRemaining);
//	@Override
//	public double turnRadians(double turnRemaining){
//		System.err.println("ComponentBase.java turnRadians() called, make sure this happens by the subclasses");
//		return 0;
//	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point2D getPivot() {
		return pivot;
	}

	/**
	 * {@inheritDoc}
	 */
	public Point2D getOrigin(ITransformable peer) {
		return Coordinates.getPivot(peer, pivot.getX(), pivot.getY());
	}

	@Override
	public int getComponentIndex() {
		return slotIndex;
	}

//	@Override
//	public double getBearingRadians(ScannedShipEvent event) {
//		if(slotIndex < event.getSlotBearings().length){
//				return event.getSlotBearings()[slotIndex] - (PI / 2);
//		}
//		return -1;
//	}

	@Override
	public double getBearingDegrees(ScannedShipEvent event) {
		return Math.toDegrees(getBearingRadians(event));
	}


	@Override
	public double getBearingDegrees(ScannedMissileEvent event) {
		return Math.toDegrees(getBearingRadians(event));
	}

	@Override
	public double getBearingRadians(ScannedShipEvent event) {

		if (slotIndex < event.getSlotBearings().length) {
			return event.getSlotBearings()[slotIndex];
		}
		return -1;
	}

	@Override
	public double getBearingRadians(ScannedMissileEvent event) {

		if (slotIndex < event.getSlotBearings().length) {
			return event.getSlotBearings()[slotIndex];
		}
		return -1;
	}




	/**
	 * <i><b>Only visible towards subclasses.</b></i>
	 * <p />
	 * This class is used for the serialization of a component. Sub-classes
	 * of this class have to expand on this to serialize the super class.
	 * 
	 * @author Thales B.V. / Jiri Waning
	 * @version 0.1
	 * @since 1.8.3.0 Alpha 1
	 */
	protected abstract static class HiddenComponentHelper implements ISerializableHelper, IHiddenComponentHelper {

		@Override
		public final void update(ComponentBase component) {
			// 1st - Update this class.
			// 2nd - Update the subclass.
		}
		public abstract void updateSub(ComponentBase component);
		
		/**
		 * Add the size of the base component and request the sub-class to add his additional size too.
		 */
		@Override
		public final int sizeOf(RbSerializer serializer, Object object) {
			int size = 0;

			// 1st - Add the size of this class.
			size += 3 * RbSerializer.SIZEOF_DOUBLE; // Pivot(x+y)+Angle
			size += RbSerializer.SIZEOF_INT; // Color+Type

			// 2nd - Add the size of the subclass.
			size += getSize(serializer, object);

			return size;
		}
		protected abstract int getSize(RbSerializer serializer, Object object);

		/**
		 * Serialize the base component and request the sub-class to serialize itself too.
		 */
		@Override
		public final void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
			ComponentBase obj = (ComponentBase)object;

			// 1st - Serialize this class.
			serializer.serialize(buffer, obj.pivot.getX());
			serializer.serialize(buffer, obj.pivot.getY());
			serializer.serialize(buffer, obj.angle);
			serializer.serialize(buffer, obj.color.getRGB());

			// 2nd - Serialize the subclass.
			serializeSub(serializer, buffer, obj);
		}
		protected abstract void serializeSub(RbSerializer serializer, ByteBuffer buffer, Object object);

		/**
		 * De-serialize the base component and request the sub-class to de-serialize itself too.
		 */
		@Override
		public final Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
			ComponentBase component = getObject();

			// 1st - De-serialize this class.
			component.pivot = new Point2D.Double(
					serializer.deserializeDouble(buffer),
					serializer.deserializeDouble(buffer));
			component.angle = serializer.deserializeDouble(buffer);
			component.color = new Color(serializer.deserializeInt(buffer));

			// 2nd - De-serialize the subclass.
			deserializeSub(serializer, buffer, component);

			return component;
		}
		
		/**
		 * De-serialize the subclass of this super class.
		 * @param serializer The serializer that does the transcoding.
		 * @param buffer The buffer in which the data is stored.
		 * @param component The component to whom to restore the values.
		 */
		protected abstract void deserializeSub(RbSerializer serializer, ByteBuffer buffer, Object component);
		
		/**
		 * Get an empty object to work with.
		 * <p/>
		 * <i>The object has to come from the subclass of {@code RobotComponent}.</i>
		 * @return A new empty subclass.
		 */
		protected abstract ComponentBase getObject();
	}
}
