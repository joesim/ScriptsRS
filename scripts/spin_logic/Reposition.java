package scripts.spin_logic;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.webwalker_logic.WebWalker;

public class Reposition implements Task {

	@Override
	public String action() {
		return "Reposition";
	}

	@Override
	public int priority() {
		return 100000000;
	}

	@Override
	public boolean validate() {
		return (Game.getPlane()==1 && Player.getPosition().getY()>=3216);
	}

	@Override
	public void execute() {
		WebWalker.walkToBank();
	}

}
