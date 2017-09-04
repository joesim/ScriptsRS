package scripts.tabber.utilities;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;

import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.Timer;

public class Utils {

	public static Timer timerButler = null;
	
	public static boolean shouldMakeTeles(){
		return Inventory.getCount(Constants.SOFT_CLAY)>0;
	}
	
	public static void restartTimer(){
		timerButler = new Timer(1000000000);
	}
	
	public static boolean isButlerOut(){
		if (timerButler == null){
			return false;
		}
		return timerButler.getElapsed()<12000;
	}
	
	public static void randomSleep() {
		if (Functions.percentageBool100000(Variables.P_RANDOMSLEEP)) {
			General.sleep(200, 5000);
		}
	}
}
