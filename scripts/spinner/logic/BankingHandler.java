package scripts.spinner.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.Spinner;
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
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;
import scripts.webwalker_logic.shared.helpers.BankHelper;

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
		if (Functions.percentageBool(Variables.P_BANK_1)) {
			Variables.bankLumb = Functions.findNearestId(20, 18491);// 27291
		} else {
			Variables.bankLumb = Functions.findNearestId(20, 27291);// 27291
		}
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
		long t = Timing.currentTimeMillis();
		while (Variables.c14.checkCondition() && (Timing.currentTimeMillis() - t) < 10000) {
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
			if (flax.getStack()<28){
				
				if (Banking.close()){
					General.sleep(2000, 5000);
					Variables.bankLumb.click("Collect Bank booth");
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							return Interfaces.get(402)!=null;
						}
					}, General.random(3000, 5000));
					if (!Interfaces.get(402).getChild(7).getChild(1).click("Collect")){
						Spinner.stop = true;
					}
					Interfaces.get(402).getChild(2).getChild(11).click("Close");
				}
				
				
				return;
			}
			MouseMoveJoe.humanMouseMove(new TBox(flax.getArea()), 1);
			if (Functions.percentageBool(Variables.P_FAST_WITHDRAW)) {
				Utils.clickKey();
			} else {
				Banking.withdrawItem(flax, 0);
			}
			if (Functions.percentageBool(Variables.P_CLOSE_BANK)) {
				if (Functions.percentageBool(Variables.P_CLOSE_TAB)) {
					Functions.FTAB(27, 1);
				} else {
					Banking.close();
				}
			}
			CustomFlaxAB.randomSleep();

			Utils.clickMinimap(new RSTile(3206, 3210, 2));

			// Antiban------------------------------------------------
			CustomFlaxAB.thingsToCheck();
			// -------------------------------------------------------

			Variables.stairsTopLumb = Utils.findGoodStairs();
			MouseMoveJoe.hoverMouse(HoverBox.get(32, Variables.P_M_HOVER_STAIRS, 1), Variables.c7,
					General.randomDouble(2, 4), true, 30, false, 10000);
			if (Inventory.getCount(Variables.FLAX_ID) == 0) {
				return;
			}
			long t2 = Timing.currentTimeMillis();
			do { // TODO: good stairs clicking
				if (Functions.percentageBool(Variables.P_CLICK_TOP_STAIRS)){
				MouseMoveJoe.playMouseFollowObject(Variables.stairsTopLumb, "Climb-down", 2, null, false, 10, 0, 0, 0,
						7);
				CustomFlaxAB.randomSleep();
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				} else {
					if (AccurateMouse.click(Variables.stairsTopLumb, "Climb-down")){
						break;
					}
				}
				if (Game.getCrosshairState() != 2) {
					SleepJoe.sleepHumanDelay(1, 1, 2000);
				}
			} while (Game.getPlane()==2 && Game.getCrosshairState() != 2 && (Timing.currentTimeMillis() - t2) < 10000);
			MouseMoveJoe.hoverMouse(HoverBox.get(35, Variables.P_M_HOVER_SPINNER, 1), Variables.c1,
					General.randomDouble(2, 4), false, 20, true, 10000);
			CustomFlaxAB.randomSleep();
		} catch (NullPointerException e) {
			if (Banking.isBankScreenOpen()) {
				Banking.close();
				General.sleep(2000, 4000);
			}
		}
	}

}
