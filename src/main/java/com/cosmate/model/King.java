package com.cosmate.model;

import java.util.LinkedList;

public class King extends Piece {
    public King(int color) {
        super(color, -1, -1, 5); // type 5 for King
    }

    @Override
    public LinkedList<Move> getPossibleMoves(GameState gameState) {
        LinkedList<Move> moves = new LinkedList<>();
        
        // Check all eight surrounding squares
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                
                int newX = x + dx;
                int newY = y + dy;
                
                if (!gameState.isValidPosition(newX, newY)) {
                    continue;
                }
                
                Piece targetPiece = gameState.getPieceAt(newX, newY);
                if (targetPiece == null || targetPiece.getColor() != this.color) {
                    Move move = new Move(x, y, newX, newY);
                    // Only add move if it doesn't put king in check
                    if (!wouldBeInCheck(move, gameState)) {
                        moves.add(move);
                    }
                }
            }
        }
        
        return moves;
    }

    @Override
    public boolean isLegalMove(int x2, int y2, Move move, GameState gameState) {
        // Must move only one square in any direction
        int dx = Math.abs(x2 - x);
        int dy = Math.abs(y2 - y);
        
        if (dx > 1 || dy > 1) {
            return false;
        }
        
        // Check if move would put king in check
        return !wouldBeInCheck(move, gameState);
    }

    @Override
    public boolean checked(GameState gameState) {
        // Check if any enemy piece can capture the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = gameState.getPieceAt(i, j);
                if (piece != null && piece.getColor() != this.color) {
                    LinkedList<Move> moves = piece.getPossibleMoves(gameState);
                    for (Move move : moves) {
                        if (move.getToX() == x && move.getToY() == y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean wouldBeInCheck(Move move, GameState gameState) {
        // Create temporary game state to test move
        GameState tempState = new GameState(gameState);
        ChessVisualizer[][] board = tempState.getBoard();
        
        // Make the move
        board[move.getToX()][move.getToY()].addPiece(this);
        board[move.getFromX()][move.getFromY()].removePiece();
        
        // Update king position in temporary state
        if (color == 0) {
            tempState.setWhiteKingPosition(new Move(move.getToX(), move.getToY()));
        } else {
            tempState.setBlackKingPosition(new Move(move.getToX(), move.getToY()));
        }
        
        // Check if king would be in check
        return checked(tempState);
    }
}

