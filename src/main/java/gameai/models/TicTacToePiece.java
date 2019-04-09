package gameai.models;

import java.util.List;

public class TicTacToePiece extends Piece {
    public enum TicTacToeFigure {
        CROSS, CIRCLE
    }
    private TicTacToeFigure figure;
    
    public TicTacToePiece(TicTacToeFigure figure) {
    	this.figure = figure;
    }
    
    public String toString() {
    	return figure + "";
    }
    
    public TicTacToeFigure getFigure() {
    	return figure;
    }
    
    public boolean equals(TicTacToePiece o) {
		if( o.getFigure() == this.getFigure()) {
			return true;
		}
    	return false;
    }
    
    public void setFigure(TicTacToeFigure figure) {
    	this.figure = figure;
    }

}
