package ai;

import java.util.*;

import model.*;

public class BasicAI extends AI {
	
	public BasicAI(Board board) {
		super(board);
	}
	
	private CardMove[] getPossibleCardMoves() {
		Move[] moves = getBoard().getPossibleMoves();
		
		int n = 0;
		CardMove[] cardMoves = new CardMove[moves.length];
		
		for (Move move : moves) {
			if (!(move instanceof CardMove)) continue;
			
			cardMoves[n++] = (CardMove) move;
		}
		
		return Arrays.copyOf(cardMoves, n);
	}
	
	public Move getNextMove() {
		CardMove[] moves = getPossibleCardMoves();
		
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
		
		for (CardMove move : moves) {
			if (move.getDestination() instanceof BlockStack) {
				return move;
			}
		}
		
		for (CardMove move : moves) {
			if (move.getOrigin() instanceof TableStack && 
				move.getDestination() instanceof TableStack) {
				TableStack origin = (TableStack) move.getOrigin();
				
				if (origin.getNumberOfCoveredCards() > 0 && 
					origin.getNumberOfUncoveredCards() == move.getCount()) {
					return move;
				}
			}
		}
		
		for (CardMove move : moves) {
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
		
		for (CardMove move : moves) {
			if (move.getOrigin() instanceof Deck && 
				!(move.getDestination() instanceof Deck)) {
				return move;
			}
		}
		
		DeckMove deckMove = new DeckMove(getBoard());
		
		if (deckMove.isPossible()) {
			return deckMove;
		}else{
			return null;
		}
	}
	
}
