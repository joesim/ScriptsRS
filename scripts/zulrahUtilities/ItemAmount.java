package scripts.zulrahUtilities;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;

import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class ItemAmount {

	public int[] ITEM_IDS;
	public int amount;


	public ItemAmount(int amount, int... ids) {
		this.amount = amount;
		this.ITEM_IDS = ids;
	}

	public boolean withdrawItem() {
		if (this.amount != -1) {
			this.amount = this.amount - Inventory.find(ITEM_IDS).length;
			if (amount <= 0) {
				return true;
			}
		}
		RSItem theThing;
		do {
			theThing = pickAvailableIDBank();
			if (theThing == null) {
				General.sleep(100);
			}
		} while (theThing == null);
		if (amount == 1) {
			MoveMouseZul.humanMouseMove(new TBox(theThing.getArea()), UniqueZulrah.SPEED_WITHDRAW_ITEMS);
			MoveMouseZul.fastClickBank(1, 1);
			return true;
		} else if (amount > 1) {
			MoveMouseZul.humanMouseMove(new TBox(theThing.getArea()), UniqueZulrah.SPEED_WITHDRAW_ITEMS);
			if (amount == 5 && Functions.percentageBool(10)) {
				Banking.withdrawItem(theThing, 5);
			} else {
				for (int i = 0; i < amount; i++) {
					MoveMouseZul.fastClickBank(1, 1);
				}
				if (amount > UniqueZulrah.NUMBER_ITEMS_VARIABLE_LIMIT && Functions.percentageBool(UniqueZulrah.PERCENT_CLICK_ANOTHER_ITEM)) {
					MoveMouseZul.fastClickBank(1, 1);
				}
			}
			return true;
		} else if (amount == -1 && Inventory.getAll().length<27) {
			MoveMouseZul.humanMouseMove(new TBox(theThing.getArea()), UniqueZulrah.SPEED_WITHDRAW_ITEMS);
			MoveMouseZul.fastClick(3, 1);
			Point pt = new Point((int) Mouse.getPos().getX(), (int) (Mouse.getPos().getY() + 71));
			SleepJoe.sleepHumanDelay(0.5, 0, 600);
			Mouse.hop(pt);
			SleepJoe.sleepHumanDelay(0.3, 0, 600);
			MoveMouseZul.fastClick(1, 1);
			SleepJoe.sleepHumanDelay(1, 1, 1000);
			return true;
		}
		return false;
	}

	public RSItem pickAvailableIDBank() {

		if (ITEM_IDS.length == 1) {
			return Functions.findNearestBankItem(ITEM_IDS[0]);
		} else if (ITEM_IDS.length > 1) {
			for (int id : ITEM_IDS) {
				RSItem item = Functions.findNearestBankItem(id);
				if (Functions.percentageBool(UniqueZulrah.PERCENT_PICK_GOOD_ITEM_BANKING) && item != null) {
					return item;
				}
			}

			for (int id : ITEM_IDS) {
				RSItem item = Functions.findNearestBankItem(id);
				if (item != null) {
					return item;
				}
			}
		}
		return null;
	}
}
