package scripts.chinner_logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;

import scripts.api.Task;
import scripts.chinner_utilities.Utils;
import scripts.webwalker_logic.WebWalker;
import scripts.webwalker_logic.shared.helpers.BankHelper;

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
		return (Utils.isInNeedOfSupplies() || !Utils.isPlayerAtApeAtoll());//et player at cw bank.
	}

	@Override
	public void execute() {
		WebWalker.walkToBank();
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				if (BankHelper.isInBank()){
					return true;
				}
				return false;
			}
		}, General.random(3000, 4000));
	}

}
