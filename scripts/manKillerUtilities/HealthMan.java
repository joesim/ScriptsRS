package scripts.manKillerUtilities;

import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.Zulrah;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class HealthMan {
	
	private static final int FOOD_ID  = 333;
	public static int eatPercentage;
	private static ABCUtil util = new ABCUtil();

	public static void loadHealth(){
		eatPercentage = util.generateEatAtHP();
	}

	public static boolean checkHealth() {

		if (eatPercentage >= 100*((double)Functions.getHealth()/(double)Functions.getMaxHealth())) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
			}
			SleepJoe.sleepHumanDelay(1, 1, 2000);
			RSItem trout = Functions.findNearestItemId(FOOD_ID);
			if (trout != null) {
				MouseMoveJoe.humanMouseMove(new TBox(trout.getArea()), Zulrah.SPEED);
				MouseMoveJoe.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				eatPercentage = util.generateEatAtHP();
				return true;
			}
		}
		return false;
	}

	public static boolean getCheckHealth() {

		if (eatPercentage >= 100*((double)Functions.getHealth()/(double)Functions.getMaxHealth())) {
			return true;
		}
		return false;

	}
	
	public static boolean checkIfGotFood(){
		return (Inventory.find(FOOD_ID)!=null);
	}

}
