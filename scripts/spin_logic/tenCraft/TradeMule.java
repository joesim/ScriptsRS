package scripts.spin_logic.tenCraft;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.types.RSPlayer;

import scripts.Spinner;
import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.muler.utilities.Variables;
import scripts.spin_logic.GettingTenCraft;

public class TradeMule implements Task {

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
		return !GettingTenCraft.BUY_BEFORE_CRAFT &&  !Spinner.tradedTheMule && Constants.grand_exchange.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		if (Variables.IS_MULE){
			Spinner.tradedTheMule = true;
			return;
		}
		RSPlayer[] mule = Players.find(Spinner.NAME_TRADE);
		if (mule.length > 0) {
			mule[0].click("Trade with " + Spinner.NAME_TRADE);
			if (Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Interfaces.get(335) != null;
				}
			}, General.random(4000, 10000))) {
				long t = Timing.currentTimeMillis();
				General.sleep(500, 4000);
				while (Trading.getWindowState() != null) {
					General.sleep(300, 2000);
					Trading.accept();
				}
				if ((Timing.currentTimeMillis() - t) > 3000) {
					Spinner.tradedTheMule = true;
				}
			}
		} else {
			General.sleep(1500, 5000);
		}
	}

}
