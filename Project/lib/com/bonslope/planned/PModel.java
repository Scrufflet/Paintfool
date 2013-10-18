/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to store pixel data that user can render out
 * on the screen. You can load pixels from an image or create your
 * own pixels.
 * 
 */

package com.bonslope.planned;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class PModel extends PRenderable {
	
	private int mask = 0;
	
	// Constructor to load model, also cut out transparent background
	public PModel(String imagePath, int mask) {
		
		super(0, 0);
		
		this.mask = mask;
		
		// Create a temporary bufferedImage to load image into
		BufferedImage temp = null;
		
		URL url = PModel.class.getResource(imagePath); // URL path to image file
		
		try { // Attempt to load an image from path
			
			temp = ImageIO.read(url);
			
			// Set w and h of model depending on image w and h
			width = temp.getWidth();
			height = temp.getHeight();
			
		} catch(Exception e) { // In case of failure, shut down program and print error
			
			System.err.println("Image for model can't be found.");
			System.exit(0);
			
		}
		
		// Load pixels from image into array
		pixels = new int[width * height];
		temp.getRGB(0, 0, width, height, pixels, 0, width);
		
		// Remove mask and make all pixels positive int rgb
		for(int i = 0; i < pixels.length; i ++) {
			
			pixels[i] += 16777216;
			
			if(pixels[i] == mask)
				pixels[i] = 0;
			
		}
		
	}
	
	// Constructor to load model using another model as template
	public PModel(PModel model) {
		
		super(0, 0);
		
		setPixels(model.pixels);
		setWidth(model.width);
		setHeight(model.height);
		
	}
	
	// Constructor to create empty model
	public PModel(int width, int height) {
		
		super(width, height);
		
	}
	
	// Cut a chunk from model and return new model containing pixels cut out
	public PModel getCutModel(int xPos, int yPos, int w, int h) {
		
		// Create new model out of nothing
		PModel model = new PModel(w, h);
		
		// Loop through new pixels and set them to correct pixels of
		// its own
		for(int x = 0; x < w; x ++) {
			for(int y = 0; y < h; y ++) {
				
				model.pixels[y * w + x] = pixels[(y + yPos) * getWidth() + (x + xPos)];
				
			}
		}
		
		return model;
		
	}
	
	// Render model in workspace
	public void render(PRenderable renderable, PCamera camera) {
		int cameraX = 0, cameraY = 0;
		
		// Make sure camera has been inserted, otherwise set to 0, 0
		if(camera != null) {
			
			cameraX = camera.getX();
			cameraY = camera.getY();
			
		}
		
		// Render out model pixels by camera position
		for(int x = 0; x < getWidth(); x ++) {
			
			int pixelX = x + cameraX;
			
			if(pixelX < 0 && pixelX >= renderable.getWidth())
				continue;
			
			for(int y = 0; y < getHeight(); y ++) {
				
				int pixelY = y + cameraY;
				
				if(pixelY < 0 || pixelY >= renderable.getHeight())
					continue;
				
				if(getPixels()[y * getWidth() + x] != 0)
					renderable.setPixel(pixelX, pixelY, getPixels()[y * getWidth() + x]);
				
			}
		}
		
	}
	
	// Render model in workspace
	public void render(PRenderable renderable, PCamera camera, int color) {
		int cameraX = 0, cameraY = 0;
		
		// Make sure camera has been inserted, otherwise set to 0, 0
		if(camera != null) {
			
			cameraX = camera.getX();
			cameraY = camera.getY();
			
		}
		
		// Render out model pixels by camera position
		for(int x = 0; x < getWidth(); x ++) {
			
			int pixelX = x + cameraX;
			
			if(pixelX < 0 && pixelX >= renderable.getWidth())
				continue;
			
			for(int y = 0; y < getHeight(); y ++) {
				
				int pixelY = y + cameraY;
				
				if(pixelY < 0 || pixelY >= renderable.getHeight())
					continue;
				
				if(getPixels()[y * getWidth() + x] != mask)
					renderable.setPixel(pixelX, pixelY, color);
				
			}
		}
		
	}
		
}
