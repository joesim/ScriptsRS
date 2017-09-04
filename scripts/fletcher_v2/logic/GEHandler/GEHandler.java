package scripts.fletcher_v2.logic.GEHandler;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;

import scripts.api.Task;
import scripts.fletcher_v2.utilities.Conditions;
import scripts.fletcher_v2.utilities.Utils;
import scripts.fletcher_v2.utilities.Variables;
import scripts.ge_utilities.GEUtils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class GEHandler implements Task {

	public static boolean shouldGE = false;
	public static ArrayList<String> items = new ArrayList<String>();
	public static ArrayList<Integer> prices = new ArrayList<Integer>();
	public static ArrayList<Integer> quantities = new ArrayList<Integer>();
	public static ArrayList<Boolean> sell = new ArrayList<Boolean>();
	public static int compteur = 0;
	public static boolean justDoneGE = false;
	
	
	@Override
	public String action() {
		return "GE handler...";
	}

	@Override
	public int priority() {
		return 100;
	}

	@Override
	public boolean validate() {
		return shouldGE;
	}

	@Override
	public void execute() {
		General.println("GE Handler");
		if (Banking.isBankScreenOpen()) {
			Banking.depositAll();
			if (Timing.waitCondition(Conditions.cEmptyInventory, General.random(4000, 10000))) {
				Banking.withdraw(0, "Coins");
				if (Banking.find("Yew longbow").length > 0 && !items.contains("Yew longbow")) {
					do {
						General.println("Getting yew longbows to sell");
						General.sleep(500, 3000);
						if (Interfaces.get(12).getChild(24).click("Note")) {
							Utils.randomSleep();
							General.sleep(100, 1000);
							long t = Timing.currentTimeMillis();
							int nylb = Banking.find("Yew longbow")[0].getStack();
							while (!org.tribot.api2007.Banking.withdraw(0, "Yew longbow")
									&& (Timing.currentTimeMillis() - t) < 5000) {
								General.sleep(1000, 2000);
							}
							if (Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return Inventory.getCount("Yew longbow") > 0;
								}
							}, General.random(3000, 6000))) {
								Utils.randomSleep();
								General.sleep(1000);
								SleepJoe.sleepHumanDelay(2, 1, 10000);
								if (nylb > 0) {
									GEHandler.items.add("Yew longbow");
									GEHandler.prices.add(General.randomSD(580, 600, 590, 1));
									GEHandler.quantities.add(nylb);
									GEHandler.sell.add(true);
								}
							}
						} else {
							General.println("Pas clicked Note");
						}
					} while (Inventory.getCount("Yew longbow") == 0);
				}
				if (Banking.find("Maple longbow (u)").length > 0 && !items.contains("Maple longbow (u)")) {
					do {
						General.println("Getting Maple longbow (u)s to sell");
						General.sleep(500, 3000);
						if (Interfaces.get(12).getChild(24).click("Note")) {
							Utils.randomSleep();
							General.sleep(100, 1000);
							long t = Timing.currentTimeMillis();
							int nylb = Banking.find("Maple longbow (u)")[0].getStack();
							while (!org.tribot.api2007.Banking.withdraw(0, "Maple longbow (u)")
									&& (Timing.currentTimeMillis() - t) < 5000) {
								General.sleep(1000, 2000);
							}
							if (Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return Inventory.getCount("Maple longbow (u)") > 0;
								}
							}, General.random(3000, 6000))) {
								Utils.randomSleep();
								General.sleep(1000);
								SleepJoe.sleepHumanDelay(2, 1, 10000);
								if (nylb > 0) {
									GEHandler.items.add("Maple longbow (u)");
									GEHandler.prices.add(1);
									GEHandler.quantities.add(nylb);
									GEHandler.sell.add(true);
								}
							}
						} else {
							General.println("Pas clicked Note");
						}
					} while (Inventory.getCount("Maple longbow (u)") == 0);
				}
				if (Timing.waitCondition(Conditions.coinsInInv, General.random(5000, 10000))) {
					if (Functions.percentageBool(Variables.PERCENT_CLOSE_TRIBOT)) {
						org.tribot.api2007.Banking.close();
					} else {
						Functions.FTAB(27, 1);
					}
					Utils.randomSleep();
					if (DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							return Interfaces.get(12) == null;
						}
					}, General.random(5000, 15000), Screen.ALL_SCREEN, 80)) {
						General.println("Opening GE");
						GEUtils.openGE();
						Utils.randomSleep();
						GEUtils.placeOffer(items, prices, quantities, sell);
						Utils.randomSleep();
						GEUtils.closeGE();
						Utils.randomSleep();
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return GrandExchange.getWindowState() == null;
							}
						}, General.random(5000, 10000));
						long t = Timing.currentTimeMillis();
						while (!Banking.openBank() && !Banking.isBankScreenOpen()
								&& (Timing.currentTimeMillis() - t) < 15000) {
							General.sleep(1000, 2000);
						}
						Utils.randomSleep();
						Banking.depositAll();
						if (Banking.isBankScreenOpen()){
							justDoneGE = true;
						}
						Utils.randomSleep();
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Inventory.getAll().length == 0;
							}
						}, General.random(10000, 15000));
						General.sleep(General.randomSD(1000, 4000, 2000, 1));
					}
				}
			}
		} else {
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.bankScreenOpen, General.random(5000, 10000));
				return;
			}
		}
		shouldGE = false;
		General.println("Finished GE Handler");
	}

	public static void clearEverything() {
		items.clear();
		prices.clear();
		quantities.clear();
		sell.clear();
	}

}
