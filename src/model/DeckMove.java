package model;

public class DeckMove extends Move {
	
	public DeckMove(Board board) {
		super(board);
	}
	
	public boolean isPossible() {
		return getBoard().getDeck().getTotalNumberOfCardsOnDeck() > 0;
	}
	
}
