package Tools;

import java.util.Objects;
import java.util.Random;

public class Coord {
    private double row;
    private double col;

    public Coord(double x, double y) {
        col = x;
        row = y;
    }

    public static void printArray(Coord[] placedDucks) {
        int length = (int) Math.sqrt(placedDucks.length);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.println(placedDucks[length * i + j]);
            }
        }
    }

    public double getCol() {
        return col;
    }

    public double getRow() {
        return row;
    }

    public static boolean isOriginal(Coord newCoord, Coord[] duckCoords, int i) {
        boolean isOriginal = true;
        int j = 0;

        while (isOriginal && j < i) {
            isOriginal = !newCoord.equals(duckCoords[j]);
            j++;
        }

        return isOriginal;
    }

    public static Coord randomOriginalCoord(BattleSpecs specs, Coord[] duckCoords, int i, Random seeder) {
        Coord newCoord;

        do {
            newCoord = new Coord(seeder.nextInt(specs.numRows), seeder.nextInt(specs.numCol));
        } while (!Coord.isOriginal(newCoord, duckCoords, i));

        return newCoord;
    }

    // Needs numObstacles, numRows and numCol
    public static Coord[] randomCoords(BattleSpecs specs){
        Coord duckCoords[] = new Coord[specs.numObstacles];
        Random seeder = new Random();
        seeder.setSeed(specs.seed);

        for (int i = 0; i < specs.numObstacles; i++) {
            duckCoords[i] = Coord.randomOriginalCoord(specs, duckCoords, i, seeder);
        }

        return duckCoords;
    }

    public static int manhattanDistance(Coord origin, Coord end){
        return (int) (Math.abs(origin.col - end.col) + Math.abs(origin.row - end.col));
    }

    @Override
    public String toString() {
        return "(" + row * 64 + 32 + ", " + col * 64 + 32 + ")";
    }

    @Override
    public boolean equals(Object obj) {
        return ((Coord) obj).row == this.row && ((Coord) obj).col == this.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
