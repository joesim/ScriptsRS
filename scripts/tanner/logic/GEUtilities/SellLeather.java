package scripts.tanner.logic.GEUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Utils;
import scripts.tanner.logic.GEHandler;
import scripts.tanner.utilities.Variables;
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
		return !GEHandler.soldLeather && Inventory.getCount(Variables.SELECTED_LEATHER) > 0
				&& Constants.grand_exchange.contains(Player.getPosition());
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
				int moneybefore = Inventory.find("Coins")[0].getStack();
				while (!GrandExchange.offer(Variables.SELECTED_LEATHER, General.random(1, 10), 1, true)) {
					General.sleep(1000, 2000);
				}
				General.sleep(500, 2000);
				collect();
				int priceToSellAt = Inventory.find("Coins")[0].getStack() - moneybefore;
				int howMany = Inventory.find(Variables.SELECTED_LEATHER)[0].getStack() - General.random(5, 10);
				while (!GrandExchange.offer(Variables.SELECTED_LEATHER, priceToSellAt, howMany, true)) {
					General.sleep(1000, 2000);
				}
				if (DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						RSGEOffer[] offers = GrandExchange.getOffers();
						for (RSGEOffer of : offers) {
							if (Variables.SELECTED_LEATHER.equals(of.getItemName())
									&& of.getStatus() == RSGEOffer.STATUS.COMPLETED) {
								return true;
							}
						}
						return false;
					}
				}, General.random(180000, 300000), Screen.ALL_SCREEN)) {
					collect();
					GEHandler.soldLeather = true;
				} else {
					return;
				}

			}
		}
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
