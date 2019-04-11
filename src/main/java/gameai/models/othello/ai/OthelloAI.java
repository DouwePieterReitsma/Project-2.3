package gameai.models.othello.ai;

import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPlayer;
import javafx.geometry.Pos;

public abstract class OthelloAI extends OthelloPlayer {
	private Position move;

    public OthelloAI(OthelloBoard board, OthelloColor playerColor) {
        super(board, playerColor);
    }

    public abstract Position calculateMove();

    public void play() {
        if(board.getCurrentTurnColor() == playerColor) {
            move = calculateMove();

            System.out.printf("\n\nTurn %d, Player: %s\n\n", board.getTurn() + 1, board.getCurrentTurnColor() + " MOVE: " + move);

            if (move != null) {
                doMove(move);
            } else {
                // no move available
                board.advanceTurn();
            }

            //System.out.println(board.toString());
        }
    }

    public Position GetMove() {
    	return move;
    }
}