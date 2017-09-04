package scripts.spinner.logic;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.api.Task;
import scripts.others.DynamicWaiting;
import scripts.others.Screen;
import scripts.spinner.utilities.MouseSpin;
import scripts.spinner.utilities.Variables;
import scripts.utilities.Functions;

public class OpenDoorBack implements Task {

	@Override
	public String action() {
		return "Opening door from stairs...";
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public boolean validate() {
		return Objects.findNearest(7, 1543).length > 0
				&& !Functions.playerOnTiles(Player.getPosition(), Variables.tile1spin, Variables.tile2spin);
		// && Game.getPlane() == 1 && Inventory.find("Flax").length > 14;
	}

	@Override
	public void execute() {
		RSObject[] gate = Objects.findNearest(15, 1543);
		if (gate.length > 0) {
			if (MouseSpin.clickModel(gate[0].getModel())) {
				RSObject[] spine = Objects.findNearest(15, 14889);
				DynamicWaiting.hoverWaitModel(new Condition() {
					@Override
					public boolean active() {
						General.sleep(100, 200);
						return Objects.findNearest(7, 1543).length == 0;
					}
				}, General.random(5000, 10000), spine[0].getModel());
			}
		}
	}

}
