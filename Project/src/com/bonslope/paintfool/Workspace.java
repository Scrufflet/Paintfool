package com.bonslope.paintfool;

import com.bonslope.gui.Bar;
import com.bonslope.gui.BarArtist;
import com.bonslope.gui.Brush;
import com.bonslope.paintfool.world.World;
import com.bonslope.planned.PWindow;
import com.bonslope.planned.PWorkspace;

import java.util.Random;

public class Workspace extends PWorkspace {
	
	// Game settings
	public static final int GAME_SETTING_SURVIVAL = 0, GAME_SETTING_ARTIST = 1;
	public static int GAME_SETTING = Workspace.GAME_SETTING_ARTIST;
	public static Seed GAME_SEED = new Seed(new Random().nextDouble() + "");
	
	public World world;
	private Camera camera;
	private Bar[] bar;
	private Brush brush;
	
	private PWindow window;
	
	public Workspace(PWindow window) {
		
		super(window);
		
		this.window = window;
		
		init();
		
	}
	
	public void init() {
		
		World.REGIONS_HORIZONTAL = getWidth() / World.REGION_WIDTH + 3;
		
		if(World.REGIONS_HORIZONTAL % 2 == 0)
			World.REGIONS_HORIZONTAL += 1;
		
		camera = new Camera(0, 0);
		world = new World();
		brush = new Brush(camera);
		bar = new Bar[] {new Bar(this, brush), new BarArtist(this, brush)};
		
		world.init(camera, this);
		camera.init(window, world);
		brush.init(window, bar, world);
		
	}
	
	public void tick() {
		
		fillAll(0x000000);
		
		camera.tick();
		world.tick();
		bar[Workspace.GAME_SETTING].tick();
		brush.tick();
		
	}
	
	public void render() {
		
		world.render(this);
		brush.render(this);
		bar[Workspace.GAME_SETTING].render(this);
		
	}
	
	public PWindow getWindow() {
		
		return window;
		
	}
	
}
