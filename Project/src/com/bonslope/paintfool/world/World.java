package com.bonslope.paintfool.world;

import java.util.ArrayList;
import java.util.List;

import com.bonslope.paintfool.Camera;
import com.bonslope.paintfool.Seed;
import com.bonslope.paintfool.Workspace;
import com.bonslope.planned.PColor;
import com.bonslope.planned.PRenderable;

public class World extends PRenderable {

	public static final Pixel PIXEL_SKY = new Pixel().setColors(new int[] {0x7EC9F7});
	public static Sky SKY;
	
	public static int REGIONS_HORIZONTAL = 7; // Not static because it depends on screen size
	public static final int REGION_WIDTH = 200, REGION_HEIGHT = 500;
	private  List<Region> region = new ArrayList<Region>();
	private Generator generator;
	
	private Camera camera;
	
	public World() {
		
		super(1, 1);
		
	}
	
	public void init(Camera camera, PRenderable renderable) {
		
		SKY = new Sky(1, renderable.getHeight());
		SKY.generate(World.PIXEL_SKY.getFirstColor(), Sky.SKY_DAY_CHANGE);
		
		this.camera = camera;
		
		int steps = Seed.MINIMUM_SEED_LENGTH / 4;
		generator = new Generator(REGION_WIDTH, REGION_HEIGHT);
		generator.setSeed(Double.parseDouble("0." + Workspace.GAME_SEED.getSeed().substring(0, steps)) + 1, Double.parseDouble("0." + Workspace.GAME_SEED.getSeed().substring(steps, steps * 2)) + 2, Double.parseDouble("0." + Workspace.GAME_SEED.getSeed().substring(steps * 2, steps * 3)) + 3);
		
		for(int x = 0; x < REGIONS_HORIZONTAL; x ++) {
			
			Generator gen = new Generator(REGION_WIDTH, REGION_HEIGHT);
			gen.setSeed(generator.getSeed()[0], generator.getSeed()[1], generator.getSeed()[2]);
			region.add(new Region(x * REGION_WIDTH, 0, REGION_WIDTH, REGION_HEIGHT, x, gen));
			
		}
		
		// Set camera to the center of the world
		camera.centerOnSquare(region.get(region.size() / 2).getX(), region.get(region.size() / 2).getY(), REGION_WIDTH, REGION_HEIGHT, renderable);
		
	}
	
	public static final int MOVE_REGION_BACK = 0, MOVE_REGION_FORWARD = 1;
	
	public void moveRegion(int move) {
		
		Region temp = null;
		
		switch(move) {
		
		case MOVE_REGION_BACK:
			
			temp = region.get(region.size() - 1);
			temp.setPosition(region.get(0).getPosition() - 1);
			temp.setX(region.get(0).getX() - REGION_WIDTH);
			temp.generate();
			
			region.remove(region.size() - 1);
			region.add(0, temp);
			
			break;
			
		case MOVE_REGION_FORWARD:
			
			temp = region.get(0);
			temp.setPosition(region.get(region.size() - 1).getPosition() + 1);
			temp.setX(region.get(region.size() - 1).getX() + REGION_WIDTH);
			temp.generate();
			
			region.remove(0);
			region.add(temp);
			
			break;
		
		}
		
	}
	
	// Place out pixel on x and y in region depending on screen coordinates
	public void setPixel(int x, int y, int color) {
		
		int cameraFromBorderX = camera.getX() + x - region.get(0).getX();
		int regionX = cameraFromBorderX - (cameraFromBorderX / World.REGION_WIDTH) * World.REGION_WIDTH;
		y += camera.getY();
		
		region.get(cameraFromBorderX / World.REGION_WIDTH).setPixel(regionX, y, color);
		
	}
	
	// Return pixel from regions depending on screen coordinate
	public int getPixel(int x, int y) {
		
		int cameraFromBorderX = camera.getX() + x - region.get(0).getX();
		int regionX = cameraFromBorderX - (cameraFromBorderX / World.REGION_WIDTH) * World.REGION_WIDTH;
		y += camera.getY();
		
		return region.get(cameraFromBorderX / World.REGION_WIDTH).getPixel(regionX, y);
		
	}
	
	public void setShadowPixel(int x, int y, int color) {
		
		int cameraFromBorderX = camera.getX() + x - region.get(0).getX();
		int regionX = cameraFromBorderX - (cameraFromBorderX / World.REGION_WIDTH) * World.REGION_WIDTH;
		y += camera.getY();
		
		region.get(cameraFromBorderX / World.REGION_WIDTH).setShadowPixel(regionX, y, color);
		
	}
	
	public int getPixelWithShadows(int x, int y) {
		
		int cameraFromBorderX = camera.getX() + x - region.get(0).getX();
		int regionX = cameraFromBorderX - (cameraFromBorderX / World.REGION_WIDTH) * World.REGION_WIDTH;
		y += camera.getY();
		
		return PColor.changeHex(region.get(cameraFromBorderX / World.REGION_WIDTH).getPixel(regionX, y), region.get(cameraFromBorderX / World.REGION_WIDTH).getShadows().getPixel(regionX, y));
		
	}
	
	public void tick() {
		
		
		
	}
	
	public void render(PRenderable renderable) {
		
		for(int x = 0; x < renderable.getWidth(); x ++)
			World.SKY.render(renderable, new Camera(x, 0));
		
		for(int x = 0; x < region.size(); x ++)
			region.get(x).render(renderable, camera);
		
	}
	
}
