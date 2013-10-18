package com.bonslope.planned.control;

public interface PCActionMouseWheel {
	
	// Method called whenever mouse wheel has been scrolled up
	public abstract void mouseWheelScrolledAwayFromUser();
	
	// Method called whenever mouse wheel has been scrolled down
	public abstract void mouseWheelScrolledTowardUser();
	
}
