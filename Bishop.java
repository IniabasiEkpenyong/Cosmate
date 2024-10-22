import java.util.LinkedList;

// Implements the basic rules of the bishop chess piece
public class Bishop extends Piece {

    public Bishop(int color, int x, int y, int type) {
        super(color, x, y, type);
    }

    public LinkedList<Move> getPossibleMoves(UI c) {
        LinkedList<Move> moves = new LinkedList<>();
        Move p;
        int n, m;
        // Check the four possible diagonal move directions for the bishop
        // diagonal 1
        if (x - 1 >= 0 && y - 1 >= 0
                && c.chessBoard[x - 1][y - 1].checkPiece() != color) {   // move up-left
            n = -1;
            while (x + n >= 0 && y + n >= 0) {
                if (c.chessBoard[x + n][y + n].hasPiece) {
                    if (c.chessBoard[x + n][y + n].checkPiece() != color) {
                        p = new Move(x + n, y + n);
                        if (isLegalMove(x, y, p, c)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x + n, y + n);
                if (isLegalMove(x, y, p, c))
                    moves.add(p);
                n--;
            }
        }
        // diagonal 2 - up-right
        if (x - 1 >= 0 && y + 1 <= 7
                && c.chessBoard[x - 1][y + 1].checkPiece() != color) {
            n = -1;
            m = 1;
            while (x + n >= 0 && y + m <= 7) {
                if (c.chessBoard[x + n][y + m].hasPiece) {
                    if (c.chessBoard[x + n][y + m].checkPiece() != color) {
                        p = new Move(x + n, y + m);
                        if (isLegalMove(x, y, p, c)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x + n, y + m);
                if (isLegalMove(x, y, p, c))
                    moves.add(p);
                n--;
                m++;
            }
        }
        // diagonal 3 - down left
        if (x + 1 <= 7 && y - 1 >= 0
                && c.chessBoard[x + 1][y - 1].checkPiece() != color) {
            n = 1;
            m = -1;
            while (x + n <= 7 && y + m >= 0) {
                if (c.chessBoard[x + n][y + m].hasPiece) {
                    if (c.chessBoard[x + n][y + m].checkPiece() != color) {
                        p = new Move(x + n, y + m);
                        if (isLegalMove(x, y, p, c)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x + n, y + m);
                if (isLegalMove(x, y, p, c))
                    moves.add(p);
                n++;
                m--;
            }
        }
        // diagonal 4 - down-right
        if (x + 1 <= 7 && y + 1 <= 7 && c.chessBoard[x + 1][y + 1].checkPiece()
                != color) {
            n = 1;
            while (x + n <= 7 && y + n <= 7) {
                if (c.chessBoard[x + n][y + n].hasPiece) {
                    if (c.chessBoard[x + n][y + n].checkPiece() != color) {
                        p = new Move(x + n, y + n);
                        if (isLegalMove(x, y, p, c)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x + n, y + n);
                if (isLegalMove(x, y, p, c))
                    moves.add(p);
                n++;
            }
        }
        return moves;
    }

    public boolean isLegalMove(int x, int y, Move move, UI c) {
        return c.isValidMove(x, y, move.getX(), move.getY(), this);
    }

    public boolean checked(UI player) {
        return false;
    }


}
