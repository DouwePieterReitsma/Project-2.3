package gameai.models;

public class OthelloPiece extends Piece {
    public OthelloColor getColor() {
        return color;
    }

    private enum OthelloColor {
        WHITE, BLACK
    }

    private OthelloColor color;

    public OthelloPiece(OthelloColor color) {
        this.color = color;
    }
}
