package scripts.zulrahUtilities;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;

import scripts.Zulrah;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class HealthZul {
	
	private static int eatAtHealth;
	private static int drinkAtPrayer;
	
	
	public HealthZul(){
		eatAtHealth = Functions.generateRandomIntCloserToMin(43, 50);
		drinkAtPrayer = Functions.generateRandomIntCloserToMin(8, 30);
	}
	
	public void checkGeneralhealth() {
		checkHealthUrgent();
		checkHealth();
		checkPrayer();
		checkPoison();
	}

	public void checkPrayer() {
		if (getCheckPrayer()) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
			}
			RSItem pot = Functions.findNearestItemId(InventoryZulrah.POT_FOR_PRAYER);
			if (pot != null) {
				MoveMouseZul.humanMouseMove(new TBox(pot.getArea()), UniqueZulrah.SPEED_FOOD);
				MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				drinkAtPrayer = Functions.generateRandomIntCloserToMin(8, 30);
			}
		}
	}

	public void checkHealth() {

		if (getCheckHealth()) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
			}
			RSItem shark = Functions.findNearestItemId(InventoryZulrah.SHARK);
			if (shark != null) {
				MoveMouseZul.humanMouseMove(new TBox(shark.getArea()), UniqueZulrah.SPEED_FOOD);
				MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				eatAtHealth = Functions.generateRandomIntCloserToMin(43, 50);
			} else {
				RSItem kar = Functions.findNearestItemId(InventoryZulrah.KARAMBWAN);
				if (kar != null) {
					MoveMouseZul.humanMouseMove(new TBox(kar.getArea()), UniqueZulrah.SPEED_FOOD);
					MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 3, 2);
					eatAtHealth = Functions.generateRandomIntCloserToMin(43, 50);
				}

			}
		}

	}

	public void checkHealthUrgent() {

		if ((Functions.getMaxHealth()-Functions.getHealth()) >= InventoryZulrah.COMBO_FOOD_HEALING) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
			}
			RSItem shark = Functions.findNearestItemId(InventoryZulrah.SHARK);
			RSItem kar = Functions.findNearestItemId(InventoryZulrah.KARAMBWAN); // karambwan
			if (shark != null) {
				MoveMouseZul.humanMouseMove(new TBox(shark.getArea()), UniqueZulrah.SPEED_FOOD);
				MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 1, 0);
			}
			if (kar != null) {
				MoveMouseZul.humanMouseMove(new TBox(kar.getArea()), UniqueZulrah.SPEED_FOOD);
				MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				SleepJoe.sleepHumanDelay(1, 0, 600);
			}
		}

	}

	public void checkPoison() {
		if (Game.getSetting(102) > 0) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
			}
			RSItem pot = Functions.findNearestItemId(InventoryZulrah.VENOM_POT);
			if (pot != null) {
				MoveMouseZul.humanMouseMove(new TBox(pot.getArea()), UniqueZulrah.SPEED_FOOD);
				MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
			}
		}
	}

	public static boolean getCheckHealth() {

		int id = Zulrah.zulrah.getID();
		if (id == 2042){
			if ((Functions.getMaxHealth()-Functions.getHealth()) >= InventoryZulrah.COMBO_FOOD_HEALING && (Inventory.find(InventoryZulrah.SHARK)!=null || Inventory.find(InventoryZulrah.KARAMBWAN)!=null)) {
				return true;
			}
		} else if (id == 2043) {
			if ((Functions.getMaxHealth()-Functions.getHealth()) >= InventoryZulrah.COMBO_FOOD_HEALING && (Inventory.find(InventoryZulrah.SHARK)!=null || Inventory.find(InventoryZulrah.KARAMBWAN)!=null)) {
				return true;
			}
		} else if (id == 2044) {
			if (eatAtHealth > Functions.getHealth() && (Inventory.find(InventoryZulrah.SHARK)!=null || Inventory.find(InventoryZulrah.KARAMBWAN)!=null)) {
				return true;
			}
		}
		
		return false;

	}

	public static boolean getCheckPrayer() {
		int id = Zulrah.zulrah.getID();
		int prayGreen = drinkAtPrayer - 5;
		int prayRed = drinkAtPrayer - 4;
		if (id == 2042){
			if (prayGreen >= Skills.SKILLS.PRAYER.getCurrentLevel() && Inventory.find(InventoryZulrah.POT_FOR_PRAYER)!=null) {
				return true;
			}
		} else if (id == 2043) {
			if (prayRed >= Skills.SKILLS.PRAYER.getCurrentLevel() && Inventory.find(InventoryZulrah.POT_FOR_PRAYER)!=null) {
				return true;
			}
		} else if (id == 2044) {
			if (drinkAtPrayer >= Skills.SKILLS.PRAYER.getCurrentLevel() && Inventory.find(InventoryZulrah.POT_FOR_PRAYER)!=null) {
				return true;
			}
		}
		return false;
	}

	
	public void restore() {

		if (Functions.percentageBool(UniqueZulrah.PERCENT_NOT_RESTORING)) {
			return;
		}
		checkPoison();
		while (Functions.getHealth() < (Functions.getMaxHealth() - 20)) {
			if (!GameTab.TABS.INVENTORY.isOpen()) {
				Functions.FTAB(27, 1);
			}
			RSItem shark = Functions.findNearestItemId(InventoryZulrah.SHARK);
			if (shark != null) {
				MoveMouseZul.humanMouseMove(new TBox(shark.getArea()), UniqueZulrah.SPEED_FOOD);
				MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
				eatAtHealth = Functions.generateRandomIntCloserToMin(43, 50);
			} else {
				RSItem kar = Functions.findNearestItemId(InventoryZulrah.KARAMBWAN);
				if (kar != null) {
					MoveMouseZul.humanMouseMove(new TBox(kar.getArea()), UniqueZulrah.SPEED_FOOD);
					MoveMouseZul.spamClick(new TBox(Mouse.getPos(), 10, 10), 3, 2);
					eatAtHealth = Functions.generateRandomIntCloserToMin(43, 50);
				}

			}
		}
	}
	
}
