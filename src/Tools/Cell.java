package Tools;

import java.util.Objects;

public class Cell {
    private int heuristic;
    private int traveledDistance;
    private int f;
    private Coord cellCoord;
    private Cell previous;

    public int getG() {
        return traveledDistance;
    }

    public int getHeuristic(){
        return heuristic;
    }

    public Cell(Coord position, int hValue, int gValue){
       cellCoord = position;
       heuristic = hValue;
       traveledDistance = gValue;
       f = hValue + gValue;
    }

    public Cell(Coord position, int hValue, int gValue, Cell previous){
        this(position, hValue, gValue);
        this.previous = previous;
    }

    public double getF(){
        return f;
    }
    public double getCol(){
        return cellCoord.getCol();
    }

    public double getRow(){
        return cellCoord.getRow();
    }

    public Coord getCoord() {
        return cellCoord;
    }

    public Cell getPrevious() {
        return previous;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (f != cell.f) return false;
        return cellCoord.equals(cell.cellCoord);
    }

    @Override
    public int hashCode() {
        int result = f;
        result = 31 * result + cellCoord.hashCode();
        return result;
    }
}
