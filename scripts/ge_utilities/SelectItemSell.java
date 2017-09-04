package scripts.ge_utilities;

import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.GrandExchange.WINDOW_STATE;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class SelectItemSell implements Task {

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
		return GEJoe.SELL && WINDOW_STATE.NEW_OFFER_WINDOW.equals(GrandExchange.getWindowState());
	}

	@Override
	public void execute() {
		RSItem[] item = Inventory.find(GEJoe.NAME);
		if (item.length>0){
			MouseMoveJoe.clickItem("", item, 1, true);
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(1);
					return GEJoe.NAME.equals(Interfaces.get(465).getChild(24).getChild(25).getText());
				}
			}, General.random(5000, 10000), Screen.ALL_SCREEN);
		} else {
			General.println("Pas l'item dans l'inventory");
			throw new NullPointerException();
		}
	}

	
}
