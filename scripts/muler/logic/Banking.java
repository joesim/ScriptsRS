package scripts.muler.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;

import scripts.api.Task;
import scripts.muler.utilities.Variables;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class Banking implements Task {

	@Override
	public String action() {
		return "Banking...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return BankHelper.isInBank() && Variables.WALK_TO_BANK;
	}

	@Override
	public void execute() {
		if (BankHelper.openBank()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return org.tribot.api2007.Banking.isBankScreenOpen();
				}
			}, General.random(10000, 12000));
			org.tribot.api2007.Banking.depositAll();
			General.sleep(500, 3000);
			Mouse.moveBox(268, 312, 321, 326);
			Mouse.click(1);

			for (int i = 0; i < Variables.itemNames.size(); i++) {
				if (Variables.itemNames.get(i).equals("Coins") && Variables.IS_MULE) {
					org.tribot.api2007.Banking.withdraw(0, Variables.itemNames.get(i));
				} else {
					org.tribot.api2007.Banking.withdraw(Variables.itemAmount.get(i), Variables.itemNames.get(i));
				}
				General.sleep(1000, 3000);
			}
			org.tribot.api2007.Banking.withdraw(1, 1000);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Inventory.getAll().length > 0;
				}
			}, General.random(4000, 10000))) {
				org.tribot.api2007.Banking.close();
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return !org.tribot.api2007.Banking.isBankScreenOpen();
					}
				}, General.random(3000, 5000));
			}
		}

	}

}
