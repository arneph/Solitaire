package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;

import model.*;
import model.Cards.*;

@SuppressWarnings("serial")
public class SolitaireView extends JPanel implements MouseListener, 
													 MouseMotionListener {
	private Board board;
	
	private Rectangle dragRectangle;
	private Card[] draggedCards;
	private BoardElement dragOrigin;
	private BoardElement dragDestination;
	
	private Point cursorPosition;
	
	public SolitaireView() {
		board = new Board();
		
		cursorPosition = null;
		dragRectangle = null;
		draggedCards = null;
		dragOrigin = null;
		dragDestination = null;
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board b) {
		board = b;
		
		repaint();
	}
	
	//Positioning:
	private Rectangle getDeckRectangle() {
		return new Rectangle(20, 20, Images.CardW, Images.CardH);
	}
	
	private Rectangle getDeckStackRectangle() {
		return new Rectangle(40 + Images.CardW, 20, 
		                     40 + Images.CardW, Images.CardH);
	}
	
	private Rectangle getCardAtIndexOnDeckStackRectangle(int index) {
		if (index < 0 || index >= 3) {
			throw new IllegalArgumentException();
		}
		
		return new Rectangle(40 + Images.CardW + index * 20, 20, 
		                     Images.CardW, Images.CardH);
	}
	
	private Rectangle getCardAtTopOfDeckStackRectangle() {
		if (board == null) {
			return new Rectangle();
		}
		
		int n = Math.min(3, board.getDeck().getNumberOfCardsOnDeckStack());
		
		if (n == 0) {
			return new Rectangle();
			
		}else{
			return new Rectangle(40 + Images.CardW + (n - 1) * 20, 20, 
			                     Images.CardW, Images.CardH);
		}
	}
	
	private Rectangle getTableStacksRectangle() {
		int w = getWidth();
		
		int tableStacksWidth = 7 * Images.CardW + 120;
		
		return new Rectangle((w - tableStacksWidth) / 2, 60 + Images.CardH, 
		                     tableStacksWidth, 360 + Images.CardH);
	}
	
	private Rectangle getTableStackAtIndexRectangle(int index) {
		if (index < 0 || index >= 7) {
			throw new IllegalArgumentException();
		}
		
		int w = getWidth();
		
		int tableStacksWidth = 7 * Images.CardW + 120;
		
		return new Rectangle((w - tableStacksWidth) / 2 + index * (Images.CardW + 20), 60 + Images.CardH, 
		                     Images.CardW, 360 + Images.CardH);
	}
	
	private Rectangle getCardAtIndexOnTableStackAtIndexRectangle(int stackIndex, int cardIndex) {
		if (stackIndex < 0 || stackIndex >= 7 || 
			cardIndex < 0 || cardIndex >= 19) {
			throw new IllegalArgumentException();
		}
		
		int w = getWidth();
		
		int tableStacksWidth = 7 * Images.CardW + 120;
		
		return new Rectangle((w - tableStacksWidth) / 2 + stackIndex * (Images.CardW + 20), 60 + Images.CardH + cardIndex * 20, 
		                     Images.CardW, Images.CardH);
	}

	private Rectangle getCardsInRangeOnTableStackAtIndexRectangle(int stackIndex, int cardStartIndex, int cardEndIndex) {
		if (stackIndex < 0 || stackIndex >= 7 || 
			cardStartIndex < 0 || cardStartIndex >= 19 ||
			cardEndIndex < cardStartIndex || cardEndIndex > 19) {
			throw new IllegalArgumentException();
		}
		
		int w = getWidth();
		
		int tableStacksWidth = 7 * Images.CardW + 120;
		
		if (cardEndIndex - cardStartIndex == 0) {
			return new Rectangle();
			
		}else{
			return new Rectangle((w - tableStacksWidth) / 2 + stackIndex * (Images.CardW + 20), 60 + Images.CardH + cardStartIndex * 20, 
			                     Images.CardW, 20 * (cardEndIndex - cardStartIndex - 1) + Images.CardH);
		}
	}
	
	private Rectangle getBlockStacksRectangle() {
		int w = getWidth();
		
		int blockStacksWidth = 4 * Images.CardW + 60;
		
		return new Rectangle(w - blockStacksWidth - 20, 20, 
		                     blockStacksWidth, Images.CardH);
	}
	
	private Rectangle getBlockStackAtIndexRectangle(int index) {
		if (index < 0 || index >= 4) {
			throw new IllegalArgumentException();
		}
		
		int w = getWidth();
		
		int blockStacksWidth = 4 * Images.CardW + 60;
		
		return new Rectangle(w - blockStacksWidth - 20 + index * (Images.CardW + 20), 20, 
		                     Images.CardW, Images.CardH);
	}
	
	public void mouseClicked(MouseEvent e) {
		cursorPosition = e.getPoint();
		
		if (board == null) {
			return;
		}
		
		if (getDeckRectangle().contains(e.getPoint())) {
			board.getDeck().turnDeck();
			
			repaint();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		cursorPosition = e.getPoint();
		
		if (board == null || dragOrigin != null || dragDestination != null) {
			return;
		}
		
		if (getCardAtTopOfDeckStackRectangle().contains(e.getPoint())) {
			startDrag(board.getDeck(), 1);
			
			return;
		}
		
		for (int i = 0; i < 7; i++) {
			int n = board.getTableStackAtIndex(i).getNumberOfCardsOnStack();
			int coveredCards = board.getTableStackAtIndex(i).getNumberOfCoveredCards();
			
			if (n == 0) continue;
			
			for (int j = n - 1; j >= coveredCards; j--) {
				if (getCardAtIndexOnTableStackAtIndexRectangle(i, j).contains(e.getPoint())) {
					startDrag(board.getTableStackAtIndex(i), n - j);
					
					return;
				}
			}
		}
		
		for (int i = 0; i < 4; i++) {
			int n = board.getBlockStackAtIndex(i).getNumberOfCardsOnStack();
			
			if (n == 0) continue;
			
			if (getBlockStackAtIndexRectangle(i).contains(e.getPoint())) {
				startDrag(board.getBlockStackAtIndex(i), 1);
				
				return;
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		Point oldCursorPosition = cursorPosition;
		
		cursorPosition = e.getPoint();
		
		if (board == null || dragOrigin == null || dragDestination != null) {
			return;
		}
		
		dragRectangle.translate(cursorPosition.x - oldCursorPosition.x, 
		                        cursorPosition.y - oldCursorPosition.y);
		
		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		Point oldCursorPosition = cursorPosition;
		
		cursorPosition = e.getPoint();
		
		if (board == null || dragOrigin == null || dragDestination != null) {
			return;
		}
		
		dragRectangle.translate(cursorPosition.x - oldCursorPosition.x, 
		                        cursorPosition.y - oldCursorPosition.y);
		
		for (int i = 0; i < 7; i++) {
			if (board.getTableStackAtIndex(i).canAddCardsToStack(draggedCards) == false) continue;
			
			int n = board.getTableStackAtIndex(i).getNumberOfCardsOnStack();
			
			if (n == 0 && getCardAtIndexOnTableStackAtIndexRectangle(i, 0).contains(e.getPoint())) {
				endDrag(board.getTableStackAtIndex(i));
				
				return;
				
			}else if (n > 0 && getCardsInRangeOnTableStackAtIndexRectangle(i, n - 1, n).contains(e.getPoint())) {
				endDrag(board.getTableStackAtIndex(i));
				
				return;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			if (board.getBlockStackAtIndex(i).canAddCardToStack(draggedCards[0]) == false) continue;
			
			if (getBlockStackAtIndexRectangle(i).contains(e.getPoint())) {
				endDrag(board.getBlockStackAtIndex(i));
				
				return;
			}
		}
		
		endDrag(dragOrigin);
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	private void startDrag(BoardElement origin, int count) {
		if (origin == board.getDeck()) {
			dragRectangle = getCardAtTopOfDeckStackRectangle();
			draggedCards = new Card[] {board.getDeck().getTopCardOnDeckStack()};
			dragOrigin = origin;
			dragDestination = null;
			
			repaint();
			
			return;
		}
		
		for (int i = 0; i < 7; i++) {
			if (origin != board.getTableStackAtIndex(i)) continue;
			
			int startIndex = board.getTableStackAtIndex(i).getNumberOfCardsOnStack() - count;
			
			dragRectangle = getCardsInRangeOnTableStackAtIndexRectangle(i, startIndex, startIndex + count);
			draggedCards = board.getTableStackAtIndex(i).getCardsInRange(startIndex, startIndex + count);
			dragOrigin = origin;
			dragDestination = null;
			
			repaint();
			
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			if (origin != board.getBlockStackAtIndex(i)) continue;
			
			dragRectangle = getBlockStackAtIndexRectangle(i);
			draggedCards = new Card[] {board.getBlockStackAtIndex(i).getTopCard()};
			dragOrigin = origin;
			dragDestination = null;
			
			repaint();
			
			return;
		}
	}
	
	private void endDrag(BoardElement destination) {
		dragDestination = destination;
		
		if (dragOrigin != dragDestination) {
			if (dragOrigin == board.getDeck()) {
				board.getDeck().removeCardFromDeckStack();
			}
			
			for (int i = 0; i < 7; i++) {
				if (dragOrigin != board.getTableStackAtIndex(i)) continue;
				
				int startIndex = board.getTableStackAtIndex(i).getNumberOfCardsOnStack() - draggedCards.length;
				
				board.getTableStackAtIndex(i).removeCardsFromStack(startIndex);
			}
			
			for (int i = 0; i < 4; i++) {
				if (dragOrigin != board.getBlockStackAtIndex(i)) continue;
				
				board.getBlockStackAtIndex(i).removeCardFromStack();
			}
			
			for (int i = 0; i < 7; i++) {
				if (dragDestination != board.getTableStackAtIndex(i)) continue;
				
				board.getTableStackAtIndex(i).addCardsToStack(draggedCards);
			}
			
			for (int i = 0; i < 4; i++) {
				if (dragDestination != board.getBlockStackAtIndex(i)) continue;
				
				board.getBlockStackAtIndex(i).addCardToStack(draggedCards[0]);
			}
		}
		
		Rectangle r = null;
		
		if (dragDestination == board.getDeck()) {
			r = getCardAtTopOfDeckStackRectangle();
		}
		
		for (int i = 0; i < 7; i++) {
			if (dragDestination != board.getTableStackAtIndex(i)) continue;
			
			int startIndex = board.getTableStackAtIndex(i).getNumberOfCardsOnStack() - draggedCards.length;
			
			r = getCardsInRangeOnTableStackAtIndexRectangle(i, startIndex, startIndex + draggedCards.length);
		}
		
		for (int i = 0; i < 4; i++) {
			if (dragDestination != board.getBlockStackAtIndex(i)) continue;
			
			r = getBlockStackAtIndexRectangle(i);
		}
		
		final Rectangle animationStart = dragRectangle;
		final Rectangle animationEnd = r;
		
		final double distance = Math.sqrt(Math.pow(animationEnd.x - animationStart.x, 2) + 
		                                  Math.pow(animationEnd.y - animationStart.y, 2));
		final double duration = Math.min(0.25, distance / 500.0);
		final double startTime = System.currentTimeMillis();
		final double endTime = startTime + duration * 1000.0;
		
		final Timer timer = new Timer(16, null);
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double currentTime = System.currentTimeMillis();
				double linearProgress = (currentTime - startTime) / (endTime - startTime);
				
				if (linearProgress < 0.0) {
					linearProgress = 0.0;
				}
				if (linearProgress > 1.0) {
					linearProgress = 1.0;
				}
				
				double progress = 0.5 * (1.0 - Math.cos(linearProgress * Math.PI));
				
				int x = (int) Math.round(animationStart.x + progress * (animationEnd.x - animationStart.x));
				int y = (int) Math.round(animationStart.y + progress * (animationEnd.y - animationStart.y));
				
				dragRectangle.setLocation(x, y);
				
				paintImmediately(getBounds());
				
				if (linearProgress == 1.0) {
					timer.stop();
					
					dragRectangle = null;
					draggedCards = null;
					dragOrigin = null;
					dragDestination = null;
				}
			}
		};
		
		timer.setInitialDelay(0);
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.addActionListener(actionListener);
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		int w = getWidth();
		int h = getHeight();
		
		g2d.setColor(new Color(0, 191, 0));
		g2d.fillRect(0, 0, w, 40 + Images.CardH);
		
		g2d.setColor(new Color(0, 159, 0));
		g2d.fillRect(0, 40 + Images.CardH, w, h - 40 - Images.CardH);
		
		if (board == null) return;
		
		paintDeck(g2d);
		paintTableStacks(g2d);
		paintBlockStacks(g2d);
		paintDraggedCards(g2d);
	}
	
	private void paintDeck(Graphics2D g2d) {
		Color spaceColor = new Color(127, 127, 127);

		Rectangle deck = getDeckRectangle();
		
		g2d.setColor(spaceColor);
		g2d.drawRoundRect(deck.x, deck.y, 
		                  deck.width, deck.height, 
		                  5, 5);
		
		if (board.getDeck().getNumberOfCardsOnDeck() > 0) {
			g2d.drawImage(Images.imageForCard(0), deck.x, deck.y, null);
		}
		
		Card[] visibleDeckStackCards = board.getDeck().getVisibleCardsOnDeckStack();
		int n = visibleDeckStackCards.length;
		
		if (dragOrigin == board.getDeck() && dragDestination == null || 
			dragDestination == board.getDeck()) {
			n--;
		}
		
		if (n >= 1) {
			Rectangle card = getCardAtIndexOnDeckStackRectangle(0);
			
			g2d.drawImage(Images.imageForCard(visibleDeckStackCards[0].ordinal()),
			              card.x, 
			              card.y, 
			              null);
		}
		if (n >= 2) {
			Rectangle card = getCardAtIndexOnDeckStackRectangle(1);
			
			g2d.drawImage(Images.imageForCard(visibleDeckStackCards[1].ordinal()),
			              card.x, 
			              card.y, 
			              null);
		}
		if (n == 3) {
			Rectangle card = getCardAtIndexOnDeckStackRectangle(2);
			
			g2d.drawImage(Images.imageForCard(visibleDeckStackCards[2].ordinal()),
			              card.x, 
			              card.y, 
			              null);
		}
	}
	
	private void paintTableStacks(Graphics2D g2d) {
		Color spaceColor = new Color(127, 127, 127);
		
		for (int i = 0; i < 7; i++) {
			Rectangle stack = getCardAtIndexOnTableStackAtIndexRectangle(i, 0);
			
			g2d.setColor(spaceColor);
			g2d.drawRoundRect(stack.x, stack.y, 
			                  stack.width, stack.height, 
			                  5, 5);
			
			Card[] stackCards = board.getTableStackAtIndex(i).getStack();
			int coveredCards = board.getTableStackAtIndex(i).getNumberOfCoveredCards();
			
			for (int j = 0; j < stackCards.length; j++) {
				Rectangle card = getCardAtIndexOnTableStackAtIndexRectangle(i, j);
				
				if (dragOrigin == board.getTableStackAtIndex(i) && dragDestination == null|| 
					dragDestination == board.getTableStackAtIndex(i)) {
					if (j >= stackCards.length - draggedCards.length) break;
				}
				
				if (j < coveredCards) {
					g2d.drawImage(Images.imageForCard(0), 
					              card.x, 
					              card.y, 
					              null);
				}else{
					g2d.drawImage(Images.imageForCard(stackCards[j].ordinal()), 
					              card.x, 
					              card.y, 
					              null);
				}
			}
		}
	}
	
	private void paintBlockStacks(Graphics2D g2d) {
		Color spaceColor = new Color(127, 127, 127);
		
		for (int i = 0; i < 4; i++) {
			Rectangle stack = getBlockStackAtIndexRectangle(i);
			
			g2d.setColor(spaceColor);
			g2d.drawRoundRect(stack.x, stack.y, 
			                  stack.width, stack.height, 
			                  5, 5);
			
			Card[] stackCards = board.getBlockStackAtIndex(i).getStack();
			int n = stackCards.length;

			if (dragOrigin == board.getBlockStackAtIndex(i) && dragDestination == null || 
				dragDestination == board.getBlockStackAtIndex(i)) {
				n--;
			}
			
			if (n > 0) {
				g2d.drawImage(Images.imageForCard(stackCards[n - 1].ordinal()), 
				              stack.x, 
				              stack.y, 
				              null);
			}
		}
	}
	
	private void paintDraggedCards(Graphics2D g2d) {
		if (draggedCards == null) return;
		
		for (int i = 0; i < draggedCards.length; i++) {
			g2d.drawImage(Images.imageForCard(draggedCards[i].ordinal()), 
			              dragRectangle.x, 
			              dragRectangle.y + i * 20, 
			              null);
		}
	}
	
}
