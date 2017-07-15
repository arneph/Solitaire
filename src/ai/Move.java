package ai;

import model.*;
import model.Cards.*;

public class Move {
	private Board board;
	private BoardElement origin;
	private BoardElement destination;
	private int count;
	
	public Move(Board b, BoardElement o, BoardElement d, int c) {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		
		if (o == null || !b.isElement(o) || 
			d == null || !b.isElement(d)) {
			throw new IllegalArgumentException();
		}
		
		if (o == d && !(o instanceof Deck)) {
			throw new IllegalArgumentException();
		}
		
		if (c <= 0) {
			throw new IllegalArgumentException();
		}
		
		board = b;
		origin = o;
		destination = d;
		count = c;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public BoardElement getOrigin() {
		return origin;
	}
	
	public BoardElement getDestination() {
		return destination;
	}
	
	public int getCount() {
		return count;
	}
	
	public boolean isPossible() {
		if (origin == board.getDeck() && 
			destination == board.getDeck()) {
			if (count != 1) return false;
			
			int m = board.getDeck().getNumberOfCardsOnDeck();
			int n = board.getDeck().getNumberOfCardsOnDeckStack();
			
			return (m + n) > 0;
		}
		
		Card[] cards = null;
		
		if (origin instanceof Deck) {
			if (count != 1) return false;
			
			Deck deck = (Deck) origin;
			
			if (deck.getNumberOfCardsOnDeckStack() == 0) return false;
			
			cards = new Card[] {deck.getTopCardOnDeckStack()};
			
		}else if (origin instanceof TableStack) {
			TableStack stack = (TableStack) origin;
			
			if (count > stack.getNumberOfUncoveredCards()) return false;
			
			int n = stack.getNumberOfCardsOnStack();
			
			cards = stack.getCardsInRange(n - count, n);
			
		}else if (origin instanceof BlockStack) {
			if (count != 1) return false;
			
			BlockStack stack = (BlockStack) origin;
			
			if (stack.getNumberOfCardsOnStack() == 0) return false;
			
			cards = new Card[] {stack.getTopCard()};
		}
		
		if (destination instanceof TableStack) {
			TableStack stack = (TableStack) destination;
			
			if (stack.canAddCardsToStack(cards) == false) return false;
			
		}else if (destination instanceof BlockStack) {
			if (count != 1) return false;
			
			BlockStack stack = (BlockStack) destination;
			
			if (stack.canAddCardToStack(cards[0]) == false) return false;
		}
		
		return true;
	}
	
}
