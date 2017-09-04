package scripts.tanner.logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tanner.utilities.Constants;
import scripts.tanner.utilities.Variables;
import scripts.utilities.WalkingJoe;

public class WalkToTanner implements Task {

	@Override
	public String action() {
		return "Walking to tanner...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		RSNPC[] tanner = NPCs.find(Constants.TANNER_ID);
		return (Inventory.getCount(Variables.SELECTED_HIDE) > 0
				&& !(tanner.length>0 && tanner[0].isOnScreen()));
	}

	@Override
	public void execute() {
		WalkingJoe.walkingTo(tileTanner());
		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				General.sleep(120, 200);
				RSNPC[] tanner = NPCs.find(Constants.TANNER_ID);
				return (tanner.length>0 && tanner[0].isOnScreen());
			}
		}, General.random(6000, 10000), Screen.ALL_SCREEN);
	}

	private RSTile tileTanner() {
		int x = 3276;
		int y = 3191;
		int offX = 0;
		int offY = 0;
		return new RSTile(x + offX, y + offY, 0);
	}

}
