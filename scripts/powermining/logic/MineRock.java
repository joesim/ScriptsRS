package scripts.powermining.logic;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.powermining.utilities.Constants;

public class MineRock implements Task {

	@Override
	public String action() {
		return "Mining a rock...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return Inventory.getAll().length<Constants.MAX_ORE;
	}

	@Override
	public void execute() {
		RSObject[] rocks = Objects.findNearest(5, Constants.TIN_COPPER);
		if (rocks.length>0){
			
		}
	}

}
