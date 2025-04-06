
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import src.main.java.com.cosmate.model.ChessVisualizer;
import src.main.java.com.cosmate.model.King;
import src.main.java.com.cosmate.service.ChessAI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;

// This class defines the visual interface features of this chess program
// within it are methods that include initializing the board, updating piece
// visuals at a new location, highlighting potential moves, etc.
public class UI {
    private LinkedList<Move> highlightSpaces = new LinkedList<>();
    private JPanel panel; // stores window and panel information
    private JLabel label; // label to structure player turn display in window
    private Move blackKing; // stores current move of blackKing
    private Move whiteKing; // stores current move of whiteKing
    private int turn; // stores turn data where t = 0 for white and t = 1 for
    // black
    private boolean check; // stores whether or not a check has occurred
    private boolean stale; // stores whether or not a stale has occurred
    private ChessVisualizer currentPiece; // refers to location and type data
    // current piece

    ChessVisualizer[][] chessBoard = new ChessVisualizer[8][8];
    // public variable stores chessBoard data throughout classes

    private ChessAI ai;
    private boolean vsComputer = true; // Set to true to play against AI

    // constructor that initializes windows with chessboard and pieces
    public UI() {

        // creates window
        JFrame frame = new JFrame("Chess Game");
        panel = new JPanel();
        label = new JLabel("PLayer 1 turn");

        // informed of creation of window
        // https://www.tabnine.com/code/java/methods/javax.swing.JLabel/setHorizontalAlignment
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(900, 10));
        panel.setLayout(new GridLayout(8, 8));
        panel.setPreferredSize(new Dimension(900, 900));
        frame.add(panel, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initBoardState();
        frame.setBounds(50, 50, 900, 950);
        frame.setVisible(true);

        // set turn to initialize in changeTurn()
        turn = -1;
        changeTurn();

        // check and stalemate conditions are initialized to false
        check = false;
        stale = false;
        whiteKing = new Move(7, 3);
        blackKing = new Move(0, 3);

        ai = new ChessAI(this);
    }

    // iterates through possible moves of player for checkmate
    // returns false if moves are possible and king is in check
    private boolean checkCheckmate() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].hasPiece && chessBoard[i][j].piece.color == turn) {
                    if (chessBoard[i][j].piece.getPossibleMoves(this).size() != 0) {
                        return false;
                    }
                }

            }
        }

        // returns checkmate label based on winner
        if (turn == 1) {
            label.setText("Checkmate - Player 1 wins!");
        }
        else {
            label.setText("Checkmate - Player 2 wins!");
        }

        // no more clicks possible
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].hasPiece)
                    removeListeners(chessBoard[i][j], this);
            }
        }
        return true;
    }

    // prints message if no moves possible for both players
    private void stalemate() {
        label.setText("No available moves for either player; stalemate");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].hasPiece)
                    removeListeners(chessBoard[i][j], this);
            }
        }
    }

    // Checks the gamestate to determine if check has occurred (for the current turn's player)
    // Also checks for stalemate
    private boolean isChecked() {
        int x = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].hasPiece && chessBoard[i][j].piece.color != turn) {
                    // checks if any piece has a legal move to prevent check
                    LinkedList<Move> moves = chessBoard[i][j].piece.getPossibleMoves(
                            this);
                    if (moves.size() > 1) {
                        x = 1;
                    }

                    // examines if king has possible moves
                    // if not, isChecked() is true
                    for (int k = 0; k < moves.size(); k++) {
                        if (turn == 0) {
                            if (moves.get(k).getX() == whiteKing.getX()
                                    && moves.get(k).getY() == whiteKing.getY()) {
                                return true;
                            }
                        }
                        else {
                            if (moves.get(k).getX() == blackKing.getX()
                                    && moves.get(k).getY() == blackKing.getY()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        // if no possible moves, but not in check, stalemate
        if (x == 0) {
            if (stale) {
                stalemate();
                System.out.println("Stalemate");
            }
            else {
                stale = true;
                System.out.println("Stale set to true");
            }
        }

        return false;
    }

    // Used in isValidMove()
    public void makeMove(int x1, int y1, int x2, int y2, Piece p) {
        // move piece panel to the new location
        chessBoard[x2][y2].addPiece(p);

        // update whiteKing/blackKing if king was moved
        if (chessBoard[x2][y2].piece.type == 5) {
            if (chessBoard[x2][y2].piece.color == 0) {
                whiteKing.setNewValues(x2, y2);
            }
            else {
                blackKing.setNewValues(x2, y2);
            }
        }

        // removes panel from the old location
        chessBoard[x1][y1].removePiece();
        chessBoard[x2][y2].piece.updateLocation(x2, y2);    // set the new coordinates for panel
    }

    // Used in isValidMove()
    public void makeMove(int x1, int y1, int x2, int y2, Piece p, Piece replacedPiece) {
        chessBoard[x2][y2].addPiece(p);                // move piece panel to the new location

        if (chessBoard[x2][y2].piece.type
                == 5) {            // update whiteKing/blackKing if king was moved
            if (chessBoard[x2][y2].piece.color == 0)
                whiteKing.setNewValues(x2, y2);
            else
                blackKing.setNewValues(x2, y2);
        }

        chessBoard[x1][y1].removePiece();                // and remove panel from the old location
        chessBoard[x2][y2].piece.updateLocation(x2, y2);    // set the new coordinates for panel
        chessBoard[x1][y1].addPiece(replacedPiece);    // replace the old piece
    }

    public boolean isValidMove(int x1, int y1, int x2, int y2, Piece p) {
        // If white's turn, white move must not put white king in check state
        boolean returnValue = true;
        Piece removedPiece = null;
        if (chessBoard[x2][y2].hasPiece) {
            removedPiece = chessBoard[x2][y2].piece;
        }

        makeMove(x1, y1, x2, y2, p);        // try making the move
        if (turn == 0) {                    // and check for 'check' afterwards
            if (chessBoard[whiteKing.getX()][whiteKing.getY()].piece.checked(this)) {
                returnValue = false;
            }
        }
        else {
            if (chessBoard[blackKing.getX()][blackKing.getY()].piece.checked(this)) {
                returnValue = false;
            }
        }

        if (removedPiece == null)            // reset board to its original state
            makeMove(x2, y2, x1, y1, p);
        else
            makeMove(x2, y2, x1, y1, p, removedPiece);

        return returnValue;                // if no 'check', will return true
    }

    // Sets the button listeners to the pieces of whoever's turn it is and removes listeners from the opposing player's pieces
    private void changeTurn() {
        if (turn == -1 || turn == 1) {
            turn = 0;
            label.setText("Player 1 turn");
        } else {
            turn = 1;
            label.setText("Player 2 turn");
            
            if (vsComputer) {
                // Add small delay to make the AI move visible
                javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
                    makeAIMove();
                });
                timer.setRepeats(false);
                timer.start();
            }
        }

        // Tests using labels, with clicks if turn switches, different label is
        // presented
        if (check) {
            if (turn == 0) {
                label.setText("Player One has no more moves");
            }
            else {
                label.setText("Player Two has no more moves");
            }
        }
        else {
            if (turn == 0) {
                label.setText("Player One turn");
            }
            else {
                label.setText("Player Two turn");
            }
        }

        // highlights possible moves during turn
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard[i][j].hasPiece) {
                    if (chessBoard[i][j].piece.color == turn)
                        addHighlightListener(chessBoard[i][j], this);
                    else
                        removeListeners(chessBoard[i][j], this);
                }
            }
        }
    }

    // Adds listener to show valid move locations with highlight
    private void addHighlightListener(ChessVisualizer b, UI player) {
        b.button.addActionListener(e -> {
            // Reset previous piece's color if exists
            if (currentPiece != null) {
                // Reset to original board color instead of keeping cyan
                if (currentPiece.xPoint % 2 == currentPiece.yPoint % 2) {
                    currentPiece.button.setBackground(new Color(127, 166, 80));  // Green
                } else {
                    currentPiece.button.setBackground(Color.WHITE);
                }
                currentPiece = null;
            }
            
            LinkedList<Move> moves = b.piece.getPossibleMoves(player);
            setHighlight(moves);
            currentPiece = b;
            currentPiece.button.setBackground(Color.yellow);  // Change from cyan to yellow to match move highlights
        });
    }

    // Adds listener to a valid move location to accept a move
    private void addMoveListener(ChessVisualizer b, UI c) {
        b.button.addActionListener(e -> {
            System.out.println(currentPiece.getSymbol() + " moved");
            // remove the opponent's newPiece if occupying clicked square
            if (b.hasPiece) {
                b.removePiece();
            }
            b.addPiece(currentPiece.piece);

            if (b.piece.type == 5) {
                if (b.piece.color == 0) {
                    whiteKing.setNewValues(b.xPoint, b.yPoint);
                }
                else {
                    blackKing.setNewValues(b.xPoint, b.yPoint);
                }
            }

            ChessVisualizer newPiece = chessBoard[currentPiece.piece.x][currentPiece.piece.y];

            removeListeners(newPiece, c);
            // Reset to original board color
            if (newPiece.xPoint % 2 == newPiece.yPoint % 2) {
                newPiece.button.setBackground(new Color(127, 166, 80));  // Green
            } else {
                newPiece.button.setBackground(Color.WHITE);
            }

            // remove newPiece from old location and sets image in new space
            newPiece.removePiece();
            b.piece.updateLocation(b.xPoint, b.yPoint);
            
            // Clear all highlights
            setHighlight();
            
            currentPiece = null;

            // Check for checkmate first
            if (!checkCheckmate()) {
                // If not checkmate, check for check condition
                check = isChecked();
                changeTurn();
            }
        });
    }

    private void removeListeners(ChessVisualizer b, UI player) {
        // https://stackoverflow.com/questions/19469881/remove-all-event-listeners-of-specific-type
        // informed how to remove event listener
        for (ActionListener a : b.button.getActionListeners())
            b.button.removeActionListener(a);
    }

    // highlights a list of coordinates (moves available for a selected piece)
    private void setHighlight(LinkedList<Move> moves) {
        // Reset previous highlights
        if (highlightSpaces.size() > 0) {
            Move move;
            while (highlightSpaces.size() > 0) {
                move = highlightSpaces.pop();
                // Reset to original board color
                if (move.getX() % 2 == move.getY() % 2) {
                    chessBoard[move.getX()][move.getY()].button.setBackground(new Color(127, 166, 80));
                } else {
                    chessBoard[move.getX()][move.getY()].button.setBackground(Color.WHITE);
                }
                removeListeners(chessBoard[move.getX()][move.getY()], this);
            }
        }

        // Highlight new possible moves
        Move p1;
        for (int i = 0; i < moves.size(); i++) {
            p1 = moves.get(i);
            chessBoard[p1.getX()][p1.getY()].button.setBackground(Color.yellow);
            highlightSpaces.add(moves.get(i));
            addMoveListener(chessBoard[p1.getX()][p1.getY()], this);
        }

        // If clicked piece exists, highlight it yellow to match possible moves
        if (currentPiece != null) {
            currentPiece.button.setBackground(Color.yellow);
            currentPiece = null;
        }
    }

    // clears highlighted fields
    private void setHighlight() {
        if (highlightSpaces.size() > 0) {
            Move move;
            while (highlightSpaces.size() > 0) {
                move = highlightSpaces.pop();
                // Use the same color logic as setcolor method
                if (move.getX() % 2 == move.getY() % 2) {
                    chessBoard[move.getX()][move.getY()].button.setBackground(new Color(127, 166, 80));  // Green
                } else {
                    chessBoard[move.getX()][move.getY()].button.setBackground(Color.WHITE);
                }
                removeListeners(chessBoard[move.getX()][move.getY()], this);
            }
        }
    }

    // Initializes the chessboard to starting position
    private void initBoardState() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                // sets coordinates for white pieces except pawns
                if (i == 0) {
                    if (j == 0 || j == 7) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Rook(1, i, j, 2), this);
                        setcolor(i, j, "B_R.png");
                    }
                    else if (j == 1 || j == 6) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Knight(1, i, j, 3),
                                                               this);
                        setcolor(i, j, "B_N.png");
                    }
                    else if (j == 2 || j == 5) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Bishop(1, i, j, 4),
                                                               this);
                        setcolor(i, j, "B_B.png");
                    }
                    else if (j == 3) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new King(1, i, j, 5), this);
                        setcolor(i, j, "B_K.png");
                    }
                    else {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Queen(1, i, j, 6), this);
                        setcolor(i, j, "B_Q.png");
                    }
                }

                // sets black pawns
                else if (i == 1) {
                    chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                           new Pawn(1, i, j, 1), this);
                    setcolor(i, j, "B_P.png");
                }

                // sets white pawns
                else if (i == 6) {
                    chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                           new Pawn(0, i, j, 1), this);
                    setcolor(i, j, "W_P.png");
                }

                // sets black pieces except pawns
                else if (i == 7) {
                    if (j == 0 || j == 7) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Rook(0, i, j, 2), this);
                        setcolor(i, j, "W_R.png");
                    }
                    else if (j == 1 || j == 6) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Knight(0, i, j, 3),
                                                               this);
                        setcolor(i, j, "W_N.png");
                    }
                    else if (j == 2 || j == 5) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Bishop(0, i, j, 4),
                                                               this);
                        setcolor(i, j, "W_B.png");
                    }
                    else if (j == 3) {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new King(0, i, j, 5), this);
                        setcolor(i, j, "W_K.png");
                    }
                    else {
                        chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel,
                                                               new Queen(0, i, j, 6), this);
                        setcolor(i, j, "W_Q.png");
                    }
                }
                // every other square is empty
                else {
                    chessBoard[i][j] = new ChessVisualizer(i, j, new JButton(""), panel);
                    setcolor(i, j, "");
                }
            }
        }
    }

    // main method calls User Interface class, creating a window with the
    // chess pieces
    public static void main(String[] args) {
        new UI();
    }

    // sets piece image as well as color of chessboard spaces
    public void setcolor(int i, int j, String icon) {
        if (!icon.isEmpty()) {
            // https://stackoverflow.com/questions/24046924/what-does-getclass-getresource-do-when-creating-imageicon
            // informed of how to access image files
            chessBoard[i][j].button.setIcon(
                    new ImageIcon(getClass().getResource("/icons/" + icon)));
        }

        // Paints every other square in chessboard green and white
        if (i % 2 == j % 2) {
            chessBoard[i][j].button.setBackground(new Color(127, 166, 80));
        }
        else {
            chessBoard[i][j].button.setBackground(Color.WHITE);
        }
        chessBoard[i][j].button.setBorderPainted(false);
        chessBoard[i][j].button.setOpaque(true);
    }

    // resets color of space a piece has left
    public void resetcolor() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setcolor(i, j, "");

            }
        }
    }

    // Add this new method to handle AI moves
    private void makeAIMove() {
        Move[] bestMove = ai.getBestMove();
        if (bestMove != null) {
            int fromX = bestMove[0].getX();
            int fromY = bestMove[0].getY();
            int toX = bestMove[1].getX();
            int toY = bestMove[1].getY();
            
            Piece movingPiece = chessBoard[fromX][fromY].piece;
            makeMove(fromX, fromY, toX, toY, movingPiece);
            
            // Check for game ending conditions
            if (!checkCheckmate()) {
                changeTurn();
            }
        }
    }
}



