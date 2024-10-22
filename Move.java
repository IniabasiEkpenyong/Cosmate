// This class deals with the coordinate values of the moves of each
// of the chess pieces
public class Move {
    private Integer x;
    private Integer y;

    // constructor initialized with current coordinates
    public Move(int x1, int y1) {
        x = x1;
        y = y1;
    }

    // returns x-coordinate of piece
    public int getX() {
        return x;
    }

    // returns y-coordinate of piece
    public int getY() {
        return y;
    }

    // set new coordinates
    public void setNewValues(int x2, int y2) {
        x = x2;
        y = y2;
    }

    // returns a String of the coordinates of the movement
    public String toString() {
        return x.toString() + ", " + y.toString();
    }
}


