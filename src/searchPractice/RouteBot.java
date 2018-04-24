//TODO Joder está quedando to feo, una limpieza no estaría de mas

package searchPractice;

import Tools.BattleSpecs;
import Tools.Cell;
import Tools.Coord;
import robocode.Robot;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RouteBot extends Robot {
    private Coord[] duckCoords;
    private BattleSpecs specs;
    private Coord init;
    private Coord end;

    private Set<Cell> openSet;
    private Set<Cell> closedSet;

    public void run() {
        try {
            specs = BattleSpecs.getBattleSpecs();
        } catch (FileNotFoundException e) {
            System.err.println("There is a problem with the battleProperties file");
        }
        init = new Coord(this.getX(), this.getY());

        end = new Coord(ThreadLocalRandom.current().nextDouble(specs.numRows)
                , ThreadLocalRandom.current().nextDouble(specs.numCol));

        openSet = new HashSet<>();
        closedSet = new HashSet<>();

        astar();
    }

    private void astar() {
        openSet.add(new Cell(init, 0, 0));
        while (!openSet.isEmpty()) {
            Cell currentCell = getLesserF();
            if (currentCell.getCoord().equals(end)){

            }
            openSet.addAll(neighbours(currentCell));
            closedSet.add(currentCell);

        }
    }

    private Cell getLesserF() {
        Iterator setIterator = openSet.iterator();
        Cell potentialNode = (Cell) setIterator.next();

        while (setIterator.hasNext()) {
            Cell iteratedNode = (Cell) setIterator.next();

            if (iteratedNode.getF() > potentialNode.getF()) {
                potentialNode = iteratedNode;
            }
        }

        return potentialNode;
    }

    //TODO hostia, a ver si hacemos esto mas bonito porque joder que feo y propenso a errores
    private Set<Cell> neighbours(Cell current) {
        Set<Cell> neighbours = new HashSet<>();

        if (current.getCol() - 1 >= 0 && isFree(current.getCol() - 1, current.getRow())) {
            Coord leftist = new Coord(current.getCol() - 1, current.getRow());
            int heuristic = Coord.manhattanDistance(leftist, end);

            neighbours.add(new Cell(leftist, heuristic, current.getG() + 1, current));
        }

        if (current.getRow() - 1 >= 0 && isFree(current.getCol(), current.getRow() - 1)) {
            Coord upper = new Coord(current.getCol(), current.getRow() - 1);
            int heuristic = Coord.manhattanDistance(upper, end);

            neighbours.add(new Cell(upper, heuristic, current.getG() + 1, current));
        }

        if (current.getCol() + 1 < specs.numCol && isFree(current.getCol() + 1, current.getRow())) {
            Coord rightist = new Coord(current.getCol() + 1, current.getRow());
            int heuristic = Coord.manhattanDistance(rightist, end);

            neighbours.add(new Cell(rightist, heuristic, current.getG() + 1, current));
        }

        if (current.getRow() + 1 < specs.numRows && isFree(current.getCol(), current.getRow() + 1)) {
            Coord bottomer = new Coord(current.getCol(), current.getRow() + 1);
            int heuristic = Coord.manhattanDistance(bottomer, end);

            neighbours.add(new Cell(bottomer, heuristic, current.getG() + 1, current));
        }

        return neighbours;
    }

    private boolean isFree(double col, double row) {
        boolean isFree = true;
        Coord coordToTest = new Coord(col, row);
        int i = 0;

        while (isFree && i < duckCoords.length) {
            isFree = !coordToTest.equals(duckCoords[i]);
        }

        return isFree;
    }

}