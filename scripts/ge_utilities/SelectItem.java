package scripts.ge_utilities;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.GrandExchange.WINDOW_STATE;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class SelectItem implements Task {

	@Override
	public String action() {
		return null;
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return !GEJoe.SELL && WINDOW_STATE.NEW_OFFER_WINDOW.equals(GrandExchange.getWindowState());
	}

	@Override
	public void execute() {

		if (!Interfaces.get(162).getChild(42).isHidden() || Functions.percentageBool(10)) {
			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(15).getChild(6), 1, true);
			SleepJoe.sleepHumanDelay(10, 1, 10000);
		}

		Keyboard.typeString(GEJoe.NAME.toLowerCase());
		SleepJoe.sleepHumanDelay(3, 1, 10000);
		for (int i = 1; i <= 25; i += 3) {
			try {
				Interfaces.get(162).getChild(38).getChild(i).getText();
			} catch (NullPointerException e) {
				General.println("Nom pas trouvé");
				break;
			}
			if (Interfaces.get(162).getChild(38).getChild(i).getText().equals(GEJoe.NAME)) {
				MouseMoveJoe.clickClickable("", Interfaces.get(162).getChild(38).getChild(i - 1), 1, true); // box																					// buy
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(1);
						return Interfaces.get(465).getChild(24).getChild(25).getText().equals(GEJoe.NAME);
					}
				}, General.random(10000, 20000), Screen.MAIN_SCREEN);

			}
		}
	}

}
