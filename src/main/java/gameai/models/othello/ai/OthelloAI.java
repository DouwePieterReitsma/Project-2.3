package gameai.models.othello.ai;

import java.util.List;
import java.util.Random;

import gameai.controllers.ConnectionListenerThread;
import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;
import gameai.models.othello.OthelloBoard;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPlayer;
import javafx.geometry.Pos;

/**
 * This class serves as a base for other OthelloAIs.
 */
public abstract class OthelloAI extends OthelloPlayer {
	private Position move;

    /**
     * @param board board to play on.
     * @param playerColor
     */
    public OthelloAI(OthelloBoard board, OthelloColor playerColor) {
        super(board, playerColor);
    }

    public abstract Position calculateMove();

    public void play() {
        if(board.getCurrentTurnColor() == playerColor&& !board.isGameOver()) {
        	move = calculateMove();


            if (move != null) {
                doMove(move);
            }

            else {
            	board.advanceTurn();
            }
        }
    }

    public Position getMove() {
    	return move;
    }
}