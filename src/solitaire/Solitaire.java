package solitaire;

import java.awt.*;

import javax.swing.*;

import ui.*;

public class Solitaire {
	private static JFrame window;
	private static SolitaireView view;
	
	public static void main(String[] args) {
		setupUI();
	}
	
	private static void setupUI() {
		Images.loadImages();
		
		window = new JFrame("Solitaire");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(640, 500));
		window.setSize(640, 500);
		window.setLayout(new BorderLayout());
		
		view = new SolitaireView();
		
		window.add(view, BorderLayout.CENTER);
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
