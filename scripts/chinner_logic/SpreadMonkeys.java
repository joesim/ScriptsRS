package scripts.chinner_logic;

import scripts.api.Task;
import scripts.chinner_utilities.Utils;

public class SpreadMonkeys implements Task {

	@Override
	public String action() {
		return "Spreading monkeys...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Utils.shouldSpreadMonkeys() && !Utils.shouldHeal() && !Utils.monkeyStopAttacking() && !Utils.shouldTeleOut());
	}

	@Override
	public void execute() {
		
	}

}
