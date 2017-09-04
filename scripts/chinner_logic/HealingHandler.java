package scripts.chinner_logic;

import scripts.api.Task;
import scripts.chinner_utilities.Utils;

public class HealingHandler implements Task {

	@Override
	public String action() {
		return "Healing...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Utils.shouldHeal());
	}

	@Override
	public void execute() {
		if (Utils.shouldHealHealth()) {

		}

		if (Utils.isPoisoned()) {

		}
		// Drink pray pot to the max we can.

		//
	}

}
