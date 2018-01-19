package robocode.naval;

import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public enum CannonType implements Serializable {

    /**
     * This defines the radartypes, with their attributes.
     */
    /**
     * Original:
     * turn rate = 8
     * damagemultiplier = 4
     * bunusmultiplier = 2
     * bulletspeedMultiplier = 1
     * maxbulletpower = 3
     */
    ORIGINAL        (0, 8,  4, 2, 1, 3),
    SINGLE_BARREL   (1, 16, 4, 2, 2, 3),
    DOUBLE_BARREL   (2, 8,  5, 2, 1, 6),
    UNINITIALIZED   (3, 0, 0, 0, 0, 0);

    private int id;
    private double turnRate, damageMultiplier, bonusMultiplier, bulletSpeedMultiplier, maxBulletPower;


    //base constructor
    CannonType(int id, double turnRate, double damageMultiplier, double bonusMultiplier, double bulletSpeedMultiplier, double maxBulletPower) {
        this.id = id;
        this.turnRate = turnRate;
        this.damageMultiplier = damageMultiplier;
        this.bonusMultiplier = bonusMultiplier;
        this.bulletSpeedMultiplier = bulletSpeedMultiplier;
        this.maxBulletPower = maxBulletPower;
    }

    //returns the id
    public int getId() {
        return id;
    }

    //get the type using the id, used during deserialization
    public static CannonType getTypeByID(int id){
        switch (id){
            case 0:
                return ORIGINAL;
            case 1:
                return SINGLE_BARREL;
            case 2:
                return DOUBLE_BARREL;
            case 3:
                return UNINITIALIZED;
        }
        return null;
    }

    /**
     * Methods for external use
     */
//    public double getBulletDamage(double bulletPower) {
//        double damage = damageMultiplier * bulletPower;
//
//        if (bulletPower > 1) {
//            damage += 1.2 * (bulletPower - 1);
//        }
//        return damage;
//    }

//    public double getBulletHitBonus(double bulletPower) {
//        return bonusMultiplier * bulletPower;
//    }

//    public double getBulletSpeed(double bulletPower) {
//        bulletPower = Math.min(Math.max(bulletPower, NavalRules.MIN_BULLET_POWER), maxBulletPower);
//        return (20 - 3 * bulletPower) * bulletSpeedMultiplier;
//    }

    /**
     * Getters
     */
    public double getTurnRate() {
        return turnRate;
    }

    public double getMaxBulletPower() {
        return maxBulletPower;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public double getBonusMultiplier() {
        return bonusMultiplier;
    }

    public double getBulletSpeedMultiplier() {
        return bulletSpeedMultiplier;
    }

    /**
     * Serializer methods
     */
    static ISerializableHelper createHiddenSerializer() {
        return new SerializableHelper();
    }

    public byte getSerializeType(){
        return RbSerializer.GunType_TYPE;
    }

    private static class SerializableHelper implements ISerializableHelper {
        public int sizeOf(RbSerializer serializer, Object object) {
            return RbSerializer.SIZEOF_INT;
        }

        public void serialize(RbSerializer serializer, ByteBuffer buffer, Object object) {
            CannonType gt = (CannonType) object;

            serializer.serialize(buffer, gt.id);
        }

        public Object deserialize(RbSerializer serializer, ByteBuffer buffer) {
            int id = buffer.getInt();

            return CannonType.getTypeByID(id);
        }
    }
}
