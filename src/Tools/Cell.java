package Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private int heuristic;
    private int traveledDistance;
    private int f;
    private Coord cellCoord;
    private List<Coord.Cardinal> directions;

    public int getG() {
        return traveledDistance;
    }

    public int getHeuristic(){
        return heuristic;
    }

    public List<Coord.Cardinal> getDirections() {
        return directions;
    }

    public Cell(Coord position, int hValue, int gValue){
       cellCoord = position;
       heuristic = hValue;
       traveledDistance = gValue;
       f = hValue + gValue;
       directions = new ArrayList<>();

    }

    public Cell(Coord position, int hValue, int gValue, List<Coord.Cardinal> nextHeading){
        this(position, hValue, gValue);
        this.directions = nextHeading;
    }

    public double getF(){
        return f;
    }
    public double getCol(){
        return cellCoord.getCol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return cellCoord.equals(cell.cellCoord);
    }

    @Override
    public int hashCode() {
        return cellCoord.hashCode();
    }

    public double getRow(){
        return cellCoord.getRow();
    }

    public Coord getCoord() {
        return cellCoord;
    }

}
