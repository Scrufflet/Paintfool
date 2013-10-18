package com.bonslope.gui;

import com.bonslope.planned.PCamera;
import com.bonslope.planned.PEntity;
import com.bonslope.planned.PModel;
import com.bonslope.planned.PRenderable;

public class Cell extends PEntity {
	
	private Item item = null;
	private int amount = 0;
	
	public Cell(PModel model, int x, int y) {
		
		super(model, x, y);
		
	}
	
	public void render(PRenderable renderable) {
		
		render(renderable, null);
		
		if(item != null)
			item.render(renderable, new PCamera(getX(), getY()));
		
	}
	
	public void setItem(Item item) {
		
		this.item = item;
		
	}
	
	public Item getItem() {
		
		return item;
		
	}
	
	public void setAmount(int amount) {
		
		this.amount = amount;
		
	}
	
	public void changeAmount(int change) {
		
		this.amount += change;
		
	}
	
	public int getAmount() {
		
		return amount;
		
	}
	
}
