package com.bonslope.paintfool;

import com.bonslope.paintfool.world.World;
import com.bonslope.planned.PCamera;
import com.bonslope.planned.PPointMoving;
import com.bonslope.planned.PRenderable;
import com.bonslope.planned.control.PCKeys;
import com.bonslope.planned.PWindow;

public class Camera extends PCamera {
	
	public static double CAMERA_SPEED = .3;
	
	private PWindow window;
	private World world;
	
	private PCamera lastCamera;
	
	public Camera(int x, int y) {
		
		super(x, y);
		
		lastCamera = new PCamera(0, 0);
		
	}
	
	public void init(PWindow window, World world) {
		
		this.window = window;
		this.world = world;
		
	}

	public void tick() {
		
		if(PCKeys.getKey(PCKeys.keyW))
			move(0, -Camera.CAMERA_SPEED * window.getDelta());
		if(PCKeys.getKey(PCKeys.keyD))
			move(Camera.CAMERA_SPEED * window.getDelta(), 0);
		if(PCKeys.getKey(PCKeys.keyS))
			move(0, Camera.CAMERA_SPEED * window.getDelta());
		if(PCKeys.getKey(PCKeys.keyA))
			move(-Camera.CAMERA_SPEED * window.getDelta(), 0);
		
	}
	
	public void centerOnSquare(int x, int y, int width, int height, PRenderable renderable) {
		
		super.centerOnSquare(x, y, width, height, renderable);
		
		lastCamera.setPoint(new PPointMoving(getPoint().getX(), getPoint().getY()));
//		lastCamera.move(renderable.getWidth() / 2, renderable.getHeight() / 2);
//		System.out.println(lastCamera.getX());
		
	}
	
	public void move(double moveX, double moveY) {
		
		super.move(moveX, moveY);
		
		if(getX() - lastCamera.getX() <= -200) {
			
			lastCamera.move(-200, 0);
			world.moveRegion(World.MOVE_REGION_BACK);
			
		} else if(getX() - lastCamera.getX() >= 200) {
			
			lastCamera.move(200, 0);
			world.moveRegion(World.MOVE_REGION_FORWARD);
			
		}
//		System.out.println(getX() - lastCamera.getX());
		
	}
	
}
