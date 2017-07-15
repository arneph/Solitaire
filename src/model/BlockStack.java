package model;

import java.util.*;

import model.Cards.*;

public class BlockStack extends BoardElement {
	private Card[] stack;
	
	public BlockStack() {
		stack = new Card[0];
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
	
	public Card getTopCard() {
		if (stack.length == 0) {
			return Card.None;
		}
		
		return stack[stack.length - 1];
	}
	
	public Card[] getStack() {
		return stack;
	}
	
	public void setStack(Card[] s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		stack = s;
	}
	
	public boolean canAddCardToStack(Card card) {
		if (stack.length == 0) {
			return Cards.isAce(card);
			
		}else{
			return Cards.isBlockSuccessor(card, stack[stack.length - 1]);
		}
	}
	
	public void addCardToStack(Card card) {
		if (canAddCardToStack(card) == false) {
			throw new IllegalArgumentException();
		}
		
		int n = stack.length;
		
		stack = Arrays.copyOf(stack, n + 1);
		stack[n] = card;
	}
	
	public void removeCardFromStack() {
		if (stack.length == 0) {
			throw new IllegalArgumentException();
		}
		
		int n = stack.length;
		
		stack = Arrays.copyOf(stack, n - 1);
	}
	
	public boolean isComplete() {
		return stack.length == 13;
	}
	
}