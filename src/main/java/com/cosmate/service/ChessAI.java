package com.cosmate.service;

import com.cosmate.model.*;
import java.util.LinkedList;

public class ChessAI {
    private static final int DEPTH = 3;
    
    // Piece values for evaluation
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    // Track previous moves to prevent repetition
    private Move lastMove = null;
    private Move secondLastMove = null;

    public ChessAI() { }

    // Main method to get the best move for black
    public Move getBestMove(GameState gameState) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;
        ChessVisualizer[][] board = gameState.getBoard();

        // Get all possible moves for black pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasPiece() && board[i][j].getPiece().getColor() == 1) {
                    LinkedList<Move> possibleMoves = board[i][j].getPiece().getPossibleMoves(gameState);
                    
                    for (Move move : possibleMoves) {
                        // Create a copy of the game state
                        GameState tempState = new GameState(gameState);
                        ChessVisualizer[][] tempBoard = tempState.getBoard();
                        
                        // Make temporary move
                        Piece piece = tempBoard[i][j].getPiece();
                        tempBoard[i][j].removePiece();
                        tempBoard[move.getToX()][move.getToY()].addPiece(piece);
                        piece.setLocation(move.getToX(), move.getToY());
                        
                        // Evaluate position after move
                        int score = minimax(tempState, DEPTH - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = move;
                        }
                    }
                }
            }
        }
        
        if (bestMove != null) {
            secondLastMove = lastMove;
            lastMove = bestMove;
        }
        
        return bestMove;
    }

    private int minimax(GameState state, int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0) {
            return evaluatePosition(state);
        }

        ChessVisualizer[][] board = state.getBoard();
        int color = isMaximizing ? 1 : 0;

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].hasPiece() && board[i][j].getPiece().getColor() == color) {
                        LinkedList<Move> moves = board[i][j].getPiece().getPossibleMoves(state);
                        for (Move move : moves) {
                            GameState tempState = new GameState(state);
                            makeMove(tempState, i, j, move);
                            
                            int score = minimax(tempState, depth - 1, false, alpha, beta);
                            maxScore = Math.max(maxScore, score);
                            alpha = Math.max(alpha, score);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
            }
            return maxScore;
        } else {
            int minScore = Integer.MAX_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].hasPiece() && board[i][j].getPiece().getColor() == color) {
                        LinkedList<Move> moves = board[i][j].getPiece().getPossibleMoves(state);
                        for (Move move : moves) {
                            GameState tempState = new GameState(state);
                            makeMove(tempState, i, j, move);
                            
                            int score = minimax(tempState, depth - 1, true, alpha, beta);
                            minScore = Math.min(minScore, score);
                            beta = Math.min(beta, score);
                            if (beta <= alpha) {
                                break;
                            }
                        }
                    }
                }
            }
            return minScore;
        }
    }

    private void makeMove(GameState state, int fromX, int fromY, Move move) {
        ChessVisualizer[][] board = state.getBoard();
        Piece piece = board[fromX][fromY].getPiece();
        board[fromX][fromY].removePiece();
        board[move.getToX()][move.getToY()].addPiece(piece);
        piece.setLocation(move.getToX(), move.getToY());
        state.updateKingPosition(move, piece);
    }

    private int evaluatePosition(GameState state) {
        int score = 0;
        ChessVisualizer[][] board = state.getBoard();
        
        // Material evaluation
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasPiece()) {
                    Piece piece = board[i][j].getPiece();
                    int pieceValue = getPieceValue(piece);
                    
                    if (piece.getColor() == 1) {  // Black (AI)
                        score += pieceValue;
                        // Add development bonus for pieces moved from starting position
                        if (piece.getType() != 5 && piece.getType() != 4) { // Not king or queen
                            if (i != 0 && i != 1) { // Not in starting rows
                                score += 10; // Development bonus
                            }
                        }
                    } else {
                        score -= pieceValue;
                    }
                }
            }
        }
        
        // Penalize repetitive moves
        if (lastMove != null && secondLastMove != null) {
            if (lastMove.getToX() == secondLastMove.getToX() && 
                lastMove.getToY() == secondLastMove.getToY()) {
                score -= 50; // Penalty for moving back and forth
            }
        }
        
        return score;
    }

    private int getPieceValue(Piece piece) {
        switch (piece.getType()) {
            case 0: return PAWN_VALUE;
            case 3: return ROOK_VALUE;
            case 1: return KNIGHT_VALUE;
            case 2: return BISHOP_VALUE;
            case 5: return KING_VALUE;
            case 4: return QUEEN_VALUE;
            default: return 0;
        }
    }
}
