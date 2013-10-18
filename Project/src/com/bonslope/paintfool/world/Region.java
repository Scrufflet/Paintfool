package com.bonslope.paintfool.world;

import com.bonslope.planned.PCamera;
import com.bonslope.planned.PColor;
import com.bonslope.planned.PRenderable;

public class Region extends PRenderable {
	
	private PRenderable shadows;
	
	private int x, y;
	private int position;
	
	private boolean changed = false;
	
	private Generator generator;
	
	public Region(int x, int y, int width, int height, int position, Generator generator) {
		
		super(width, height);
		
		this.x = x;
		this.y = y;
		this.setPosition(position);
		this.generator = generator;
		
		shadows = new PRenderable(getWidth(), getHeight());
		
		generate();
		
	}
	
	public void generate() {
		
		generator.generate(x);
		setPixels(generator.getPixels());
		shadows.setPixels(generator.getShadows().getPixels());
		
	}
	
	public void tick() {
		
		
		
	}
	
	public void render(PRenderable renderable, PCamera camera) {
		int cameraX = x, cameraY = y;
		
		// Make sure camera has been inserted, otherwise set to 0, 0
		if(camera != null) {
			
			cameraX -= camera.getX();
			cameraY -= camera.getY();
			
		}
		
		// Render out model pixels by camera position
		for(int x = 0; x < getWidth(); x ++) {
			
			int pixelX = x + cameraX;
			
			if(pixelX < 0 && pixelX >= renderable.getWidth())
				continue;
			
			for(int y = 0; y < getHeight(); y ++) {
				
				int pixelY = y + cameraY;
				
				if(pixelY < 0 || pixelY >= renderable.getHeight())
					continue;
				
				if(getPixels()[y * getWidth() + x] != 0)
					renderable.setPixel(pixelX, pixelY, PColor.changeHex(getPixels()[y * getWidth() + x], shadows.getPixels()[y * getWidth() + x]));
				
				
			}
		}
		
	}
	
	public void setX(int x) {
		
		this.x = x;
		
	}
	
	public int getX() {
		
		return x;
		
	}
	
	public void setY(int y) {
		
		this.y = y;
		
	}
	
	public int getY() {
		
		return y;
		
	}
	
	public void setPosition(int position) {
		
		this.position = position;
		
	}
	
	public int getPosition() {
		
		return position;
		
	}
	
	public void setChanged(boolean changed) {
		
		this.changed = changed;
		
	}
	
	public boolean getChanged() {
		
		return changed;
		
	}
	
	public void setShadows(PRenderable shadows) {
		
		this.shadows = shadows;
		
	}
	
	public PRenderable getShadows() {
		
		return shadows;
		
	}
	
	public void setShadowPixel(int x, int y, int color) {
		
		shadows.setPixel(x, y, color);
		
	}
	
	public int getShadowPixel(int x, int y) {
		
		return shadows.getPixel(x, y);
		
	}
	
}
