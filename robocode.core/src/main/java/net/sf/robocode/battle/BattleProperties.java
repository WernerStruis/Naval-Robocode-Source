/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.battle;


import net.sf.robocode.io.FileUtil;
import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;
import robocode.naval.RadarType;
import robocode.naval.ShipType;

import java.io.*;
import java.util.Properties;

import static net.sf.robocode.io.Logger.logError;


/**
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class BattleProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    private final static String
            BATTLEFIELD_WIDTH = "robocode.battle.battlefieldWidth",
            BATTLEFIELD_HEIGHT = "robocode.battle.battlefieldHeight",
            BATTLE_NUMROUNDS = "robocode.battle.default.numberOfBattles",
            BATTLE_GUNCOOLINGRATE = "robocode.battle.default.gunCoolingRate",
            BATTLE_RULES_INACTIVITYTIME = "robocode.battle.default.inactivityTime",
            BATTLE_HIDE_ENEMY_NAMES = "robocode.battle.default.hideEnemyNames",
            BATTLE_SELECTEDROBOTS = "robocode.battle.selectedRobots",
            BATTLE_INITIAL_POSITIONS = "robocode.battle.initialPositions",
            BATTLE_SENTRY_BORDER_SIZE = "robocode.battle.default.sentryBorderSize",

            BATTLE_CONFIG_RADAR = "robocode.battle.config.radar",
            BATTLE_CONFIG_DEFAULT_RADAR = "robocode.battle.config.default.radar",
            BATTLE_CONFIG_CANNON = "robocode.battle.config.cannon",
            BATTLE_CONFIG_DEFAULT_CANNON = "robocode.battle.config.default.cannon",
            BATTLE_CONFIG_SHIP = "robocode.battle.config.ship",
            BATTLE_CONFIG_DEFAULT_SHIP = "robocode.battle.config.default.ship";

    ;

    private int battlefieldWidth = 2000;
    private int battlefieldHeight = 2000;
    private int numRounds = 1;
    private double gunCoolingRate = 0.1;
    private long inactivityTime = 450;
    private boolean hideEnemyNames = false;
    private int sentryBorderSize = 100;

    private double singleBarrelRange = 400;
    private double doubleBarrelRange = 700;

    private String selectedRobots;
    private String initialPositions;

    private String shipConfiguration;
    private String radarConfiguration;
    private String cannonConfiguration;


    public BattleProperties() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(FileUtil.getRobocodeConfigFile());
            this.load(in);
        } catch (FileNotFoundException e) {
            logError("No " + FileUtil.getRobocodeConfigFile().getName() + ". Using defaults.");
        } catch (IOException e) {
            logError("Error while reading " + FileUtil.getRobocodeConfigFile().getName() + ": " + e);
        } finally {
            if (in != null) {
                // noinspection EmptyCatchBlock
                try {
                    in.close();
                } catch (IOException e) {
                }

            }
        }
    }

    private final Properties props = new Properties();

    /**
     * Gets the battlefieldWidth.
     *
     * @return Returns a int
     */
    public int getBattlefieldWidth() {
        return battlefieldWidth;
    }

    /**
     * Sets the battlefieldWidth.
     *
     * @param battlefieldWidth The battlefieldWidth to set
     */
    public void setBattlefieldWidth(int battlefieldWidth) {
        this.battlefieldWidth = battlefieldWidth;
        props.setProperty(BATTLEFIELD_WIDTH, "" + battlefieldWidth);
    }

    public double getSingleBarrelBulletRange() {
        return singleBarrelRange;
    }
    public double getDoubleBarrelBulletRange() {
        return doubleBarrelRange;
    }

    public String getShipConfiguration() {
        return shipConfiguration;
    }

    public void setShipConfiguration(String shipConfiguration) {
        this.shipConfiguration = shipConfiguration;
        props.setProperty(BATTLE_CONFIG_SHIP, shipConfiguration);
    }


    public String getRadarConfiguration() {
        return radarConfiguration;
    }

    public void setRadarConfiguration(String radarConfiguration) {
        this.radarConfiguration = radarConfiguration;
        props.setProperty(BATTLE_CONFIG_RADAR, radarConfiguration);
    }

    public String getCannonConfiguration() {
        return cannonConfiguration;
    }

    public void setCannonConfiguration(String cannonConfiguration) {
        this.cannonConfiguration = cannonConfiguration;
        props.setProperty(BATTLE_CONFIG_CANNON, cannonConfiguration);
    }


    /**
     * Gets the battlefieldHeight.
     *
     * @return Returns a int
     */
    public int getBattlefieldHeight() {
        return battlefieldHeight;
    }

    /**
     * Sets the battlefieldHeight.
     *
     * @param battlefieldHeight The battlefieldHeight to set
     */
    public void setBattlefieldHeight(int battlefieldHeight) {
        this.battlefieldHeight = battlefieldHeight;
        props.setProperty(BATTLEFIELD_HEIGHT, "" + battlefieldHeight);
    }

    /**
     * Gets the numRounds.
     *
     * @return Returns a int
     */
    public int getNumRounds() {
        return numRounds;
    }

    /**
     * Sets the numRounds.
     *
     * @param numRounds The numRounds to set
     */
    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
        props.setProperty(BATTLE_NUMROUNDS, "" + numRounds);
    }

    /**
     * Returns the rate at which the gun will cool down, i.e. the amount of heat the gun heat will drop per turn.
     * <p>
     * The gun cooling rate is default 0.1 per turn, but can be changed by the battle setup.
     * So don't count on the cooling rate being 0.1!
     *
     * @return the gun cooling rate
     * @see #setGunCoolingRate(double)
     * @see Robot#getGunHeat()
     * @see Robot#fire(double)
     * @see Robot#fireBullet(double)
     * @see robocode.BattleRules#getGunCoolingRate()
     */
    public double getGunCoolingRate() {
        return gunCoolingRate;
    }

    /**
     * Sets the rate at which the gun will cool down, i.e. the amount of heat the gun heat will drop per turn.
     *
     * @param gunCoolingRate the new gun cooling rate
     * @see #getGunCoolingRate
     * @see Robot#getGunHeat()
     * @see Robot#fire(double)
     * @see Robot#fireBullet(double)
     * @see robocode.BattleRules#getGunCoolingRate()
     */
    public void setGunCoolingRate(double gunCoolingRate) {
        this.gunCoolingRate = gunCoolingRate;
        props.setProperty(BATTLE_GUNCOOLINGRATE, "" + gunCoolingRate);
    }

    /**
     * Returns the allowed inactivity time, where the robot is not taking any action, before will begin to be zapped.
     * The inactivity time is measured in turns, and is the allowed time that a robot is allowed to omit taking
     * action before being punished by the game by zapping.
     * <p>
     * When a robot is zapped by the game, it will loose 0.1 energy points per turn. Eventually the robot will be
     * killed by zapping until the robot takes action. When the robot takes action, the inactivity time counter is
     * reset.
     * <p>
     * The allowed inactivity time is per default 450 turns, but can be changed by the battle setup.
     * So don't count on the inactivity time being 450 turns!
     *
     * @return the allowed inactivity time.
     * @see robocode.BattleRules#getInactivityTime()
     * @see Robot#doNothing()
     * @see AdvancedRobot#execute()
     */
    public long getInactivityTime() {
        return inactivityTime;
    }

    /**
     * Sets the allowed inactivity time, where the robot is not taking any action, before will begin to be zapped.
     *
     * @param inactivityTime the new allowed inactivity time.
     * @see robocode.BattleRules#getInactivityTime()
     * @see Robot#doNothing()
     * @see AdvancedRobot#execute()
     */
    public void setInactivityTime(long inactivityTime) {
        this.inactivityTime = inactivityTime;
        props.setProperty(BATTLE_RULES_INACTIVITYTIME, "" + inactivityTime);
    }

    /**
     * Sets the flag defining if enemy names are hidden for robots during a battle.
     *
     * @param hideEnemyNames true if the enemy names must be hidden for a robot; false otherwise.
     * @since 1.7.3
     */
    public void setHideEnemyNames(boolean hideEnemyNames) {
        this.hideEnemyNames = hideEnemyNames;
        props.setProperty(BATTLE_HIDE_ENEMY_NAMES, "" + hideEnemyNames);
    }

    /**
     * Returns true if the enemy names are hidden for robots during a battle; false otherwise.
     *
     * @since 1.7.3
     */
    public boolean getHideEnemyNames() {
        return hideEnemyNames;
    }

    /**
     * Gets the selectedRobots.
     *
     * @return Returns a String
     */
    public String getSelectedRobots() {
        return selectedRobots;
    }

    /**
     * Sets the selectedRobots.
     *
     * @param selectedRobots The selectedRobots to set
     */
    public void setSelectedRobots(String selectedRobots) {
        this.selectedRobots = selectedRobots;
        props.setProperty(BATTLE_SELECTEDROBOTS, "" + selectedRobots);
    }

    /**
     * Sets the selectedRobots.
     *
     * @param robots The robots to set
     */
    public void setSelectedRobots(RobotSpecification[] robots) {
        StringBuffer robotString = new StringBuffer();
        RobotSpecification robot;

        for (int i = 0; i < robots.length; i++) {
            robot = robots[i];
            if (robot == null) {
                continue;
            }

            robotString.append(robot.getClassName());

            if (!(robot.getVersion() == null || robot.getVersion().length() == 0)) {
                robotString.append(' ').append(robot.getVersion());
            }

            if (i < robots.length - 1) {
                robotString.append(',');
            }
        }
        setSelectedRobots(robotString.toString());
    }

    /**
     * Gets the initial robot positions and headings.
     *
     * @return a comma-separated string containing the initial positions and headings as a comma separated string:
     * x1,y1,heading1, x2,y2,heading2
     * @since 1.7.1.2
     */
    public String getInitialPositions() {
        return initialPositions;
    }

    /**
     * Sets the initial robot positions and headings.
     *
     * @param positions is the initial positions and headings as a comma separated string:
     *                  x1,y1,heading1, x2,y2,heading2
     * @since 1.7.1.2
     */
    public void setInitialPositions(String positions) {
        this.initialPositions = positions;
    }

    /**
     * Sets the initial robot positions and headings.
     *
     * @param initialSetups is the initial positions and headings as a {@link RobotSetup} array.
     * @since 1.9.2.0
     */
    public void setInitialPositions(RobotSetup[] initialSetups) {
        if (initialSetups == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (RobotSetup setup : initialSetups) {
            String x = (setup.getX() == null) ? "?" : "" + setup.getX();
            String y = (setup.getY() == null) ? "?" : "" + setup.getY();
            String heading = (setup.getHeading() == null) ? "?" : "" + setup.getHeading();

            sb.append('(');
            sb.append(x).append(',');
            sb.append(y).append(',');
            sb.append(heading);
            sb.append("),");
        }

        this.initialPositions = sb.toString();
    }

    /**
     * Returns the sentry border size for a {@link robocode.BorderSentry BorderSentry}.
     *
     * @return the border size in units/pixels.
     */
    public int getSentryBorderSize() {
        return sentryBorderSize;
    }

    /**
     * Returns the sentry border size for a {@link robocode.BorderSentry BorderSentry}.
     *
     * @param borderSize is the sentry border size in units/pixels.
     */
    public void setSentryBorderSize(int borderSize) {
        sentryBorderSize = borderSize;
        props.setProperty(BATTLE_SENTRY_BORDER_SIZE, "" + sentryBorderSize);
    }

    public void store(FileOutputStream out, String desc) throws IOException {
        props.store(out, desc);
    }

    public void load(FileInputStream in) throws IOException {
        props.load(in);
        battlefieldWidth = Integer.parseInt(props.getProperty(BATTLEFIELD_WIDTH, "2000"));
        battlefieldHeight = Integer.parseInt(props.getProperty(BATTLEFIELD_HEIGHT, "2000"));
        gunCoolingRate = Double.parseDouble(props.getProperty(BATTLE_GUNCOOLINGRATE, "0.1"));
        inactivityTime = Long.parseLong(props.getProperty(BATTLE_RULES_INACTIVITYTIME, "450"));
        hideEnemyNames = Boolean.parseBoolean(props.getProperty(BATTLE_HIDE_ENEMY_NAMES, "false"));
        numRounds = Integer.parseInt(props.getProperty(BATTLE_NUMROUNDS, "10"));

        selectedRobots = props.getProperty(BATTLE_SELECTEDROBOTS, "");
        initialPositions = props.getProperty(BATTLE_INITIAL_POSITIONS, "");
        sentryBorderSize = Integer.parseInt(props.getProperty(BATTLE_SENTRY_BORDER_SIZE, "100"));

        radarConfiguration = props.getProperty(BATTLE_CONFIG_RADAR, props.getProperty(BATTLE_CONFIG_DEFAULT_RADAR, "[100-10],[50-20]"));
        cannonConfiguration = props.getProperty(BATTLE_CONFIG_CANNON, props.getProperty(BATTLE_CONFIG_DEFAULT_CANNON, "[8.0-4.0-2.0-2.0-3.0-400.0],[16.0-4.0-2.0-1.0-3.0-700.0],[8-5-2-1-6-700]"));
        shipConfiguration = props.getProperty(BATTLE_CONFIG_SHIP, props.getProperty(BATTLE_CONFIG_DEFAULT_SHIP, "[20.4-2-100],[15.4-1-150],[10.4-1-200]"));


        singleBarrelRange = getBulletRange(cannonConfiguration.split(",")[0]);
        doubleBarrelRange = getBulletRange(cannonConfiguration.split(",")[1]);

    }

    public double getBulletRange(String configString){
        String[] parts = configString.split("-");
        String configPart = parts[parts.length - 1];
        return Double.parseDouble((configPart.substring(0, configPart.length() - 1)));
    }
}
