package com.cosmate.model;

import java.util.LinkedList;

public class Rook extends Piece {
    public Rook(int color) {
        super(color, -1, -1, 3); // type 3 for Rook
    }

    @Override
    public LinkedList<Move> getPossibleMoves(GameState gameState) {
        LinkedList<Move> moves = new LinkedList<>();
        
        // Check horizontal and vertical directions
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        
        for (int[] dir : directions) {
            int newX = x;
            int newY = y;
            
            while (true) {
                newX += dir[0];
                newY += dir[1];
                
                if (!gameState.isValidPosition(newX, newY)) {
                    break;
                }
                
                Piece targetPiece = gameState.getPieceAt(newX, newY);
                if (targetPiece == null) {
                    moves.add(new Move(x, y, newX, newY));
                    continue;
                }
                
                if (targetPiece.getColor() != this.color) {
                    moves.add(new Move(x, y, newX, newY));
                }
                break;
            }
        }
        
        return moves;
    }

    @Override
    public boolean isLegalMove(int x2, int y2, Move move, GameState gameState) {
        // Must be horizontal or vertical movement
        if (x != x2 && y != y2) {
            return false;
        }
        
        // Check path is clear
        if (x == x2) {
            int dir = (y2 > y) ? 1 : -1;
            for (int currentY = y + dir; currentY != y2; currentY += dir) {
                if (gameState.getPieceAt(x, currentY) != null) {
                    return false;
                }
            }
        } else {
            int dir = (x2 > x) ? 1 : -1;
            for (int currentX = x + dir; currentX != x2; currentX += dir) {
                if (gameState.getPieceAt(currentX, y) != null) {
                    return false;
                }
            }
        }
        
        return true;
    }

    @Override
    public boolean checked(GameState gameState) {
        return false; // Rooks don't implement check detection
    }
}
