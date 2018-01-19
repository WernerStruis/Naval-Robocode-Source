package robocode.naval.Components.CannonComponents;

import robocode.exception.RobotException;
import robocode.naval.CannonType;
import robocode.naval.Components.ComponentBase;
import robocode.naval.NavalRules;
import robocode.naval.interfaces.componentInterfaces.ICannonFunctions;
import robocode.robotinterfaces.ITransformable;
import robocode.robotinterfaces.peer.IBasicShipPeer;
import robocode.util.Utils;

import java.awt.*;
import java.awt.geom.Point2D;

import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * A class that resembles a weapon component for on a ship.
 *
 * @author Thales B.V. / Jiri Waning / Thomas Hakkers
 * @version 1.1
 * @since 1.9.2.2
 */
public abstract class CannonComponent extends ComponentBase implements ICannonFunctions {
    private static final long serialVersionUID = 4L;

    private Color bulletColor = Color.white;

    //This is the blind arch for the weapon.
    BlindSpot blindSpot;
    private final CannonType type;

    //component attributes
    private double turnRate = -1, damageMultiplier = -1, bonusMultiplier = -1, bulletSpeedMultiplier = -1, maxBulletPower = -1;

    // The current (over)heat of the gun.
    double gunHeat;

    //The fire power of the weapon.
    double power;

    //starting angle of this component
    double startingAngle;


    /**
     * The half and full blind arc angle for any weapon. These
     * can be used to determine if a weapon is currently at the
     * minimum/maximum allowed angle. (160&deg; &amp; 80&deg;)
     */
    public static final double BLIND_SPOT_ANGLE = 65.0d,
            HALF_BLIND_SPOT_ANGLE = (BLIND_SPOT_ANGLE / 2);

    /**
     * The angular opening, in radians, of the weapons.
     */
    private static final double
            ANGULAR_OPENING = Math.toRadians(BLIND_SPOT_ANGLE), // 160 degrees
            HALF_ANGULAR_OPENING = (ANGULAR_OPENING / 2);

    /**
     * variables used to generate blindarc
     */
    private double start, extend;


    public CannonComponent(Point2D pivot, CannonType type, double start, double extent, double startingAngle) {
        super(pivot, 0.0d);

        this.type = type;
        this.turnRate = type.getTurnRate();
        this.damageMultiplier = type.getDamageMultiplier();
        this.bonusMultiplier = type.getBonusMultiplier();
        this.bulletSpeedMultiplier = type.getBulletSpeedMultiplier();
        this.maxBulletPower = type.getMaxBulletPower();

        this.power = 1.0d;
        this.gunHeat = 3.0d; // Robocode default

        this.start = start;
        this.extend = extent;
        this.startingAngle = startingAngle;
    }


    public void setAttributeValues(double turnRate, double damageMultiplier, double bonusMultiplier, double bulletSpeedMultiplier, double maxBulletPower) {
        checkCaller();
        this.turnRate = turnRate;
        this.damageMultiplier = damageMultiplier;
        this.bonusMultiplier = bonusMultiplier;
        this.bulletSpeedMultiplier = bulletSpeedMultiplier;
        this.maxBulletPower = maxBulletPower;
    }

    private void checkCaller(){
        try
        {
            throw new Exception("Who called me?");
        }
        catch( Exception e )
        {
            if(!e.getStackTrace()[2].getClassName().equals("net.sf.robocode.battle.peer.ShipPeer")){
                throw new RobotException("Cheat detected: Calling setAttributeValues from ship is forbidden.");
            }
        }
    }

    @Override
    public void setUpComponent(int slotIndex, IBasicShipPeer owner) {
        this.slotIndex = slotIndex;
        this.owner = owner;

        setPivot(new Point2D.Double(0, owner.getSlotOffset(slotIndex)));


        if (Utils.isNear(angle, 0)) {
            setStandardBlindSpot();
        } else {
            setBlindSpot(Math.toRadians(start), Math.toRadians(extend));
        }
        setDefaultHeading();
    }

    /**
     * Sets the default heading of the weapon based on its type.
     */
    private void setDefaultHeading() {
        super.setAngle(getTypeOffset() + startingAngle);
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

    public boolean inBlindSpot() {
        if (blindSpot.hasSecondBlindSpot()) {
            return blindSpot.inBlindSpot(angle) || blindSpot.getSecondBlindSpot().inBlindSpot(angle);
        } else {
            return blindSpot.inBlindSpot(angle);
        }
    }



    /**
     * Sets the blind arch based upon the type of the weapon.
     * <p>
     * The following types are supported:
     * <ul>
     * </ul>
     */

    private void setStandardBlindSpot() {
        double offset = getTypeOffset();
        start = offset - HALF_ANGULAR_OPENING - (PI);

        this.blindSpot = new BlindSpot(start, ANGULAR_OPENING, isInMiddle());
    }

    /**
     * Sets the BlindSpot for the WeaponComponent
     *
     * @param start  The starting angle of the BlindSpot
     * @param extent The extent of the BlindSpot
     */
    private void setBlindSpot(double start, double extent) {
        blindSpot = new BlindSpot(start, extent, isInMiddle());
    }

    /**
     * Get the angular offset in radians based upon the weapon type.
     * THOMA_NOTE: Basically makes sure that back cannons are facing south and that front cannons are facing north. (Returns PI/2 for front cannon and 3PI/2 for back cannon)
     *
     * @return The angular offset in radians based upon the weapon type.
     */
    public double getTypeOffset() {
        //TODO THIS ONE PROBABLY IS NOT RIGHT
        if (slotIndex < (owner.getMaxComponents() / 2)) {
            return 0;
        } else {
            return PI;
        }

    }


    /**
     * Cannon methods, as declared in ICannonFunctions
     */
    @Override
    public BlindSpot getCopyOfBlindSpot() {
        return new BlindSpot(blindSpot);
    }


    @Override
    public boolean getAtBlindSpot() {
        if (blindSpot.hasSecondBlindSpot()) {
            return Utils.isNear(angle, blindSpot.getSecondBlindSpot().getFarRight()) || Utils.isNear(angle, blindSpot.getSecondBlindSpot().getFarLeft()) || Utils.isNear(angle, blindSpot.getFarRight()) || Utils.isNear(angle, blindSpot.getFarLeft());
        } else {
            return Utils.isNear(angle, blindSpot.getFarRight()) || Utils.isNear(angle, blindSpot.getFarLeft());
        }
    }

    public abstract void fire();

//    @Override
//    public void fire(double power) {
//        if (!inBlindSpot()) {
//            setFirePower(owner.getEnergy(), power);
//            owner.fireComponent(slotIndex);
//        }
//    }

    public CannonType getType() {
        return type;
    }

    private boolean isInMiddle() {
        return slotIndex != 0 && slotIndex != owner.getMaxComponents() - 1;
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
     * Getters
     */
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
     * Set the fire power of the weapon.
     *
     * @param energy The energy the robot has left.
     * @param power  The fire power of the weapon.
     */
    public void setFirePower(double energy, double power) {
        this.power = min(energy, min(max(power, NavalRules.MIN_BULLET_POWER), maxBulletPower));
    }

    public double getPower() {
        return power;
    }

    public double getStartingAngle() {
        return startingAngle;
    }

    public Color getBulletColor() {
        return bulletColor;
    }

    /**
     * Calculate methods preferrably moved to cannontypepeer
     */
    public double getBulletDamage(double bulletPower) {
        double damage = damageMultiplier * bulletPower;

        if (bulletPower > 1) {
            damage += 1.2 * (bulletPower - 1);
        }
        return damage;
    }

    public double getBulletHitBonus(double bulletPower) {
        return bonusMultiplier * bulletPower;
    }

    public double getBulletSpeed(double bulletPower) {
        bulletPower = Math.min(Math.max(bulletPower, NavalRules.MIN_BULLET_POWER), maxBulletPower);
        return (20 - 3 * bulletPower) * bulletSpeedMultiplier;
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
    public double getTurnRemainingDegrees() {
        return owner.getComponentTurnRemainingDegrees(slotIndex);
    }

    @Override
    public double getTurnRemainingRadians() {
        return owner.getComponentTurnRemainingRadians(slotIndex);

    }

    @Override
    public void setBulletColor(Color color) {
        this.bulletColor = color;
    }


    @Override
    public void setTurnLeftDegrees(double angle) {
        owner.rotate(slotIndex, -angle);
    }

    @Override
    public void setTurnRightDegrees(double angle) {
        owner.rotate(slotIndex, angle);
    }

    @Override
    public void setTurnLeftRadians(double angle) {
        setTurnLeftDegrees(Math.toDegrees(angle));
    }

    @Override
    public void setTurnRightRadians(double angle) {
        setTurnRightDegrees(Math.toDegrees(angle));
    }

    @Override
    public double getTurnRateDegrees() {
        return turnRate;
    }

    @Override
    public double getTurnRateRadians() {
        return Math.toRadians(getTurnRateDegrees());
    }

    @Override
    public double getHeadingDegrees() {
        return (owner.getComponentHeadingDegrees(slotIndex) + (owner.getBodyHeadingDegrees() * 180 / PI)) % 360;
    }

    @Override
    public double getHeadingRadians() {
        return Math.toRadians(getHeadingDegrees());
    }

    @Override
    public void setAdjustComponentForShipTurn(boolean independent) {
        owner.setAdjustComponentForShipTurn(slotIndex, independent);
    }

    @Override
    public double getAngle() {
        return angle;
    }

}
