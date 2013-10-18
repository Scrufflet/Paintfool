package com.bonslope.paintfool;

public class Seed {
	
	private String seed;
	public static final int MINIMUM_SEED_LENGTH = 128;
	
	public Seed(String seed) {
		
		this.seed = seed;
		
		correctSeed();
		
	}
	
	private String[] lettersToCorrect = {"AKU-¤[|", "BLV_!&§", "CMW,?%* ", "DNX;=$^", "EOY'<(£~", "FP#Z\")@¨", "GQÅ./{´½", "HRÄT\\}`", "ISÖ:]>J"};
	
	public void correctSeed() {
		
		correctSeedSymbols();
		if(seed.length() < Seed.MINIMUM_SEED_LENGTH) correctSeedLength();
		
	}
	
	public void correctSeedSymbols() {
		
		for(int x = 0; x < seed.length(); x ++)
			for(int i = 0; i < lettersToCorrect.length; i ++)
				if(lettersToCorrect[i].contains(seed.substring(x, x + 1).toUpperCase()))
					seed = (x != 0 ? seed.substring(0, x) : "") + (i + 1) + (x != seed.length() - 1 ? seed.substring(x + 1) : "");
		
	}
	
	public void correctSeedLength() {
		
		while(true) {
			
			String temp = "";
			String full = "";
			
			for(int x = 0; x < seed.length(); x ++) {
				
				int number = -1;
				int cur = Integer.parseInt(seed.substring(x, x + 1));
				
				if(cur < 5)
					number = 1;
				
				temp = temp + (cur + number);
				
			}
			for(int x = 0; x < seed.length(); x ++)
				full = full + seed.substring(x, x + 1) + temp.substring(x, x + 1);
			
			seed = full;
			
			if(seed.length() > Seed.MINIMUM_SEED_LENGTH)
				break;
			
		}
		
	}
	
	public String getSeed() {
		
		return seed;
		
	}
	
}
