package searchPractice;

import Tools.BattleSpecs;
import Tools.Coords;
import robocode.Robot;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static Tools.Coords.randomOriginalCoords;

public class RouteBot extends Robot {
    private BattleSpecs specs;
    private Coords[] duckCoords;

    @Override
    public void run() {
        setSpecs();
        duckCoords = getDucks();
        while (true) {

            ahead(100);

            turnGunRight(360);

            back(100);

            turnGunRight(360);
            setSpecs();

        }
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

}
