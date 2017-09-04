package scripts.tabber.tabber_logic.refillUtilities;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.HouseTabs;
import scripts.tabber.tabber_logic.RefillHandler;
import scripts.tabber.utilities.Constants;
import scripts.tabber.utilities.Utils;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class SellTabs implements Task {

	public static int supply = General.random(8, 10)*1000;
	
	public static String tele = "Teleport to house";
	
	@Override
	public String action() {
		return "Selling tabs...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return Constants.grand_exchange.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		RSNPC[] gePeople = NPCs.find(2148);
		if (gePeople.length > 0) {

			if (AccurateMouse.click(gePeople[0], "Exchange") && Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return GrandExchange.getWindowState() != null;
				}
			}, General.random(5000, 10000))) {
				abortEverything();
				Utils.randomSleep();
				int moneybefore = Inventory.find("Coins")[0].getStack();
				while (!GrandExchange.offer(tele, General.random(3000, 10000), 1, false)){
					General.sleep(1000, 2000);
				}
				Utils.randomSleep();
				General.sleep(2000, 5000);
				collect();
				int priceToSellAt = moneybefore - Inventory.find("Coins")[0].getStack() - General.random(20, 25);
				while (!GrandExchange.offer(tele, priceToSellAt, Inventory.find(tele)[0].getStack()-General.random(5, 10),
						true)){
					General.sleep(1000, 2000);
				}
				Utils.randomSleep();
				if (DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						RSGEOffer[] offers = GrandExchange.getOffers();
						for (RSGEOffer of : offers) {
							if (of!=null && tele.equals(of.getItemName())
									&& of.getStatus() == RSGEOffer.STATUS.COMPLETED) {
								return true;
							}
						}
						return false;
					}
				}, General.random(600000, 1200000), Screen.ALL_SCREEN)) {
					General.sleep(1000, 2000);
					collect();
					buySupplies();
				} else {
					HouseTabs.stop = true;
					RefillHandler.stop = true;
					return;
				}
			}
		}
	}

	private void buySupplies() {
		while (!GrandExchange.offer("Earth rune", General.random(10, 20), supply, false)){
			General.sleep(1000, 2000);
		}
		Utils.randomSleep();
		General.sleep(500, 2000);
		while (!GrandExchange.offer("Law rune", General.random(250, 300), supply, false)){
			General.sleep(1000, 2000);
		}
		Utils.randomSleep();
		General.sleep(500, 2000);
		collect();
		int moneybefore = Inventory.find("Coins")[0].getStack();
		while (!GrandExchange.offer("Soft clay", General.random(3000, 10000), 1, false)){
			General.sleep(1000, 2000);
		}
		Utils.randomSleep();
		General.sleep(2000, 5000);
		collect();
		int priceToBuyAt = moneybefore - Inventory.find("Coins")[0].getStack() + General.random(10, 15);
		while (!GrandExchange.offer("Soft clay", priceToBuyAt, supply, false)){
			General.sleep(1000, 2000);
		}
		Utils.randomSleep();
		if (DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100, 200);
				RSGEOffer[] offers = GrandExchange.getOffers();
				for (RSGEOffer of : offers) {
					if ("Soft clay".equals(of.getItemName()) && of.getStatus() == RSGEOffer.STATUS.COMPLETED) {
						return true;
					}
				}
				return false;
			}
		}, General.random(600000, 1200000), Screen.ALL_SCREEN)) {
			General.sleep(500, 3000);
			collect();
			GrandExchange.close();
			General.sleep(1000, 2000);
			Inventory.find("Teleport to House")[0].click("Break");
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100,200);
					return Objects.find(20, Constants.LECTERN).length>0;
				}
			}, General.random(5000, 10000), Screen.ALL_SCREEN);
			RefillHandler.stop = true;
			return;
		} else {
			HouseTabs.stop = true;
			RefillHandler.stop = true;
			return;
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
