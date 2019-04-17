package gameai.models.othello;

import gameai.models.IllegalMoveException;
import gameai.models.Position;
import javafx.application.Platform;

/**
 * Class for representing an Othello player.
 */
public abstract class OthelloPlayer {
    protected OthelloBoard board;
    public OthelloColor playerColor;

    /**
     * @param board board to make moves on.
     * @param playerColor the color to make moves for.
     */
    public OthelloPlayer(OthelloBoard board, OthelloColor playerColor) {
        this.board = board;
        this.playerColor = playerColor;
    }

    /**
     * @param position position to play.
     */
    public void doMove(Position position) {
        if (board.getCurrentTurnColor() == playerColor) {
            try {
                board.setPieceAtPosition(new OthelloPiece(playerColor), position);
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            }
        }
    }

    public OthelloBoard getBoard() {
        return board;
    }

    public void setBoard(OthelloBoard board) {
        this.board = board;
    }
}
