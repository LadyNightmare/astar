package Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleSpecs {
    public int numObstacles;
    public int roundsNumber;
    public int inactivityTime;
    public double gunCoolingRate;
    public int sentryBorderSize;
    public boolean hideEnemyNames;
    public int numRows;
    public int numCol;
    public int seed;


    public static BattleSpecs getBattleSpecs() throws FileNotFoundException {
        BattleSpecs battleSpecs = new BattleSpecs();

        Scanner specScanner = generateScanner();

        int numRows = Integer.parseInt(specScanner.nextLine());
        int numCol = Integer.parseInt(specScanner.nextLine());
        battleSpecs.seed = Integer.parseInt(specScanner.nextLine());

        battleSpecs.numObstacles = ((int) (numRows * numCol * 0.3));
        battleSpecs.roundsNumber = (5);
        battleSpecs.gunCoolingRate = (1.0);
        battleSpecs.hideEnemyNames = (true);
        battleSpecs.inactivityTime = (10000000);
        battleSpecs.sentryBorderSize = (50);
        battleSpecs.numRows = (numRows);
        battleSpecs.numCol = (numCol);
        return battleSpecs;
    }

    private static Scanner generateScanner() throws FileNotFoundException {
        Scanner specScanner = new Scanner(new File("battleProperties"));
        specScanner.nextLine();

        return specScanner;
    }
}