package gameai.models;

public class TicTacToePiece extends Piece {
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
