package Tools;

import java.util.Random;

public class Coords {
    private double row;
    private double col;

    public Coords(double x, double y){
        row = x;
        col = y;
    }

    public double getCol() {
        return col;
    }

    public double getRow() {
        return row;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Coords) obj).row == this.row && ((Coords) obj).col == this.col;
    }

    public static boolean areOriginal(Coords newCoords, Coords[] duckCoords, int i) {
        boolean isOriginal = true;
        int j = 0;

        while (isOriginal && j < i) {
            isOriginal = !newCoords.equals(duckCoords[j]);
            j++;
        }

        return isOriginal;
    }

    public static Coords randomOriginalCoords(BattleSpecs specs, Coords[] duckCoords, int i, Random seeder) {
        Coords newCoords;

        do {
            newCoords = new Coords(seeder.nextInt(specs.getNumRows()), seeder.nextInt(specs.getNumCol()));
        } while (!Coords.areOriginal(newCoords, duckCoords, i));

        return newCoords;
    }
}
