package scripts.zulrahUtilities;

import java.util.ArrayList;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class InventoryZulrah {

	public static final int AB = 4874;
	public static final int AB75 = 4875;
	public static final int AB50 = 4876;
	public static final int AB25 = 4877;
	public static final int AT = 4868;
	public static final int AT75 = 4869;
	public static final int AT50 = 4870;
	public static final int AT25 = 4871;
	public static final int IB = 6920;
	public static final int STAFF = 12899;
	public static final int BOOK = 3842;
	public static final int OCCULT_NECKLACE = 12002;
	public static final int GOD_CAPE = 2413;

	public static final int[] RANGE_POT = { 2444, 169, 171, 173 };
	public static final int[] VENOM_POT = { 12913, 12915, 12917, 12919 };
	public static final int[] MAGE_POT = { 3040, 3042, 3044, 3046 };
	public static final int[] R_DUELING = { 2552, 2554, 2556, 2558, 2560, 2562 };
	public static final int[] SUPER_RESTORE_POTS = { 3024, 3026, 3028, 3030 };
	
	public static final int GUTHIX_BOOTS = 19927;
	public static final int GUTHIX_BODY = 10378;
	public static final int SUPER_RESTORE = 3024;
	public static final int TOXB = 12926;
	public static final int BLACK_CHAPS = 2497;
	public static final int FURY = 6585;
	public static final int AVA = 10499;
	public static final int TELE_ZUL = 12938;
	public static final int SHARK = 385;
	public static final int KARAMBWAN = 3144;
	public static final int CAMELOT_TELE = 8010;
	public static final int COINS = 995;

	public static final int GUTHIX_BLESSING = 20226;
	public static final int COMBAT_BRACELET = 11972;
	public static final int RING_OF_RECOIL = 2550;

	// This shit s going to change
	public final static int[] gearToSwitchMage = { AB, AB75, AB50, AB25, AT, AT75, AT50, AT25, IB, STAFF, BOOK,
			OCCULT_NECKLACE, GOD_CAPE, GUTHIX_BLESSING, COMBAT_BRACELET, RING_OF_RECOIL };
	public final static int[] gearToSwitchRanged = { GUTHIX_BOOTS, GUTHIX_BODY, TOXB, BLACK_CHAPS, FURY, AVA,
			GUTHIX_BLESSING, COMBAT_BRACELET, RING_OF_RECOIL };	
	
	public final static ItemAmount[] configScroll = { new ItemAmount(1, GUTHIX_BOOTS), new ItemAmount(1, GUTHIX_BODY),
			new ItemAmount(1, SUPER_RESTORE), new ItemAmount(1, RANGE_POT), new ItemAmount(1, TOXB),
			new ItemAmount(1, BLACK_CHAPS), new ItemAmount(1, VENOM_POT), new ItemAmount(1, MAGE_POT),
			new ItemAmount(1, FURY), new ItemAmount(1, AVA), new ItemAmount(1, TELE_ZUL), new ItemAmount(1, R_DUELING),
			new ItemAmount(5, KARAMBWAN), new ItemAmount(-1, SHARK) };
	
	public final static ItemAmount[] configCharter = { new ItemAmount(1, GUTHIX_BOOTS), new ItemAmount(1, GUTHIX_BODY),
			new ItemAmount(1, SUPER_RESTORE), new ItemAmount(1, RANGE_POT), new ItemAmount(1, TOXB),
			new ItemAmount(1, BLACK_CHAPS), new ItemAmount(1, VENOM_POT), new ItemAmount(1, MAGE_POT),
			new ItemAmount(1, FURY), new ItemAmount(1, AVA), new ItemAmount(1, CAMELOT_TELE), new ItemAmount(-1, COINS), new ItemAmount(1, R_DUELING),
			new ItemAmount(5, KARAMBWAN), new ItemAmount(-1, SHARK) };
	
	public final static ItemAmount[] bankInventory = configScroll;
	
	public final static int MAIN_FOOD = SHARK;
	public final static int TICK_FOOD = KARAMBWAN;
	public final static int[] POT_FOR_PRAYER = SUPER_RESTORE_POTS;
	public static final int COMBO_FOOD_HEALING = 38;

	public static void bank() {
		depositNotWantedItems();
		withdrawItems();
	}

	public static void withdrawItems() {
		if (!checkOk()) {
			for (ItemAmount i : bankInventory) {
				i.withdrawItem();
				if (Functions.percentageBool(UniqueZulrah.PERCENT_WAIT_WITHDRAW)) {
					SleepJoe.sleepHumanDelay(2, 1, 4000);
				}
			}
		}
	}

	public static void depositNotWantedItems() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (ItemAmount i : bankInventory) {
			for (int id : i.ITEM_IDS) {
				list.add(id);
			}
		}
		int[] listeInt = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			listeInt[i] = list.get(i);
		}
		for (ItemAmount iA : bankInventory) {
			RSItem[] lesItems = Inventory.find(iA.ITEM_IDS);
			if (lesItems.length > iA.amount && iA.amount != -1) {
				for (int i = 0; i < (lesItems.length - iA.amount); i++) {
					lesItems[i].click("Deposit");
				}
			}
		}
		Banking.depositAllExcept(listeInt);
	}

	public static boolean checkOk() {

		for (ItemAmount iA : bankInventory) {
			RSItem[] lesItems = Inventory.find(iA.ITEM_IDS);
			if (lesItems.length != iA.amount && iA.amount != -1
					&& iA.amount < UniqueZulrah.NUMBER_ITEMS_VARIABLE_LIMIT) {
				return false;
			}
		}
		if (Inventory.getAll().length > 26) {
			return true;
		}
		return false;

	}

	public static boolean checkMageGearOn() {
		RSItem[] items = Inventory.find(gearToSwitchMage);
		if (items.length == 0) {
			return true;
		}
		return false;
	}
}
