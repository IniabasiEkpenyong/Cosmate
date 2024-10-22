import java.util.LinkedList;

// implements basic rules of knight movement
public class Knight extends Piece {

    public Knight(int color, int x, int y, int type) {
        super(color, x, y, type);
    }

    public LinkedList<Move> getPossibleMoves(UI player) {
        LinkedList<Move> moves = new LinkedList<>();
        Move move;
        // for horse on left
        // l-movements to the left and right upwards
        if (x <= 6) {
            if (x <= 5) {
                if (y - 1 >= 0 && player.chessBoard[x + 2][y - 1].checkPiece() != color) {
                    move = new Move(x + 2, y - 1);
                    if (isLegalMove(x, y, move, player))
                        moves.add(move);
                }
                if (y + 1 <= 7 && player.chessBoard[x + 2][y + 1].checkPiece() != color) {
                    move = new Move(x + 2, y + 1);
                    if (isLegalMove(x, y, move, player))
                        moves.add(move);
                }
            }
            if (y - 2 >= 0 && player.chessBoard[x + 1][y - 2].checkPiece() != color) {
                move = new Move(x + 1, y - 2);
                if (isLegalMove(x, y, move, player))
                    moves.add(move);

            }
            if (y + 2 <= 7 && player.chessBoard[x + 1][y + 2].checkPiece() != color) {
                move = new Move(x + 1, y + 2);
                if (isLegalMove(x, y, move, player))
                    moves.add(move);
            }
        }

        // for horse on right
        // l-movements to the left and right upwards and left and right downwards
        if (x >= 1) {
            if (x >= 2) {
                if (y - 1 >= 0 && player.chessBoard[x - 2][y - 1].checkPiece() != color) {
                    move = new Move(x - 2, y - 1);
                    if (isLegalMove(x, y, move, player))
                        moves.add(move);
                }
                if (y + 1 <= 7 && player.chessBoard[x - 2][y + 1].checkPiece() != color) {
                    move = new Move(x - 2, y + 1);
                    if (isLegalMove(x, y, move, player))
                        moves.add(move);
                }
            }
            if (y - 2 >= 0 && player.chessBoard[x - 1][y - 2].checkPiece() != color) {
                move = new Move(x - 1, y - 2);
                if (isLegalMove(x, y, move, player))
                    moves.add(move);
            }
            if (y + 2 <= 7 && player.chessBoard[x - 1][y + 2].checkPiece() != color) {
                move = new Move(x - 1, y + 2);
                if (isLegalMove(x, y, move, player))
                    moves.add(move);
            }
        }
        return moves;
    }

    public boolean isLegalMove(int x, int y, Move move, UI c) {
        return c.isValidMove(x, y, move.getX(), move.getY(), this);
    }

    public boolean checked(UI c) {
        return false;
    }
}
