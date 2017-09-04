package scripts.minautorUtilities;

import java.util.ArrayList;

import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;

import scripts.MinautorKiller;
import scripts.manKillerUtilities.HealthMan;
import scripts.utilities.Condition;
import scripts.utilities.Functions;

public class ConditionMino implements Condition {

	private ArrayList<Integer> numbers = new ArrayList<Integer>();

	public ConditionMino(int... ids) {
		for (int i = 0; i < ids.length; i++) {
			numbers.add(ids[i]);
		}
	}

	public boolean checkCondition() {

		// Si le man interagit.
		if (numbers.contains(1)) {
			if (Player.getRSPlayer().getInteractingIndex() == -1) {
				return true;
			}
		}

		// Si vie trop bas
		if (numbers.contains(2)) {
			if (HealthMan.getCheckHealth()) {
				return true;
			}
		}

		// Si pas player a bonne place
		if (numbers.contains(3)) {
			if (!Functions.playerOnTiles(Player.getPosition(), MinautorKiller.tile1barrack, MinautorKiller.tile2barrack)) {
				return true;
			}
		}

		// Si pas player a bonne place
		if (numbers.contains(4)) {
			if (NPCChat.getMessage()!=null || NPCChat.getOptions()!=null) {
				NPCChat.clickContinue(true);
				return true;
			}
		}

		return false;
	}
}
