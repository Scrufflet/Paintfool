package com.bonslope.planned;

public class PFont {
	
	private PModelSheet font;
	private int blankSpace;
	
	private String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ1234567890.,/\\()!?:;-+$*\'";
	private int[] remove;
	
	// Constructor with letter input
	public PFont(PModelSheet font, int blankSpace, String letters) {
		
		this.font = font;
		this.blankSpace = blankSpace;
		this.letters = letters;
		
		init();
		
	}
	
	// Constructor for the lazy bastard! <3
	public PFont(PModelSheet font, int blankSpace) {
		
		this.font = font;
		this.blankSpace = blankSpace;
		
		init();
		
	}
	
	public void init() {
		
		remove = new int[letters.length()];
		
		for(int i = 0; i < remove.length; i ++) {
			
			int pixelsIn = 0;
			
			for(int x = 0; x < font.getCellW(); x ++) {
				
				boolean willBreak = true;
				
				for(int y = 0; y < font.getCellH(); y ++) {
					
					if(font.getModel(i, 0).getPixel(x, y) != 0x000000)
						willBreak = false;
					
				}
				
				if(willBreak) {
					
					pixelsIn = font.getCellW() - x;
					
					break;
					
				}
					
			}
			
			remove[i] = pixelsIn;
//			System.out.println(letters.substring(i, i + 1) + " has " + pixelsIn + " pixels of remove spacing!");
			
		}
		
	}
	
	private int getLetterId(String letter) {
		
		for(int i = 0; i < letters.length(); i ++) {
			
			if(letter.equalsIgnoreCase(letters.substring(i, i + 1)))
				return i;
			
		}
		
		return -1;
		
	}
	
	public int getWidthOf(String text) {
		
		int width = text.length() * (font.getCellW() + blankSpace);
		
		for(int i = 0; i < text.length(); i ++) {
			
			int curLetter = getLetterId(text.substring(i, i + 1));
			
			if(curLetter == -1)
				continue;
			
			width -= remove[curLetter];
			
		}
		
		return width;
		
	}
	
	public void renderString(PRenderable workspace, String text, int offX, int offY) {
		
		for(int i = 0; i < text.length(); i ++) {
			
			int offRemove = 0;
			
			if(!text.substring(i, i + 1).equals(" ")) {
				
				int curLetter = getLetterId(text.substring(i, i + 1));
				
				font.getModel(curLetter, 0).render(workspace, new PCamera(offX, offY));
				offRemove = remove[curLetter];
//				System.out.println(offRemove);
				
			}
			
			offX += font.getCellW() + blankSpace - offRemove;
			
		}
		
	}
	
	public void renderString(PRenderable workspace, String text, int offX, int offY, int color) {
		
		for(int i = 0; i < text.length(); i ++) {
			
			int offRemove = 0;
			
			if(!text.substring(i, i + 1).equals(" ")) {
				
				int curLetter = getLetterId(text.substring(i, i + 1));
				
				font.getModel(curLetter, 0).render(workspace, new PCamera(offX, offY), color);
				offRemove = remove[curLetter];
//				System.out.println(offRemove);
				
			}
			
			offX += font.getCellW() + blankSpace - offRemove;
			
		}
		
	}
	
	public PModel getRenderStringAsModel(String text) {
		
		PModel model = new PModel(getWidthOf(text), font.getCellH());
		
		renderString(model, text, 0, 0);
		
		return model;
		
	}
	
}