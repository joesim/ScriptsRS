package scripts.utilities;

import java.awt.event.KeyEvent;

import org.tribot.api.input.Keyboard;

public class KeyboardThread implements Runnable {

	private int tabNumber;
	private double multiplier;
	
	public KeyboardThread(int tab, double mul){
		tabNumber = tab;
		multiplier = mul;
	}
	
	@Override
	public void run() {
		
		SleepJoe sl = new SleepJoe();
		Keyboard.sendPress(KeyEvent.CHAR_UNDEFINED, tabNumber);
		sl.sleepHumanDelay(0.2, (int) (30 * multiplier), (int) (160 * multiplier));
		Keyboard.sendRelease(KeyEvent.CHAR_UNDEFINED, tabNumber);
		
	}

}
