package com.cosmate.model;

import java.util.List;
import java.util.LinkedList;

public class GameState {
    private ChessVisualizer[][] board;
    private int turn; // 0 for white, 1 for black
    private boolean check;
    private boolean stalemate;
    private boolean checkmate;
    private Move whiteKingPosition;
    private Move blackKingPosition;
    private List<Move> possibleMoves;
    private String message;
    private boolean vsComputer;
    private boolean gameOver;
    private String winner;

    // Constructor
    public GameState() {
        this.board = new ChessVisualizer[8][8];
        this.turn = 0; // White starts
        this.check = false;
        this.stalemate = false;
        this.checkmate = false;
        this.whiteKingPosition = new Move(7, 3); // Initial white king position
        this.blackKingPosition = new Move(0, 3); // Initial black king position
        this.possibleMoves = new LinkedList<>();
        this.message = "White's turn";
        this.vsComputer = true;
        this.gameOver = false;
        this.winner = null;
    }

    // Copy constructor for creating a deep copy of the game state
    public GameState(GameState other) {
        this.board = new ChessVisualizer[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (other.board[i][j] != null) {
                    this.board[i][j] = new ChessVisualizer(other.board[i][j]);
                }
            }
        }
        this.turn = other.turn;
        this.check = other.check;
        this.stalemate = other.stalemate;
        this.checkmate = other.checkmate;
        this.whiteKingPosition = new Move(other.whiteKingPosition);
        this.blackKingPosition = new Move(other.blackKingPosition);
        this.possibleMoves = new LinkedList<>(other.possibleMoves);
        this.message = other.message;
        this.vsComputer = other.vsComputer;
        this.gameOver = other.gameOver;
        this.winner = other.winner;
    }

    // Getters and Setters
    public ChessVisualizer[][] getBoard() {
        return board;
    }

    public void setBoard(ChessVisualizer[][] board) {
        this.board = board;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
        updateMessage();
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
        updateMessage();
    }

    public boolean isStalemate() {
        return stalemate;
    }

    public void setStalemate(boolean stalemate) {
        this.stalemate = stalemate;
        if (stalemate) {
            this.gameOver = true;
            this.message = "Game Over - Stalemate!";
        }
    }

    public boolean isCheckmate() {
        return checkmate;
    }

    public void setCheckmate(boolean checkmate) {
        this.checkmate = checkmate;
        if (checkmate) {
            this.gameOver = true;
            this.winner = (turn == 0) ? "Black" : "White";
            this.message = "Checkmate! " + winner + " wins!";
        }
    }

    public Move getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public void setWhiteKingPosition(Move whiteKingPosition) {
        this.whiteKingPosition = whiteKingPosition;
    }

    public Move getBlackKingPosition() {
        return blackKingPosition;
    }

    public void setBlackKingPosition(Move blackKingPosition) {
        this.blackKingPosition = blackKingPosition;
    }

    public List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Move> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isVsComputer() {
        return vsComputer;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    // Helper methods
    private void updateMessage() {
        if (!gameOver) {
            String playerTurn = (turn == 0) ? "White" : "Black";
            this.message = check ? 
                playerTurn + " is in check!" : 
                playerTurn + "'s turn";
        }
    }

    // Method to get a piece at a specific position
    public Piece getPieceAt(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8 && board[x][y] != null) {
            return board[x][y].getPiece();
        }
        return null;
    }

    // Method to check if a position is valid
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    // Method to check if a position is empty
    public boolean isEmptyPosition(int x, int y) {
        return isValidPosition(x, y) && 
               (board[x][y] == null || !board[x][y].hasPiece());
    }

    // Method to update king positions after a move
    public void updateKingPosition(Move move, Piece piece) {
        if (piece != null && piece.getType() == 5) { // 5 represents King
            if (piece.getColor() == 0) { // White
                this.whiteKingPosition = new Move(move);
            } else { // Black
                this.blackKingPosition = new Move(move);
            }
        }
    }

    // Method to create a JSON representation of the game state
    @Override
    public String toString() {
        return String.format(
            "GameState{turn=%d, check=%b, stalemate=%b, checkmate=%b, message='%s', gameOver=%b}",
            turn, check, stalemate, checkmate, message, gameOver
        );
    }

    // Method to reset the game state
    public void reset() {
        this.turn = 0;
        this.check = false;
        this.stalemate = false;
        this.checkmate = false;
        this.whiteKingPosition = new Move(7, 3);
        this.blackKingPosition = new Move(0, 3);
        this.possibleMoves.clear();
        this.message = "White's turn";
        this.gameOver = false;
        this.winner = null;
        // Board will be reset separately in the GameService
    }

    // Method to validate a move
    public boolean isValidMove(Move move) {
        // Check if the move is within bounds
        if (!isValidPosition(move.getFromX(), move.getFromY()) || 
            !isValidPosition(move.getToX(), move.getToY())) {
            return false;
        }

        // Check if there is a piece at the starting position
        Piece piece = getPieceAt(move.getFromX(), move.getFromY());
        if (piece == null) {
            return false;
        }

        // Check if it's the correct player's turn
        if (piece.getColor() != turn) {
            return false;
        }

        // Check if the move is in the list of possible moves
        return possibleMoves.contains(move);
    }
}