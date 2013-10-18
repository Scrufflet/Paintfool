/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * The This class is used by the user to render things on the screen. It handles rendering in a pixel array fairly
 * simple.
 * 
 */

package com.bonslope.planned;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PWorkspace extends PRenderable {
	
	// Screen pixels
	private BufferedImage screen;
	
	// Width and height of workspace
	public int pixelSize;
	
	// Constructor
	public PWorkspace(int w, int h, int pixelSize) {
		
		super(w, h);
		
		this.pixelSize = pixelSize;
		
		initWindow();
		
	}
	
	// Constructor to create object out of PWindow
	public PWorkspace(PWindow window) {
		
		super(window.getW() / window.getPixelSize(), window.getH() / window.getPixelSize());
		
		this.pixelSize = window.getPixelSize();
		
		initWindow(); // Initialize objects
		
	}
	
	private void initWindow() {
		
		// Image to hold all pixels
		screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		
		// Connect image with array
		pixels = ((DataBufferInt) screen.getRaster().getDataBuffer()).getData();
		
	}
	
	// Tick method - for users to overwrite
	protected void tick() { }
	
	// Render method not visible to users
	public void render(Graphics g) {
		
		// Make a call to the method users use to process pixels, before update
		render();
		
		// Update screen by drawing over with screen image
		g.drawImage(screen, 0, 0, null);
		
	}
	
	// Render method visible to user, so that they can process pixels
	protected void render() { }
	
	// Set pixelSize
	public void setPixelSize(int pixelSize) {
		
		this.pixelSize = pixelSize;
		
	}
	
	// Get pixelSize
	public int getPixelSize() {
		
		return pixelSize;
		
	}
	
	/** PIXEL PROCESSING **/
	
	
	
	/** PIXEL PROCESSING **/
	
}
