package searchPractice;

import Tools.BattleSpecs;
import Tools.Coords;
import robocode.Robot;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RouteBot extends Robot {
    private BattleSpecs specs;

    @Override
    public void run() {
        setSpecs();
        Coords[] duckPlaces = getDucks();
    }

    private void setSpecs() {
        specs = new BattleSpecs();
        specs.setNumRows((int) getBattleFieldHeight() / 64);
        specs.setNumObstacles(getOthers());
    }

    private Coords[] getDucks() {
        Coords duckCoords[] = new Coords[specs.getNumObstacles()];
        Random seeder = ThreadLocalRandom.current();
        seeder.setSeed(64);

        for (int i = 0; i < specs.getNumObstacles(); i++) {
            duckCoords[i] = randomOriginalCoords(specs, duckCoords, i, seeder);
        }

        return duckCoords;
    }

    public static Coords randomOriginalCoords(BattleSpecs specs, Coords[] duckCoords, int i, Random seeder) {
        Coords newCoords;

        do {
            newCoords = new Coords(seeder.nextInt(specs.getNumRows()), seeder.nextInt(specs.getNumCol()));
        } while (!Coords.areOriginal(newCoords, duckCoords, i));

        return newCoords;
    }
}
