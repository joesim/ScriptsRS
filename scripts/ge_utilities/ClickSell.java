package scripts.ge_utilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.GrandExchange.WINDOW_STATE;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class ClickSell implements Task {

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
		return GEJoe.SELL && WINDOW_STATE.SELECTION_WINDOW.equals(GrandExchange.getWindowState());
	}

	@Override
	public void execute() {
		int box = 0;
		for (int i = 7; i < 15; i++) {
			if (Interfaces.get(465).getChild(i).getChild(22).isHidden()) {
				box = i;
				break;
			}
		}

		if (box == 0) {
			General.println("Grand exchange plein");
			throw new NullPointerException();
		} else {
			if (MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(box).getChild(4), 1, true)){
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(1);
						return !Interfaces.get(465).getChild(4).isHidden();
					}
				}, General.random(5000, 10000), Screen.MAIN_SCREEN);
			}
		}
		SleepJoe.sleepHumanDelay(1, 1, 4000);
	}

	
}
