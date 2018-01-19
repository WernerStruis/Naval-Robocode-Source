package net.sf.robocode.RepositoryManager;


import net.sf.robocode.core.ContainerBase;
import net.sf.robocode.repository.CompetitionManager;
import net.sf.robocode.repository.ICompetitionManager;
import net.sf.robocode.repository.IRepositoryManagerBase;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.SettingsManager;
import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Ignore;
import org.junit.Test;
import robocode.control.RobotSpecification;
import robocode.control.events.RoundEndedEvent;

import static org.junit.Assert.assertTrue;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@Ignore
public class CompetitionManagerTest extends RobocodeTestBed{
    final ICompetitionManager competitionManager;
    private int carI = 0;
    private int corI = 0;
    private int cruI = 0;

    public CompetitionManagerTest(){
        competitionManager = new CompetitionManager(new SettingsManager());
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
    public void testRepositoryCompetitorsLoading(){
        for(RobotSpecification rs : competitionManager.getSpecifications()){
            IRobotSpecItem item = (IRobotSpecItem) HiddenAccess.getFileSpecification(rs);

            String classNameVersion = item.getUniqueFullClassNameWithVersion();
            assertTrue("This is no competition bot.", classNameVersion.startsWith("tested.competition"));
            if(classNameVersion.equals("tested.competition.CarrierTestShip")){
                carI++;
                assertTrue("More cariers than expected (2)", carI <=2);
            }else if(classNameVersion.equals("tested.competition.CorvetteTestShip")){
                corI++;
                assertTrue("More cariers than expected (2)", corI <=2);
            }else if(classNameVersion.equals("tested.competition.CruiserTestShip")){
                cruI++;
                assertTrue("More cariers than expected (2)", cruI <=2);
            }

        }
    }
}
