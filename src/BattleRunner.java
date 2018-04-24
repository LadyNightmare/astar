import Tools.BattleSpecs;
import Tools.Coord;
import robocode.control.*;

import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

//
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//
public class BattleRunner {

    private static BattleSpecs specs;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 1) {
            System.err.println("Please introduce as parameter robocode pathname");
        } else {
            String pathname = args[0];

            specs = BattleSpecs.getBattleSpecs();

            RobocodeEngine engine = getRobocodeEngine(pathname);

            BattleSpecification battleSpec = generateBattle(engine);

            engine.runBattle(battleSpec, true);
            engine.close();
            System.exit(0);
        }
    }

    private static RobocodeEngine getRobocodeEngine(String pathname) {
        RobocodeEngine engine = new RobocodeEngine(new java.io.File(pathname));

        engine.setVisible(true);
        return engine;
    }

    private static BattleSpecification generateBattle(RobocodeEngine engine) {
        BattlefieldSpecification battlefield = new BattlefieldSpecification(specs.numCol * 64, specs.numRows * 64);
        RobotSpecification[] existingRobots = new RobotSpecification[specs.numObstacles + 1];

        RobotSetup[] robotSetups = getRobotSetups(engine, existingRobots);
        return new BattleSpecification(battlefield, specs.roundsNumber, specs.inactivityTime, specs.gunCoolingRate,
                specs.sentryBorderSize, specs.hideEnemyNames, existingRobots, robotSetups);
    }


    private static RobotSetup[] getRobotSetups(RobocodeEngine engine, RobotSpecification[] existingRobots) {
        RobotSpecification[] modelRobots = engine.getLocalRepository("sample.SittingDuck,searchPractice.RouteBot*");
        RobotSetup[] robotSetups = new RobotSetup[specs.numObstacles + 1];

        System.out.println("BattleRunner bots: ");
        Coord[] randomDucks = Coord.randomCoords(specs);
        placeDucks(randomDucks, modelRobots, robotSetups, existingRobots);

        createAgent(existingRobots, modelRobots, robotSetups, randomDucks);

        return robotSetups;
    }

    private static void placeDucks(Coord[] ducksToPlace, RobotSpecification[] modelRobots, RobotSetup[] robotSetups, RobotSpecification[] existingRobots) {
        RobotSpecification sittingDuck = modelRobots[0];

        for (int i = 0; i < ducksToPlace.length; i++) {
            existingRobots[i] = sittingDuck;
            robotSetups[i] = new RobotSetup(ducksToPlace[i].getRow() * 64 + 32, ducksToPlace[i].getCol() * 64 + 32, 0.0);
        }
    }

    private static void createAgent(RobotSpecification[] existingRobots, RobotSpecification[] modelRobots, RobotSetup[] robotSetups, Coord[] placedDucks) {
        existingRobots[specs.numObstacles] = modelRobots[1];
        Coord agentCoord = Coord.randomOriginalCoord(specs, placedDucks, specs.numObstacles, ThreadLocalRandom.current());
        robotSetups[specs.numObstacles] = new RobotSetup(
                agentCoord.getRow() * 64 + 32, agentCoord.getCol() * 64 + 32, 0.0);
    }
}