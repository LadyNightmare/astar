import Tools.BattleSpecs;
import Tools.Coords;
import robocode.control.*;

import java.util.concurrent.ThreadLocalRandom;

//
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//
public class BattleRunner {

    public static void main(String[] args) {
        if (args.length < 3){
            System.err.println("Propers parameter are: pathname numRows numCol");
        }
        else {
            String pathname = args[0];
            int numRows = Integer.parseInt(args[1]);
            int numCol = Integer.parseInt(args[2]);

            RobocodeEngine engine = getRobocodeEngine(pathname);

            BattleSpecification battleSpec = generateBattle(engine, numRows, numCol);

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

    private static BattleSpecification generateBattle(RobocodeEngine engine, int numRows, int numCol) {
        BattlefieldSpecification battlefield = new BattlefieldSpecification(numCol * 64, numRows * 64);
        BattleSpecs specs = getBattleSpecs(numRows, numCol);

        RobotSpecification[] existingRobots = new RobotSpecification[specs.getNumObstacles() + 1];

        RobotSetup[] robotSetups = getRobotSetups(engine, specs, existingRobots);
        return new BattleSpecification(battlefield, specs.getRoundsNumber(), specs.getInactivityTime(), specs.getGunCoolingRate(),
                specs.getSentryBorderSize(), specs.getHideEnemyNames(), existingRobots, robotSetups);
    }

    private static BattleSpecs getBattleSpecs(int numRows, int numCol) {
        BattleSpecs battleSpecs = new BattleSpecs();
        battleSpecs.setNumObstacles(new Double(numRows * numCol * 0.3).intValue());
        battleSpecs.setRoundsNumber(5);
        battleSpecs.setGunCoolingRate(1.0);
        battleSpecs.setHideEnemyNames(true);
        battleSpecs.setInactivityTime(10000000);
        battleSpecs.setSentryBorderSize(50);
        battleSpecs.setNumRows(numRows);
        battleSpecs.setNumCol(numCol);
        return battleSpecs;
    }

    private static RobotSetup[] getRobotSetups(RobocodeEngine engine, BattleSpecs specs, RobotSpecification[] existingRobots) {
        RobotSpecification[] modelRobots = engine.getLocalRepository("sample.SittingDuck,searchPractice.RouteBot*");
        RobotSetup[] robotSetups = new RobotSetup[specs.getNumObstacles() + 1];

        Coords[] placedDucks = randomDucks(specs, modelRobots, robotSetups, existingRobots);

        createAgent(specs, existingRobots, modelRobots, robotSetups, placedDucks);
        return robotSetups;
    }

    private static void createAgent(BattleSpecs specs, RobotSpecification[] existingRobots, RobotSpecification[] modelRobots, RobotSetup[] robotSetups, Coords[] placedDucks) {
        existingRobots[specs.getNumObstacles()] = modelRobots[1];
        Coords agentCoords = randomOriginalCoords(specs.getNumRows(), specs.getNumCol(), placedDucks, specs.getNumObstacles());
        robotSetups[specs.getNumObstacles()] = new RobotSetup(
                agentCoords.getRow() * 64 + 32, agentCoords.getCol()  * 64 + 32, 0.0);
    }

    private static Coords[] randomDucks(BattleSpecs specs, RobotSpecification[] modelRobots, RobotSetup[] robotSetups, RobotSpecification[] existingRobots) {
        Coords duckCoords[] = new Coords[specs.getNumObstacles()];

        for (int i = 0; i < specs.getNumObstacles(); i++) {
            existingRobots[i] = modelRobots[0];
            duckCoords[i] = randomOriginalCoords(specs.getNumRows(), specs.getNumCol(), duckCoords, i);

            robotSetups[i] = new RobotSetup(duckCoords[i].getRow() * 64 + 32, duckCoords[i].getCol() * 64 + 32, 0.0);
        }

        return duckCoords;
    }

    private static Coords randomOriginalCoords(int numRows, int numCol, Coords[] duckCoords, int i) {
        Coords newCoords;

        do {
            newCoords = new Coords(ThreadLocalRandom.current().nextInt(0, numRows),
                    ThreadLocalRandom.current().nextInt(0, numCol));
        } while (!isOriginal(newCoords, duckCoords, i));

        return newCoords;
    }

    private static boolean isOriginal(Coords newCoords, Coords[] duckCoords, int i) {
        boolean isOriginal = true;
        int j = 0;

        while (isOriginal && j < i) {
            isOriginal = !newCoords.equals(duckCoords[j]);
            j++;
        }

        return isOriginal;
    }
}