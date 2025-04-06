package com.cosmate.model;

import java.util.LinkedList;

public class Queen extends Piece {
    public Queen(int color) {
        super(color, -1, -1, 4); // type 4 for Queen
    }

    @Override
    public LinkedList<Move> getPossibleMoves(GameState gameState) {
        LinkedList<Move> moves = new LinkedList<>();
        
        // Queen combines Rook and Bishop movements
        // Check all eight directions
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},  // Rook moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}  // Bishop moves
        };
        
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
        // Must be either diagonal or straight movement
        boolean isDiagonal = Math.abs(x2 - x) == Math.abs(y2 - y);
        boolean isStraight = x == x2 || y == y2;
        
        if (!isDiagonal && !isStraight) {
            return false;
        }
        
        // Check path is clear
        if (isStraight) {
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
        } else {
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
        }
        
        return true;
    }

    @Override
    public boolean checked(GameState gameState) {
        return false; // Queens don't implement check detection
    }
}

