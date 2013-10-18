package com.bonslope.planned.D25;

import com.bonslope.planned.PCamera;
import com.bonslope.planned.PModelSheet;
import com.bonslope.planned.PPointMoving;
import com.bonslope.planned.PWorkspace;

public class PD25Entity {
	
	// PModel to hold pixels of entity and PPointMoving to store where model is on the screen
	private PModelSheet modelSheet;
	private PPointMoving point;
	
	public static final int directionUpward = 0;
	public static final int directionUpwardRight = 1;
	public static final int directionRight = 2;
	public static final int directionRightDown = 3;
	public static final int directionDownward = 4;
	public static final int directionDownwardLeft = 5;
	public static final int directionLeft = 6;
	public static final int directionLeftUpward = 7;
	
	private int direction = directionDownward;
	private double animation = 0;
	
	// Constructor to load up entity with a model, x and y coordinates
	public PD25Entity(PModelSheet model, int x, int y) {
		
		// Set model to model gotten
		setModelSheet(model);
		
		// Set points depending on coordinates gotten
		point = new PPointMoving(x, y);
		
	}
	
	// Tick method for user to use when programming AI or other features
	public void tick() { }
	
	// Render out entity on the screen
	// Rendering camera onto model
	public void render(PWorkspace workspace, PCamera camera) {
		
		int offX = point.getX(), offY = point.getY();
		
		if(camera != null) {
			
			offX -= camera.getX();
			offY -= camera.getY();
			
		}
		
		modelSheet.getModel((int) animation, direction).render(workspace, new PCamera(offX, offY));
		
	}
	
	// Set model
	public void setModelSheet(PModelSheet modelSheet) {
		
		this.modelSheet = modelSheet;
		
	}
	
	// Get model
	public PModelSheet getModel() {
		
		return modelSheet;
		
	}
	
	// Update animation depending on how player has moved
	public void updateAnimation(double xMove, double yMove) {
		
		if(yMove < 0 && xMove == 0) // Player moved upward
			direction = PD25Entity.directionUpward;
		else if(yMove < 0 && xMove > 0) // PLayer moved upward-right
			direction = PD25Entity.directionUpwardRight;
		else if(yMove == 0 && xMove > 0) // Player moved right
			direction = PD25Entity.directionRight;
		else if(yMove > 0 && xMove > 0) // Player moved right-downward
			direction = PD25Entity.directionRightDown;
		else if(yMove > 0 && xMove == 0) // Player moved downward
			direction = PD25Entity.directionDownward;
		else if(yMove > 0 && xMove < 0) // Player moved downward-left
			direction = PD25Entity.directionDownwardLeft;
		else if(yMove == 0 && xMove < 0) // Player moved left
			direction = PD25Entity.directionLeft;
		else if(yMove < 0 && xMove < 0) // Player moved left-upward
			direction = PD25Entity.directionLeftUpward;
		
	}
	
	// Move point any amount of pixels, even below a pixel due to the PPointMoving
	public void move(double xMove, double yMove) {
		
		point.move(xMove, yMove);
		
		updateAnimation(xMove, yMove);
		
	}
	
	// Move this entity and a camera 'probably' being linked to it
	public void move(double xMove, double yMove, PCamera camera) {
		
		move(xMove, yMove);
		
		camera.move(xMove, yMove);
		
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
	public int getW() {
		
		return modelSheet.getCellW();
		
	}
	
	// Get h from entity by model
	public int getH() {
		
		return modelSheet.getCellH();
		
	}
	
	// Set direction
	public void setDirection(int direction) {
		
		this.direction = direction;
		
	}
	
	// Return direction from PD25Entity
	public int getDirection() {
		
		return direction;
		
	}
	
	// Set animation frame
	public void setAnimation(double animation) {
		
		this.animation = animation;
		
	}
	
	// Return animation from PD25Entity
	public double getAnimation() {
		
		return animation;
		
	}

}
