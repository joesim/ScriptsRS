package scripts.fletcher.logic.starter;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Trading;
import org.tribot.api2007.types.RSPlayer;

import scripts.api.Task;
import scripts.fletcher.Fletcher;
import scripts.fletcher.logic.Starter;
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

		int level = Skills.getCurrentLevel(Skills.SKILLS.FLETCHING);
		return level == 1 && !Fletcher.tradedTheMule && Constants.grand_exchange.contains(Player.getPosition());
	}

	@Override
	public void execute() {

		RSPlayer[] mule = Players.find(Starter.MULE_NAME);
		if (mule.length > 0) {
			mule[0].click("Trade with " + Starter.MULE_NAME);
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
					Fletcher.tradedTheMule = true;
				}
			}
		} else {
			General.sleep(1500, 5000);
		}
	}
}
