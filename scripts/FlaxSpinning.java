package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.flaxUtilities.CameraFlax;
import scripts.flaxUtilities.CheckSpinning;
import scripts.flaxUtilities.FlaxState;
import scripts.spin_utilities.ConditionFlax;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.utilities.Timer;
import scripts.zulrahUtilities.CustomAntiban;

/**
 * TODO: Perfect clicking and script working fine. TODO: Ultimate antiban. TODO:
 * Constants for multiple accounts
 * 
 * @author joel_
 *
 */
public class FlaxSpinning extends Script implements Painting {

	private static final double SPEED_OPEN_DOOR = 1.5;
	public static final double SPEED_FAST_CLICK = 1;
	private static final int P_CLIMBUP_RIGHT = 95;
	private static final int P_CLICK_ONETIME_STAIRS2 = 80;
	private static final int P_HOVERMM = 80;
	private static final int P_MULTIPLE_CLICKS_MM = 15;
	private static final int P_MOUSE_OUT_ALL = 90;
	private static final int P_CHANCE_OUT = 10;
	private static final int P_MOUSE_HOVER_DEPOSIT = 90;
	private static final int P_DEPOSIT = 99;
	private static final int P_CLOSE_BANK = 97;
	private static final int P_CLOSE_TAB = 70;
	private static final int P_FAST_WITHDRAW = 96;
	private static final int P_USEMM = 95;
	private static final int MIN_BREAK = 600000;
	private static final int MAX_BREAK = 1800000;
	private static final double P_MOUSE_LEAVE = 0.3;
	private static final double P_LOGOUT = 0.08; // 0.08 = 2 hours avg.
	private static final double P_RANDOMSLEEP = 0.1;
	public static final int MAX_OUT_GAME_HOVER = 10000;
	public static final Double SPEED_CLICK_STAIRS_MIN = 1.0;
	public static final Double SPEED_CLICK_STAIRS_MAX = 2.0;
	private static final int P_CLICK_FIRST_BANK = 90;
	private static final int P_CHANCE_MOVE_WHEN_MOUSE_MM = 90;
	public static final double P_RIGHT_CLICK_STAIRS_HOVER = 0.1;
	private static final double P_WAIT_LONGER_DEPOSIT = 0.5;
	private static final double SLEEP_MODIFIER_CLICK_SPIN = 0.4;

	public static RSObject bankLumb;
	public static RSObject stairsTopLumb;
	public static RSTile tile1spin = new RSTile(3208, 3212, 1);
	public static RSTile tile2spin = new RSTile(3212, 3217, 1);
	public static RSTile tile1spinner = new RSTile(3209, 3213, 1);
	public static RSTile tile2spinner = new RSTile(3209, 3213, 1);
	public FlaxState state = FlaxState.BANK;
	public String action = null;
	public ConditionFlax c1 = new ConditionFlax(1);
	public ConditionFlax c2c13c14c17 = new ConditionFlax(2, 13, 14, 17);
	public ConditionFlax c3c8 = new ConditionFlax(3, 8);
	public ConditionFlax c4c12 = new ConditionFlax(4, 12);
	public ConditionFlax c5 = new ConditionFlax(5);
	public ConditionFlax c6c14 = new ConditionFlax(6, 14);
	public ConditionFlax c7 = new ConditionFlax(7);
	public ConditionFlax c9c12 = new ConditionFlax(9, 12);
	public ConditionFlax c10 = new ConditionFlax(10);
	public ConditionFlax c11 = new ConditionFlax(11);
	public ConditionFlax c12 = new ConditionFlax(12);
	public ConditionFlax c13 = new ConditionFlax(13);
	public ConditionFlax c14 = new ConditionFlax(14);
	public ConditionFlax c15 = new ConditionFlax(15);
	public ConditionFlax c16 = new ConditionFlax(16);
	public ConditionFlax c17 = new ConditionFlax(17);
	public ConditionFlax c18 = new ConditionFlax(18);
	public Timer timerMouseOut = new Timer(1000000000);
	public Timer timerLogOut = new Timer(1000000000);
	private ABCUtil util = new ABCUtil();
	private int runEnergy = util.generateRunActivation() + 30 > 99 ? 99 : util.generateRunActivation() + 30;

	private void onStart() {
		MouseMoveJoe.loadDataNormal();
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		Mouse.setSpeed(70);
		HoverBox.load();
	}

	@Override
	public void run() {
		RSObject stairs2 = null;
		onStart();
		CameraFlax cf = new CameraFlax();
		Thread th = new Thread(cf);
		th.start();
		sleep(25000);
		startTime = System.currentTimeMillis();
		startXP = Skills.getXP(Skills.SKILLS.CRAFTING);
		if (Game.getPlane() == 1) {
			state = FlaxState.BANK;
		} else if (Game.getPlane() == 2 && Player.getPosition().getY() > 3215) {
			state = FlaxState.CLICK_BANK;
		} else {
			state = FlaxState.CLICK_MM_BANK;
		}
		while (true) {
			switch (state) {
			case BANK:

				action = "Clicking middle stairs...";
				stairs2 = Functions.findNearestId(15, 16672);
				// Antiban------------------------------------------------
				randomSleep();
				thingsToCheck();
				// -------------------------------------------------------
				openDoor();

				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------
				boolean cont = false;
				do {
					MouseMoveJoe.playMouseFollowObject(stairs2, "Climb",
							Functions.randomDouble(SPEED_CLICK_STAIRS_MIN, SPEED_CLICK_STAIRS_MAX), null, true, 0, 0, 0,
							0, 10);
					if (Game.isUptext("Climb")) {
						if (Functions.percentageBool(P_CLIMBUP_RIGHT)) {
							println(Game.getUptextMenuNode().contains("Climb-up"));
							MouseMoveJoe.fastClick(3, SPEED_FAST_CLICK);
							Point pt = Mouse.getPos();
							TBox boxDown = new TBox(pt.x - 72, pt.y + 14, pt.x + 54, pt.y + 32);
							TBox box = new TBox(pt.x - 72, pt.y + 34, pt.x + 54, pt.y + 46);
							TBox boxUp = new TBox(pt.x - 72, pt.y + 52, pt.x + 54, pt.y + 70);
							if (Functions.percentageBool(10)) {
								MouseMoveJoe.humanMouseMove(boxUp, General.random(3, 6));
							} else if (Functions.percentageBool(6)) {
								MouseMoveJoe.humanMouseMove(boxDown, General.random(3, 6));
							}
							MouseMoveJoe.humanMouseMove(box, General.random(3, 6));
							MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
							if (Game.getCrosshairState() == 2) {
								cont = true;
							}
						} else {
							if (Functions.percentageBool(P_CLICK_ONETIME_STAIRS2)) {
								MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
							} else {
								MouseMoveJoe.spamClick(new TBox(0, 0, 10, 10), 2, 1);
							}
							if (Game.getCrosshairState() == 2) {
								MouseMoveJoe.hoverMouse(HoverBox.get(29, 80, 1), c9c12, 1, true, 30, true);
								if (c12.checkCondition()) {
									break;
								}
								MouseMoveJoe.humanMouseMove(new TBox(180, 395, 421, 410), General.random(3, 6));
								MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
								cont = true;
							}
						}

					}
					if (!cont) {
						SleepJoe.sleepHumanDelay(1, 1, 2000);
					}
				} while (!cont);

				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------

				MouseMoveJoe.hoverMouse(HoverBox.get(36, P_HOVERMM, 1), c4c12, 1, true, P_CHANCE_MOVE_WHEN_MOUSE_MM,
						true);
				if (c12.checkCondition()) {
					break;
				}
				state = FlaxState.CLICK_MM_BANK;
				break;
			case CLICK_MM_BANK:
				if (Game.getPlane() != 2) {
					state = FlaxState.BANK;
					break;
				}
				randomSleep();
				action = "Clicking minimap bank...";
				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------
				if (Functions.percentageBool(P_USEMM)) {
					clickMinimap(new RSTile(3208, 3219, 2));
				} else {
					RSTile tileScreen2 = new RSTile(3206, 3216, 2);
					Point pMM = Projection.tileToScreen(tileScreen2, 0);
					MouseMoveJoe.humanMouseMove(new TBox(pMM, 11), 1.2);
					MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
					if (Functions.percentageBool(P_MULTIPLE_CLICKS_MM)) {
						SleepJoe.sleepHumanDelay(1, 1, 1000);
						MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
						if (Functions.percentageBool(P_MULTIPLE_CLICKS_MM)) {
							SleepJoe.sleepHumanDelay(1, 1, 1000);
							MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
						}
					}
				}
				state = FlaxState.CLICK_BANK;
				break;

			case CLICK_BANK:
				randomSleep();
				action = "Clicking bank...";
				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------

				bankLumb = Functions.findNearestId(20, 18491);
				MouseMoveJoe.hoverMouse(HoverBox.get(31), c5, 2, false, 80, false);
				if (Functions.percentageBool(P_CLICK_FIRST_BANK)) {
					clickBank(bankLumb, "Bank Bank booth", -20, 0);
				} else {
					RSObject b2 = Objects.findNearest(20, 27291)[0];
					clickBank(b2, "Bank Bank booth", -26, 0);
				}
				if (Functions.percentageBool(P_MOUSE_HOVER_DEPOSIT)) {
					MouseMoveJoe.hoverMouse(HoverBox.get(34, 90, 1), c6c14, 2, false, 30, false);
					if (Functions.percentageBool100000(P_WAIT_LONGER_DEPOSIT)) {
						MouseMoveJoe.hoverMouse(HoverBox.get(1), new ConditionTime(General.random(1000, 12000)), 2,
								false, 30, false);
					}
				} else {
					MouseMoveJoe.hoverMouse(HoverBox.get(28, 90, 1), c6c14, 2, false, 30, false);
					if (Functions.percentageBool100000(P_WAIT_LONGER_DEPOSIT)) {
						MouseMoveJoe.hoverMouse(HoverBox.get(1), new ConditionTime(General.random(1000, 12000)), 2,
								false, 30, false);
					}
				}
				while (c14.checkCondition()) {
					clickBank(bankLumb, "Bank Bank booth", -20, 0);
					MouseMoveJoe.hoverMouse(HoverBox.get(34, 90, 1), c6c14, 2, true, 30, false);
				}
				state = FlaxState.CLICK_DEPOSIT;
				break;
			case CLICK_DEPOSIT:
				randomSleep();
				action = "Depositing bow strings...";
				clickDeposit();
				state = FlaxState.WITHDRAW_FLAX;
				break;
			case WITHDRAW_FLAX:
				randomSleep();
				action = "Withdraw flax...";
				RSItem flax = Functions.findNearestBankItem(1779);
				MouseMoveJoe.humanMouseMove(new TBox(flax.getArea()), 1);
				if (Functions.percentageBool(P_FAST_WITHDRAW)) {
					clickKey();
				} else {
					Banking.withdrawItem(flax, 0);
				}
				if (Functions.percentageBool(P_CLOSE_BANK)) {
					if (Functions.percentageBool(P_CLOSE_TAB)) {
						Functions.FTAB(27, 1);
					}
				} else {
					Banking.close();
				}
				state = FlaxState.CLICK_MM_STAIRS;
				break;
			case CLICK_MM_STAIRS:
				randomSleep();
				action = "Clicking stairs top...";
				clickMinimap(new RSTile(3206, 3210, 2));

				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------

				stairsTopLumb = findGoodStairs();
				MouseMoveJoe.hoverMouse(HoverBox.get(32, 90, 1), c7, 2, true, 30, false);
				do { // TODO: good stairs clicking
					MouseMoveJoe.playMouseFollowObject(stairsTopLumb, "Climb-down", 2, null, false, 10, 0, 0, 0, 10);
					randomSleep();
					MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
					if (Game.getCrosshairState() != 2) {
						SleepJoe.sleepHumanDelay(1, 1, 2000);
					}
				} while (Game.getCrosshairState() != 2);
				state = FlaxState.GO_SPIN;
				break;
			case GO_SPIN:

				randomSleep();
				action = "Spinning...";
				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------

				openDoorBack();

				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------

				MouseMoveJoe.hoverMouse(HoverBox.get(35), c1, 2, false, 20, true);
				randomSleep();
				MouseMoveJoe.hoverMouse(new TBox(Mouse.getPos(), 50),
						new ConditionTime(SleepJoe.sleepHumanDelayOut(SLEEP_MODIFIER_CLICK_SPIN, 1, 10000)), 2, true,
						20, false);
				RSObject spinner = Functions.findNearestId(15, 14889);
				do { // TODO: good spinner clicking
					if (Interfaces.get(459) != null) {
						break;
					}
					MouseMoveJoe.playMouseFollowObject(spinner, "Spin", 1, null, true, 0, 0, 0, 0, 5);
					randomSleep();
					MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
					if (Game.getCrosshairState() != 2) {
						SleepJoe.sleepHumanDelay(1, 1, 2000);
					}
				} while (Game.getCrosshairState() != 2);
				if (Interfaces.get(459) != null) {
					state = FlaxState.INTERFACE;
					break;
				}
				if (Functions.percentageBool(80)) {
					MouseMoveJoe.hoverMouse(HoverBox.get(28, 90, 1), c2c13c14c17, 2, true, 20, true);
				} else {
					while (!c2c13c14c17.checkCondition()) {
						sleep(200, 500);
					}
				}

				if (Player.getRSPlayer().isInCombat()) {
					MouseMoveJoe.hoverMouse(HoverBox.get(1), c16, 2, true, 20, true);
					break;
				}

				if (c13.checkCondition() || c14.checkCondition() || c17.checkCondition()) {
					break;
				}
				state = FlaxState.INTERFACE;
				break;
			case INTERFACE:
				if (Functions.percentageBool(5)) {
					try {
						Interfaces.get(459).getChild(91).click("Make X");
					} catch (NullPointerException e) {
						state = FlaxState.GO_SPIN;
						break;
					}
				} else {
					try {
						MouseMoveJoe.humanMouseMove(new TBox(Interfaces.get(459).getChild(91).getAbsoluteBounds()),
								1.5);
					} catch (NullPointerException e) {
						state = FlaxState.GO_SPIN;
						break;
					}
					if (Functions.percentageBool(100)) {
						SleepJoe.sleepHumanDelay(0.2, 1, 800);
					}
					clickKey();
				}
				Timer timeMake = new Timer(1000000000);
				while (!Interfaces.get(162).getChild(42).isHidden() && timeMake.getElapsed() < 5000) {
					sleep(200, 500);
				}
				if (timeMake.getElapsed() >= 5000) {
					break;
				}
				SleepJoe.sleepHumanDelay(3, 1, 6000);
				typeAmount();

				// Antiban------------------------------------------------
				thingsToCheck();
				// -------------------------------------------------------

				stairs2 = Functions.findNearestId(15, 16672);
				if (Functions.percentageBool(90)) {
					MouseMoveJoe.hoverMouseSpinning(new TBox(stairs2.getModel().getCentrePoint(), 80), c3c8,
							General.random(2, 3), true, 30, false, P_CHANCE_OUT, P_MOUSE_OUT_ALL);
				} else {
					MouseMoveJoe.hoverMouseSpinning(HoverBox.get(1), c3c8, General.random(2, 3), true, 30, false,
							P_CHANCE_OUT, P_MOUSE_OUT_ALL);
				}

				if (Inventory.find(1779).length != 0) {
					break;
				}
				state = FlaxState.BANK;
				break;
			case WAIT:
				break;
			default:
				break;
			}
		}
	}

	private RSObject findGoodStairs() {
		RSObject[] objs = Objects.findNearest(20, 16673);
		for (RSObject o : objs) {
			if (o.getPosition().equals(new RSTile(3205, 3208, 2))) {
				return o;
			}
		}
		return null;
	}

	private void randomSleep() {
		if (Functions.percentageBool100000(P_RANDOMSLEEP)) {
			sleep(200, 5000);
		}
	}

	private void thingsToCheck() {
		CustomAntiban.antibanFlax();
		if (!Game.isRunOn() && Game.getRunEnergy() > runEnergy) {
			MouseMoveJoe.humanMouseMove(new TBox(544, 126, 591, 150), Functions.randomDouble(1.0, 1.5));
			MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
			runEnergy = util.generateRunActivation() + 30 > 99 ? 99 : util.generateRunActivation() + 30;
		}
		if (Functions.percentageBool100000(P_MOUSE_LEAVE)) {
			action = "Mouse out...";
			Mouse.leaveGame();
			General.sleep(1000, 30000);
		}
		// Small break;

		if (Functions.percentageBool100000(P_LOGOUT)) {
			action = "Break...";
			setLoginBotState(false);
			Login.logout();
			Mouse.leaveGame();
			sleep(MIN_BREAK, MAX_BREAK);
			Login.login();
			setLoginBotState(true);
			SleepJoe.sleepHumanDelay(2, 1, 4000);
			while (Game.getGameState() != 30) {
				sleep(200, 400);
			}
			MouseMoveJoe.humanMouseMove(new TBox(272, 293, 489, 371), 2);
			MouseMoveJoe.fastClick(1, 1);
			;
			General.sleep(1000, 3000);
		}
		CustomAntiban.antibanFlax();
	}

	private void openDoorBack() {
		RSObject[] gate = Objects.findNearest(General.random(5, 6), 1543);
		if (c13.checkCondition()) {
			while (gate.length > 0) {
				MouseMoveJoe.playMouseFollowObject(gate[0], "Open", SPEED_OPEN_DOOR, null, true, 0, 0, 0, 0, 5);
				randomSleep();
				MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
				if (Game.getCrosshairState() != 2) {
					openDoorBack();
				}
				MouseMoveJoe.hoverMouse(HoverBox.get(33), c11, General.random(2, 4), false, 20, false);
				gate = Objects.findNearest(5, 1543);
			}
		}
	}

	private void openDoor() {
		RSObject[] gate = Objects.findNearest(5, 1543);
		if (c12.checkCondition()) {
			while (gate.length > 0) {
				MouseMoveJoe.playMouseFollowObject(gate[0], "Open", SPEED_OPEN_DOOR, null, true, 0, 0, 0, 0, 5);
				randomSleep();
				MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
				randomSleep();
				if (Game.getCrosshairState() != 2) {
					openDoor();
				}
				MouseMoveJoe.hoverMouse(HoverBox.get(30, 95, 1), c11, General.random(2, 4), true, 20, true);
				gate = Objects.findNearest(5, 1543);
			}
		}
	}

	private void clickDeposit() {
		if (Functions.percentageBool(P_DEPOSIT)) {
			MouseMoveJoe.humanMouseMove(new TBox(431, 300, 456, 325), Functions.randomDouble(1.0, 2.0));
			randomSleep();
			if (Game.isUptext("Deposit inventory"))
				MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
			else
				clickDeposit();
		} else {
			Banking.depositAll();
		}
	}

	private void clickBank(RSObject obj, String text, int offx, int offy) {
		do {
			MouseMoveJoe.playMouseFollowObjectText(obj, text, Functions.randomDouble(1.5, 2.0), null, true, offx, offy,
					0, 0, 20);
			randomSleep();
			if (Game.isUptext(text)) {
				if (Functions.percentageBool(0)) {
					MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
				} else {
					while (!bankLumb.click(text)) {
						if (Banking.isBankScreenOpen()) {
							break;
						}
						sleep(200, 400);
					}
					break;
				}
			}
			if (Game.getCrosshairState() == 1) {
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}
		} while (Game.getCrosshairState() != 2);
		if (Player.getRSPlayer().getInteractingIndex() != -1) {
			clickBank(obj, text, offx, offy);
		}
	}

	private void clickMinimap(RSTile tile) {
		Point pMM = Projection.tileToMinimap(tile);
		MouseMoveJoe.humanMouseMove(new TBox(pMM, 11),
				Functions.randomDouble(SPEED_CLICK_STAIRS_MIN, SPEED_CLICK_STAIRS_MAX));
		randomSleep();
		MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
		if (Functions.percentageBool(P_MULTIPLE_CLICKS_MM)) {
			SleepJoe.sleepHumanDelay(1, 1, 1000);
			pMM = Projection.tileToMinimap(tile);
			MouseMoveJoe.humanMouseMove(new TBox(pMM, 11),
					Functions.randomDouble(SPEED_CLICK_STAIRS_MIN, SPEED_CLICK_STAIRS_MAX));
			randomSleep();
			MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
			if (Functions.percentageBool(P_MULTIPLE_CLICKS_MM)) {
				SleepJoe.sleepHumanDelay(1, 1, 1000);
				pMM = Projection.tileToMinimap(tile);
				MouseMoveJoe.humanMouseMove(new TBox(pMM, 11),
						Functions.randomDouble(SPEED_CLICK_STAIRS_MIN, SPEED_CLICK_STAIRS_MAX));
				randomSleep();
				MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
			}
		}
	}

	private void clickKey() {
		MouseMoveJoe.fastClick(3, SPEED_FAST_CLICK);
		randomSleep();
		Point pt = new Point((int) Mouse.getPos().getX(), (int) (Mouse.getPos().getY() + 71));
		SleepJoe.sleepHumanDelay(0.3, 0, 600);
		Mouse.hop(pt);
		randomSleep();
		SleepJoe.sleepHumanDelay(0.2, 0, 600);
		MouseMoveJoe.fastClick(1, SPEED_FAST_CLICK);
	}

	private void typeAmount() {
		if (Functions.percentageBool(96)) {
			Keyboard.holdKey('9', 57, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			Keyboard.holdKey('9', 57, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			if (Functions.percentageBool(7)) {
				Keyboard.holdKey('9', 57, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				SleepJoe.sleepHumanDelay(1, 30, 60);
			}
		} else {
			Keyboard.holdKey('2', 50, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			Keyboard.holdKey('8', 56, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			if (Functions.percentageBool(7)) {
				Keyboard.holdKey('8', 56, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				SleepJoe.sleepHumanDelay(1, 30, 60);
			}
		}
		if (Functions.percentageBool(1)) {
			General.sleep(1000, 12000);
		}
		SleepJoe.sleepHumanDelay(1.4, 120, 240);
		randomSleep();
		Keyboard.pressEnter();
		SleepJoe.sleepHumanDelay(0.2, 30, 60);
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private long startTime = System.currentTimeMillis();
	private long startXP = Skills.getXP(Skills.SKILLS.CRAFTING);

	@Override
	public void onPaint(Graphics g) {

		long timeRan = (System.currentTimeMillis() - startTime) / 1000;
		long expGained = Skills.getXP(Skills.SKILLS.CRAFTING) - startXP;
		long bowStringDone = (expGained / 15);
		long bowHour = Math.round(Double.valueOf(bowStringDone) / Double.valueOf(Double.valueOf(timeRan) / 3600));
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		g.drawString("Temps: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)),
				20, 70);
		g.drawString("Action: " + action, 20, 90);
		g.drawString("Number strings: " + bowStringDone, 20, 110);
		g.drawString("Strings/hour: " + bowHour, 20, 130);

		// if (Zulrah.zulrah != null) {
		// g.drawString("Zulrah animation: " + Zulrah.zulrah.getAnimation(),
		// 300, 370);
		// }
		// g.drawString("CheckDeath player : " + CheckDeath.playerDead + " zul:
		// " + CheckDeath.zulrahDead, 200, 390);
		// g.drawString("Player interacting index: " +
		// Player.getRSPlayer().getInteractingIndex(), 200, 410);
		// g.drawString("CheckRed shouldMove " + CheckRed.shouldMove, 200, 430);
		// g.drawString("CheckJad " + CheckJad.shouldSwitch, 200, 450);

	}

}
