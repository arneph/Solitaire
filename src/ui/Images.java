package ui;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;

public class Images {
	public static final int CardW = 60;
	public static final int CardH = 75;
	
	public static Image cardBack;
	public static Image[][] cards;
	
	public static Image imageForCard(int n) {
		if (cardBack == null) {
			loadImages();
		}
		
		if (n == 0) {
			return cardBack;
		}else if (1 <= n && n <= 52) {
			return cards[(n - 1) / 13][(n - 1) % 13];
		}else{
			return null;
		}
	}
	
	public static void loadImages() {
		try{
			URL cardBackURL = ClassLoader.getSystemResource("0.png");
			
			cardBack = ImageIO.read(cardBackURL).getScaledInstance(CardW, CardH, Image.SCALE_SMOOTH);
			cards = new Image[4][13];
			
			for (int i = 0; i < 13; i++) {
				URL cURL = ClassLoader.getSystemResource("C-" + (i + 1) + ".png");
				URL dURL = ClassLoader.getSystemResource("D-" + (i + 1) + ".png");
				URL hURL = ClassLoader.getSystemResource("H-" + (i + 1) + ".png");
				URL sURL = ClassLoader.getSystemResource("S-" + (i + 1) + ".png");
				
				cards[0][i] = ImageIO.read(cURL);//.getScaledInstance(CardW, CardH, Image.SCALE_SMOOTH);
				cards[1][i] = ImageIO.read(dURL);//.getScaledInstance(CardW, CardH, Image.SCALE_SMOOTH);
				cards[2][i] = ImageIO.read(hURL);//.getScaledInstance(CardW, CardH, Image.SCALE_SMOOTH);
				cards[3][i] = ImageIO.read(sURL);//.getScaledInstance(CardW, CardH, Image.SCALE_SMOOTH);
			}
		}catch (IOException e) {}
	}
	
}
