package scripts.fletcher.logic.fletchYew;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletcher.logic.AfterSeventy;
import scripts.fletcher.utilities.DynamicWaitingFletching;
import scripts.fletcher.utilities.MouseFletch;
import scripts.fletcher.utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class StringYews implements Task {

	private int interfaceID = 309;
	private int childID = 6;
	private String bowu = "Yew longbow (u)";
	private String bow = "Yew longbow";

	@Override
	public String action() {
		return "Stringing longbows...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return !AfterSeventy.cutting && (Inventory.getCount(bowu) > 0 && Inventory.getCount("Bow string") > 0);
	}

	@Override
	public void execute() {
		General.println("Stringing");
		RSItem[] yewu = Inventory.find(this.bowu);
		RSItem[] string = Inventory.find("Bow string");
		if (yewu.length > 0 && string.length > 0) {
			boolean yewuFirstInv = false;
			for (RSItem it : Inventory.getAll()) {
				if (it.getDefinition().getName().equals(this.bowu)) {
					yewuFirstInv = true;
					yewu = Functions.inverseArray(yewu);
					break;
				} else if (it.getDefinition().getName().equals("Bow string")) {
					yewuFirstInv = false;
					string = Functions.inverseArray(string);
					break;
				}
			}
			boolean yewuFirst = Functions.percentageBool(scripts.fletcher.utilities.Variables.PERCENT_YEWU_FIRST);
			if ((yewuFirst && (Game.isUptext("Use Yew longbow (u) ->") || MouseFletch.clickItem(this.bowu, yewu)))
					|| !yewuFirst) {
				Utils.randomSleep();
				SleepJoe.sleepHumanDelay(1, 1, 3000);
				if (MouseFletch.clickItem("Bow string", string)) {
					Utils.randomSleep();
					if (yewuFirst || Game.isUptext("Use Yew longbow (u) ->")
							|| MouseFletch.clickItem(this.bowu, yewu)) {
						Utils.randomSleep();
						if (DynamicWaiting.hoverWaitScreen(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Interfaces.get(interfaceID) != null
										&& Interfaces.get(interfaceID).getChild(childID) != null;
							}
						}, General.random(3000, 5000), Screen.STRING_BOW, 60)) {
							if (Functions.percentageBool(scripts.fletcher.utilities.Variables.PERCENT_MAKE_X_YEWLB)) {
								Utils.randomSleep();
								if (MouseFletch.clickClickable("Make X",
										Interfaces.get(interfaceID).getChild(childID))) {
									Utils.randomSleep();
									if (Timing.waitCondition(new Condition() {
										@Override
										public boolean active() {
											General.sleep(100, 200);
											return Interfaces.get(interfaceID) == null;
										}
									}, General.random(3000, 4000))) {
										SleepJoe.sleepHumanDelay(1, 1, 2999);
										Utils.randomSleep();
										typeAmount();
										RSNPC[] bankers = NPCs.find(5455, 5456);
										DynamicWaitingFletching.hoverWaitFletching(new Condition() {
											@Override
											public boolean active() {
												General.sleep(100, 200);
												return Inventory.getCount("Bow string") == 0
														|| Inventory.getCount(bowu) == 0
														|| NPCChat.getMessage() != null;
											}
										}, General.random(70000, 100000), Screen.ALL_SCREEN, 60000, bankers);
									}
								}
							} else {
								if (Functions
										.percentageBool(scripts.fletcher.utilities.Variables.PERCENT_MAKE_ALL_YEW_LB)) {
									Utils.randomSleep();
									if (MouseFletch.clickClickable("Make All",
											Interfaces.get(interfaceID).getChild(childID))) {

										RSNPC[] bankers = NPCs.find(5455, 5456);
										DynamicWaitingFletching.hoverWaitFletching(new Condition() {
											@Override
											public boolean active() {
												General.sleep(100, 200);
												return Inventory.getCount("Bow string") == 0 || Inventory.getCount(bowu) == 0 || NPCChat.getMessage() != null;
											}
										}, General.random(30000, 100000), Screen.ALL_SCREEN, 60000, bankers);

									}
								} else {
									int r = General.random(0, 1);
									int number = 1;
									switch (r) {
									case 0:
										number = 1;
										break;
									case 1:
										number = 5;
										break;
									}
									final int number2 = number;
									if (MouseFletch.clickClickable("Make " + number,
											Interfaces.get(interfaceID).getChild(childID))) {
										Utils.randomSleep();
										if (DynamicWaiting.hoverWaitScreen(new Condition() {
											@Override
											public boolean active() {
												General.sleep(100, 200);
												return Interfaces.get(interfaceID) == null;
											}
										}, General.random(3000, 4000), Screen.ALL_SCREEN)) {
											RSNPC[] bankers = NPCs.find(5455, 5456);
											DynamicWaitingFletching.hoverWaitFletching(new Condition() {
												@Override
												public boolean active() {
													General.sleep(100, 200);
													return Inventory.getCount(bow) == number2
															|| NPCChat.getMessage() != null;
												}
											}, General.random(70000, 100000), Screen.ALL_SCREEN, 60000, bankers);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		General.println("Finished stringing");
	}

	public static void typeAmount() {
		if (Functions.percentageBool(Variables.P_TYPE_99)) {
			Keyboard.holdKey('9', 57, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			CustomFlaxAB.randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			Keyboard.holdKey('9', 57, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			CustomFlaxAB.randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			if (Functions.percentageBool(7)) {
				Keyboard.holdKey('9', 57, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				SleepJoe.sleepHumanDelay(1, 30, 60);
			}
			if (Functions.percentageBool(7)) {
				Keyboard.holdKey('9', 57, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				SleepJoe.sleepHumanDelay(1, 30, 60);
			}
			if (Functions.percentageBool(7)) {
				Keyboard.holdKey('9', 57, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				SleepJoe.sleepHumanDelay(1, 30, 60);
			}
		} else {
			if (Functions.percentageBool(Variables.P_TYPE_28)) {
				Keyboard.holdKey('2', 50, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				CustomFlaxAB.randomSleep();
				SleepJoe.sleepHumanDelay(1, 30, 60);
				Keyboard.holdKey('8', 56, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				CustomFlaxAB.randomSleep();
				SleepJoe.sleepHumanDelay(1, 30, 60);
				if (Functions.percentageBool(7)) {
					Keyboard.holdKey('8', 56, new Condition() {
						@Override
						public boolean active() {
							SleepJoe.sleepHumanDelay(0.2, 30, 60);
							return true;
						}
					});
					SleepJoe.sleepHumanDelay(1, 30, 60);
				}
			} else {
				Keyboard.typeString(String.valueOf(General.random(28, 1000)));
			}
		}
		if (Functions.percentageBool(1)) {
			General.sleep(1000, 12000);
		}
		SleepJoe.sleepHumanDelay(1.4, 120, 240);
		CustomFlaxAB.randomSleep();
		Keyboard.pressEnter();
		SleepJoe.sleepHumanDelay(0.2, 30, 60);
	}

}
