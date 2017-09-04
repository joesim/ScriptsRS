package scripts.spin_logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_logic.GEHandler;
import scripts.utilities.SleepJoe;
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
					while (Inventory.getAll().length != 0) {
						Banking.depositAll();
						General.sleep(500, 3000);
					}
					SleepJoe.sleepHumanDelay(1, 1, 5000);
					if (Banking.find("Flax").length > 0 && Banking.find("Flax")[0].getStack() > 28) {
						GEHandler.stop = true;
						return;
					}
					if (Interfaces.get(12).getChild(24).click("Note")) {
						General.sleep(1000, 2000);
						long t = Timing.currentTimeMillis();
						while (!Banking.withdraw(1, "Varrock teleport") && (Timing.currentTimeMillis() - t) < 10000) {
							General.sleep(1000, 2000);
						}
						General.sleep(1000, 2000);
						if ((Timing.currentTimeMillis() - t) >= 10000) {
							throw new NullPointerException();
						}
						t = Timing.currentTimeMillis();
						while (!Banking.withdraw(0, "Bow string") && (Timing.currentTimeMillis() - t) < 15000) {
							General.sleep(1000, 2000);
						}
						General.sleep(1000, 2000);
						
						t = Timing.currentTimeMillis();
						while (!Banking.withdraw(0, "Coins") && (Timing.currentTimeMillis() - t) < 10000) {
							General.sleep(1000, 2000);
						}
						
						General.sleep(1000, 2000);
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
							t = Timing.currentTimeMillis();
							while (!Inventory.find("Varrock teleport")[0].click("Break") && (Timing.currentTimeMillis() - t) < 10000) {
								General.sleep(1000, 2000);
							}
							General.sleep(1000, 2000);
							if ((Timing.currentTimeMillis() - t) >= 10000) {
								throw new NullPointerException();
							}
							
							Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return Constants.varrock.contains(Player.getPosition());
								}
							}, General.random(10000, 15000));
						}

					} else {
						return;
					}
				} else {
					return;
				}

			}

		} else {
			WebWalker.walkToBank();
		}
	}

}
