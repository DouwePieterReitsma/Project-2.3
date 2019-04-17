package gameai.models;

/**
 * The Tic Tac Toe pieces to be set on the board.
 * @author Jelle
 *
 */
public class TicTacToePiece extends Piece {
    private TicTacToeFigure figure;
    
    /**
     * Constructur whichs sets what kind of figure the piece has.
     * @param figure
     * The figure the current piece is.
     */
    public TicTacToePiece(TicTacToeFigure figure) {
    	this.figure = figure;
    }
    
    /**
     * Overrides the toString method to display the content of the piece.
     * @return
     * String representation of the figure.
     */
    public String toString() {
    	return figure + "";
    }
    
    public TicTacToeFigure getFigure() {
    	return figure;
    }
    
    /**
     * Overrides of the equals method to set the good condition for comparison.
     * @param o
     * the piece to be compared.
     * @return
     * True if pieces are the same, false if not.
     */
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
