package gameai.models;

public class OthelloPiece extends Piece {
    private enum OthelloColor {
        WHITE, BLACK
    }

    private OthelloColor color;

    public OthelloPiece(OthelloColor color) {
        this.color = color;
    }

    @Override
    public boolean canMoveTo(Position position) {
        return true;
    }
}
