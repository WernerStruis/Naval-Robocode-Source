package net.sf.robocode.RepositoryManager;


import net.sf.robocode.core.ContainerBase;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.io.Logger;
import net.sf.robocode.repository.IRepositoryManagerBase;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.repository.Repository;
import net.sf.robocode.repository.RepositoryManager;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.SettingsManager;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Ignore;
import org.junit.Test;
import robocode.control.RobotSpecification;
import robocode.control.events.RoundEndedEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@Ignore
public class RepositoryManagerTest extends RobocodeTestBed {
    final IRepositoryManagerBase repository;
    private Set<String> falseItems = new HashSet<String>();
    private int carI = 0;
    private int corI = 0;
    private int cruI = 0;

    public RepositoryManagerTest() {
        falseItems.add("NoPackageShortName");
        falseItems.add("sample.Interactive_v2");
        repository = ContainerBase.getComponent(net.sf.robocode.repository.IRepositoryManagerBase.class);
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        super.onRoundEnded(event);
    }

    @Override
    protected void runSetup() {
        super.runSetup();
    }

    @Override
    public String getRobotNames() {
        return "tested.ships.SittingDuck";
    }

    @Test
    public void testRepositoryLoading() {
        assert repository.getSpecifications() != null;
        for (RobotSpecification rs : repository.getSpecifications()) {
            if (!falseItems.contains(rs.getClassName())) {
                assertTrue("This item does not belong in this repository: " + rs.getClassName(), rs.getClassName().startsWith("tested.") || rs.getClassName().startsWith("sample."));
            }
        }
    }
}
