package solitaire;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ai.*;
import model.Board;
import ui.*;

public class Solitaire {
	private static JFrame window;
	private static BoardView view;
	
	private static JDialog controlDialog;
	private static JButton resetButton;
	private static JComboBox<String> playerComboBox;
	private static JSlider speedSlider;
	
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
		
		view = new BoardView();
		
		window.add(view, BorderLayout.CENTER);
		
		controlDialog = new JDialog();
		controlDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		controlDialog.setResizable(false);
		controlDialog.setSize(new Dimension(250, 150));
		controlDialog.setLayout(null);
		
		resetButton = new JButton("Reset");
		resetButton.setLocation(12, 12);
		resetButton.setSize(226, 25);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				view.setBoard(new Board());
				playerComboBox.setSelectedIndex(0);
			}
		});
		
		playerComboBox = new JComboBox<>(new String[] {"Human", "Random AI", "Basic AI", "ScoreAI", "Look Ahead AI"});
		playerComboBox.setSelectedIndex(0);
		playerComboBox.setLocation(12, 49);
		playerComboBox.setSize(226, 25);
		playerComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (playerComboBox.getSelectedIndex() == 0) {
					view.setAI(null);
				}else if (playerComboBox.getSelectedIndex() == 1) {
					view.setAI(new RandomAI(view.getBoard()));
				}else if (playerComboBox.getSelectedIndex() == 2) {
					view.setAI(new BasicAI(view.getBoard()));
				}else if (playerComboBox.getSelectedIndex() == 3) {
					view.setAI(new ScoreAI(view.getBoard()));
				}else if (playerComboBox.getSelectedIndex() == 4) {
					view.setAI(new LookAheadAI(view.getBoard()));
				}
			}
		});
		
		speedSlider = new JSlider(JSlider.HORIZONTAL, 20, 5000, 500);
		speedSlider.setLocation(12, 86);
		speedSlider.setSize(226, 30);
		speedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				view.setSpeed(speedSlider.getValue());
			}
		});
		
		controlDialog.add(resetButton);
		controlDialog.add(playerComboBox);
		controlDialog.add(speedSlider);
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		controlDialog.setLocation(window.getX() + window.getWidth() + 20, window.getY());
		controlDialog.setVisible(true);
		
		window.requestFocus();
	}
	
}
