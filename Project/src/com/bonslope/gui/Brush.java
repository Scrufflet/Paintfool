package com.bonslope.gui;

import com.bonslope.paintfool.Camera;
import com.bonslope.paintfool.Workspace;
import com.bonslope.paintfool.world.Pixel;
import com.bonslope.paintfool.world.World;
import com.bonslope.planned.PCamera;
import com.bonslope.planned.PPoint;
import com.bonslope.planned.PRenderable;
import com.bonslope.planned.PWindow;
import com.bonslope.planned.control.PCActionMouse;
import com.bonslope.planned.control.PCActionMouseMotion;
import com.bonslope.planned.control.PCMouse;
import com.bonslope.shape.Circle;
import com.bonslope.shape.PCircle;

public class Brush implements PCActionMouseMotion, PCActionMouse {
	
	private PPoint point = new PPoint(0, 0);
	
	private static final int BUTTON_NONE = 0, BUTTON_LEFT = 1, BUTTON_RIGHT = 3;
	private int buttonDown = 0;
	
	private PWindow window;
	private World world;
	
	private int brushRadius = 10;
	private Circle brush;
	private Circle filledBrush;
	
	private Item item = null;
	
	private Camera camera;
	
	public Brush(Camera camera) {
		
		this.camera = camera;
		
	}
	
	public void init(PWindow window, Bar[] bar, World world) {
		
		this.window = window;
		this.world = world;
		
		updateBrush(bar[Workspace.GAME_SETTING].getSelectedCell().getItem());
		
		PCMouse.bindActionMouseMotion(this);
		PCMouse.bindActionMouse(this);
		
	}
	
	public void updateBrush(Item item) {
		
		this.item = item;
		brush = new Circle(brushRadius, PCircle.CIRCLE_TYPE_OUTLINED);
		filledBrush = new Circle(brushRadius, PCircle.CIRCLE_TYPE_FILLED);
		
	}
	
	public void tick() {
		
		if(buttonDown == BUTTON_LEFT) // Build
			filledBrush.render(world, camera, new PCamera(point.getX() - brush.getRadius(), point.getY() - brush.getRadius()), item != null ? item.toPixel().getColors() : Pixel.PIXEL_AIR.getColors(), false, Pixel.PIXEL_AIR.getFirstColor());
		else if(buttonDown == BUTTON_RIGHT) // Destroy
			filledBrush.render(world, camera, new PCamera(point.getX() - brush.getRadius(), point.getY() - brush.getRadius()), Pixel.PIXEL_AIR.getFirstColor(), true, 0);
		
	}
	
	public void render(PRenderable renderable) {
		
		brush.renderChanged(renderable, new PCamera(point.getX() - brush.getRadius(), point.getY() - brush.getRadius()), world, -60);
		
	}
	
	public void setItem(Item item) {
		
		this.item = item;
		
	}
	
	public void setButtonDown(int buttonDown) {
		
		this.buttonDown = buttonDown;
		
	}
	
	public int getButtonDown() {
		
		return buttonDown;
		
	}
	
	public void mouseMoved(PPoint mousePosition) {
		
		point = new PPoint(mousePosition.getX() / window.getPixelSize(), mousePosition.getY() / window.getPixelSize());
		
	}
	
	public void mouseDragged(PPoint mousePosition) {
		
		point = new PPoint(mousePosition.getX() / window.getPixelSize(), mousePosition.getY() / window.getPixelSize());
		
	}
	
	public void mousePressed(int buttonId) {
		
		this.buttonDown = buttonId;
		
	}
	
	public void mouseReleased(int buttonId) {
		
		this.buttonDown = Brush.BUTTON_NONE;
		
	}
	
	public void mouseClicked(int buttonId) {
		
		
		
	}
	
	public void mouseEntered(int buttonId) {
		
		
		
	}
	
	public void mouseExited(int buttonId) {
		
		
		
	}
	
}
