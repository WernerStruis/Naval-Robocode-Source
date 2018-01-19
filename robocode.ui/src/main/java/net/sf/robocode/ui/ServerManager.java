package net.sf.robocode.ui;

import net.sf.robocode.battle.Battle;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.host.CompetitionServer.CompetitionClient;
import net.sf.robocode.host.CompetitionServer.CompetitionServer;
import net.sf.robocode.host.CompetitionServer.ManagerCallBacks;
import net.sf.robocode.host.CompetitionServer.ServerCallBacks;
import net.sf.robocode.repository.IRobotSpecItem;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class ServerManager implements ManagerCallBacks {
    private CompetitionServer server;
    private Thread serverThread;
    private ServerCallBacks cb;
    private WindowManager manager;

    public ServerManager(WindowManager manager){
        this.manager = manager;
    }

    public void sendRobot(String ipString, int portNumber, IRobotSpecItem robot){
        CompetitionClient client = new CompetitionClient(ipString, portNumber, robot);
        Thread t = new Thread(client);
        t.start();
    }
    public void startCompetitionServer(BattleProperties properties){
        System.out.println("Starting the competition server.");

        server = new CompetitionServer(this, properties);
        cb = server.getCallBacks();
        serverThread = new Thread(server);
        serverThread.start();
    }

    public String getIp(){
        return cb.getIp();
    }

    public boolean closeCompetitionServer(){
        System.out.println("Closing the competition server.");
        return cb.closeServer();
    }

    @Override
    public void onRobotTransfered() {
        manager.onRobotTransfered();
    }
//
    @Override
    public void onServerStarted(String ip, int port, BattleProperties properties) {
        manager.onServerStarted(ip, port, properties);
    }

    @Override
    public void onError(String error) {
        System.err.println(error);
    }
}
