package gameai.models.othello.ai;

import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPlayer;
import javafx.geometry.Pos;

public abstract class OthelloAI extends OthelloPlayer {
    public OthelloAI(OthelloColor playerColor) {
        super(playerColor);
    }

    public abstract Position calculateMove();

    public void play() {
        board.setCurrentTurnColor(playerColor);

        Position move = calculateMove();

        if(move != null) {
            doMove(move);
        }
    }
}