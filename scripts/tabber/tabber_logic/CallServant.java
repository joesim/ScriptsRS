package scripts.tabber.tabber_logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.tabber.utilities.Conditions;
import scripts.tabber.utilities.Constants;
import scripts.tabber.utilities.Utils;

public class CallServant implements Task {

	@Override
	public String action() {
		return "Calling servant...";
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public boolean validate() {

		RSArea areaCallButler = new RSArea(Player.getPosition(), 3);
		RSNPC[] butler = NPCs.find(227);
		if (Inventory.getCount(Constants.SOFT_CLAY) < 6 && !Utils.isButlerOut()
				&& (butler.length == 0 || !areaCallButler.contains(butler[0]))) {
			return true;
		}
		return false;
	}

	@Override
	public void execute() {
		if (GameTab.open(GameTab.TABS.OPTIONS)) {
			Utils.randomSleep();
			Interfaces.get(261).getChild(75).click("View House Options");
			Utils.randomSleep();
			Timing.waitCondition(Conditions.callServantInterface, General.random(3000, 5000));
			if (Interfaces.get(370) != null && Interfaces.get(370).getChild(15) != null) {
				Utils.randomSleep();
				Interfaces.get(370).getChild(15).click("Call Servant");
				Timing.waitCondition(Conditions.messageOuOptions, General.random(3000, 5000));
			}

		}
	}

}
