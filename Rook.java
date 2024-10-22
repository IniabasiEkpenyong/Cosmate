import java.util.LinkedList;

// implements basic rules of rook movement
public class Rook extends Piece {

    public Rook(int c, int x, int y, int t) {
        super(c, x, y, t);
    }

    public LinkedList<Move> getPossibleMoves(UI player) {
        LinkedList<Move> moves = new LinkedList<>();
        Move p;
        int n;

        // iterates through and stores possible moves upward if space isn't occupied
        // by another piece of the same color
        if (x - 1 >= 0 && player.chessBoard[x - 1][y].checkPiece() != color) {
            n = -1;
            while (x + n >= 0) {
                if (player.chessBoard[x + n][y].hasPiece) {
                    if (player.chessBoard[x + n][y].checkPiece() != color) {
                        p = new Move(x + n, y);
                        if (isLegalMove(x, y, p, player)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x + n, y);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n--;
            }
        }

        // iterates through and stores possible moves downward if space isn't occupied
        // by another piece of the same color
        if (x + 1 <= 7 && player.chessBoard[x + 1][y].checkPiece() != color) {
            n = 1;
            while (x + n <= 7) {
                if (player.chessBoard[x + n][y].hasPiece) {
                    if (player.chessBoard[x + n][y].checkPiece() != color) {
                        p = new Move(x + n, y);
                        if (isLegalMove(x, y, p, player)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x + n, y);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n++;
            }
        }

        // iterates through and stores possible moves left if space isn't occupied
        // by another piece of the same color
        if (y - 1 >= 0 && player.chessBoard[x][y - 1].checkPiece() != color) {
            n = -1;
            while (y + n >= 0) {
                if (player.chessBoard[x][y + n].hasPiece) {
                    if (player.chessBoard[x][y + n].checkPiece() != color) {
                        p = new Move(x, y + n);
                        if (isLegalMove(x, y, p, player)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x, y + n);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n--;
            }
        }

        // iterates through and stores possible moves right if space isn't occupied
        // by another piece of the same color
        if (y + 1 <= 7 && player.chessBoard[x][y + 1].checkPiece() != color) {
            n = 1;
            while (y + n <= 7) {
                if (player.chessBoard[x][y + n].hasPiece) {
                    if (player.chessBoard[x][y + n].checkPiece() != color) {
                        p = new Move(x, y + n);
                        if (isLegalMove(x, y, p, player)) {
                            moves.add(p);
                            break;
                        }
                    }
                    break;
                }
                p = new Move(x, y + n);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n++;
            }
        }
        return moves;
    }

    // checks valid moves for each piece
    public boolean isLegalMove(int x, int y, Move move, UI player) {
        return player.isValidMove(x, y, move.getX(), move.getY(), this);
    }

    public boolean checked(UI player) {
        return false;
    }
}
