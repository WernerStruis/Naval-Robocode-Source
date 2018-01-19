package net.sf.robocode.core;

import net.sf.robocode.BlocklyCallbacks.BlocklyCallBacks;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.ui.IWindowManager;

import java.util.List;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class Main {
    private String[] args;
    public static BlocklyCallBacks cb;


    public Main(String[] args, BlocklyCallBacks callbacks) {
        this.args = args;

        cb = callbacks;
    }

    public void run() {
        System.out.println("Main.java - run - Called!");
        HiddenAccess.navalRobocodeMain(args);
//        runBattle();
    }

    public void runBattle() {
        try {
            Thread.sleep(2000);
            IBattleManager manager = Container.getComponent(IBattleManager.class);

//            manager.setRepository(Container.cache.getComponent(IRepositoryManager.class));

            BattleProperties properties = new BattleProperties();
            properties.setSelectedRobots("navalsample.NavalFire*, navalsample.NavalFire*");
            manager.startNewBattle(properties, true, false);
            manager.resumeBattle();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<IRobotSpecItem> getRobotList() {
        IRepositoryManager manager = Container.getComponent(IRepositoryManager.class);
        List<IRobotSpecItem> items = manager.getRepositoryItems(false, false, false, false, false, false, false, true);

        return items;
    }


    public void showRobocode(boolean visible) {
        IWindowManager windowManager = Container.getComponent(IWindowManager.class);
        windowManager.showRobocodeFrame(visible, false);
    }

    public void setClassPath(String classPath) {
        System.getProperties().setProperty("robocode.class.path", classPath);
    }


    public static void main(String[] args) {

        System.out.println("HI");
        new Main(args, null).run();
    }
}
