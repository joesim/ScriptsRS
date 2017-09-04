package scripts.fletcher.logic.fletchSeventy;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletcher.logic.GettingSeventy;
import scripts.fletcher.logic.GEHandler.GEHandler;
import scripts.fletcher.utilities.Conditions;
import scripts.fletcher.utilities.MouseFletch;
import scripts.fletcher.utilities.Utils;
import scripts.fletcher.utilities.Variables;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class Banking implements Task {

	public static int level = 1;

	private String logsBefore = "";
	private String logs = "";
	private String bowBefore = "";
	private String bow = "";

	@Override
	public String action() {
		return null;
	}

	@Override
	public int priority() {
		return General.random(1, 2);
	}

	@Override
	public boolean validate() {
		level = Skills.getCurrentLevel(Skills.SKILLS.FLETCHING);
		if (level < 10) {
			logsBefore = "Logs";
			logs = "Logs";
			bow = "Arrow shaft";
			bowBefore = "Arrow shaft";
		} else if (level < 25) {
			logsBefore = "Logs";
			logs = "Logs";
			bow = "Longbow (u)";
			bowBefore = "Arrow shaft";
		} else if (level < 40) {
			logsBefore = "Logs";
			logs = "Oak logs";
			bow = "Oak longbow (u)";
			bowBefore = "Longbow (u)";
		} else if (level < 55) {
			logsBefore = "Oak logs";
			logs = "Willow logs";
			bow = "Willow longbow (u)";
			bowBefore = "Oak longbow (u)";
		} else if (level < 70) {
			logsBefore = "Willow logs";
			logs = "Maple logs";
			bow = "Maple longbow (u)";
			bowBefore = "Willow longbow (u)";
		} else {

		}
		return Inventory.getCount(bow) > 0 || Inventory.getCount(logs) == 0 || Inventory.getCount("Knife") == 0;
	}

	@Override
	public void execute() {//

		Utils.randomSleep();

		RSNPC[] bankers = NPCs.find(5455, 5456);
		if (Game.isUptext(" -> ")) {
			Player.getPosition().click("");
			General.sleep(200, 3000);
		}
		if (GEHandler.justDoneGE || (ChooseOption.isOpen() && ChooseOption.select("Bank Banker"))
				|| (bankers.length > 0 && MouseFletch.clickNPC("Bank Banker", bankers))
				|| org.tribot.api2007.Banking.isBankScreenOpen()) {
			GEHandler.justDoneGE = false;
			Utils.randomSleep();
			if (DynamicWaiting.hoverWaitScreen(Conditions.bankScreenOpen, General.random(3000, 10000), Screen.INVENTORY,
					70)) {
				Utils.randomSleep();
				if (level >= 70) {
					Utils.randomSleep();
					org.tribot.api2007.Banking.depositAll();
					SleepJoe.sleepHumanDelay(1, 1, 2000);
					GettingSeventy.stop = true;
					return;
				}
				if (Functions.percentageBool100000(Variables.PERCENT_DEPOSIT_ALL)) {
					org.tribot.api2007.Banking.depositAll();
					DynamicWaiting.hoverWaitScreen(Conditions.cEmptyInventory, General.random(5000, 10000),
							Screen.ALL_SCREEN, 30);
				} else if (Functions.percentageBool100000(Variables.PERCENT_SHOW_MENU)) {
					Interfaces.get(12).getChild(8).click("Show menu");
					DynamicWaiting.hoverWaitScreen(Conditions.justWait, General.random(3000, 20000),
							Screen.MAIN_SCREEN);
					Interfaces.get(12).getChild(8).click("Dismiss menu");
				} else if (Functions.percentageBool100000(Variables.PERCENT_RIGHT_CLICK)) {
					Mouse.randomRightClick();
				}

				Utils.randomSleep();
				RSItem[] logsItem = org.tribot.api2007.Banking.find(logs);

				if (Functions.percentageBool100000(Variables.PERCENT_WITHDRAW_FULL) && logsItem.length > 0) { // AB
					Utils.randomSleep();
					MouseFletch.clickItem("Withdraw-All", logsItem);
					SleepJoe.sleepHumanDelay(1, 1, 3000);
				}

				Utils.randomSleep();
				MouseFletch.depositAllExcept("Knife", logs);
				SleepJoe.sleepHumanDelay(1, 1, 3000);

				if (Inventory.getCount(logs) == 28) {
					Utils.randomSleep();
					org.tribot.api2007.Banking.deposit(1, logs);
				}

				if (Inventory.find("Knife").length == 0) {
					Utils.randomSleep();
					SleepJoe.sleepHumanDelay(1, 0, 2000);
					org.tribot.api2007.Banking.withdraw(1, "Knife");
					SleepJoe.sleepHumanDelay(2, 1, 3000);
				}

				if (Functions.percentageBool100000(Variables.PERCENT_WITHDRAW_RANDOM_ITEM)) {
					Utils.randomSleep();
					// withdraw random item
					RSItem[] bankItems = org.tribot.api2007.Banking.getAll();
					int index = General.random(1, bankItems.length - 1);
					org.tribot.api2007.Banking.withdraw(0, bankItems[index].getID());
					Utils.randomSleep();
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							return Inventory.getCount(bankItems[index].getID()) > 0;
						}
					}, General.random(5000, 10000));
					Utils.randomSleep();
					org.tribot.api2007.Banking.deposit(0, bankItems[index].getID());
					SleepJoe.sleepHumanDelay(1, 1, 3000);
					// deposit
				} else if (Functions.percentageBool100000(Variables.PERCENT_SEARCH_ITEM) && logsItem.length > 0) {
					Utils.randomSleep();
					Interfaces.get(12).getChild(28).click("Search");
					SleepJoe.sleepHumanDelay(1, 1, 3000);
					Keyboard.typeString(logs);
					SleepJoe.sleepHumanDelay(2, 1, 6000);
				}

				if (logsItem.length > 0) {
					Utils.randomSleep();
					MouseFletch.clickItem("Withdraw-All", logsItem);
					SleepJoe.sleepHumanDelay(1, 1, 3000);
				} else {
					org.tribot.api2007.Banking.depositAll();
					Timing.waitCondition(Conditions.cEmptyInventory, General.random(5000, 10000));
					Utils.randomSleep();
					SleepJoe.sleepHumanDelay(2, 1, 10000);

					Utils.randomSleep();
					General.sleep(500, 2000);
					SleepJoe.sleepHumanDelay(2, 1, 10000);
					GEHandler.clearEverything();
					General.sleep(1000);
					SleepJoe.sleepHumanDelay(2, 1, 10000);
					GEHandler.items.add(logs);
					GEHandler.prices.add(General.random(4, 6) * 100);
					GEHandler.quantities.add(General.randomSD(9, 12, 1)*160);
					GEHandler.sell.add(false);
					GEHandler.shouldGE = true;
					return;

				}
			}
			SleepJoe.sleepHumanDelay(1, 1, 3000);
			if (Functions.percentageBool(Variables.PERCENT_CLOSE_TRIBOT)) {
				org.tribot.api2007.Banking.close();
			} else {
				Functions.FTAB(27, 1);
			}
			Utils.randomSleep();
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Interfaces.get(12) == null && Inventory.getAll().length == 28;
				}
			}, General.random(200, 500), Screen.KNIFE, 80);
		}

	}

}
