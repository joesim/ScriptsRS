package scripts.spin_logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGEOffer;

import scripts.api.Task;
import scripts.ge_utilities.GEJoe;
import scripts.muler.utilities.Constants;
import scripts.spin_logic.GEHandler;
import scripts.tabber.utilities.Utils;

public class BuyHide implements Task {

	@Override
	public String action() {
		return "Buying hides...";
	}

	@Override
	public int priority() {
		return 2;
	}

	@Override
	public boolean validate() {
		return GEHandler.soldLeather && Constants.grand_exchange.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		abortEverything();
		int money = Inventory.find("Coins")[0].getStack();
		int price = General.random(7, 8);
		int number = 13000 * General.random(1, 3);
		if ((money - (number*price))<=1){
			number = (money)/price;
		}
		GEJoe.placeOffer("Flax", number,price, false, false, false);
		General.sleep(2000, 3000);
		General.sleep(500, 2000);
		collect();
		GEHandler.boughtHide = true;
	}

	private void collect() {
		Interfaces.get(465).getChild(6).getChild(0).click("Collect");
		General.sleep(1000, 3000);
	}

	private void abortEverything() {
		RSGEOffer[] offers = GrandExchange.getOffers();
		boolean aborted = false;
		for (RSGEOffer of : offers) {
			if (of.click("Abort")) {
				aborted = true;
				General.sleep(1000, 2000);
			}

		}
		Utils.randomSleep();
		if (aborted) {
			Interfaces.get(465).getChild(6).getChild(0).click("Collect");
			General.sleep(1000, 3000);
		}
	}

}
