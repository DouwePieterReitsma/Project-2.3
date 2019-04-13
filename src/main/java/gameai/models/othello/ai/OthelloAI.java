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

public abstract class OthelloAI extends OthelloPlayer {
	private Position move;

    public OthelloAI(OthelloBoard board, OthelloColor playerColor) {
        super(board, playerColor);
    }

    public abstract Position calculateMove();

    public void play() {
        if(board.getCurrentTurnColor() == playerColor&& !board.isGameOver()) {
            move = calculateMove();
            System.out.println(board);

            System.out.printf("\n\nTurn %d, Player: %s\n\n", board.getTurn() + 1, board.getCurrentTurnColor() + " MOVE: " + move);

            if (move != null) {
                doMove(move);
            } 
//            else if(this.getBoard().getLegalMoves(this.playerColor).size() > 0) {
//            	move = this.getBoard().getLegalMoves(this.playerColor).get(0);
//            	doMove(move);
//            }
            else {
            	board.advanceTurn();
            }

            //System.out.println(board.toString());
        }
    }

    private Position getRandomMove(List<Position> legalMoves) {
        if (legalMoves.isEmpty()) return null;

        return legalMoves.get(new Random().nextInt(legalMoves.size()));
    }

    public Position GetMove() {
    	return move;
    }
}