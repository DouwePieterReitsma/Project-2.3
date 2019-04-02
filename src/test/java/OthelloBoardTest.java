import gameai.models.Piece;
import gameai.models.othello.OthelloBoard;
import gameai.models.Position;
import gameai.models.othello.OthelloColor;
import gameai.models.othello.OthelloPiece;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OthelloBoardTest {
    @Test
    public void getNeighbors(){
        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE);

        Position position = new Position(3, 3);
        OthelloPiece neighbor1 = new OthelloPiece(OthelloColor.BLACK);
        OthelloPiece neighbor2 = new OthelloPiece(OthelloColor.WHITE);

        board.getPositions()[3][4].setPiece(neighbor1);
        board.getPositions()[3][2].setPiece(neighbor2);

        List<Position> neighbors = board.getOpponentNeighbors(position);

        assertEquals(1, neighbors.size());
    }

    @Test
    public void tryNeighbor() {
        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE);

        OthelloPiece neighbor1 = new OthelloPiece(OthelloColor.BLACK);

        board.getPositions()[3][4].setPiece(neighbor1);


        assertTrue(board.tryNeighbor(4, 3) != null);
    }

    @Test
    public void hasPieceInLine() {
        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE);

        OthelloPiece black = new OthelloPiece(OthelloColor.BLACK);
        OthelloPiece white = new OthelloPiece(OthelloColor.WHITE);

        board.getPositions()[0][0].setPiece(white);
        board.getPositions()[0][1].setPiece(black);
        board.getPositions()[0][2].setPiece(black);
        board.getPositions()[0][3].setPiece(black);
        board.getPositions()[0][4].setPiece(white);

        assertTrue(board.hasPieceInLine(new Position(0,0), 1, 0));
    }

    @Test
    public void legalMoves() {
        OthelloBoard board = new OthelloBoard(OthelloColor.WHITE);

        OthelloPiece black = new OthelloPiece(OthelloColor.BLACK);
        OthelloPiece white = new OthelloPiece(OthelloColor.WHITE);

        board.getPositions()[0][0].setPiece(white);
        board.getPositions()[0][1].setPiece(black);
        board.getPositions()[0][2].setPiece(null);
        board.getPositions()[1][0].setPiece(black);

        List<Position> moves = board.getLegalMoves();

        for(Position move : moves) {
            System.out.println(move);
        }

        assertEquals(2, moves.size());
//        board.getPositions()[0][4].setPiece(white);
    }
}
