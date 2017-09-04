package scripts.spin_utilities;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.utilities.Functions;
import scripts.utilities.HoverBox;
import scripts.utilities.MouseMoveJoe;
import scripts.utilities.SleepJoe;
import scripts.utilities.TBox;

public class Utils {

	public static void openDoorBack() {
		RSObject[] gate = Objects.findNearest(General.random(5, 6), 1543);
		if (Variables.c13.checkCondition()) {
			long t = Timing.currentTimeMillis();
			while (gate.length > 0 && (Timing.currentTimeMillis() - t) < 10000) {
				MouseMoveJoe.playMouseFollowObject(gate[0], "Open", Variables.SPEED_OPEN_DOOR, null, true, 0, 0, 0, 0,
						5);
				CustomFlaxAB.randomSleep();
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				if (Game.getCrosshairState() != 2) {
					openDoorBack();
				}
				MouseMoveJoe.hoverMouse(HoverBox.get(33), Variables.c11, General.random(2, 4), false, 20, false, 4000);
				gate = Objects.findNearest(5, 1543);
			}
		}
	}

	public static void openDoor() {
		RSObject[] gate = Objects.findNearest(5, 1543);
		if (Variables.c12.checkCondition()) {
			long t = Timing.currentTimeMillis();
			while (gate.length > 0 && (Timing.currentTimeMillis() - t) < 10000) {
				MouseMoveJoe.playMouseFollowObject(gate[0], "Open", Variables.SPEED_OPEN_DOOR, null, true, 0, 0, 0, 0,
						5);
				CustomFlaxAB.randomSleep();
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				CustomFlaxAB.randomSleep();
				if (Game.getCrosshairState() != 2) {
					openDoor();
				}
				MouseMoveJoe.hoverMouse(HoverBox.get(30, 95, 1), Variables.c11, General.random(2, 4), true, 20, true,
						10000);
				gate = Objects.findNearest(5, 1543);
			}
		}
	}

	public static void clickDeposit() {
		if (Functions.percentageBool(Variables.P_DEPOSIT)) {
			MouseMoveJoe.humanMouseMove(new TBox(431, 300, 456, 325), Functions.randomDouble(1.0, 2.0));
			CustomFlaxAB.randomSleep();
			SleepJoe.sleepHumanDelay(0.2, 1, 1000);
			long t = Timing.currentTimeMillis();
			while (!Banking.isBankScreenOpen() && (Timing.currentTimeMillis()-t)<General.random(2000, 3000)){
				SleepJoe.sleepHumanDelay(0.5, 1, 2000);
			}
			if (Banking.isBankScreenOpen()) {
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			}
			SleepJoe.sleepHumanDelay(0.2, 1, 1000);
		} else {
			Banking.depositAll();
		}
	}

	public static void clickBank(RSObject obj, String text, int offx, int offy) {
		long t = Timing.currentTimeMillis();
		do {
			MouseMoveJoe.playMouseFollowObjectText(obj, text, Functions.randomDouble(1.5, 2.0), null, true, offx, offy,
					0, 0, 20);
			CustomFlaxAB.randomSleep();
			if (Game.isUptext(text)) {
				if (Functions.percentageBool(Variables.P_MOUSE_CLICK_BANK)) {
					MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
				} else {
					long t2 = Timing.currentTimeMillis();
					while (!Variables.bankLumb.click(text) && (Timing.currentTimeMillis() - t2) < 10000) {
						if (Banking.isBankScreenOpen()) {
							break;
						}
						General.sleep(200, 400);
					}
					break;
				}
			}
			if (Game.getCrosshairState() == 1) {
				SleepJoe.sleepHumanDelay(1, 1, 2000);
			}

		} while (!Banking.isBankScreenOpen() && (Game.getCrosshairState() != 2 && (Timing.currentTimeMillis() - t) < 10000));
		if (Player.getPosition().getY()>=3221){
			General.sleep(10,1000);
			Timing.waitCondition(new Condition() {
				
				@Override
				public boolean active() {
					General.sleep(10,20);
					return !Player.isMoving() || Banking.isBankScreenOpen();
				}
			}, General.random(3000, 6000));
		}
		if (Player.getRSPlayer().getInteractingIndex() != -1) {
			clickBank(obj, text, offx, offy);
		}
	}

	public static void clickMinimap(RSTile tile) {
		Point pMM = Projection.tileToMinimap(tile);
		MouseMoveJoe.humanMouseMove(new TBox(pMM, 15),
				Functions.randomDouble(Variables.SPEED_CLICK_STAIRS_MIN, Variables.SPEED_CLICK_STAIRS_MAX));
		CustomFlaxAB.randomSleep();
		MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
		if (Functions.percentageBool(Variables.P_MULTIPLE_CLICKS_MM)) {
			SleepJoe.sleepHumanDelay(1, 1, 1000);
			pMM = Projection.tileToMinimap(tile);
			MouseMoveJoe.humanMouseMove(new TBox(pMM, 15),
					Functions.randomDouble(Variables.SPEED_CLICK_STAIRS_MIN, Variables.SPEED_CLICK_STAIRS_MAX));
			CustomFlaxAB.randomSleep();
			MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			if (Functions.percentageBool(Variables.P_MULTIPLE_CLICKS_MM)) {
				SleepJoe.sleepHumanDelay(1, 1, 1000);
				pMM = Projection.tileToMinimap(tile);
				MouseMoveJoe.humanMouseMove(new TBox(pMM, 15),
						Functions.randomDouble(Variables.SPEED_CLICK_STAIRS_MIN, Variables.SPEED_CLICK_STAIRS_MAX));
				CustomFlaxAB.randomSleep();
				MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
			}
		}
	}

	public static void clickKey() {
		MouseMoveJoe.fastClick(3, Variables.SPEED_FAST_CLICK);
		CustomFlaxAB.randomSleep();
		Point pt = new Point((int) Mouse.getPos().getX(), (int) (Mouse.getPos().getY() + Variables.CLICK_OFFSET));
		SleepJoe.sleepHumanDelay(Variables.SLEEP_MODIF_RIGHT_CLICK, 0, 3000);
		Mouse.hop(pt);
		CustomFlaxAB.randomSleep();
		SleepJoe.sleepHumanDelay(0.5, 0, 3000);
		long t = Timing.currentTimeMillis();
		while (!ChooseOption.isOpen() && (Timing.currentTimeMillis() - t) < General.random(4000, 5000)) {
			SleepJoe.sleepHumanDelay(Variables.SLEEP_MODIF_RIGHT_CLICK, 0, 3000);
		}
		MouseMoveJoe.fastClick(1, Variables.SPEED_FAST_CLICK);
	}

	public static void typeAmount() {
		if (Functions.percentageBool(Variables.P_TYPE_99)) {
			Keyboard.holdKey('9', 57, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			CustomFlaxAB.randomSleep();
			SleepJoe.sleepHumanDelay(1, 30, 60);
			Keyboard.holdKey('9', 57, new Condition() {
				@Override
				public boolean active() {
					SleepJoe.sleepHumanDelay(0.2, 30, 60);
					return true;
				}
			});
			CustomFlaxAB.randomSleep();
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
			if (Functions.percentageBool(Variables.P_TYPE_28)) {
				Keyboard.holdKey('2', 50, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				CustomFlaxAB.randomSleep();
				SleepJoe.sleepHumanDelay(1, 30, 60);
				Keyboard.holdKey('8', 56, new Condition() {
					@Override
					public boolean active() {
						SleepJoe.sleepHumanDelay(0.2, 30, 60);
						return true;
					}
				});
				CustomFlaxAB.randomSleep();
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
			} else {
				Keyboard.typeString(String.valueOf(General.random(28, 1000)));
			}
		}
		if (Functions.percentageBool(1)) {
			General.sleep(1000, 12000);
		}
		SleepJoe.sleepHumanDelay(1.4, 120, 240);
		CustomFlaxAB.randomSleep();
		Keyboard.pressEnter();
		SleepJoe.sleepHumanDelay(0.2, 30, 60);
	}

	public static RSObject findGoodStairs() {
		RSObject[] objs = Objects.findNearest(20, 16673);
		for (RSObject o : objs) {
			if (o.getPosition().equals(new RSTile(3205, 3208, 2))) {
				return o;
			}
		}
		return null;
	}

}
