package scripts.tabber;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.types.RSModel;
import org.tribot.script.Script;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.tabber.tabber_logic.EnterHouse;
import scripts.tabber.tabber_logic.GoToLecternSpot;

public class TestTabs extends Script {

	@Override
	public void run() {
//		Task t = new GoToLecternSpot();
//		if (t.validate()){
//			t.execute();
//		}
		Camera.setCameraRotation(0);
		Keyboard.sendPress(' ', 32);
		sleep(30000000);
	}

}
