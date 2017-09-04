package scripts.spin_utilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;

import scripts.utilities.AntiBan;
import scripts.utilities.Functions;

public class CustomFlaxAB {

	
	public static void randomSleep() {
		if (Functions.percentageBool100000(Variables.P_RANDOMSLEEP)) {
			General.sleep(200, 5000);
		}
	}
	
	public static void thingsToCheck() {
		AntiBan.timedActionsNoCam();
		AntiBan.activateRun();
		if (Functions.percentageBool100000(Variables.P_MOUSE_LEAVE)) {
			Mouse.leaveGame();
			General.sleep(1000, 30000);
		}
		AntiBan.timedActionsNoCam();
	}
	
}
