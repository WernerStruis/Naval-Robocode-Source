package net.sf.robocode.battle.peer;

import robocode.naval.RadarType;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class RadarTypePeer {

    private int scanRadius, turnSpeed;

    public RadarTypePeer(int scanRadius, int turnSpeed) {
        this.scanRadius = scanRadius;
        this.turnSpeed = turnSpeed;
    }

    public int getScanRadius() {
        return scanRadius;
    }

    public int getTurnSpeed() {
        return turnSpeed;
    }
}
