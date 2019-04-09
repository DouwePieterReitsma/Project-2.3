package gameai.models.othello.ai;

import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPlayer;
import javafx.geometry.Pos;

public abstract class OthelloAI extends OthelloPlayer {
    public OthelloAI(OthelloBoard board, OthelloColor playerColor) {
        super(board, playerColor);
    }

    public abstract Position calculateMove();

    public void play() {
        if(board.getCurrentTurnColor() == playerColor) {
            Position move = calculateMove();

            System.out.printf("\n\nTurn %d, Player: %s\n\n", board.getTurn() + 1, board.getCurrentTurnColor());

            if (move != null) {
                doMove(move);
            } else {
                // no move available
                board.advanceTurn();
            }

            //System.out.println(board.toString());
        }
    }
}