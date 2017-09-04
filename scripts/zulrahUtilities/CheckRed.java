package scripts.zulrahUtilities;

import org.tribot.api.General;

import scripts.Zulrah;

public class CheckRed implements Runnable {


	// Condition arret
	public volatile boolean finished = false;

	public int lastAnim = -1;
	public static volatile boolean shouldMove = false;

	/**
	 * Stop le thread
	 */
	public void stopMe() {
		finished = true;
	}
	
	public void moved(){
		shouldMove = false;
	}

	@Override
	public void run() {

		while (!finished) {
			if (!shouldMove && lastAnim==-1 && (Zulrah.zulrah.getAnimation() == 5807 || Zulrah.zulrah.getAnimation() == 5806)){
				lastAnim = 5806;
				shouldMove = true;
				
			} else if (Zulrah.zulrah.getAnimation() == -1){
				lastAnim = -1;
				shouldMove = false;
			}
			General.sleep(30);
		}

	}

}
