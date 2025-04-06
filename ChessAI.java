import java.util.LinkedList;

public class ChessAI {
    private static final int DEPTH = 3; // How many moves ahead to look
    private UI gameUI;
    
    // Piece values for evaluation
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    public ChessAI(UI ui) {
        this.gameUI = ui;
    }

    // Main method to get the best move for black
    public Move[] getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        Move[] bestMove = null;

        // Get all possible moves for black pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameUI.chessBoard[i][j].hasPiece && gameUI.chessBoard[i][j].piece.color == 1) {
                    LinkedList<Move> possibleMoves = gameUI.chessBoard[i][j].piece.getPossibleMoves(gameUI);
                    
                    for (Move move : possibleMoves) {
                        // Try the move
                        Piece originalPiece = null;
                        if (gameUI.chessBoard[move.getX()][move.getY()].hasPiece) {
                            originalPiece = gameUI.chessBoard[move.getX()][move.getY()].piece;
                        }
                        
                        // Make temporary move
                        gameUI.makeMove(i, j, move.getX(), move.getY(), gameUI.chessBoard[i][j].piece);
                        
                        // Evaluate position after move
                        int score = minimax(DEPTH - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        
                        // Undo move
                        gameUI.makeMove(move.getX(), move.getY(), i, j, gameUI.chessBoard[move.getX()][move.getY()].piece);
                        if (originalPiece != null) {
                            gameUI.chessBoard[move.getX()][move.getY()].addPiece(originalPiece);
                        }
                        
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = new Move[]{new Move(i, j), move};
                        }
                    }
                }
            }
        }
        
        return bestMove;
    }

    // Minimax algorithm with alpha-beta pruning
    private int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        if (depth == 0) {
            return evaluatePosition();
        }

        if (isMaximizing) {
            int maxScore = Integer.MIN_VALUE;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (gameUI.chessBoard[i][j].hasPiece && gameUI.chessBoard[i][j].piece.color == 1) {
                        LinkedList<Move> moves = gameUI.chessBoard[i][j].piece.getPossibleMoves(gameUI);
                        for (Move move : moves) {
                            // Make move
                            Piece originalPiece = null;
                            if (gameUI.chessBoard[move.getX()][move.getY()].hasPiece) {
                                originalPiece = gameUI.chessBoard[move.getX()][move.getY()].piece;
                            }
                            gameUI.makeMove(i, j, move.getX(), move.getY(), gameUI.chessBoard[i][j].piece);
                            
                            int score = minimax(depth - 1, false, alpha, beta);
                            
                            // Undo move
                            gameUI.makeMove(move.getX(), move.getY(), i, j, gameUI.chessBoard[move.getX()][move.getY()].piece);
                            if (originalPiece != null) {
                                gameUI.chessBoard[move.getX()][move.getY()].addPiece(originalPiece);
                            }
                            
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
                    if (gameUI.chessBoard[i][j].hasPiece && gameUI.chessBoard[i][j].piece.color == 0) {
                        LinkedList<Move> moves = gameUI.chessBoard[i][j].piece.getPossibleMoves(gameUI);
                        for (Move move : moves) {
                            // Make move
                            Piece originalPiece = null;
                            if (gameUI.chessBoard[move.getX()][move.getY()].hasPiece) {
                                originalPiece = gameUI.chessBoard[move.getX()][move.getY()].piece;
                            }
                            gameUI.makeMove(i, j, move.getX(), move.getY(), gameUI.chessBoard[i][j].piece);
                            
                            int score = minimax(depth - 1, true, alpha, beta);
                            
                            // Undo move
                            gameUI.makeMove(move.getX(), move.getY(), i, j, gameUI.chessBoard[move.getX()][move.getY()].piece);
                            if (originalPiece != null) {
                                gameUI.chessBoard[move.getX()][move.getY()].addPiece(originalPiece);
                            }
                            
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

    // Evaluate the current board position
    private int evaluatePosition() {
        int score = 0;
        
        // Material counting
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameUI.chessBoard[i][j].hasPiece) {
                    int pieceValue = getPieceValue(gameUI.chessBoard[i][j].piece);
                    if (gameUI.chessBoard[i][j].piece.color == 1) { // Black
                        score += pieceValue;
                    } else { // White
                        score -= pieceValue;
                    }
                }
            }
        }
        
        return score;
    }

    // Get the value of a piece
    private int getPieceValue(Piece piece) {
        switch (piece.type) {
            case 1: return PAWN_VALUE;
            case 2: return ROOK_VALUE;
            case 3: return KNIGHT_VALUE;
            case 4: return BISHOP_VALUE;
            case 5: return KING_VALUE;
            case 6: return QUEEN_VALUE;
            default: return 0;
        }
    }
}
