package scripts.muler_spinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.utilities.SleepJoe;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class BankingMule implements Task {

	private RSArea area = new RSArea(new RSTile(3208,3219,2), 3);
	
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
		return area.contains(Player.getPosition()) && (Inventory.find("Bow string").length==0 || Inventory.find("Coins").length==0);
	}

	@Override
	public void execute() {
		if (BankHelper.openBank()) {
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(20, 40);
					return Banking.isBankScreenOpen();
				}
			}, General.random(9000, 12000))) {
				while (Inventory.getAll().length != 0) {
					Banking.depositAll();
					General.sleep(500, 3000);
				}
				SleepJoe.sleepHumanDelay(1, 1, 5000);
				int stayingCoins = 0;
				int numCoins = 0;
				if (!MulerSpinner.TAKE_ALL) {
					stayingCoins = (General.random(200, 250) * 1000);
					numCoins = Banking.find("Coins")[0].getStack() - stayingCoins;
				}
				if (Interfaces.get(12).getChild(24).click("Note")) {
					General.sleep(1000, 2000);
					if (Banking.withdraw(0, "Bow string")) {
						General.sleep(1000, 2000);
						if (Banking.withdraw(numCoins, "Coins")) {
							General.sleep(1000, 2000);
							if (Banking.find("Flax").length>0 && Banking.find("Flax")[0].getStack()>1000){
								Banking.withdraw(Banking.find("Flax")[0].getStack()-1000, "Flax");
							}
							
							Banking.close();
							General.sleep(1000,2000);
						}
					}
				}
			}
		}
	}

}
