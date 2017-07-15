package ai;

import model.*;

public class BasicAI extends AI {
	
	public Move getNextMove() {
		Move[] moves = getPossibleMoves();
		
		int freeTableStacks = 0;
		boolean kingOnTop = false;

		if (Cards.isKing(getBoard().getDeck().getTopCardOnDeckStack())) {
			kingOnTop = true;
		}
		
		for (int i = 0; i < 7; i++) {
			if (getBoard().getTableStackAtIndex(i).getNumberOfCardsOnStack() == 0) {
				freeTableStacks++;
			}else{
				int n = getBoard().getTableStackAtIndex(i).getNumberOfCoveredCards();
				
				if (n > 0 && Cards.isKing(getBoard().getTableStackAtIndex(i).getCardAtIndex(n))) {
					kingOnTop = true;
				}
			}
		}
		
		for (Move move : moves) {
			if (move.getDestination() instanceof BlockStack) {
				return move;
			}
		}
		
		for (Move move : moves) {
			if (move.getOrigin() instanceof TableStack && 
				move.getDestination() instanceof TableStack) {
				TableStack origin = (TableStack) move.getOrigin();
				
				if (origin.getNumberOfCoveredCards() > 0 && 
					origin.getNumberOfUncoveredCards() == move.getCount()) {
					return move;
				}
			}
		}
		
		for (Move move : moves) {
			if (move.getOrigin() instanceof TableStack && 
				move.getDestination() instanceof TableStack) {
				TableStack origin = (TableStack) move.getOrigin();
				
				if (kingOnTop && 
					freeTableStacks == 0 && 
					origin.getNumberOfUncoveredCards() == move.getCount()) {
					return move;
				}
			}
		}
		
		for (Move move : moves) {
			if (move.getOrigin() instanceof Deck && 
				!(move.getDestination() instanceof Deck)) {
				return move;
			}
		}
		
		return new Move(getBoard(), getBoard().getDeck(), getBoard().getDeck(), 1);
	}
	
}
