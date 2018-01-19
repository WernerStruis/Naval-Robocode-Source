//package tested.ships;
//
//import robocode.CorvetteShip;
//import robocode.Ship;
//import robocode.naval.Components.CannonComponents.CannonComponent;
//import robocode.naval.CannonType;
//import robocode.naval.Components.RadarComponents.RadarComponent;
//import robocode.naval.RadarType;
//
///**
// * @author Thales B.V. / Colin Heppener (Naval Robocode contributor)
// */
//public class MissileShip extends CorvetteShip<CannonComponent, RadarComponent, CannonComponent> {
//
//    @Override
//    public RadarType getRadarType() {
//        return RadarType.LONG_RANGE;
//    }
//
//    @Override
//    public CannonType getFrontCannonType() {
//        return CannonType.SINGLE_BARREL;
//    }
//
//    @Override
//    public CannonType getBackCannonType() {
//        return CannonType.DOUBLE_BARREL;
//    }
//
//
//    public void run(){
//        super.run();
//
//        launchMissile(30);
//        System.out.println("launched Missile");
//    }
//}
