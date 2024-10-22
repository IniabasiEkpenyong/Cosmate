import java.util.LinkedList;

// implements basic rules of queen movement
public class Queen extends Piece {

    public Queen(int c, int x, int y, int t) {
        super(c, x, y, t);
    }

    public LinkedList<Move> getPossibleMoves(UI player) {
        LinkedList<Move> moves = new LinkedList<>();
        Move p;
        int n, m;

        // checks for moves up with queen
        if (x - 1 >= 0 && player.chessBoard[x - 1][y].checkPiece() != color) {
            n = -1;
            while (x + n >= 0) {
                if (player.chessBoard[x + n][y].hasPiece
                        && player.chessBoard[x + n][y].checkPiece() != color) {
                    p = new Move(x + n, y);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x - 1, y);
                if (isLegalMove(x, y, p, player))
                    moves.add(new Move(x + n, y));
                n--;
            }
        }

        // checks for moves down with queen
        if (x + 1 <= 7 && player.chessBoard[x + 1][y].checkPiece() != color) {
            n = 1;
            while (x + n <= 7) {            // move down
                if (player.chessBoard[x + n][y].hasPiece
                        && player.chessBoard[x + n][y].checkPiece() != color) {
                    p = new Move(x + n, y);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x + n, y);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n++;
            }
        }

        // checks for moves left with queen
        if (y - 1 >= 0 && player.chessBoard[x][y - 1].checkPiece() != color) {
            n = -1;
            while (y + n >= 0) {            // move left
                if (player.chessBoard[x][y + n].hasPiece
                        && player.chessBoard[x][y + n].checkPiece() != color) {
                    p = new Move(x, y + n);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x, y + n);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n--;
            }
        }

        // checks for moves right with queen
        if (y + 1 <= 7 && player.chessBoard[x][y + 1].checkPiece() != color) {
            n = 1;
            while (y + n <= 7) {
                if (player.chessBoard[x][y + n].hasPiece
                        && player.chessBoard[x][y + n].checkPiece() != color) {
                    p = new Move(x, y + n);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(new Move(x, y + n));
                        break;
                    }
                    break;
                }
                p = new Move(x, y + n);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n++;
            }
        }

        // checks for up-right moves with queen
        if (x - 1 >= 0 && y - 1 >= 0
                && player.chessBoard[x - 1][y - 1].checkPiece() != color) {   // move up-left
            n = -1;
            while (x + n >= 0 && y + n >= 0) {
                if (player.chessBoard[x + n][y + n].hasPiece
                        && player.chessBoard[x + n][y + n].checkPiece() != color) {
                    p = new Move(x + n, y + n);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x + n, y + n);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n--;
            }
        }

        // checks for moves up-right
        if (x - 1 >= 0 && y + 1 <= 7
                && player.chessBoard[x - 1][y + 1].checkPiece() != color) {
            n = -1;
            m = 1;
            while (x + n >= 0 && y + m <= 7) {
                if (player.chessBoard[x + n][y + m].hasPiece
                        && player.chessBoard[x + n][y + m].checkPiece() != color) {
                    p = new Move(x + n, y + m);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x + n, y + m);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n--;
                m++;
            }
        }

        // checks for moves down-left
        if (x + 1 <= 7 && y - 1 >= 0
                && player.chessBoard[x + 1][y - 1].checkPiece() != color) {   // move down-left
            n = 1;
            m = -1;
            while (x + n <= 7 && y + m >= 0) {
                if (player.chessBoard[x + n][y + m].hasPiece
                        && player.chessBoard[x + n][y + m].checkPiece() != color) {
                    p = new Move(x + n, y + m);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x + n, y + m);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
                n++;
                m--;
            }
        }

        // checks for moves down-right for the queen
        if (x + 1 <= 7 && y + 1 <= 7
                && player.chessBoard[x + 1][y + 1].checkPiece() != color) {   // move down-right
            n = 1;
            while (x + n <= 7 && y + n <= 7) {
                if (player.chessBoard[x + n][y + n].hasPiece
                        && player.chessBoard[x + n][y + n].checkPiece() != color) {
                    p = new Move(x + n, y + n);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                        break;
                    }
                    break;
                }
                p = new Move(x + n, y + n);
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

    public boolean checked(UI c) {
        return false;
    }
}

