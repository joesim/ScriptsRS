package scripts.fletcher_v2.utilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;

public class Conditions {

	public static Condition cEmptyInventory = new Condition() {

		@Override
		public boolean active() {
			General.sleep(100, 200);
			return Inventory.getAll().length == 0;
		}
	};

	public static Condition bankScreenOpen = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return org.tribot.api2007.Banking.isBankScreenOpen();
		}
	};

	public static Condition onlyKnife = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return Inventory.getAll().length == 1 && Inventory.getCount("Knife") == 1;
		}
	};

	public static Condition coinsInInv = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			General.println("Here");
			return Inventory.getCount("Coins") > 0;
		}
	};

	public static Condition bankScreenNotOpenAndInvFull = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return Inventory.getAll().length == 28;
		}
	};

	public static Condition justWait = new Condition() {
		@Override
		public boolean active() {
			General.sleep(100, 200);
			return false;
		}
	};

}
