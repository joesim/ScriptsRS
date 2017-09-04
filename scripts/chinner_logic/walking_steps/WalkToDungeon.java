package scripts.chinner_logic.walking_steps;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.chinner_utilities.Constants;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.webwalker_logic.local.walker_engine.interaction_handling.AccurateMouse;

public class WalkToDungeon implements Task {

	@Override
	public String action() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean validate() {
		return (Constants.apeAtollArea.contains(Player.getPosition()));
	}

	@Override
	public void execute() {

		// Putting on monkey greegree
		RSItem[] monkeyGreeGree = Inventory.find(Constants.MONKEY_GREEGREE);
		if (monkeyGreeGree.length > 0) {
			while (!monkeyGreeGree[0].click("Hold")) {
				General.sleep(100, 200);
			}
		}

		// Walking to dungeon entrance
		Walking.generateStraightPath(new RSTile(2765, 2703, 0));
		Walking.walkPath(Walking.generateStraightPath(new RSTile(2765, 2703, 0)));
		General.sleep(5000);
		RSObject[] dungeonEntrance = Objects.find(20, 4780);
		if (dungeonEntrance.length > 0) {
			if (dungeonEntrance[0].isOnScreen()) {
				if (AccurateMouse.click(dungeonEntrance[0], "Climb-down")) {
					DynamicWaiting.hoverWaitScreen(new Condition() {
						@Override
						public boolean active() {
							return (Constants.dungeonArea.contains(Player.getPosition()));
						}
					}, General.random(4000, 5000), Screen.MAIN_SCREEN);
					General.sleep(500, 1000);
				}
			} else {
				WebWalking.walkTo(dungeonEntrance[0].getPosition());
			}
		}
	}

}
