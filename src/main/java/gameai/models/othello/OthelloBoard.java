package gameai.models.othello;

import gameai.models.Board;
import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoard extends Board {
    public OthelloBoard(OthelloColor startingColor, OthelloPlayer whitePlayer, OthelloPlayer blackPlayer) {
        super(8, 8);

        this.currentTurnColor = startingColor;

        if (whitePlayer != null && blackPlayer != null) {
            whitePlayer.setBoard(this);
            blackPlayer.setBoard(this);
        }

        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    private OthelloColor currentTurnColor;
    private OthelloPlayer whitePlayer;
    private OthelloPlayer blackPlayer;

    @Override
    public List<Position> getLegalMoves() {
        return getLegalMoves(currentTurnColor);
    }

    public List<Position> getLegalMoves(OthelloColor color) {
        List<Position> legalMoves = new ArrayList<>();

        for(int i = 0; i < positions.length; i++){
            for(int j = 0; j < positions[i].length; j++){
                Position position = positions[i][j];

                if (isLegalMove(position, color))
                    legalMoves.add(position);
            }
        }

        return legalMoves;
    }

    private boolean isLegalMove(Position position, OthelloColor color) {
        OthelloPiece piece = (OthelloPiece)position.getPiece();

        // check if a piece is already at the given position
        if (piece != null) return false;

        // get the adjacent positions for the given position
        List<Position> neighbors = getOpponentNeighbors(position, color);

        // no adjacent opponent pieces
        if (neighbors.size() == 0)
            return false;

        for(Position neighbor : neighbors) {
            int horizontalDirection = neighbor.getX() - position.getX();
            int verticalDirection = neighbor.getY() - position.getY();

            if (hasPieceInLine(position, color, horizontalDirection, verticalDirection)) return true;
        }

        return false;
    }

    private boolean hasPieceInLine(Position startingPosition, OthelloColor color, int horizontalDirection, int verticalDirection) {
        return findPieceInLine(startingPosition, color, horizontalDirection, verticalDirection) != null;
    }

    private OthelloMove findPieceInLine(Position startingPosition, OthelloColor color, int horizontalDirection, int verticalDirection) {
        OthelloMove move = new OthelloMove(startingPosition);
        Position currentPosition = null;
        OthelloPiece piece = null;

        int x = startingPosition.getX() + horizontalDirection;
        int y = startingPosition.getY() + verticalDirection;

        currentPosition = positions[y][x];
        piece = (OthelloPiece)currentPosition.getPiece();

        // the first neighbor of the starting position must be of a different color
        if (piece.getColor() == color) return null;

        while(!(x < 0 || x > 7 || y < 0 || y > 7)) {
            currentPosition = positions[y][x];

            piece = (OthelloPiece)currentPosition.getPiece();

            // empty position
            if(piece == null) return null;

            // found piece
            if (piece.getColor() == color) return move;

            x += horizontalDirection;
            y += verticalDirection;

            move.getPiecesToSwap().add(currentPosition);
        }

        return null;
    }

    private void turnPieces(Position position, OthelloColor color) {
        // get the adjacent positions for the given position
        List<Position> neighbors = getOpponentNeighbors(position, color);

        // no adjacent opponent pieces
        if (neighbors.size() == 0) return;

        for(Position neighbor : neighbors) {
            int horizontalDirection = neighbor.getX() - position.getX();
            int verticalDirection = neighbor.getY() - position.getY();

            OthelloMove m = findPieceInLine(position, color, horizontalDirection, verticalDirection);

            if (m != null) {
                for (Position p : m.getPiecesToSwap()) {
                    p.setPiece(new OthelloPiece(color));
                }
            }
        }
    }

    private Position tryNeighbor(int x, int y, OthelloColor color) {

        // bounds check
        if(x < 0 || x > 7 || y < 0 || y > 7) return null;

        Position position = positions[y][x];

        OthelloPiece piece = (OthelloPiece)position.getPiece();

        // no piece at position
        if (piece == null)
            return null;

        // we don't need to include the current color's own pieces
        if (piece.getColor() == color)
            return null;

        return positions[y][x];
    }

    private List<Position> getOpponentNeighbors(Position position, OthelloColor color) {
        List<Position> neighbors = new ArrayList<>();
        Position neighbor = null;

        neighbor = tryNeighbor(position.getX() - 1, position.getY() + 1, color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() - 1, position.getY(), color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() - 1, position.getY() - 1, color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX(), position.getY() + 1, color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX(), position.getY() - 1, color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() + 1, position.getY() + 1, color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() + 1, position.getY(), color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        neighbor = tryNeighbor(position.getX() + 1, position.getY() - 1, color);
        if (neighbor != null) {
            neighbors.add(neighbor);
        }

        return neighbors;
    }

    private int getPiecesLeft(OthelloColor color) {
        int pieces = 0;

        for(int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                OthelloPiece piece = (OthelloPiece) positions[i][j].getPiece();

                if(piece != null) {
                    if (piece.getColor() == color) pieces++;
                }
            }
        }

        return pieces;
    }

    private boolean boardIsFull() {
        for(int i = 0 ;i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (positions[i][j].getPiece() == null) return false;
            }
        }

        return true;
    }

    public OthelloColor getWinner() {
        int whitePiecesLeft = getPiecesLeft(OthelloColor.WHITE);
        int blackPiecesLeft = getPiecesLeft(OthelloColor.BLACK);

        return whitePiecesLeft > blackPiecesLeft ? OthelloColor.WHITE : OthelloColor.BLACK;
    }

    public boolean isGameOver() {
        if(boardIsFull()) {
            OthelloColor winner = getWinner();

            System.out.println(winner == OthelloColor.WHITE ? "White Victory!" : "Black Victory!");

            return true;
        }

        return false;
    }

    @Override
    public void setPieceAtPosition(Piece piece, int x, int y) throws IllegalMoveException {
        super.setPieceAtPosition(piece, x, y);

        turnPieces(new Position(x, y), ((OthelloPiece)piece).getColor());

        // next player's turn
        setCurrentTurnColor(currentTurnColor == OthelloColor.WHITE ? OthelloColor.BLACK : OthelloColor.WHITE);
    }

    public OthelloColor getCurrentTurnColor() {
        return currentTurnColor;
    }

    public void setCurrentTurnColor(OthelloColor currentTurnColor) {
        this.currentTurnColor = currentTurnColor;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                OthelloPiece piece = (OthelloPiece)positions[i][j].getPiece();

                result.append(piece != null ? piece.toString() : "X");
                result.append(" ");
            }

            result.append(System.lineSeparator());
        }

        return result.toString();
    }
}