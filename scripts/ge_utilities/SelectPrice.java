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

public class SelectPrice implements Task {

	public static boolean PRICE_SELECTED = false;
	
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
		return !PRICE_SELECTED && WINDOW_STATE.NEW_OFFER_WINDOW.equals(GrandExchange.getWindowState())
				&& GEJoe.NAME.equals(Interfaces.get(465).getChild(24).getChild(25).getText())
				&& !(String.valueOf(GEJoe.PRICE) + " coins")
						.contains(Interfaces.get(465).getChild(24).getChild(39).getText().replaceAll(",", ""));
	}

	@Override
	public void execute() {

		if (GEJoe.LOWPRICE && Functions.percentageBool(80)) {
			int nt = General.randomSD(4, 10, 6, 2);
			for (int i=0; i<nt;i++){
				MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(50), 1, true);//bas
				SleepJoe.sleepHumanDelay(0.6, 1, 2000);
			}
			PRICE_SELECTED = true;
		} else if (GEJoe.HIGHPRICE && Functions.percentageBool(80)){
			int nt = General.randomSD(4, 10, 6, 2);
			for (int i=0; i<nt;i++){
				MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(53), 1, true);//haut
				SleepJoe.sleepHumanDelay(0.6, 1, 2000);
			}
			PRICE_SELECTED = true;
		} else {
		
			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(52), 1, true);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.random(10, 20);
					return Interfaces.get(162).getChild(42).isHidden();
				}
			}, General.random(5000, 10000))) {
				SleepJoe.sleepHumanDelay(1, 1, 10000);
				Keyboard.typeSend(String.valueOf(GEJoe.PRICE));
				SleepJoe.sleepHumanDelay(1, 1, 10000);
			} else {
				SleepJoe.sleepHumanDelay(1, 1, 5000);
				return;
			}

			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(1);
					return (String.valueOf(GEJoe.PRICE) + " coins")
							.contains(Interfaces.get(465).getChild(24).getChild(39).getText().replaceAll(",", ""));
				}
			}, General.random(5000, 10000), Screen.MAIN_SCREEN);
		}
	}

}
