package scripts.chinner_logic.walking_steps;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.chinner_utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class WalkToDaero implements Task {

	@Override
	public String action() {
		return "Walking to Daero...";
	}

	@Override
	public int priority() {
		return 2;
	}

	@Override
	public boolean validate() {
		return (Constants.gnomeTreeArea.contains(Player.getPosition()));
	}

	@Override
	public void execute() {

		// Climbing down stairs
		long t1 = Timing.currentTimeMillis();
		while (Game.getPlane() == 3 && (Timing.currentTimeMillis()-t1)<10000) {
			RSObject[] stairs = Objects.find(20, 16679);
			if (stairs.length > 0) {
				if (stairs[0].isOnScreen()) {
					AccurateMouse.click(stairs[0], "Climb-down");
					DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							return (Game.getPlane() == 2);
						}
					}, General.random(1000, 3000), Screen.MAIN_SCREEN);
				} else {
					WebWalking.walkTo(stairs[0].getPosition());
				}
			}
		}

		long t2 = Timing.currentTimeMillis();
		while (Game.getPlane() == 2 && (Timing.currentTimeMillis()-t2)<10000) {
			RSObject[] stairs = Objects.find(5, 2884);
			if (stairs.length > 0) {
				AccurateMouse.click(stairs[0], "Climb-down");
				DynamicWaiting.hoverWaitScreen(new Condition() {
					@Override
					public boolean active() {
						return (Game.getPlane() == 1);
					}
				}, General.random(1000, 3000), Screen.MINIMAP);
			}
			General.sleep(100, 200);
		}

		long t3 = Timing.currentTimeMillis();
		while (!Constants.waydarArea.contains(Player.getPosition()) && (Timing.currentTimeMillis()-t3)<30000) {
			WebWalking.walkTo(new RSTile(2480, 3488, 1));
			RSNPC[] daero = NPCs.find(2020);
			if (daero.length > 0) {
				if (daero[0].isOnScreen()) {
					AccurateMouse.click(daero[0], "Travel");
					DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							return (Constants.waydarArea.contains(Player.getPosition()));
						}
					}, General.random(10000, 15000), Screen.MAIN_SCREEN);
				} else {
					WebWalking.walkTo(daero[0].getPosition());
				}
			}
		}
		General.sleep(1000, 2000);
	}

}
