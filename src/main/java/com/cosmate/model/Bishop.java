package com.cosmate.model;

import java.util.LinkedList;

public class Bishop extends Piece {
    public Bishop(int color) {
        super(color, -1, -1, 2); // type 2 for Bishop
    }

    @Override
    public LinkedList<Move> getPossibleMoves(GameState gameState) {
        LinkedList<Move> moves = new LinkedList<>();
        
        // Check all four diagonal directions
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        
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
        // Must be diagonal movement
        if (Math.abs(x2 - x) != Math.abs(y2 - y)) {
            return false;
        }
        
        // Check path is clear
        int xDir = (x2 > x) ? 1 : -1;
        int yDir = (y2 > y) ? 1 : -1;
        
        int currentX = x + xDir;
        int currentY = y + yDir;
        
        while (currentX != x2 && currentY != y2) {
            if (gameState.getPieceAt(currentX, currentY) != null) {
                return false;
            }
            currentX += xDir;
            currentY += yDir;
        }
        
        return true;
    }

    @Override
    public boolean checked(GameState gameState) {
        return false; // Bishops don't implement check detection
    }
}
