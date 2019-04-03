package gameai.models;

import java.util.List;

public abstract class Board {
	protected int turn = 0;
    protected Position[][] positions;

    public Board(int x, int y) {
        positions = new Position[y][x];

        for(int i = 0; i < y; i++){
            for(int j = 0; j < x; j++){
                positions[i][j] = new Position(j, i);
            }
        }
    }

    public Position[][] getPositions() {
        return positions;
    }

    public void setPositions(Position[][] positions) {
        this.positions = positions;
    }

    public abstract List<Position> getLegalMoves();

    public void setPieceAtPosition(Piece piece, int x, int y) throws IllegalMoveException {
        Position position = positions[y][x];

        List<Position> legalMoves = getLegalMoves();

        if(!legalMoves.contains(position)) {
            throw new IllegalMoveException();
        }

        position.setPiece(piece);
    }
    
    public int getTurn() {
    	return turn;
    }
    
    public void setTurn(int turn) {
    	this.turn = turn;
    }
}