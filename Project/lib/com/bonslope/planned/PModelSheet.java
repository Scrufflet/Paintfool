/**
 * 
 * @author Alexander Hållenius 'ulixava'
 * 
 * This class is used to load up many models using one image for the models.
 * The models can later be used together with the D25 library to create animation
 * objects and units.
 * 
 */

package com.bonslope.planned;

public class PModelSheet {
	
	// Needed variables
	private PModel[][] models; // Array containing all models being loaded from image-sheet
	private int cellW, cellH; // Height and width of each model
	
	// Constructor
	public PModelSheet(String imagePath, int mask, int horCells, int verCells) {
		
		// Loading up a model containing the pixels of all models being created
		PModel model = new PModel(imagePath, mask);
		
		init(model, horCells, verCells);
		
	}
	
	// Constructor to create using a model
	public PModelSheet(PModel model, int horCells, int verCells) {
		
		init(model, horCells, verCells);
		
	}
	
	public void init(PModel model, int horCells, int verCells) {
		
		// Set width and height for each cell
		setCellW(model.getWidth() / horCells);
		setCellH(model.getHeight() / verCells);
		
		// Create the models array from horizontal cells and vertical cells
		models = new PModel[horCells][verCells];
		
		// For loop to loop through all existing models that shall be created
		for(int x = 0; x < models.length; x ++) {
			for(int y = 0; y < models[0].length; y ++) {
				
				// Create models cutting smaller chunks from big model
				models[x][y] = model.getCutModel(x * getCellW(), y * getCellH(), getCellW(), getCellH());
				
			}
		}
		
	}
	
	// Method to draw out full model-sheet to user
	// Used to check and see if images are loaded correctly
	public void render(PWorkspace workspace, PCamera camera) {
		
		int cameraX = 0, cameraY = 0;
		
		if(camera != null) {
			
			cameraX = camera.getX();
			cameraY = camera.getY();
			
		}
		
		// Loop through all images
		for(int x = 0; x < models.length; x ++) {
			for(int y = 0; y < models[x].length; y ++) {
				
				// Render images on the screen using offset variables from user
				models[x][y].render(workspace, new PCamera(x * cellW + cameraX, y * cellH + cameraY));
				
			}
		}
		
	}
	
	// Set cell height
	public void setCellH(int cellH) {
		
		this.cellH = cellH;
		
	}
	
	// Get cell height
	public int getCellH() {
		
		return cellH;
		
	}
	
	// Set cell width
	public void setCellW(int cellW) {
		
		this.cellW = cellW;
		
	}
	
	// Get cell width
	public int getCellW() {
		
		return cellW;
		
	}
	
	public int getHorModels() {
		
		return models.length;
		
	}
	
	public int getVerModels() {
		
		return models[0].length;
		
	}
	
	// Set a specific model
	public void setModel(int xCell, int yCell, PModel model) {
		
		this.models[xCell][yCell] = model;
		
	}
	
	// Get a specific model
	public PModel getModel(int xCell, int yCell) {
		
		return this.models[xCell][yCell];
		
	}
	
}
