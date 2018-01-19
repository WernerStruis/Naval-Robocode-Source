package net.sf.robocode.ComponentProperties;

import net.sf.robocode.battle.BaseBattle;
import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.CannonTypePeer;
import net.sf.robocode.battle.peer.RadarTypePeer;
import net.sf.robocode.battle.peer.ShipTypePeer;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Test;
import robocode.naval.CannonType;
import robocode.naval.RadarType;
import robocode.naval.ShipType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;



/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class BattlePropertiesTest extends BaseBattle {
    private BattleProperties properties;
    private String customProperties = "/Properties/robocode-with-custom.properties";
    private String noCustomProperties = "/Properties/robocode-without-custom.properties";

    Map<RadarType, RadarTypePeer> RadarValues = new HashMap<RadarType, RadarTypePeer>();
    Map<CannonType, CannonTypePeer> CannonValues = new HashMap<CannonType, CannonTypePeer>();
    Map<ShipType, ShipTypePeer> ShipValues = new HashMap<ShipType, ShipTypePeer>();

    public BattlePropertiesTest(){
        super(null, null, null);

    }

    public double getBulletRange(String configString){
        String[] parts = configString.split("-");
        String configPart = parts[parts.length - 1];
        return Double.parseDouble((configPart.substring(0, configPart.length() - 1)));
    }

    @Test
    public void testWithCustoms(){
        try {
            properties = new BattleProperties();
            properties.load(new FileInputStream(BattlePropertiesTest.class.getResource(customProperties).getFile()));
            assertTrue("Properties not loaded.", properties.getShipConfiguration().equals("[0-0-0],[0-0-0],[0-0-0]"));
            assertTrue("Properties not loaded.", properties.getRadarConfiguration().equals("[0-0],[0-0]"));
            assertTrue("Properties not loaded.", properties.getCannonConfiguration().equals("[0-0-0-0-0-0],[0-0-0-0-0-0],[0-0-0-0-0-0]"));

            Battle battle = new Battle();

            String cannonConfigString = properties.getCannonConfiguration();
            double sbBulletRange = getBulletRange(cannonConfigString.split(",")[0]);
            double dbBulletRange = getBulletRange(cannonConfigString.split(",")[1]);

            assertTrue("Bullet range not correct", sbBulletRange == 0);
            assertTrue("Bullet range not correct", dbBulletRange == 0);

            createShipTypePeers(properties.getShipConfiguration());
            createRadarPeers(properties.getRadarConfiguration());
            createCannonPeers(properties.getCannonConfiguration());

            RadarTypePeer rtplr = getRadarValues(RadarType.LONG_RANGE);
            assertTrue("Properties not processed.", rtplr.getScanRadius() == 0);
            assertTrue("Properties not processed.", rtplr.getTurnSpeed() == 0);

            RadarTypePeer rtpsr = getRadarValues(RadarType.SHORT_RANGE);
            assertTrue("Properties not processed.", rtpsr.getScanRadius() == 0);
            assertTrue("Properties not processed.", rtpsr.getTurnSpeed() == 0);

            CannonTypePeer ctpsb = getCannonValues(CannonType.SINGLE_BARREL);
            assertTrue("Properties not processed.", ctpsb.getTurnRate() == 0);
            assertTrue("Properties not processed.", ctpsb.getMaxBulletPower() == 0);
            assertTrue("Properties not processed.", ctpsb.getBonusMultiplier() == 0);
            assertTrue("Properties not processed.", ctpsb.getBulletSpeedMultiplier() == 0);
            assertTrue("Properties not processed.", ctpsb.getDamageMultiplier() == 0);

            CannonTypePeer ctpdb = getCannonValues(CannonType.DOUBLE_BARREL);
            assertTrue("Properties not processed.", ctpdb.getTurnRate() == 0);
            assertTrue("Properties not processed.", ctpdb.getMaxBulletPower() == 0);
            assertTrue("Properties not processed.", ctpdb.getBonusMultiplier() == 0);
            assertTrue("Properties not processed.", ctpdb.getBulletSpeedMultiplier() == 0);
            assertTrue("Properties not processed.", ctpdb.getDamageMultiplier() == 0);

            ShipTypePeer stpcr = getShipValues(ShipType.CRUISER);
            assertTrue("Properties not processed.", stpcr.getMaxVelocity() == 0);
            assertTrue("Properties not processed.", stpcr.getMaxTurnRate() == 0);
            assertTrue("Properties not processed.", stpcr.getEnergy() == 0);

            ShipTypePeer stpco = getShipValues(ShipType.CORVETTE);
            assertTrue("Properties not processed.", stpco.getMaxVelocity() == 0);
            assertTrue("Properties not processed.", stpco.getMaxTurnRate() == 0);
            assertTrue("Properties not processed.", stpco.getEnergy() == 0);

            ShipTypePeer stpca = getShipValues(ShipType.CARRIER);
            assertTrue("Properties not processed.", stpca.getMaxVelocity() == 0);
            assertTrue("Properties not processed.", stpca.getMaxTurnRate() == 0);
            assertTrue("Properties not processed.", stpca.getEnergy() == 0);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithoutCustoms(){
        try {
            properties = new BattleProperties();
            properties.load(new FileInputStream(BattlePropertiesTest.class.getResource(noCustomProperties).getFile()));
            assertTrue("Properties not loaded.", properties.getShipConfiguration().equals("[20.4-2-100],[15.4-1-150],[10.4-1-200]"));
            assertTrue("Properties not loaded.", properties.getRadarConfiguration().equals("[1200-10],[600-20]"));
            assertTrue("Properties not loaded.", properties.getCannonConfiguration().equals("[8-4-2-1-3-400],[16-4-2-2-3-700],[8-5-2-1-6-700]"));

            String cannonConfigString = properties.getCannonConfiguration();
            double sbBulletRange = getBulletRange(cannonConfigString.split(",")[0]);
            double dbBulletRange = getBulletRange(cannonConfigString.split(",")[1]);

            assertTrue("Bullet range not correct", sbBulletRange == 400);
            assertTrue("Bullet range not correct", dbBulletRange == 700);

            Battle battle = new Battle();

            createShipTypePeers(properties.getShipConfiguration());
            createRadarPeers(properties.getRadarConfiguration());
            createCannonPeers(properties.getCannonConfiguration());

            RadarTypePeer rtplr = getRadarValues(RadarType.LONG_RANGE);
            assertTrue("Properties not processed.", rtplr.getScanRadius() == 1200);
            assertTrue("Properties not processed.", rtplr.getTurnSpeed() == 10);

            RadarTypePeer rtpsr = getRadarValues(RadarType.SHORT_RANGE);
            assertTrue("Properties not processed.", rtpsr.getScanRadius() == 600);
            assertTrue("Properties not processed.", rtpsr.getTurnSpeed() == 20);

            //[8-4-2-1-3],[16-4-2-2-3],[8-5-2-1-6]
            CannonTypePeer ctpsb = getCannonValues(CannonType.SINGLE_BARREL);
            assertTrue("Properties not processed.", ctpsb.getTurnRate() == 16);
            assertTrue("Properties not processed.", ctpsb.getDamageMultiplier() == 4);
            assertTrue("Properties not processed.", ctpsb.getBonusMultiplier() == 2);
            assertTrue("Properties not processed.", ctpsb.getBulletSpeedMultiplier() == 2);
            assertTrue("Properties not processed.", ctpsb.getMaxBulletPower() == 3);

            CannonTypePeer ctpdb = getCannonValues(CannonType.DOUBLE_BARREL);
            assertTrue("Properties not processed.", ctpdb.getTurnRate() == 8);
            assertTrue("Properties not processed.", ctpdb.getDamageMultiplier() == 5);
            assertTrue("Properties not processed.", ctpdb.getBonusMultiplier() == 2);
            assertTrue("Properties not processed.", ctpdb.getBulletSpeedMultiplier() == 1);
            assertTrue("Properties not processed.", ctpdb.getMaxBulletPower() == 6);

            //[20.4-2-100],[15.4-1-150],[10.4-1-200]
            ShipTypePeer stpcr = getShipValues(ShipType.CRUISER);
            assertTrue("Properties not processed.", stpcr.getMaxVelocity() == 20.4);
            assertTrue("Properties not processed.", stpcr.getMaxTurnRate() == 2);
            assertTrue("Properties not processed.", stpcr.getEnergy() == 100);

            ShipTypePeer stpco = getShipValues(ShipType.CORVETTE);
            assertTrue("Properties not processed.", stpco.getMaxVelocity() == 15.4);
            assertTrue("Properties not processed.", stpco.getMaxTurnRate() == 1);
            assertTrue("Properties not processed.", stpco.getEnergy() == 150);

            ShipTypePeer stpca = getShipValues(ShipType.CARRIER);
            assertTrue("Properties not processed.", stpca.getMaxVelocity() == 10.4);
            assertTrue("Properties not processed.", stpca.getMaxTurnRate() == 1);
            assertTrue("Properties not processed.", stpca.getEnergy() == 200);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RadarTypePeer getRadarValues(RadarType type) {
        return RadarValues.get(type);
    }

    public CannonTypePeer getCannonValues(CannonType type) {
        return CannonValues.get(type);
    }


    public ShipTypePeer getShipValues(ShipType type) {
        return ShipValues.get(type);
    }

    public void createShipTypePeers(String shipTypeString){
        ShipValues = new HashMap<ShipType, ShipTypePeer>();

        String[] parts = shipTypeString.split(",");

        for (int i = 0; i < parts.length; i++) {
            double maxVelocity, maxTurnRate, energy;

            parts[i] = parts[i].substring(1, parts[i].length() - 1);

            String[] values = parts[i].split("-");
            maxVelocity = Double.parseDouble(values[0]);
            maxTurnRate = Double.parseDouble(values[1]);
            energy = Double.parseDouble(values[2]);

            ShipValues.put(ShipType.getTypeByID(i), new ShipTypePeer(
                    maxVelocity,
                    maxTurnRate,
                    energy));
        }
    }

    public void createRadarPeers(String radarString) {
        RadarValues = new HashMap<RadarType, RadarTypePeer>();
//        if (battleRules.isCustomConfig()) {
        String[] parts = radarString.split(",");

        for (int i = 0; i < parts.length; i++) {
            RadarType type = RadarType.getTypeByID(i);
            RadarValues.put(type, new RadarTypePeer(
                    (Integer.parseInt(parts[i].substring(parts[i].indexOf('[') + 1, parts[i].indexOf('-')))),
                    Integer.parseInt(parts[i].substring(parts[i].indexOf('-') + 1, parts[i].indexOf(']'))
                    )));
        }
//        }
    }

    public void createCannonPeers(String cannonString) {
        CannonValues = new HashMap<CannonType, CannonTypePeer>();

        String[] parts = cannonString.split(",");

        for (int i = 0; i < parts.length; i++) {
            double turnRate, damageMultiplier, bonusMultiplier, bulletSpeedMultiplier, maxBulletPower;

            parts[i] = parts[i].substring(1, parts[i].length() - 1);

            String[] values = parts[i].split("-");
            turnRate = Double.parseDouble(values[0]);
            damageMultiplier = Double.parseDouble(values[1]);
            bonusMultiplier = Double.parseDouble(values[2]);
            bulletSpeedMultiplier = Double.parseDouble(values[3]);
            maxBulletPower = Double.parseDouble(values[4]);

            CannonValues.put(CannonType.getTypeByID(i), new CannonTypePeer(
                    turnRate,
                    damageMultiplier,
                    bonusMultiplier,
                    bulletSpeedMultiplier,
                    maxBulletPower));
        }
    }


    @Override
    protected void runRound() {

    }

    @Override
    public void setPaintEnabled(int robotIndex, boolean enable) {

    }
}
