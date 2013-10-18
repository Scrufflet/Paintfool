package com.bonslope.gui;

import com.bonslope.paintfool.Workspace;

public class BarArtist extends Bar {
	
	public BarArtist(Workspace workspace, Brush brush) {
		
		super(workspace, brush);
		
		for(Item item : Item.getItems()) {
			
			addItem(item);
			
		}
		
	}
	
}
