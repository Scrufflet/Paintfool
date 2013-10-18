package com.bonslope.planned.control;

public interface PCActionMouse {
	
	public abstract void mousePressed(int buttonId);
	
	public abstract void mouseReleased(int buttonId);
	
	public abstract void mouseClicked(int buttonId);
	
	public abstract void mouseEntered(int buttonId);
	
	public abstract void mouseExited(int buttonId);
	
}
