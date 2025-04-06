package com.cosmate.model;
// This class deals with the coordinate values of the moves of each
// of the chess pieces

/**
 * Represents a chess move from one position to another.
 */
public class Move {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    /**
     * Copy constructor
     */
    public Move(Move other) {
        this.fromX = other.fromX;
        this.fromY = other.fromY;
        this.toX = other.toX;
        this.toY = other.toY;
    }

    /**
     * Constructor for storing a single position (used for king positions)
     */
    public Move(int x, int y) {
        this.fromX = x;
        this.fromY = y;
        this.toX = x;
        this.toY = y;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public void setNewValues(int x, int y) {
        this.fromX = x;
        this.fromY = y;
        this.toX = x;
        this.toY = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Move)) return false;
        Move other = (Move) obj;
        return fromX == other.fromX && 
               fromY == other.fromY && 
               toX == other.toX && 
               toY == other.toY;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + fromX;
        result = 31 * result + fromY;
        result = 31 * result + toX;
        result = 31 * result + toY;
        return result;
    }

    // returns a String of the coordinates of the movement
    public String toString() {
        return fromX + ", " + fromY + " to " + toX + ", " + toY;
    }
}


