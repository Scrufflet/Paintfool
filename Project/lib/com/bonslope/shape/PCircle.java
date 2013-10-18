package com.bonslope.shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bonslope.planned.PCamera;
import com.bonslope.planned.PPoint;
import com.bonslope.planned.PRenderable;

public class PCircle {
	
	public static final int CIRCLE_TYPE_OUTLINED = 0, CIRCLE_TYPE_FILLED = 1;
	
	private List<PPoint> points = new ArrayList<PPoint>();
	
	private int radius;
	
	public PCircle(int radius, int circleType) {
		
		this.radius = radius;
		
		switch(circleType) {
		
		case PCircle.CIRCLE_TYPE_OUTLINED:
			
			init(radius);
			
			break;
			
		case PCircle.CIRCLE_TYPE_FILLED:
			
			init(radius);
			
			points = new FilledCircle(radius * 2 + 1, radius * 2 + 1, points).getPoints();
			
			break;
		
		}
		
	}
	
	public void init(int radius) {
		
		int f = 1 - radius;
		int ddF_x = 1;
		int ddF_y = -2 * radius;
		int x = 0;
		int y = radius;
		int x0 = radius, y0 = radius;
		
		addPoint(x0, y0 + radius);
		addPoint(x0, y0 - radius);
		addPoint(x0 + radius, y0);
		addPoint(x0 - radius, y0);
		
		while(x < y) {

			if(f >= 0) {
				
				y--;
				ddF_y += 2;
				f += ddF_y;
				
			}
			
			x++;
			ddF_x += 2;
			f += ddF_x;
			
			addPoint(x0 + x, y0 + y);
			addPoint(x0 - x, y0 + y);
			addPoint(x0 + x, y0 - y);
			addPoint(x0 - x, y0 - y);
			addPoint(x0 + y, y0 + x);
			addPoint(x0 - y, y0 + x);
			addPoint(x0 + y, y0 - x);
			addPoint(x0 - y, y0 - x);
			
		}
		
	}
	
	public void addPoint(int x, int y) {
		
		boolean canAdd = true;
		
		for(PPoint point : points)
			if(!(point.getX() == x && point.getY() == y))
				canAdd = true;
		
		if(canAdd)
			points.add(new PPoint(x, y));
		
	}
	
	// Render object on top of renderable
	public void render(PRenderable renderable, PCamera camera, int color) {
		
		for(PPoint point : points)
			renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), color);
		
	}
	
	// Render object on top of renderable with random colors
	public void render(PRenderable renderable, PCamera camera, int[] colors) {
		
		for(PPoint point : points)
			renderable.setPixel(point.getX() + camera.getX(), point.getY() + camera.getY(), colors[new Random().nextInt(colors.length)]);
		
	}
	
	public int getRadius() {
		
		return radius;
		
	}
	
	public List<PPoint> getPoints() {
		
		return points;
		
	}
	
	class FilledCircle extends PRenderable {

		public FilledCircle(int width, int height, List<PPoint> points) {
			
			super(width, height);
			
			init(points);
			
		}
		
		public void init(List<PPoint> points) {
			
			for(PPoint point : points)
				setPixel(point.getX(), point.getY(), 0xff0000);
			
			renderFillArea(getWidth() / 2, getHeight() / 2, 0xff0000);
			
		}
		
		public ArrayList<PPoint> getPoints() {
			
			ArrayList<PPoint> toReturn = new ArrayList<PPoint>();
			
			for(int x = 0; x < getWidth(); x ++)
				for(int y = 0; y < getHeight(); y ++)
					if(getPixel(x, y) != 0)
						toReturn.add(new PPoint(x, y));
			
			return toReturn;
			
		}
		
	}
	
}
