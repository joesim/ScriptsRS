package scripts.potato_picker.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.webwalker_logic.WebWalker;

public class WalkToBank implements Task {

	@Override
	public int priority() {
		return 3;
	}
	
	@Override
	public boolean validate() {
		//Second part of expression: mostly to bank items first after tutorial island instead of picking potatoes.
		return (Inventory.isFull() || (Inventory.getAll().length>0 && !PickPotato.potatoArea.contains(Player.getPosition())));
	}	
	
	@Override
	public void execute() {
		WebWalker.walkToBank();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				if (Banking.isInBank()){
					return true;
				}
				return false;
			}
		}, General.random(3000, 4000));
	}

	@Override
	public String action() {
		return "Walking to bank...";
	}
	

}
