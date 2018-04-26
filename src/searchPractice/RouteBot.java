//TODO Joder está quedando to feo, una limpieza no estaría de mas

package searchPractice;

import Tools.BattleSpecs;
import Tools.Cell;
import Tools.Coord;
import Tools.Coord.Cardinal;
import robocode.Robot;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RouteBot extends Robot {
    private Coord[] duckCoords;
    private BattleSpecs specs;
    private Coord init;
    private Coord end;

    private Set<Cell> openSet;
    private Set<Cell> closedSet;

    public void run() {
        System.out.println("HEY I'M ALIVE");

        initialize();

        Cell end = astar();

        driveToEnd(end);
    }

    private void driveToEnd(Cell end) {
        if (end != null){
            List<Cell> track = trackToEnd(end);
            followTrack(track);
        }
    }

    private void followTrack(List<Cell> track) {
        while (!track.isEmpty()){
            Cell nextCell = track.get(0);
            moveToCell(nextCell);
            track.remove(0);
        }
    }

    private void moveToCell(Cell nextCell) {
        Coord actualPosition = new Coord((getX()-32)/64,(getY()-32)/64);
        Cardinal orientation = getOrientation();
        headToNexCell(nextCell, orientation);
    }


    private void headToNexCell(Cell nextCell, Cardinal orientation) {
        Cardinal nextPosition = null;
        int horizontalDelta = (int) Math.round(nextCell.getCol() - getX());
        int verticalDelta = (int) Math.round(nextCell.getRow() - getY());

        if(horizontalDelta > 0)
            nextPosition = Cardinal.EAST;
        else if (horizontalDelta < 0)
            nextPosition = Cardinal.WEST;
        else if (verticalDelta > 0)
            nextPosition = Cardinal.NORTH;
        else if (verticalDelta < 0)
            nextPosition = Cardinal.SOUTH;

        headToCardinal(orientation, nextPosition);
    }

    private void headToCardinal(Cardinal currentOrientation, Cardinal nextOrientation) {

    }

    private Cardinal getOrientation() {
        int degreesHeading = (int) Math.round(getHeading());
        Cardinal orientation;
        switch (degreesHeading){
            case 0:
                orientation = Cardinal.NORTH;
                break;
            case 90:
                orientation = Cardinal.EAST;
                break;
            case 180:
                orientation = Cardinal.SOUTH;
                break;
            case 270:
                orientation = Cardinal.WEST;
                break;
        }
    }

    private void initialize() {
        try {
            specs = BattleSpecs.getBattleSpecs();
        } catch (FileNotFoundException e) {
            System.err.println("There is a problem with the battleProperties file");
        }
        init = new Coord((this.getX() - 32)/64, (this.getY() - 32)/64);

        duckCoords = Coord.randomCoords(specs);

        end = Coord.randomOriginalCoord(specs, duckCoords, specs.numObstacles, ThreadLocalRandom.current());

        openSet = new HashSet<>();
        closedSet = new HashSet<>();
    }

    private List<Cell> trackToEnd(Cell end) {
        Cell current = end;
        List<Cell> path = new ArrayList<>();
        path.add(current);
        while (current.getPrevious() != null){
            current = current.getPrevious();
            path.add(0, current);
        }

        return path;
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
        Set<Cell> alreadyAdded = new HashSet<>();

        for (Cell potential : neighbours) {
            if (!closedSet.contains(potential)) {
                checkIfExists(openSet, potential);
            } else {
                alreadyAdded.add(potential);
            }
        }

        neighbours.removeAll(alreadyAdded);
        openSet.addAll(neighbours);
    }

    private void checkIfExists(Set<Cell> openSet, Cell potential) {
        Set<Cell> toReplace = new HashSet<>();

        for (Cell alreadyIn : openSet) {
            if (potential.getCoord().equals(alreadyIn.getCoord())
                    && potential.getF() < alreadyIn.getF()) {
                toReplace.add(alreadyIn);
            }
        }

        openSet.removeAll(toReplace);
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
            i++;
        }

        return isFree;
    }

}