package com.bonslope.paintfool;

import com.bonslope.planned.PModel;

public class Model extends PModel {
	
	public static final Model MODEL_CELL = new Model("/gui/cell.png", 0x000000);
	public static final Model MODEL_CELL_CURSOR = new Model("/gui/cell_cursor.png", 0x000000);
	
	public Model(String imagePath, int mask) {
		
		super(imagePath, mask);
		
	}
	
}
