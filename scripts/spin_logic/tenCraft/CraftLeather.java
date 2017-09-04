package scripts.spin_logic.tenCraft;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Magic;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Skills;

import scripts.api.Task;
import scripts.muler.Muler;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spin_logic.GettingTenCraft;
import scripts.utilities.Functions;

public class CraftLeather implements Task {

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
		return (GettingTenCraft.BUY_BEFORE_CRAFT && GettingTenCraft.JUST_CRAFT) || (GettingTenCraft.boughtShit);
	}

	@Override
	public void execute() {
		if (Banking.openBank()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					if (Banking.isBankScreenOpen()) {
						return true;
					}
					return false;
				}
			}, General.random(10000, 15000));
			if (Banking.isBankScreenOpen()) {
				Banking.depositAll();
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Inventory.getAll().length == 0;
					}
				}, General.random(10000, 15000));

				while (!Banking.withdraw(0, "Needle")) {
					General.sleep(100, 1000);
				}
				General.sleep(1000, 2000);
				while (!Banking.withdraw(0, "Thread")) {
					General.sleep(100, 1000);
				}
				General.sleep(1000, 2000);
				while (!Banking.withdraw(0, "Leather")) {
					General.sleep(100, 1000);
				}
				General.sleep(1000, 2000);
				Banking.close();
			}
		} else {
			General.sleep(1000, 2000);
			return;
		}
		// 1: craft, 2: bank
		int state = 1;
		while (Skills.getActualLevel(Skills.SKILLS.CRAFTING) < 10) {
			if (!Banking.isBankScreenOpen() && Inventory.getCount("Leather") > 0) {
				state = 1;
			} else {
				state = 2;
			}

			switch (state) {
			case 1:
				if (Inventory.find("Leather")[0].click("Leather")) {
					General.sleep(50, 500);
					if (Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(10, 20);
							return Game.isUptext("->");
						}
					}, General.random(2000, 5000))) {
						if (Inventory.find("Needle")[0].click("Needle"))
							if (Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									General.sleep(100, 200);
									return Interfaces.get(154) != null;
								}
							}, General.random(10000, 15000))) {
								if (Interfaces.get(154).getChild(126).click("Make All")) {
									DynamicWaiting.hoverWaitScreen(new Condition() {
										@Override
										public boolean active() {
											General.sleep(100, 200);
											return Inventory.getCount("Leather") == 0 || NPCChat.getMessage() != null;
										}
									}, General.random(120000, 180000), Screen.ALL_SCREEN);
									if (NPCChat.getMessage() != null && Functions.percentageBool(50)) {
										NPCChat.clickContinue(false);
									}
								}

							}
					}
				}
				break;
			case 2:
				if (Banking.openBank()) {
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(100, 200);
							if (Banking.isBankScreenOpen()) {
								return true;
							}
							return false;
						}
					}, General.random(10000, 15000));
					if (Banking.isBankScreenOpen()) {
						Banking.deposit(0, "Leather gloves");
						General.sleep(500, 2000);
						Banking.withdraw(0, "Needle");
						General.sleep(500, 2000);
						Banking.withdraw(0, "Thread");
						General.sleep(500, 2000);
						Banking.withdraw(0, "Leather");
						General.sleep(500, 2000);
						Banking.close();
					}
				}
				break;
			}
		}

		if (Banking.openBank()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					if (Banking.isBankScreenOpen()) {
						return true;
					}
					return false;
				}
			}, General.random(10000, 15000));
			if (Banking.isBankScreenOpen()) {
				while (Inventory.getAll().length != 0) {
					Banking.depositAll();
					General.sleep(500, 2000);
				}
				while (Inventory.find("Flax").length == 0) {
					Banking.withdraw(General.random(28, 10000), "Flax");
					General.sleep(500, 3000);
				}
				General.sleep(500, 2000);
				Banking.close();
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						if (!Banking.isBankScreenOpen()) {
							return true;
						}
						return false;
					}
				}, General.random(10000, 15000));
				if (GameTab.open(TABS.MAGIC)) {
					General.sleep(1000, 4000);
					Magic.selectSpell("Lumbridge Home Teleport");
					General.sleep(20000, 30000);
					Muler.stop = true;
				}

			}
		}

		GettingTenCraft.stop = true;
	}

}
