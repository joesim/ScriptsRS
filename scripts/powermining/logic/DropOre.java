package scripts.powermining.logic;

import org.tribot.api.General;
import org.tribot.api2007.Inventory;

import scripts.api.Task;
import scripts.powermining.utilities.Constants;
import scripts.utilities.ShiftDrop;
import scripts.utilities.SleepJoe;

public class DropOre implements Task {

	@Override
	public String action() {
		return "Dropping ore...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return Inventory.getAll().length>=Constants.MAX_ORE;
	}

	@Override
	public void execute() {
		
		//Shiftdrop
		ShiftDrop.shiftDrop(Constants.THINGS_TO_DROP);
		SleepJoe.sleepHumanDelay(1, 1, 1000);
		
		//Generating new ore count drop at.
		int number =General.randomSD(23, 36, 1);
		if (number<=28){
			Constants.MAX_ORE = number;
		} else {
			Constants.MAX_ORE = 28;
		}
	}

}
