package com.cosmate.model;
import java.util.LinkedList;

/**
 * Abstract base class for all chess pieces.
 * Defines common properties and behaviors that all chess pieces share.
 */
public abstract class Piece {
    protected int color;    // 0 = white, 1 = black
    protected int x;        // horizontal location (0-7)
    protected int y;        // vertical location (0-7)
    protected int type;     // 0=pawn, 1=knight, 2=bishop, 3=rook, 4=queen, 5=king

    /**
     * Creates a new piece with the specified properties.
     * @param color The piece color (0 for white, 1 for black)
     * @param x Initial x position (0-7)
     * @param y Initial y position (0-7)
     * @param type Piece type (0=pawn, 1=knight, 2=bishop, 3=rook, 4=queen, 5=king)
     */
    public Piece(int color, int x, int y, int type) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    // Getters
    public int getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    /**
     * Updates the piece's position on the board.
     * @param x2 New x position
     * @param y2 New y position
     */
    public void setLocation(int x2, int y2) {
        this.x = x2;
        this.y = y2;
    }

    /**
     * Creates a deep copy of this piece.
     * @return A new piece with the same properties
     */
    public Piece copy() {
        Piece newPiece = null;
        switch (this.type) {
            case 0: newPiece = new Pawn(this.color); break;
            case 1: newPiece = new Knight(this.color); break;
            case 2: newPiece = new Bishop(this.color); break;
            case 3: newPiece = new Rook(this.color); break;
            case 4: newPiece = new Queen(this.color); break;
            case 5: newPiece = new King(this.color); break;
        }
        if (newPiece != null) {
            newPiece.setLocation(this.x, this.y);
        }
        return newPiece;
    }

    /**
     * Gets all possible moves for this piece in the current game state.
     * @param gameState Current state of the game
     * @return List of all possible moves
     */
    public abstract LinkedList<Move> getPossibleMoves(GameState gameState);

    /**
     * Checks if a move is legal for this piece.
     * @param x2 Target x position
     * @param y2 Target y position
     * @param move The move to check
     * @param gameState Current state of the game
     * @return true if the move is legal, false otherwise
     */
    public abstract boolean isLegalMove(int x2, int y2, Move move, GameState gameState);

    /**
     * Checks if this piece is putting the opponent's king in check.
     * Only implemented by King class, others return false.
     * @param gameState Current state of the game
     * @return true if this piece is checking the opponent's king
     */
    public abstract boolean checked(GameState gameState);

    /**
     * Helper method to check if a position is within board bounds.
     * @param x X position to check
     * @param y Y position to check
     * @return true if position is valid
     */
    protected boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    /**
     * Helper method to check if a move would put the king in check.
     * @param move The move to check
     * @param gameState Current state of the game
     * @return true if the move would result in check
     */
    protected boolean wouldResultInCheck(Move move, GameState gameState) {
        // Create a temporary game state
        GameState tempState = new GameState(gameState);
        ChessVisualizer[][] board = tempState.getBoard();
        
        // Make the move
        Piece originalPiece = board[move.getFromX()][move.getFromY()].getPiece();
        board[move.getFromX()][move.getFromY()].removePiece();
        board[move.getToX()][move.getToY()].addPiece(originalPiece);
        originalPiece.setLocation(move.getToX(), move.getToY());

        // Update king position if moving a king
        if (this.type == 5) {
            if (this.color == 0) {
                tempState.setWhiteKingPosition(new Move(move.getToX(), move.getToY()));
            } else {
                tempState.setBlackKingPosition(new Move(move.getToX(), move.getToY()));
            }
        }

        // Check if king is in check after move
        Move kingPos = (this.color == 0) ? 
            tempState.getWhiteKingPosition() : 
            tempState.getBlackKingPosition();
        
        return board[kingPos.getFromX()][kingPos.getFromY()].getPiece().checked(tempState);
    }
}
