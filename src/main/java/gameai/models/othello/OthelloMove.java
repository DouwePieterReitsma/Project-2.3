package gameai.models.othello;

import gameai.models.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an Othello move.
 */
public class OthelloMove {
    private Position position;
    private List<Position> piecesToSwap;

    /**
     * @param position move position
     */
    public OthelloMove(Position position) {
        this.position = position;
        piecesToSwap = new ArrayList<>();
    }

    /**
     * copy constructor
     * @param move move to copy
     */
    public OthelloMove(OthelloMove move) {
        this.position = new Position(move.getPosition());
        this.piecesToSwap = new ArrayList<>();

        for(Position position : move.getPiecesToSwap()) {
            piecesToSwap.add(new Position(position));
        }
    }

    public List<Position> getPiecesToSwap() {
        return piecesToSwap;
    }

    public void setPiecesToSwap(List<Position> piecesToSwap) {
        this.piecesToSwap = piecesToSwap;
    }

    // number of opponent pieces this move will swap
    public int getScore() {
        return piecesToSwap != null ? piecesToSwap.size() : 0;
    }

    public Position getPosition() {
        return position;
    }
}
