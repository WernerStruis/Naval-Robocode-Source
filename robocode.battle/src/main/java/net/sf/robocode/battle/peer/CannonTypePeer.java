package net.sf.robocode.battle.peer;

import robocode.naval.CannonType;
import robocode.naval.NavalRules;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class CannonTypePeer {
    private double turnRate, damageMultiplier, bonusMultiplier, bulletSpeedMultiplier, maxBulletPower;

    public CannonTypePeer(double turnRate, double damageMultiplier, double bonusMultiplier, double bulletSpeedMultiplier, double maxBulletPower) {
        this.turnRate = turnRate;
        this.damageMultiplier = damageMultiplier;
        this.bonusMultiplier = bonusMultiplier;
        this.bulletSpeedMultiplier = bulletSpeedMultiplier;
        this.maxBulletPower = maxBulletPower;
    }




    /**
     * Basic getters
     * @return
     */

    public double getTurnRate() {
        return turnRate;
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

    public double getMaxBulletPower() {
        return maxBulletPower;
    }
}
