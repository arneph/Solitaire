package ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.Timer;

import ai.*;
import model.*;
import model.Cards.*;

@SuppressWarnings("serial")
public class BoardView extends JPanel implements ActionListener, 
													 MouseListener, 
													 MouseMotionListener {
	private Board board;
	private AI ai;
	private Move move;
	
	private Card[] draggedCards;
	private BoardElement dragOrigin;
	private BoardElement dragDestination;
	
	private Rectangle[] dragStartRectangles;
	private Rectangle[] dragCurrentRectangles;
	private Rectangle[] dragEndRectangles;
	
	private Point cursorStartPosition;
	private Point cursorCurrentPosition;
	private Point cursorEndPosition;
	
	private Timer timer;
	private long startTime;
	private long endTime;
	
	public BoardView() {
		board = new Board();
		ai = null;
		move = null;
		
		draggedCards = null;
		dragOrigin = null;
		dragDestination = null;
		
		dragStartRectangles = null;
		dragCurrentRectangles = null;
		dragEndRectangles = null;
		
		cursorStartPosition = null;
		cursorCurrentPosition = new Point(320, 160);
		cursorEndPosition = null;
		
		timer = new Timer(16, this);
		timer.setInitialDelay(0);
		timer.setCoalesce(true);
		timer.setRepeats(true);
		
		startTime = 0;
		endTime = 0;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		timer.start();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board b) {
		board = b;
		
		repaint();
	}
	
	//AI:
	public boolean isHumanPlayer() {
		return (ai == null);
	}
	
	public AI getAI() {
		return ai;
	}
	
	public void setAI(AI a) {
		if (ai == a) {
			return;
		}
		
		if (ai == null) {
			removeMouseListener(this);
			removeMouseMotionListener(this);
		}
		
		ai = a;
		move = null;
		
		if (ai == null) {
			addMouseListener(this);
			addMouseMotionListener(this);
			
		}else{
			move = ai.getNextMove();
			
			if (move != null) {
				prepareForMove();
			}
		}
		
		repaint();
	}
	
	private void prepareForMove() {
		if (move == null || 
			move.isPossible() == false || 
			move.getBoard() != board) {
			return;
		}
		
		cursorStartPosition = cursorCurrentPosition;
		
		if (move instanceof DeckMove) {
			int centerX = (int) getRectangleOfDeck().getCenterX();
			int centerY = (int) getRectangleOfDeck().getCenterY();
			
			cursorEndPosition = new Point(centerX, centerY);
			
		}else if (move instanceof CardMove) {
			CardMove cardMove = (CardMove) move;
			
			if (cardMove.getOrigin() == board.getDeck()) {
				int centerX = (int) getRectangleOfTopCardOnDeckStack().getCenterX();
				int centerY = (int) getRectangleOfTopCardOnDeckStack().getCenterY();
				
				cursorEndPosition = new Point(centerX, centerY);
				
			}else if (cardMove.getOrigin() instanceof TableStack) {
				int stackIndex = board.getIndexOfTableStack((TableStack) cardMove.getOrigin());
				int m = cardMove.getCount();
				int n = board.getTableStackAtIndex(stackIndex).getNumberOfCardsOnStack();
				
				int centerX = (int) getRectangleOfCardOnTableStack(stackIndex, n - m).getCenterX();
				int centerY = (int) getRectangleOfCardOnTableStack(stackIndex, n - m).getCenterY();
				
				if (m > 1) {
					centerY = getRectangleOfCardOnTableStack(stackIndex, n - m).y + 10;
				}
				
				cursorEndPosition = new Point(centerX, centerY);
				
			}else if (cardMove.getOrigin() instanceof BlockStack) {
				int stackIndex = board.getIndexOfBlockStack((BlockStack) cardMove.getOrigin());
				
				int centerX = (int) getRectangleOfBlockStack(stackIndex).getCenterX();
				int centerY = (int) getRectangleOfBlockStack(stackIndex).getCenterY();
				
				cursorEndPosition = new Point(centerX, centerY);
			}
			
		}
		
		startTime = System.currentTimeMillis();
		endTime = startTime + 500;
	}
	
	private void performMove() {
		if (move == null || 
			move.isPossible() == false) {
			return;
		}
			
		if (move instanceof DeckMove) {
			board.applyMove(move);
			
			cursorStartPosition = cursorCurrentPosition;
			cursorEndPosition = cursorCurrentPosition;
			
			move = null;
			
			startTime = System.currentTimeMillis();
			endTime = startTime + 100;
			
		}else if (move instanceof CardMove) {
			CardMove cardMove = (CardMove) move;
			
			draggedCards = board.getCardsInMove(cardMove);
			dragOrigin = cardMove.getOrigin();
			dragDestination = cardMove.getDestination();
			
			if (dragOrigin instanceof Deck) {
				dragStartRectangles = new Rectangle[] {getRectangleOfTopCardOnDeckStack()};
			
			}else if (dragOrigin instanceof TableStack) {
				int stackIndex = board.getIndexOfTableStack((TableStack) dragOrigin);
				int startIndex = ((TableStack) dragOrigin).getNumberOfCardsOnStack() - cardMove.getCount();
				
				dragStartRectangles = new Rectangle[cardMove.getCount()];
				
				for (int j = 0; j < cardMove.getCount(); j++) {
					dragStartRectangles[j] = getRectangleOfCardOnTableStack(stackIndex, startIndex + j);
				}
				
			}else if (dragOrigin instanceof BlockStack) {
				int stackIndex = board.getIndexOfBlockStack((BlockStack) dragOrigin);
				
				dragStartRectangles = new Rectangle[] {getRectangleOfBlockStack(stackIndex)};	
			}
			
			dragCurrentRectangles = dragStartRectangles.clone();
			
			if (dragDestination instanceof TableStack) {
				int stackIndex = board.getIndexOfTableStack((TableStack) dragDestination);
				int startIndex = ((TableStack) dragDestination).getNumberOfCardsOnStack();
				
				dragEndRectangles = new Rectangle[draggedCards.length];
				
				for (int j = 0; j < draggedCards.length; j++) {
					dragEndRectangles[j] = getRectangleOfCardOnTableStack(stackIndex, startIndex + j);
				}
				
			}else if (dragDestination instanceof BlockStack) {
				int stackIndex = board.getIndexOfBlockStack((BlockStack) dragDestination);
				
				dragEndRectangles = new Rectangle[] {getRectangleOfBlockStack(stackIndex)};
			}
			
			board.applyMove(cardMove);
			
			move = null;
			
			int deltaX = (int) (dragEndRectangles[0].getCenterX() - dragStartRectangles[0].getCenterX());
			int deltaY = (int) (dragEndRectangles[0].getCenterY() - dragStartRectangles[0].getCenterY());
			
			cursorStartPosition = cursorCurrentPosition;
			cursorEndPosition = new Point(cursorStartPosition.x + deltaX, 
			                              cursorStartPosition.y + deltaY);
			
			startTime = System.currentTimeMillis();
			endTime = startTime + 500;
		}
	}
	
	//Timer handling:
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() != timer) {
			return;
		}
		
		if (startTime == 0 || endTime == 0 || 
			startTime > endTime) {
			return;
		}
		
		long currentTime = System.currentTimeMillis();
		double linearProgress = 1.0 * (currentTime - startTime) / (endTime - startTime);
		
		if (linearProgress < 0.0) {
			linearProgress = 0.0;
		}
		if (linearProgress > 1.0) {
			linearProgress = 1.0;
		}
		
		double progress = 0.5 * (1.0 - Math.cos(linearProgress * Math.PI));
		
		boolean repaint = false;
		
		if (dragStartRectangles != null && dragEndRectangles != null) {
			for (int i = 0; i < dragCurrentRectangles.length; i++) {
				Rectangle start = dragStartRectangles[i];
				Rectangle end = dragEndRectangles[i];
				
				int x = (int) Math.round(start.x + progress * (end.x - start.x));
				int y = (int) Math.round(start.y + progress * (end.y - start.y));
				int w = (int) Math.round(start.width + progress * (end.width - start.width));
				int h = (int) Math.round(start.height + progress * (end.height - start.height));
				
				dragCurrentRectangles[i] = new Rectangle(x, y, w, h);
			}
			
			repaint = true;
		}
		
		if (cursorStartPosition != null && cursorEndPosition != null) {
			Point start = cursorStartPosition;
			Point end = cursorEndPosition;

			int x = (int) Math.round(start.x + progress * (end.x - start.x));
			int y = (int) Math.round(start.y + progress * (end.y - start.y));
			
			cursorCurrentPosition = new Point(x, y);
			
			repaint = true;
		}
		
		if (repaint) {
			paintImmediately(getBounds());			
		}
		
		if (currentTime >= endTime) {
			draggedCards = null;
			dragOrigin = null;
			dragDestination = null;
			
			dragStartRectangles = null;
			dragCurrentRectangles = null;
			dragEndRectangles = null;
			
			cursorStartPosition = null;
			cursorEndPosition = null;
			
			startTime = 0;
			endTime = 0;
			
			animationEnded();
		}
	}
	
	private void animationEnded() {
		if (board.canFinishGameAutomatically()) {
			int n = 52 - board.getNumberOfCardsOnBlockStacks();

			draggedCards = new Card[n];
			dragOrigin = null;
			dragDestination = null;
			
			dragStartRectangles = new Rectangle[n];
			dragCurrentRectangles = new Rectangle[n];
			dragEndRectangles = new Rectangle[n];
			
			int c = 0;
			
			for (int i = 1; i <= 52; i++) {
				Card card = Card.values()[i];
				
				BoardElement l = board.getLocationOfCard(card);
				
				if (l instanceof BlockStack) continue;
				
				draggedCards[c] = card;
				
				Rectangle r = null;
				
				if (l == board.getDeck()) {
					int index = board.getDeck().getIndexOfCardOnDeckStack(card);
					
					r = getRectangleOfCardOnDeckStack(index);
					
				}else if (l instanceof TableStack) {
					int stackIndex = board.getIndexOfTableStack((TableStack) l);
					int cardIndex = board.getTableStackAtIndex(stackIndex).getIndexOfCard(card);
					
					r = getRectangleOfCardOnTableStack(stackIndex, cardIndex);
				}
				
				dragStartRectangles[c] = r;
				dragCurrentRectangles[c] = r;
				
				c++;
			}
			
			board.finishGameAutomatically();
			
			for (int i = 0; i < n; i++) {
				Card card = draggedCards[i];
				
				BlockStack stack = (BlockStack) board.getLocationOfCard(card);
				int index = board.getIndexOfBlockStack(stack);
				
				dragEndRectangles[i] = getRectangleOfBlockStack(index);
			}
			
			startTime = System.currentTimeMillis();
			endTime = startTime + 500;
			
		}else if (ai != null) {
			if (move == null) {
				move = ai.getNextMove();
				
				if (move != null) {
					prepareForMove();
				}
				
			}else{
				performMove();
			}
		}
	}
	
	//Mouse event handling:
	public void mouseClicked(MouseEvent e) {
		cursorCurrentPosition = e.getPoint();
		
		if (board == null || board.isGameOver()) {
			return;
		}
		
		if (getRectangleOfDeck().contains(e.getPoint())) {
			board.getDeck().turnDeck();
			
			repaint();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		cursorCurrentPosition = e.getPoint();
		
		if (board == null || board.isGameOver() || 
			dragOrigin != null || dragDestination != null) {
			return;
		}
		
		if (getRectangleOfTopCardOnDeckStack().contains(e.getPoint())) {
			startDrag(board.getDeck(), 1);
			
			return;
		}
		
		for (int i = 0; i < 7; i++) {
			int n = board.getTableStackAtIndex(i).getNumberOfCardsOnStack();
			int coveredCards = board.getTableStackAtIndex(i).getNumberOfCoveredCards();
			
			if (n == 0) continue;
			
			for (int j = n - 1; j >= coveredCards; j--) {
				if (getRectangleOfCardOnTableStack(i, j).contains(e.getPoint())) {
					startDrag(board.getTableStackAtIndex(i), n - j);
					
					return;
				}
			}
		}
		
		for (int i = 0; i < 4; i++) {
			int n = board.getBlockStackAtIndex(i).getNumberOfCardsOnStack();
			
			if (n == 0) continue;
			
			if (getRectangleOfBlockStack(i).contains(e.getPoint())) {
				startDrag(board.getBlockStackAtIndex(i), 1);
				
				return;
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		Point oldCursorPosition = cursorCurrentPosition;
		
		cursorCurrentPosition = e.getPoint();
		
		if (board == null || board.isGameOver() || 
			dragOrigin == null || dragDestination != null) {
			return;
		}
		
		for (Rectangle r : dragCurrentRectangles) {
			r.translate(cursorCurrentPosition.x - oldCursorPosition.x, 
			            cursorCurrentPosition.y - oldCursorPosition.y);			
		}
		
		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		Point oldCursorPosition = cursorCurrentPosition;
		
		cursorCurrentPosition = e.getPoint();
		
		if (board == null || dragOrigin == null || dragDestination != null) {
			return;
		}
		
		for (Rectangle r : dragCurrentRectangles) {
			r.translate(cursorCurrentPosition.x - oldCursorPosition.x, 
			            cursorCurrentPosition.y - oldCursorPosition.y);			
		}
		
		for (int i = 0; i < 7; i++) {
			if (board.getTableStackAtIndex(i).canAddCardsToStack(draggedCards) == false) continue;
			
			int n = board.getTableStackAtIndex(i).getNumberOfCardsOnStack();
			
			if (n == 0 && getRectangleOfCardOnTableStack(i, 0).contains(e.getPoint())) {
				endDrag(board.getTableStackAtIndex(i));
				
				return;
				
			}else if (n > 0 && getRectangleOfCardsOnTableStack(i, n - 1, n).contains(e.getPoint())) {
				endDrag(board.getTableStackAtIndex(i));
				
				return;
			}
		}
		
		for (int i = 0; i < 4; i++) {
			if (board.getBlockStackAtIndex(i).canAddCardToStack(draggedCards[0]) == false) continue;
			
			if (getRectangleOfBlockStack(i).contains(e.getPoint())) {
				endDrag(board.getBlockStackAtIndex(i));
				
				return;
			}
		}
		
		endDrag(dragOrigin);
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	//Drag & Drop:
	private void startDrag(BoardElement origin, int count) {
		if (origin == board.getDeck()) {
			draggedCards = new Card[] {board.getDeck().getTopCardOnDeckStack()};
			dragOrigin = origin;
			dragDestination = null;

			dragCurrentRectangles = new Rectangle[] {getRectangleOfTopCardOnDeckStack()};
			
			repaint();
			
			return;
		}
		
		for (int i = 0; i < 7; i++) {
			if (origin != board.getTableStackAtIndex(i)) continue;
			
			int startIndex = board.getTableStackAtIndex(i).getNumberOfCardsOnStack() - count;
			
			draggedCards = board.getTableStackAtIndex(i).getCardsInRange(startIndex, startIndex + count);
			dragOrigin = origin;
			dragDestination = null;
			
			dragCurrentRectangles = new Rectangle[count];
			
			for (int j = 0; j < count; j++) {
				dragCurrentRectangles[j] = getRectangleOfCardOnTableStack(i, startIndex + j);
			}
			
			repaint();
			
			return;
		}
		
		for (int i = 0; i < 4; i++) {
			if (origin != board.getBlockStackAtIndex(i)) continue;
			
			draggedCards = new Card[] {board.getBlockStackAtIndex(i).getTopCard()};
			dragOrigin = origin;
			dragDestination = null;
			
			dragCurrentRectangles = new Rectangle[] {getRectangleOfBlockStack(i)};

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
		
		dragStartRectangles = dragCurrentRectangles.clone();
		
		if (dragDestination == board.getDeck()) {
			dragEndRectangles = new Rectangle[] {getRectangleOfTopCardOnDeckStack()};
		}
		
		for (int i = 0; i < 7; i++) {
			if (dragDestination != board.getTableStackAtIndex(i)) continue;
			
			int startIndex = board.getTableStackAtIndex(i).getNumberOfCardsOnStack() - draggedCards.length;
			
			dragEndRectangles = new Rectangle[draggedCards.length];
			
			for (int j = 0; j < draggedCards.length; j++) {
				dragEndRectangles[j] = getRectangleOfCardOnTableStack(i, startIndex + j);
			}
		}
		
		for (int i = 0; i < 4; i++) {
			if (dragDestination != board.getBlockStackAtIndex(i)) continue;
			
			dragEndRectangles = new Rectangle[] {getRectangleOfBlockStack(i)};
		}
		
		double distance = Math.sqrt(Math.pow(dragEndRectangles[0].x - dragStartRectangles[0].x, 2) + 
		                            Math.pow(dragEndRectangles[0].y - dragStartRectangles[0].y, 2));
		double duration = Math.min(0.25, distance / 500.0);
		
		startTime = System.currentTimeMillis();
		endTime = (long)(startTime + duration * 1000.0);
	}
	
	//Positioning:
	private Rectangle getRectangleOfHeader() {
		int w = getWidth();
		
		return new Rectangle(0, 0, w, 40 + Images.CardH);
	}
	
	private Rectangle getRectangleOfTable() {
		int w = getWidth();
		int h = getHeight();
		
		return new Rectangle(0, 40 + Images.CardH, w, h - 40 - Images.CardH);
	}
	
	private Rectangle getRectangleOfDeck() {
		return new Rectangle(20, 20, Images.CardW, Images.CardH);
	}
	
	private Rectangle getRectangleOfCardOnDeckStack(int index) {
		if (index < 0 || index >= 3) {
			throw new IllegalArgumentException();
		}
		
		return new Rectangle(40 + Images.CardW + index * 20, 20, 
		                     Images.CardW, Images.CardH);
	}
	
	private Rectangle getRectangleOfTopCardOnDeckStack() {
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
	
	private Rectangle getRectangleOfCardOnTableStack(int stackIndex, int cardIndex) {
		if (stackIndex < 0 || stackIndex >= 7 || 
			cardIndex < 0 || cardIndex >= 19) {
			throw new IllegalArgumentException();
		}
		
		int w = getWidth();
		
		int tableStacksWidth = 7 * Images.CardW + 120;
		
		return new Rectangle((w - tableStacksWidth) / 2 + stackIndex * (Images.CardW + 20), 60 + Images.CardH + cardIndex * 20, 
		                     Images.CardW, Images.CardH);
	}

	private Rectangle getRectangleOfCardsOnTableStack(int stackIndex, int cardStartIndex, int cardEndIndex) {
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
	
	private Rectangle getRectangleOfBlockStack(int index) {
		if (index < 0 || index >= 4) {
			throw new IllegalArgumentException();
		}
		
		int w = getWidth();
		
		int blockStacksWidth = 4 * Images.CardW + 60;
		
		return new Rectangle(w - blockStacksWidth - 20 + index * (Images.CardW + 20), 20, 
		                     Images.CardW, Images.CardH);
	}
	
	//Drawing:
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Rectangle header = getRectangleOfHeader();
		Rectangle table = getRectangleOfTable();
		
		g2d.setColor(new Color(0, 191, 0));
		g2d.fillRect(header.x, header.y, 
		             header.width, header.height);
		
		g2d.setColor(new Color(0, 159, 0));
		g2d.fillRect(table.x, table.y, 
		             table.width, table.height);
		
		if (board == null) return;
		
		paintDeck(g2d);
		paintTableStacks(g2d);
		paintBlockStacks(g2d);
		paintDraggedCards(g2d);
		paintCursor(g2d);
	}
	
	private void paintDeck(Graphics2D g2d) {
		Color spaceColor = new Color(127, 127, 127);

		Rectangle deck = getRectangleOfDeck();
		
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
			Rectangle card = getRectangleOfCardOnDeckStack(0);
			
			g2d.drawImage(Images.imageForCard(visibleDeckStackCards[0].ordinal()),
			              card.x, 
			              card.y, 
			              null);
		}
		if (n >= 2) {
			Rectangle card = getRectangleOfCardOnDeckStack(1);
			
			g2d.drawImage(Images.imageForCard(visibleDeckStackCards[1].ordinal()),
			              card.x, 
			              card.y, 
			              null);
		}
		if (n == 3) {
			Rectangle card = getRectangleOfCardOnDeckStack(2);
			
			g2d.drawImage(Images.imageForCard(visibleDeckStackCards[2].ordinal()),
			              card.x, 
			              card.y, 
			              null);
		}
	}
	
	private void paintTableStacks(Graphics2D g2d) {
		Color spaceColor = new Color(127, 127, 127);
		
		for (int i = 0; i < 7; i++) {
			Rectangle stack = getRectangleOfCardOnTableStack(i, 0);
			
			g2d.setColor(spaceColor);
			g2d.drawRoundRect(stack.x, stack.y, 
			                  stack.width, stack.height, 
			                  5, 5);
			
			Card[] stackCards = board.getTableStackAtIndex(i).getStack();
			int coveredCards = board.getTableStackAtIndex(i).getNumberOfCoveredCards();
			
			for (int j = 0; j < stackCards.length; j++) {
				Rectangle card = getRectangleOfCardOnTableStack(i, j);
				
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
			Rectangle stack = getRectangleOfBlockStack(i);
			
			g2d.setColor(spaceColor);
			g2d.drawRoundRect(stack.x, stack.y, 
			                  stack.width, stack.height, 
			                  5, 5);
			
			if (board.isGameOver() == false) {
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
				
			}else{
				Card card = board.getBlockStackAtIndex(i).getTopCard();
				
				if (draggedCards != null) {
					for (Card c : draggedCards) {
						int index = board.getBlockStackAtIndex(i).getIndexOfCard(c);
						
						if (index == -1) {
							continue;
						}else if (index == 0) {
							card = Cards.None;
							break;
						}else if ((card.ordinal() - 1) % 13 > index) {
							card = board.getBlockStackAtIndex(i).getCardAtIndex(index - 1);
						}
					}
				}
				
				if (!Cards.isNone(card)) {
					g2d.drawImage(Images.imageForCard(card.ordinal()), 
					              stack.x, 
					              stack.y, 
					              null);
				}
			}
		}
	}
	
	private void paintDraggedCards(Graphics2D g2d) {
		if (draggedCards == null) return;
		
		for (int i = 0; i < draggedCards.length; i++) {
			g2d.drawImage(Images.imageForCard(draggedCards[i].ordinal()), 
			              dragCurrentRectangles[i].x, 
			              dragCurrentRectangles[i].y, 
			              null);
		}
	}
	
	private void paintCursor(Graphics2D g2d) {
		if (ai == null) return;
		
		Color cursorFillColor = Color.black;
		Color cursorBorderColor = Color.white;
		
		int x = cursorCurrentPosition.x;
		int y = cursorCurrentPosition.y;
		
		GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		
		path.moveTo(x, y);
		path.lineTo(x, y + 15);
		path.lineTo(x + 10.6, y + 10.6);
		path.closePath();
		
		g2d.setColor(cursorFillColor);
		g2d.fill(path);
		g2d.setColor(cursorBorderColor);
		g2d.draw(path);
	}
	
}
