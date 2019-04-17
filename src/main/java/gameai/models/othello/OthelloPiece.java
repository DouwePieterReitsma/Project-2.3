package gameai.models.othello;

import gameai.models.Piece;

/**
 * class for representing an Othello piece.
 */
public class OthelloPiece extends Piece {
    public OthelloColor getColor() {
        return color;
    }

    private OthelloColor color;

    public OthelloPiece(OthelloColor color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color == OthelloColor.WHITE ? "W" : "B";
    }
}
