package scripts.manKillerUtilities;

import java.util.ArrayList;

import org.tribot.api2007.Game;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;

import scripts.MenKiller;
import scripts.utilities.Condition;
import scripts.utilities.Functions;
import scripts.utilities.Timer;

/**
 * Classe qui contient une condition.
 * 
 * @author joel_
 *
 */
public class ConditionMan implements Condition {

	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private Timer tm = new Timer(1000000);
	private boolean timerRestart = true;

	public ConditionMan(int... ids) {
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

		// Si sur etage 1
		if (numbers.contains(3)) {
			if (Game.getPlane() == 1) {
				return true;
			}
		}

		// Si pas player a bonne place
		if (numbers.contains(4)) {
			if (!Functions.playerOnTiles(Player.getPosition(), MenKiller.tile1barrack, MenKiller.tile2barrack)) {
				return true;
			}
		}

		// Si pas player a bonne place
		if (numbers.contains(5)) {
			if (NPCChat.getMessage()!=null || NPCChat.getOptions()!=null) {
				NPCChat.clickContinue(true);
				return true;
			}
		}
		
		if (numbers.contains(6)) {
			if (MenKiller.man!=null && MenKiller.man.getAnimation()==836) {
				return true;
			}
		}

		return false;
	}

}
