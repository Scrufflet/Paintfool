package com.bonslope.planned;

import java.util.LinkedList;

public class PRenderable {
	
	protected int[] pixels;
	protected int width, height;
	
	public PRenderable(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		this.pixels = new int[width * height];
		
	}
	
	// Set width of workspace
	public void setWidth(int width) {
		
		this.width = width;
		
	}
	
	// Get width of workspace
	public int getWidth() {
		
		return width;
		
	}
	
	// Set width of workspace
	public void setHeight(int height) {
		
		this.height = height;
		
	}
		
	// Get width of workspace
	public int getHeight() {
		
		return height;
		
	}
	
	public void setPixels(int[] pixels) {
		
		this.pixels = pixels;
		
	}
	
	public int[] getPixels() {
		
		return pixels;
		
	}
	
	// Fill the whole workspace with color given by user
	public void fillAll(int color) {
		
		for(int x = 0; x < getWidth(); x ++) {
			for(int y = 0; y < getHeight(); y ++) {
				
				setPixel(x, y, color);
				
			}
		}
		
	}
	
	// Render single pixel on screen
	public void setPixel(int x, int y, int color) {
		
		if(x >= 0 && x < width && y >= 0 && y < height)
			pixels[width * y + x] = color;
		
	}
	
	public void setPixel(int position, int color) {
		
		pixels[position] = color;
		
	}
	
	public int getPixel(int x, int y) {
		
		return width * y + x >= 0 && width * y + x < pixels.length ? pixels[width * y + x] : 0;
		
	}
	
	// Render pixel on screen using a PPoint
	public void renderPoint(PPoint point, int color) {
		
		setPixel(point.getX(), point.getY(), color);
		
	}
	
	// Render line using Bresenham's line algorithm
	protected void renderLine(int x0, int y0, int x1, int y1, int color) {
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);

		int sx = (x0 < x1) ? 1 : -1;
		int sy = (y0 < y1) ? 1 : -1;

		int err = dx - dy;

		while (true) {
		    setPixel(x0, y0, color);

		    if (x0 == x1 && y0 == y1) {
		        break;
		    }

		    int e2 = 2 * err;

		    if (e2 > -dy) {
		        err = err - dy;
		        x0 = x0 + sx;
		    }

		    if (e2 < dx) {
		        err = err + dx;
		        y0 = y0 + sy;
		    }
		}
		
	}
	
	// Render shape
	public void renderShape(PPoint[] points, int offX, int offY, int color) {
		
		if(points.length < 3) {
			
			System.err.println("Rendering shape requires three or more points.");
			System.exit(0);
			
		}
		
		for(int i = 0; i < points.length - 1; i ++) {
			
			renderLine(points[i].getX() + offX, points[i].getY() + offY, points[i + 1].getX() + offX, points[i + 1].getY() + offY, color);
			
		}
		
		renderLine(points[points.length - 1].getX() + offX, points[points.length - 1].getY() + offY, points[0].getX() + offX, points[0].getY() + offY, color);
		
	}
	
	// Fill an area using the floodIt method
	// It's using a version of the floodfill going through an area of pixels having a certain color
	public void renderFillArea(int x, int y, int color) {
	
		if (pixels[y * width + x] != color) 
			floodIt(pixels, x, y, width, height, pixels[y * width + x], color);
	
	}
	
	// Flood it used by renderFillArea
	private void floodIt(int[] pixels, int x, int y, int width, int height, int oldColor, int fillColor) {

		int[] point = new int[] { x, y };
		LinkedList<int[]> points = new LinkedList<int[]>();
		points.addFirst(point);
		
		while (!points.isEmpty()) {
		
			point = points.remove();
		
			x = point[0];
			y = point[1];
			int xr = x;
			
			int yp = y * width;
			int ypp = yp + width;
			int ypm = yp - width;
		
			do {
			
				pixels[xr + yp] = fillColor;
				xr++;
			
			}
			
			while (xr < width && pixels[xr + y * width] == oldColor);
			
			int xl = x;
			
			do {
			
				pixels[xl + yp] = fillColor;
				xl--;
			
			}
			
			while (xl >= 0 && pixels[xl + y * width] == oldColor);
			
			xr--;
			xl++;
			      
			boolean upLine = false;
			boolean downLine = false;
			
			for (int xi = xl; xi <= xr; xi++) {
				
				if (y > 0 && pixels[xi + ypm] == oldColor && !upLine) {
				
					points.addFirst(new int[] { xi, y - 1 });
					upLine = true;
				
				} else
					upLine = false;
		
				if (y < height - 1 && pixels[xi + ypp] == oldColor && !downLine) {
					
					points.addFirst(new int[] { xi, y + 1 });
					downLine = true;
				
				} else
					downLine = false;
				
			}
		
		}
		
	}
	
	
	
}
