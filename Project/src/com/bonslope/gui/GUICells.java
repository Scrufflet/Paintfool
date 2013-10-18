package com.bonslope.gui;

import java.util.ArrayList;
import java.util.List;

import com.bonslope.planned.PRenderable;

public class GUICells {
	
	protected List<Cell> cells = new ArrayList<Cell>();
	
	private boolean visible = true;
	
	public GUICells() {
		
		
		
	}
	
	// Returns true if item was added
	public boolean addItem(Item item) {
		
		boolean add = false;
		
		for(Cell cell : cells) {
			
			if(cell.getItem() == null || cell.getItem() == item)
				add = true;
			
			if(add && cell.getAmount() < Bar.MAX_STACK_SIZE) {
				
				cell.setItem(item);
				cell.changeAmount(1);
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	// Returns true if item was added
	public boolean addItem(Item item, int position) {
		
		boolean add = false;
		
		if(cells.get(position).getItem() == null || cells.get(position).getItem() == item)
			add = true;
		
		if(add) {
			
			cells.get(position).setItem(item);
			cells.get(position).changeAmount(1);
			
			return true;
			
		}
		
		return false;
		
	}
	
	public void render(PRenderable renderable) {
		
		if(!visible)
			return;
		
		for(Cell cell : cells)
			cell.render(renderable);
		
	}
	
	public void addCell(Cell cell) {
		
		cells.add(cell);
		
	}
	
	public void setCells(ArrayList<Cell> cells) {
		
		this.cells = cells;
		
	}
	
	public List<Cell> getCells() {
		
		return cells;
		
	}
	
	public void setVisible(boolean state) {
		
		this.visible = state;
		
	}
	
	public boolean getVisible() {
		
		return visible;
		
	}
	
}
