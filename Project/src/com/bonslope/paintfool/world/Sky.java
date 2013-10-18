package com.bonslope.paintfool.world;

import com.bonslope.planned.PColor;
import com.bonslope.planned.PModel;

public class Sky extends PModel {
	
	public static double SKY_DAY_CHANGE = 1.5;
	
	public Sky(int width, int height) {
		
		super(width, height);
		
	}
	
	public void generate(int startColor, double change) {
		
		int[] rgb = PColor.hexToRGB(startColor);
		
		for(int y = 0; y < pixels.length; y ++)
			pixels[y] = PColor.changeHex(PColor.rgbToHex(rgb[0], rgb[1], rgb[2]), (int) (y / change));
		
	}
	
}
