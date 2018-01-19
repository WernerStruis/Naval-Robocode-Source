package robocode.naval;
import static java.lang.Math.abs;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public enum ShipType{

    CRUISER(0, 20.4, 2, 2, 1000, 40, 120, 45, 0, 0, 0),
    CORVETTE(1, 15.4, 1, 3, 100, 40, 170, 14.5, 0, 0, 50),
    CARRIER(2, 10.4, 1, 4, 100, 40, 207, -15, 0, 0, 0),
    NO_SHIP(-1, 8, 10, 2, 100, 40, 200, 0, 0, 0, 0);


    public final int
            width,
            height,
            halfWidth,
            halfHeight;


    public final double
            radarOffset, // center, center    PivotY - central radar Y = radar Y
            fWeaponOffset, // center, front
            bWeaponOffset, // center, back
            mWeaponOffset,
            prowOffset,
            align;

    private int ID;

    private int maxSlots;
//    private double maxVelocity, maxTurnRate, energy;


    ShipType(int ID, double maxVelocity, double maxTurnRate, int maxSlots, double energy, int width, int height, double radarAlign, double frontAlign, double backAlign, double mAlign) {
        this.ID = ID;
//        this.maxVelocity = maxVelocity;
//        this.maxTurnRate = maxTurnRate;
        this.maxSlots = maxSlots;
//        this.energy = energy;
        this.width = width;
        this.height = height;
        this.halfWidth = width >> 1;
        this.halfHeight = height >> 1;
        this.align = frontAlign;
        this.prowOffset = height / 4.14;
        radarOffset = (halfHeight - prowOffset) + radarAlign;
        fWeaponOffset = (34.0d - prowOffset) + frontAlign;
        bWeaponOffset = (height - prowOffset - 24.0d) + backAlign;
        mWeaponOffset = (height - prowOffset - 70.0d) + mAlign;
    }



    public double getAlign() {
        return align;
    }

    public double getProwOffset() {
        return prowOffset;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public int getID() {
        return ID;
    }

//    public double getMaxVelocity() {
//        return maxVelocity;
//    }
//
//    public double getMaxTurnRate() {
//        return maxTurnRate;
//    }

//    public double getTurnRate(double velocity) {
//        return maxTurnRate - 0.75 * abs(velocity);
//    }
//
//    public double getMaxTurnRateRadians() {
//        return Math.toRadians(maxTurnRate);
//    }
//
//    public double getTurnRateRadians(double velocity) {
//        return Math.toRadians(getTurnRate(velocity));
//    }
//
//    public double getEnergy() {
//        return energy;
//    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    public double getRadarOffset() {
        return radarOffset;
    }

    public double getfWeaponOffset() {
        return fWeaponOffset;
    }

    public double getbWeaponOffset() {
        return bWeaponOffset;
    }

    public double getmWeaponOffset() {
        return mWeaponOffset;
    }

    public static ShipType getTypeByID(int id) {
        switch (id) {
            case 0:
                return CRUISER;
            case 1:
                return CORVETTE;
            case 2:
                return CARRIER;
            default:
                return NO_SHIP;
        }
    }
}
