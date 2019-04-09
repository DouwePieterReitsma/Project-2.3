package gameai.models.othello.ai;

import gameai.models.IllegalMoveException;
import gameai.models.Position;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AlphaBetaOthelloAI extends OthelloAI {
    private OthelloColor opponentColor;
    private int depth;

    public AlphaBetaOthelloAI(OthelloBoard board, OthelloColor playerColor, int depth) {
        super(board, playerColor);

        this.depth = depth;
    }

    @Override
    public Position calculateMove() {
        return alphabetaDecision(this.depth);
    }

    private int alphabeta(OthelloBoard board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        List<Position> legalMoves = null;
        int value;

        if (depth == 0 || board.isGameOver()) {
            return heuristic(board);
        }

        if (maximizingPlayer) {
            value = Integer.MIN_VALUE;

            legalMoves = board.getLegalMoves(board.getCurrentTurnColor());

            for (Position move : legalMoves) {
                OthelloBoard temp = evaluate(board, move);

                if (temp == null) continue;

                value = max(value, alphabeta(temp, depth - 1, alpha, beta, false));
                alpha = max(alpha, value);


                if (alpha >= beta)
                    break;
            }

            return value;
        } else {
            value = Integer.MAX_VALUE;

            legalMoves = board.getLegalMoves(board.getCurrentTurnColor());

            for (Position move : legalMoves) {
                OthelloBoard temp = evaluate(board, move);

                if (temp == null) continue;

                value = min(value, alphabeta(temp, depth - 1, alpha, beta, true));
                beta = min(beta, value);

                if (alpha >= beta)
                    break;
            }

            return value;
        }
    }

    private Position alphabetaDecision(int depth) {
        List<Position> legalMoves = this.board.getLegalMoves(playerColor);

        int bestScore = 0;
        Position result = null;

        for (Position move : legalMoves) {
            OthelloBoard temp = evaluate(this.board, move);

            int score = alphabeta(temp, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

            if (score > bestScore) {
                bestScore = score;
                result = move;
            }
        }

        return result;
    }

    //todo: add weights
    private int heuristic(OthelloBoard board) {
        int score = 0;

        // coin parity
        score += getCoinParity(board);

        // utility value
        score += getUtilityValueForPlayer(board, playerColor) - getUtilityValueForPlayer(board, opponentColor);

        return score;
    }

    private int getCoinParity(OthelloBoard board) {
        int maximizingPlayerScore = board.getPlayerScore(playerColor);
        int minimizingPlayerScore = board.getPlayerScore(opponentColor);

        if(maximizingPlayerScore + minimizingPlayerScore == 0) return 0;

        return 100 * (maximizingPlayerScore - minimizingPlayerScore) / (maximizingPlayerScore + minimizingPlayerScore);
    }

    private int getUtilityValueForPlayer(OthelloBoard board, OthelloColor color) {
        Position[][] positions = board.getPositions();
        int result = 0;

        int[][] weights = {
                {4, -3, 2, 2, 2, 2, -3, 4},
                {-3, -4, -1, -1, -1, -1, -4, -3},
                {2, -1, 1, 0, 0, 1, -1, 2},
                {2, -1, 0, 1, 1, 0, -1, 2},
                {2, -1, 0, 1, 1, 0, -1, 2},
                {2, -1, 1, 0, 0, 1, -1, 2},
                {-3, -4, -1, -1, -1, -1, -4, -3},
                {4, -3, 2, 2, 2, 2, -3, 4}
        };

        for(int row = 0; row < positions.length; row++) {
            for(int column = 0; column < positions[row].length; column++) {
                OthelloPiece piece = (OthelloPiece)positions[column][row].getPiece();

                if(piece == null) continue;

                if(piece.getColor() == color) {
                    result += weights[row][column];
                }
            }
        }

        return result;
    }

    private OthelloBoard evaluate(OthelloBoard original, Position position) {
        OthelloBoard board = new OthelloBoard(original);
        position = board.getPositions()[position.getY()][position.getX()];

        try {
            board.setPieceAtPosition(new OthelloPiece(board.getCurrentTurnColor()), position);
        } catch (IllegalMoveException e) {
            e.printStackTrace();

            return null;
        }

        return board;
    }
}
