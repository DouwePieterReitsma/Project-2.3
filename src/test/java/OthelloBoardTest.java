import gameai.models.Piece;
import gameai.models.othello.OthelloBoard;
import gameai.models.Position;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;
import gameai.models.othello.RandomOthelloAI;
import javafx.geometry.Pos;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OthelloBoardTest {
    @Test
    public void legalMoves() {
        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE, null, null);

        OthelloPiece black = new OthelloPiece(OthelloColor.BLACK);
        OthelloPiece white = new OthelloPiece(OthelloColor.WHITE);

        board.getPositions()[3][3].setPiece(new OthelloPiece(OthelloColor.BLACK));
        board.getPositions()[4][3].setPiece(new OthelloPiece(OthelloColor.WHITE));
        board.getPositions()[3][4].setPiece(new OthelloPiece(OthelloColor.WHITE));
        board.getPositions()[4][4].setPiece(new OthelloPiece(OthelloColor.BLACK));

//        List<Position> moves = board.getLegalMoves(board.getCurrentTurnColor());
        List<Position> moves = board.getLegalMoves(OthelloColor.BLACK);

        assertEquals(4, moves.size());
    }

    @Test
    public void randomVsRandom() {
//        RandomOthelloAI white = new RandomOthelloAI(OthelloColor.WHITE);
//        RandomOthelloAI black = new RandomOthelloAI(OthelloColor.BLACK);
//
//        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE, white, black);
//
//        //init board
//        board.getPositions()[3][3].setPiece(new OthelloPiece(OthelloColor.BLACK));
//        board.getPositions()[4][3].setPiece(new OthelloPiece(OthelloColor.WHITE));
//        board.getPositions()[3][4].setPiece(new OthelloPiece(OthelloColor.WHITE));
//        board.getPositions()[4][4].setPiece(new OthelloPiece(OthelloColor.BLACK));
//
//        while(!board.isGameOver()) {
//            white.play();
//            black.play();
//        }
    }
}
