package scripts.spinner.logic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Interfaces;

import scripts.api.Task;

public class ReportClickExit implements Task {

	@Override
	public String action() {
		return "Clicking report exit...";
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public boolean validate() {
		return Interfaces.get(553)!=null;
	}

	@Override
	public void execute() {
		Interfaces.get(553).getChild(1).getChild(11).click("Close");
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				return Interfaces.get(553)==null;
			}
		}, General.random(3000, 5000));
	}

}
