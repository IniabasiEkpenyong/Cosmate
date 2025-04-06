package com.cosmate.service;

import org.springframework.stereotype.Service;
import com.cosmate.model.*;

@Service
public class GameService {
    private GameState gameState;
    private ChessAI ai;

    public GameService() {
        this.gameState = new GameState();
        this.ai = new ChessAI();
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize the chess board with pieces in starting positions
        ChessVisualizer[][] board = new ChessVisualizer[8][8];
        
        // Initialize all squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new ChessVisualizer(i, j);
            }
        }

        // Set up black pieces
        board[0][0].addPiece(new Rook(1));
        board[0][1].addPiece(new Knight(1));
        board[0][2].addPiece(new Bishop(1));
        board[0][3].addPiece(new Queen(1));
        board[0][4].addPiece(new King(1));
        board[0][5].addPiece(new Bishop(1));
        board[0][6].addPiece(new Knight(1));
        board[0][7].addPiece(new Rook(1));
        for (int i = 0; i < 8; i++) {
            board[1][i].addPiece(new Pawn(1));
        }

        // Set up white pieces
        board[7][0].addPiece(new Rook(0));
        board[7][1].addPiece(new Knight(0));
        board[7][2].addPiece(new Bishop(0));
        board[7][3].addPiece(new Queen(0));
        board[7][4].addPiece(new King(0));
        board[7][5].addPiece(new Bishop(0));
        board[7][6].addPiece(new Knight(0));
        board[7][7].addPiece(new Rook(0));
        for (int i = 0; i < 8; i++) {
            board[6][i].addPiece(new Pawn(0));
        }

        gameState.setBoard(board);
    }

    public GameState makeMove(Move move) {
        if (isValidMove(move)) {
            executeMove(move);
            updateGameStatus();
            
            if (gameState.isVsComputer() && gameState.getTurn() == 1) {
                Move aiMove = ai.getBestMove(gameState);
                executeMove(aiMove);
                updateGameStatus();
            }
        }
        return gameState;
    }

    private boolean isValidMove(Move move) {
        ChessVisualizer[][] board = gameState.getBoard();
        if (!gameState.isValidPosition(move.getFromX(), move.getFromY()) ||
            !gameState.isValidPosition(move.getToX(), move.getToY())) {
            return false;
        }

        ChessVisualizer fromSquare = board[move.getFromX()][move.getFromY()];
        if (!fromSquare.hasPiece()) {
            return false;
        }

        Piece piece = fromSquare.getPiece();
        if (piece.getColor() != gameState.getTurn()) {
            return false;
        }

        return piece.isLegalMove(move.getToX(), move.getToY(), move, gameState);
    }

    private void executeMove(Move move) {
        ChessVisualizer[][] board = gameState.getBoard();
        Piece piece = board[move.getFromX()][move.getFromY()].getPiece();
        
        board[move.getFromX()][move.getFromY()].removePiece();
        board[move.getToX()][move.getToY()].addPiece(piece);
        piece.setLocation(move.getToX(), move.getToY());
        
        gameState.updateKingPosition(move, piece);
        gameState.setTurn(gameState.getTurn() == 0 ? 1 : 0);
    }

    private void updateGameStatus() {
        boolean inCheck = isKingInCheck(gameState.getTurn());
        gameState.setCheck(inCheck);
        
        if (inCheck && !hasLegalMoves(gameState.getTurn())) {
            gameState.setCheckmate(true);
        } else if (!inCheck && !hasLegalMoves(gameState.getTurn())) {
            gameState.setStalemate(true);
        }
    }

    private boolean isKingInCheck(int color) {
        Move kingPos = color == 0 ? gameState.getWhiteKingPosition() : gameState.getBlackKingPosition();
        return gameState.getBoard()[kingPos.getFromX()][kingPos.getFromY()].getPiece().checked(gameState);
    }

    private boolean hasLegalMoves(int color) {
        ChessVisualizer[][] board = gameState.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].hasPiece() && board[i][j].getPiece().getColor() == color) {
                    if (!board[i][j].getPiece().getPossibleMoves(gameState).isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void newGame() {
        gameState = new GameState();
        initializeBoard();
    }
}
