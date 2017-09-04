package scripts.spinner.logic;

import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.spin_utilities.CustomFlaxAB;
import scripts.spin_utilities.Utils;
import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.utilities.Timer;

public class InterfaceHandler implements Task {

	@Override
	public String action() {
		return "Spinner interface...";
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 7;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return (Interfaces.get(459) != null);
	}

	@Override
	public void execute() {
		if (Functions.percentageBool(Variables.MOUSE_MOVE_SPINNER_MAKEX_PERCENT)) {
			try {
				Interfaces.get(459).getChild(91).click("Make X");
			} catch (NullPointerException e) {
				return;
			}
		} else {
			try {
				MouseMoveJoe.humanMouseMove(new TBox(Interfaces.get(459).getChild(91).getAbsoluteBounds()),
						1.5);
			} catch (NullPointerException e) {
				return;
			}
			if (Functions.percentageBool(98)) {
				SleepJoe.sleepHumanDelay(0.2, 1, 800);
			} else {
				SleepJoe.sleepHumanDelay(5, 1, 10000);
			}
			Utils.clickKey();
		}
		
		if (Functions.percentageBool(Variables.PERCENT_TYPE_BEFORE)){
			Utils.typeAmount();
		}
		
		Timer timeMake = new Timer(1000000000);
		while (!Interfaces.get(162).getChild(42).isHidden() && timeMake.getElapsed() < 5000) {
			General.sleep(200, 500);
		}
		if (timeMake.getElapsed() >= 5000) {
			return;
		}
		SleepJoe.sleepHumanDelay(3, 1, 6000);
		Utils.typeAmount();

		// Antiban------------------------------------------------
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------

		RSObject stairs2 = Functions.findNearestId(15, 16672);
		if (Functions.percentageBool(90)) {
			MouseMoveJoe.hoverMouseSpinning(new TBox(stairs2.getModel().getCentrePoint(), 80), Variables.c3c8,
					General.random(2, 3), true, 30, false, Variables.P_CHANCE_OUT, Variables.P_MOUSE_OUT_ALL);
		} else {
			MouseMoveJoe.hoverMouseSpinning(HoverBox.get(1), Variables.c3c8, General.random(2, 3), true, 30, false,
					Variables.P_CHANCE_OUT, Variables.P_MOUSE_OUT_ALL);
		}
	}

}
