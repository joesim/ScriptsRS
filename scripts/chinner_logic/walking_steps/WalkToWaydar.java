package scripts.chinner_logic.walking_steps;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.chinner_utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class WalkToWaydar implements Task {

	@Override
	public String action() {
		return "Walking to Waydar...";
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public boolean validate() {
		return (Constants.waydarArea.contains(Player.getPosition()));
	}

	@Override
	public void execute() {

		RSNPC[] waydar = NPCs.find(2021);
		if (waydar.length > 0) {
			if (waydar[0].isOnScreen()) {
				AccurateMouse.click(waydar[0], "Travel");
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return (Constants.lumdoArea.contains(Player.getPosition()));
					}
				}, General.random(4000, 5000), Screen.MAIN_SCREEN);
				General.sleep(2000, 3000);
			} else {
				WebWalking.walkTo(waydar[0].getPosition());
			}
		}
		General.sleep(1000, 2000);
	}

}
