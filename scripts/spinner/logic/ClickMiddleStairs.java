package scripts.spinner.logic;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
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

public class ClickMiddleStairs implements Task{

	@Override
	public String action() {
		return "Clicking middle stairs...";
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public boolean validate() {
		return ((Inventory.getCount(Variables.BOWSTRING_ID)>=27 ||  Inventory.getAll().length<8) && Game.getPlane()==1);
	}

	@Override
	public void execute() {

		RSObject stairs2 = Functions.findNearestId(15, 16672);
		
		// Antiban------------------------------------------------
		CustomFlaxAB.randomSleep();
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------
		
		Utils.openDoor();

		// Antiban------------------------------------------------
		CustomFlaxAB.randomSleep();
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------
		boolean cont = false;
		long t = Timing.currentTimeMillis();
		int number = 0;
		do {
			number++;
			MouseMoveJoe.playMouseFollowObject(stairs2, "Climb",
					Functions.randomDouble(Variables.SPEED_CLICK_STAIRS_MIN, Variables.SPEED_CLICK_STAIRS_MAX), null, true, 0, 0, 0,
					0, 10);
			if (Game.isUptext("Climb")) {
				if (Functions.percentageBool(Variables.P_CLIMBUP_RIGHT)) {
					MouseMoveJoe.fastClick(3, Variables.SPEED_FAST_CLICK);
					Point pt = Mouse.getPos();
					TBox boxDown = new TBox(pt.x - 72, pt.y + 14, pt.x + 54, pt.y + 32);
					TBox box = new TBox(pt.x - 72, pt.y + 34, pt.x + 54, pt.y + 46);
					TBox boxUp = new TBox(pt.x - 72, pt.y + 52, pt.x + 54, pt.y + 70);
					if (Functions.percentageBool(10)) {
						MouseMoveJoe.humanMouseMove(boxUp, General.random(3, 6));
					} else if (Functions.percentageBool(6)) {
						MouseMoveJoe.humanMouseMove(boxDown, General.random(3, 6));
					}
					MouseMoveJoe.humanMouseMove(box, General.random(3, 6));
					MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
					if (Game.getCrosshairState() == 2 || Game.getPlane()==2) {
						cont = true;
					}
				} else {
					if (Functions.percentageBool(Variables.P_CLICK_ONETIME_STAIRS2)) {
						MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
					} else {
						MouseMoveJoe.spamClick(new TBox(0, 0, 10, 10), 2, 1);
					}
					if (Game.getCrosshairState() == 2) {
						MouseMoveJoe.hoverMouse(HoverBox.get(29, 80, 1), Variables.c9c12, 1, true, 30, true,10000);
						if (Variables.c12.checkCondition()) {
							break;
						}
						MouseMoveJoe.humanMouseMove(new TBox(180, 395, 421, 410), General.random(3, 6));
						MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
						cont = true; 
					}
				}

			}
			if (!cont) {
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}
		} while (!cont && (Timing.currentTimeMillis()-t)<15000 && number<3);

		// Antiban------------------------------------------------
		CustomFlaxAB.thingsToCheck();
		// -------------------------------------------------------

		MouseMoveJoe.hoverMouse(HoverBox.get(36, Variables.P_HOVERMM, 1), Variables.c4c12, General.random(2, 3), true, Variables.P_CHANCE_MOVE_WHEN_MOUSE_MM,
				true,10000);
		
	}

}
