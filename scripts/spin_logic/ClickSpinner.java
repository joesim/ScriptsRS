package scripts.spin_logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Utils;
import scripts.spin_utilities.Variables;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class ClickSpinner implements Task {

	@Override
	public String action() {
		return "Clicking spinner";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Game.getPlane() == 1 && Inventory.getCount(Variables.BOWSTRING_ID) < 27
				&& Inventory.getCount(Variables.FLAX_ID) > 0);
	}

	@Override
	public void execute() {

		CustomFlaxAB.randomSleep();
		// Antiban------------------------------------------------
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------

		Utils.openDoorBack();

		// Antiban------------------------------------------------
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------
		if (Functions.percentageBool(Variables.P_WAIT_CLICKING_SPINNER)) {
			MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 50),
					new ConditionTime(SleepJoe.sleepHumanDelayOut(Variables.SLEEP_MODIFIER_CLICK_SPIN, 2, 10000)),
					General.random(3, 5), true, 20, false, 10000);
		} else if (Functions.percentageBool(Variables.P_WAIT_CLICKING_SPINNER_2)) {
			MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 50),
					new ConditionTime(SleepJoe.sleepHumanDelayOut(Variables.SLEEP_MODIFIER_CLICK_SPIN, 2, 10000)),
					General.random(3, 5), true, 20, false, 10000);
		} else {
			MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 50),
					new ConditionTime(SleepJoe.sleepHumanDelayOut(Variables.SLEEP_MODIFIER_CLICK_SPIN, 2, 10000)),
					General.random(3, 5), false, 20, false, 10000);
		}
		RSObject spinner = Functions.findNearestId(15, 14889);
		long t = Timing.currentTimeMillis();
		if (Functions.percentageBool(Variables.PERCENT_NOT_ACC_MOUSE_SPINNER)) {
			do { // TODO: good spinner clicking
				if (Interfaces.get(459) != null) {
					break;
				}
				MouseMoveJoe.playMouseFollowObject(spinner, "Spin", 1, null, true, 0, 0, 0, 0, 5);
				CustomFlaxAB.randomSleep();
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				if (Game.getCrosshairState() != 2) {
					SleepJoe.sleepHumanDelay(1, 1, 2000);
				}
			} while (Game.getCrosshairState() != 2 && (Timing.currentTimeMillis() - t) < 10000);
		} else {
			while (!AccurateMouse.click(spinner, "Spin") && (Timing.currentTimeMillis() - t) < 10000
					&& Interfaces.get(459) == null) {
				General.sleep(200, 500);
			}
		}
		if (Interfaces.get(459) != null) {
			return;
		}

		if (Variables.c17.checkCondition()) {
			if (Functions.percentageBool(Variables.P_RECLICK_SPINNER)) {
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return Player.isMoving();
					}
				}, General.random(1000, 2000), Screen.MAIN_SCREEN);
			}
		}

		if (Functions.percentageBool(Variables.PERCENT_HOVER_SPINNER)) {
			MouseMoveJoe.hoverMouse(HoverBox.get(28, 90, 1), Variables.c2c13c14c17, General.random(2, 6), true, 20,
					true, 10000);
		} else {
			long t2 = Timing.currentTimeMillis();
			while (!Variables.c2c13c14c17.checkCondition() && (Timing.currentTimeMillis() - t2) < 10000) {
				General.sleep(200, 500);
			}
		}

		if (Player.getRSPlayer().isInCombat()) {
			MouseMoveJoe.hoverMouse(HoverBox.get(1), Variables.c16, 2, true, 20, true, 10000);
		}

	}

}
