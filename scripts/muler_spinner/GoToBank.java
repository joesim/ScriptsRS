package scripts.muler_spinner;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.api.Task;
import scripts.webwalker_logic.WebWalker;

public class GoToBank implements Task {

	private RSArea area = new RSArea(new RSTile(3208,3219,2), 3);
	
	@Override
	public String action() {
		return null;
	}

	@Override
	public int priority() {
		return 10;
	}

	@Override
	public boolean validate() {
		return !area.contains(Player.getPosition());
	}

	@Override
	public void execute() {
		WebWalker.walkTo(new RSTile(3208,3219,2));
		Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(20, 40);
				RSNPC[] npc = NPCs.find(3227);
				return npc.length > 0 && npc[0].isOnScreen();
			}
		}, General.random(3000, 4000));

	}

}
