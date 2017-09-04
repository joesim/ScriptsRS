package scripts.fletcher_v2.utilities;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSNPC;

import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Variables;
import scripts.utilities.AntiBan;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public class DynamicWaitingFletching extends DynamicWaiting {

	
	public static boolean hoverWaitFletching(Condition c, int timeOut, Screen screen, int maxOut, RSNPC[] npc) {

		boolean mouseOut = false;
		if (Functions.percentageBool(Variables.CHANCE_MOUSE_OUT)) {
			mouseOut = true;
		}

		if (Functions.percentageBool100000(Variables.MOUSE_OUT_GOOD_TIME)) {
			General.println("Out of game for a good amount of time");
			Mouse.leaveGame();
			Mouse.move(new Point(General.random(0, 600), General.random(-600,-10)));
			General.sleep(General.randomSD(60000, 250000, 3));
		}

		Integer mSpeed = Mouse.getSpeed();
		Mouse.setSpeed(SPEED_HOVER);
		long t = Timing.currentTimeMillis();
		while (!c.active() && (Timing.currentTimeMillis() - t) < timeOut) {

			if (Functions.percentageBool100000(Variables.PERCENT_RIGHT_CLICK) && npc.length != 0) {
				MouseFletch.rightClickNPC("patate", npc);
				if (Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return ChooseOption.isOpen();
					}
				}, General.random(1000, 2000))) {
					if (Functions.percentageBool(Variables.STAY_IN_CHOOSEOPTION)) {
						if (Functions.percentageBool(1)) {
							int perc = PERCENT_MOVING;
							PERCENT_MOVING = 1;
							hoverWaitRectangle(c, timeOut, ChooseOption.getArea());
							PERCENT_MOVING = perc;
						} else {
							hoverWaitRectangle(c, timeOut, ChooseOption.getArea());
						}
					} else {
						hoverWaitRectangle(c, General.random(500, 5000), ChooseOption.getArea());
					}
				}
			}

			if (Functions.percentageBool(10)) {
				Inventory.open();
			}
			if (mouseOut && Functions.percentageBool(Variables.CHANCE_MOUSE_OUT_IT)) {
				Mouse.leaveGame();
				if (Functions.percentageBool(Variables.CHANCE_LOOKING_CLIENT)) {
					Timing.waitCondition(c, General.random(10000, 60000));
				} else {
					General.sleep(1000, maxOut);
					SleepJoe.sleepHumanDelay(10, 1, 20000);
				}
			} else if (General.random(0, 100) > PERCENT_MOVING) {
				Timing.waitCondition(c, General.random(MIN_WAIT, MAX_WAIT));
			} else {
				Point pt;
				pt = choosePointRelative(screen.getRectangle(), RECTANGLE_MULTIPLIER);
				Mouse.move(pt);
			}
			AntiBan.timedActions();
		}
		Mouse.setSpeed(mSpeed);
		return c.active();
		
	}
	
}
