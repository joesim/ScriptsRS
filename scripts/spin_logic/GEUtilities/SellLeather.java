package scripts.spin_logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSGEOffer.STATUS;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.ge_utilities.GEJoe;
import scripts.muler.utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_logic.GEHandler;
import scripts.tabber.utilities.Utils;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class SellLeather implements Task {

	@Override
	public String action() {
		return "Selling leather...";
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public boolean validate() {
		return !GEHandler.soldLeather && Constants.grand_exchange.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		RSNPC[] gePeople = NPCs.find(2148);
		if (gePeople.length > 0) {
			if (GrandExchange.getWindowState() != null
					|| (AccurateMouse.click(gePeople[0], "Exchange") && Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							return GrandExchange.getWindowState() != null;
						}
					}, General.random(5000, 10000)))) {
				abortEverything();
				General.sleep(2000,4000);
				int moneybefore = Inventory.find("Coins").length>0 ? Inventory.find("Coins")[0].getStack():0;
				GEJoe.placeOffer("Bow string", 1, General.random(1, 10),  true, false, true);
				General.sleep(2000, 3000);
				General.sleep(1000, 2000);
				collect();
				General.sleep(1000,2000);
				int priceToSellAt = Inventory.find("Coins")[0].getStack() - moneybefore;
				if (priceToSellAt==0){
					return;
				}
				int howMany = Inventory.find("Bow string")[0].getStack();
				
				GEJoe.placeOffer("Bow string", howMany, priceToSellAt, true, false, false);				
				if (DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						RSGEOffer[] offers = GrandExchange.getOffers();
						for (RSGEOffer of : offers) {
							if ("Bow string".equals(of.getItemName()) && of.getStatus() == RSGEOffer.STATUS.COMPLETED) {
								return true;
							}
						}
						return false;
					}
				}, General.random(180000, 300000), Screen.ALL_SCREEN)) {
					collect();
					GEHandler.soldLeather = true;
				} else {
					collect();
					return;
				}

			}
		}
	}

	private void collect() {
		Interfaces.get(465).getChild(6).getChild(0).click("Collect");
		General.sleep(1500, 4000);
	}

	private void abortEverything() {
		RSGEOffer[] offers = GrandExchange.getOffers();
		boolean aborted = false;
		for (RSGEOffer of : offers) {
			if (of.click("Abort")) {
				aborted = true;
				General.sleep(1000, 2000);
			} else if (of.getStatus().equals(STATUS.COMPLETED)) {
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
