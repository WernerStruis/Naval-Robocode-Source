package robocode.naval;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public enum RadarType {

    /**
     * This defines the radartypes, with their attributes.
     */
    LONG_RANGE(0, 1200, 10),
    SHORT_RANGE(1, 600, 20),
    UNINITIALIZED(2, 0, 0),;

    private int id, scanRadius, turnSpeed;

    RadarType(int id, int scanRadius, int turnSpeed){
        this.id = id;
        this.scanRadius = scanRadius;
        this.turnSpeed = turnSpeed;
    }

    public int getId() {
        return id;
    }

    public int getScanRadius() {
        return scanRadius;
    }

    public int getTurnRate() {
        return turnSpeed;
    }

    //get the type using the id, used during deserialization
    public static RadarType getTypeByID(int id){
        switch (id){
            case 0:
                return LONG_RANGE;
            case 1:
                return SHORT_RANGE;
            case 2:
                return UNINITIALIZED;
        }
        return null;
    }

}
