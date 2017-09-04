package scripts;

import java.awt.Point;

import org.tribot.api.input.Mouse;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import scripts.utilities.Condition;
import scripts.utilities.Functions;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;
import scripts.utilities.Timer;

@ScriptManifest(authors = { "Joe" }, category = "Agility", name = "AgiJoeV")
public class AgiVarrock extends Script {

	ABCUtil util = new ABCUtil();
	
	private final int ROCKCLIMB = 3027;
	MouseMoveJoe moveMM = new MouseMoveJoe();
	SleepJoe sleepJoe = new SleepJoe();
	
	private boolean onStart() {
		println("Sup!");
		return true;
	}

	@Override
	public void run() {
		setAIAntibanState(false);
		setRandomSolverState(false);
		setAutoResponderState(false);
		onStart();
		int state = 0;
		while (true) {
			Timer tm = null;
			switch (state) {
			case 0:
				println("0");
				RSTile tile = new RSTile(3222, 3414, 0);
				Point point = Projection.tileToMinimap(tile);
				moveMM.humanMouseMove(new TBox(point, 15, 7));
				moveMM.spamClick(new TBox(point, 10, 5), 2, 1);
				sleepJoe.sleepHumanDelay(0.2, 0, 600);
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(100, 0, 300, 50), new Condition(1, 0, 0, 2));
				if (tm.getElapsed() < 29000) {
					println(tm.getElapsed());
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.5, 0, 1000);
						moveMM.playMouseFollow(3219, 3413, 3222, 3416, ROCKCLIMB, "Climb", 2);
						checkCameraAngle();
						moveMM.fastClick(1, 1);
					} while (!(Game.getCrosshairState() == 2) && tm.getElapsed() < 10000);
				} else if (checkCaseHuit()) {
					state = 8;
					break;
				} else if (checkCaseUn()) {
					state = 1;
					break;
				} else if (checkCaseZero()) {
					state = 0;
					break;
				}

				state = 1;
				break;
			case 1:
				println("1");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(150, 10, 364, 80), new Condition(3, 0, 0, 2));
				sleep(Functions.generateRandomInt(200, 300));

				if (tm.getElapsed() < 29000) {
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.2, 0, 1000);
						try {
						moveMM.playMouseFollow(10587, "Cross", 2);
						} catch (NullPointerException e){
							sleepJoe.sleepHumanDelay(3, 500, 3000);
						}
						if (checkCaseZero()){
							state = 0;
							break;
						} else {
							moveMM.playMouseFollow(10587, "Cross", 2);
						}
						moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
					} while (!(Game.getCrosshairState() == 2));
				} else if (checkCaseZero()) {
					state = 0;
					break;
				} else if (checkCaseDeux()) {
					state = 2;
					break;
				} else if (checkCaseUn()) {
					state = 1;
					break;
				}

				if (state ==0){
					break;
				}
				state = 2;
				break;
			case 2:
				println("2");
				sleep(600,1500); // HOVER!!!!
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(150, 10, 364, 80), new Condition(12, 13, 0, 2));
				if (tm.getElapsed() < 29000) {
					if (Player.getPosition().getPlane() != 3) {
						sleepJoe.sleepHumanDelay(3, 0, 3000);
						walkBack();
						state = 0;
						sleepJoe.sleepHumanDelay(3, 0, 3000);
						break;
					}

					RSTile obj2 = new RSTile(3201, 3416, 3);
					RSObject gap = null;
					do {
						checkCameraAngle();
						gap = Functions.findNearestId(15, 10642); // change
						obj2 = new RSTile(3201, 3416, 3);
						if (gap != null) {
							if (gap.isOnScreen()) {
								sleepJoe.sleepHumanDelay(0.5, 50, 400);
								moveMM.playMouseFollow(10642, "Leap", 2);
								moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
								state = 3;
							}
						}
						if (obj2 != null && state != 3) {
							if (obj2.isOnScreen()) {
								sleepJoe.sleepHumanDelay(0.5, 50, 400);
								moveMM.humanMouseMove(new TBox(obj2.getHumanHoverPoint(), 20, 20));
								moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 1, 0);
							}
						}
					} while (!(Game.getCrosshairState() == 2));
				} else if (checkCaseUn()) {
					state = 1;
					break;
				} else if (checkCaseTrois()) {
					state = 3;
					break;
				} else if (checkCaseDeux()) {
					state = 2;
					break;
				}
				state = 3;
				break;
			case 3:
				println("3");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(90, 0, 320, 150), new Condition(4, 0, 0, 2));
				if (tm.getElapsed() < 29000) {
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.5, 0, 1000);
						moveMM.playMouseFollow(10777, "Balance", 2);
						moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
						;
					} while (!(Game.getCrosshairState() == 2));
				} else if (checkCaseDeux()) {
					state = 2;
					break;
				} else if (checkCaseQuatre()) {
					state = 4;
					break;
				} else if (checkCaseTrois()) {
					state = 3;
					break;
				}
				state = 4; // change
				break;
			case 4:
				println("4");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(27, 54, 188, 260), new Condition(5, 13, 0, 2));
				if (tm.getElapsed() < 29000) {
					if (Player.getPosition().getPlane() != 3) {
						sleepJoe.sleepHumanDelay(3, 0, 3000);
						walkBack();
						state = 0;
						sleepJoe.sleepHumanDelay(3, 0, 3000);
						break;
					}

					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.2, 0, 1000);
						try {
							moveMM.playMouseFollow(10778, "Leap", 2);
						} catch (NullPointerException e) {
							println("Caught one");
							sleep(3000,7000);
							moveMM.playMouseFollow(10778, "Leap", 2);
						}
						moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);

					} while (!(Game.getCrosshairState() == 2));
				} else if (checkCaseTrois()) {
					state = 3;
					break;
				} else if (checkCaseCinq()) {
					state = 5;
					break;
				} else if (checkCaseQuatre()) {
					state = 4;
					break;
				}
				state = 5; // change
				break;
			case 5:
				println("5");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(526,108,709,157), new Condition(14, 0, 0, 2));
				sleepJoe.sleepHumanDelay(3, 500, 3000);
				if (tm.getElapsed() < 29000) {
					Point point2 = null;
					RSTile tile2 = new RSTile(3208, 3397, 3);
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.2, 0, 1000);
						point2 = Projection.tileToMinimap(tile2);
						if (point2.x != -1) {
							moveMM.humanMouseMove(new TBox(point2, 15, 11));
							moveMM.spamClick(new TBox(point2, 10, 5), 4, 1);
							sleepJoe.sleepHumanDelay(1, 0, 600);
						}
					} while (point2.x == -1);
					tm = new Timer(100000);
					moveMM.hoverMouse(new TBox(54, 254, 473, 364), new Condition(6, 0, 0, 2));
					if (tm.getElapsed() < 29000) {
						do {
							checkCameraAngle();
							sleepJoe.sleepHumanDelay(0.2, 0, 1000);
							moveMM.playMouseFollow(10779, "Leap", 2); // change
																		// text
																		// +
																		// id
							moveMM.fastClick(1, 1);
						} while (!(Game.getCrosshairState() == 2));
					} else if (checkCaseQuatre()) {
						state = 4;
						break;
					} else if (checkCaseSix()) {
						state = 6;
						break;
					} else if (checkCaseCinq()) {
						state = 5;
						break;
					}
				} else if (checkCaseQuatre()) {
					state = 4;
					break;
				} else if (checkCaseSix()) {
					state = 6;
					break;
				} else if (checkCaseCinq()) {
					state = 5;
					break;
				}
				state = 6;
				break;
			case 6:
				println("6");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(526,108,709,157), new Condition(15, 0, 0, 2));
				if (tm.getElapsed() < 29000) {
					Point point3 = null;
					RSTile tile3 = new RSTile(3232, 3402, 3);
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.2, 0, 1000);
						point3 = Projection.tileToMinimap(tile3);
						if (point3.x != -1) {
							moveMM.humanMouseMove(new TBox(point3, 10, 5));
							moveMM.spamClick(new TBox(point3, 10, 5), 4, 1);
							while (!Player.isMoving()) {
								moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 1, 0);
							}
						}
					} while (point3.x == -1);
					tm = new Timer(100000);
					moveMM.hoverMouse(new TBox(54, 254, 473, 364), new Condition(7, 0, 0, 2));
					if (tm.getElapsed() < 29000) {
						do {
							checkCameraAngle();
							sleepJoe.sleepHumanDelay(0.2, 0, 1000);
							try {
								moveMM.playMouseFollow(10780, "Leap", 2); // change
																			// text
																			// +
																			// id
							} catch (NullPointerException e) {
								println("Caught one");
								sleep(3000,7000);
								moveMM.playMouseFollow(10780, "Leap", 2);
							}
							moveMM.fastClick(1, 1);
						} while (!(Game.getCrosshairState() == 2));
					} else if (checkCaseCinq()) {
						state = 5;
						break;
					} else if (checkCaseSept()) {
						state = 7;
						break;
					} else if (checkCaseSix()) {
						state = 6;
						break;
					}
				} else if (checkCaseCinq()) {
					state = 5;
					break;
				} else if (checkCaseSept()) {
					state = 7;
					break;
				} else if (checkCaseSix()) {
					state = 6;
					break;
				}
				state = 7;
				break;
			case 7:
				println("7");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(304, 83, 489, 266), new Condition(11, 0, 0, 2));
				if (tm.getElapsed() < 29000) {
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.2, 0, 1000);
						try {
							moveMM.playMouseFollow(10781, "Hurdle", 2);
						} catch (NullPointerException e) {
							println("Caught one");
							sleep(3000,7000);
							moveMM.playMouseFollow(10781, "Hurdle", 2);
						}
						moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
						while (!Player.isMoving()) {
							moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 1, 0);
						}
					} while (!(Game.getCrosshairState() == 2));
				} else if (checkCaseSix()) {
					state = 6;
					break;
				} else if (checkCaseHuit()) {
					state = 8;
					break;
				} else if (checkCaseSept()) {
					state = 7;
					break;
				}
				state = 8; // change
				break;
			case 8:
				println("8");
				tm = new Timer(100000);
				moveMM.hoverMouse(new TBox(304, 83, 489, 266), new Condition(9, 0, 0, 2));
				if (tm.getElapsed() < 29000) {
					do {
						checkCameraAngle();
						sleepJoe.sleepHumanDelay(0.2, 0, 1000);
						try{
						moveMM.playMouseFollow(10817, "Jump", 2); 
						} catch (NullPointerException e){
							println("Caught one");
							sleep(3000,6000);
							if (checkCaseZero()){
								break;
							}
							moveMM.playMouseFollow(10817, "Jump", 2); 
						}
						moveMM.spamClick(new TBox(Mouse.getPos(), 10, 10), 2, 1);
					} while (!(Game.getCrosshairState() == 2));

					state = 0;
					tm = new Timer(100000);
					while (Player.getRSPlayer().getPosition().getPlane() == 3 && tm.getElapsed()<10000) {
						checkCameraAngle();
						checkLevelUp();
						if (Functions.generateRandomInt(1, 30)==1){
							moveMM.humanMouseMove(new TBox(543,8,709,157));
						}
						sleep(200,400);
					}
					if (tm.getElapsed()>10000){
						state = 8;
						break;
					}
					
				} else if (checkCaseSept()) {
					state = 7;
					break;
				} else if (checkCaseZero()) {
					state = 0;
					break;
				} else if (checkCaseHuit()) {
					state = 8;
					break;
				}
				sleep(100,500);
				checkEnergy();
				break;
			}
		}

	}

	private boolean checkCaseZero() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3219, 3412, 0), new RSTile(3240, 3419, 0))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseUn() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3214, 3410, 3), new RSTile(3219, 3419, 3))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseDeux() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3201, 3413, 3), new RSTile(3208, 3417, 3))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseTrois() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3194, 3416, 1), new RSTile(3197, 3416, 1))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseQuatre() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3192, 3402, 3), new RSTile(3198, 3406, 3))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseCinq() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3182, 3382, 3), new RSTile(3208, 3403, 3))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseSix() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3218, 3393, 3), new RSTile(3232, 3403, 3))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseSept() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3236, 3403, 3), new RSTile(3240, 3408, 3))) {
			return true;
		}
		return false;
	}

	private boolean checkCaseHuit() {
		if (Functions.playerOnTiles(Player.getPosition(), new RSTile(3236, 3410, 3), new RSTile(3240, 3415, 3))) {
			return true;
		}
		return false;
	}

	private void checkLevelUp() {
		if (org.tribot.api2007.NPCChat.getClickContinueInterface() != null) {
			org.tribot.api2007.NPCChat.clickContinue(false);
		}

	}

	private void checkCameraAngle(){
		if (Camera.getCameraRotation() > 105 || Camera.getCameraRotation()<73){
			println("Camera rot");
			Camera.setCameraRotation(Functions.generateRandomInt(85, 95));
		}
		if (Camera.getCameraAngle()<90){
			println("Camera angle");
			Camera.setCameraAngle(100);
		}
	}
	
	private void walkBack() {
		Walking.walkPath(Walking.generateStraightPath(new RSTile(3222, 3414, 0)));
	}

	private void checkEnergy() {
		if (!Game.isRunOn() && Game.getRunEnergy() > Functions.generateRandomInt(60, 80)) {
			moveMM.humanMouseMove(new TBox(558, 135, 588, 145));
			moveMM.fastClick(1, 1);
		}
	}

}