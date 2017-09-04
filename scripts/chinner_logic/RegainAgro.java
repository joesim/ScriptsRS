package scripts.chinner_logic;

import scripts.api.Task;
import scripts.chinner_utilities.Utils;

public class RegainAgro implements Task {

	@Override
	public String action() {
		return "Re-gaining agro...";
	}

	@Override
	public int priority() {
		return 0;
	}

	@Override
	public boolean validate() {
		return (Utils.monkeyStopAttacking() && !Utils.shouldHeal() && !Utils.shouldTeleOut());
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
