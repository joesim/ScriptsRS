package scripts.muler.logic;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.muler.utilities.Constants;
import scripts.muler.utilities.Variables;
import scripts.webwalker_logic.WebWalker;

public class WalkToGE implements Task {

	@Override
	public String action() {
		return "Walking to GE";
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public boolean validate() {
		return Constants.varrock.contains(Player.getPosition()) || !Variables.WALK_TO_BANK;
	}

	@Override
	public void execute() {
		WebWalker.walkTo(new RSTile(3165,3486,0));
		Mouse.leaveGame();
		General.sleep(15000, 30000);
	}

}
