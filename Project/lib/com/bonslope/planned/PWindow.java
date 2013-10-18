/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to open up an image that you can easely bind a workspace area to. The PWindow contains a build-in
 * gameloop so that the user does not have to set one up himself.
 * 
 */

package com.bonslope.planned;

import java.applet.*;
import javax.swing.*;

import com.bonslope.planned.control.PCKeys;
import com.bonslope.planned.control.PCMouse;


import java.awt.*;
import java.awt.event.WindowListener;

public class PWindow extends Applet implements Runnable {
	private static final long serialVersionUID = 1L; // Serial version UID for the extension 'Applet'
	
	// Screen look
	private String title;
	
	// Screen sizing
	private int pixelSize;
	private Dimension size; // Screen size
	private Dimension pixel; // Pixel image size
	
	// Screen handling
	public static boolean isRunning = false;
	private boolean showFPS = false;
	private double frameRate = 60.0;
	private int fps = (int) frameRate;
	private int delta;
	private long lastFrame;
	
	public static final int gameLoopRegular = 0, gameLoopAdvanced = 1, gameLoopDeltaDriven = 2;
	private int gameLoopType = gameLoopRegular;
	
	// Rendering
	private Image screenImage;
	
	// Objects
	private JFrame frame;
	private PWorkspace workspace;
	
	// Constructor
	public PWindow(int width, int height, final int pixelSize) {
		
		// Set sizes
		size = new Dimension(width, height);
		this.pixelSize = pixelSize;
		pixel = new Dimension(width / pixelSize, height / pixelSize);
		
		// Set preferred size of window
		setPreferredSize(size);
		
		// Set up a JFrame as the content of the PWindow
		frame = new JFrame();
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		addKeyListener(new PCKeys());
		addMouseListener(new PCMouse());
		addMouseWheelListener(new PCMouse());
		addMouseMotionListener(new PCMouse());
		
	}
	
	// Set game loop type
	public void setGameLoopType(int gameLoopType) {
		
		this.gameLoopType = gameLoopType;
		
	}
	
	// Get game loop type
	public int getGameLoopType() {
		
		return gameLoopType;
		
	}
	
	// Set head title of the window
	public void setTitle(String title) {
		
		this.title = title;
		
		// Update the frame so that user may see the new title
		updateFrame();
		
	}
	
	// Bind a workspace to this window
	public void bindWorkspace(PWorkspace workspace) {
		
		this.workspace = workspace;
		
	}
	
	// Set reliability (RESIZABILITY does not exist) of window
	public void setResizable(boolean bResizable) {
		
		frame.setResizable(bResizable);
		
	}
	
	// Set visibility of fps up in the left-top corner of window
	public void setShowFPS(boolean showFPS) {
		
		this.showFPS = showFPS;
		
	}
	
	// Set frame-rate
	public void setFrameRate(double frameRate) {
		
		this.frameRate = frameRate;
		
	}
	
	// Get frame-rate
	public double getFrameRate() {
		
		return frameRate;
		
	}
	
	// Get pixel-size
	public int getPixelSize(int pixelSize) {
		
		return pixelSize;
		
	}
	
	// Get width of window
	public int getW() {
		
		return size.width;
		
	}
	
	// Get height of window
	public int getH() {
		
		return size.height;
		
	}
	
	// Get width of screen-pixels
	public int getPixelW() {
		
		return pixel.width;
		
	}
	
	// Get height of screen-pixels
	public int getPixelH() {
		
		return pixel.height;
		
	}
	
	// Set pixelSize
	public void setPixelSize(int pixelSize) {
		
		this.pixelSize = pixelSize;
		
	}
	
	// Get pixelSize
	public int getPixelSize() {
		
		return pixelSize;
		
	}
	
	public void addWindowListener(WindowListener wl) {
		
		frame.addWindowListener(wl);
		
	}
	
	// Update frame after setting titles etc. user might have changed
	public void updateFrame() {
		
		frame.setTitle(title);
		
	}
	
	// Set the icon image of frame
	public void setIconImage(Image image) {
		
		frame.setIconImage(image);
		
	}
	
	// Initialize objects needed
	public void init() {
		
		// Create image to hold things rendered on screen
		screenImage = createVolatileImage((int) pixel.getWidth(), (int) pixel.getHeight());
		
	}
	
	// Start up PWindow framework
	public void start() {
		
		// Make sure framework's not already running
		if(!isRunning) {
			
			isRunning = true; // Make sure framework will not be ran again
			
			new Thread(this).start(); // Start gameloop
			
		}
		
		// Inform user that start method has been called
		System.out.println("PWindow was successfully initialized and ran.");
		
	}
	
	// Method to stop the framework and close down PWindow
	public void stop() {
		
		isRunning = false;
		
	}
	
	// Tick method
	public void tick() {
		
		if(workspace != null)
			workspace.tick();
		else {
			
			// Error message if the workspace does not exist
			System.err.println("No PWorkspace has been bound to PWindow.");
			System.err.println("Please bind PWorkspace to PWindow using the 'bindWorkspace(Workspace workspace)' method.");
			System.exit(0); // Close the program
			
		}
		
	}
	
	// Render method called by the main gameloop
	// This method sets up the graphical objects, also calls the workspace the user is using
	public void render() {
		
		Graphics g = screenImage.getGraphics(); // Set up graphics
		
		// Make sure workspace is not null in case the tick check doesn't correctly work
		if(workspace != null)
			workspace.render(g);
		
		// Render fps in the top left corner of the PWindow
		if(showFPS) {
			
			g.setColor(new Color(240, 240, 240));
			g.drawString(fps + "", 5, 15);
		
		}
		
		g.dispose(); // Dispose 'em
		
		// Render image drawn on to the screen
		g = getGraphics();
		
		g.drawImage(screenImage, 0, 0, (int) size.getWidth(), (int) size.getHeight(), null);
		
		g.dispose(); // Dispose 'em again
		
	}
	
	// Run method handles the gameloop - it also takes care of the rendering and ticking of the game
	public void run() {
		
		init();
		
		requestFocus(); //Make sure we get the focus so we can play it straight off
		
		// Set up timing variables for fps calculation
		long lastTi = System.currentTimeMillis();
		long curTi = lastTi;
		long count = 0, countMax = 1000;
		int framesPassed = 0;
		
		// Check for what type of game loop it is
		switch(gameLoopType) {
			
		case gameLoopAdvanced:
			
			// Set variables up for game loop
			long lastTime = System.nanoTime();
			double unprocessed = 0;
			int frames = 0;
			long lastTimer1 = System.currentTimeMillis();
			
			int toTick = 0;
			
			long lastRenderTime = System.nanoTime();
			int min = 999999999;
			int max = 0;
			
			while (isRunning) {
				double nsPerTick = 1000000000.0 / frameRate;
				boolean shouldRender = false;
				
				// Make sure the game is updated the right amount of times by adding to the "update" count
				while (unprocessed >= 1) {
					
					toTick++;
					unprocessed -= 1;
				
				}
				
				int tickCount = toTick;
				
				if (toTick > 0 && toTick < 3)
					tickCount = 1;
				if (toTick > 20)
					toTick = 20;
				
				for (int i = 0; i < tickCount; i++) {
					
					toTick--;
					tick();
					
					shouldRender = true; // If the game is ticking, there will be rendering
				
				}
				
				// Calculate a rendering time
				if (shouldRender) {
					
					frames++;
				
					long renderTime = System.nanoTime();
					int timePassed = (int) (renderTime - lastRenderTime);
					
					if (timePassed < min)
						min = timePassed;
					if (timePassed > max)
						max = timePassed;
					
					lastRenderTime = renderTime;
				
				}
				
				long now = System.nanoTime();
				unprocessed += (now - lastTime) / nsPerTick;
				lastTime = now;
				
				// Render if anything on the screen is changed
				if (shouldRender) {
					
					render();
					
				}
				
				// Calculate the fps
				if (System.currentTimeMillis() - lastTimer1 > 1000) {
					
					lastTimer1 += 1000;
					fps = frames;
					frames = 0;
				
				}
			    
			}
			
			break;
			
		case gameLoopRegular: // Regular "un-fancy" game loop
			
			while(isRunning) {
				
				framesPassed ++;
				
				// Tick 'n render
				tick();
				render();
				
				// Calculate fps
				curTi = System.currentTimeMillis();
				count += curTi - lastTi;
				
				if(count >= countMax) {
					
					fps = framesPassed;
					framesPassed = 0;
					count = 0;
					
				}
				
				lastTi = curTi;
				
				// Sleep game loop over frame rate
				try {
					
					Thread.sleep((int) (1000 / frameRate));
					
				} catch(Exception e) { }
			
			}
			
			break;
			
		case gameLoopDeltaDriven:
			
			while(PWindow.isRunning) {
				
				tick();
				render();
				
				generateDelta();
				
				// Calculate fps
				curTi = System.currentTimeMillis();
				count += curTi - lastTi;
				
				if(count >= countMax) {
					
					fps = framesPassed;
					framesPassed = 0;
					count = 0;
					
				}
				
				lastTi = curTi;
				
				framesPassed ++;
				
			}
			
			break;
			
		}
		
	}
	
	public void generateDelta() {
		
		long time = System.currentTimeMillis();
		delta = (int) (time - lastFrame);
		lastFrame = time;
		
	}
	
	public int getDelta() {
		
		return delta;
		
	}
	
	// Ooooh! I'm a comment you'll never forget. :D
	
}
