package scripts.zulrahUtilities;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;

import scripts.Zulrah;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;

public final class CustomAntiban {

	public static void antiban() {
		if (Functions.percentageBool(20)) {
			if (Zulrah.util.shouldExamineEntity()) {
				Zulrah.util.examineEntity();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldCheckTabs()) {
				Zulrah.util.checkTabs();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldCheckXP()) {
				Zulrah.util.checkXP();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldLeaveGame()) {
				Zulrah.util.leaveGame();
				if (General.random(0, 200) == 0) {
					General.sleep(60000, 180000);
				}
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldMoveMouse()) {
				Zulrah.util.moveMouse();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldPickupMouse()) {
				Zulrah.util.pickupMouse();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldRightClick()) {
				Zulrah.util.rightClick();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			} else if (Zulrah.util.shouldRotateCamera()) {
				Zulrah.util.rotateCamera();
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}
		}
	}

	public static void antibanTwo() {
		if (Zulrah.util.shouldExamineEntity()) {
			Zulrah.util.examineEntity();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		} 
		if (Zulrah.util.shouldCheckTabs()) {
			Zulrah.util.checkTabs();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldCheckXP()) {
			Zulrah.util.checkXP();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldLeaveGame()) {
			Zulrah.util.leaveGame();
			if (General.random(0, 200) == 0) {
				General.sleep(60000, 180000);
			}
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldMoveMouse()) {
			Zulrah.util.moveMouse();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldPickupMouse()) {
			Zulrah.util.pickupMouse();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldRightClick()) {
			Zulrah.util.rightClick();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldRotateCamera()) {
			Zulrah.util.rotateCamera();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}

	}
	
	public static void antibanFlax() {
		if (Zulrah.util.shouldExamineEntity()) {
			Zulrah.util.examineEntity();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		} 
		if (Zulrah.util.shouldCheckTabs()) {
			Zulrah.util.checkTabs();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldCheckXP()) {
			Zulrah.util.checkXP();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldLeaveGame()) {
			Zulrah.util.leaveGame();
			if (General.random(0, 200) == 0) {
				General.sleep(60000, 180000);
			}
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldMoveMouse()) {
			Zulrah.util.moveMouse();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldPickupMouse()) {
			Zulrah.util.pickupMouse();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}
		if (Zulrah.util.shouldRightClick()) {
			Zulrah.util.rightClick();
			SleepJoe.sleepHumanDelay(1, 1, 2000);
		}

	}
	
	public static void leaveMouseFor(int time){
		Mouse.leaveGame();
		General.sleep(time);
	}

}
