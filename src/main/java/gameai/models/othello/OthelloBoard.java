package gameai.models.othello;

import gameai.models.Board;
import gameai.models.IllegalMoveException;
import gameai.models.Piece;
import gameai.models.Position;
import gameai.views.OthelloView;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoard extends Board {

    public OthelloBoard(OthelloColor startingColor) {
        super(8, 8);


        setCurrentTurnColor(startingColor);
        //this.currentTurnColor = startingColor;
    }

    public OthelloBoard(Position[][] positions, OthelloColor startingColor) {
        this(startingColor);

        setPositions(positions);
    }

    public OthelloBoard(OthelloBoard board) {
        super(8, 8);

        setCurrentTurnColor(board.getCurrentTurnColor());

        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions[i].length; j++) {
                positions[i][j] = new Position(board.getPositions()[i][j]);
            }
        }
    }

    private OthelloColor currentTurnColor;
    private OthelloColor opponentColor;

    @Override
    public List<Position> getLegalMoves() {
        return getLegalMoves(currentTurnColor);
    }

    public List<Position> getLegalMoves(OthelloColor color) {
        List<Position> result = new ArrayList<>();

        for(OthelloMove move : getLegalMovesWrapper(color)) {
            result.add(move.getPosition());
        }

        return result;
    }

    public List<OthelloMove> getLegalMovesWrapper(OthelloColor color) {
        List<OthelloMove> legalMoves = new ArrayList<>();

        for (Position[] row : positions) {
            for (Position column : row) {
                OthelloMove move = tryPosition(column, color);

                if (move != null)
                    legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    private OthelloMove tryPosition(Position position, OthelloColor color) {
        OthelloPiece piece = (OthelloPiece)position.getPiece();
        OthelloMove move = new OthelloMove(position);

        // check if a piece is already at the given position
        if (piece != null) return null;

        // get the adjacent positions for the given position
        List<Position> neighbors = getOpponentNeighbors(position, color);

        // no adjacent opponent pieces
        if (neighbors.size() == 0)
            return null;

        for(Position neighbor : neighbors) {
            int horizontalDirection = neighbor.getX() - position.getX();
            int verticalDirection = neighbor.getY() - position.getY();

            OthelloMove temp = findPieceInLine(position, color, horizontalDirection, verticalDirection);

            // if piece is found add all enemy pieces that would be swapped to the piecesToSwap list
            if (temp != null) {
                move.getPiecesToSwap().addAll(temp.getPiecesToSwap());
            }
        }

        return move.getScore() > 0 ? move : null;
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

    public int getPlayerScore(OthelloColor color) {
        int score = 0;

        for (Position[] row : positions) {
            for (Position column : row) {
                OthelloPiece piece = (OthelloPiece) column.getPiece();

                if (piece != null) {
                    if (piece.getColor() == color) score++;
                }
            }
        }

        return score;
    }

    public OthelloMatchResult getMatchResult() {
        int whitePiecesLeft = getPlayerScore(OthelloColor.WHITE);
        int blackPiecesLeft = getPlayerScore(OthelloColor.BLACK);

        if (whitePiecesLeft == blackPiecesLeft) return OthelloMatchResult.TIE;

        return whitePiecesLeft > blackPiecesLeft ? OthelloMatchResult.WHITE_WINS : OthelloMatchResult.BLACK_WINS;
    }

    public boolean isGameOver() {
        return getLegalMoves(currentTurnColor).isEmpty() && getLegalMoves(opponentColor).isEmpty();
    }

    @Override
    public void setPieceAtPosition(Piece piece, Position position) throws IllegalMoveException {
        super.setPieceAtPosition(piece, position);

        turnPieces(position, ((OthelloPiece)piece).getColor());

        advanceTurn();
    }

    public void advanceTurn() {
        turn++;

        setCurrentTurnColor(opponentColor);
    }

    public OthelloColor getCurrentTurnColor() {
        return currentTurnColor;
    }
    public OthelloColor getOpponentColor() { return opponentColor; }

    public void setCurrentTurnColor(OthelloColor currentTurnColor) {
        this.currentTurnColor = currentTurnColor;
        this.opponentColor = currentTurnColor == OthelloColor.WHITE ? OthelloColor.BLACK : OthelloColor.WHITE;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Position[] row : positions) {
            for (Position column : row) {
                OthelloPiece piece = (OthelloPiece) column.getPiece();

                result.append(piece != null ? piece.toString() : "X");
                result.append(" ");
            }

            result.append(System.lineSeparator());
        }

        return result.toString();
    }
}