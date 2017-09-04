package scripts.tabber.tabber_logic;

import java.awt.event.KeyEvent;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Conditions;
import scripts.tabber.utilities.Constants;
import scripts.tabber.utilities.MouseTab;
import scripts.tabber.utilities.Utils;
import scripts.tabber.utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class ClickButler implements Task {

	public static boolean pressingSpaceBar = false;

	@Override
	public String action() {
		return "Clicking butler...";
	}

	@Override
	public int priority() {
		return 4;
	}

	@Override
	public boolean validate() {
		return ((Inventory.getCount(Constants.SOFT_CLAY) <= MakeTabsInterface.numberToFetchAt)
				&& NPCs.find(227).length > 0);
	}

	@Override
	public void execute() {
		RSNPC[] butler = NPCs.find(227);
		if (butler.length > 0) {
		//	pressSpaceBar();
			if (Conditions.messageOuOptions.active() || (ChooseOption.isOpen() && ChooseOption.select("Talk"))
					|| MouseTab.clickButler(butler[0].getModel(), "Talk")) {
			//	pressSpaceBar();
				Utils.randomSleep();
				if (DynamicWaiting.hoverWaitScreen(Conditions.messageOuOptions, General.random(1000, 2000),
						Screen.CHAT_BOX)) {
				//	pressSpaceBar();
					if (NPCChat.getClickContinueInterface() != null) {
						if (NPCChat.getMessage().contains("Your goods")) {
							if (!DynamicWaiting.hoverWaitScreen(Conditions.inventorySoftClay, General.random(3000, 5000),
									Screen.MAIN_SCREEN)){
								clickContinue();
							}
							return;
						} else {
							clickContinue();
							Timing.waitCondition(Conditions.messageOuOptions, General.random(3000, 5000));
							NPCChat.selectOption("Okay", true);
							Timing.waitCondition(Conditions.clickContinueInterface, General.random(3000, 5000));
							clickContinue();
							DynamicWaiting.hoverWaitScreen(Conditions.inventorySoftClay, General.random(3000, 5000),
									Screen.MAIN_SCREEN);
						}

					} else {
						clickFetch();
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								General.sleep(100, 200);
								return NPCChat.getMessage() != null && NPCChat.getMessage().contains("Very good");
							}
						}, General.random(3000, 5000));
						clickContinue();
						Utils.restartTimer();
						if (Inventory.getCount(Constants.SOFT_CLAY) == 0) {
							RSObject[] lecterns = Objects.findNearest(30, Constants.LECTERN);
							RSModel lect = null;
							if (lecterns.length > 0) {
								lect = lecterns[0].getModel();
							}
							DynamicWaiting.hoverWaitTabs(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return NPCs.find(227) != null && !Utils.isButlerOut()
											&& (Inventory.getCount(Constants.SOFT_CLAY) > 10
													|| (NPCChat.getMessage() != null
															&& NPCChat.getMessage().contains("fee")));
								}
							}, General.random(17000, 22000), Screen.MAIN_SCREEN, 5000, lect);
						}
					}
				}
			} else {
				General.sleep(General.randomSD(50, 5000, 200, 2));
			}
			releaseSpaceBar();
		}
	}

	private void clickFetch() {
		if (pressingSpaceBar || Functions.percentageBool(Variables.CLICK_ONE)) {
			MouseTab.clickBox(new TBox(148, 392, 408, 411));
		} else {
			General.sleep(General.randomSD(1, 4000, 300, 2));
			Keyboard.holdKey('1', 49, new Condition() {
				@Override
				public boolean active() {
					General.sleep(General.randomSD(1, 4000, 300, 2));
					return true;
				}
			});
		}

	}

	private void pressSpaceBar() {
		if (!pressingSpaceBar && Functions.percentageBool100000(100)) {
			General.sleep(General.randomSD(0, 2000, 400, 2));
			Keyboard.sendPress(KeyEvent.CHAR_UNDEFINED, 32);
			General.println("Sent press");
			pressingSpaceBar = true;
		}
	}

	private void releaseSpaceBar() {
		if (pressingSpaceBar) {
			General.sleep(General.randomSD(0, 2000, 400, 2));
			Keyboard.sendRelease(' ', 32);
			General.println("Release press");
			pressingSpaceBar = false;
		}
	}

	private static void clickContinue() {
		if (!pressingSpaceBar) {
			if (Functions.percentageBool(Variables.CHANCE_CLICK_CONTINUE)) {
				NPCChat.clickContinue(true);
			} else {
				General.sleep(General.randomSD(1, 4000, 300, 2));
				Keyboard.holdKey(' ', 32, new Condition() {
					@Override
					public boolean active() {
						General.sleep(General.randomSD(1, 4000, 300, 2));
						return true;
					}
				});
			}
		}
	}

}
