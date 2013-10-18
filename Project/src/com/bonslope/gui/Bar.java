package com.bonslope.gui;

import com.bonslope.paintfool.Model;
import com.bonslope.paintfool.Workspace;
import com.bonslope.planned.PCamera;
import com.bonslope.planned.PModel;
import com.bonslope.planned.PPoint;
import com.bonslope.planned.PRenderable;
import com.bonslope.planned.control.PCActionMouseWheel;
import com.bonslope.planned.control.PCMouse;

public class Bar extends GUICells implements PCActionMouseWheel {
	
	public static final int BAR_CELLS_VERTICAL = 7;
	public static final int BAR_BACKGROUND_BORDER = 3;
	public static final int CELLS_BETWEEN = 2;
	public static final int CELL_SIZE = 16;
	public static final int CELL_X_OFFSET = BAR_BACKGROUND_BORDER;
	
	public static final int MAX_STACK_SIZE = 999;
	
	private boolean isRotating = false;
	private boolean isRotatingMoving = false;
	private double rotationSpeed = .23;
	private double pixel = 0, pixelsMoved = 0, pixelsMovedDone = CELL_SIZE + CELLS_BETWEEN;
	
	private static final int SCROLLING_TOWARDS_USER = 0, SCROLLING_AWAY_FROM_USER = 1;
	private int lastScrollingDirection = SCROLLING_TOWARDS_USER;
	
	private int cursor;
	
	private static final int DIRECTION_UP = 0, DIRECTION_RIGHT = 1, DIRECTION_DOWN = 2, DIRECTION_LEFT = 3;
	private int[] directions;
	
	private Background background;
	private Workspace workspace;
	private Brush brush;
	
	public Bar(Workspace workspace, Brush brush) {
		
		this.workspace = workspace;
		this.brush = brush;
		
		init();
		
		PCMouse.bindActionMouseWheel(this);
		
	}
	
	public Cell getSelectedCell() {
		
		return cells.get(cursor);
		
	}
	
	public void init() {
		
		// Add all cells
		for(int y = 0; y < Bar.BAR_CELLS_VERTICAL; y ++)
			addCell(new Cell(Model.MODEL_CELL, workspace.getWidth() - Bar.CELL_SIZE - Bar.CELL_X_OFFSET, workspace.getHeight() / 2 - ((Bar.CELL_SIZE + Bar.CELLS_BETWEEN) * Bar.BAR_CELLS_VERTICAL) / 2 + (Bar.CELL_SIZE + Bar.CELLS_BETWEEN) * y + Bar.CELLS_BETWEEN));
		addCell(new Cell(Model.MODEL_CELL, getCells().get(Bar.BAR_CELLS_VERTICAL - 1).getX() + Bar.CELL_SIZE + Bar.CELLS_BETWEEN, getCells().get(Bar.BAR_CELLS_VERTICAL - 1).getY()));
		for(int y = Bar.BAR_CELLS_VERTICAL - 1; y >= 0; y --)
			addCell(new Cell(Model.MODEL_CELL, workspace.getWidth() - Bar.CELL_SIZE - Bar.CELL_X_OFFSET + (CELL_SIZE + CELLS_BETWEEN) * 2, workspace.getHeight() / 2 - ((Bar.CELL_SIZE + Bar.CELLS_BETWEEN) * Bar.BAR_CELLS_VERTICAL) / 2 + (Bar.CELL_SIZE + Bar.CELLS_BETWEEN) * y + Bar.CELLS_BETWEEN));
		addCell(new Cell(Model.MODEL_CELL, getCells().get(0).getX() + Bar.CELL_SIZE + Bar.CELLS_BETWEEN, getCells().get(0).getY()));
		
		cursor = Bar.BAR_CELLS_VERTICAL / 2;
		
		// Calculate and create background image
		int startX = getCells().get(0).getX() - Bar.BAR_BACKGROUND_BORDER,
				startY = getCells().get(0).getY() - Bar.BAR_BACKGROUND_BORDER;
		int endX = getCells().get(Bar.BAR_CELLS_VERTICAL - 1).getX() + Bar.CELL_SIZE + Bar.BAR_BACKGROUND_BORDER,
				endY = getCells().get(Bar.BAR_CELLS_VERTICAL - 1).getY() + Bar.CELL_SIZE + Bar.BAR_BACKGROUND_BORDER;
		
		int width = endX - startX,
				height = endY - startY;
		
		directions = new int[getCells().size()];
		
		background = new Background(width + CELL_SIZE * 2 + CELLS_BETWEEN, height, 0x3FA4E8);
		
	}
	
	public void tick() {
		
		if(workspace.getWindow().getDelta() > 0)
			if(isRotating && !isRotatingMoving) {
				
				isRotatingMoving = true;
				
				switch(lastScrollingDirection) {
				
				case SCROLLING_TOWARDS_USER:
					
					for(int i = 0; i < directions.length; i ++) {
						
						int cell = i - 1;
						
						if(cell < 0)
							cell = directions.length - 1;
						
						if(getCells().get(i).getX() < getCells().get(cell).getX())
							directions[i] = Bar.DIRECTION_RIGHT;
						else if(getCells().get(i).getX() > getCells().get(cell).getX())
							directions[i] = Bar.DIRECTION_LEFT;
						else if(getCells().get(i).getY() < getCells().get(cell).getY())
							directions[i] = Bar.DIRECTION_DOWN;
						else if(getCells().get(i).getY() > getCells().get(cell).getY())
							directions[i] = Bar.DIRECTION_UP;
						
					}
					
					cursor ++;
					
					if(cursor == directions.length)
						cursor = 0;
					
					brush.updateBrush(getSelectedCell().getItem());
					
					break;
					
				case SCROLLING_AWAY_FROM_USER:
					
					for(int i = 0; i < directions.length; i ++) {
						
						int cell = i + 1;
						
						if(cell == directions.length)
							cell = 0;
						
						if(getCells().get(i).getX() < getCells().get(cell).getX())
							directions[i] = Bar.DIRECTION_RIGHT;
						else if(getCells().get(i).getX() > getCells().get(cell).getX())
							directions[i] = Bar.DIRECTION_LEFT;
						else if(getCells().get(i).getY() < getCells().get(cell).getY())
							directions[i] = Bar.DIRECTION_DOWN;
						else if(getCells().get(i).getY() > getCells().get(cell).getY())
							directions[i] = Bar.DIRECTION_UP;
						
					}
					
					cursor --;
					
					if(cursor == -1)
						cursor = directions.length - 1;
					
					brush.updateBrush(getSelectedCell().getItem());
					
					break;
				
				}
				
				isRotating = true;
				
				isRotating = false;
				
			} else if(isRotatingMoving) {
				
				pixel += rotationSpeed * workspace.getWindow().getDelta();
				
				if(pixel >= 1) {
					
					int position = 0;
					
					for(Cell cell : getCells()) {
						
						switch(directions[position]) {
						
						case Bar.DIRECTION_UP:
							
							cell.move(0, -1);
							
							break;
							
						case Bar.DIRECTION_RIGHT:
							
							cell.move(1, 0);
							
							break;
							
						case Bar.DIRECTION_DOWN:
							
							cell.move(0, 1);
							
							break;
							
						case Bar.DIRECTION_LEFT:
							
							cell.move(-1, 0);
							
							break;
						
						}
						
						position ++;
						
					}
					
					pixelsMoved ++;
					pixel = 0;
					
				}
				
				if(pixelsMoved == pixelsMovedDone) {
					
					pixelsMoved = 0;
					isRotatingMoving = false;
					
				}
				
			}
		
	}
	
	public void render(PRenderable renderable) {
		
		background.render(renderable, new PCamera(workspace.getWidth() - Bar.CELL_SIZE - Bar.CELL_X_OFFSET - 3, workspace.getHeight() / 2 - background.getHeight() / 2 + 1));
		
		super.render(renderable);
		
		Model.MODEL_CELL_CURSOR.render(renderable, new PCamera(getCells().get(cursor).getX() - 1, getCells().get(cursor).getY() - 1));
		
	}
	
	// For background parts
	class Background extends PModel {
		
		public Background(int width, int height, int color) {
			
			super(width, height);
			
			init(color);
			
		}
		
		public void init(int color) {
			
			fillAll(color);
			
			// Create rounded corners
			setPixel(0, 0, 0x000000);
			setPixel(0, 1, 0x000000);
			setPixel(1, 0, 0x000000);
			setPixel(0, getHeight() - 1, 0x000000);
			setPixel(0, getHeight() - 2, 0x000000);
			setPixel(1, getHeight() - 1, 0x000000);
			
			setPixel(getWidth() - 1, 0, 0x000000);
			setPixel(getWidth() - 2, 0, 0x000000);
			setPixel(getWidth() - 1, 1, 0x000000);
			setPixel(getWidth() - 1, getHeight() - 1, 0x000000);
			setPixel(getWidth() - 1, getHeight() - 2, 0x000000);
			setPixel(getWidth() - 2, getHeight() - 1, 0x000000);
			
			int width = (Bar.CELL_SIZE + Bar.CELLS_BETWEEN * 2) - Bar.BAR_BACKGROUND_BORDER * 2,
					height = (Bar.CELL_SIZE + Bar.CELLS_BETWEEN) * (Bar.BAR_CELLS_VERTICAL - 2) - Bar.BAR_BACKGROUND_BORDER - Bar.CELLS_BETWEEN;
			renderShape(new PPoint[] {new PPoint(0, 0), new PPoint(width, 0), new PPoint(width, height), new PPoint(0, height)}, Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE, Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE, 0x000000);
			
			renderFillArea(Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE + 2, Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE + 2, 0x000000);
			
			// Set inner corners to color
			setPixel(Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE, Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE, color);
			setPixel(Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE, Bar.BAR_BACKGROUND_BORDER * 2 + Bar.CELL_SIZE + height, color);
			
		}
		
	}
	
	public void mouseWheelScrolledAwayFromUser() {
		
		lastScrollingDirection = Bar.SCROLLING_AWAY_FROM_USER;
		
		isRotating = true;
		
	}
	
	public void mouseWheelScrolledTowardUser() {
		
		lastScrollingDirection = Bar.SCROLLING_TOWARDS_USER;
		
		isRotating = true;
		
	}
	
}
