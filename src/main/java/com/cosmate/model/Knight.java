package com.cosmate.model;

import java.util.LinkedList;

public class Knight extends Piece {
    public Knight(int color) {
        super(color, -1, -1, 1); // type 1 for Knight
    }

    @Override
    public LinkedList<Move> getPossibleMoves(GameState gameState) {
        LinkedList<Move> moves = new LinkedList<>();
        
        // All possible L-shaped moves
        int[][] knightMoves = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2}, {1, 2},
            {2, -1}, {2, 1}
        };
        
        for (int[] move : knightMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            
            if (!gameState.isValidPosition(newX, newY)) {
                continue;
            }
            
            Piece targetPiece = gameState.getPieceAt(newX, newY);
            if (targetPiece == null || targetPiece.getColor() != this.color) {
                moves.add(new Move(x, y, newX, newY));
            }
        }
        
        return moves;
    }

    @Override
    public boolean isLegalMove(int x2, int y2, Move move, GameState gameState) {
        int dx = Math.abs(x2 - x);
        int dy = Math.abs(y2 - y);
        
        // Must move in L-shape (2 squares in one direction and 1 in the other)
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public boolean checked(GameState gameState) {
        return false; // Knights don't implement check detection
    }
}
