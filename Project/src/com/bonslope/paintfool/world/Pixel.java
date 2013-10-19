package com.bonslope.paintfool.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bonslope.gui.Item;

public class Pixel {
	
	private static List<Pixel> pixels = new ArrayList<Pixel>(); // List containing all pixels
	
	public static List<Pixel> getPixels() {
		
		return pixels;
		
	}
	
	// Static pixels
	public static Pixel PIXEL_AIR = new Pixel().addToList().setColors(new int[] {0x000000}).setName("AIR");
	public static Pixel PIXEL_SOIL = new Pixel().addToList().setColors(new int[] {0x8b6a47, 0x8a6741, 0x89653f}).setName("SOIL");
	public static Pixel PIXEL_GRASS = new Pixel().addToList().setColors(new int[] {0x6aae00, 0x66a800, 0x63a300}).setName("GRASS");
	public static Pixel PIXEL_ROCK = new Pixel().addToList().setColors(new int[] {0xa8a7a3, 0xa3a29d, 0xa09f9a}).setName("ROCK");
	public static Pixel PIXEL_SAND = new Pixel().addToList().setColors(new int[] {0xe4c494, 0xdfbf8f, 0xdbbc8d}).setName("SAND");
	
	public static Pixel findPixel(String name) {
		
		// Go through all pixels and find pixel with matching name
		for(Pixel pixel : pixels)
			if(pixel.getName().equalsIgnoreCase(name))
				return pixel;
		
		return null;
		
	}
	
	public static Pixel findPixel(int color) {
		
		for(Pixel pixel : pixels)
			for(int i = 0; i < pixel.getColors().length; i ++)
				if(pixel.getColors()[i] == color)
					return pixel;
		
		return Pixel.PIXEL_AIR;
		
	}
	
	// Private for every pixel
	private String name;
	private int[] colors;
	
	public Pixel() {
		
		
		
	}
	
	public Pixel addToList() {
		
		Pixel.pixels.add(this);
		
		return this;
		
	}
	
	public Pixel setColors(int[] color) {
		
		this.colors = color;
		
		return this;
		
	}
	
	public int[] getColors() {
		
		return colors;
		
	}
	
	public int getRandomColor() {
		
		return colors[new Random().nextInt(colors.length)];
		
	}
	
	public int getFirstColor() {
		
		return colors[0];
		
	}
	
	public Pixel setName(String name) {
		
		this.name = name;
		
		return this;
		
	}
	
	public String getName() {
		
		return name;
		
	}
	
	public Item toItem() {
		
		return Item.findItem(name);
		
	}
	
}
