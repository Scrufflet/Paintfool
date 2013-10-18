package com.bonslope.planned;

public class PColor {
	
	// Static
	public static int rgbToHex(int red, int green, int blue) {
		
		return ((red & 0x0ff) << 16) | ((green & 0x0ff) << 8) | (blue & 0x0ff);
		
	}
	
	public static int[] hexToRGB(int color) {
		
		return new int[] {(color & 0x00ff0000) >> 16, (color & 0x0000ff00) >> 8, color & 0x000000ff};
		
	}
	
	public static int changeHex(int color, int value) {
		
		int[] rgb = hexToRGB(color);
		
		for(int i = 0; i < rgb.length; i ++) {
			
			rgb[i] += value;
			
			if(rgb[i] < 0)
				rgb[i] = 0;
			if(rgb[i] > 255)
				rgb[i] = 255;
			
		}
		
		return rgbToHex(rgb[0], rgb[1], rgb[2]);
		
	}
	
	// Non-static
	private int color;
	
	public PColor(int red, int green, int blue) {
		
		color = rgbToHex(red, green, blue);
		
	}
	
	public PColor(int hex) {
		
		this.color = hex;
		
	}
	
	public void setColor(int hex) {
		
		color = hex;
		
	}
	
	public void setColor(int red, int green, int blue) {
		
		color = rgbToHex(red, green, blue);
		
	}
	
	public int getColor() {
		
		return color;
		
	}
	
}
