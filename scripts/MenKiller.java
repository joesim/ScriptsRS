package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.manKillerUtilities.ConditionMan;
import scripts.manKillerUtilities.FailSafeThreadMan;
import scripts.manKillerUtilities.HealthMan;
import scripts.manKillerUtilities.MenKillerState;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.zulrahUtilities.CustomAntiban;

public class MenKiller extends Script implements Painting {

	public static final int[] MAN_ID = { 3078, 3079, 3080 };
	private static final double SPEED = 1;
	public MenKillerState state = MenKillerState.KILLING;
	public boolean noFood = false;

	// RSNPC
	private RSNPC men[] = null;
	public static RSNPC man = null;

	// Tiles
	public static final RSTile tileDumb = new RSTile(0, 0, 0);
	public static final RSTile tile1barrack = new RSTile(3091, 3507, 0);
	public static final RSTile tile2barrack = new RSTile(3100, 3513, 0);

	// Condition
	private ConditionMan c1c2c3c4c5 = new ConditionMan(1, 2, 3, 4, 5);
	private ConditionMan c2c3c4c5c6 = new ConditionMan(2, 3, 4, 5, 6);

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
		men = NPCs.find(MAN_ID);
		FailSafeThreadMan fst = new FailSafeThreadMan();
		Thread thr = new Thread(fst);
		thr.start();
		
		while (!noFood) {
			SleepJoe.sleepHumanDelay(0.5, 1, 200);
			switch (state) {
			case KILLING:
				CustomAntiban.antiban();
				sleep(1, 2);
				man = findMan();
				attackMen(man);
				MouseMoveJoe.hoverMouse(HoverBox.get(1), c1c2c3c4c5);
				if (Game.getPlane() == 1) {
					println("Climb down");
					state = MenKillerState.CLIMBDOWN;
					break;
				} else if (!Functions.playerOnTiles(Player.getPosition(), tile1barrack, tile2barrack)) {
					println("Changer Position");
					state = MenKillerState.OPENDOOR;
					break;
				} else if (HealthMan.getCheckHealth()) {
					println("Vie");
					if (!HealthMan.checkHealth()) {
						state = MenKillerState.BANKING;
						break;
					}
					break;
				}
				MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 100),
						new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 10000)), 5, false);
				break;
			case CLIMBDOWN:
				RSObject[] echelle = Objects.findNearest(5, 16679);
				while (echelle == null || !echelle[0].isOnScreen()) {
					sleep(500);
				}
				if (echelle.length > 0) {
					while (echelle.length > 0) {
						echelle[0].click("Climb-down");
						SleepJoe.sleepHumanDelay(1.5, 1, 1000);
						echelle = Objects.findNearest(5, 16679);
					}
				}
				state = MenKillerState.KILLING;
				break;
			case BANKING:
				break;
			case OPENDOOR:
				OpenGateDoor();
				if (!Functions.playerOnTiles(Player.getPosition(), tile1barrack, tile2barrack)) {
					Point point = Projection.tileToScreen(Functions.returnMiddleTile(tile1barrack, tile2barrack), 0);
					if (point.x > 3 && point.y > 3 && point.y < 315 && point.x < 490) {
						DynamicClicking.clickRSTile(Functions.returnMiddleTile(tile1barrack, tile2barrack),
								"Walk here");
						SleepJoe.sleepHumanDelay(0.2, 0, 600);
					} else {
						RSTile t = Functions.returnMiddleTile(tile1barrack, tile2barrack);
						RSTile[] goingPath = Walking.generateStraightPath(new RSTile(t.getX(), t.getY(), 0));
						Walking.walkPath(goingPath);
					}
				}
				state = MenKillerState.KILLING;
				break;
			default:
				break;
			}
			println(c2c3c4c5c6.checkCondition());

		}

	}

	private RSNPC findMan() {
		men = NPCs.findNearest(MAN_ID);
		for (RSNPC m : men) {
			if (m.isOnScreen() && m.getInteractingIndex() == -1 && m.getAnimation() != 836
					&& Functions.playerOnTiles(m.getPosition(), tile1barrack, tile2barrack)) {
				return m;
			}
		}
		return null;
	}

	private void attackMen(RSNPC man) {

		while (Player.getRSPlayer().getInteractingIndex() == -1 && man != null && man.getInteractingIndex() == -1
				&& man.isOnScreen() && man.getAnimation() != 836 && !c2c3c4c5c6.checkCondition()
				&& !Combat.isUnderAttack()) {
			clickMan(man);
			MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 7),
					new ConditionTime(SleepJoe.sleepHumanDelayOut(1.5, 1, 1000)), 2, false);

		}
	}

	private void clickMan(RSNPC man) {
		MouseMoveJoe.playMouseFollowNPC(man, "ttack", SPEED, c2c3c4c5c6);
		if (!c2c3c4c5c6.checkCondition() && Game.isUptext("Attack Man"))
			General.println("Click!");
		MouseMoveJoe.fastClick(1, 0.5);
	}

	public boolean OpenGateDoor() {

		RSObject[] gate = Objects.findNearest(10, 1524);
		if (gate.length > 0) {
			while (gate.length > 0) {
				gate[0].click("Open");
				SleepJoe.sleepHumanDelay(1.5, 1, 1000);
				gate = Objects.findNearest(10, 1524);
			}
			return true;
		}
		return false;
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private final long startXP = Skills.getXP(Skills.SKILLS.RANGED);
	private final long startTime = System.currentTimeMillis();

	@Override
	public void onPaint(Graphics g) {

		g.setFont(font);

		long timeRan = (System.currentTimeMillis() - startTime)/1000;
		long gainedXP = Skills.getXP(Skills.SKILLS.RANGED) - startXP;
		long xpPerHour = (long) (gainedXP * 3600000 / timeRan)/1000;
		g.setColor(new Color(255, 0, 0));
		g.drawString("Exp gained : " + gainedXP, 20, 60);
		g.drawString("Exp per hour: " + xpPerHour, 20,80);
		g.drawString("Time running: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)), 20, 100);
		g.drawString("Man animation: " + man.getAnimation(), 20, 120);
		g.drawString("Ranged level: " + Skills.getCurrentLevel(Skills.SKILLS.RANGED), 20, 140);
		g.drawString("Defence level: " + Skills.getCurrentLevel(Skills.SKILLS.DEFENCE), 20, 160);
		g.drawString("Magic level: " + Skills.getCurrentLevel(Skills.SKILLS.MAGIC), 20, 180);
	}

}
