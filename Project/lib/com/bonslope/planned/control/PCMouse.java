package com.bonslope.planned.control;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import com.bonslope.planned.PPoint;


public class PCMouse implements MouseListener, MouseWheelListener, MouseMotionListener {
	
	private static PPoint mousePosition = new PPoint(0, 0);
	
	private static List<PCActionMouseWheel> actionMouseWheels = new ArrayList<PCActionMouseWheel>();
	private static List<PCActionMouse> actionMouses = new ArrayList<PCActionMouse>();
	private static List<PCActionMouseMotion> actionMouseMotions = new ArrayList<PCActionMouseMotion>();
	
	public static void bindActionMouseMotion(PCActionMouseMotion myActionMouseMotion) {
		
		actionMouseMotions.add(myActionMouseMotion);
		
	}
	
	public static void bindActionMouse(PCActionMouse myActionMouse) {
		
		actionMouses.add(myActionMouse);
		
	}
	
	public static void bindActionMouseWheel(PCActionMouseWheel myMouseWheelAction) {
		
		actionMouseWheels.add(myMouseWheelAction);
		
	}
	
	public static PPoint getMousePosition() {
		
		return mousePosition;
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		int rotation = e.getWheelRotation();
		
		// Loop through actionMouseWheels array if user has any bound
		if(actionMouseWheels.size() != 0) {
			
			for(PCActionMouseWheel actionMouseWheel : actionMouseWheels) {
			
				if(rotation < 0)
					actionMouseWheel.mouseWheelScrolledAwayFromUser();
				else if(rotation > 0)
					actionMouseWheel.mouseWheelScrolledTowardUser();
			
			}
			
		}
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
		// Loop through actionMouses array if user has any bound
		if(actionMouses.size() != 0) {
			
			for(PCActionMouse actionMouse : actionMouses) {
				
				actionMouse.mouseClicked(e.getButton());
				
			}
			
		}
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
		// Loop through actionMouses array if user has any bound
		if(actionMouses.size() != 0) {
			
			for(PCActionMouse actionMouse : actionMouses) {
				
				actionMouse.mouseEntered(e.getButton());
				
			}
			
		}
		
	}
	
	public void mouseExited(MouseEvent e) {
		
		// Loop through actionMouses array if user has any bound
		if(actionMouses.size() != 0) {
			
			for(PCActionMouse actionMouse : actionMouses) {
				
				actionMouse.mouseExited(e.getButton());
				
			}
			
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
		
		// Loop through actionMouses array if user has any bound
		if(actionMouses.size() != 0) {
			
			for(PCActionMouse actionMouse : actionMouses) {
				
				actionMouse.mousePressed(e.getButton());
				
			}
			
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
		// Loop through actionMouses array if user has any bound
		if(actionMouses.size() != 0) {
			
			for(PCActionMouse actionMouse : actionMouses) {
				
				actionMouse.mouseReleased(e.getButton());
				
			}
			
		}
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
		// Move mouse
		mousePosition = new PPoint(e.getX(), e.getY());
		
		// Loop through actionMouseMotions array if user has any bound
		if(actionMouseMotions.size() != 0) {
			
			for(PCActionMouseMotion actionMouseMotion : actionMouseMotions) {
				
				actionMouseMotion.mouseDragged(mousePosition);
				
			}
			
		}
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
		// Move mouse
		mousePosition = new PPoint(e.getX(), e.getY());
		
		// Loop through actionMouseMotions array if user has any bound
		if(actionMouseMotions.size() != 0) {
			
			for(PCActionMouseMotion actionMouseMotion : actionMouseMotions) {
				
				actionMouseMotion.mouseMoved(mousePosition);
				
			}
			
		}
		
	}
	
}
