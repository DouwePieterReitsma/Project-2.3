package gameai.models;

import gameai.IllegalMoveException;

public abstract class Piece {
    public void moveTo(Position position) throws IllegalMoveException {
        if (canMoveTo(position)) {
            position.setPiece(this);
        }
        else {
            throw new IllegalMoveException();
        }
    }

    public abstract boolean canMoveTo(Position position);
}