package scripts.zulrahUtilities;

import java.util.ArrayList;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCChat;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;

import scripts.MasterFarmer;
import scripts.Zulrah;
import scripts.utilities.Condition;
import scripts.utilities.Functions;
import scripts.utilities.StaticTimer;
import scripts.utilities.Timer;

/**
 * Classe qui contient une condition.
 * 
 * @author joel_
 *
 */
public class ConditionZul implements Condition {

	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private Timer tm = new Timer(1000000);
	private boolean timerRestart = true;

	public ConditionZul(RSTile deb, int... ids) {
		for (int i = 0; i < ids.length; i++) {
			numbers.add(ids[i]);
		}
	}

	public boolean checkCondition() {
		if (timerRestart) {
			tm = new Timer(1000000);
			timerRestart = false;
		}
		if (checkThingsUp()) {
			timerRestart = true;
			return true;
		} else {
			return false;
		}
	}

	public boolean checkThingsUp() {

		if (numbers.contains(1)) {
			if (HealthZul.getCheckHealth()) {
				return true;
			}
		}

		if (numbers.contains(2)) {
			if (HealthZul.getCheckPrayer()) {
				return true;
			}
		}

		if (numbers.contains(3)) {
			if (tm.getElapsed() > 18770) {
				General.println("Time out");
				return true;
			}
		}

		// Zulrah quit
		if (numbers.contains(4)) {
			if (Zulrah.zulrah == null || Zulrah.zulrah.getAnimation() == 5072) {
				return true;
			}
		}

		// Zulrah enter
		if (numbers.contains(5)) {
			if (Zulrah.zulrah != null
					&& (Zulrah.zulrah.getAnimation() == 5073 || Zulrah.zulrah.getAnimation() == 5071 || Zulrah.zulrah.getAnimation()==5069 || Zulrah.zulrah.getAnimation() == 5807 || Zulrah.zulrah.getAnimation() == 5806)) {
				return true;
			}
		}

		// Destination = null
		if (numbers.contains(6)) {
			if (Game.getDestination() == null || (!Player.getRSPlayer().isMoving() && Player.getRSPlayer().getAnimation()==-1)) {
				return true;
			}
		}

		// Zulrah green on screen
		if (numbers.contains(7)) {
			if (Zulrah.zulrah.isOnScreen()) {
				return true;
			}
		}

		// Zulrah red animation hit
		if (numbers.contains(8)) {
			if (Zulrah.zulrah != null
					&& (Zulrah.zulrah.getAnimation() == 5806 || Zulrah.zulrah.getAnimation() == 5807)) {
				return true;
			}
		}

		// Zulrah red animation hit
		if (numbers.contains(9)) {
			if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() != 5806 && Zulrah.zulrah.getAnimation() != 5807) {
				return true;
			}
		}

		// Zulrah animation -1
		if (numbers.contains(10)) {
			if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == -1) {
				return true;
			}
		}

		// Zulrah red animation hit
		if (numbers.contains(11)) {
			if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5069) {
				return true;
			}
		}

		if (numbers.contains(12)) {
			if (Player.getRSPlayer().getInteractingIndex() == -1) {
				return true;
			}
		}

		if (numbers.contains(13)) {
			if (CheckRed.shouldMove) {
				return true;
			}
		}

		if (numbers.contains(14)) {
			if (CheckJad.shouldSwitch) {
				return true;
			}
		}

		if (numbers.contains(15)) {
			if (!Player.isMoving()) {
				return true;
			}
		}

		if (numbers.contains(16)) {
			if (Zulrah.bankClanWars.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(17)) {
			if (Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1cw, Zulrah.tile2cw)) {
				return true;
			}
		}

		if (numbers.contains(18)) {
			if (Functions.getHealth() <= 0) {
				return true;
			}
		}

		if (numbers.contains(19)) {
			if (CheckDeath.playerDead || CheckDeath.zulrahDead) {
				return true;
			}
		}

		if (numbers.contains(20)) {
			if (Banking.isBankScreenOpen()) {
				return true;
			}
		}

		if (numbers.contains(21)) {
			if (Zulrah.portal == null || Zulrah.portal.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(22)) {
			if (Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1port, Zulrah.tile2port)) {
				return true;
			}
		}

		if (numbers.contains(23)) {
			if (Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1zul, Zulrah.tile2zul)) {
				return true;
			}
		}

		if (numbers.contains(24)) {
			if (Zulrah.boatZulrah.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(25)) {
			if (NPCChat.getOptions() != null) {
				return true;
			}
		}

		if (numbers.contains(26)) {
			if (!Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1boat, Zulrah.tile2boat) && NPCChat.getClickContinueInterface()==null) {
				return true;
			}
		}

		if (numbers.contains(27)) {
			Zulrah.stairsLumb = Functions.findNearestId(20, 16671);
			if (Zulrah.stairsLumb.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(28)) {
			if (Game.getPlane() == 1) {
				return true;
			}
		}

		if (numbers.contains(29)) {
			if (Game.getPlane() == 2) {
				return true;
			}
		}

		if (numbers.contains(30)) {
			if (Zulrah.bankLumby.isOnScreen()) {
				return true;
			}
		}

		if (numbers.contains(31)) {
			if (!Banking.isBankScreenOpen()) {
				return true;
			}
		}

		if (numbers.contains(32)) {
			if (Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1lumb, Zulrah.tile2lumb)) {
				return true;
			}
		}
		
		if (numbers.contains(33)) {
			if (Functions.findNearestId(5, 11699)!=null) {
				return true;
			}
		}

		if (numbers.contains(34)) {
			if (Functions.findNearestNPC(2042)!=null) {
				return true;
			}
		}
		
		if (numbers.contains(35)) {
			if (CheckZulrah.getPercentageDone()>Zulrah.PERCENT_PHASE_HOVER) {
				return true;
			}
		}
		
		if (numbers.contains(36)) {
			if (NPCChat.getName()!=null) {
				return true;
			}
		}
		
		if (numbers.contains(37)) {
			if (Functions.playerOnTiles(Player.getPosition(), Zulrah.tile1camelot, Zulrah.tile2camelot)) {
				return true;
			}
		}
		
		// *********************************
		// Mauvaises positions
		// *********************************
		if (numbers.contains(1001)) {
			if (!Zulrah.p1.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1002)) {
			if (!Zulrah.p2.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1003)) {
			if (!Zulrah.p3.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1004)) {
			if (!Zulrah.p4.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1005)) {
			if (!Zulrah.p5.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1006)) {
			if (!Zulrah.p6.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1007)) {
			if (!Zulrah.p7.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1008)) {
			if (!Zulrah.p8.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1009)) {
			if (!Zulrah.p9.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1010)) {
			if (!Zulrah.p10.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1011)) {
			if (!Zulrah.p11.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1012)) {
			if (!Zulrah.p12.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1013)) {
			if (!Zulrah.p13.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(1014)) {
			if (!Zulrah.p14.checkPlayerOk()) {
				return true;
			}
		}
		
		if (numbers.contains(1015)) {
			if (!Zulrah.p15.checkPlayerOk()) {
				return true;
			}
		}
		
		if (numbers.contains(1016)) {
			if (!Zulrah.p16.checkPlayerOk()) {
				return true;
			}
		}
		
		if (numbers.contains(1017)) {
			if (!Zulrah.p17.checkPlayerOk()) {
				return true;
			}
		}

		// ********************* Bonne Position
		// *********************************
		if (numbers.contains(2001)) {
			if (Zulrah.p1.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2002)) {
			if (Zulrah.p2.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2003)) {
			if (Zulrah.p3.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2004)) {
			if (Zulrah.p4.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2005)) {
			if (Zulrah.p5.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2006)) {
			if (Zulrah.p6.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2007)) {
			if (Zulrah.p7.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2008)) {
			if (Zulrah.p8.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2009)) {
			if (Zulrah.p9.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2010)) {
			if (Zulrah.p10.checkPlayerOk()) {
				return true;
			}
		}

		if (numbers.contains(2011)) {
			if (Zulrah.p11.checkPlayerOk()) {
				return true;
			}
		}
		if (numbers.contains(2012)) {
			if (Zulrah.p12.checkPlayerOk()) {
				return true;
			}
		}
		if (numbers.contains(2013)) {
			if (Zulrah.p13.checkPlayerOk()) {
				return true;
			}
		}
		if (numbers.contains(2014)) {
			if (Zulrah.p14.checkPlayerOk()) {
				return true;
			}
		}
		if (numbers.contains(2015)) {
			if (Zulrah.p15.checkPlayerOk()) {
				return true;
			}
		}
		if (numbers.contains(2016)) {
			if (Zulrah.p16.checkPlayerOk()) {
				return true;
			}
		}
		if (numbers.contains(2017)) {
			if (Zulrah.p17.checkPlayerOk()) {
				return true;
			}
		}

		// *********************** TEMPS ECOULE
		// *************************************
		if (numbers.contains(3001)) {
			if (tm.getElapsed() > 2500) {
				return true;
			}
		}

		if (numbers.contains(3500)) {
			if (tm.getElapsed() > 500) {
				return true;
			}
		}

		if (numbers.contains(3002)) {
			if (StaticTimer.getElapsed() > 10000) {
				return true;
			}
		}

		if (numbers.contains(3003)) {
			if (StaticTimer.getElapsed() > 8002) {
				return true;
			}
		}
		//*********THIEVING****************
		if (numbers.contains(101)) {
			if (MasterFarmer.farmer != null && MasterFarmer.farmer.isMoving() || !Game.isUptext("Pickpocket")) {
				return true;
			}
		}

		return false;
	}
}
