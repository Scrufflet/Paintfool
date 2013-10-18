package com.bonslope.planned.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class PCKeys implements KeyListener {
	
	// List of keys to call
	public static final int keyA = KeyEvent.VK_A;
	public static final int keyB = KeyEvent.VK_B;
	public static final int keyC = KeyEvent.VK_C;
	public static final int keyD = KeyEvent.VK_D;
	public static final int keyE = KeyEvent.VK_E;
	public static final int keyF = KeyEvent.VK_F;
	public static final int keyG = KeyEvent.VK_G;
	public static final int keyH = KeyEvent.VK_H;
	public static final int keyI = KeyEvent.VK_I;
	public static final int keyJ = KeyEvent.VK_J;
	public static final int keyK = KeyEvent.VK_K;
	public static final int keyL = KeyEvent.VK_L;
	public static final int keyM = KeyEvent.VK_M;
	public static final int keyN = KeyEvent.VK_N;
	public static final int keyO = KeyEvent.VK_O;
	public static final int keyP = KeyEvent.VK_P;
	public static final int keyQ = KeyEvent.VK_Q;
	public static final int keyR = KeyEvent.VK_R;
	public static final int keyS = KeyEvent.VK_S;
	public static final int keyT = KeyEvent.VK_T;
	public static final int keyU = KeyEvent.VK_U;
	public static final int keyV = KeyEvent.VK_V;
	public static final int keyW = KeyEvent.VK_W;
	public static final int keyX = KeyEvent.VK_X;
	public static final int keyY = KeyEvent.VK_Y;
	public static final int keyZ = KeyEvent.VK_Z;
	public static final int key1 = KeyEvent.VK_1;
	public static final int key2 = KeyEvent.VK_2;
	public static final int key3 = KeyEvent.VK_3;
	public static final int key4 = KeyEvent.VK_4;
	public static final int key5 = KeyEvent.VK_5;
	public static final int key6 = KeyEvent.VK_6;
	public static final int key7 = KeyEvent.VK_7;
	public static final int key8 = KeyEvent.VK_8;
	public static final int key9 = KeyEvent.VK_9;
	public static final int key0 = KeyEvent.VK_0;
	public static final int keyF1 = KeyEvent.VK_F1;
	public static final int keyF2 = KeyEvent.VK_F2;
	public static final int keyF3 = KeyEvent.VK_F3;
	public static final int keyF4 = KeyEvent.VK_F4;
	public static final int keyF5 = KeyEvent.VK_F5;
	public static final int keyF6 = KeyEvent.VK_F6;
	public static final int keyF7 = KeyEvent.VK_F7;
	public static final int keyF8 = KeyEvent.VK_F8;
	public static final int keyF9 = KeyEvent.VK_F9;
	public static final int keyF10 = KeyEvent.VK_F10;
	public static final int keyF11 = KeyEvent.VK_F11;
	public static final int keyF12 = KeyEvent.VK_F12;
	public static final int keyUP = KeyEvent.VK_UP;
	public static final int keyDOWN = KeyEvent.VK_DOWN;
	public static final int keyLEFT = KeyEvent.VK_LEFT;
	public static final int keyRIGHT = KeyEvent.VK_RIGHT;
	public static final int keyCAPSLOCK = KeyEvent.VK_CAPS_LOCK;
	public static final int keySHIFT = KeyEvent.VK_SHIFT;
	public static final int keyTAB = KeyEvent.VK_TAB;
	public static final int keySPACE = KeyEvent.VK_SPACE;
	public static final int keyENTER = KeyEvent.VK_ENTER;
	public static final int keyCONTROL = KeyEvent.VK_CONTROL;
	public static final int keyALT = KeyEvent.VK_ALT;
	public static final int keyALTGRAPH = KeyEvent.VK_ALT_GRAPH;
	public static final int keyCOMMA = KeyEvent.VK_COMMA;
	public static final int keyPERIOD = KeyEvent.VK_PERIOD;
	public static final int keyPLUS = KeyEvent.VK_PLUS;
	public static final int keyMINUS = KeyEvent.VK_MINUS;
	public static final int keyASTERISK = KeyEvent.VK_ASTERISK;
	public static final int keyBACKSLASH = KeyEvent.VK_BACK_SLASH;
	public static final int keyBACKSPACE = KeyEvent.VK_BACK_SPACE;
	public static final int keyGREATER = KeyEvent.VK_GREATER;
	public static final int keyLESS = KeyEvent.VK_LESS;

	private static boolean[] keys = new boolean[256];
	
	private static List<PCActionKeys> actionKeys = new ArrayList<PCActionKeys>();
	
	public static void bindActionKeys(PCActionKeys myActionKeys) {
		
		actionKeys.add(myActionKeys);
		
	}
	
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		// Make sure key pressed is inside key array bounds, then make true
		if(key < keys.length)
			keys[key] = true;
		
		// Loop through actionKeys array to see if user has any ActionKeys installed
		if(actionKeys.size() != 0) {
			
			for(PCActionKeys actionKeys : PCKeys.actionKeys) {
				
				actionKeys.keyPressed(key);
				
			}
			
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		// Make sure key released is inside key array bounds, then make false
		if(key < keys.length)
			keys[key] = false;
		
		// Loop through actionKeys array to see if user has any ActionKeys installed
		if(actionKeys.size() != 0) {
			
			for(PCActionKeys actionKeys : PCKeys.actionKeys) {
				
				actionKeys.keyPressed(key);
				
			}
			
		}
		
	}
	
	public void keyTyped(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		// Loop through actionKeys array to see if user has any ActionKeys installed
		if(actionKeys.size() != 0) {
			
			for(PCActionKeys actionKeys : PCKeys.actionKeys) {
				
				actionKeys.keyPressed(key);
				
			}
			
		}
		
	}
	
	public static boolean getKey(int keyId) {
		
		return keyId < keys.length && keys[keyId];
		
	}
	
	public static void setKey(int keyId, boolean state) {
		
		if(keyId < keys.length)
			keys[keyId] = state;
		
	}
	
}
