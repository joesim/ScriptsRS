package scripts.zulrahUtilities;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Zulrah;
import scripts.utilities.Functions;

public class CheckDeath implements Runnable {

	public static volatile boolean playerDead = false;
	public static volatile boolean zulrahDead = false;
	public volatile boolean finished = false;
	
	public void stopMe(){
		finished = true;
	}
	
	public static void restart(){
		zulrahDead = false;
		playerDead = false;
	}
	
	public void zulNotDead(){
		zulrahDead = false;
	}
	
	public void playerNotDead(){
		playerDead = false;
	}
	
	public CheckDeath(){
		playerDead = false;
		zulrahDead = false;
	}
	
	@Override
	public void run() {
		RSObject zulTele = null;
		RSTile tile1lumb = new RSTile(3218, 3214, 0);
		RSTile tile2lumb = new RSTile(3226, 3222, 0);
		while (!finished) {
			General.sleep(200);
			if (!zulrahDead){
				zulTele = Functions.findNearestId(20, 11701);
			}
			if (zulTele != null) {
				zulrahDead = true;
			}
			if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5804) {
				zulrahDead = true;
			}
			if (Functions.getHealth() == 0) {
				playerDead = true;
			}
			if (Functions.playerOnTiles(Player.getPosition(), tile1lumb, tile2lumb)) {
				playerDead = true;
			}
		}

	}

}
