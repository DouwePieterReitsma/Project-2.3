import gameai.models.Board;
import gameai.models.OthelloBoard;
import gameai.models.OthelloPiece;
import gameai.models.Position;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OthelloBoardTest {
    private final OthelloBoard board = new OthelloBoard();

    @Test
    public void getNeighbors(){
        Position position = new Position(3, 3);

        List<Position> neighbors = board.getNeighbors(position);

        assertEquals(8, neighbors.size());
    }
}
