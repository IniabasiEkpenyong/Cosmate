import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;

// stores chessboard data concerning pieces on square, coordinates and color
public class ChessVisualizer {
    int xPoint;
    int yPoint;
    JButton button;
    boolean hasPiece;
    Piece piece;
    int pieceType;
    Color col;

    // Constructor for empty squares that creates a clickable button in the
    // chess window (UI)
    public ChessVisualizer(int x, int y, JButton b, JPanel panel) {
        xPoint = x;
        yPoint = y;
        button = b;
        hasPiece = false;
        piece = null;
        pieceType = 0;

        panel.add(button);
    }

    // Overloaded constructor for squares containing pieces that creates a
    // clickable button in the chess window (UI)
    public ChessVisualizer(int x, int y, JButton b, JPanel panel, Piece newPiece, UI player) {
        xPoint = x;
        yPoint = y;
        button = b;
        piece = newPiece;
        hasPiece = true;
        pieceType = piece.type;

        panel.add(button);
    }

    // return piece color on square if not empty
    public int checkPiece() {
        if (piece == null)
            return -1;
        return piece.color;
    }

    // Edits square data to include chess piece
    public void addPiece(Piece newPiece) {
        piece = newPiece;
        hasPiece = true;
        pieceType = piece.type;
        setImage(piece.type);
    }

    // sets image icon based on piece type and color on square
    public void setImage(int t) {
        if (piece.color == 0) {
            if (t == 1) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/W_P.png")));
            }
            else if (t == 2) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/W_R.png")));
            }
            else if (t == 3) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/W_N.png")));
            }
            else if (t == 4) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/W_B.png")));
            }
            else if (t == 5) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/W_K.png")));
            }
            else if (t == 6) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/W_Q.png")));
            }
        }
        else {
            if (t == 1) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/B_P.png")));
            }
            else if (t == 2) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/B_R.png")));
            }
            else if (t == 3) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/B_N.png")));
            }
            else if (t == 4) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/B_B.png")));
            }
            else if (t == 5) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/B_K.png")));
            }
            else if (t == 6) {
                button.setIcon(new ImageIcon(getClass().getResource("/icons/B_Q.png")));
            }
        }
    }

    // makes an empty square in place of a removed piece
    public void removePiece() {
        if (!hasPiece)
            return;
        piece = null;
        hasPiece = false;
        pieceType = 0;
        // button.setText("");
        button.setIcon(null);
    }

    // Test for actionMouseListener that checks if clicked piece is the correct
    // one to move a capital letter is printed for white pieces and lower-case
    // for black
    public String getSymbol() {
        if (piece.color == 0) {
            if (pieceType == 1) {
                return "P";
            }
            else if (pieceType == 2) {
                return "R";
            }
            else if (pieceType == 3) {
                return "N";
            }
            else if (pieceType == 4) {
                return "B";
            }
            else if (pieceType == 5) {
                return "K";
            }
            else if (pieceType == 6) {
                return "Q";
            }
        }
        else {
            if (pieceType == 1) {
                return "p";
            }
            else if (pieceType == 2) {
                return "r";
            }
            else if (pieceType == 3) {
                return "n";
            }
            else if (pieceType == 4) {
                return "b";
            }
            else if (pieceType == 5) {
                return "k";
            }
            else if (pieceType == 6) {
                return "q";
            }
        }
        return "";
    }
}

