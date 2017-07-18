package model;

public abstract class Move {
	private Board board;
	
	public Move(Board board) {
		if (board == null) {
			throw new IllegalArgumentException();
		}
		
		this.board = board;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public abstract boolean isPossible();
	
}
