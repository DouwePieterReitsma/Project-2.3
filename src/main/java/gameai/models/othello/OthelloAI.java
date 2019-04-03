package gameai.models.othello;

import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;
import javafx.geometry.Pos;

public abstract class OthelloAI extends OthelloPlayer {
    public OthelloAI(OthelloColor playerColor) {
        super(playerColor);
    }

    public abstract Position calculateMove();

    public void play() {
        Position move = calculateMove();

        if(move != null) {
            doMove(move);
        } else {
            // no moves, skip turn
            board.setCurrentTurnColor(playerColor == OthelloColor.WHITE ? OthelloColor.BLACK : OthelloColor.WHITE);
        }

        System.out.println(board.toString());
    }
}