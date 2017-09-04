package scripts.spinner_tribot.spinner_utilities;

import java.util.ArrayList;

import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;

import scripts.flaxUtilities.CheckSpinning;
import scripts.utilities.Condition;
import scripts.utilities.Functions;
import scripts.utilities.SleepJoe;
import scripts.utilities.Timer;

public class ConditionFlax implements Condition {

	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private Timer tm = new Timer(1000000);
	private boolean timerRestart = true;
	
	public ConditionFlax(int... ids) {
		for (int i = 0; i < ids.length; i++) {
			numbers.add(ids[i]);
		}
	}

	@Override
	public boolean checkCondition() {
		if (timerRestart) {
			tm = new Timer(1000000);
			timerRestart = false;
		}
		if (checkThings()) {
			timerRestart = true;
			return true;
		} else {
			return false;
		}
	}

	private boolean checkThings() {
		if (numbers.contains(1)) {
			if (Game.getPlane() == 1) {
				return true;
			}
		}

		if (numbers.contains(2)) {
			if (Interfaces.get(459) != null) {
				return true;
			}
		}

		if (numbers.contains(3)) {
			if (Inventory.find(1779).length == 0) {
				return true;
			}
		}

		if (numbers.contains(4)) {
			if (Game.getPlane() == 2) {
				return true;
			}
		}

		if (numbers.contains(5)) {
			if (Variables.bankLumb!=null && Variables.bankLumb.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(6)) {
			if (Banking.isBankScreenOpen()) {
				return true;
			}
		}

		if (numbers.contains(7)) {
			if (Variables.stairsTopLumb.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(8)) {
			if (NPCChat.getMessage() != null || NPCChat.getOptions() != null) {
				if (Functions.percentageBool(10)) {
					NPCChat.clickContinue(true);
				}
				return true;
			}
		}

		if (numbers.contains(9)) {
			if (NPCChat.getMessage() != null || NPCChat.getOptions() != null) {
				return true;
			}
		}

		if (numbers.contains(10)) {
			if (Objects.findNearest(5, 1543).length > 0) {
				return true;
			}
		}

		if (numbers.contains(11)) {
			if (Objects.findNearest(5, 1543).length == 0) {
				return true;
			}
		}

		if (numbers.contains(12)) {
			if (Objects.findNearest(5, 1543).length > 0
					&& Functions.playerOnTiles(Player.getPosition(), Variables.tile1spin, Variables.tile2spin)) {
				return true;
			}
		}

		if (numbers.contains(13)) {
			if (Objects.findNearest(5, 1543).length > 0
					&& !Functions.playerOnTiles(Player.getPosition(), Variables.tile1spin, Variables.tile2spin)) {
				return true;
			}
		}

		if (numbers.contains(14)) {
			if (Player.getRSPlayer().getInteractingIndex() != -1) {
				return true;
			}
		}
		
		if (numbers.contains(15)) {
			if (Game.isUptext("Bank Bank booth") || Game.isUptext("Climb")) {
				return true;
			}
		}
		
		if (numbers.contains(16)) {
			if (!Player.getRSPlayer().isInCombat()) {
				return true;
			}
		}
		
		if (numbers.contains(17)) {
			if (!Player.isMoving() && !Functions.playerOnTiles(Player.getPosition(), Variables.tile1spinner, Variables.tile2spinner)) {
				SleepJoe.sleepHumanDelay(3, 1, 6000);
				return true;
			}
		}
		
		if (numbers.contains(18)) {
			if (CheckSpinning.notSpinning) {
				return true;
			}
		}
		
		if (numbers.contains(1000)) {
			if (tm.getElapsed()>120000) {
				return true;
			}
		}
		return false;
	}

}
