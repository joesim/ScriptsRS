package scripts.ge_utilities;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.GrandExchange;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSGEOffer;
import org.tribot.api2007.types.RSNPC;

import scripts.fletcher.utilities.MouseFletch;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Utils;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class GEUtils {

	private static final int PERCENT_QUANTITY_FIRST = General.random(10, 90);

	public static boolean openGE() {
		RSNPC[] ge = NPCs.findNearest("Grand Exchange Clerk");
		do {
			if (ge.length > 0) {
				if (MouseFletch.clickNPC("Exchange Grand Exchange Clerk", ge)) {
					if (DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							return GrandExchange.getWindowState() != null;
						}
					}, General.random(5000, 10000), Screen.MAIN_SCREEN)) {
						return true;
					}
				}
			}
		} while (GrandExchange.getWindowState() == null);
		return false;
	}

	public static void placeOffer(ArrayList<String> items, ArrayList<Integer> prices, ArrayList<Integer> quantities,
			ArrayList<Boolean> sell) {
		if (GrandExchange.getWindowState() != null) {
			abortEverything();
			if (Functions.percentageBool(Variables.PERCENT_ORDER_GE)) {
				for (int i = 0; i < items.size(); i++) {
					if (items.get(i).equals("Logs")) {
						GrandExchange.offer(items.get(i), prices.get(i), quantities.get(i), sell.get(i));
						Interfaces.get(465).getChild(24).getChild(0).click("Choose");
						General.sleep(1000, 2000);
						Keyboard.typeString(items.get(i));
						if (Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Interfaces.get(162).getChild(38).getChild(9) != null;
							}
						}, General.random(10000, 15000))) {
							General.sleep(1000, 2000);
							Interfaces.get(162).getChild(38).getChild(9).click("");
							General.sleep(3000, 5000);
							GrandExchange.setPrice(true, prices.get(i));
							GrandExchange.setQuantity(quantities.get(i));
							GrandExchange.confirmOffer(true);
						}
					} else {
						while (!GrandExchange.offer(items.get(i), prices.get(i), quantities.get(i), sell.get(i))) {
							General.sleep(1000, 2000);
						}
					}
				}
			} else {
				for (int i = items.size() - 1; i >= 0; i--) {
					if (items.get(i).equals("Logs")) {
						GrandExchange.offer(items.get(i), prices.get(i), quantities.get(i), sell.get(i));
						Interfaces.get(465).getChild(24).getChild(0).click("Choose");
						General.sleep(1000, 2000);
						Keyboard.typeString(items.get(i));
						if (Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Interfaces.get(162).getChild(38).getChild(9) != null;
							}
						}, General.random(10000, 15000))) {
							General.sleep(1000, 2000);
							Interfaces.get(162).getChild(38).getChild(9).click("");
							General.sleep(3000, 5000);
							GrandExchange.setPrice(true, prices.get(i));
							GrandExchange.setQuantity(quantities.get(i));
							GrandExchange.confirmOffer(true);
						}
					} else {
						while (!GrandExchange.offer(items.get(i), prices.get(i), quantities.get(i), sell.get(i))) {
							General.sleep(1000, 2000);
						}
					}
				}
			}
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					General.println("Here");
					if (items.size() == 0)
						return true;
					RSGEOffer[] offers = GrandExchange.getOffers();
					for (RSGEOffer of : offers) {
						if (items.contains(of.getItemName()) && of.getStatus() == RSGEOffer.STATUS.COMPLETED) {
							int ind = items.indexOf(of.getItemName());
							items.remove(ind);
							prices.remove(ind);
							quantities.remove(ind);
							sell.remove(ind);
							General.println(items.size());
							if (items.size() == 0)
								return true;
						}
					}
					return false;
				}
			}, General.random(30000, 60000), Screen.ALL_SCREEN);
			if (items.size() > 0) {
				int i = 0;
				for (Integer price : prices) {
					prices.set(i, (int) (price - (0.2) * price));
					i++;
				}
				placeOffer(items, prices, quantities, sell);
			}

			collect();
		}
	}

	public static void closeGE() {
		GrandExchange.close();
	}

	private static void collect() {
		Interfaces.get(465).getChild(6).getChild(0).click("Collect");
		General.sleep(1000, 3000);
	}

	private static void abortEverything() {
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
			collect();
		}
	}

	public static boolean placeAnOffer(String name, int price, int quantity, boolean sell) {

		if (Interfaces.get(465) == null) {
			openGE();
		}

		if (!Interfaces.get(465).getChild(4).isHidden()) {
			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(4), 1, true);
			if (!Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Interfaces.get(465).getChild(4).isHidden();
				}
			}, General.random(10000, 20000))) {
				return false;
			}
		}

		int box = 0;
		for (int i = 7; i < 15; i++) {
			if (Interfaces.get(465).getChild(i).getChild(22).getTextColour() == 0) {
				box = i;
				break;
			}
		}

		if (box == 0) {
			General.println("Grand exchange plein");
			throw new NullPointerException();
		} else {
			if (!sell) {
				MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(box).getChild(3), 1, true); // box
																											// buy
			} else {
				MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(box).getChild(4), 1, true); // box
																											// sell
			}
			DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return !Interfaces.get(465).getChild(4).isHidden();
				}
			}, General.random(10000, 20000), Screen.MAIN_SCREEN);
			SleepJoe.sleepHumanDelay(3, 1, 10000);
			if (!sell) {
				boolean okay = false;
				do {
					Keyboard.typeString(name);
					SleepJoe.sleepHumanDelay(3, 1, 10000);
					for (int i = 1; i <= 25; i += 3) {
						try {
							Interfaces.get(162).getChild(38).getChild(i).getText();
						} catch (NullPointerException e){
							break;
						}
						if (Interfaces.get(162).getChild(38).getChild(i).getText().equals(name)) {
							MouseMoveJoe.clickClickable("", Interfaces.get(162).getChild(38).getChild(i - 1), 1, true); // box
																														// buy
							if (DynamicWaiting.hoverWaitScreen(new Condition() {
								@Override
								public boolean active() {
									General.sleep(50, 100);
									return Interfaces.get(465).getChild(24).getChild(25).getText().equals(name);
								}
							}, General.random(10000, 20000), Screen.MAIN_SCREEN)) {
								setUpQuantityAndPrice(quantity, price);
								if (DynamicWaiting.hoverWaitScreen(new Condition() {
									@Override
									public boolean active() {
										General.sleep(10,20);
										if (Interfaces.get(465).getChild(24).getChild(25).getText().equals(name)
												&& Interfaces.get(465).getChild(24).getChild(32).getText()
														.contains(String.valueOf(quantity))
												&& Interfaces.get(465).getChild(24).getChild(39).getText()
														.contains(String.valueOf(price))) {
											MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(54), 1,
													true);
											return true;
										}
										return false;
									}
								}, General.random(4000, 10000), Screen.MAIN_SCREEN)){
									okay = true;
								} else {
									General.println("Strating back");
									MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(15).getChild(6), 1, true); // box																						// buy
									SleepJoe.sleepHumanDelay(1, 1, 10000);
								}
							} else {
								General.println("Wrong item");
								MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(15).getChild(6), 1, true); // box
																														// buy
								SleepJoe.sleepHumanDelay(1, 1, 10000);
							}
						}
					}
				} while (!okay);

			} else {
				if (MouseMoveJoe.clickItem("", Inventory.find(name), 1, true)){
					SleepJoe.sleepHumanDelay(3, 1, 10000);
					setUpQuantityAndPrice(quantity, price);
					if (DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							General.sleep(10,20);
							if (Interfaces.get(465).getChild(24).getChild(25).getText().equals(name)
									&& Interfaces.get(465).getChild(24).getChild(32).getText()
											.contains(String.valueOf(quantity))
									&& Interfaces.get(465).getChild(24).getChild(39).getText()
											.contains(String.valueOf(price))) {
								MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(54), 1,
										true);
								return true;
							}
							return false;
						}
					}, General.random(4000, 10000), Screen.MAIN_SCREEN)){
						
					}
				}
			}
		}

		return false;
	}

	private static void setUpQuantityAndPrice(int quantity, int price) {

		if (Functions.percentageBool(PERCENT_QUANTITY_FIRST)) {
			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(52), 1, true);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.random(10, 20);
					return Interfaces.get(162).getChild(42).isHidden();
				}
			}, General.random(5000, 10000))) {
				SleepJoe.sleepHumanDelay(1, 1, 10000);
				Keyboard.typeSend(String.valueOf(price));
				SleepJoe.sleepHumanDelay(1, 1, 10000);
			} else {
				return;
			}

			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(49), 1, true);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.random(10, 20);
					return Interfaces.get(162).getChild(42).isHidden();
				}
			}, General.random(5000, 10000))) {
				SleepJoe.sleepHumanDelay(1, 1, 10000);
				Keyboard.typeSend(String.valueOf(quantity));
				SleepJoe.sleepHumanDelay(1, 1, 10000);
			} else {
				return;
			}
		} else {
			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(49), 1, true);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.random(10, 20);
					return Interfaces.get(162).getChild(42).isHidden();
				}
			}, General.random(5000, 10000))) {
				SleepJoe.sleepHumanDelay(1, 1, 10000);
				Keyboard.typeSend(String.valueOf(quantity));
				SleepJoe.sleepHumanDelay(1, 1, 10000);
			} else {
				return;
			}

			MouseMoveJoe.clickClickable("", Interfaces.get(465).getChild(24).getChild(52), 1, true);
			if (Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.random(10, 20);
					return Interfaces.get(162).getChild(42).isHidden();
				}
			}, General.random(5000, 10000))) {
				SleepJoe.sleepHumanDelay(1, 1, 10000);
				Keyboard.typeSend(String.valueOf(price));
				SleepJoe.sleepHumanDelay(1, 1, 10000);
			} else {
				return;
			}
		}
	}

}
