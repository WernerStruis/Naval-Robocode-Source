package net.sf.robocode.core;

import net.sf.robocode.BlocklyCallbacks.BlocklyCallBacks;
import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IWindowManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class ExternalManager {
    private String[] args;

    public static BlocklyCallBacks cb;

    //managers
    private IWindowManager windowManager;
    private IRepositoryManager repositoryManager;
    private IBattleManager battleManager;
    private ISettingsManager settingsManager;

    public ExternalManager(String[] args, BlocklyCallBacks callBacks) {
        this.args = args;
        cb = callBacks;
    }

    public void show(boolean visible) {
        getWindowManager().getRobocodeFrame().setVisible(visible);
    }

    public void runBattle(String selectedRobots) {
        show(true);

        IBattleManager manager = getBattleManager();
        manager.stop(false);
        manager.cleanup();
//            manager.setRepository(Container.cache.getComponent(IRepositoryManager.class));
        getSettingsManager().setOptionsBattleDesiredTPS(30);
        getSettingsManager().saveProperties();
        BattleProperties properties = new BattleProperties();
        properties.setSelectedRobots(selectedRobots);
        manager.startNewBattle(properties, true, false);
        manager.resumeBattle();
    }

    public void endBattle(){
        IBattleManager manager = getBattleManager();
        manager.stop(true);
    }

    public Collection<String> getDevPaths(){
        return getSettingsManager().getOptionsDevelopmentPaths();
    }

    public void addDevPath(String path){
        Collection<String> paths = getSettingsManager().getOptionsDevelopmentPaths();
        paths.add(path);
        getSettingsManager().setOptionsDevelopmentPaths(paths);
    }

    public ISettingsManager getSettingsManager(){
        if(settingsManager == null){
            settingsManager = Container.getComponent(ISettingsManager.class);
        }
        return settingsManager;
    }

    public IBattleManager getBattleManager() {
        if (battleManager == null) {
            battleManager = Container.getComponent(IBattleManager.class);
        }
        return battleManager;
    }

    public IWindowManager getWindowManager() {
        if (windowManager == null) {
            windowManager = Container.getComponent(IWindowManager.class);
        }
        return windowManager;
    }

    public IRepositoryManager getRepositoryManager() {
        if (repositoryManager == null) {
            repositoryManager = Container.getComponent(IRepositoryManager.class);
        }
        return repositoryManager;
    }

    public List<String> getRobotList() {
        IRepositoryManager manager = getRepositoryManager();
        List<IRobotSpecItem> items = manager.getRepositoryItems(false, false, false, false, false, false, false, true);
        List<String> robots = new ArrayList<String>();

        for (IRobotSpecItem item : items) {
            robots.add(item.getFullClassName());
        }
        return robots;
    }

    public void start() {
        HiddenAccess.navalRobocodeMain(args);
    }
}
