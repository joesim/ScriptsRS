package scripts.ge_utilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
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

public class SelectQuantity implements Task {

	@Override
	public String action() {
		return null;
	}

	@Override
	public int priority() {
		return General.random(2, 4);
	}

	@Override
	public boolean validate() {
		return WINDOW_STATE.NEW_OFFER_WINDOW.equals(GrandExchange.getWindowState())
				&& GEJoe.NAME.equals(Interfaces.get(465).getChild(24).getChild(25).getText())
				&& !String.valueOf(GEJoe.QUANTITY)
						.equals(Interfaces.get(465).getChild(24).getChild(32).getText().replaceAll(",", ""));
	}

	@Override
	public void execute() {
		General.println("Quantity...");
		if (GEJoe.QUANTITY % 1000 == 0 && (GEJoe.QUANTITY / 1000) < 10 && Functions.percentageBool(80)) {
			int nt = GEJoe.QUANTITY / 1000;
			for (int i = 0; i < nt; i++) {
				MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(48), 1, true);// bas
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}
		} else if (GEJoe.QUANTITY > 1 && GEJoe.QUANTITY < 10 && Functions.percentageBool(80)) {
			int nt = GEJoe.QUANTITY-1;
			for (int i = 0; i < nt; i++) {
				MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(45), 1, true);// bas
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}
		} else {
			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(49), 1, true);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.random(10, 20);
					return Interfaces.get(162).getChild(42).isHidden();
				}
			}, General.random(5000, 10000))) {
				SleepJoe.sleepHumanDelay(1, 1, 10000);
				Keyboard.typeSend(String.valueOf(GEJoe.QUANTITY));
				SleepJoe.sleepHumanDelay(1, 1, 10000);
			} else {
				SleepJoe.sleepHumanDelay(1, 1, 5000);
				return;
			}
		}
		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				General.sleep(1);
				return String.valueOf(GEJoe.QUANTITY)
						.equals(Interfaces.get(465).getChild(24).getChild(32).getText().replaceAll(",", ""));
			}
		}, General.random(5000, 10000), Screen.MAIN_SCREEN);

	}

}
