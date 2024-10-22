import java.util.LinkedList;

// This abstract class sets the basic rules of how each piece is defined,
// how each pieces moves are updated, and shows legal moves
public abstract class Piece {
    int color; // stores color - white = 0, black = 1
    int x;  // stores horizontal location of piece
    int y;  // stores vertical location of piece
    int type; // stores type based on number, 0 = empty, 1 = pawn, 2 = rook, 3 = knight,
    // 4 = bishop, 5 = king, 6 = queen

    // constructor to store information about piece
    // (same for all subclasses) so no comment will be included
    public Piece(int color, int x, int y, int type) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    // updates x and y coordinates of piece based on input
    public void updateLocation(int x2, int y2) {
        x = x2;
        y = y2;
    }

    // abstract method stores a piece's possible moves in a linked list
    // for player to use
    public abstract LinkedList<Move> getPossibleMoves(UI player);

    // abstract method checks legality of player-inputted move
    public abstract boolean isLegalMove(int x2, int y2, Move move, UI player);

    // abstract class method to check if king is in check
    // only applies to king, so other pieces will return false
    public abstract boolean checked(UI player);

}
