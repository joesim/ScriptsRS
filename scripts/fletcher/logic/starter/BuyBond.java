package scripts.fletcher.logic.starter;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.WorldHopper;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletcher.Fletcher;
import scripts.fletcher.logic.Starter;
import scripts.muler.utilities.Constants;
import scripts.tabber.utilities.Utils;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class BuyBond implements Task {

	@Override
	public String action() {
		return null;
	}

	@Override
	public int priority() {
		return 100;
	}

	@Override
	public boolean validate() {
		return Fletcher.tradedTheMule && Constants.grand_exchange.contains(Player.getPosition());
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
				General.sleep(1000, 4000);
				
				while (!GrandExchange.offer("Old school bond", 4000000, 1, false)) {
					General.sleep(1000, 2000);
				}
				General.sleep(1000, 6000);
				while (!GrandExchange.offer("Knife", General.random(10000, 50000), 1, false)) {
					General.sleep(1000, 2000);
				}
				General.sleep(1000, 6000);
				collect();
				GrandExchange.close();
				General.sleep(1000, 5000);
				if (Inventory.open()) {
					RSItem[] bond = Inventory.find("Old school bond (untradeable)");
					if (bond.length > 0) {
						bond[0].click("Redeem");
						if (Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Interfaces.get(66) != null;
							}
						}, General.random(5000, 10000))) {
							Interfaces.get(66).getChild(4).click("Select 14");
							General.sleep(2000, 6000);
							Interfaces.get(66).getChild(89).click("Confirm");
							General.sleep(10000, 20000);
						}
					}
					WorldHopper.getRandomWorld(true);
				}
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Login.getLoginState().equals(Login.STATE.INGAME);
					}
				}, General.random(40000, 60000));
				General.sleep(1000, 6000);
				Starter.stop = true;
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
