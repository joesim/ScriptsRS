package scripts.ge_utilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletcher.utilities.MouseFletch;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;

public class OpenGE implements Task {

	@Override
	public String action() {
		return "Opening GE...";
	}

	@Override
	public int priority() {
		return 1000;
	}

	@Override
	public boolean validate() {
		return GrandExchange.getWindowState() == null;
	}

	@Override
	public void execute() {
		RSNPC[] ge = NPCs.findNearest("Grand Exchange Clerk");
		if (ge.length > 0) {
			if (MouseFletch.clickNPC("Exchange Grand Exchange Clerk", ge)) {
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(1);
						return GrandExchange.getWindowState() != null;
					}
				}, General.random(5000, 10000), Screen.MAIN_SCREEN);

			}
		}
	}

}
