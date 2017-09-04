package scripts.fletcher.logic.starter;

import org.tribot.api.General;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.tabber.utilities.Utils;
import scripts.utilities.Functions;
import scripts.webwalker_logic.WebWalker;

public class WalkToGE implements Task {

	public static boolean doneSettings = false;

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
		return (!Constants.grand_exchange.contains(Player.getPosition())
				&& Login.getLoginState().equals(Login.STATE.INGAME));
	}

	@Override
	public void execute() {
		if (!doneSettings) {
			GameTab.open(TABS.OPTIONS);
			General.sleep(1000, 3000);
			Interfaces.get(261).getChild(1).getChild(7).click("Controls");
			General.sleep(1000, 3000);
			Interfaces.get(261).getChild(64).click("Keybinding");
			General.sleep(1000, 3000);
			Interfaces.get(121).getChild(103).click("Select");
			General.sleep(1000, 3000);
			Interfaces.get(121).getChild(1).getChild(11).click("Close");
			General.sleep(1000, 3000);
			doneSettings = true;
		}
		if (Functions.percentageBool(20)) {
			WebWalker.walkTo(new RSTile(3198, 3461, 0));
			Utils.randomSleep();
			WebWalker.walkTo(new RSTile(3165, 3486, 0));
		} else {
			WebWalker.walkTo(new RSTile(3165, 3486, 0));
			Utils.randomSleep();
		}
	}

}
