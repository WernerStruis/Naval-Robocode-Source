package net.sf.robocode.test.naval.ShipTypes;

import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import robocode.control.events.BattleStartedEvent;
import robocode.exception.RobotException;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class TestInvalidShip extends RobocodeTestBed {

    public class Expectedfailure implements TestRule {

        @Override
        public Statement apply(Statement base, Description description) {
            return statement(base, description);
        }

        private Statement statement(final Statement base, final Description description) {
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    try {
                        base.evaluate();
                    } catch (Throwable e) {
                        if (e instanceof RobotException || e instanceof AssertionError) {
                            System.err.println("This is wat we wanted");
                        } else {
                            throw e;
                        }
                    }
                }
            };
        }
    }


    @Override
    public String getRobotNames() {
        return "tested.ships.InvalidShip";
    }


    @Override
    public void onBattleStarted(BattleStartedEvent event) {
        super.onBattleStarted(event);
    }

    @Override
    public String getInitialPositions() {
        return "(500,500,0)";
    }

    @Rule
    public Expectedfailure expectedfailure = new Expectedfailure();

    @Test
    public void startFailingGame() {
        TestInvalidShip tis = new TestInvalidShip();
        tis.run();
    }

}