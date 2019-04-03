package gameai.models.othello;

import gameai.models.IllegalMoveException;
import gameai.models.Position;

public abstract class OthelloPlayer {
    protected OthelloBoard board;
    protected OthelloColor playerColor;

    public OthelloPlayer(OthelloColor playerColor) {
        this.playerColor = playerColor;
    }

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
