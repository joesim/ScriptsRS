package scripts.tanner.logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tanner.logic.GEHandler;
import scripts.tanner.utilities.Variables;
import scripts.webwalker_logic.WebWalker;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class Teleport implements Task {

	@Override
	public String action() {
		return "Teleporting...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return GEHandler.shouldRefill;
	}

	@Override
	public void execute() {
		if (BankHelper.isInBank()) {
			if (BankHelper.openBank()) {
				if (DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return Banking.isBankScreenOpen();
					}
				}, General.random(9000, 12000), Screen.MAIN_SCREEN)) {
					General.sleep(500, 3000);
					Mouse.moveBox(268, 312, 321, 326);
					Mouse.click(1);
					General.sleep(100, 1000);
					if (Banking.withdraw(1, "Varrock teleport")) {
						General.sleep(200, 1000);
						if (Banking.withdraw(0, Variables.SELECTED_LEATHER)) {
							General.sleep(200, 1000);
							Banking.close();
							General.sleep(200, 1000);
							Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return !org.tribot.api2007.Banking.isBankScreenOpen();
								}
							}, General.random(3000, 5000));
							if (Inventory.open()) {
								Inventory.find("Varrock teleport")[0].click("Break");
								Timing.waitCondition(new Condition() {
									@Override
									public boolean active() {
										General.sleep(100, 200);
										return Constants.varrock.contains(Player.getPosition());
									}
								}, General.random(10000, 15000));
							}
						}
					}
				}
			}

		} else {
			WebWalker.walkToBank();
		}
	}

}
