package scripts.chinner_logic.walking_steps;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.chinner_utilities.Constants;
import scripts.chinner_utilities.Utils;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;

public class WalkToCombatArea implements Task {

	@Override
	public String action() {
		return "Walking to monkeys...";
	}

	@Override
	public int priority() {
		return 6;
	}

	@Override
	public boolean validate() {
		return (Constants.dungeonArea.contains(Player.getPosition()));
	}

	@Override
	public void execute() {
		RSTile[] path = { new RSTile(2771, 9100, 0), new RSTile(2782, 9101, 0), new RSTile(2773, 9110, 0),
				new RSTile(2763, 9119, 0), new RSTile(2777, 9118, 0), new RSTile(2791, 9117, 0),
				new RSTile(2799, 9112, 0), new RSTile(2800, 9100, 0), new RSTile(2810, 9109, 0),
				new RSTile(2801, 9129, 0), new RSTile(2792, 9130, 0), new RSTile(2780, 9129, 0),
				new RSTile(2769, 9132, 0), new RSTile(2756, 9133, 0), new RSTile(2744, 9135, 0),
				new RSTile(2733, 9138, 0), new RSTile(2724, 9138, 0), new RSTile(2720, 9130, 0),
				new RSTile(2735, 9130, 0), new RSTile(2731, 9120, 0), new RSTile(2719, 9119, 0),
				new RSTile(2706, 9114, 0) };

		while (!Constants.combatArea.contains(Player.getPosition())) {
			long t = Timing.currentTimeMillis();
			Walking.walkPath(path, new Condition() {
				@Override
				public boolean active() {
					if ((Timing.currentTimeMillis() - t) > 2000 && !Player.isMoving()) {
						return true;
					}
					return (Constants.combatArea.contains(Player.getPosition()) || Utils.shouldHeal());
				}
			}, 180000);
			if (Utils.shouldHeal()){
				Utils.healWalking();
			}
		}
		DynamicWaiting.hoverWaitScreen(new Condition() {
			@Override
			public boolean active() {
				return (Constants.combatArea.contains(Player.getPosition()));
			}
		}, General.random(10000, 15000), Screen.MAIN_SCREEN);
		General.sleep(1000, 2000);
	}

}
