package scripts.spinner_tribot.spinner_logic;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Utils;
import scripts.spin_utilities.Variables;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class BankingHandler implements Task {

	@Override
	public String action() {
		return "Banking...";
	}

	@Override
	public int priority() {
		return 2;
	}

	@Override
	public boolean validate() {
		Variables.bankLumb = Functions.findNearestId(20, 18491);
		return (Game.getPlane() == 2 && Variables.bankLumb != null && Variables.bankLumb.isOnScreen()
				&& Inventory.getCount(Variables.FLAX_ID) != 28);
	}

	@Override
	public void execute() {
		// Antiban------------------------------------------------
		CustomFlaxAB.randomSleep();
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------

		if (Functions.percentageBool(Variables.P_CLICK_FIRST_BANK)) {
			Utils.clickBank(Variables.bankLumb, "Bank Bank booth", -20, 0);
		} else {
			RSObject b2 = Objects.findNearest(20, 27291)[0];
			Utils.clickBank(b2, "Bank Bank booth", -26, 0);
		}
		if (Functions.percentageBool(Variables.P_MOUSE_HOVER_DEPOSIT)) {
			MouseMoveJoe.hoverMouse(HoverBox.get(34, 90, 1), Variables.c6c14c19, 2, false, 30, false, 10000);
			if (Functions.percentageBool100000(Variables.P_WAIT_LONGER_DEPOSIT)) {
				MouseMoveJoe.hoverMouse(HoverBox.get(1), new ConditionTime(General.random(1000, 12000)), 2, false, 30,
						false, 10000);
			}
		} else {
			MouseMoveJoe.hoverMouse(HoverBox.get(28, 90, 1), Variables.c6c14c19, 2, false, 30, false, 10000);
			if (Functions.percentageBool100000(Variables.P_WAIT_LONGER_DEPOSIT)) {
				MouseMoveJoe.hoverMouse(HoverBox.get(1), new ConditionTime(General.random(1000, 12000)), 2, false, 30,
						false, 10000);
			}
		}
		while (Variables.c14.checkCondition()) {
			Utils.clickBank(Variables.bankLumb, "Bank Bank booth", -20, 0);
			MouseMoveJoe.hoverMouse(HoverBox.get(34, 90, 1), Variables.c6c14c19, 2, true, 30, false, 10000);
		}

		try {
			/////////////////////////////////////
			CustomFlaxAB.randomSleep();
			Utils.clickDeposit();
			////////////////////////////////////////
			CustomFlaxAB.randomSleep();

			RSItem flax = Functions.findNearestBankItem(1779);
			MouseMoveJoe.humanMouseMove(new TBox(flax.getArea()), 1);
			if (Functions.percentageBool(Variables.P_FAST_WITHDRAW)) {
				Utils.clickKey();
			} else {
				Banking.withdrawItem(flax, 0);
			}
			if (Functions.percentageBool(Variables.P_CLOSE_BANK)) {
				if (Functions.percentageBool(Variables.P_CLOSE_TAB)) {
					Functions.FTAB(27, 1);
				}
			} else {
				Banking.close();
			}

			CustomFlaxAB.randomSleep();

			Utils.clickMinimap(new RSTile(3206, 3210, 2));

			// Antiban------------------------------------------------
			CustomFlaxAB.thingsToCheck();
			// -------------------------------------------------------

			Variables.stairsTopLumb = Utils.findGoodStairs();
			MouseMoveJoe.hoverMouse(HoverBox.get(32, 90, 1), Variables.c7, 2, true, 30, false, 10000);
			do { // TODO: good stairs clicking
				MouseMoveJoe.playMouseFollowObject(Variables.stairsTopLumb, "Climb-down", 2, null, false, 10, 0, 0, 0,
						10);
				CustomFlaxAB.randomSleep();
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				if (Game.getCrosshairState() != 2) {
					SleepJoe.sleepHumanDelay(1, 1, 2000);
				}
			} while (Game.getCrosshairState() != 2);
		} catch (NullPointerException e) {
			if (Banking.isBankScreenOpen()) {
				Banking.close();
				General.sleep(2000, 4000);
			}
		}
	}

}
