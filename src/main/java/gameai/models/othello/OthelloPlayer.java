package gameai.models.othello;

import gameai.models.IllegalMoveException;
import gameai.models.Position;
import javafx.application.Platform;

public abstract class OthelloPlayer {
    protected OthelloBoard board;
    protected OthelloColor playerColor;

    public OthelloPlayer(OthelloBoard board, OthelloColor playerColor) {
        this.board = board;
        this.playerColor = playerColor;
    }

    public void doMove(Position position) {
        if (board.getCurrentTurnColor() == playerColor) {
            try {
                board.setPieceAtPosition(new OthelloPiece(playerColor), position);
                /*if(playerColor == playerColor.WHITE) {
                	Platform.runLater(() -> board.GetOthelloView().SetMove(position.getX(), position.getY(), true));
                }
                else {
                	Platform.runLater(() -> board.GetOthelloView().SetMove(position.getX(), position.getY(), false));
                }*/

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
