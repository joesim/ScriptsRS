package scripts.muler.logic;

import scripts.api.Task;
import scripts.muler.utilities.Variables;
import scripts.webwalker_logic.WebWalker;

public class WalkToBank implements Task {

	@Override
	public String action() {
		return "Walking to bank...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return Variables.WALK_TO_BANK;
	}

	@Override
	public void execute() {
		WebWalker.walkToBank();
	}

}
