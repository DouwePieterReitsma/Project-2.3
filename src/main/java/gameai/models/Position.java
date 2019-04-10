package gameai.models;

public class Position {
    private int x;
    private int y;
    private Piece piece;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this(position.getX(), position.getY());

        setPiece(position.getPiece());
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isAvailable() {
        return getPiece() == null;
    }

    @Override
    public String toString() {
        return String.format("Position: %d, %d", getX(), getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Position))
            return false;

        Position position = (Position)obj;

        return getX() == position.getX() && getY() == position.getY();
    }
}