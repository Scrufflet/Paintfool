package com.bonslope.paintfool.world;

import com.bonslope.planned.PRenderable;

public class Generator extends PRenderable {
	
	// Generator options
	public static final int GENERATOR_PEAK_HEIGHT = 39;
	public static final int GENERATOR_FLATNESS = 135;
	public static int GENERATOR_OFFSET = 0;
	public static final int GENERATOR_ROCK_HEIGHT = 100; // Pixels from contour top
	
	public static final int SHADOW_CHANGE_PER_MOVE = 7;
	public static final int SHADOW_MOVE_SIZE = 3;
	
	private PRenderable shadows;
	private PRenderable backdrop;
	
	private int[] contour;
	private int startX;
	private double seed1 = -1, seed2 = -1, seed3 = -1;
	
	public Generator(int width, int height) {
		
		super(width, height);
		
		init();
		
	}
	
	public void init() {
		
		if(GENERATOR_OFFSET == 0)
			GENERATOR_OFFSET = getHeight() / 2; // Set offset to center of screen to get same amount of sky as ground
		
		shadows = new PRenderable(width, height);
		backdrop = new PRenderable(width, height);
		
		contour = new int[width];
		
	}
	
	public void generate(int startX) {
		
		fillAll(Pixel.PIXEL_AIR.getRandomColor());
		
		this.startX = startX;
		
		generateContour();
		generateSoil();
		generateRock();
		generateGrass();
		generateShadows();
		generateBackdrop();
		
	}
	
	public void generateBackdrop() {
		
		for(int x = 0; x < World.REGION_WIDTH; x ++)
			for(int y = 0; y < World.REGION_HEIGHT; y ++) {
				
				Pixel pixel = Pixel.findPixel(getPixel(x, y));
				
				if(pixel == Pixel.PIXEL_SOIL)
					backdrop.setPixel(x, y, Pixel.PIXEL_SOIL.getRandomColor());
				else if(pixel == Pixel.PIXEL_ROCK)
					backdrop.setPixel(x, y, Pixel.PIXEL_ROCK.getRandomColor());
				
			}
		
	}
	
	public void generateSoil() {
		
		for(int x = 0; x < contour.length; x ++)
			for(int y = contour[x]; y < contour[x] + GENERATOR_ROCK_HEIGHT; y ++)
				setPixel(x, y, Pixel.PIXEL_SOIL.getRandomColor());
		
	}
	
	public void generateRock() {
		
		int startY = 0;
		
		for(int x = 0; x < contour.length; x ++) {
			
			startY = contour[x] + GENERATOR_ROCK_HEIGHT;
			
			for(int y = startY; y < getHeight(); y ++)
				setPixel(x, y, Pixel.PIXEL_ROCK.getRandomColor());
			
			// Bottom grass
			if(x % 2 == 0)
				setPixel(x, startY - 1, Pixel.PIXEL_ROCK.getRandomColor());
			
			if((int) (x / 4) % 2 == 0)
				setPixel(x, startY - 1, Pixel.PIXEL_ROCK.getRandomColor());
			
		}
	}
	
	public void generateGrass() {
		
		for(int x = 0; x < contour.length; x ++) {
			
			// Generate base layer
			for(int y = 0; y < 3; y ++)
				setPixel(x, contour[x] - 1 + y, Pixel.PIXEL_GRASS.getRandomColor());
			
			// Add 'straggly' grass
			
			// Bottom grass
			if(x % 2 == 0)
				setPixel(x, contour[x] + 2, Pixel.PIXEL_GRASS.getRandomColor());
			
			if((int) (x / 4) % 2 == 0)
				setPixel(x, contour[x] + 2, Pixel.PIXEL_GRASS.getRandomColor());
			
			// Top grass
			if(x % 2 == 0)
				setPixel(x, contour[x] - 2, Pixel.PIXEL_GRASS.getRandomColor());
			
			if(x / 7 % 2 != 0)
				setPixel(x, contour[x] - 2, Pixel.PIXEL_GRASS.getRandomColor());
			
		}
		
	}
	
	public static void generateShadowColumn(int[] solidColumn, int[] shadowColumn) {
		
		boolean setShadows = true;
		int move = 0, darkness = 0;
		int height = shadowColumn.length;
		
		for(int y = 0; y < height; y ++) {
			
			if(setShadows) {
				
				shadowColumn[y] = darkness;
				
				if(move == Generator.SHADOW_MOVE_SIZE) {
					
					move = 0;
					darkness -= SHADOW_CHANGE_PER_MOVE;
					
				}
				
				if(solidColumn[y] != Pixel.PIXEL_AIR.getFirstColor())
					move ++;
				
			}
			
			if(y == height - 1)
				break;
			
		}
		
	}
	
	public void generateShadows() {
		
		for(int x = 0; x < contour.length; x ++) {
			
			int solid[] = new int[getHeight()];
			int shadows[] = new int[getHeight()];
			for(int y = 0; y < getHeight(); y ++)
				solid[y] = getPixel(x, y);
				
			Generator.generateShadowColumn(solid, shadows);
			
			for(int y = 0; y < shadows.length; y ++)
				this.shadows.setPixel(x, y, shadows[y]);
			
		}		
		
	}
	
	public void generateContour() {
		
		for(int x = startX; x < startX + contour.length; x ++) {
			
			double height = GENERATOR_PEAK_HEIGHT / seed1 * Math.sin((float) x / GENERATOR_FLATNESS * seed1 + seed1);
			height += GENERATOR_PEAK_HEIGHT / seed2 * Math.sin((float) x / GENERATOR_FLATNESS * seed2 + seed2);
			height += GENERATOR_PEAK_HEIGHT / seed3 * Math.sin((float) x / GENERATOR_FLATNESS * seed3 + seed3);
			height += GENERATOR_OFFSET;
			
			contour[x - startX] = (int) height;
			
		}
		
	}
	
	public void setSeed(double seed1, double seed2, double seed3) {
		
		this.seed1 = seed1;
		this.seed2 = seed2;
		this.seed3 = seed3;
		
	}
	
	public double[] getSeed() {
		
		return new double[] {seed1, seed2, seed3};
		
	}
	
	public int[] getContour() {
		
		return contour;
		
	}
	
	public void setShadows(PRenderable shadows) {
		
		this.shadows = shadows;
		
	}
	
	public PRenderable getShadows() {
		
		return shadows;
		
	}
	
	public void setBackdrop(PRenderable backdrop) {
		
		this.backdrop = backdrop;
		
	}
	
	public PRenderable getBackdrop() {
		
		return backdrop;
		
	}
	
}
