package scripts.ge_utilities;

import org.tribot.api.General;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.GrandExchange.WINDOW_STATE;

import scripts.api.Task;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class Confirm implements Task {

	@Override
	public String action() {
		return null;
	}

	@Override
	public int priority() {
		return 100;
	}

	@Override
	public boolean validate() {
		return WINDOW_STATE.NEW_OFFER_WINDOW.equals(GrandExchange.getWindowState())
				&& GEJoe.NAME.equals(Interfaces.get(465).getChild(24).getChild(25).getText())
				&& (SelectPrice.PRICE_SELECTED || (String.valueOf(GEJoe.PRICE) + " coins")
						.contains(Interfaces.get(465).getChild(24).getChild(39).getText().replaceAll(",", "")))
				&& String.valueOf(GEJoe.QUANTITY).equals(Interfaces.get(465).getChild(24).getChild(32).getText().replaceAll(",", ""));
	}

	@Override
	public void execute() {
		int nb = General.randomSD(1, 3, 1, 1);
		for (int i = 0; i < 1  ; i++) {
			if (MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(54), 1, true)) {
				SleepJoe.sleepHumanDelay(0.5, 1, 2000);
				GEJoe.CONFIRMED = true;
			}
		}
	}

}
