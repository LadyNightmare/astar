package Tools;

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
}
