import java.util.LinkedList;

public class King extends Piece {

    public King(int color, int x, int y, int type) {
        super(color, x, y, type);
    }

    public LinkedList<Move> getPossibleMoves(UI c) {
        LinkedList<Move> moves = new LinkedList<>();
        Move p;

        // stores possible left movement
        if (x - 1 >= 0 && c.chessBoard[x - 1][y].checkPiece() != color) {  // up
            p = new Move(x - 1, y);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores possible down-left movement
        if (x - 1 >= 0 && y - 1 >= 0
                && c.chessBoard[x - 1][y - 1].checkPiece() != color) {
            p = new Move(x - 1, y - 1);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores possible down movement
        if (y + 1 <= 7 && c.chessBoard[x][y - 1].checkPiece() != color) {
            // check for left movement
            p = new Move(x, y - 1);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores possible up-left movement
        if (x + 1 <= 7 && y - 1 >= 0
                && c.chessBoard[x + 1][y - 1].checkPiece() != color) {
            p = new Move(x + 1, y - 1);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores possible right movement
        if (x + 1 <= 7 && c.chessBoard[x + 1][y].checkPiece() != color) {
            p = new Move(x + 1, y);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores up-right moves
        if (x + 1 <= 7 && y + 1 <= 7
                && c.chessBoard[x + 1][y + 1].checkPiece() != color) {
            p = new Move(x + 1, y + 1);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores up movement
        if (y + 1 <= 7 && c.chessBoard[x][y + 1].checkPiece() != color) {
            p = new Move(x, y + 1);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }

        // stores up-left movement
        if (x - 1 >= 0 && y + 1 <= 7
                && c.chessBoard[x - 1][y + 1].checkPiece() != color) {
            p = new Move(x - 1, y + 1);
            if (isLegalMove(x, y, p, c))
                moves.add(p);
        }
        return moves;
    }

    public boolean isLegalMove(int x, int y, Move p, UI player) {
        return player.isValidMove(x, y, p.getX(), p.getY(), this);
    }

    // Checks if the king is checked or not
    public boolean checked(UI c) {
        // up
        int n = -1;
        while (x + n >= 0) {
            if (c.chessBoard[x + n][y].hasPiece) {
                if (c.chessBoard[x + n][y].piece.color != color && (
                        c.chessBoard[x + n][y].pieceType == 2
                                || c.chessBoard[x + n][y].pieceType == 6))
                    return true;
                break;
            }
            n--;
        }

        // left
        n = -1;
        while (y + n >= 0) {
            if (c.chessBoard[x][y + n].hasPiece) {
                if (c.chessBoard[x][y + n].piece.color != color && (
                        c.chessBoard[x][y + n].pieceType == 2
                                || c.chessBoard[x][y + n].pieceType == 6))
                    return true;
                break;
            }
            n--;
        }

        // down
        n = 1;
        while (x + n <= 7) {
            if (c.chessBoard[x + n][y].hasPiece) {
                if (c.chessBoard[x + n][y].piece.color != color && (
                        c.chessBoard[x + n][y].pieceType == 2
                                || c.chessBoard[x + n][y].pieceType == 6))
                    return true;
                break;
            }
            n++;
        }

        // right
        n = 1;
        while (y + n <= 7) {
            if (c.chessBoard[x][y + n].hasPiece) {
                if (c.chessBoard[x][y + n].piece.color != color && (
                        c.chessBoard[x][y + n].pieceType == 2
                                || c.chessBoard[x][y + n].pieceType == 6))
                    return true;
                break;
            }
            n++;
        }

        // up-left
        n = -1;
        while (x + n >= 0 && y + n >= 0) {
            if (c.chessBoard[x + n][y + n].hasPiece) {
                if (c.chessBoard[x + n][y + n].piece.color != color && (
                        c.chessBoard[x + n][y + n].pieceType == 4
                                || c.chessBoard[x + n][y + n].pieceType == 6))
                    return true;
                break;
            }
            n--;
        }

        // up-right
        n = -1;
        int m = 1;
        while (x + n >= 0 && y + m <= 7) {
            if (c.chessBoard[x + n][y + m].hasPiece) {
                if (c.chessBoard[x + n][y + m].piece.color != color && (
                        c.chessBoard[x + n][y + m].pieceType == 4
                                || c.chessBoard[x + n][y + m].pieceType == 6))
                    return true;
                break;
            }
            n--;
            m++;
        }

        // down-left
        n = 1;
        m = -1;
        while (x + n <= 7 && y + m >= 0) {
            if (c.chessBoard[x + n][y + m].hasPiece) {
                if (c.chessBoard[x + n][y + m].piece.color != color && (
                        c.chessBoard[x + n][y + m].pieceType == 4
                                || c.chessBoard[x + n][y + m].pieceType == 6))
                    return true;
                break;
            }
            n++;
            m--;
        }

        // down-right
        n = 1;
        while (x + n <= 7 && y + n <= 7) {
            if (c.chessBoard[x + n][y + n].hasPiece) {
                if (c.chessBoard[x + n][y + n].piece.color != color && (
                        c.chessBoard[x + n][y + n].pieceType == 4
                                || c.chessBoard[x + n][y + n].pieceType == 6))
                    return true;
                break;
            }
            n++;
        }
        return false;
    }
}

