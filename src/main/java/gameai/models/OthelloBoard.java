package gameai.models;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoard extends Board {
    public OthelloBoard() {
        super(8, 8);
    }

    @Override
    public List<Position> getLegalMoves() {
        List<Position> legalMoves = new ArrayList<>();

        Position[][] positions = getPositions();

        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                Position position = positions[i][j];


            }
        }

        return legalMoves;
    }

    private Position tryNeighbor(int x, int y) {
        Position[][] positions = getPositions();
        Position position = null;

        try {
            position = positions[y][x];

            if (position.getPiece() != null){
                return position;
            }
        } catch (IndexOutOfBoundsException | NullPointerException e) {

        }

        return position;
    }

    private List<Position> getNeighbors(Position position) {
        List<Position> neighbors = new ArrayList<>();
        Position neighbor = null;

        neighbor = tryNeighbor(position.getX() - 1, position.getY() + 1);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() - 1, position.getY());
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() - 1, position.getY() - 1);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX(), position.getY() + 1);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX(), position.getY() - 1);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() + 1, position.getY() + 1);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() + 1, position.getY());
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() + 1, position.getY() - 1);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        return neighbors;
    }
}