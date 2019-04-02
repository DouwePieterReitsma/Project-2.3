package gameai.models.othello;

import gameai.models.Board;
import gameai.models.Position;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoard extends Board {
    public OthelloBoard(OthelloColor startingColor) {
        super(8, 8);

        this.currentTurnColor = startingColor;
    }

    private OthelloColor currentTurnColor;
    private OthelloColor opponentColor;

    @Override
    public List<Position> getLegalMoves() {
        List<Position> legalMoves = new ArrayList<>();

        Position[][] positions = getPositions();

        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                Position position = positions[i][j];

                if (isLegalMove(position, currentTurnColor))
                    legalMoves.add(position);
            }
        }

        return legalMoves;
    }

    public boolean isLegalMove(Position position, OthelloColor color) {
        OthelloPiece piece = (OthelloPiece)position.getPiece();

        // check if a piece is already at the given position
        if (piece != null) return false;

        // get the adjacent positions for the given position
        List<Position> neighbors = getOpponentNeighbors(position);

        // no adjacent opponent pieces
        if (neighbors.size() == 0)
            return false;

        for(Position neighbor : neighbors) {
            int horizontalDirection = neighbor.getX() - position.getX();
            int verticalDirection = neighbor.getY() - position.getY();

            if (hasPieceInLine(position, horizontalDirection, verticalDirection)) return true;
        }

        return false;
    }

    public boolean hasPieceInLine(Position startingPosition, int horizontalDirection, int verticalDirection) {
        Position currentPosition = null;
        OthelloPiece piece = null;
        int x = startingPosition.getX() + horizontalDirection;
        int y = startingPosition.getY() + verticalDirection;

        currentPosition = positions[y][x];
        piece = (OthelloPiece)currentPosition.getPiece();

        // the first neighbor of the starting position must be of a different color
        if (piece.getColor() == currentTurnColor) return false;

        while(!(x < 0 || x > 7 || y < 0 || y > 7)) {
            currentPosition = positions[y][x];

            piece = (OthelloPiece)currentPosition.getPiece();

            // empty position
            if(piece == null) return false;

            // found piece
            if (piece.getColor() == currentTurnColor) return true;

            x += horizontalDirection;
            y += verticalDirection;
        }

        return false;
    }

    public Position tryNeighbor(int x, int y) {

        // bounds check
        if(x < 0 || x > 7 || y < 0 || y > 7) return null;

        Position position = positions[y][x];

        OthelloPiece piece = (OthelloPiece)position.getPiece();

        // no piece at position
        if (piece == null)
            return null;

        // we don't need to include the current color's own pieces
        if (piece.getColor() == currentTurnColor)
            return null;

        return positions[y][x];
    }

    public List<Position> getOpponentNeighbors(Position position) {
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

    public OthelloColor getCurrentTurnColor() {
        return currentTurnColor;
    }

    public void setCurrentTurnColor(OthelloColor currentTurnColor) {
        this.currentTurnColor = currentTurnColor;
        this.opponentColor = currentTurnColor == OthelloColor.WHITE ? OthelloColor.BLACK : OthelloColor.WHITE;
    }
}