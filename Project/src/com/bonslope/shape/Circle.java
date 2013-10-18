package com.bonslope.shape;

import java.util.Random;

import com.bonslope.paintfool.world.Generator;
import com.bonslope.paintfool.world.World;
import com.bonslope.planned.PCamera;
import com.bonslope.planned.PColor;
import com.bonslope.planned.PPoint;
import com.bonslope.planned.PRenderable;

public class Circle extends PCircle {

	public Circle(int radius, int circleType) {
		
		super(radius, circleType);
		
	}
	
	public void render(PRenderable renderable, PCamera camera, int color, boolean overwritePixels, int mask) {
		
		for(PPoint point : getPoints()) {
			
			if(overwritePixels)
				renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), color);
			else if(renderable.getPixel(point.getX() + camera.getX(), point.getY() + camera.getY()) == mask)
				renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), color);
			
			int height = World.REGION_HEIGHT;
			int solid[] = new int[height];
			int shadows[] = new int[height];
			for(int y = 0; y < height; y ++)
				solid[y] = ((World)renderable).getPixel(point.getX() + camera.getX(), y - camera.getY());
				
			Generator.generateShadowColumn(solid, shadows);
			
			for(int y = 0; y < shadows.length; y ++)
				((World)renderable).setShadowPixel(point.getX() + camera.getX(), y - camera.getY(), shadows[y]);
			
		}
	}
	
	// Render object on top of renderable with random colors
	public void render(PRenderable renderable, PCamera camera, int[] colors, boolean overwritePixels, int mask) {
		
		for(PPoint point : getPoints()) {
			
			if(overwritePixels)
				renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), colors[new Random().nextInt(colors.length)]);
			else if(renderable.getPixel(point.getX() + camera.getX(), point.getY() + camera.getY()) == mask)
				renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), colors[new Random().nextInt(colors.length)]);
			System.out.println(renderable.getHeight());
			
			int height = World.REGION_HEIGHT;
			int solid[] = new int[height];
			int shadows[] = new int[height];
			for(int y = 0; y < height; y ++)
				solid[y] = ((World)renderable).getPixel(point.getX() + camera.getX(), y);
				
			Generator.generateShadowColumn(solid, shadows);
			
			for(int y = 0; y < shadows.length; y ++)
				((World)renderable).setShadowPixel(point.getX() + camera.getX(), y, shadows[y]);
			
		}
			
	}
	
	public void renderChanged(PRenderable renderable, PCamera camera, World world, int factor) {
		
		for(PPoint point : getPoints())
			renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), PColor.changeHex(world.getPixelWithShadows(point.getX() + camera.getX(), point.getY() + camera.getY()), factor));
		
	}

}
