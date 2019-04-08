package gameai.models.othello.ai;

import gameai.models.IllegalMoveException;
import gameai.models.Position;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloMove;
import gameai.models.othello.OthelloPiece;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AlphaBetaOthelloAI extends OthelloAI {
    private OthelloColor opponentColor;
    private int depth;

    public AlphaBetaOthelloAI(OthelloBoard board, OthelloColor playerColor) {
        super(board, playerColor);

        opponentColor = playerColor == OthelloColor.WHITE ? OthelloColor.BLACK : OthelloColor.WHITE;

        this.depth = 5;
    }

    public AlphaBetaOthelloAI(OthelloBoard board, OthelloColor playerColor, int depth) {
        this(board,playerColor);

        this.depth = depth;
    }

    @Override
    public Position calculateMove() {
        return alphabetaDecision(this.depth);
    }

    private int alphabeta(OthelloBoard board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        List<OthelloMove> legalMoves = null;
        int value;

        if (depth == 0 || board.isGameOver()) {
            return heuristic(board);
        }

        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;

            legalMoves = board.getLegalMovesWrapper(playerColor);

            for(OthelloMove move : legalMoves) {
                OthelloBoard temp = evaluate(board, move);

                if(temp == null) continue;

                value = max(value, alphabeta(temp, depth - 1, alpha, beta, false));
                alpha = max(alpha, value);


                if (alpha >= beta)
                    break;
            }

            return value;
        }
        else {
            value = Integer.MAX_VALUE;

            legalMoves = board.getLegalMovesWrapper(opponentColor);

            for(OthelloMove move : legalMoves) {
                OthelloBoard temp = evaluate(board, move);

                if(temp == null) continue;

                value = min(value, alphabeta(temp, depth - 1, alpha, beta, true));
                beta = min(beta, value);

                if (alpha >= beta)
                    break;
            }

            return value;
        }
    }

    private Position alphabetaDecision(int depth) {
        List<OthelloMove> legalMoves = board.getLegalMovesWrapper(playerColor);
        OthelloMove result = null;
        int bestScore = 0;

        for(OthelloMove move : legalMoves) {
            int score = alphabeta(board, depth, Integer.MIN_VALUE, Integer.MAX_VALUE,true);

            if(score > bestScore) {
                bestScore = score;
                result = move;
            }
        }

        return result != null ? result.getPosition() : null;
    }

    private int heuristic(OthelloBoard board) {
        return board.getPlayerScore(playerColor) - board.getPlayerScore(opponentColor);
    }

    private OthelloBoard evaluate(OthelloBoard original, OthelloMove move) {
        OthelloBoard board = new OthelloBoard(original);

        try {
            board.setPieceAtPosition(new OthelloPiece(board.getCurrentTurnColor()), move.getPosition());
        } catch (IllegalMoveException e) {
            e.printStackTrace();

            return null;
        }

        return board;
    }
}
