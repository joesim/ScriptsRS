package scripts.tanner.logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tanner.utilities.Variables;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class BankingHandler implements Task {

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
		RSObject[] banks = Objects.find(20, 6943);
		return (banks.length > 0 && banks[0].isOnScreen() && Inventory.getCount(Variables.SELECTED_HIDE) == 0)
				|| (Constants.grand_exchange.contains(Player.getPosition()));
	}

	@Override
	public void execute() {
		if (BankHelper.openBank()) {
			if (DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					return Banking.isBankScreenOpen();
				}
			}, General.random(9000, 12000), Screen.INVENTORY)) {
				Banking.deposit(0, Variables.SELECTED_LEATHER);
				if (Banking.find(Variables.SELECTED_HIDE).length == 0
						|| Banking.find(Variables.SELECTED_HIDE)[0].getStack() < 20) {
					GEHandler.shouldRefill = true;
					return;
				}
				Banking.withdraw(0, Variables.SELECTED_HIDE);
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return Inventory.getCount(Variables.SELECTED_HIDE) > 0;
					}
				}, General.random(2000, 4000), Screen.MINIMAP);
			}
		}
	}

}
