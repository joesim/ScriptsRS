package scripts.zulrahUtilities;

import java.util.ArrayList;
import java.util.Collections;

import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class Switching {	
	
	public static void correctSwitch(boolean mage) {
		if (mage) {
			switchGearMagic();
		} else {
			switchGearRanged();
		}
	}

	public static void correctPray(boolean prayMage, boolean prayEagle) {
		if (prayMage) {
			PrayerZul.checkMage();
		} else {
			PrayerZul.checkRanged();
		}
		if (prayEagle) {
			PrayerZul.checkEagle();
		} else {
			PrayerZul.checkMystic();
		}
	}

	public static void switchGearRangedComplete() {
		RSItem[] items = Inventory.find(InventoryZulrah.gearToSwitchRanged);

		if (items != null) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
				SleepJoe.sleepHumanDelay(UniqueZulrah.TABS_SLEEP_MULTIPLIER, 1, 100);
			}
		}
		if (Functions.percentageBool(UniqueZulrah.PERCENT_SWITCH_CORRECT_ORDER)) {
			for (RSItem item : items) {
				MoveMouseZul.humanMouseMove(new TBox(item.getArea()), UniqueZulrah.SPEED_MOUSE_SWITCH);
				MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
			}
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j<items.length;j++) {
				list.add(j);
			}
			Collections.shuffle(list);
			for (Integer i : list) {
				MoveMouseZul.humanMouseMove(new TBox(items[i].getArea()), UniqueZulrah.SPEED_MOUSE_SWITCH);
				MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
			}
		}

	}
	
	public static void switchGearRanged() {
		RSItem[] items = Inventory.find(InventoryZulrah.gearToSwitchRanged);

		if (items != null) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
				SleepJoe.sleepHumanDelay(UniqueZulrah.TABS_SLEEP_MULTIPLIER, 1, 100);
			}
		}
		if (Functions.percentageBool(UniqueZulrah.PERCENT_SWITCH_CORRECT_ORDER)) {
			for (RSItem item : items) {
				MoveMouseZul.humanMouseMove(new TBox(item.getArea()), UniqueZulrah.SPEED_MOUSE_SWITCH);
				MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
			}
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j<items.length;j++) {
				list.add(j);
			}
			Collections.shuffle(list);
			for (Integer i : list) {
				MoveMouseZul.humanMouseMove(new TBox(items[i].getArea()), UniqueZulrah.SPEED_MOUSE_SWITCH);
				MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
			}
		}

	}

	public static void switchGearMagic() {

		RSItem[] items = Inventory.find(InventoryZulrah.gearToSwitchMage);
		if (items != null) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
				SleepJoe.sleepHumanDelay(UniqueZulrah.TABS_SLEEP_MULTIPLIER, 1, 100);
			}
		}
		if (Functions.percentageBool(UniqueZulrah.PERCENT_SWITCH_CORRECT_ORDER)) {
			for (RSItem item : items) {
				MoveMouseZul.humanMouseMove(new TBox(item.getArea()), UniqueZulrah.SPEED_MOUSE_SWITCH);
				MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
			}
		} else {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j<items.length;j++) {
				list.add(j);
			}
			Collections.shuffle(list);
			for (Integer i : list) {
				MoveMouseZul.humanMouseMove(new TBox(items[i].getArea()), UniqueZulrah.SPEED_MOUSE_SWITCH);
				MoveMouseZul.fastClick(1, UniqueZulrah.CLICK_SPEED);
			}
		}

	}
	

	public static void switchRapid() {
		Functions.FTAB(112, 1);
		MoveMouseZul.humanMouseMove(new TBox(655, 256, 715, 289), 0.5);
		SleepJoe.sleepHumanDelay(0.2, 1, 100);
		MoveMouseZul.fastClick(1, 0.5);

	}

	public static void switchLongRange() {
		Functions.FTAB(112, 1);
		MoveMouseZul.humanMouseMove(new TBox(570, 308, 632, 343), 0.5);
		SleepJoe.sleepHumanDelay(0.2, 1, 100);
		MoveMouseZul.fastClick(1, 0.5);
	}

	
}
