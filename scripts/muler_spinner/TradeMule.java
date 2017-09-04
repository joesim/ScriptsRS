package scripts.muler_spinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;

public class TradeMule implements Task {

	private RSArea area = new RSArea(new RSTile(3208, 3219, 2), 3);

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
		return !MulerSpinner.MULE_NAME.equals(Player.getRSPlayer().getName()) && area.contains(Player.getPosition())
				&& Inventory.find("Bow string").length > 0 && Inventory.find("Coins").length > 0;
	}

	@Override
	public void execute() {
		RSPlayer[] mule = Players.find(MulerSpinner.MULE_NAME);
		if (mule.length > 0) {
			mule[0].click("Trade with " + MulerSpinner.MULE_NAME);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Interfaces.get(335) != null;
				}
			}, General.random(4000, 10000))) {
				General.sleep(500, 4000);
				while (!Trading.offer(0, "Bow string")) {
					General.sleep(100, 1000);
				}
				while (!Trading.offer(0, "Coins")) {
					General.sleep(100, 1000);
				}
				while (Trading.getWindowState() != null) {
					General.sleep(300, 2000);
					Trading.accept();
				}
				MulerSpinner.stop = true;
			}
		} else {
			General.sleep(1500, 5000);
		}
	}

}
