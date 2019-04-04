package gameai.models.othello.ai;

import gameai.models.Position;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.ai.OthelloAI;

import java.util.List;

public class MiniMaxOthelloAI extends OthelloAI {
    private OthelloColor opponentColor;

    public MiniMaxOthelloAI(OthelloColor playerColor) {
        super(playerColor);

        opponentColor = playerColor == OthelloColor.WHITE ? OthelloColor.BLACK : OthelloColor.WHITE;
    }

    @Override
    public Position calculateMove() {
        List<Position> legalMoves = board.getLegalMoves(playerColor);

        return null;
    }
}
