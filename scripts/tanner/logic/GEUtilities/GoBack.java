package scripts.tanner.logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Player;
import org.tribot.api2007.GameTab.TABS;

import scripts.api.Task;
import scripts.muler.Muler;
import scripts.muler.utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tanner.logic.GEHandler;
import scripts.tanner.utilities.Variables;

public class GoBack implements Task {

	@Override
	public String action() {
		return "Going back...";
	}

	@Override
	public int priority() {
		return 4;
	}

	@Override
	public boolean validate() {
		return GEHandler.boughtHide && GEHandler.soldLeather;
	}

	@Override
	public void execute() {
		if (GrandExchange.close()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return GrandExchange.getWindowState() == null;
				}
			}, General.random(5000, 10000));
			if (Banking.openBank()) {
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return Banking.isBankScreenOpen();
					}
				}, General.random(9000, 12000), Screen.MAIN_SCREEN);
				Banking.depositAllExcept("Coins");
				General.sleep(100, 1000);
				if (Banking.withdraw(1, "Lumbridge teleport")) {
					General.sleep(500, 2000);
					if (Banking.withdraw(0, Variables.SELECTED_HIDE)) {
						General.sleep(100, 1000);
						if (Banking.close()) {
							if (Inventory.open()) {
								if (Inventory.find("Lumbridge teleport")[0].click("Break")) {
									Mouse.leaveGame();
									General.sleep(2000, 5000);
									GEHandler.stop = true;
									return;
								}
							}

						}
					}
				}

			}
		}
	}

}
