package com.cosmate.model;

/**
 * Represents a square on the chess board and manages the piece on it.
 */
public class ChessVisualizer {
    private int x;
    private int y;
    private boolean hasPiece;
    private Piece piece;
    private int pieceType;

    /**
     * Creates an empty square
     */
    public ChessVisualizer(int x, int y) {
        this.x = x;
        this.y = y;
        this.hasPiece = false;
        this.piece = null;
        this.pieceType = 0;
    }

    /**
     * Creates a square with a piece
     */
    public ChessVisualizer(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.hasPiece = true;
        this.pieceType = piece.getType();
    }

    /**
     * Copy constructor for creating a deep copy
     */
    public ChessVisualizer(ChessVisualizer other) {
        this.x = other.x;
        this.y = other.y;
        this.hasPiece = other.hasPiece;
        if (other.piece != null) {
            this.piece = other.piece.copy();
        }
        this.pieceType = other.pieceType;
    }

    /**
     * Gets the piece's color (-1 if empty)
     */
    public int getPieceColor() {
        if (piece == null) {
            return -1;
        }
        return piece.getColor();
    }

    /**
     * Adds a piece to this square
     */
    public void addPiece(Piece piece) {
        this.piece = piece;
        this.hasPiece = true;
        this.pieceType = piece.getType();
    }

    /**
     * Removes the piece from this square
     */
    public void removePiece() {
        this.piece = null;
        this.hasPiece = false;
        this.pieceType = 0;
    }

    /**
     * Gets the piece on this square
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Checks if this square has a piece
     */
    public boolean hasPiece() {
        return hasPiece;
    }

    /**
     * Gets the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the piece type (0 if empty)
     */
    public int getPieceType() {
        return pieceType;
    }

    /**
     * Gets the chess notation symbol for the piece
     */
    public String getSymbol() {
        if (!hasPiece) {
            return ".";
        }
        
        String symbols = "PRNBKQ";  // Piece symbols in order of type
        char symbol = symbols.charAt(pieceType);
        return piece.getColor() == 0 ? String.valueOf(symbol) : String.valueOf(symbol).toLowerCase();
    }
}

