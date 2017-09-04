package scripts.potato_picker.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.webwalker_logic.WebWalker;

public class WalkToPotato implements Task {

	@Override
	public int priority() {
		return 2;
	}

	@Override
	public boolean validate() {
		return (Inventory.getAll().length==0 && !PickPotato.potatoArea.contains(Player.getPosition()));
	}

	@Override
	public void execute() {
		final RSTile destinationPotato = new RSTile(General.random(3144, 3148), General.random(3284,3288), 0);
		WebWalker.walkTo(destinationPotato);
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				if (PickPotato.potatoArea.contains(Player.getPosition())){
					return true;
				}
				return false;
			}
		}, General.random(5000, 7000));
	}

	@Override
	public String action() {
		return "Walking to potatoes...";
	}

}
