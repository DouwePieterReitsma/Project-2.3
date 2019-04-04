package gameai.models.othello.ai;

import gameai.models.Position;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.ai.OthelloAI;

import java.util.List;
import java.util.Random;

public class RandomOthelloAI extends OthelloAI {
    public RandomOthelloAI(OthelloColor playerColor) {
        super(playerColor);
    }

    @Override
    public Position calculateMove() {
        List<Position> legalMoves = board.getLegalMoves(playerColor);

        return getRandomMove(legalMoves);
    }

    private Position getRandomMove(List<Position> legalMoves) {
        if (legalMoves.size() == 0) return null;

        return legalMoves.get(new Random().nextInt(legalMoves.size()));
    }
}
