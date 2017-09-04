package scripts.spin_logic;

import org.tribot.api2007.Interfaces;

import scripts.api.Task;
import scripts.utilities.SleepJoe;

public class CloseInterfaces implements Task {

	@Override
	public String action() {
		return "Closing unwanted interfaces...";
	}

	@Override
	public int priority() {
		return 100000;
	}

	@Override
	public boolean validate() {
		return Interfaces.get(192)!=null || Interfaces.get(402)!=null;
	}

	@Override
	public void execute() {
		if (Interfaces.get(192)!=null){
			Interfaces.get(192).getChild(1).getChild(11).click("Close");
		} else {
			Interfaces.get(402).getChild(2).getChild(11).click("Close");
		}
		SleepJoe.sleepHumanDelay(3, 1, 10000);
	}

}
