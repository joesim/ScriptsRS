package scripts.spinner_tribot.spinner_logic;

import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Utils;
import scripts.spin_utilities.Variables;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class ClickMMStairs implements Task {

	@Override
	public String action() {
		// TODO Auto-generated method stub
		return "Clicking minimap stairs";
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean validate() {
		return (Game.getPlane()==2 && Inventory.getCount(Variables.FLAX_ID)==28);
	}

	@Override
	public void execute() {
		CustomFlaxAB.randomSleep();

		Utils.clickMinimap(new RSTile(3206, 3210, 2));

		// Antiban------------------------------------------------
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------

		Variables.stairsTopLumb = Utils.findGoodStairs();
		MouseMoveJoe.hoverMouse(HoverBox.get(32, 90, 1), Variables.c7, 2, true, 30, false,10000);
		long t = Timing.currentTimeMillis();
		do { // TODO: good stairs clicking
			MouseMoveJoe.playMouseFollowObject(Variables.stairsTopLumb, "Climb-down", 2, null, false, 10, 0, 0, 0, 10);
			CustomFlaxAB.randomSleep();
			MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			if (Game.getCrosshairState() != 2) {
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}
		} while (Game.getCrosshairState() != 2 && (Timing.currentTimeMillis()-t)<10000);
		MouseMoveJoe.hoverMouse(HoverBox.get(35), Variables.c1, 2, false, 20, true,10000);
		CustomFlaxAB.randomSleep();
	}
	
}
