package scripts.tabber.tabber_logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Constants;

public class EnterHouse implements Task {

	@Override
	public String action() {
		return "Entering house...";
	}

	@Override
	public int priority() {
		return 1000;
	}

	@Override
	public boolean validate() {
		return Objects.find(10, 15478).length>0;
	}

	@Override
	public void execute() {
		Objects.find(10, 15478)[0].click("Build mode Portal");
		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				General.sleep(100,200);
				return Objects.find(20, Constants.LECTERN).length>0;
			}
		}, General.random(5000, 10000), Screen.ALL_SCREEN);
	}

}
