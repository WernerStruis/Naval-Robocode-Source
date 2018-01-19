package robocode.naval.Components.CannonComponents;

import net.sf.robocode.security.IHiddenComponentHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.naval.CannonType;
import robocode.naval.Components.ComponentBase;

import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class SingleBarrelCannon extends CannonComponent {

    private final int POWER = 1;

    /** Preserved for use during the serialization! **/
    public SingleBarrelCannon() {
        this(0.0d, 0.0d, 0.0d, 0.0d, 0.0d);
    }

    public SingleBarrelCannon(double startingAngle) {
        this(0.0d, 0.0d, 0.0d, 0.0d, startingAngle);
    }

    private SingleBarrelCannon(double x, double y, double start, double extent, double startingAngle) {
        super(new Point2D.Double(x, y), CannonType.SINGLE_BARREL, start, extent, startingAngle);
    }

    @Override
    public void fire() {
        if (!inBlindSpot()) {
            setFirePower(owner.getEnergy(), this.POWER);
            owner.fireComponent(slotIndex);
        }
    }

    // ======================================================================
    // Serialize for this component.
    // ======================================================================
    public byte getSerializeType(){
        return RbSerializer.SingleBarrelCannon_TYPE;
    }

    /**
     * Creates a hidden weapon component helper for accessing hidden methods on this object.
     *
     * @return a hidden weapon component helper.
     */
    // this method is invisible on RobotAPI
    static IHiddenComponentHelper createHiddenHelper() {
        return new HiddenWeaponHelper();
    }

    /**
     * Creates a hidden weapon component helper for accessing hidden methods on this object.
     *
     * @return a hidden weapon component helper.
     */
    // this class is invisible on RobotAPI
    static ISerializableHelper createHiddenSerializer() {
        return new HiddenWeaponHelper();
    }

    // this class is invisible on RobotAPI
    private static class HiddenWeaponHelper extends HiddenComponentHelper {

        private ISerializableHelper blindSpotHelper;

        /**
         * {@inheritDoc}
         */
        public void updateSub(ComponentBase component) {
            blindSpotHelper = BlindSpot.createHiddenSerializer();
        }

        /**
         * {@inheritDoc}
         */
        protected int getSize(RbSerializer serializer, Object object) {
            int size = 0;
            size += blindSpotHelper.sizeOf(serializer, ((SingleBarrelCannon)(object)).blindSpot);
            size += 5 * RbSerializer.SIZEOF_DOUBLE;
            size += 1 * RbSerializer.SIZEOF_BOOL;
            return size;
        }

        /**
         * {@inheritDoc}
         */
        protected void serializeSub(RbSerializer serializer, ByteBuffer buffer, Object object) {
            SingleBarrelCannon obj = (SingleBarrelCannon)object;

            // Serialize all the fields.
            blindSpotHelper.serialize(serializer, buffer, object);
            serializer.serialize(buffer, obj.gunHeat);
            serializer.serialize(buffer, obj.power);
            serializer.serialize(buffer, obj.getStartingAngle());
        }

        /**
         * {@inheritDoc}
         */
        protected void deserializeSub(RbSerializer serializer, ByteBuffer buffer, Object component) {
            SingleBarrelCannon weapon = (SingleBarrelCannon)component;

            // De-serialize the fields.
            weapon.blindSpot = (BlindSpot) blindSpotHelper.deserialize(serializer, buffer);
            weapon.gunHeat = serializer.deserializeDouble(buffer);
            weapon.power = serializer.deserializeDouble(buffer);
            weapon.startingAngle = serializer.deserializeDouble(buffer);
        }

        /**
         * {@inheritDoc}
         */
        protected ComponentBase getObject() {
            return new SingleBarrelCannon();
        }
    }
}
