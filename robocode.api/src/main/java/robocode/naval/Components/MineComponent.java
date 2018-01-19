package robocode.naval.Components;

import net.sf.robocode.security.IHiddenComponentHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.Mine;
import robocode.ScannedShipEvent;
import robocode.naval.interfaces.componentInterfaces.IMineFunctions;
import robocode.robotinterfaces.peer.IBasicShipPeer;

import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

/**
 * The class that represents the component on the Ship that drops a mine.
 * 
 * @author Thales B.V. / Thomas Hakkers
 */
public class MineComponent extends ComponentBase implements IMineFunctions{
	private static final long serialVersionUID = -3196489261971513847L;

	//The current time is takes to recharge.
	private double mineRecharge = 0;

	public MineComponent(){
		super(new Point2D.Double(0.0d, 0.0d), 0.0d);
	}

	@Override
	public Mine placeMine(double firePower) {
		return owner.mine(firePower, slotIndex);
	}

	@Override
	public void setUpComponent(int slotIndex, IBasicShipPeer owner) {
		this.slotIndex = slotIndex;
		this.owner = owner;

		setPivot(new Point2D.Double(0, owner.getSlotOffset(slotIndex)));
	}

	/**
	 * Sets the recharge heat for the MineComponent.
	 * @see {@link MineComponent#rechargeMine(double coolingRate) rechargeMine()}
	 * @param heat
	 */
	public void setMineRecharge(double heat){
		mineRecharge = heat;
	}

	/**
	 * Basically the same as coolDown for weapons. It's just that Mines tend to explode once they get hot
	 * @return The current "mine recharge". If it's 0, you can place a mine.
	 */
	public double getMineRecharge(){
		return mineRecharge;
	}

	/**
	 * Recharges your MineComponent.
	 * Once the mineRecharge is back to 0, a mine can be placed again.
	 * @param coolingRate The rate at which the mine is being recharged.
	 */
	public void rechargeMine(double coolingRate) {
		mineRecharge -= coolingRate;
		if (mineRecharge < 0.0d) {
			mineRecharge = 0.0d;
		}
	}

	@Deprecated
	@Override
	public final double turnRadians(double turnRemaining) {
		//TODO we want this method moved from IComonent to IMovementfuncitons
		return 0;
	}

	@Override
	public double getBearingRadians(ScannedShipEvent event) {
		return -1;
	}


	// ======================================================================
	// Serialize for this component.
	// ======================================================================

	public byte getSerializeType(){
		return RbSerializer.MineComponent_TYPE;
	}


	/**
	 * Creates a hidden mine component helper for accessing hidden methods on this object.
	 *
	 * @return a hidden mine component helper.
	 */
	// this method is invisible on RobotAPI
	static IHiddenComponentHelper createHiddenHelper() {
		return new HiddenMineComponentHelper();
	}

	/**
	 * Creates a hidden weapon component helper for accessing hidden methods on this object.
	 *
	 * @return a hidden weapon component helper.
	 */
	// this class is invisible on RobotAPI
	static ISerializableHelper createHiddenSerializer() {
		return new HiddenMineComponentHelper();
	}

	// this class is invisible on RobotAPI
	private static class HiddenMineComponentHelper extends HiddenComponentHelper {

		/**
		 * {@inheritDoc}
		 */
		protected int getSize(RbSerializer serializer, Object object) {
			int size = 0;
			size += 3 * RbSerializer.SIZEOF_DOUBLE;
			size += 1 * RbSerializer.SIZEOF_BOOL;
			return size;
		}

		/**
		 * {@inheritDoc}
		 */
		protected void serializeSub(RbSerializer serializer, ByteBuffer buffer, Object object) {
			MineComponent obj = (MineComponent)object;
			serializer.serialize(buffer, obj.mineRecharge);
		}

		/**
		 * {@inheritDoc}
		 */
		protected void deserializeSub(RbSerializer serializer, ByteBuffer buffer, Object component) {
			MineComponent weapon = (MineComponent)component;

			// De-serialize the fields.
			weapon.mineRecharge = serializer.deserializeDouble(buffer);
		}

		/**
		 * {@inheritDoc}
		 */
		protected ComponentBase getObject() {
			return new MineComponent();
		}

		@Override
		public void updateSub(ComponentBase component) {
		}
	}
}
