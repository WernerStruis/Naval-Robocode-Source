package net.sf.robocode.battle.peer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class ShipTypePeer {

    private double maxVelocity, maxTurnRate, energy;

    public ShipTypePeer(double maxVelocity, double maxTurnRate, double energy) {
        this.maxVelocity = maxVelocity;
        this.maxTurnRate = maxTurnRate;
        this.energy = energy;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public double getMaxTurnRate() {
        return maxTurnRate;
    }

    public double getEnergy() {
        return energy;
    }
}
