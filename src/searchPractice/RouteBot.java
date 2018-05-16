//TODO Joder está quedando to feo, una limpieza no estaría de mas

package searchPractice;

import Tools.BattleSpecs;
import Tools.Cell;
import Tools.Coord;
import Tools.Coord.Cardinal;
import robocode.Robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class RouteBot extends Robot {
    private Coord[] duckCoords;
    private BattleSpecs specs;
    private Coord init;
    private Coord end;

    private Set<Cell> openSet;
    private Set<Cell> closedSet;

    private PrintWriter writer;

    public void run() {
        System.out.println("HEY I'M ALIVE");
        try {
            writer = new PrintWriter(new File("directions.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        initialize();

        Cell end = astar();

        if (Objects.nonNull(end)) {
            followTrack(end.getDirections());
        }
        writer.close();
    }

    private void followTrack(List<Cardinal> track) {
        while (!track.isEmpty()) {
            Cardinal nextHeading = track.get(0);
            printHeading(nextHeading);
            moveToHeading(nextHeading);
            track.remove(0);
        }
    }

    private void printHeading(Cardinal nextHeading) {
        switch (nextHeading) {
            case NORTH:
                writer.print("↑");
            case EAST:
                writer.print("→");
            case SOUTH:
                writer.print("↓");
            case WEST:
                writer.print("←");
        }
    }

    private void moveToHeading(Cardinal nextHeading) {
        int degreesHeading = (int) Math.round(getHeading());
        Cardinal currentOrientation = Cardinal.cardinalOf(degreesHeading, writer);
        headToCardinal(currentOrientation, nextHeading);
        ahead(64);
    }

    private void headToCardinal(Cardinal currentOrientation, Cardinal nextOrientation) {
        double turnAngle = nextOrientation.degrees - currentOrientation.degrees;

        if (turnAngle > 0) {
            turnRight(turnAngle);
        } else {
            turnLeft(Math.abs(turnAngle));
        }
    }

    private void initialize() {
        try {
            specs = BattleSpecs.getBattleSpecs();
        } catch (FileNotFoundException e) {
            System.err.println("There is a problem with the battleProperties file");
        }
        duckCoords = Coord.randomCoords(specs);

        init = Coord.randomOriginalCoord(specs, duckCoords, specs.numObstacles);

        end = Coord.randomOriginalCoord(specs, duckCoords, specs.numObstacles);

        openSet = new HashSet<>();
        closedSet = new HashSet<>();
    }

    private Cell astar() {
        boolean solved = false;
        Cell finalCell = null;

        openSet.add(new Cell(init, 0, 0));

        while (!openSet.isEmpty() && !solved) {
            Cell currentCell = getLesserF();
            if (currentCell.getCoord().equals(end)) {
                solved = true;
                finalCell = currentCell;
            } else {
                closedSet.add(currentCell);
                openSet.remove(currentCell);
                addWithoutRepetition(openSet, neighbours(currentCell));
            }
        }

        return finalCell;
    }

    private void addWithoutRepetition(Set<Cell> openSet, Set<Cell> neighbours) {
        //Checks if there is already on openSet a Cell with less F
        Set<Cell> alreadyAddedOnAnySet = new HashSet<>();

        for (Cell potential : neighbours) {
            if (closedSet.contains(potential) || openSet.contains(potential))
                alreadyAddedOnAnySet.add(potential);
        }

        neighbours.removeAll(alreadyAddedOnAnySet);
        openSet.addAll(neighbours);
    }

    private Cell getLesserF() {
        Iterator hashSetIterator = openSet.iterator();
        Cell potentialNode = (Cell) hashSetIterator.next();

        while (hashSetIterator.hasNext()) {
            Cell iteratedNode = (Cell) hashSetIterator.next();

            if (iteratedNode.getF() < potentialNode.getF()) {
                potentialNode = iteratedNode;
            }
        }

        return potentialNode;
    }

    //TODO hostia, a ver si hacemos esto mas bonito porque joder que feo y propenso a errores
    private Set<Cell> neighbours(Cell current) {
        Set<Cell> neighbours = new HashSet<>();

        tryCardinal(Cardinal.WEST, current, neighbours, -1, 0);
        tryCardinal(Cardinal.EAST, current, neighbours, 1, 0);
        tryCardinal(Cardinal.NORTH, current, neighbours, 0, 1);
        tryCardinal(Cardinal.SOUTH, current, neighbours, 0, -1);

        return neighbours;
    }

    private void tryCardinal(Cardinal nextOrientation, Cell current, Set<Cell> neighbours, int horizontalDelta, int verticalDelta) {
        if (isFree(current.getCol() + horizontalDelta, current.getRow() + verticalDelta)) {
            Coord next = new Coord(current.getCol() + horizontalDelta, current.getRow() + verticalDelta);
            int heuristic = Coord.manhattanDistance(next, end);
            List<Cardinal> nextDirections = new ArrayList<>(current.getDirections());
            nextDirections.add(nextOrientation);

            neighbours.add(new Cell(next, heuristic, current.getG() + 1, nextDirections));
        }
    }

    private boolean isFree(double col, double row) {
        boolean isFree = col >= 0 && col < specs.numCol && row >= 0 && row < specs.numRows;

        if (isFree) {
            Coord coordToTest = new Coord(col, row);
            int i = 0;

            while (isFree && i < duckCoords.length) {
                isFree = !coordToTest.equals(duckCoords[i]);
                i++;
            }
        }
        return isFree;
    }

}