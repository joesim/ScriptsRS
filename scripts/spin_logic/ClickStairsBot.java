package scripts.spin_logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.webwalker_logic.WebWalker;

public class ClickStairsBot implements Task {

	@Override
	public String action() {
		return "Clicking stairs bottom...";
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public boolean validate() {
		return Game.getPlane()==0;
	}

	@Override
	public void execute() {
		WebWalker.walkTo(new RSTile(3205,3209,1));
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Game.getPlane()==1;
			}
		}, General.random(3000, 6000));
	}

}
