package scripts.muler.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.types.RSPlayer;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.muler.utilities.Variables;

public class TradeMule implements Task {

	public static boolean TRADE_SUCCEEDED = false;

	@Override
	public String action() {
		return "Trading the mule...";
	}

	@Override
	public int priority() {
		return 1000000;
	}

	@Override
	public boolean validate() {
		return !TRADE_SUCCEEDED && Constants.grand_exchange.contains(Player.getPosition()) && !Variables.IS_MULE;
	}

	@Override
	public void execute() {
		RSPlayer[] mule = Players.find(Variables.MULE_NAME);
		if (mule.length > 0) {
			mule[0].click("Trade with " + Variables.MULE_NAME);
			if (Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Interfaces.get(335) != null;
				}
			}, General.random(4000, 10000))) {
				long t = Timing.currentTimeMillis();
				for (int i = 0; i < Variables.itemNames.size(); i++) {
					Trading.offer(Variables.itemAmount.get(i), Variables.itemNames.get(i));
				}
				while (Trading.getWindowState() != null) {
					General.sleep(300, 2000);
					Trading.accept();
				}
				if ((Timing.currentTimeMillis() - t) > 3000) {
					TRADE_SUCCEEDED = true;
				}
			}
		} else {
			General.sleep(1500, 5000);
		}

	}

}
