import robocode.control.*;

//
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//
public class BattleRunner {

    public static void main(String[] args) {
        RobocodeEngine engine = getRobocodeEngine();
        BattleSpecification battleSpec = randomBots(engine);


        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        // Cleanup our RobocodeEngine
        engine.close();
        // Make sure that the Java VM is shut down properly
        System.exit(0);
    }

    private static BattleSpecification randomBots(RobocodeEngine engine) {
        int numPixelRows = 1024;
        int numPixelCol =576;

        BattlefieldSpecification battlefield = new BattlefieldSpecification(numPixelRows, numPixelCol);
        BattleSpecification battleSpec = randomBots(engine, battlefield);

        return battleSpec;
    }

    private static BattleSpecification randomBots(RobocodeEngine engine, BattlefieldSpecification battlefield) {
        int numObstacles = 43;
        int numberOfRounds = 5;
        long inactivityTime = 10000000;
        double gunCoolingRate = 1.0;
        int sentryBorderSize = 50;
        boolean hideEnemyNames = false;

        /*
         * Create obstacles and place them at random so that no pair of obstacles
         * are at the same position
         */

        RobotSpecification[] existingRobots = new RobotSpecification[numObstacles+1];
        RobotSetup[] robotSetups = getRobotSetups(engine, numObstacles, existingRobots);
        return new BattleSpecification(battlefield, numberOfRounds, inactivityTime, gunCoolingRate, sentryBorderSize,
            hideEnemyNames, existingRobots, robotSetups);
    }

    private static RobotSetup[] getRobotSetups(RobocodeEngine engine, int numObstacles, RobotSpecification[] existingRobots) {
        RobotSpecification[] modelRobots = engine.getLocalRepository("sample.SittingDuck,searchpractice.RouteBot*");
        RobotSetup[] robotSetups = new RobotSetup[numObstacles+1];
        for(int NdxObstacle=0;NdxObstacle<numObstacles;NdxObstacle++) {
            double initialObstacleRow;
            double initialObstacleCol;
            existingRobots[NdxObstacle]=modelRobots[0]; robotSetups[NdxObstacle]=new RobotSetup(initialObstacleRow,
                    initialObstacleCol,0.0);
            robotSetups[NdxObstacle]=new RobotSetup(initialObstacleRow,
                    initialObstacleCol,0.0);
        }

        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.RamFire,sample.Corners");

        /*
         * Create the agent and place it in a random position without obstacle
         */
        existingRobots[numObstacles]=modelRobots[1];
        double initialAgentRow;
        double initialAgentCol;
        robotSetups[numObstacles]=new RobotSetup(initialAgentRow, initialAgentCol,0.0);
        return robotSetups;
    }

    private static RobocodeEngine getRobocodeEngine() {
        // Create the RobocodeEngine
        RobocodeEngine engine = new RobocodeEngine(new java.io.File("/Users/Yago/robocode")); // Run from C:/Robocode

        // Show the Robocode battle view
        engine.setVisible(true);
        return engine;
    }
}