import java.util.LinkedList;

// implements basic rules of pawn movement
public class Pawn extends Piece {
    private boolean hasMoved; // checks whether pawn has already moved

    public Pawn(int color, int x, int y, int type) {
        super(color, x, y, type);
        hasMoved = false;
    }

    public LinkedList<Move> getPossibleMoves(UI player) {
        LinkedList<Move> moves = new LinkedList<>();
        Move p;
        // for white pawns
        if (color == 0) {
            // Check for legal moves forward
            if (x - 1 > 0 && !player.chessBoard[x - 1][y].hasPiece) {
                // Move one square forward
                if (x == 6 && !player.chessBoard[x - 2][y].hasPiece) {
                    p = new Move(x - 2, y);
                    if (isLegalMove(x, y, p, player))
                        moves.add(p);
                }
                p = new Move(x - 1, y);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
            }
        }
        // for black pawns
        else {
            // Check for legal moves forward
            if (x + 1 < 8 && !player.chessBoard[x + 1][y].hasPiece) {
                // Move one square forward
                if (x == 1 && !player.chessBoard[x + 2][y].hasPiece) {
                    p = new Move(x + 2, y);
                    if (isLegalMove(x, y, p, player)) {
                        moves.add(p);
                    }
                }
                p = new Move(x + 1, y);
                if (isLegalMove(x, y, p, player)) {
                    moves.add(p);
                }
            }
        }

        // white pawns
        if (color == 0 && x - 1 > 0) {
            // Checks for capture diagonally left
            if (y - 1 >= 0 && player.chessBoard[x - 1][y - 1].hasPiece
                    && player.chessBoard[x - 1][y - 1].piece.color != color) {
                p = new Move(x - 1, y - 1);
                if (isLegalMove(x, y, p, player)) {
                    moves.add(p);
                }
            }
            // Checks for legal capture diagonally right
            if (y + 1 < 8 && player.chessBoard[x - 1][y + 1].hasPiece
                    && player.chessBoard[x - 1][y + 1].piece.color != color) {
                p = new Move(x - 1, y + 1);
                if (isLegalMove(x, y, p, player)) {
                    moves.add(p);
                }
            }
        }

        // black pawns
        else if (color == 1 && x + 1 < 8) {
            if (y - 1 >= 0 && player.chessBoard[x + 1][y - 1].hasPiece
                    && player.chessBoard[x + 1][y - 1].piece.color != color) {
                p = new Move(x + 1, y - 1);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
            }
            if (y + 1 < 8 && player.chessBoard[x + 1][y + 1].hasPiece
                    && player.chessBoard[x + 1][y + 1].piece.color != color) {
                p = new Move(x + 1, y + 1);
                if (isLegalMove(x, y, p, player))
                    moves.add(p);
            }
        }
        return moves;
    }

    // checks if pawn has moved to determine if it can move forward twice
    public boolean hasMoved() {
        return hasMoved;
    }

    // sets boolean for hasMoved depending on if pawn has moved or not
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // checks valid moves for each piece
    public boolean isLegalMove(int x, int y, Move move, UI player) {
        return player.isValidMove(x, y, move.getX(), move.getY(), this);
    }

    public boolean checked(UI player) {
        return false;
    }
}
