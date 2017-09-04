package scripts.muler.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.muler.Muler;
import scripts.muler.utilities.Constants;
import scripts.muler.utilities.Variables;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class GEHandler implements Task {

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
		return Constants.grand_exchange.contains(Player.getPosition()) && (Variables.BUY_BOND || Variables.BUY_FLAX);
	}

	@Override
	public void execute() {
		RSNPC[] gePeople = NPCs.find(2148);
		if (gePeople.length > 0) {
			gePeople[0].click("Exchange Grand Exchange");
			if (Timing.waitCondition(new Condition() {

				@Override
				public boolean active() {
					General.sleep(100, 200);
					return GrandExchange.getWindowState() != null;
				}
			}, General.random(5000, 10000))) {
				
				abortEverything();
				if (Variables.BUY_FLAX) {
					GrandExchange.offer("Flax", 3, 100000, false);
					General.sleep(1000, 6000);
				}
				if (Variables.BUY_BOND) {
					GrandExchange.offer("Old school bond", 3200000, 1, false);
					General.sleep(1000, 6000);
				}
				Interfaces.get(465).getChild(6).getChild(0).click("Collect");
				General.sleep(1000, 6000);
				GrandExchange.close();
				General.sleep(5000, 10000);
				if (Variables.BUY_BOND) {
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
				}

				if (Variables.CONTINUE_SPINNING) {
					BankHelper.openBankAndWait();
					Banking.depositAll();
					General.sleep(2000, 6000);
					Banking.withdraw(0, "Flax");
					General.sleep(300, 2000);
					Banking.close();
					General.sleep(1000, 5000);
					if (GameTab.open(TABS.MAGIC)){
						Magic.selectSpell("Lumbridge Home Teleport");
						General.sleep(20000, 30000);
						Muler.stop = true;
					}
					
				}

			}
		}
	}

	private void abortEverything() {
		RSGEOffer[] offers = GrandExchange.getOffers();
		for (RSGEOffer of: offers){
			of.click("Abort");
			General.sleep(1000, 2000);
		}
		Interfaces.get(465).getChild(6).getChild(0).click("Collect");
		General.sleep(1000, 6000);
		
	}

}
