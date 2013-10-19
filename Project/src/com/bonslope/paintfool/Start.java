package com.bonslope.paintfool;

import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import com.bonslope.planned.PWindow;

public class Start {
	
	public static final String GAME_TITLE = "Paintfool";
	public static final String GAME_VERSION = "Fruitparty";
	
	public Start() {
		
		PWindow window = new PWindow((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.85), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.85), 4);
		Workspace workspace = new Workspace(window);
		
		window.bindWorkspace(workspace);
		window.setTitle(Start.GAME_TITLE + " " + GAME_VERSION);
		window.setGameLoopType(PWindow.gameLoopDeltaDriven);
		window.setShowFPS(true);
		
		Image temp = null;
		
		try { // Attempt to load an image from path
			
			temp = ImageIO.read(Start.class.getResource("/icon.png"));
			
		} catch(Exception e) { // In case of failure, shut down program and print error
			
			System.err.println("Icon image can't be found.");
			System.exit(0);
			
		}
		
		window.setIconImage(temp);
		
		window.start();
		
	}
	
	public static void main(String[] args) {
		
		new Start(); // Get rid of static reference
		
	}
	
}
