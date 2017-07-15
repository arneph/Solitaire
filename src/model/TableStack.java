package model;

import java.util.*;

import model.Cards.*;

public class TableStack extends BoardElement {
	private Card[] stack;
	private int coveredCards;
	
	public TableStack() {
		stack = new Card[0];
		coveredCards = 0;
	}
	
	public int getNumberOfCardsOnStack() {
		return stack.length;
	}
	
	public Card getCardAtIndex(int index) {
		if (index < 0 || index >= stack.length) {
			throw new IllegalArgumentException();
		}
		
		return stack[index];
	}
	
	public Card[] getCardsInRange(int startIndex, int endIndex) {
		if (startIndex < 0 || 
			startIndex > endIndex || 
			endIndex > stack.length) {
			throw new IllegalArgumentException();
		}
		
		return Arrays.copyOfRange(stack, startIndex, endIndex);
	}
	
	public Card[] getStack() {
		return stack;
	}
	
	public void setStack(Card[] s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		stack = s;
		coveredCards = 0;
	}
	
	public int getNumberOfCoveredCards() {
		return coveredCards;
	}
	
	public void setNumberOfCoveredCards(int n) {
		if (n < 0 || n >= stack.length) {
			throw new IllegalArgumentException();
		}
		
		coveredCards = n;
	}
	
	public void setNumberOfUncoveredCards(int n) {
		if (n < Math.min(1, stack.length)) {
			throw new IllegalArgumentException();
		}
		
		coveredCards = stack.length - n;
	}
	
	public int getNumberOfUncoveredCards() {
		return stack.length - coveredCards;
	}
	
	public boolean canAddCardsToStack(Card[] cards) {
		if (cards == null || cards.length == 0) {
			throw new IllegalArgumentException();
		}
		
		if (stack.length == 0) {
			return Cards.isKing(cards[0]);
			
		}else{
			return Cards.isTableStackPredecessor(cards[0], stack[stack.length - 1]);			
		}
	}
	
	public void addCardsToStack(Card[] cards) {
		if (canAddCardsToStack(cards) == false) {
			throw new IllegalArgumentException();
		}
		
		int m = stack.length;
		int n = cards.length;
		
		stack = Arrays.copyOf(stack, m + n);
		
		System.arraycopy(cards, 0, stack, m, n);
	}
	
	public void removeCardsFromStack(int cardIndex) {
		if (cardIndex < coveredCards || 
			cardIndex >= stack.length) {
			throw new IllegalArgumentException();
		}
		
		stack = Arrays.copyOf(stack, cardIndex);
		
		if (coveredCards > 0 && 
			coveredCards == stack.length) {
			coveredCards--;
		}
	}
	
}
