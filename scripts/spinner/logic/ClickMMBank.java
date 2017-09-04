package scripts.spinner.logic;

import java.awt.Point;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Utils;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class ClickMMBank implements Task {

	@Override
	public String action() {
		return "Clicking minimap bank...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		Variables.bankLumb = Functions.findNearestId(20, 18491);
		return (Game.getPlane() == 2 && Variables.bankLumb != null && !Variables.bankLumb.isOnScreen()
				&& (Inventory.getCount(Variables.BOWSTRING_ID) >= 27 || Inventory.getCount(Variables.FLAX_ID) != 28));
	}

	@Override
	public void execute() {
		// Antiban------------------------------------------------
		CustomFlaxAB.thingsToCheck();
		CustomFlaxAB.randomSleep();
		// -------------------------------------------------------

		if (Functions.percentageBool(Variables.P_USEMM)) {
			Utils.clickMinimap(new RSTile(3208, 3219, 2));
		} else {
			RSTile tileScreen2 = new RSTile(3206, 3216, 2);
			Point pMM = Projection.tileToScreen(tileScreen2, 0);
			MouseMoveJoe.humanMouseMove(new TBox(pMM, 11), 1.2);
			MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			if (Functions.percentageBool(Variables.P_MULTIPLE_CLICKS_MM)) {
				SleepJoe.sleepHumanDelay(1, 1, 1000);
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				if (Functions.percentageBool(Variables.P_MULTIPLE_CLICKS_MM)) {
					SleepJoe.sleepHumanDelay(1, 1, 1000);
					MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				}
			}
		}
		MouseMoveJoe.hoverMouse(HoverBox.get(31, Variables.P_M_HOVER_BANK, 1), Variables.c5, 2, false, 80, false, 10000);
	}

}
