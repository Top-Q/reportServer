//package il.co.topq.systems.report.tests.system_tests;
//
//
//import il.co.topq.systems.report.tests.players.AdminUser;
//import il.co.topq.systems.report.tests.players.Database;
//import il.co.topq.systems.report.tests.players.JSystemGUI;
//import org.junit.Assert;
//
//import java.util.Random;
//
//public class JSystemPublishFlowsTest extends AbstractSystemTest {
//
//
//    /**
//     * Publishing a Scenario with random number of tests without properties
//     */
//    @org.junit.Test
//    public void publishScenarioWoProperties() {
//        //Clean Database
//        new AdminUser().cleanDatabase();
//
//        int randNumberOfTests = new Random().nextInt(300);
//        //Publish a Scenario with a random number of tests
//        new JSystemGUI().publish(JSystemGUI.PublishType.NO_PROPERTIES, randNumberOfTests);
//        //Verify that only one scenario is created in the database
//        Assert.assertEquals("Number of scenario ", new Database().countScenarios().longValue(), 1L);
//        //Verify that there are randNumberOfTest in the database
//        Assert.assertEquals("Number of test ", new Database().countTests().intValue(), randNumberOfTests);
//    }
//
//
//    /**
//     * Publishing a Scenario with random number of tests with only scenario properties
//     * Each scenario has for test purpose 2 properties and the same is for tests
//     */
//    @org.junit.Test
//    public void publishScenarioWithScenarioProperties() {
//        //Clean Database
//        new AdminUser().cleanDatabase();
//        int randNumberOfTests = new Random().nextInt(300);
//        //Publish a Scenario with a random number of tests
//        new JSystemGUI().publish(JSystemGUI.PublishType.SCENARIO_PROPERTIES, randNumberOfTests);
//        //Verify that only one scenario is created in the database
//        Assert.assertEquals("Number of scenario ", 1L, new Database().countScenarios().longValue());
//        //Verify that there are randNumberOfTest in the database
//        Assert.assertEquals("Number of test ", randNumberOfTests, new Database().countTests().intValue());
//        //Verify that the number of properties is correct
//        Assert.assertEquals("Number of scenario properties ", 2, new Database().countScenarioProperties().intValue());
//    }
//
//    /**
//     * Publishing a Scenario with random number of tests with only scenario properties
//     * Each scenario has for test purpose 2 properties and the same is for tests
//     */
//    @org.junit.Test
//    public void publishScenarioWithTestProperties() {
//        //Clean Database
//        new AdminUser().cleanDatabase();
//        int randNumberOfTests = new Random().nextInt(300);
//        //Publish a Scenario with a random number of tests
//        new JSystemGUI().publish(JSystemGUI.PublishType.TEST_PROPERTIES, randNumberOfTests);
//        //Verify that only one scenario is created in the database
//        Assert.assertEquals("Number of scenario ", 1L, new Database().countScenarios().longValue());
//        //Verify that there are randNumberOfTest in the database
//        Assert.assertEquals("Number of test ", randNumberOfTests, new Database().countTests().intValue());
//        //Verify that the number of properties is correct
//        Assert.assertEquals("Number of test properties ", 2 * randNumberOfTests, new Database().countTestProperties().intValue());
//    }
//
//    /**
//     * Publishing a Scenario with random number of tests with only scenario properties
//     * Each scenario has for test purpose 2 properties and the same is for tests
//     */
//    @org.junit.Test
//    public void publishScenarioWithScenarioAndTestProperties() {
//        //Clean Database
//        new AdminUser().cleanDatabase();
//        int randNumberOfTests = new Random().nextInt(300);
//        //Publish a Scenario with a random number of tests
//        new JSystemGUI().publish(JSystemGUI.PublishType.DEFAULT, randNumberOfTests);
//        //Verify that only one scenario is created in the database
//        Assert.assertEquals("Number of scenario ", 1L, new Database().countScenarios().longValue());
//        //Verify that there are randNumberOfTest in the database
//        Assert.assertEquals("Number of test ", randNumberOfTests, new Database().countTests().intValue());
//        //Verify that the number of properties is correct
//        Assert.assertEquals("Number of test properties ", 2 * randNumberOfTests, new Database().countTestProperties().intValue());
//        //Verify that the number of properties is correct
//        Assert.assertEquals("Number of scenario properties ", 2, new Database().countScenarioProperties().intValue());
//    }
//
//
//}
