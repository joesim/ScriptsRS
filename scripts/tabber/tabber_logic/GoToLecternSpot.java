package scripts.tabber.tabber_logic;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.tabber.utilities.Constants;

public class GoToLecternSpot implements Task {

	RSTile spot = null;

	@Override
	public String action() {
		return "Reposition to lectern spot...";
	}

	@Override
	public int priority() {
		return 11;
	}

	@Override
	public boolean validate() {
		RSObject[] lecterns = Objects.findNearest(30, Constants.LECTERN);
		if (lecterns.length > 0 && !lecterns[0].isOnScreen()) {
			RSTile lec = lecterns[0].getPosition();
			int x = lec.getX();
			int y = lec.getY() + 1;
			spot = new RSTile(x, y, 0);
			if (!spot.equals(Player.getPosition())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute() {
		if (spot.isOnScreen()) {
			spot.click("Walk here");
		} else {
			Mouse.click(Projection.tileToMinimap(spot), 1);
		}
		DynamicWaiting.hoverWaitScreen(new Condition() {
			
			@Override
			public boolean active() {
				General.sleep(80, 120);
				return spot.equals(Player.getPosition());
			}
		}, General.random(5000, 10000), Screen.ALL_SCREEN);
	}

}
