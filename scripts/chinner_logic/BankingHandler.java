package scripts.chinner_logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.Chinner;
import scripts.api.Task;
import scripts.chinner_utilities.Conditions;
import scripts.chinner_utilities.Constants;
import scripts.chinner_utilities.Utils;
import scripts.chinner_utilities.Variables;
import scripts.webwalker_logic.shared.helpers.BankHelper;

public class BankingHandler implements Task {

	@Override
	public String action() {
		return "Banking...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (!Utils.isFullyReady() && BankHelper.isInBank());
	}

	@Override
	public void execute() {
		if (BankHelper.openBank() && Timing.waitCondition(Conditions.bankScreenOpen(), General.random(4000, 5000))) {

			// Deposit all
			if (Inventory.getAll().length != 0) {
				Banking.depositAll();
				Timing.waitCondition(Conditions.inventoryEmpty(), General.random(2000, 3000));
			}

			// Equipping selected number of chins
			int amountChinsToWithdraw = Variables.AMOUNT_CHINS;
			RSItem[] chins = Equipment.find(Variables.SELECTED_CHINS);
			if (chins.length > 0) {
				amountChinsToWithdraw -= chins[0].getStack();
				amountChinsToWithdraw += General.random(0, 5);
			}
			if (amountChinsToWithdraw > 0) {
				RSItem[] chinsBank = Banking.find(Variables.SELECTED_CHINS);
				if (chinsBank.length > 0 && chinsBank[0].getStack() >= amountChinsToWithdraw) {
					Banking.withdraw(amountChinsToWithdraw, Variables.SELECTED_CHINS);
					Timing.waitCondition(Conditions.inventoryNotEmpty(), General.random(2000, 3000));
					Banking.close();
					Timing.waitCondition(Conditions.bankScreenNotOpen(), General.random(2000, 3000));
					RSItem[] chinsInventory = Inventory.find(Variables.SELECTED_CHINS);
					if (chinsInventory.length > 0) {
						chinsInventory[0].click("Wield " + chinsInventory[0].getDefinition().getName());
					}
					if (BankHelper.openBank()) {
						Timing.waitCondition(Conditions.bankScreenOpen(), General.random(4000, 5000));
					}
				} else {
					General.println("Not enough chins! Logging out...");
					Chinner.stop();
					return;
				}
			}
			General.sleep(120, 400);

			// Withdraw greegree
			if (Banking.find(Constants.MONKEY_GREEGREE).length > 0) {
				Banking.withdraw(1, Constants.MONKEY_GREEGREE);
			} else {
				General.println("No greegree! Logging out...");
				Chinner.stop();
				return;
			}
			General.sleep(120, 400);

			// Withdraw RoD
			if (Banking.find(Constants.RING_OF_DUELING).length > 0) {
				Banking.withdraw(1, Constants.RING_OF_DUELING);
			} else {
				General.println("No ring of dueling! Logging out...");
				Chinner.stop();
				return;
			}
			General.sleep(120, 400);

			// Withdraw poison pots
			if (Variables.NUMBER_POISON_POT > 0) {
				RSItem[] poisonPot = Banking.find(Variables.SELECTED_POISON_POT[0]);
				if (poisonPot.length > 0 && poisonPot[0].getStack() >= Variables.NUMBER_POISON_POT) {
					Banking.withdraw(Variables.NUMBER_POISON_POT, Variables.SELECTED_POISON_POT[0]);
				} else {
					General.println("Not enough poison pots! Logging out...");
					Chinner.stop();
					return;
				}
			}
			General.sleep(120, 400);

			// Withdraw ranging pot
			if (Variables.NUMBER_RANGING_POT > 0) {
				RSItem[] rangePot = Banking.find(Constants.RANGING_POT[0]);
				if (rangePot.length > 0 && rangePot[0].getStack() >= Variables.NUMBER_RANGING_POT) {
					Banking.withdraw(Variables.NUMBER_RANGING_POT, Constants.RANGING_POT[0]);
				} else {
					General.println("Not enough ranging pots! Logging out...");
					Chinner.stop();
					return;
				}
			}
			General.sleep(120, 400);

			// Withdraw food
			if (Variables.NUMBER_FOOD > 0) {
				RSItem[] food = Banking.find(Variables.SELECTED_FOOD);
				if (food.length > 0 && food[0].getStack() >= Variables.NUMBER_FOOD) {
					Banking.withdraw(Variables.NUMBER_FOOD, Variables.SELECTED_FOOD);
				} else {
					General.println("Not enough food! Logging out...");
					Chinner.stop();
					return;
				}
			}
			General.sleep(120, 400);

			// Withdraw prayer pots
			if (Variables.NUMBER_PRAY_POT > 0) {
				RSItem[] prayPots = Banking.find(Constants.PRAYER_POT[0]);
				if (prayPots.length > 0 && prayPots[0].getStack() >= Variables.NUMBER_PRAY_POT) {

					Banking.withdraw(Variables.NUMBER_PRAY_POT, Constants.PRAYER_POT[0]);
				} else {
					General.println("Not enough prayer pots! Logging out...");
					Chinner.stop();
					return;
				}
			}
			General.sleep(120, 400);

			// Closing bank
			Banking.close();
			Timing.waitCondition(Conditions.bankScreenNotOpen(), General.random(3000, 4000));

		}
	}

}
