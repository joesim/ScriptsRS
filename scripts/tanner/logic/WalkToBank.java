package scripts.tanner.logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tanner.utilities.Constants;
import scripts.tanner.utilities.Variables;
import scripts.webwalker_logic.WebWalker;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class WalkToBank implements Task {

	@Override
	public String action() {
		return "Walking to bank...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Inventory.getCount(Variables.SELECTED_HIDE) == 0 && !BankHelper.isInBank());
	}

	@Override
	public void execute() {
		WebWalker.walkToBank();
		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				RSObject[] banks = Objects.find(20, 6943);
				return (banks.length>0 && banks[0].isOnScreen());
			}
		}, General.random(3000, 12000), Screen.MAIN_SCREEN);
	}

}
