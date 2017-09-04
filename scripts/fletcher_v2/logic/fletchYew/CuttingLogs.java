package scripts.fletcher_v2.logic.fletchYew;

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
import scripts.fletcher_v2.logic.AfterSeventy;
import scripts.fletcher_v2.utilities.DynamicWaitingFletching;
import scripts.fletcher_v2.utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;

public class CuttingLogs implements Task {

	public static int level = 1;
	
	private int interfaceID = 305;
	private int childID = 14;
	private String logs = "Yew logs";
	private String bow = "Yew longbow (u)";
	
	@Override
	public String action() {
		return "Cutting logs...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return AfterSeventy.cutting && (Inventory.getCount(logs) > 0 && Inventory.getCount("Knife") > 0);
	}


	@Override
	public void execute() {
		General.println("Cutting");
		RSItem[] knife = Inventory.find("Knife");
		RSItem[] logsItem = Inventory.find(this.logs);
		if (knife.length > 0 && logsItem.length > 0) {
			boolean knifeFirst = Functions.percentageBool(scripts.fletcher.utilities.Variables.PERCENT_KNIFE_FIRST);
			if ((knifeFirst && (Game.isUptext("Use Knife ->") || MouseMoveJoe.clickItem("Use", knife,1))) || !knifeFirst) {
				Utils.randomSleep();
				SleepJoe.sleepHumanDelay(1, 1, 3000);
				if (MouseMoveJoe.clickItem(logs, logsItem,1)) {
					Utils.randomSleep();
					if (knifeFirst || Game.isUptext("Use Knife ->") || MouseMoveJoe.clickItem("Use", knife,1)) {
						Utils.randomSleep();
						if (DynamicWaiting.hoverWaitScreen(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return Interfaces.get(interfaceID) != null
										&& Interfaces.get(interfaceID).getChild(childID) != null;
							}
						}, General.random(3000, 5000), Screen.LONGBOW, 60)) {
							if (Functions.percentageBool(scripts.fletcher.utilities.Variables.PERCENT_MAKE_ALL)) {
								Utils.randomSleep();
								if (MouseMoveJoe.clickClickable("Make X",
										Interfaces.get(interfaceID).getChild(childID),2)) {
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
												return Inventory.getCount(logs) == 0 || NPCChat.getMessage() != null;
											}
										}, General.random(70000, 100000), Screen.ALL_SCREEN, 60000, bankers);
									}
								}
							} else {
								int r = General.random(0, 2);
								int number = 1;
								switch (r) {
								case 0:
									number = 1;
									break;
								case 1:
									number = 5;
									break;
								case 2:
									number = 10;
									break;
								}
								final int number2 = number;
								if (MouseMoveJoe.clickClickable("Make " + number,
										Interfaces.get(interfaceID).getChild(childID),1)) {
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
												return Inventory.getCount(bow) == number2 || NPCChat.getMessage() != null;
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
		General.println("Finished cutting");
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
