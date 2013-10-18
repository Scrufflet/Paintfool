/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to move a point around on the screen using a move method structure to allow it walk less than one pixel
 * at a time.
 * 
 */

package com.bonslope.planned;

public class PPointMoving {
	
	private double x = 0, y = 0;
	
	// Constructor with x and y variables to create it
	public PPointMoving(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	// Method to move the point more or less than one pixel at a time
	public void move(double xMove, double yMove) {
		
		// Increase variables holding current amount moved with decimals
		x += xMove;
		y += yMove;
		
	}
	
	// Set x coordinate
	public void setX(double x) {
		
		this.x = x;
		
	}
	
	// Get x coordinate
	public int getX() {
		
		return (int) x;
		
	}
	
	// Set y coordinate
	public void setY(int y) {
		
		this.y = y;
		
	}
	
	// Get y coordinate
	public int getY() {
		
		return (int) y;
		
	}
	
}
