/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to create an entity that moves around. For objects standing still the PModel should be used.
 * 
 */

package com.bonslope.planned;

public class PEntity {
	
	// PModel to hold pixels of entity and PPointMoving to store where model is on the screen
	private PModel model;
	private PPointMoving point;
	
	// Constructor to load up entity with a model, x and y coordinates
	public PEntity(PModel model, int x, int y) {
		
		// Set model to model gotten
		setModel(model);
		
		// Set points depending on coordinates gotten
		point = new PPointMoving(x, y);
		
	}
	
	// Tick method for user to use when programming AI or other features
	public void tick() { }
	
	// Render out entity on the screen
	// Rendering camera onto model
	public void render(PRenderable renderable, PCamera camera) {
		
		int offX = point.getX(), offY = point.getY();
		
		if(camera != null) {
			
			offX -= camera.getX();
			offY -= camera.getY();
			
		}
		
		model.render(renderable, new PCamera(offX, offY));
		
	}
	
	// Set model
	public void setModel(PModel model) {
		
		this.model = model;
		
	}
	
	// Get model
	public PModel getModel() {
		
		return model;
		
	}
	
	public PPointMoving getPoint() {
		
		return point;
		
	}
	
	// Move point any amount of pixels, even below a pixel due to the PPointMoving
	public void move(double xMove, double yMove) {
		
		point.move(xMove, yMove);
		
	}
	
	// Set x coordinate of PPointMoving
	public void setX(int x) {
		
		point.setX(x);
		
	}
	
	// Get x coordinate from PPointMoving
	public int getX() {
		
		return point.getX();
		
	}
	
	// Set y coordinate of PPointMoving
	public void setY(int y) {
		
		point.setY(y);
		
	}
	
	// Get y coordinate from PPointMoving
	public int getY() {
		
		return point.getY();
		
	}
	
	// Get w from entity by model
	public int getWidth() {
		
		return model.getWidth();
		
	}
	
	// Get h from entity by model
	public int getHeight() {
		
		return model.getHeight();
		
	}
	
}
