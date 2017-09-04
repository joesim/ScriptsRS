package scripts.tabber.tabber_logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Constants;
import scripts.tabber.utilities.Variables;
import scripts.utilities.Functions;

public class ClickLectern implements Task {

	@Override
	public String action() {
		return "Clicking lectern...";
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public boolean validate() {
		RSObject[] lecterns = Objects.findNearest(30, Constants.LECTERN);
		return lecterns.length > 0 && lecterns[0].isOnScreen() && Inventory.getCount(Constants.SOFT_CLAY) > 0;
	}

	@Override
	public void execute() {
		RSObject[] lecterns = Objects.findNearest(30, Constants.LECTERN);
		if (lecterns.length > 0) {
			if ((ChooseOption.isOpen() && ChooseOption.select("Study")) || lecterns[0].click("Study")) {
				if (Functions.percentageBool(Variables.PERCENT_MOUSE_ON_TABS)) {
					DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							General.random(50, 100);
							return Interfaces.get(79) != null;
						}
					}, General.random(7000, 10000), Screen.HOUSE_TABS);
				} else {
					DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							General.random(50, 100);
							return Interfaces.get(79) != null;
						}
					}, General.random(7000, 10000), Screen.ALL_SCREEN);
				}
			}
		}
	}

}
