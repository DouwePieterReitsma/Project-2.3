package gameai.models.othello.ai;

import gameai.models.Position;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.ai.OthelloAI;

import java.util.List;
import java.util.Random;

/**
 * This class generates random moves for the supplied board.
 */
public class RandomOthelloAI extends OthelloAI {
    public RandomOthelloAI(OthelloBoard board, OthelloColor playerColor) {
        super(board, playerColor);
    }

    @Override
    public Position calculateMove() {
        List<Position> legalMoves = board.getLegalMoves(playerColor);

        return getRandomMove(legalMoves);
    }

    /**
     * @param legalMoves list of legal moves for the player
     * @return a random position.
     */
    private Position getRandomMove(List<Position> legalMoves) {
        if (legalMoves.isEmpty()) return null;

        return legalMoves.get(new Random().nextInt(legalMoves.size()));
    }
}