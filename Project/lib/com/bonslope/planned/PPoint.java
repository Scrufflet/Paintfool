/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to store x and y of a point on the screen. Its main usage is to mark out points of a shape
 * to render on the screen. But can also be used for cameras and other things.
 * 
 */

package com.bonslope.planned;

public class PPoint {
	
	// X and y variables for point
	private int x, y;
	
	// Constructor
	public PPoint(int x, int y) {
		
		setX(x);
		setY(y);
		
	}
	
	// Set the coordinates of point
	public void setCoordinates(int x, int y) {
		
		setX(x);
		setY(y);
		
	}
	
	// Set x
	public void setX(int x) {
		
		this.x = x;
		
	}
	
	// Get x
	public int getX() {
		
		return x;
		
	}
	
	// Set y
	public void setY(int y) {
		
		this.y = y;
		
	}
	
	// Get y
	public int getY() {
		
		return y;
		
	}
	
}
