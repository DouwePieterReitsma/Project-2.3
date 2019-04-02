package gameai;

import gameai.models.Piece;
import gameai.models.Position;

public class IllegalMoveException extends Exception {
    public IllegalMoveException() {
        super();
    }

    public IllegalMoveException(String message) {
        super(message);
    }

    public IllegalMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMoveException(Throwable cause) {
        super(cause);
    }
}
