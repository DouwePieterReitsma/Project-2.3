package gameai.models.othello;

import gameai.models.Position;

import java.util.ArrayList;
import java.util.List;

public class OthelloMove {
    private Position position;
    private List<Position> piecesToSwap;

    public OthelloMove(Position position) {
        this.position = position;
        piecesToSwap = new ArrayList<>();
    }

    public List<Position> getPiecesToSwap() {
        return piecesToSwap;
    }

    public void setPiecesToSwap(List<Position> piecesToSwap) {
        this.piecesToSwap = piecesToSwap;
    }
}
