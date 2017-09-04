package scripts.spinner.utilities;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;

import scripts.spin_utilities.Variables;
import scripts.utilities.Functions;

public class Conditions {

	public static Condition conditionOpenDoorBack= new Condition() {
		
		@Override
		public boolean active() {
			General.sleep(100,200);
			return Objects.findNearest(5, 1543).length > 0
					&& Functions.playerOnTiles(Player.getPosition(), Variables.tile1spin, Variables.tile2spin);
		}
	};
	
}
