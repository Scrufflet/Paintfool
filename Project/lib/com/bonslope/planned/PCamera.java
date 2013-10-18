/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to keep track of a specific point on the screen.
 * Moving or still.
 * 
 */

package com.bonslope.planned;

public class PCamera {
	
	// Point to hold coordinates
	private PPointMoving point;
	
	// Constructor with x and y coordinates to set
	public PCamera(int x, int y) {
		
		setPoint(x, y);
		
	}
	
	// Constructor with PPoint variable to set
	public PCamera(PPointMoving point) {
		
		setPoint(point);
		
	}
	
	// Center camera on point using x and y variables
	public void centerOnPoint(int x, int y, PRenderable renderable) {
		
		setPoint(x - renderable.getWidth() / 2, y - renderable.getHeight() / 2);
		
	}
	
	// Center camera on point using PPoint variable
	public void centerOnPoint(PPoint point, PRenderable renderable) {
		
		centerOnPoint(point.getX(), point.getY(), renderable);
		
	}
	
	// Center camera on square using x, y, w and h variables
	public void centerOnSquare(int x, int y, int w, int h, PRenderable renderable) {
		
		centerOnPoint(x + w / 2, y + h / 2, renderable);
		
	}
	
	// Move point in camera around
	public void move(double xMove, double yMove) {
		
		point.move(xMove, yMove);
		
	}
	
	// Set the point by x and y variables
	public void setPoint(int x, int y) {
		
		point = new PPointMoving(x, y);
		
	}
	
	// Set the point using another point variable
	public void setPoint(PPointMoving point) {
		
		this.point = point;
		
	}
	
	// Get the point
	public PPointMoving getPoint() {
		
		return point;
		
	}
	
	// Set x of point
	public void setX(int x) {
		
		point.setX(x);
		
	}
	
	// Get x from point
	public int getX() {
		
		return point.getX();
		
	}
	
	// Set y of point
	public void setY(int y) {
		
		point.setY(y);
		
	}
	
	// Get y from point
	public int getY() {
		
		return point.getY();
		
	}
	
}
