package scripts.potato_picker.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;

import scripts.PotatoPicker;
import scripts.api.Task;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class BankPotato implements Task {

	private int numberAttempts = 0;

	@Override
	public int priority() {
		return 4;
	}

	@Override
	public boolean validate() {
		return (BankHelper.isInBank() && Inventory.getAll().length > 0);
	}

	@Override
	public void execute() {
		if (BankHelper.openBank()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					if (Banking.isBankScreenOpen()) {
						return true;
					}
					return false;
				}
			}, General.random(10000, 15000));
			if (Banking.isBankScreenOpen()) {
				Banking.depositAll();
				Banking.close();
				resetAttempts();
			}
		} else {
			incrementAttempts();
			if (madeTooManyAttempts()) {
				Login.logout();
				PotatoPicker.stopBot();
			}
		}
	}

	@Override
	public String action() {
		return "Banking potatoes...";
	}

	private boolean madeTooManyAttempts() {
		return numberAttempts > 3;
	}

	private void incrementAttempts() {
		numberAttempts++;
	}

	private void resetAttempts() {
		numberAttempts = 0;
	}

}
