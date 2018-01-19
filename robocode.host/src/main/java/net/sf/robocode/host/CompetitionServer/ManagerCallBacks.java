package net.sf.robocode.host.CompetitionServer;

import net.sf.robocode.battle.BattleProperties;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public interface ManagerCallBacks {

    void onServerStarted(String ip, int port, BattleProperties properties);
    void onRobotTransfered();

    void onError(String error);

}