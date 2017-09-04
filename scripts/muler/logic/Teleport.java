package scripts.muler.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.muler.utilities.Variables;

public class Teleport implements Task{
	
	@Override
	public String action() {
		return "Teleporting";
	}

	@Override
	public int priority() {
		return 2;
	}

	@Override
	public boolean validate() {
		return Inventory.getCount("Varrock teleport")>0 && !Banking.isBankScreenOpen() && Variables.WALK_TO_BANK;
	}

	@Override
	public void execute() {
		if (Inventory.open()){
			Inventory.find("Varrock teleport")[0].click("Break");
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(100, 200);
					return Constants.varrock.contains(Player.getPosition());
				}
			}, General.random(10000, 15000));
		}
	}

}
