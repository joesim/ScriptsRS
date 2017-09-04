package scripts.fletcher_v2.logic.fletchYew;

import java.awt.Rectangle;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletcher_v2.logic.AfterSeventy;
import scripts.fletcher_v2.logic.GEHandler.GEHandler;
import scripts.fletcher_v2.utilities.Conditions;
import scripts.fletcher_v2.utilities.MouseFletch;
import scripts.fletcher_v2.utilities.Utils;
import scripts.fletcher_v2.utilities.Variables;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class BankingCutting implements Task {

	public static int level = 1;

	private String logs = "Yew logs";
	private String bow = "Yew longbow (u)";

	public static int howManyToCut = General.randomSD(5, 25, 15, 2) * 100;

	@Override
	public String action() {
		return "Banking...";
	}

	@Override
	public int priority() {
		return General.random(1, 2);
	}

	@Override
	public boolean validate() {
		return (AfterSeventy.cutting
				&& (Inventory.getCount(bow) > 0 || Inventory.getCount("Knife") == 0 || Inventory.getCount(logs) == 0))
				|| (!AfterSeventy.cutting && (Inventory.getCount(bow) == 0 || Inventory.getCount("Bow string") == 0
						|| Inventory.getCount("Yew longbow") > 0));
	}

	@Override
	public void execute() {//
		General.println("Banking");
		Utils.randomSleep();
		RSNPC[] bankers = NPCs.find(5455, 5456);
		if (Game.isUptext(" -> ")) {
			Player.getPosition().click("");
			General.sleep(200, 3000);
		}
		if (GEHandler.justDoneGE || (ChooseOption.isOpen() && ChooseOption.select("Bank Banker"))
				|| (bankers.length > 0 && MouseFletch.clickNPC("Bank Banker", bankers)) || Banking.isBankScreenOpen()) {
			GEHandler.justDoneGE = false;
			Utils.randomSleep();
			if (DynamicWaiting.hoverWaitScreen(Conditions.bankScreenOpen, General.random(3000, 10000), Screen.INVENTORY,
					70)) {
				// Antiban
				// ************************************************************************************
				Antiban1();

				// Deposit
				// ***********************************************************************************
				if (AfterSeventy.cutting) {
					Utils.randomSleep();
					RSItem[] logsItem = org.tribot.api2007.Banking.find(logs);

					// ANTIBAN
					if (Functions.percentageBool100000(Variables.PERCENT_WITHDRAW_FULL) && logsItem.length > 0) { // AB
						MouseFletch.clickItem("Withdraw-All", logsItem);
						SleepJoe.sleepHumanDelay(1, 1, 3000);
					}

					boolean depositedAll = false;
					if (Functions.percentageBool100000(Variables.PERCENT_DEPOSIT_ALL_BUT_NOT_WANT)) {
						depositedAll = true;
						Banking.depositAll();
						Timing.waitCondition(Conditions.cEmptyInventory, General.random(10000, 15000));
					}
					Utils.randomSleep();

					// Depositing
					MouseFletch.depositAllExcept("Knife");
					if (logsItem.length > 0 && !depositedAll) {
						Rectangle rec = logsItem[0].getArea();
						DynamicWaiting.hoverWaitRectangle(Conditions.onlyKnife, General.random(5000, 10000), rec, 70);
					}

				} else {
					RSItem[] yewu = org.tribot.api2007.Banking.find(bow);
					RSItem[] string = org.tribot.api2007.Banking.find("Bow string");
					if (Functions.percentageBool100000(Variables.PERCENT_WITHDRAW_FULL) && yewu.length > 0) {
						Utils.randomSleep();
						MouseFletch.clickItem("Withdraw-All", yewu);
						SleepJoe.sleepHumanDelay(1, 1, 3000);
					}

					if (Functions.percentageBool100000(Variables.PERCENT_WITHDRAW_FULL) && string.length > 0) {
						Utils.randomSleep();
						MouseFletch.clickItem("Withdraw-All", string);
						SleepJoe.sleepHumanDelay(1, 1, 3000);
					}

					MouseFletch.depositAll();
					if (yewu.length > 0) {
						Rectangle rec = yewu[0].getArea();
						DynamicWaiting.hoverWaitRectangle(Conditions.cEmptyInventory, General.random(5000, 10000), rec,
								70);
					}

					if (Functions.percentageBool100000(Variables.PERCENT_WITHDRAW_RANDOM_ITEM)) {
						// withdraw random item
						RSItem[] bankItems = org.tribot.api2007.Banking.getAll();
						if (bankItems.length > 1) {
							int index = General.random(0, bankItems.length - 1);
							org.tribot.api2007.Banking.withdraw(0, bankItems[index].getID());
							Utils.randomSleep();
							Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return Inventory.getCount(bankItems[index].getID()) > 0;
								}
							}, General.random(5000, 10000));
							org.tribot.api2007.Banking.deposit(0, bankItems[index].getID());
							Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return Inventory.getCount(bankItems[index].getID()) == 0;
								}
							}, General.random(5000, 10000));
							Utils.randomSleep();
							SleepJoe.sleepHumanDelay(1, 1, 3000);
						}
					}
				}

				// Determine what to do now.
				// **************************************
				RSItem[] yewlogs1 = Banking.find("Yew logs");
				RSItem[] string1 = Banking.find("Bow string");
				RSItem[] yewu1 = Banking.find("Yew longbow (u)");
				int numberYews = 0;
				int numberString = 0;
				int numberYewu = 0;
				if (yewlogs1.length > 0) {
					numberYews = yewlogs1[0].getStack();
				}
				if (string1.length > 0) {
					numberString = string1[0].getStack();
				}
				if (yewu1.length > 0) {
					numberYewu = yewu1[0].getStack();
				}
				if (numberYews == 0 && (numberString == 0 || numberYewu == 0)) {

					General.println("No yew logs or no supplies for stringing");

					GEHandler.clearEverything();
					int quantityYewLogs = General.randomSD(5, 15, 2) * 100;
					if (!GEHandler.items.contains("Yew logs")) {
						GEHandler.items.add("Yew logs");
						GEHandler.prices.add(General.randomSD(4, 6, 1) * 100);
						GEHandler.quantities.add(quantityYewLogs);
						GEHandler.sell.add(false);
					}

					if (!GEHandler.items.contains("Bow string")
							&& ((numberYewu + quantityYewLogs) - numberString) > 0) {
						GEHandler.items.add("Bow string");
						GEHandler.prices.add(General.randomSD(4, 6, 1) * 100);
						GEHandler.quantities.add((numberYewu + quantityYewLogs) - numberString);
						GEHandler.sell.add(false);
					}

					GEHandler.shouldGE = true;
					AfterSeventy.cutting = true;
					return;
				} else if (numberYews > 0) {
					AfterSeventy.cutting = true;
				} else {
					AfterSeventy.cutting = false;
				}

				if (AfterSeventy.cutting) {
					Utils.randomSleep();
					RSItem[] logsItem = org.tribot.api2007.Banking.find(logs);

					if (Inventory.find("Knife").length == 0) {
						Utils.randomSleep();
						SleepJoe.sleepHumanDelay(1, 0, 2000);
						org.tribot.api2007.Banking.withdraw(1, "Knife");
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Inventory.getCount("Knife") > 0;
							}
						}, General.random(3000, 5000));
						SleepJoe.sleepHumanDelay(1, 1, 3000);
					}

					if (logsItem.length > 0) {
						Utils.randomSleep();
						MouseFletch.clickItem("Withdraw-All", logsItem);
						SleepJoe.sleepHumanDelay(1, 1, 3000);
					}
				} else {

					Banking.deposit(1, "Knife");
					SleepJoe.sleepHumanDelay(1, 1, 3000);
					RSItem[] yewu = org.tribot.api2007.Banking.find(bow);
					RSItem[] string = org.tribot.api2007.Banking.find("Bow string");

					if (yewu.length > 0 && string.length > 0) {
						if (Functions.percentageBool(Variables.PERCENT_YEWU_FIRST)) {
							if (!MouseFletch.clickItem("Withdraw-14", yewu)) {
								Banking.withdraw(14, bow);
								Utils.randomSleep();
							}
							SleepJoe.sleepHumanDelay(1, 1, 3000);
							if (!MouseFletch.clickItem("Withdraw-14", string)) {
								Banking.withdraw(14, "Bow string");
								Utils.randomSleep();
							}
							SleepJoe.sleepHumanDelay(1, 1, 3000);
						} else {
							if (!MouseFletch.clickItem("Withdraw-14", string)) {
								Banking.withdraw(14, "Bow string");
								Utils.randomSleep();
							}
							SleepJoe.sleepHumanDelay(1, 1, 3000);
							if (!MouseFletch.clickItem("Withdraw-14", yewu)) {
								Banking.withdraw(14, bow);
								Utils.randomSleep();
							}
							SleepJoe.sleepHumanDelay(1, 1, 3000);
						}
					}
				}
			}
			SleepJoe.sleepHumanDelay(1, 1, 3000);
			if (Functions.percentageBool(Variables.PERCENT_CLOSE_TRIBOT)) {
				org.tribot.api2007.Banking.close();
			} else {
				Functions.FTAB(27, 1);
			}
			Utils.randomSleep();
			if (AfterSeventy.cutting) {
				DynamicWaiting.hoverWaitScreen(Conditions.bankScreenNotOpenAndInvFull, General.random(5000, 10000),
						Screen.KNIFE, 80);
			} else {
				DynamicWaiting.hoverWaitScreen(Conditions.bankScreenNotOpenAndInvFull, General.random(5000, 10000),
						Screen.INVENTORY, 80);
			}
		}
		General.println("Finished banking");
	}

	private void Antiban1() {
		// *********** ANTI-BAN
		// *********************************************************************
		if (Functions.percentageBool100000(Variables.PERCENT_DEPOSIT_ALL)) {
			Utils.randomSleep();
			org.tribot.api2007.Banking.depositAll();
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Inventory.getAll().length == 0;
				}
			}, General.random(5000, 10000), Screen.ALL_SCREEN, 30);
		} else if (Functions.percentageBool100000(Variables.PERCENT_SHOW_MENU)) {
			if (Interfaces.get(12).getChild(8).click("Show menu")) {
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return false;
					}
				}, General.random(3000, 20000), Screen.MAIN_SCREEN);
				Interfaces.get(12).getChild(8).click("Dismiss menu");
			}
		} else if (Functions.percentageBool100000(Variables.PERCENT_RIGHT_CLICK)) {
			Mouse.randomRightClick();
		}
		// *******************************************************************************************
	}

}
