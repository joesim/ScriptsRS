package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.manKillerUtilities.HealthMan;
import scripts.minautorUtilities.ConditionMino;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.zulrahUtilities.CustomAntiban;

public class MinautorKiller extends Script implements Painting {

	// ID du minautor
	public final static int MINAUTOR_ID = 2483;

	// Tiles
	public static final RSTile tileDumb = new RSTile(0, 0, 0);
	public static final RSTile tile1barrack = new RSTile(1885, 5187, 0);
	public static final RSTile tile2barrack = new RSTile(1910, 5204, 0);

	// RSNPC
	private RSNPC minotaurs[] = null;
	public static RSNPC mino = null;

	public boolean noFood = false;

	private static final double SPEED = 1;

	public void onStart() {
		MouseMoveJoe.loadDataNormal();
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		super.setAIAntibanState(false);
		Mouse.setSpeed(105);
		HoverBox.load();
		HealthMan.loadHealth();
	}

	@Override
	public void run() {
		onStart();
		println("Allright! On est parti!");
		ConditionMino c1c2c3c4 = new ConditionMino(1, 2, 3, 4);
		while (!noFood) {
			CustomAntiban.antiban();
			mino = findMino();
			attackMino(mino);
			MouseMoveJoe.hoverMouse(HoverBox.get(1), c1c2c3c4);
			if (!Functions.playerOnTiles(Player.getPosition(), tile1barrack, tile2barrack)) {
				println("Changer Position");
				Walking.walkTo(Functions.returnMiddleTile(tile1barrack, tile2barrack));
			} else if (HealthMan.getCheckHealth()) {
				println("Vie");
				if (HealthMan.checkIfGotFood()) {
					HealthMan.checkHealth();
				} else {
					noFood = true;
				}
			}
			MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 100),
					new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 10000)), 5, false);
			println("Killer chicken");
		}

	}

	private void attackMino(RSNPC mino2) {
		while (mino2 != null && mino2.isOnScreen() && Player.getRSPlayer().getInteractingIndex() == -1 && mino2.getInteractingIndex() == -1) {
			clickMino(mino2);
			SleepJoe.sleepHumanDelay(0.05, 1, 100);
			MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 7),
					new ConditionTime(SleepJoe.sleepHumanDelayOut(0.8, 1, 1000)), 2, false);
		}
	}

	private void clickMino(RSNPC mino2) {
		MouseMoveJoe.playMouseFollowNPC(mino2, "ttack", SPEED, null);
		println("Ciboire");
		MouseMoveJoe.fastClick(1, 0.5);
	}

	private RSNPC findMino() {
		minotaurs = NPCs.findNearest(MINAUTOR_ID);
		for (RSNPC m : minotaurs) {
			if (m.isOnScreen() && m.getInteractingIndex() == -1 && m.getAnimation() != 4265
					&& Functions.playerOnTiles(m.getPosition(), tile1barrack, tile2barrack)) {
				return m;
			}
		}
		return null;
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private final long startXP = Skills.getXP(Skills.SKILLS.RANGED);
	private final long startTime = System.currentTimeMillis();

	@Override
	public void onPaint(Graphics g) {

		g.setFont(font);

		long timeRan = System.currentTimeMillis() - startTime;
		long gainedXP = Skills.getXP(Skills.SKILLS.RANGED) - startXP;
		long xpPerHour = (long) (gainedXP * 3600000 / timeRan);
		g.setColor(new Color(0, 255, 0));
		g.drawString("Exp gained : " + gainedXP, 20, 50);
		g.drawString("Exp per hour: " + xpPerHour, 20, 70);
		g.drawString("Time running: " + timeRan, 20, 90);
		g.drawString("NPC animation: " + mino.getAnimation(), 20, 110);
	}

}
