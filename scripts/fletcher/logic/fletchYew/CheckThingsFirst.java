package scripts.fletcher.logic.fletchYew;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;

import scripts.api.Task;
import scripts.fletcher.logic.AfterSeventy;
import scripts.fletcher.utilities.Conditions;
import scripts.fletcher.utilities.MouseFletch;
import scripts.fletcher.utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.utilities.SleepJoe;

public class CheckThingsFirst implements Task {

	public static boolean checked = false;
	
	@Override
	public String action() {
		return "Checking bank first...";
	}

	@Override
	public int priority() {
		return 100;
	}

	@Override
	public boolean validate() {
		return !checked;
	}

	@Override
	public void execute() {
		General.println("Checking things first...");
		RSNPC[] bankers = NPCs.find(5455, 5456);
		if ((bankers.length > 0 && MouseFletch.clickNPC("Bank Banker", bankers))) {
			if (DynamicWaiting.hoverWaitScreen(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return org.tribot.api2007.Banking.isBankScreenOpen();
				}
			}, General.random(3000, 10000), Screen.INVENTORY,70)) {
				SleepJoe.sleepHumanDelay(1, 1, 3000);
				Banking.depositAll();
				Timing.waitCondition(Conditions.cEmptyInventory, General.random(5000, 10000));
				Utils.randomSleep();
			}
		}
		checked = true;
		General.println("Finished checking things first...");
	}

}
