package suite;

import controller.GamePlayTest;
import model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.UtilTest;
import view.*;


@RunWith(Suite.class)
@Suite.SuiteClasses({

        GamePlayTest.class, CardTest.class, CountryTest.class, DiceTest.class, PlayerTest.class,
        RiskMapTest.class, UtilTest.class, CardExchangeTest.class, DriverTest.class, PhaseTest.class,
        PlayerWorldDominationTest.class})

public class TestSuite {

}
