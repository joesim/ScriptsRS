package scripts.tabber.tabber_logic.refillUtilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_utilities.Variables;
import scripts.tabber.utilities.Utils;
import scripts.utilities.Functions;
import scripts.webwalker_logic.WebWalker;

public class WalkToGE implements Task {

	@Override
	public String action() {
		return "Walking to GE...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public void execute() {
		Inventory.open();
		General.sleep(1000, 3000);
		Inventory.find(8007)[0].click("Break");
		Utils.randomSleep();
		
		if (DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				return scripts.tabber.utilities.Constants.varrock.contains(Player.getPosition());
			}
		}, General.random(10000, 20000), Screen.ALL_SCREEN)) {

			if (Functions.percentageBool(20)) {
				WebWalker.walkTo(new RSTile(3198, 3461, 0));
				Utils.randomSleep();
				WebWalker.walkTo(new RSTile(3165, 3486, 0));
			} else {
				WebWalker.walkTo(new RSTile(3165, 3486, 0));
				Utils.randomSleep();
			}
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return !Player.isMoving();
				}
			}, General.random(10000, 20000), Screen.ALL_SCREEN);
		}
	}

}
