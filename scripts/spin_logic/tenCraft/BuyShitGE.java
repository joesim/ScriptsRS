package scripts.spin_logic.tenCraft;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.ge_utilities.GEJoe;
import scripts.spin_logic.GettingTenCraft;
import scripts.tabber.utilities.Utils;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class BuyShitGE implements Task {

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
		return GettingTenCraft.BUY_BEFORE_CRAFT && !GettingTenCraft.boughtShit;
	}

	@Override
	public void execute() {

		General.sleep(1000, 6000);
		RSNPC[] gePeople = NPCs.find(2148);
		if (gePeople.length > 0) {

			if (AccurateMouse.click(gePeople[0], "Exchange") && Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return GrandExchange.getWindowState() != null;
				}
			}, General.random(5000, 10000))) {
				General.sleep(500,1000);
				abortEverything();
				Utils.randomSleep();
				int money = Inventory.find("Coins")[0].getStack();
				int price = General.random(7, 8);
				int number = 13000 * General.random(1, 3);
				if ((money - (number * price) - 25000) <= 1) {
					number = (money - 25000) / price;
				}
				GEJoe.placeOffer("Flax", number, price, false, false, false);
				General.sleep(1000, 2000);

				Utils.randomSleep();
				GEJoe.placeOffer("Varrock teleport", General.random(1, 2) * 10, 1000, false, false, false);
				General.sleep(1000, 2000);

				Utils.randomSleep();

				collect();

				GrandExchange.close();
				General.sleep(3000, 12000);
				GettingTenCraft.boughtShit = true;
			}
		}

	}

	private void collect() {
		Interfaces.get(465).getChild(6).getChild(0).click("Collect");
		General.sleep(1000, 3000);
	}

	private void abortEverything() {
		RSGEOffer[] offers = GrandExchange.getOffers();
		for (RSGEOffer of : offers) {
			of.click("Abort");
			General.sleep(1000, 2000);
		}
		Utils.randomSleep();
		Interfaces.get(465).getChild(6).getChild(0).click("Collect");
		General.sleep(1000, 3000);
	}

}
