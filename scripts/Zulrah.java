package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.Painting;

import scripts.utilities.CamThread;
import scripts.utilities.ConditionTime;
import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.SleepJoe;
import scripts.utilities.StaticTimer;
import scripts.utilities.TBox;
import scripts.zulrahUtilities.BankZul;
import scripts.zulrahUtilities.BankingState;
import scripts.zulrahUtilities.CheckDeath;
import scripts.zulrahUtilities.CheckJad;
import scripts.zulrahUtilities.CheckRed;
import scripts.zulrahUtilities.CheckZulrah;
import scripts.zulrahUtilities.ConditionZul;
import scripts.zulrahUtilities.CustomAntiban;
import scripts.zulrahUtilities.DeadState;
import scripts.zulrahUtilities.DeadZul;
import scripts.zulrahUtilities.HealthZul;
import scripts.zulrahUtilities.LootingZul;
import scripts.zulrahUtilities.MoveMouseZul;
import scripts.zulrahUtilities.PositionZul;
import scripts.zulrahUtilities.PrayerZul;
import scripts.zulrahUtilities.Switching;
import scripts.zulrahUtilities.ZulrahState;

public class Zulrah extends Script implements Painting {

	// Constantes spécifique pour un bot!
	public final static int PERCENT_DEBUT_CLICK_SCREEN = 80;
	public final static double SPEED_HOVER = 3;
	public final static double SPEED = 0.3;
	public final static double SPEED_CLICK = 1;
	public final static double SPEED_COLLECT_ITEMS = 2;
	public static final double SPEED_PRAYER = 0.7;
	public final static int PERCENT_PHASE_HOVER = 80;
	public static double cashMade = 0;
	public static double cashInventory = 0;
	public static double nbKills = 0;
	public static double nbDeaths = 0;
	// State de depart
	public static ZulrahState state = ZulrahState.KILLING;
	public static BankingState bankState = BankingState.TELEPORTING;
	public static DeadState deadState = DeadState.WALKTOSTAIRS;

	// Constantes
	public final static int ZULRAH_GREEN = 2042;
	public final static int ZULRAH_RED = 2043;
	public final static int ZULRAH_BLUE = 2044;

	// Souris/keyboard/sleep/camera

	private CamThread cam = new CamThread();
	private BankZul bankZul = new BankZul(this);
	private HealthZul health = new HealthZul();
	private DeadZul deadZul = new DeadZul(this);

	// Prayer/health
	public static ABCUtil util = new ABCUtil();

	// Positions
	public static PositionZul p1 = null;
	public static PositionZul p2 = null;
	public static PositionZul p3 = null;
	public static PositionZul p4 = null;
	public static PositionZul p5 = null;
	public static PositionZul p6 = null;
	public static PositionZul p7 = null;
	public static PositionZul p8 = null;
	public static PositionZul p9 = null;
	public static PositionZul p10 = null;
	public static PositionZul p11 = null;
	public static PositionZul p12 = null;
	public static PositionZul p13 = null;
	public static PositionZul p14 = null;
	public static PositionZul p15 = null;
	public static PositionZul p16 = null;
	public static PositionZul p17 = null;

	// Tile du debut
	public RSTile debut = null;
	public static RSTile zulLeft = null;
	public static RSTile zulTop = null;
	public static RSTile zulMiddle = null;
	public static RSTile zulRight = null;

	// Condition de la boucle KILLING
	public boolean dead = false;

	// Zulrah
	public static RSNPC zulrah = null;
	public static RSModel zulMod = null;
	public boolean nextHasStarted = true;

	// Objets banking
	public static RSObject bankClanWars = null;
	public static RSObject portal = null;
	public static RSObject boatZulrah = null;
	public static RSObject stairsLumb = null;
	public static RSObject bankLumby = null;

	// RSItems

	// Tiles
	public static RSTile tile1camelot = new RSTile(2753, 3473, 0);
	public static RSTile tile2camelot = new RSTile(2762, 3481, 0);
	public static RSTile tile1cw = new RSTile(3382, 3155, 0);
	public static RSTile tile2cw = new RSTile(3391, 3164, 0);
	public static RSTile tile1port = new RSTile(3325, 4750, 0);
	public static RSTile tile2port = new RSTile(3329, 4752, 0);
	public static RSTile tile1zul = new RSTile(2193, 3053, 0);
	public static RSTile tile2zul = new RSTile(2200, 3059, 0);
	public static RSTile tile1boat = new RSTile(2209, 3052, 0);
	public static RSTile tile2boat = new RSTile(2217, 3060, 0);
	public static RSTile tile1lumb = new RSTile(3218, 3214, 0);
	public static RSTile tile2lumb = new RSTile(3226, 3224, 0);
	public static RSTile tileDumb = new RSTile(0, 0, 0);
	public static RSTile tileBank = new RSTile(3369 + Functions.generateRandomInt(-1, 1),
			3170 + Functions.generateRandomInt(-1, 1));
	public static RSTile tilePortal = new RSTile(3352 + Functions.generateRandomInt(-1, 1),
			3163 + Functions.generateRandomInt(-1, 1));
	public static RSTile tileStairs = new RSTile(3207 + Functions.generateRandomInt(-1, 1),
			3211 + Functions.generateRandomInt(-1, 1));
	public static RSTile tileLumbBank = new RSTile(3208 + Functions.generateRandomInt(-1, 1),
			3219 + Functions.generateRandomInt(-1, 1), 2);
	public static RSTile tileBoat = new RSTile(2213 + Functions.generateRandomInt(-1, 1),
			3056 + Functions.generateRandomInt(-1, 1));

	// Conditions
	public static ConditionZul c1c2c3c5c19 = null;
	public static ConditionZul c1c2c6c19 = null;
	public static ConditionZul c3c4c13c19 = null;
	public static ConditionZul c6c15c19 = null;
	public static ConditionZul c1c2c3c6c19 = null;
	public static ConditionZul c17 = new ConditionZul(tileDumb, 17, 3003);
	public static ConditionZul c16 = new ConditionZul(tileDumb, 16, 3003);
	public static ConditionZul c20 = new ConditionZul(tileDumb, 20, 3003);
	public static ConditionZul c21 = new ConditionZul(tileDumb, 21, 3003);
	public static ConditionZul c22 = new ConditionZul(tileDumb, 22, 3003);
	public static ConditionZul c23 = new ConditionZul(tileDumb, 23, 3003);
	public static ConditionZul c24 = new ConditionZul(tileDumb, 24, 3003);
	public static ConditionZul c25 = new ConditionZul(tileDumb, 25, 36, 3003);
	public static ConditionZul c26 = new ConditionZul(tileDumb, 26, 3003);
	public static ConditionZul c27 = new ConditionZul(tileDumb, 27, 3003);
	public static ConditionZul c28 = new ConditionZul(tileDumb, 28, 3003);
	public static ConditionZul c29 = new ConditionZul(tileDumb, 29, 3003);
	public static ConditionZul c30 = new ConditionZul(tileDumb, 30, 3003);
	public static ConditionZul c31 = new ConditionZul(tileDumb, 31, 3003);
	public static ConditionZul c32 = new ConditionZul(tileDumb, 32, 3003);
	public static ConditionZul c33 = new ConditionZul(tileDumb, 33, 3003);
	public static ConditionZul c37 = new ConditionZul(tileDumb, 37);

	private void onStart() {
		MoveMouseZul.loadDataNormal();
		// Suspendre les thread antiban et fatigue
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().contains("Antiban") || thread.getName().contains("Fatigue")) {
				thread.suspend();
			}
		}
		super.setAIAntibanState(false);
		// Mouse speed
		Mouse.setSpeed(105);
		// Load les boxes.
		HoverBox.load();
	}

	@Override
	public void run() {

		onStart();

		if (Functions.playerOnTiles(Player.getPosition(), tile1lumb, tile2lumb)) {
			state = ZulrahState.DEAD;
		} else if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3364, 3149, 0),
				new RSTile(3399, 3178, 0))) {
			state = ZulrahState.BANKING;
		}

		/** BOUCLE PRINCIPAL **/
		while (true) {
			switch (state) {
			/** KILLING ZULRAH **/
			case KILLING:

				
				if (Functions.percentageBool(2)){
					CustomAntiban.leaveMouseFor(General.random(3000, 20000));
				}
				
				MoveMouseZul.hoverMouse(HoverBox.get(9), c26);// TODO: Find a
																// good hook
				MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(2, 1, 10000)),
						SPEED_HOVER, false);

				if (Functions.percentageBool(2)){
					CustomAntiban.leaveMouseFor(General.random(3000, 20000));
				}
				
				CheckDeath cdt = new CheckDeath();
				Thread thr = new Thread(cdt);
				thr.start();

				// Rotation du zulrah
				int rotation = 0;

				// Tile du début + positions associés
				debut = Player.getPosition();
				p1 = new PositionZul(debut, 1);
				p2 = new PositionZul(debut, 2);
				p3 = new PositionZul(debut, 3);
				p4 = new PositionZul(debut, 4);
				p5 = new PositionZul(debut, 5);
				p6 = new PositionZul(debut, 6);
				p7 = new PositionZul(debut, 7);
				p8 = new PositionZul(debut, 8);
				p9 = new PositionZul(debut, 9);
				p10 = new PositionZul(debut, 10);
				p11 = new PositionZul(debut, 11);
				p12 = new PositionZul(debut, 12);
				p13 = new PositionZul(debut, 13);
				p14 = new PositionZul(debut, 14);
				p15 = new PositionZul(debut, 15);
				p16 = new PositionZul(debut, 16);
				p17 = new PositionZul(debut, 17);
				zulLeft = new RSTile(debut.getX() + 7, debut.getY() + 5, 0);
				zulMiddle = new RSTile(debut.getX(), debut.getY() + 6, 0);
				zulTop = new RSTile(debut.getX(), debut.getY() - 2, 0);
				zulRight = new RSTile(debut.getX() - 7, debut.getY() + 5, 0);

				// Conditions
				c1c2c3c5c19 = new ConditionZul(debut, 1, 2, 3, 5, 19);
				c1c2c6c19 = new ConditionZul(debut, 1, 2, 6, 19);
				c3c4c13c19 = new ConditionZul(debut, 1, 3, 4, 13, 19);
				c6c15c19 = new ConditionZul(debut, 6, 15, 19);
				c1c2c3c6c19 = new ConditionZul(debut, 1, 2, 3, 6, 19);

				// Se deplace a la position de depart
				RSTile togo = new RSTile(debut.getX() + 6, debut.getY() + 9, 0);
				if (Functions.percentageBool(PERCENT_DEBUT_CLICK_SCREEN)) {
					Point point = Projection.tileToScreen(togo, 0);

					if (point != null) {
						do {
							MoveMouseZul.humanMouseMoveNotStatic(new TBox(point, 30, 30), SPEED);
							MoveMouseZul.spamClick(new TBox(point, 10, 5), 3, 1);
						} while (!(Game.getCrosshairState() == 1));
						SleepJoe.sleepHumanDelay(0.2, 0, 600);
					}
				} else {
					RSTile[] goingPath = Walking.generateStraightPath(togo);
					Walking.walkPath(goingPath);
				}

				if (Functions.percentageBool(70)){
					if (Functions.percentageBool(100)) {
						cam.setCamera(General.random(170, 190), General.random(94, 103));
					}
					MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionZul(tileDumb, 34), SPEED_HOVER, true);
					zulrah = Functions.findNearestNPC(2042);
					zulMod = zulrah.getModel();

					MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 6000)),
							SPEED_HOVER, false);
					PrayerZul.checkMystic();
					MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(0.2, 1, 6000)),
							SPEED_HOVER, false);
				} else {
					MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionZul(tileDumb, 34), SPEED_HOVER, true);
					zulrah = Functions.findNearestNPC(2042);
					zulMod = zulrah.getModel();

					MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 6000)),
							SPEED_HOVER, false);
					PrayerZul.checkMystic();
					MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(0.2, 1, 6000)),
							SPEED_HOVER, false);
					if (Functions.percentageBool(100)) {
						cam.setCamera(General.random(170, 190), General.random(94, 103));
					}
				}
				
				CheckDeath.restart();
				executeStateComplete(ZULRAH_GREEN, p1, true, false, false, 0, null, p1, 19029, true, true, false, true,
						zulMiddle, zulLeft, true);

				MoveMouseZul.hoverMouse(HoverBox.get(25,80,1), new ConditionZul(debut, 5), SPEED_HOVER, true);

				if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073 && Zulrah.zulrah.getID() == 2042) {
					rotation = 3;
				} else if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
						&& Zulrah.zulrah.getID() == 2043) {
					rotation = 0;
				} else if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
						&& Zulrah.zulrah.getID() == 2044) {
					rotation = 4;
				}

				while (!dead) {

					switch (rotation) {
					// Beginning DE ROTATION 1 ET 2 - Determine la prochaine
					// rotation
					case 0:
						println("2");
						Combat.selectIndex(1);
						executeStateRedCoin(ZULRAH_RED, ZULRAH_BLUE, p8, true, true, false, false, true, false); // 12560
						println("3");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p11, false, true, true, 0, null, p5, 11194, true, false, zulMiddle,
								zulRight);//
						println("4");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p15, true, false, false, 0, null, p5, 23962, true, false, zulRight,
								zulMiddle);
						if (state != ZulrahState.KILLING) {
							break;
						}
						MoveMouseZul.hoverMouse(new TBox(0, 0, 700, 500), new ConditionZul(debut, 5));
						if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
								&& Zulrah.zulrah.getID() == 2043) {
							rotation = 1;
						} else if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
								&& Zulrah.zulrah.getID() == 2044) {
							rotation = 2;
						}
						break;

					// ***Rotation melee
					// 1******************************************************************************
					case 1:
						println("5");
						Combat.selectIndex(1);
						executeState(ZULRAH_RED, p10, true, true, false, 0, null, p12, 14067, false, true, zulMiddle,
								zulRight);
						println("6");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p12, false, true, true, 0, null, p11, 12568, true, false, zulRight,
								zulTop);
						println("7");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p2, true, false, false, 0, null, p16, 17262, false, true, zulTop,
								zulTop);
						println("8");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p16, false, true, true, 3002, p11, p12, 22504, false, false, zulTop,
								zulRight);
						println("JAD");
						Combat.selectIndex(1);
						executeJad(false, ZULRAH_GREEN, p12, true, 10);
						println("10");
						Combat.selectIndex(1);
						executeStateRedCoin(ZULRAH_RED, ZULRAH_GREEN, p9, true, true, false, false, false, false);
						rotation = 5;
						break;

					// ***Rotation melee
					// 2******************************************************************************
					case 2:
						println("5");
						Switching.switchLongRange();
						executeState(ZULRAH_BLUE, p5, false, true, true, 0, null, p10, 24325, true, true, zulTop,
								zulMiddle);
						Switching.switchRapid();
						println("6");
						Combat.selectIndex(1);
						executeState(ZULRAH_RED, p10, true, true, false, 0, null, p13, 14175, true, false, zulMiddle,
								zulLeft);
						println("7");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p13, true, false, false, 0, null, p16, 13106, false, true, zulLeft,
								zulTop);
						println("8");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p16, false, true, true, 0, null, p12, 21656, true, false, zulTop,
								zulRight);
						println("JAD");
						Combat.selectIndex(1);
						executeJad(false, ZULRAH_GREEN, p12, true, 10);// 30327
						println("10");
						Combat.selectIndex(1);
						executeStateRedCoin(ZULRAH_RED, ZULRAH_GREEN, p9, true, true, false, false, false, false);
						rotation = 5;
						break;

					// ***Rotation ranged
					// ranged******************************************************************************
					case 3:
						println("2");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p1, true, false, false, 0, null, p9, 19029, true, true, zulLeft,
								zulMiddle);
						println("3");
						Combat.selectIndex(1);
						executeStateRedCoin(ZULRAH_RED, ZULRAH_BLUE, p9, true, true, false, false, false, true);// 24826
						println("4");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p12, false, true, true, 0, null, p2, 13222, true, false, zulRight,
								zulTop);
						println("5");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p2, true, false, false, 0, null, p2, 13222, false, true, zulTop,
								zulLeft);
						println("6");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p2, false, true, true, 0, null, p12, 10760, true, false, zulLeft,
								zulMiddle);
						println("7");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p12, true, false, false, 0, null, p12, 15594, true, false, zulMiddle,
								zulRight);
						println("8");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p12, true, false, false, 0, null, p11, 12968, false, true, zulRight,
								zulMiddle);
						println("9");
						Combat.selectIndex(1);
						executeStateComplete(ZULRAH_BLUE, p11, false, true, true, 0, null, p11, 21788, true, true, true,
								false, zulMiddle, zulLeft, true);
						println("JAD");
						Combat.selectIndex(1);
						executeJad(true, ZULRAH_GREEN, p11, false, 8);
						println("11");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p11, false, true, true, 0, null, p1, 20000, true, false, zulMiddle,
								zulMiddle);
						rotation = 5;
						break;

					// ***Rotation mage
					// magic******************************************************************************
					case 4:
						println("2");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p1, false, true, true, 0, null, p5, 22366, true, false, zulLeft,
								zulTop);
						println("3");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p5, true, false, false, 0, null, p5, 15220, false, true, zulTop,
								zulRight);
						println("4");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p5, false, true, true, 0, null, p14, 18944, true, true, zulRight,
								zulMiddle);
						println("5");
						Combat.selectIndex(1);
						executeState(ZULRAH_RED, p14, true, true, false, 0, null, p2, 16843, true, false, zulMiddle,
								zulLeft);
						println("6");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p2, true, false, false, 0, null, p5, 11488, true, false, zulLeft,
								zulTop);
						println("7");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p5, true, false, false, 3002, p2, p12, 20548, false, true, zulTop,
								zulRight);
						println("8");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p12, false, true, true, 0, null, p11, 20579, true, false, zulRight,
								zulMiddle);
						println("9");
						Combat.selectIndex(1);
						executeState(ZULRAH_GREEN, p11, true, false, false, 0, null, p11, 12915, false, true, zulMiddle,
								zulMiddle);
						println("10");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p11, false, true, true, 0, null, p11, 16978, true, true, zulMiddle,
								zulLeft);
						println("JAD!!!");
						Combat.selectIndex(1);
						executeJad(true, ZULRAH_BLUE, p11, false, 8);// 18346
						println("12");
						Combat.selectIndex(1);
						executeState(ZULRAH_BLUE, p11, false, true, true, 0, null, p1, 11597, true, false, zulMiddle,
								zulMiddle);
						rotation = 5;
						break;

					// On a passer a travers une rotation complete jusqu'au
					// debut.
					case 5:
						executeState(ZULRAH_GREEN, p1, true, false, false, 0, null, p1, 15626, true, false, zulMiddle,
								zulMiddle);
						if (state != ZulrahState.KILLING) {
							break;
						}
						MoveMouseZul.hoverMouse(new TBox(0, 0, 700, 500), new ConditionZul(debut, 5));
						if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
								&& Zulrah.zulrah.getID() == 2042) {
							rotation = 3;
						} else if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
								&& Zulrah.zulrah.getID() == 2043) {
							rotation = 0;
						} else if (Zulrah.zulrah != null && Zulrah.zulrah.getAnimation() == 5073
								&& Zulrah.zulrah.getID() == 2044) {
							rotation = 4;
						}
						break;
					}

				}
				cdt.stopMe();
				CheckDeath.restart();
				dead = false;
				break;
			/** LOOTING ZULRAH + TELE CLAN WARS **/
			case LOOTING:
				nbKills++;
				LootingZul.loot();
				CheckDeath.restart();
				break;
			/** REGEN + CLAN WARS BANKING **/
			case BANKING:
				bankZul.bank();
				CheckDeath.restart();
				break;
			/** RANDOM BREAK **/
			case RANDOMBREAK:
				break;
			/** WE ARE DEAD - BANKING LUMBRIDGE **/
			case DEAD:
				nbDeaths++;
				deadZul.returnToZul();
				CheckDeath.restart();
				break;
			/** TELEPORT ZUL'ANDRA + GOING BOAT **/
			case COLLECTITEMS:
				deadZul.collectItems();
				CheckDeath.restart();
				break;
			case WAYBACK:
				break;
			}

		}

	}

	private void executeState(int id, PositionZul p, boolean mage, boolean prayMage, boolean prayEagle, int cMilli,
			PositionZul p2, PositionZul pAfter, int timePhase, boolean mageAfter, boolean prayMageAfter, RSTile curZul,
			RSTile nextZul) {

		executeStateComplete(id, p, mage, prayMage, prayEagle, cMilli, p2, pAfter, timePhase, mageAfter, prayMageAfter,
				false, false, curZul, nextZul, true);
	}

	private void executeStateComplete(int id, PositionZul p, boolean mage, boolean prayMage, boolean prayEagle,
			int cMilli, PositionZul p2, PositionZul pAfter, int timePhase, boolean mageAfter, boolean prayMageAfter,
			boolean JadAfterDontSwitchPray, boolean firstDude, RSTile curZul, RSTile nextZul, boolean shouldProtect) {

		// ETAT:
		// -> Prayer
		// -> Position
		// -> Gear
		// -> Attack style
		// -> Eating style

		// ETAT SORTI
		// -> Zulrah a quitté.
		// -> Zulrah est mort.
		// -> Player est mort.
		// -> Numero de la phase de départ ne correpond plus.

		// VERIFICATIONS WHEN ATTACKING
		// -> Correct prayers
		// -> Correct attack style
		// -> Weilding right things

		if (state != ZulrahState.KILLING) {
			println("Sorti state: " + state);
			return;
		}

		// Déclaration de variables
		boolean phaseTermine = false;
		boolean doneSwitchPray = false;
		boolean camTurned = false;
		boolean camTurned2 = false;
		int phase = 0;
		boolean switchGearAfter = mage != mageAfter;
		boolean switchPrayAfter = prayMage != prayMageAfter;

		// Thread qui check si zulrah entre ou sort de l'écran.
		CheckZulrah cd = new CheckZulrah(zulrah, timePhase);
		Thread td = new Thread(cd);
		td.start();

		// Conditions
		ConditionTime cTime1 = new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 1000));
		ConditionZul c1c2c3c4cPc12c19 = new ConditionZul(debut, 1, 2, 3, 4, p.pos, 12, 19);

		if (cMilli != 0) {

			// Condition
			ConditionZul c1c2c3c5cP2c7 = new ConditionZul(debut, 1, 2, 3, 5, 12, p2.pos, cMilli, 19);

			// Variables nécessaire pour cette partie de la phase.
			boolean over = false;
			int phase2 = 0;

			// Variables nécessaire pour arreter cette partie de la phase.
			StaticTimer.reset();
			ConditionZul conTime = new ConditionZul(debut, cMilli);

			while (!over) {
				switch (phase2) {
				case -1:
					sleep(20, 30);
					if (checkPlayerOrZulrahDead()) {
						over = true;
						break;
					}
					break;
				case 0:
					sleep(20, 30);
					if (checkPlayerOrZulrahDead()) {
						over = true;
						break;
					}

					if (!p2.checkPlayerOk()) {
						println("Direction vers good position");
						p2.moveCorrectPos();
						if (!doneSwitchPray) {
							switchGearPray(mage, prayMage, prayEagle, firstDude, shouldProtect);
							doneSwitchPray = true;
						}
						health.checkGeneralhealth();
						health.restore();
						if (Game.getDestination() != null && !p.checkTileGoodPosition(Game.getDestination())) {
							break;
						}
						println("Enter hover");
						if (Functions.generateRandomInt(1, 100) < 10) {
							MoveMouseZul.hoverMouseZulrah(HoverBox.get(1), c1c2c3c6c19, 2, true, pAfter,
									switchGearAfter, switchPrayAfter, prayMage);
						} else {
							MoveMouseZul.hoverMouseZulrah(HoverBox.get(4), c1c2c3c6c19, 2, true, pAfter,
									switchGearAfter, switchPrayAfter, prayMage);
						}
						println("sorti hover");
					}

					phase2 = 1;
					println("sorti 1");
				case 1:
					if (firstDude) {
						PrayerZul.checkMystic();
					} else {
						Switching.correctPray(prayMage, prayEagle);
					}
					sleep(10, 20);
					if (checkPlayerOrZulrahDead()) {
						over = true;
						break;
					}

					if (!doneSwitchPray) {
						switchGearPray(mage, prayMage, prayEagle, firstDude, shouldProtect);
						doneSwitchPray = true;
					}
					if (!this.nextHasStarted) {
						if (!cd.enter) {
							if (Functions.generateRandomInt(1, 100) < 10) {
								MoveMouseZul.hoverMouse(HoverBox.get(1), c1c2c3c5c19);
							} else {
								MoveMouseZul.hoverMouse(HoverBox.get(4), c1c2c3c5c19);
							}
							health.checkGeneralhealth();
						}
						if (cd.enter) {
							phase2 = 2;
							println("sorti 2");
						}
					} else { // le zulrah est deja commencé
						phase2 = 2;
						println("sorti 2");
					}

					break;

				case 2:
					println("Entree attaque!");
					if (firstDude) {
						PrayerZul.checkMystic();
					} else {
						Switching.correctPray(prayMage, prayEagle);
					}
					sleep(10, 20);
					if (checkPlayerOrZulrahDead()) {
						over = true;
						break;
					}
					health.checkGeneralhealth();

					if (zulrah != null && zulrah.isOnScreen()) {
						if (zulrah.getAnimation() != 5072) {
							attackZulrah();
						}
					} else if (zulrah != null && !camTurned2) {
						cam.turnToTile(zulrah.getPosition());
						MoveMouseZul.hoverMouse(HoverBox.get(2),
								new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 2000)), 2, false);
						camTurned2 = true;
						break;
					}

					// On hover jusqua health,prayer,zulrah quitte ou pu a la
					// bonne
					// position.

					if (Functions.generateRandomInt(1, 100) < 10) {
						MoveMouseZul.hoverMouseZulrah(HoverBox.get(1), c1c2c3c5cP2c7, 2, true, pAfter, switchGearAfter,
								switchPrayAfter, prayMage);
					} else {
						MoveMouseZul.hoverMouseZulrah(HoverBox.get(4), c1c2c3c5cP2c7, 2, true, pAfter, switchGearAfter,
								switchPrayAfter, prayMage);
					}

					// Regarde vie
					health.checkGeneralhealth();

					// Regarder position
					if (!p2.checkPlayerOk() && !CheckDeath.playerDead && !CheckDeath.zulrahDead) {
						MoveMouseZul.hoverMouse(HoverBox.get(1), cTime1, 2, false);
						if (!p2.checkPlayerOk()) {
							p2.moveCorrectPos();
							MoveMouseZul.hoverMouse(HoverBox.get(2),
									new ConditionTime(SleepJoe.sleepHumanDelayOut(2.5, 1, 2000)), 2, false);
						}
					}

					if (conTime.checkCondition()) {
						this.nextHasStarted = true;
						over = true;
						SleepJoe.sleepHumanDelay(1, 0, 800);
					}

					if (checkPlayerOrZulrahDead()) {
						over = true;
						break;
					}

					break;
				}
			}
		}

		// Execution de la phase
		while (!phaseTermine) {
			switch (phase) {
			case 0:
				sleep(10, 20);
				// Regarde si player ou zulrah dead.
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				// Si player pas bonne position
				if (!p.checkPlayerOk()) {

					p.moveCorrectPos(); // bouger correct position

					// Mettre correct pray et correct gear.
					if (!doneSwitchPray) {
						switchGearPray(mage, prayMage, prayEagle, firstDude, shouldProtect);
						doneSwitchPray = true;
					}
					health.checkGeneralhealth(); // Regarder health

					health.restore(); // Restore

					if (!p.checkTileGoodPosition(Game.getDestination())) {
						break;
					}
					println("Enter hover");
					if (Functions.generateRandomInt(1, 100) < 10) {
						MoveMouseZul.hoverMouseZulrah(HoverBox.get(1), c1c2c3c6c19, 2, true, pAfter, switchGearAfter,
								switchPrayAfter, prayMage);
					} else {
						MoveMouseZul.hoverMouseZulrah(HoverBox.get(4), c1c2c3c6c19, 2, true, pAfter, switchGearAfter,
								switchPrayAfter, prayMage);
					}
					println("Sorti hover");
				}
				println("sorti 1");
				phase = 1;
			case 1:
				if (firstDude) {
					PrayerZul.checkMystic();
				} else {
					Switching.correctPray(prayMage, prayEagle);
				}
				sleep(10, 20);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				if (!doneSwitchPray) {
					switchGearPray(mage, prayMage, prayEagle, firstDude, shouldProtect);
					doneSwitchPray = true;
				}
				if (!this.nextHasStarted) {
					if (!cd.enter) {
						if (Functions.generateRandomInt(1, 100) < 10) {
							MoveMouseZul.hoverMouseZulrah(HoverBox.get(1), c1c2c3c5c19, 2, true, pAfter,
									switchGearAfter, switchPrayAfter, prayMage); // hover
						} else {
							MoveMouseZul.hoverMouseZulrah(HoverBox.get(4), c1c2c3c5c19, 2, true, pAfter,
									switchGearAfter, switchPrayAfter, prayMage);
						}
						health.checkGeneralhealth();
					}
					if (cd.enter) {
						phase = 2;
						println("sorti 2");
					}
				} else { // le zulrah est deja commencé
					phase = 2;
					println("sorti 2");
				}

				break;

			case 2:
				if (firstDude) {
					PrayerZul.checkMystic();
				} else {
					Switching.correctPray(prayMage, prayEagle);
				}
				sleep(20, 30);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				health.checkGeneralhealth();
				if (zulrah != null && zulrah.isOnScreen()) {
					if (zulrah.getAnimation() != 5072) {
						attackZulrah();
					}
				} else if (zulrah != null && !camTurned) {
					cam.turnToTile(zulrah.getPosition());
					MoveMouseZul.hoverMouse(HoverBox.get(2), new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 2000)),
							2, false);
					camTurned = true;
					break;
				}

				// On hover jusqua health,prayer,zulrah quitte ou pu a la bonne
				// position.

				if (Functions.generateRandomInt(1, 100) < 10) {
					MoveMouseZul.hoverMouseZulrah(HoverBox.get(1), c1c2c3c4cPc12c19, 2, true, pAfter, switchGearAfter,
							switchPrayAfter, prayMage);
				} else {
					MoveMouseZul.hoverMouseZulrah(HoverBox.get(4), c1c2c3c4cPc12c19, 2, true, pAfter, switchGearAfter,
							switchPrayAfter, prayMage);
				}

				// Regarde vie
				health.checkGeneralhealth();

				// Regarder position
				if (!p.checkPlayerOk() && !CheckDeath.playerDead && !CheckDeath.zulrahDead) {
					MoveMouseZul.hoverMouse(HoverBox.get(4), cTime1, 2, false);
					if (!p.checkPlayerOk()) {
						p.moveCorrectPos(); // TODO: implement
						MoveMouseZul.hoverMouse(HoverBox.get(2),
								new ConditionTime(SleepJoe.sleepHumanDelayOut(2.5, 1, 2000)), 2, false);
					}
				}

				// Si zulrah a quitter, on sort de la phase.
				if (cd.quit) {
					phaseTermine = true;
				}

				// Si l'autre zulrah est entree on met cette variable a true.
				if (cd.nextHasStarted) {
					this.nextHasStarted = true;
				} else {
					this.nextHasStarted = false;
				}

				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				break;
			}

		}
		if (Functions.percentageBool(87)) {
			cam.setCameraAngle(100);
			cam.setCameraRotation(Functions.generateRandomInt(170, 190));
		}
		cd.stopMe();

		println("Number threads: " + Thread.getAllStackTraces().size());
	}

	private void switchGearPray(boolean mage, boolean prayMage, boolean prayEagle, boolean firstDude,
			boolean shouldProtect) {
		boolean donePray = false;
		if (Functions.percentageBool(95)) {
			if (firstDude) {
				PrayerZul.checkMystic();
			} else if (shouldProtect) {
				Switching.correctPray(prayMage, prayEagle);
			}
			donePray = true;
		}
		Switching.correctSwitch(mage);
		if (!donePray) {
			if (firstDude) {
				PrayerZul.checkMystic();
			} else if (shouldProtect) {
				Switching.correctPray(prayMage, prayEagle);
			}
		}
	}

	private boolean checkPlayerOrZulrahDead() {
		// **** Checking Zulrah ou player dead ***********
		if (CheckDeath.playerDead) {
			state = ZulrahState.DEAD;
			dead = true;
			return true;
		}
		if (CheckDeath.zulrahDead) {
			state = ZulrahState.LOOTING;
			dead = true;
			return true;
		}
		// ***********************************************
		return false;
	}

	private void executeStateRedCoin(int ID, int IDNext, PositionZul p, boolean mage, boolean prayMage,
			boolean prayEagle, boolean jad, boolean left, boolean shouldProtect) {

		if (state != ZulrahState.KILLING) {
			return;
		}

		// Déclaration de variables
		boolean phaseTermine = false;
		boolean doneSwitchPray = false;
		boolean goOriPos = false;
		int phase = 0;
		boolean camTurned = false;

		// Thread qui check si zulrah entre ou sort de l'écran.
		CheckZulrah cdZ = new CheckZulrah(zulrah, 3);
		Thread tdZ = new Thread(cdZ);
		tdZ.start();
		sleep(50);

		// Thread qui check si zulrah red should move.
		CheckRed cd = new CheckRed();
		Thread td = new Thread(cd);
		td.start();

		// ConditionTime c1s2false = new
		// ConditionTime(moveMM.sleepJoe.sleepHumanDelayOut(1, 1, 1000));

		// Execution de la phase
		while (!phaseTermine) {
			switch (phase) {
			case 0:
				sleep(10, 20);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				if (!p.checkPlayerOk()) {
					p.moveCorrectPos();
					health.checkGeneralhealth();
					if (!p.checkTileSafeRed() && !p.checkTileGoodPosition(Game.getDestination())) {
						break;
					}
					if (Functions.generateRandomInt(1, 100) < 10) {
						MoveMouseZul.hoverMouse(HoverBox.get(1), c1c2c6c19);
					} else {
						MoveMouseZul.hoverMouse(HoverBox.get(4), c1c2c6c19);
					}
				}
				phase = 1;
			case 1:
				sleep(10, 20);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				if (!doneSwitchPray && Functions.percentageBool(30)) {
					switchGearPray(mage, prayMage, prayEagle, false, shouldProtect);
					doneSwitchPray = true;
				}
				if (!this.nextHasStarted) {
					if (!cdZ.enter) {
						if (Functions.generateRandomInt(1, 100) < 10) {
							MoveMouseZul.hoverMouse(HoverBox.get(1), c1c2c3c5c19); // hover
						} else {
							MoveMouseZul.hoverMouse(HoverBox.get(4), c1c2c3c5c19);
						}
						health.checkGeneralhealth();
						sleep(5);

					}
					if (cdZ.enter) {
						phase = 2;
					}
				} else { // le zulrah est deja commencé
					phase = 2;
				}

				break;

			case 2:
				sleep(10, 20);
				health.checkGeneralhealth();
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				if (CheckRed.shouldMove) {
					p.moveRedCoin(left, goOriPos);
					cd.moved();
					health.checkGeneralhealth();
					if (!doneSwitchPray) {
						switchGearPray(mage, prayMage, prayEagle, false, shouldProtect);
						doneSwitchPray = true;
					}
					MoveMouseZul.hoverMouse(new TBox(Mouse.getPos(), 100),
							new ConditionTime(SleepJoe.sleepHumanDelayOut(2.5, 1, 2000)), SPEED_HOVER, false);
					goOriPos = !goOriPos;
					break;
				}

				// Si zulrah a quitter, on sort de la phase.
				if (cdZ.quit) {
					phaseTermine = true;
					if (cdZ.nextHasStarted) {
						this.nextHasStarted = true;
					} else {
						this.nextHasStarted = false;
					}
					break;
				}

				if (zulrah != null && zulrah.isOnScreen() && Player.getRSPlayer().getInteractingIndex() == -1
						&& !CheckRed.shouldMove) {
					if (zulrah.getAnimation() != 5072) {
						attackZulrah();
						break;
					}
				} else if (zulrah != null && !CheckRed.shouldMove && !camTurned) {
					cam.turnToTile(zulrah.getPosition());
					// moveMM.hoverMouse(new TBox(0,0,500,500), new
					// ConditionZul(debut,7));
					sleep(30);
					camTurned = true;
					break;
				}

				health.checkGeneralhealth();

				if (Functions.generateRandomInt(1, 100) < 10) {
					MoveMouseZul.hoverMouse(HoverBox.get(1), c3c4c13c19);
				} else {
					MoveMouseZul.hoverMouse(HoverBox.get(4), c3c4c13c19);
				}

				health.checkGeneralhealth();

				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}

				// Si zulrah a quitter, on sort de la phase.
				if (cdZ.quit) {
					phaseTermine = true;
				}
				if (cdZ.nextHasStarted) {
					this.nextHasStarted = true;
				} else {
					this.nextHasStarted = false;
				}
				break;
			}

		}
		cdZ.stopMe();
		cam.setCameraAngle(100);
		cam.setCameraRotation(Functions.generateRandomInt(170, 190));
		cd.stopMe();
	}

	private void executeJad(boolean left, int IDNext, PositionZul p, boolean moveAfterAttacks, int numberJadAtt) {
		// Déclaration de variables

		if (state != ZulrahState.KILLING) {
			return;
		}

		boolean prayMage = false;
		if (left) {
			prayMage = true;
		}
		Switching.correctPray(prayMage, false);

		boolean phaseTermine = false;
		boolean doneSwitchPray = false;
		int phase = 0;
		boolean camTurned = false;
		boolean camTurned2 = false;

		CheckZulrah cdZ = new CheckZulrah(zulrah, 3);
		Thread tdZ = new Thread(cdZ);
		tdZ.start();
		sleep(50);
		// Thread qui check si zulrah entre ou sort de l'écran.
		CheckJad cd = new CheckJad(numberJadAtt, prayMage);
		Thread td = new Thread(cd);
		td.start();

		ConditionZul c1c2c3c4cP2c12c19 = null;
		ConditionTime c1s2false = new ConditionTime(SleepJoe.sleepHumanDelayOut(1, 1, 1000));

		// Execution de la phase
		while (!phaseTermine) {
			switch (phase) {
			case 0:
				sleep(20, 30);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}

				// Si on n'est pas a la bonne position
				if (!p.checkPlayerOk()) {

					// Bouger vers la bonne position
					p.moveCorrectPos();

					// Si on n'a pas fait de switch
					if (!doneSwitchPray) {
						Switching.correctPray(prayMage, false);
						Switching.correctSwitch(true);
						doneSwitchPray = true;
					}

					// Si on doit changer de prayer
					if (CheckJad.shouldSwitch) {
						Switching.correctPray(cd.prayMage, false);
						cd.switched();
						health.checkGeneralhealth();
					}

					// Si destination est pas bonne
					if (Game.getDestination() != null && !p.checkTileGoodPosition(Game.getDestination())) {
						break;
					}
				}
				phase = 1;

			case 1:
				sleep(20, 30);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				// On fait le switch de gear.
				Switching.correctSwitch(true);

				MoveMouseZul.hoverMouse(HoverBox.get(4), c6c15c19);

				// Si on doit changer de prayer.
				if (CheckJad.shouldSwitch) {
					Switching.correctPray(cd.prayMage, false);
					cd.switched();
					health.checkGeneralhealth();
				}

				// Si la destination est nul.
				if (!Player.isMoving()) {
					phase = 2;
				}

				// Regarder santé
				health.checkGeneralhealth();

				break;

			case 2:
				sleep(20, 30);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}

				// Si on doit switch.
				if (CheckJad.shouldSwitch) {
					cd.switched();
					SleepJoe.sleepHumanDelay(0.5, 1, 400);
					Switching.correctPray(cd.prayMage, false);
					if (!cd.prayMage) {
						MoveMouseZul.humanMouseMove(new TBox(588, 332, 620, 351), SPEED);
					} else {
						MoveMouseZul.humanMouseMove(new TBox(628, 330, 654, 349), SPEED);
					}
					break;
				}

				if (cd.numberAttacks >= numberJadAtt) {
					if (moveAfterAttacks) {
						c1c2c3c4cP2c12c19 = new ConditionZul(debut, 1, 2, 3, 4, p2.pos, 12, 19);
						phase = 3;
						break;
					}
				}

				health.checkGeneralhealth();

				// Si le Zulrah a quitté
				if (cdZ.quit) {
					phaseTermine = true;
					health.checkGeneralhealth();
					if (cdZ.nextHasStarted) {
						this.nextHasStarted = true;
					} else {
						this.nextHasStarted = false;
					}
					sleep(1);
					break;
				}

				// Attaquer zulrah

				if (zulrah != null && zulrah.isOnScreen() && Player.getRSPlayer().getInteractingCharacter() == null
						&& !cdZ.quit && !CheckJad.shouldSwitch) {
					if (zulrah.getAnimation() != 5072) {
						attackZulrah();
						break;
					}
				} else if (zulrah != null && !cdZ.quit && !camTurned) { // Tourner
																		// la
					// caméra
					cam.turnToTile(zulrah.getPosition());
					// moveMM.hoverMouse(new TBox(0,0,500,500), new
					// ConditionZul(debut,7));
					sleep(30);
					camTurned = true;
					break;
				}

				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}

				// Si le Zulrah a quitté
				if (cdZ.quit) {
					phaseTermine = true;
					health.checkGeneralhealth();
					if (cdZ.nextHasStarted) {
						this.nextHasStarted = true;
					} else {
						this.nextHasStarted = false;
					}
					sleep(1);
					break;
				}

				break;
			case 3:
				cd.switched();
				cd.stopMe();
				General.sleep(20, 30);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				sleep(10);
				if (!p9.checkPlayerOk()) {

					p9.moveCorrectPos();
					health.checkGeneralhealth();
					sleep(5);

					// Si on ne clique pas a la bonne position (dest nest pas
					// dans bon carre) on recommence.
					if (!p9.checkTileGoodPosition(Game.getDestination())) {
						break;
					}

					// On hover jusqua ce que destination == null, ou health ou
					// prayer
					if (Functions.generateRandomInt(1, 100) < 10) {
						MoveMouseZul.hoverMouse(HoverBox.get(1), c1c2c3c6c19);
					} else {
						MoveMouseZul.hoverMouse(HoverBox.get(4), c1c2c3c6c19);
					}
				}

				phase = 4;
			case 4:
				sleep(20, 30);
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				// Si le zulrah n'est pas deja commencé
				// 1. hover jusqua health,pray,dest==null ou zulrah entre
				// Si le zulrah est entree alors on passe a la phase 2
				if (!doneSwitchPray) {
					Switching.correctPray(prayMage, false);
					Switching.correctSwitch(true);
					doneSwitchPray = true;
				}
				phase = 5;
				break;
			case 5:
				sleep(20, 30);
				// Regarder vie
				health.checkGeneralhealth();
				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}
				// Trouver le NPC
				// Si dans lecran on clique dessus
				// Sinon on bouge la camera jusqua ce qu'il soit dans l'écran et
				// on recommence le case
				if (zulrah != null && zulrah.isOnScreen()) {
					if (zulrah.getAnimation() != 5072) {
						attackZulrah();
					}
				} else if (zulrah != null && !camTurned2) {

					cam.turnToTile(zulrah.getPosition());
					// moveMM.hoverMouse(new TBox(0,0,500,500), new
					// ConditionZul(debut,7));
					sleep(30);
					camTurned2 = true;
					break;
				}

				// On hover jusqua health,prayer,zulrah quitte ou pu a la bonne
				// position.

				if (Functions.generateRandomInt(1, 100) < 10) {
					MoveMouseZul.hoverMouse(HoverBox.get(1), c1c2c3c4cP2c12c19);
				} else {
					MoveMouseZul.hoverMouse(HoverBox.get(4), c1c2c3c4cP2c12c19);
				}

				// Regarde vie
				health.checkGeneralhealth();

				// Regarder position
				if (!p9.checkPlayerOk()) {
					MoveMouseZul.hoverMouse(HoverBox.get(4), c1s2false, 2, false);
					if (!p9.checkPlayerOk()) {
						p9.moveCorrectPos(); // TODO: implement
					}
				}

				if (checkPlayerOrZulrahDead()) {
					phaseTermine = true;
					break;
				}

				// Si zulrah a quitter, on sort de la phase.
				if (cdZ.quit) {

					phaseTermine = true;
				}

				// Si l'autre zulrah est entree on met cette variable a true.
				if (cdZ.nextHasStarted) {

					this.nextHasStarted = true;
				} else {
					this.nextHasStarted = false;
				}
				break;
			}

		}
		cd.switched();
		cdZ.stopMe();
		cam.setCameraAngle(100);
		cam.setCameraRotation(Functions.generateRandomInt(170, 190));
		cd.stopMe();
	}

	private void attackWhileGoingTo(PositionZul p17) {
		// Loader l'objet zulrah

		do {
			attackZulrah();
			MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(3, 1, 2000)),
					SPEED_HOVER, false);
			p17.moveCorrectPos();
			MoveMouseZul.hoverMouse(HoverBox.get(15), new ConditionTime(SleepJoe.sleepHumanDelayOut(2, 1, 2000)),
					SPEED_HOVER, false);
		} while (!p17.checkPlayerOk());

		if (Player.getRSPlayer().getInteractingIndex() == -1) {
			attackZulrah();
		}

	}

	private void attackZulrah() {
		println("Attacking zul");
		boolean cont = false;
		while (Player.getRSPlayer().getInteractingIndex() == -1 && !HealthZul.getCheckHealth()
				&& (Zulrah.zulrah.getAnimation() != 5072) && !HealthZul.getCheckPrayer()
				&& zulrah.getAnimation() != 5072 && zulrah != null && zulrah.isOnScreen() && !CheckDeath.playerDead
				&& !CheckDeath.zulrahDead && !CheckRed.shouldMove && !CheckJad.shouldSwitch
				&& zulrah.getAnimation() != 5804 && !cont) {
			println("Clicking");
			cont = false;
			clickZulrah();
			SleepJoe.sleepHumanDelay(0.05, 1, 100);
			if (Game.getCrosshairState() == 2) {
				cont = true;
			}
			if (cont) {
				MoveMouseZul.hoverMouse(new TBox(Mouse.getPos(), 10),
						new ConditionTime(SleepJoe.sleepHumanDelayOut(0.8, 1, 1000)), 2, false);
			}
		}
	}

	private void clickZulrah() {
		ConditionZul condition = new ConditionZul(tileDumb, 1, 2, 4, 14, 13, 19);
		println(condition.checkCondition());
		if (condition.checkCondition()) {
			println(CheckJad.shouldSwitch);
			println(CheckRed.shouldMove);
			println(HealthZul.getCheckHealth());
			println(HealthZul.getCheckPrayer());
		}
		MoveMouseZul.playMouseFollowNPC(zulrah, "ulrah", SPEED, condition);
		if (Game.isUptext("ulrah")) {
			MoveMouseZul.fastClick(1, 0.5);
		}
	}

	Font font = new Font("Verdana", Font.BOLD, 14);
	private final long startTime = System.currentTimeMillis();
	
	@Override
	public void onPaint(Graphics g) {

		long timeRan = (System.currentTimeMillis() - startTime) / 1000;
		g.setFont(font);
		g.setColor(new Color(0, 255, 0));

		g.drawString("Profit el gars: " + cashMade, 20, 50);
		g.drawString("Temps: " + String.format("%d:%02d:%02d", timeRan / 3600, (timeRan % 3600) / 60, (timeRan % 60)),
				20, 70);
		g.drawString("Kills: " + nbKills, 20, 90);
		g.drawString("Deaths: " + nbDeaths, 20, 110);
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
