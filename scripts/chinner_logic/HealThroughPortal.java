package scripts.chinner_logic;

import org.tribot.api2007.Player;

import scripts.api.Task;
import scripts.chinner_utilities.Constants;

public class HealThroughPortal implements Task {

	@Override
	public String action() {
		return "Healing through the clan wars portal...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return Constants.clanWarsArea.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		
		
		
	}

}
