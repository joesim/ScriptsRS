package scripts.zulrahUtilities;

import org.tribot.api.DynamicClicking;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;

import scripts.Zulrah;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public final class LootingZul {


	public static void loot() {
		MoveMouseZul.hoverMouse(new TBox(Mouse.getPos(), 70), new ConditionTime(SleepJoe.sleepHumanDelayOut(3, 1, 10000)),
				Zulrah.SPEED_HOVER, false);

		if (Functions.percentageBool(UniqueZulrah.PERCENT_PRAY_OFF_LOOTING_FIRST)) {
			PrayerZul.prayOff();
		}
		CustomAntiban.antiban();
		while (Functions.findNearestId(20, 11701) == null) {
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		SleepJoe.sleepHumanDelay(0.2, 1, 2000);
		RSGroundItem[] rsList = GroundItems.getAll();
		CustomAntiban.antiban();
		if (Inventory.getAll().length>25){
			 int diff = 3 - (28-Inventory.getAll().length);
			 RSItem[] sharkToEat = Inventory.find(InventoryZulrah.MAIN_FOOD, InventoryZulrah.TICK_FOOD);
			 for (int i=0;i<diff;i++){
				 sharkToEat[i].click("Eat");
			 }
			 
		}
		CustomAntiban.antiban();
		if (rsList.length > 0) {
			for (RSGroundItem it : rsList) {
				if (it.getID() != 810) {
					do {
						DynamicClicking.clickRSGroundItem(it, "Take");
						SleepJoe.sleepHumanDelay(UniqueZulrah.CLICK_DELAY_LOOT, 1, 1000);
					} while (GroundItems.find(it.getID()).length > 0);
				}
			}
			if (Functions.percentageBool(30)) {
				MoveMouseZul.fastClick(1, 1);
			}
		}
		if (Functions.percentageBool(UniqueZulrah.PERCENT_PRAY_OFF_LOOTING_FIRST)) {
			PrayerZul.prayOff();
		}
		CustomAntiban.antiban();
		SleepJoe.sleepHumanDelay(0.3, 1, 300);
		CustomAntiban.antiban();
		PrayerZul.prayOff();
		Zulrah.state = ZulrahState.BANKING;

	}

}
