package Tools;

public class BattleSpecs {
    private int numObstacles;
    private int roundsNumber;
    private int inactivityTime;
    private double gunCoolingRate;
    private int sentryBorderSize;
    private boolean hideEnemyNames;
    private int numRows;
    private int numCol;

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumObstacles() {
        return numObstacles;
    }

    public void setNumObstacles(int numObstacles) {
        this.numObstacles = numObstacles;
    }

    public int getRoundsNumber() {
        return roundsNumber;
    }

    public void setRoundsNumber(int roundsNumber) {
        this.roundsNumber = roundsNumber;
    }

    public int getInactivityTime() {
        return inactivityTime;
    }

    public void setInactivityTime(int inactivityTime) {
        this.inactivityTime = inactivityTime;
    }

    public double getGunCoolingRate() {
        return gunCoolingRate;
    }

    public void setGunCoolingRate(double gunCoolingRate) {
        this.gunCoolingRate = gunCoolingRate;
    }

    public int getSentryBorderSize() {
        return sentryBorderSize;
    }

    public void setSentryBorderSize(int sentryBorderSize) {
        this.sentryBorderSize = sentryBorderSize;
    }

    public boolean getHideEnemyNames() {
        return hideEnemyNames;
    }

    public void setHideEnemyNames(boolean hideEnemyNames) {
        this.hideEnemyNames = hideEnemyNames;
    }
}
