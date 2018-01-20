package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CurrencyTester.class, LoggerTester.class, UtilsTester.class })
public class AllTests {

}
