package scripts.muler_spinner;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Trading;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;

public class AcceptTrade implements Task {

	private RSArea area = new RSArea(new RSTile(3208,3219,2), 3);
	
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
		return MulerSpinner.MULE_NAME.equals(Player.getRSPlayer().getName()) && area.contains(Player.getPosition())
				&& Inventory.find("Bow string").length > 0 && Inventory.find("Coins").length > 0;
	}

	@Override
	public void execute() {
		General.sleep(1000, 3000);
		if (Trading.getWindowState() != null) {
			while (Trading.getWindowState() != null) {
				General.sleep(1000, 5000);
				Trading.accept();
			}
		}
	}

}
