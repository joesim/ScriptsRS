package scripts.zulrahUtilities;

import org.tribot.api.General;
import org.tribot.api2007.types.RSNPC;

import scripts.Zulrah;
import scripts.utilities.Timer;

public class CheckZulrah implements Runnable {

	// IDs du zulrah présent et du prochain
	public int ID = 0;

	// Condition entree, sortie et si prochain est commencé
	public volatile boolean enter = false;
	public volatile boolean quit = false;
	public volatile boolean nextHasStarted = false;

	// Condition arret
	public volatile boolean finished = false;

	// L'objet zulrah
	public static volatile RSNPC zulrah = null;

	// Timer
	public static Timer tm = new Timer(100000000);
	public static int time = 0;
	
	public CheckZulrah(RSNPC zul, int time){
		zulrah = zul;
		tm = new Timer(1000000000);
		CheckZulrah.time = time;
	}
	
	/**
	 * Stop le thread
	 */
	public void stopMe() {
		finished = true;
	}
	
	public static int getPercentageDone(){
		return (int) (tm.getElapsed()/time);
	}

	@Override
	public void run() {

		while (!finished) {
			if (zulrah != null) {
				
				if (zulrah.getAnimation() == 5073 || zulrah.getAnimation() == 5071 || Zulrah.zulrah.getAnimation()==5069 || Zulrah.zulrah.getAnimation() == 5807 || Zulrah.zulrah.getAnimation() == 5806) {
					enter = true;
				}

				if (enter && zulrah.getAnimation() == 5072) {
					quit = true;
				}
			}
			if (enter && quit){
				if (zulrah != null) {
					if (zulrah.getAnimation() == 5073 || zulrah.getAnimation()==5071) {
						nextHasStarted = true;
					}
				}
			}
			General.sleep(40);
		}

	}

}
