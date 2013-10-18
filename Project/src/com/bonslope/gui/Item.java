package com.bonslope.gui;

import java.util.ArrayList;
import java.util.List;

import com.bonslope.paintfool.world.Pixel;
import com.bonslope.planned.PModel;

public class Item extends PModel {
	
	public static final int ITEM_SIZE = 16;
	
	private static List<Item> items = new ArrayList<Item>(); // List containing all items
	
	public static List<Item> getItems() {
		
		return items;
		
	}
	
	public static final Item ITEM_SHEET = new Item("/gui/item_sheet.png", 0x000000);
	
	public static final Item ITEM_SOIL = new Item(0, 0).addToList().setName("SOIL");
	public static final Item ITEM_GRASS = new Item(1, 0).addToList().setName("GRASS");
	public static final Item ITEM_SAND = new Item(2, 0).addToList().setName("SAND");
	public static final Item ITEM_ROCK = new Item(3, 0).addToList().setName("ROCK");
	
	private String name;
	
	public Item(String imagePath, int mask) {
		
		super(imagePath, mask);
		
	}
	
	// Constructor to cut out item (x, y) of itemSheet
	public Item(int x, int y) {
		
		super(Item.ITEM_SIZE, Item.ITEM_SIZE);
		
		setPixels(Item.ITEM_SHEET.getCutModel(x * Item.ITEM_SIZE, y * Item.ITEM_SIZE, Item.ITEM_SIZE, Item.ITEM_SIZE).getPixels());
		
	}
	
	public Item addToList() {
		
		Item.items.add(this);
		
		return this;
		
	}
	
	public Item setName(String name) {
		
		this.name = name;
		
		return this;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public Pixel toPixel() {
		
		return Pixel.findPixel(name);
		
	}
	
	public static Item findItem(String name) {
		
		for(Item item : items)
			if(item.getName().equalsIgnoreCase(name))
				return item;
		
		return null;
		
	}
	
}
