package gameai.models;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard extends Board {
    public TicTacToeBoard() {
        super(3,3);
    }

    @Override
    public List<Position> getLegalMoves() {
        return new ArrayList<>();
    }
}