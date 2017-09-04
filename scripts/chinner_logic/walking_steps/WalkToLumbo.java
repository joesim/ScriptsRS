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

public class WalkToLumbo implements Task {

	@Override
	public String action() {
		return "Walking to Lumdo...";
	}

	@Override
	public int priority() {
		return 4;
	}

	@Override
	public boolean validate() {
		return (Constants.lumdoArea.contains(Player.getPosition()));
	}

	@Override
	public void execute() {
		RSNPC[] lumdo = NPCs.find(2022);
		if (lumdo.length > 0) {
			if (lumdo[0].isOnScreen()) {
				AccurateMouse.click(lumdo[0], "Travel");
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return (Constants.apeAtollArea.contains(Player.getPosition()));
					}
				}, General.random(4000, 5000), Screen.MINIMAP);
				General.sleep(1000, 2000);
			} else {
				WebWalking.walkTo(lumdo[0].getPosition());
			}
		}
		General.sleep(1000, 2000);
	}

}
