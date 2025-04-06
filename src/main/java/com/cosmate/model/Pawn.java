package com.cosmate.model;

import java.util.LinkedList;

public class Pawn extends Piece {
    public Pawn(int color) {
        super(color, -1, -1, 0); // type 0 for Pawn
    }

    @Override
    public LinkedList<Move> getPossibleMoves(GameState gameState) {
        LinkedList<Move> moves = new LinkedList<>();
        
        // Direction depends on color (white moves up, black moves down)
        int direction = (color == 0) ? -1 : 1;
        
        // Forward move
        int newX = x + direction;
        if (gameState.isValidPosition(newX, y) && gameState.getPieceAt(newX, y) == null) {
            moves.add(new Move(x, y, newX, y));
            
            // Initial two-square move
            if ((color == 0 && x == 6) || (color == 1 && x == 1)) {
                int twoSquares = x + (2 * direction);
                if (gameState.getPieceAt(twoSquares, y) == null) {
                    moves.add(new Move(x, y, twoSquares, y));
                }
            }
        }
        
        // Capture moves
        for (int dy = -1; dy <= 1; dy += 2) {
            int newY = y + dy;
            if (gameState.isValidPosition(newX, newY)) {
                Piece targetPiece = gameState.getPieceAt(newX, newY);
                if (targetPiece != null && targetPiece.getColor() != this.color) {
                    moves.add(new Move(x, y, newX, newY));
                }
            }
        }
        
        return moves;
    }

    @Override
    public boolean isLegalMove(int x2, int y2, Move move, GameState gameState) {
        int direction = (color == 0) ? -1 : 1;
        int dx = x2 - x;
        int dy = Math.abs(y2 - y);
        
        // Forward move
        if (dy == 0) {
            // One square forward
            if (dx == direction) {
                return gameState.getPieceAt(x2, y2) == null;
            }
            // Initial two squares
            if (((color == 0 && x == 6) || (color == 1 && x == 1)) && dx == 2 * direction) {
                return gameState.getPieceAt(x2, y2) == null && 
                       gameState.getPieceAt(x + direction, y) == null;
            }
        }
        // Capture move
        else if (dy == 1 && dx == direction) {
            Piece targetPiece = gameState.getPieceAt(x2, y2);
            return targetPiece != null && targetPiece.getColor() != this.color;
        }
        
        return false;
    }

    @Override
    public boolean checked(GameState gameState) {
        return false; // Pawns don't implement check detection
    }
}
