package robocode.naval.Components;

import net.sf.robocode.security.IHiddenComponentHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.Missile;
import robocode.exception.RobotException;
import robocode.naval.CannonType;
import robocode.naval.Components.CannonComponents.BlindSpot;
import robocode.naval.Components.CannonComponents.SingleBarrelCannon;
import robocode.naval.NavalRules;
import robocode.naval.interfaces.componentInterfaces.IMissileFunctions;
import robocode.robotinterfaces.ITransformable;
import robocode.robotinterfaces.peer.IBasicShipPeer;
import robocode.util.Utils;

import java.awt.geom.Point2D;
import java.nio.ByteBuffer;

import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class MissileComponent extends ComponentBase implements IMissileFunctions {

    //starting angle of this component
    double startingAngle;

    //The fire power of the missile.
    double power;

    //this components attribute values
    private double turnRate = -1, damageMultiplier = -1, bonusMultiplier = -1, bulletSpeedMultiplier = -1, maxBulletPower = -1;

    // The current (over)heat of the gun.
    double gunHeat;

    public MissileComponent() {
        this(0.0d, 0.0d, 0.0d);
    }

    public MissileComponent(double startingAngle) {
        this(0.0d, 0.0d, startingAngle);
    }

    private MissileComponent(double x, double y, double startingAngle) {
        super(new Point2D.Double(x, y), 0.0d);
        this.startingAngle = startingAngle;
    }


    @Override
    public void setUpComponent(int slotIndex, IBasicShipPeer owner) {
        this.slotIndex = slotIndex;
        this.owner = owner;

        setPivot(new Point2D.Double(0, owner.getSlotOffset(slotIndex)));


        setDefaultHeading();
    }

    /**
     * Get the angle towards which the bullet gets fired.
     *
     * @param peer The ship that fired the bullet.
     * @return The angle towards which the bullet gets fired.
     */
    public double getFireAngle(ITransformable peer) {
        return PI - getAngle() - peer.getBodyHeading();
    }

    @Override
    public double getAngle() {
        return angle;
    }

    public double getGunHeat() {
        return gunHeat;
    }

    public double getGunHeat(double power) {
        return 1 + (power / 2);
    }

    public void updateGunHeat(double gunHeat) {
        this.gunHeat += gunHeat;
    }

    /**
     * Cool down the weapon. This is required to fire once more.
     *
     * @param coolingRate The rate at which to cool down the weapon.
     */
    public void coolDown(double coolingRate) {
        gunHeat -= coolingRate;
        if (gunHeat < 0.0d) {
            gunHeat = 0.0d;
        }
    }


    /**
     * Sets the default heading of the weapon based on its type.
     */
    private void setDefaultHeading() {
        super.setAngle(getTypeOffset() + startingAngle);
    }

    /**
     * Get the angular offset in radians based upon the weapon type.
     * THOMA_NOTE: Basically makes sure that back cannons are facing south and that front cannons are facing north. (Returns PI/2 for front cannon and 3PI/2 for back cannon)
     *
     * @return The angular offset in radians based upon the weapon type.
     */
    private double getTypeOffset() {
        if (slotIndex < (owner.getMaxComponents() / 2)) {
            return 0;
        } else {
            return PI;
        }

    }

    public void setAttributeValues(double turnRate, double damageMultiplier, double bonusMultiplier, double bulletSpeedMultiplier, double maxBulletPower) {
        checkCaller();
        this.turnRate = turnRate;
        this.damageMultiplier = damageMultiplier;
        this.bonusMultiplier = bonusMultiplier;
        this.bulletSpeedMultiplier = bulletSpeedMultiplier;
        this.maxBulletPower = maxBulletPower;
    }

    private void checkCaller() {
        try {
            throw new Exception("Who called me?");
        } catch (Exception e) {
            if (!e.getStackTrace()[2].getClassName().equals("net.sf.robocode.battle.peer.ShipPeer") && !e.getStackTrace()[2].getClassName().equals("net.sf.robocode.test.naval.MissileComponentTest") ) {
                throw new RobotException("Cheat detected: Calling setAttributeValues from ship is forbidden.");
            }
        }
    }


    @Override
    public double turnRadians(double turnRemaining) {
        double turnRate = getTurnRateRadians();
        double trueTurnRemaining = turnRemaining;

        if (turnRemaining == Double.POSITIVE_INFINITY) {
            trueTurnRemaining = Utils.normalAbsoluteAngle(angle);
        } else if (turnRemaining == Double.NEGATIVE_INFINITY) {
            trueTurnRemaining = Utils.normalAbsoluteAngle(angle);
        }
        if (trueTurnRemaining > 0) {
            //turn right
            if (trueTurnRemaining < turnRate) {
                //not more than turnRate
                angle += trueTurnRemaining;
                return 0;
            } else {
                //more that turnrate
                angle += turnRate;
                return trueTurnRemaining - turnRate;
            }
        } else {
            //turn left
            //turn right
            if (trueTurnRemaining > -turnRate) {
                //not more than turnRate
                angle += trueTurnRemaining;
                return 0;
            } else {
                //more that turnrate
                angle -= turnRate;
                return trueTurnRemaining - turnRate;
            }
        }
    }

    @Override
    public Missile launch(double firePower) {
        setMissilePower(owner.getEnergy(), power);
        return launch();
    }

    /**
     * Function called internally to launch a missile at the specified index(always front cannon)
     *
     */
    private Missile launch() {
        return owner.fireComponentMissile(slotIndex);
    }

    @Override
    public void setMissilePower(double energy, double power) {
        this.power = min(energy, min(max(power, NavalRules.MIN_BULLET_POWER), maxBulletPower));
    }

    /**
     * IMovementFunctions
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTurnLeftDegrees(double angle) {
        owner.rotate(slotIndex, -angle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTurnRightDegrees(double angle) {
        owner.rotate(slotIndex, angle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTurnLeftRadians(double angle) {
        setTurnLeftDegrees(Math.toDegrees(angle));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTurnRightRadians(double angle) {
        setTurnRightDegrees(Math.toDegrees(angle));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTurnRateDegrees() {
        return turnRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTurnRateRadians() {
        return Math.toRadians(getTurnRateDegrees());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeadingDegrees() {
        return (owner.getComponentHeadingDegrees(slotIndex) + (owner.getBodyHeadingDegrees() * 180 / PI)) % 360;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHeadingRadians() {
        return Math.toRadians(getHeadingDegrees());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAdjustComponentForShipTurn(boolean independent) {
        owner.setAdjustComponentForShipTurn(slotIndex, independent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTurnRemainingDegrees() {
        return owner.getComponentTurnRemainingDegrees(slotIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTurnRemainingRadians() {
        return owner.getComponentTurnRemainingRadians(slotIndex);
    }

    public double getPower() {
        return power;
    }

    /**
     * Serializer methods
     */
    // ======================================================================
    // Serialize for this component.
    // ======================================================================
    public byte getSerializeType() {
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

        /**
         * {@inheritDoc}
         */
        public void updateSub(ComponentBase component) {
            // Update this object...
        }

        /**
         * {@inheritDoc}
         */
        protected int getSize(RbSerializer serializer, Object object) {
            int size = 0;
            size += 7 * RbSerializer.SIZEOF_DOUBLE;
            return size;
        }

        /**
         * {@inheritDoc}
         */
        protected void serializeSub(RbSerializer serializer, ByteBuffer buffer, Object object) {
            MissileComponent obj = (MissileComponent) object;

            // Serialize all the fields.
            serializer.serialize(buffer, obj.startingAngle);
            serializer.serialize(buffer, obj.power);
//            serializer.serialize(buffer, obj.getStartingAngle());
        }

        /**
         * {@inheritDoc}
         */
        protected void deserializeSub(RbSerializer serializer, ByteBuffer buffer, Object component) {
            MissileComponent missileComponent = (MissileComponent) component;


        }

        /**
         * {@inheritDoc}
         */
        protected ComponentBase getObject() {
            return new SingleBarrelCannon();
        }
    }
}
