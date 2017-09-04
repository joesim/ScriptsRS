package scripts.muler.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.Players;
import org.tribot.api2007.Trading;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.script.interfaces.MessageListening07;

import scripts.api.Task;
import scripts.muler.Muler;
import scripts.muler.utilities.Constants;
import scripts.muler.utilities.Variables;

public class AcceptTrade implements Task {

	@Override
	public String action() {
		return "Accept trade...";
	}

	@Override
	public int priority() {
		return 10000000;
	}

	@Override
	public boolean validate() {
		return Constants.grand_exchange.contains(Player.getPosition()) && Variables.IS_MULE
				&& (Variables.NUMBER_OF_TRADES > Variables.currentTrades);
	}

	@Override
	public void execute() {
		General.sleep(1000, 3000);

		if (Trading.getWindowState() != null) {
			long t = Timing.currentTimeMillis();

			for (int i = 0; i < Variables.itemNames.size(); i++) {
				Trading.offer(Variables.itemAmount.get(i), Variables.itemNames.get(i));
			}
			while (Trading.getWindowState() != null) {
				General.sleep(1000, 5000);
				Trading.accept();
			}
			if ((Timing.currentTimeMillis() - t) > 3000) {
				Variables.currentTrades++;
			}
		}

	}

}
