package model;

import model.Cards.*;

public class CardMove extends Move {
	private BoardElement origin;
	private BoardElement destination;
	private int count;
	
	public CardMove(Board board, BoardElement origin, BoardElement destination, int count) {
		super(board);
		
		if (origin == null || 
			!getBoard().isElement(origin)) {
			throw new IllegalArgumentException();
		}
		
		if (destination == null || 
			destination instanceof Deck || 
			!getBoard().isElement(destination)) {
			throw new IllegalArgumentException();
		}
		
		if (origin == destination) {
			throw new IllegalArgumentException();
		}
		
		if (count <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.origin = origin;
		this.destination = destination;
		this.count = count;
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
